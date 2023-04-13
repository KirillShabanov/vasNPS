package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasMailNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasMailNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;



@Slf4j
@Service
public class VasMailNpsSkodaService {

    private static final Logger LOGGER = Logger.getLogger(VasMailNpsSkodaService.class.getName());

    private final VasMailNpsRepository vasMailNpsRepository;
    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;

    public VasMailNpsSkodaService(VasMailNpsRepository vasMailNpsRepository,
                                  JavaMailSender javaMailSender,
                                  VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasMailNpsRepository = vasMailNpsRepository;
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }


    @SneakyThrows
    @Scheduled(cron = "1 01 19 * * 1-5")
    @Scheduled(cron = "1 01 16 * * 6-7")
    private void SendVasNpsMail() {
        List<VasMailNpsModel> listSkoda = vasMailNpsRepository.npsListSkoda();
        List<VasManagerNpsModel> addressManagerSkoda = vasManagerNpsRepository.forMailSkoda();
        List<VasManagerNpsModel> addressManagerCopy = vasManagerNpsRepository.forMailCopy();

        ArrayList<String> addressListSkoda = new ArrayList<>();
        ArrayList<String> addressListCopy = new ArrayList<>();


        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy"); //Для формата вывода даты в excel

        for (VasManagerNpsModel vasManagerNpsModel : addressManagerSkoda) {
            addressListSkoda.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerCopy) {
            addressListCopy.add(vasManagerNpsModel.getManager_email());
        }
        //Ниже код для адресата отправки
        String setTo;
        String setCc;

        String replace = addressListCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!

        if (!(listSkoda.isEmpty())) {

            FileInputStream templateSkoda = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\temlpateSkoda.xlsx");
            //"C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\reports\\temlpateSkoda.xlsx"
            XSSFWorkbook reportSkoda = new XSSFWorkbook(templateSkoda);
            XSSFSheet listReportSkoda = reportSkoda.getSheetAt(0);

            for (int i = 0; i < listSkoda.size(); i++) {
                XSSFRow rowCall = listReportSkoda.getRow(i + 1);
                rowCall.createCell(0).setCellValue("");
                rowCall.createCell(1).setCellValue(listSkoda.get(i).getClient_surname());
                rowCall.createCell(2).setCellValue(listSkoda.get(i).getClient_name());
                rowCall.createCell(3).setCellValue(listSkoda.get(i).getPhone_1());
                if (listSkoda.get(i).getPhone_2() != null) {
                    rowCall.createCell(4).setCellValue(listSkoda.get(i).getPhone_2());
                } else {
                    rowCall.createCell(4).setCellValue("");
                }
                rowCall.createCell(5).setCellValue("VitebskAutoCity");
                rowCall.createCell(6).setCellValue("");
                rowCall.createCell(7).setCellValue(listSkoda.get(i).getVehicle_identification_number());
                rowCall.createCell(8).setCellValue(listSkoda.get(i).getReg_num());
                rowCall.createCell(9).setCellValue(listSkoda.get(i).getBrand());
                rowCall.createCell(10).setCellValue(listSkoda.get(i).getModel());
                rowCall.createCell(11).setCellValue(listSkoda.get(i).getYear_release());
                rowCall.createCell(12).setCellValue(formatDate.format(listSkoda.get(i).getDate_order()));
                rowCall.createCell(13).setCellValue("N");
                rowCall.createCell(14).setCellValue(listSkoda.get(i).getNum_order());
                rowCall.createCell(15).setCellValue("ru");
                rowCall.createCell(16).setCellValue("N");
                rowCall.createCell(17).setCellValue("000059");
                rowCall.createCell(18).setCellValue("296");
                rowCall.createCell(19).setCellValue("41007");
            }

            SimpleDateFormat dateReport = new SimpleDateFormat("dd.MM.yyyy");
            String date = dateReport.format(new Date());

            String newFile = "" + date + "- Skoda.xlsx";
            FileOutputStream fileOuts = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\NPS Skoda\\" + newFile);
            // "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\NPS Skoda\\"
            reportSkoda.write(fileOuts);
            fileOuts.close();


            setTo = addressListSkoda.toString().replace("[", "").replace("]", "");
            setCc = replace;
            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
            helper.setFrom("info@vitautocity.by");
            helper.setTo(setTo);  //-получатель
            helper.setCc(setCopy); //-копия
            helper.setSubject("NPS SKODA");
            helper.setText("""
                    Добрый день! \s
                      
                    Прошу Вас провести обратную связь с клиентами, информация по клиентам находится во вложенном файле. \s
                                    
                                    
                    Исходящий звонок осуществляется с ПН по ПТ, временная рамка с 10.00 до 17.00. \s
                    Использовать регламент SKODA. \s
                                    
                    P.S. При наличие негативного отзыва, прошу заполнять поле "Коментарий". \s
                    P.S.S. Убедительная просьба! Вносить ту оценку, которую озвучивает Клиент, ни в коем случае не пытаться изменить его мнение!!!""");

            FileSystemResource reportsSkoda = new FileSystemResource("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\NPS Skoda\\" + newFile);
            //"C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\NPS Skoda\\"
            helper.addAttachment(newFile, reportsSkoda);
            javaMailSender.send(messageVasNpsMail);

            LOGGER.log(Level.INFO, "Список SKODA сформирован и отправлен.");
            System.out.println("Список SKODA сформирован и отправлен.");
        } else {
            System.out.println("Список SKODA не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Список SKODA не сформирован, данных нет.");
        }
    }
}
