package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasFullReportNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasFullReportNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class VasMailSatisfactionReportService {

    private static final Logger LOGGER = Logger.getLogger(VasMailSatisfactionReportService.class.getName());

    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;
    private final VasFullReportNpsRepository vasFullReportNpsRepository;

    public VasMailSatisfactionReportService(JavaMailSender javaMailSender,
                                            VasManagerNpsRepository vasManagerNpsRepository,
                                            VasFullReportNpsRepository vasFullReportNpsRepository) {
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
        this.vasFullReportNpsRepository = vasFullReportNpsRepository;
    }

    @SneakyThrows
    @Scheduled(cron = "1 00 09 25 * *")
    private void customerSatisfactionReport(){
        List<VasFullReportNpsModel> listFullReportKia = vasFullReportNpsRepository.fullReportKiaForThisMonth();
        List<VasManagerNpsModel> addressFullReportKiaForThisMonth = vasManagerNpsRepository.forFullReportKiaForThisMonth();
        List<VasManagerNpsModel> addressFullReportKiaForThisCopy = vasManagerNpsRepository.forFullReportKiaForThisMonthCopy();

        ArrayList<String> addressFullReportKiaForMonth = new ArrayList<>();
        ArrayList<String> addressFullReportKiaForMonthCopy = new ArrayList<>();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd"); //Для формата вывода даты в excel

        for (VasManagerNpsModel vasManagerNpsModel : addressFullReportKiaForThisMonth){
            addressFullReportKiaForMonth.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressFullReportKiaForThisCopy){
            addressFullReportKiaForMonthCopy.add(vasManagerNpsModel.getManager_email());
        }

        //Ниже код для адресата отправки
        String setTo;
        String setCc;

        String replace = addressFullReportKiaForMonthCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!

        if (!(listFullReportKia.isEmpty())){

            FileInputStream templateSatisfactionKia = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\customerSatisfactionReport.xlsx");
            XSSFWorkbook reportSatisfactionKia = new XSSFWorkbook(templateSatisfactionKia);
            XSSFSheet listreportSatisfactionKia = reportSatisfactionKia.getSheetAt(0);

            // Количество отчётов
            int countReport = Math.toIntExact(vasFullReportNpsRepository.countReport());

            if (countReport >= 8 && countReport % 2 == 0) {
                for (int j = 1; j <= countReport - j - 2; j++) {
                    for (int i = 1; i < 25; i++) {
                        //Берем строку из отчета
                        XSSFRow mainRow = listreportSatisfactionKia.getRow(i);
                        //Копируем строку в отчёт
                        XSSFRow newRow = listreportSatisfactionKia.createRow(i + 48 * j); //this works
                        newRow.copyRowFrom(mainRow, new CellCopyPolicy());
                    }
                    for (int i = 25; i < 49; i++) {
                        //Берем строку из отчета
                        XSSFRow mainRow = listreportSatisfactionKia.getRow(i);
                        //Копируем строку в отчёт
                        XSSFRow newRow = listreportSatisfactionKia.createRow(i + 48 * j); //this works
                        newRow.copyRowFrom(mainRow, new CellCopyPolicy());
                    }
                }
            } else {
                for (int j = 1; j <= countReport - j; j++) {
                    for (int i = 1; i < 25; i++) {
                        //Берем строку из отчета
                        XSSFRow mainRow = listreportSatisfactionKia.getRow(i);
                        //Копируем строку в отчёт
                        XSSFRow newRow = listreportSatisfactionKia.createRow(i + 48 * j); //this works
                        newRow.copyRowFrom(mainRow, new CellCopyPolicy());
                    }
                    for (int i = 25; i < 49; i++) {
                        //Берем строку из отчета
                        XSSFRow mainRow = listreportSatisfactionKia.getRow(i);
                        //Копируем строку в отчёт
                        XSSFRow newRow = listreportSatisfactionKia.createRow(i + 48 * j); //this works
                        newRow.copyRowFrom(mainRow, new CellCopyPolicy());
                    }
                }
            }

            if (countReport % 2 != 0) {
                int lastRow = listreportSatisfactionKia.getLastRowNum();
                for (int i = lastRow; i < lastRow + 25; i++) {
                    //Берем строку из отчета
                    XSSFRow mainRow = listreportSatisfactionKia.getRow(i + 1);
                    //Копируем строку в отчёт
                    XSSFRow newRow = listreportSatisfactionKia.createRow((i - 23)); //this works
                    newRow.copyRowFrom(mainRow, new CellCopyPolicy());
                }
            }

            // Заполняем отчет исхлодя из количеста репортов
            for (int i = 0; i < countReport; i++) {
                int z = 24 * i; // Это коэффицент изменения количества опрашиваемых людей
                for (int j = z; j < 24 + z; j++) {
                    listreportSatisfactionKia.getRow(j + 1).getCell(1).setCellValue(formatDate.format(listFullReportKia.get(i).getOutgoingCallDate()));
                    listreportSatisfactionKia.getRow(j + 1).getCell(7).setCellValue(listFullReportKia.get(i).getVin());
                    listreportSatisfactionKia.getRow(j + 1).getCell(13).setCellValue(listFullReportKia.get(i).getIdClient());

                    String c2 = String.valueOf(listreportSatisfactionKia.getRow(j + 1).getCell(2));
                    String d2 = String.valueOf(listreportSatisfactionKia.getRow(j + 1).getCell(3));

                    listreportSatisfactionKia.getRow(j + 1)
                            .getCell(6)
                            .setCellValue(c2 +
                                    d2 +
                                    formatDate.format(listFullReportKia.get(i).getOutgoingCallDate()) +
                                    listFullReportKia.get(i).getIdClient());

                }
                listreportSatisfactionKia.getRow(z + 1).getCell(10).setCellValue(listFullReportKia.get(i).getBq010());
                listreportSatisfactionKia.getRow(z + 2).getCell(10).setCellValue(listFullReportKia.get(i).getBq020());
                listreportSatisfactionKia.getRow(z + 3).getCell(10).setCellValue(listFullReportKia.get(i).getBq030());
                listreportSatisfactionKia.getRow(z + 3).getCell(11).setCellValue(listFullReportKia.get(i).getBq030Comment());
                listreportSatisfactionKia.getRow(z + 4).getCell(10).setCellValue(listFullReportKia.get(i).getBq040());
                listreportSatisfactionKia.getRow(z + 5).getCell(10).setCellValue(listFullReportKia.get(i).getBq050());
                listreportSatisfactionKia.getRow(z + 5).getCell(11).setCellValue(listFullReportKia.get(i).getBq050Comment());
                listreportSatisfactionKia.getRow(z + 6).getCell(10).setCellValue(listFullReportKia.get(i).getBq060());
                listreportSatisfactionKia.getRow(z + 7).getCell(10).setCellValue(listFullReportKia.get(i).getBq070());
                listreportSatisfactionKia.getRow(z + 8).getCell(10).setCellValue(listFullReportKia.get(i).getBq080());
                listreportSatisfactionKia.getRow(z + 8).getCell(11).setCellValue(listFullReportKia.get(i).getBq080Comment());
                listreportSatisfactionKia.getRow(z + 9).getCell(10).setCellValue(listFullReportKia.get(i).getSq010());
                listreportSatisfactionKia.getRow(z + 10).getCell(10).setCellValue(listFullReportKia.get(i).getSq020());
                listreportSatisfactionKia.getRow(z + 11).getCell(10).setCellValue(listFullReportKia.get(i).getSq030());
                listreportSatisfactionKia.getRow(z + 12).getCell(10).setCellValue(listFullReportKia.get(i).getSq040());
                listreportSatisfactionKia.getRow(z + 13).getCell(10).setCellValue(listFullReportKia.get(i).getSq050());
                listreportSatisfactionKia.getRow(z + 14).getCell(10).setCellValue(listFullReportKia.get(i).getSq060());
                listreportSatisfactionKia.getRow(z + 15).getCell(10).setCellValue(listFullReportKia.get(i).getSq080());
                listreportSatisfactionKia.getRow(z + 16).getCell(10).setCellValue(listFullReportKia.get(i).getSq090());
                listreportSatisfactionKia.getRow(z + 17).getCell(10).setCellValue(listFullReportKia.get(i).getSq110());
                listreportSatisfactionKia.getRow(z + 18).getCell(10).setCellValue(listFullReportKia.get(i).getSq120());
                listreportSatisfactionKia.getRow(z + 19).getCell(10).setCellValue(listFullReportKia.get(i).getSq130());
                listreportSatisfactionKia.getRow(z + 20).getCell(10).setCellValue(listFullReportKia.get(i).getSq140());
                listreportSatisfactionKia.getRow(z + 21).getCell(10).setCellValue(listFullReportKia.get(i).getDq010());
                listreportSatisfactionKia.getRow(z + 22).getCell(10).setCellValue(listFullReportKia.get(i).getDq020());
                listreportSatisfactionKia.getRow(z + 23).getCell(10).setCellValue(listFullReportKia.get(i).getDq030());
                listreportSatisfactionKia.getRow(z + 24).getCell(10).setCellValue(listFullReportKia.get(i).getDq040());
            }

            SimpleDateFormat dateReport = new SimpleDateFormat("MMM yyyy");
            String date = dateReport.format(new Date());

            String newFile = date + ".xlsx";
            FileOutputStream fileOuts = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Kia reports\\Feedback\\" + newFile);

            reportSatisfactionKia.write(fileOuts);
            fileOuts.close();


            setTo = addressFullReportKiaForMonth.toString().replace("[","").replace("]","");
            setCc = replace;
            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
            helper.setFrom("info@vitautocity.by");
            helper.setTo(setTo);  //-получатель
            helper.setCc(setCopy); //-копия
            helper.setSubject("Отчёт по обратной связи с клиентами KIA");
            helper.setText("""
                Добрый день! \s
  
                Отчёт по обратной связи с клиентами находится во вложенном файле.""");

            FileSystemResource reportSatisfaction = new FileSystemResource(new File("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Kia reports\\Feedback\\" + newFile));
            //
            helper.addAttachment(newFile, reportSatisfaction);
            javaMailSender.send(messageVasNpsMail);

            LOGGER.log(Level.INFO, "Отчёт по удовлетворенности клиентов KIA сформирован и отправлен.");
            System.out.println("Отчёт по удовлетворенности клиентов KIA сформирован и отправлен.");
        } else {
            System.out.println("Отчёт по удовлетворенности клиентов KIA не сформирован.");
            LOGGER.log(Level.INFO, "Отчёт по удовлетворенности клиентов KIA.");
        }
    }

}
