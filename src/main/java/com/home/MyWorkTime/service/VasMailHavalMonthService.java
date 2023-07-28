/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Create and send a monthly report on Haval vehicles 
 * 
 *  Date of creation: 28/07/2023
 */
package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasMailNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasMailNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class VasMailHavalMonthService {

    private static final Logger LOGGER = Logger.getLogger(VasMailHavalMonthService.class.getName());

    private final VasMailNpsRepository vasMailNpsRepository;
    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;

    public VasMailHavalMonthService(VasMailNpsRepository vasMailNpsRepository,
                                  JavaMailSender javaMailSender,
                                  VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasMailNpsRepository = vasMailNpsRepository;
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

    @SneakyThrows
    @Scheduled(cron = "1 00 08 03 * *")
    private void SendMonthReportHaval(){
        
        List<VasMailNpsModel> listHaval = vasMailNpsRepository.npsListHaval();
        List<VasManagerNpsModel> addressManagerHaval = vasManagerNpsRepository.forMailHaval();
        //List<VasManagerNpsModel> addressManagerCopy = vasManagerNpsRepository.forMailHavalCopy();

        ArrayList<String> addressListHaval = new ArrayList<>();

        for (VasManagerNpsModel vasManagerNpsModel : addressManagerHaval) {
            addressListHaval.add(vasManagerNpsModel.getManager_email());
        }

        String setTo = addressListHaval.toString().replace("[", "").replace("]", "");
        
          if(!(listHaval.isEmpty())){
            FileInputStream reportHaval = new FileInputStream("D:\\MyWorkTime\\vasNPS\\src\\main\\resources\\reports\\reportHaval.xlsx");
            // "C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\reportHaval.xlsx"
               try(
                    XSSFWorkbook report = new XSSFWorkbook(reportHaval)){
                    XSSFSheet listReport = report.getSheetAt(0); 

                    XSSFRow rowCall;

                    //Количество строк исходя из количества автомобилей
                    for (int i = 0; i < listHaval.size()-1; i++){
                    rowCall = listReport.getRow(i + 10);
                    XSSFRow newRow = listReport.createRow(i + 11); 
                            newRow.copyRowFrom(rowCall, new CellCopyPolicy());
                    }
                
                    LocalDate date = LocalDate.now();
                    int months = date.getMonthValue() - 1;
                    int year = date.getYear();

                    String partName = null;
                    if (months == 1) {
                        partName = "ЯНВАРЬ";
                    } else if (months == 2) {
                        partName = "ФЕВРАЛЬ";
                    } else if (months == 3) {
                        partName = "МАРТ";
                    } else if (months == 4) {
                        partName = "АПРЕЛЬ";
                    } else if (months == 5) {
                        partName = "МАЙ";
                    } else if (months == 6) {
                        partName = "ИЮНЬ";
                    } else if (months == 7) {
                        partName = "ИЮЛЬ";
                    } else if (months == 8) {
                        partName = "АВГУСТ";
                    } else if (months == 9) {
                        partName = "СЕНТЯБРЬ";
                    } else if (months == 10) {
                        partName = "ОКТЯБРЬ";
                    } else if (months == 11) {
                        partName = "НОЯБРЬ";
                    } else if (months == 12) {
                        partName = "ДЕКАБРЬ";
                    }

                    for (int i = 0; i < listHaval.size(); i++){
                         rowCall = listReport.getRow(i + 10);
                         rowCall.getCell(0).setCellValue(months + "." + year);
                         rowCall.getCell(2).setCellValue(listHaval.get(i).getVehicle_identification_number());
                         rowCall.getCell(3).setCellValue(listHaval.get(i).getModel());
                         rowCall.getCell(4).setCellValue(listHaval.get(i).getDate_order());
                         rowCall.getCell(5).setCellValue(listHaval.get(i).getMileage());
                         rowCall.getCell(6).setCellValue(listHaval.get(i).getNum_order());
                    }

                    rowCall = listReport.getRow(0);
                    rowCall.getCell(0).setCellValue(partName + " " + String.valueOf(year));
                
                    String reportName = "Отчет Сервисная эффективность " + partName + " " + String.valueOf(year) + "г.xlsx";
                    
                    FileOutputStream reportOuts = new FileOutputStream("D:\\MyWorkTime\\vasNPS\\src\\main\\resources\\outputReports\\" + reportName);
                    // "C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Haval reports\\"
                    report.write(reportOuts);
                    reportOuts.close();


                    MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
                    helper.setFrom("info@vitautocity.by");
                    helper.setTo(setTo);  //-получатель
                    helper.setSubject(reportName);
                    helper.setText("""
                        Добрый день! \s
                          
                        Отчёт прикреплён к письму. """);
                    
                    FileSystemResource reports = new FileSystemResource("D:\\MyWorkTime\\vasNPS\\src\\main\\resources\\outputReports\\" + reportName);
                    // "C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Haval reports\\"
                    helper.addAttachment(reportName, reports);
                    javaMailSender.send(messageVasNpsMail);
               }
               LOGGER.log(Level.INFO, "Отчёт по автомобилям HAVAL сформирован и отправлен.");
               //System.out.println("Список SKODA сформирован и отправлен.");
          } else {
               //System.out.println("Список SKODA не сформирован, данных нет.");
               LOGGER.log(Level.INFO, "Отчёт по автомобилям HAVAL не сформирован, данных нет.");
          }
    }
}
