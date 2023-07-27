/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;


@Service
public class VasMailNpsMonthService {

    private static final Logger LOGGER = Logger.getLogger(VasMailNpsMonthService.class.getName());

    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;
    private final HashMap<String, Double> npsReportingMonth = new HashMap<>();

    public VasMailNpsMonthService(JavaMailSender javaMailSender,
                                  VasManagerNpsRepository vasManagerNpsRepository) {
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }


    @Scheduled(cron = "1 00 08 4 * *")
    private void reportingNpsMonth(){
        gradeNpsNameTechnicalMonthReporting();
        gradeNpsNameBodyRepairMonthReporting();
        gradeNpsDepartmentMonthReporting();
        gradeNpsOrganisationMonthReporting();
        npsForReportingMonth();
    }

    private void gradeNpsNameTechnicalMonthReporting() {

        String[] name = vasManagerNpsRepository.gradeNpsNameTech();

        for (String s : name) {
            double promoter = vasManagerNpsRepository.gradeNpsPromoterMonthReporting(s);
            double critic = vasManagerNpsRepository.gradeNpsCriticMonthReporting(s);
            double all = vasManagerNpsRepository.gradeNpsAllMonthReporting(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportingMonth.put(s, NPS);
        }
    }


    private void gradeNpsNameBodyRepairMonthReporting() {

        String[] name = vasManagerNpsRepository.gradeNpsNameBodyRepair();

        for (String s : name) {
            double promoter = vasManagerNpsRepository.gradeNpsPromoterMonthReporting(s);
            double critic = vasManagerNpsRepository.gradeNpsCriticMonthReporting(s);
            double all = vasManagerNpsRepository.gradeNpsAllMonthReporting(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportingMonth.put(s, NPS);
        }
    }


    private void gradeNpsDepartmentMonthReporting() {

        String[] department = new String[]{"OTOA", "МКЦ"};

        for (String s : department) {
            double promoter = vasManagerNpsRepository.gradeNpsDepartmentPromoterMonthReporting(s);
            double critic = vasManagerNpsRepository.gradeNpsDepartmentCriticMonthReporting(s);
            double all = vasManagerNpsRepository.gradeNpsDepartmentAllMonthReporting(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportingMonth.put(s, NPS);
        }
    }


    private void gradeNpsOrganisationMonthReporting() {

        String[] organisation = new String[]{"ВитебскАвтоСити", "Джи-Моторс"};

        for (String s : organisation) {
            double promoter = vasManagerNpsRepository.gradeNpsOrganisationPromoterMonthReporting(s);
            double critic = vasManagerNpsRepository.gradeNpsOrganisationCriticMonthReporting(s);
            double all = vasManagerNpsRepository.gradeNpsOrganisationAllMonthReporting(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportingMonth.put(s, NPS);
        }
    }

    private void npsForReportingMonth(){

        Calendar dateReport = new GregorianCalendar();
        dateReport.add(MONTH, 0);
        String dateReportMonth = dateReport.get(MONTH) + "." + dateReport.get(YEAR);


        List<VasManagerNpsModel> addressManagerNpsMonth = vasManagerNpsRepository.forMailNpsMonth();
        List<VasManagerNpsModel> addressManagerNpsMonthCopy = vasManagerNpsRepository.forMailNpsMonthCopy();
        ArrayList<String> addressNpsMonth = new ArrayList<>();
        ArrayList<String> addressNpsMonthCopy = new ArrayList<>();
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerNpsMonth){
            addressNpsMonth.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerNpsMonthCopy){
            addressNpsMonthCopy.add(vasManagerNpsModel.getManager_email());
        }

        String setTo;
        String setCc;

        String replaceTo = addressNpsMonth.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!
        String replaceCc = addressNpsMonthCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!

        try {
            FileInputStream npsReport = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\currentMonthNPS.xlsx");
            try (// "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\reports\\currentMonthNPS.xlsx"
            XSSFWorkbook report = new XSSFWorkbook(npsReport)) {
                XSSFSheet listNps = report.getSheetAt(0);

                //Для заполнения информации по ключу ИТ!!! - месяц
                for (int i = 5; i < 15; i++) {
                    XSSFCell cell = listNps.getRow(i).getCell(4);
                    String findKey = String.valueOf(cell);
                    if (Double.isNaN(npsReportingMonth.get(findKey))) {
                        listNps.getRow(i).getCell(9).setCellValue("-");
                    } else  {
                        listNps.getRow(i).getCell(9).setCellValue(String.format("%.2f", npsReportingMonth.get(findKey)));
                        listNps.getRow(i).getCell(10).setCellValue(vasManagerNpsRepository.gradeNpsAllMonthReporting(findKey));
                    }
                }
                //Для заполнения информации по ключу Департамент!!! - месяц
                String[] department = new String[]{"Отдел технического обслуживания автомобилей","Малярно-кузовной цех"};
                for (int i = 18; i < 20; i++) {
                    XSSFCell cell = listNps.getRow(i).getCell(4);
                    String findKey = String.valueOf(cell);
                    if (findKey.equals(department[0])) {
                        if (Double.isNaN(npsReportingMonth.get("OTOA"))) {
                            listNps.getRow(i).getCell(9).setCellValue("-");
                        } else {
                            listNps.getRow(i).getCell(9).setCellValue(String.format("%.2f", npsReportingMonth.get("OTOA")));
                        }
                    }
                    if (findKey.equals(department[1])) {
                        if (Double.isNaN(npsReportingMonth.get("МКЦ"))) {
                            listNps.getRow(i).getCell(9).setCellValue("-");
                        } else {
                            listNps.getRow(i).getCell(9).setCellValue(String.format("%.2f", npsReportingMonth.get("МКЦ")));
                        }
                    }
                }
                //Для заполнения информации по ключу Организация!!! - месяц
                String[] organisation = new String[]{"ООО \"ВитебскАвтоСити\"","Джи-моторс"};
                XSSFCell cell = listNps.getRow(23).getCell(4);
                String findKey = String.valueOf(cell);
                if (findKey.equals(organisation[0])) {
                    if (Double.isNaN(npsReportingMonth.get("ВитебскАвтоСити"))) {
                        listNps.getRow(23).getCell(9).setCellValue("-");
                    } else {
                        listNps.getRow(23).getCell(9).setCellValue(String.format("%.2f", npsReportingMonth.get("ВитебскАвтоСити")));
                    }
                }
                if (findKey.equals(department[1])) {
                    if (Double.isNaN(npsReportingMonth.get("Джи-моторс"))) {
                        listNps.getRow(23).getCell(9).setCellValue("-");
                    } else {
                        listNps.getRow(23).getCell(9).setCellValue(String.format("%.2f", npsReportingMonth.get("Джи-моторс")));
                    }
                }
                //Общее кол-во з/н за месяц
                listNps.getRow(25).getCell(5).setCellValue(vasManagerNpsRepository.countCurrentMonthOrderReporting());
                //Общее кол-во закрытых з/н за месяц
                listNps.getRow(26).getCell(5).setCellValue(vasManagerNpsRepository.countCurrentMonthCloseOrderReporting());
                //Процент отзвона KIA
                if (vasManagerNpsRepository.countCurrentMonthKiaOrderReporting() == 0) {
                    String percentKia = "-";
                    listNps.getRow(28).getCell(7).setCellValue(percentKia);
                } else {
                    String percentKia = String.format("%.2f", (vasManagerNpsRepository.countCurrentMonthCloseKiaOrderReporting() / vasManagerNpsRepository.countCurrentMonthKiaOrderReporting()) * 100);
                    listNps.getRow(28).getCell(7).setCellValue(percentKia);
                }
                if (vasManagerNpsRepository.countCurrentMonthSkodaOrderReporting() == 0) {
                    String percentSkoda = "-";
                    listNps.getRow(29).getCell(7).setCellValue(percentSkoda);
                } else {
                    String percentSkoda = String.format("%.2f", (vasManagerNpsRepository.countCurrentMonthCloseSkodaOrderReporting() / vasManagerNpsRepository.countCurrentMonthSkodaOrderReporting()) * 100);
                    listNps.getRow(29).getCell(7).setCellValue(percentSkoda);
                }
                if (vasManagerNpsRepository.countCurrentMonthCloseMultibrandOrderReporting() == 0) {
                    String percentMultibrand = "-";
                    listNps.getRow(30).getCell(7).setCellValue(percentMultibrand);
                } else {
                    String percentMultibrand = String.format("%.2f", (vasManagerNpsRepository.countCurrentMonthCloseMultibrandOrderReporting() / vasManagerNpsRepository.countCurrentMonthMultibrandOrderReporting()) * 100);
                    listNps.getRow(30).getCell(7).setCellValue(percentMultibrand);
                }

                //Запись в файл
                SimpleDateFormat dateReportMonthToString = new SimpleDateFormat("dd.MM.yyyy");
                String date = dateReportMonthToString.format(new Date());
                //Дата отчёта
                listNps.getRow(30).getCell(12).setCellValue(date);

                //Второй лист
                XSSFSheet listData = report.getSheetAt(1);
                String[][] dataArray = vasManagerNpsRepository.currentMonthAllOrderReporting();
                for (int i = 0; i < dataArray.length; i++){
                    for (int j = 0; j < 5; j++){
                        listData.getRow(i+2).getCell(j+1).setCellValue(dataArray[i][j]);
                    }
                }

                String file = "" + dateReportMonth + "- Ежемесячный отчёт NPS.xlsx";
                FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Month NPS reports\\" + file);
                // "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\currentMonthNPS\\"
                report.write(fileOut);
                fileOut.close();

                setTo = replaceTo;
                setCc = replaceCc;
                InternetAddress[] setToMail = InternetAddress.parse(setTo);
                InternetAddress[] setCopy = InternetAddress.parse(setCc);


                MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
                helper.setFrom("info@vitautocity.by");
                if (setCopy.length == 0) {
                    helper.setTo(setToMail);  //-получатель
                } else {
                    helper.setTo(setToMail);  //-получатель
                    helper.setCc(setCopy); //-копия
                }
                helper.setSubject("Отчёт по показателю NPS, организация ООО \"ВитебскАвтоСити\" за отчётный месяц");
                helper.setText("""
                    Добрый день! \s
  
                    К письму прикреплен отчёт по показателю NPS.""");

                FileSystemResource currentWeekNpsReport = new FileSystemResource("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Month NPS reports\\" + file);
                helper.addAttachment(file, currentWeekNpsReport);
                javaMailSender.send(messageVasNpsMail);
            } catch (MailException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //System.out.println("Отчёт NPS за отчётный месяц сформирован и отправлен.");
            LOGGER.log(Level.INFO, "Отчёт NPS за отчётный месяц сформирован и отправлен.");

        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            //System.out.println("Отчёт NPS за отчётный месяц не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Отчёт NPS за отчётный месяц не сформирован, данных нет." + " Описание ошибки: ", e);
        }
    }

}
