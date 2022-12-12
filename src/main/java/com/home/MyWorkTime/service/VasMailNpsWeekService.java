package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
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


@Slf4j
@Service
public class VasMailNpsWeekService {

    private static final Logger LOGGER = Logger.getLogger(VasMailNpsWeekService.class.getName());


    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;
    private final HashMap<String, Double> npsReportWeek = new HashMap<>();
    private final HashMap<String, Double> npsReportMonth = new HashMap<>();

    public VasMailNpsWeekService(JavaMailSender javaMailSender,
                                 VasManagerNpsRepository vasManagerNpsRepository) {
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

    @Scheduled(cron = "1 00 17 * * *")
    private void startReportNPSWeek(){
        gradeNpsNameTechnicalWeek();
        gradeNpsNameBodyRepairWeek();
        gradeNpsDepartmentWeek();
        gradeNpsOrganisationWeek();
        gradeNpsOrganisationMonth();
        gradeNpsDepartmentMonth();
        gradeNpsNameBodyRepairMonth();
        gradeNpsNameTechnicalMonth();
        createCurrentReport();
    }

    private void gradeNpsNameTechnicalWeek(){

        String[] name = vasManagerNpsRepository.gradeNpsNameTech();

        for (String s : name) {
            double promoter = vasManagerNpsRepository.gradeNpsPromoterWeek(s);
            double critic = vasManagerNpsRepository.gradeNpsCriticWeek(s);
            double all = vasManagerNpsRepository.gradeNpsAllWeek(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportWeek.put(s, NPS);
        }
    }

    private void gradeNpsNameBodyRepairWeek(){

        String[] name = vasManagerNpsRepository.gradeNpsNameBodyRepair();

        for (String s : name) {
            double promoter = vasManagerNpsRepository.gradeNpsPromoterWeek(s);
            double critic = vasManagerNpsRepository.gradeNpsCriticWeek(s);
            double all = vasManagerNpsRepository.gradeNpsAllWeek(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportWeek.put(s, NPS);
        }
    }


    private void gradeNpsDepartmentWeek() {

        String[] department = new String[]{"OTOA", "МКЦ"};

        for (String s : department) {
            double promoter = vasManagerNpsRepository.gradeNpsDepartmentPromoterWeek(s);
            double critic = vasManagerNpsRepository.gradeNpsDepartmentCriticWeek(s);
            double all = vasManagerNpsRepository.gradeNpsDepartmentAllWeek(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportWeek.put(s, NPS);
        }
    }


    private void gradeNpsOrganisationWeek() {

        String[] organisation = new String[]{"ВитебскАвтоСити", "Джи-Моторс"};

        for (String s : organisation) {
            double promoter = vasManagerNpsRepository.gradeNpsOrganisationPromoterWeek(s);
            double critic = vasManagerNpsRepository.gradeNpsOrganisationCriticWeek(s);
            double all = vasManagerNpsRepository.gradeNpsOrganisationAllWeek(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportWeek.put(s, NPS);
        }
    }


    private void gradeNpsNameTechnicalMonth() {

        String[] name = vasManagerNpsRepository.gradeNpsNameTech();

        for (String s : name) {
            double promoter = vasManagerNpsRepository.gradeNpsPromoterMonth(s);
            double critic = vasManagerNpsRepository.gradeNpsCriticMonth(s);
            double all = vasManagerNpsRepository.gradeNpsAllMonth(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportMonth.put(s, NPS);
        }
    }


    private void gradeNpsNameBodyRepairMonth() {

        String[] name = vasManagerNpsRepository.gradeNpsNameBodyRepair();

        for (String s : name) {
            double promoter = vasManagerNpsRepository.gradeNpsPromoterMonth(s);
            double critic = vasManagerNpsRepository.gradeNpsCriticMonth(s);
            double all = vasManagerNpsRepository.gradeNpsAllMonth(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportMonth.put(s, NPS);
        }
    }


    private void gradeNpsDepartmentMonth() {

        String[] department = new String[]{"OTOA", "МКЦ"};

        for (String s : department) {
            double promoter = vasManagerNpsRepository.gradeNpsDepartmentPromoterMonth(s);
            double critic = vasManagerNpsRepository.gradeNpsDepartmentCriticMonth(s);
            double all = vasManagerNpsRepository.gradeNpsDepartmentAllMonth(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportMonth.put(s, NPS);
        }
    }


    private void gradeNpsOrganisationMonth() {

        String[] organisation = new String[]{"ВитебскАвтоСити", "Джи-Моторс"};

        for (String s : organisation) {
            double promoter = vasManagerNpsRepository.gradeNpsOrganisationPromoterMonth(s);
            double critic = vasManagerNpsRepository.gradeNpsOrganisationCriticMonth(s);
            double all = vasManagerNpsRepository.gradeNpsOrganisationAllMonth(s);
            double NPS = ((promoter - critic) / all) * 100;

            npsReportMonth.put(s, NPS);
        }
    }

    private void createCurrentReport() {

        List<VasManagerNpsModel> addressManagerNpsWeek = vasManagerNpsRepository.forMailNpsWeek();
        List<VasManagerNpsModel> addressManagerNpsWeekCopy = vasManagerNpsRepository.forMailNpsWeekCopy();
        ArrayList<String> addressNpsWeek = new ArrayList<>();
        ArrayList<String> addressNpsWeekCopy = new ArrayList<>();
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerNpsWeek){
            addressNpsWeek.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerNpsWeekCopy){
            addressNpsWeekCopy.add(vasManagerNpsModel.getManager_email());
        }

        String setTo;
        String setCc;

        String replaceTo = addressNpsWeek.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!
        String replaceCc = addressNpsWeekCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!

        try {
            FileInputStream npsReport = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\currentWeekNPS.xlsx");
            //  "C:\\Users\\User\\Desktop\\VAS-NPS\\src\\main\\resources\\reports\\currentWeekNPS.xlsx"
            XSSFWorkbook report = new XSSFWorkbook(npsReport);
            XSSFSheet listNps = report.getSheetAt(0);

            //Для заполнения информации по ключу ИТ!!! - неделя
            for (int i = 5; i < 15; i++) {
                XSSFCell cell = listNps.getRow(i).getCell(0);
                String findKey = String.valueOf(cell);
                if (Double.isNaN(npsReportWeek.get(findKey))) {
                    listNps.getRow(i).getCell(5).setCellValue("-");
                } else  {
                    listNps.getRow(i).getCell(5).setCellValue(String.format("%.2f", npsReportWeek.get(findKey)));
                }
            }
            //Для заполнения информации по ключу ИТ!!! - месяц
            for (int i = 5; i < 15; i++) {
                XSSFCell cell = listNps.getRow(i).getCell(8);
                String findKey = String.valueOf(cell);
                if (Double.isNaN(npsReportMonth.get(findKey))) {
                    listNps.getRow(i).getCell(13).setCellValue("-");
                } else  {
                    listNps.getRow(i).getCell(13).setCellValue(String.format("%.2f", npsReportMonth.get(findKey)));
                }
            }
            //Для заполнения информации по ключу Департамент!!! - неделя
            String[] department = new String[]{"Отдел технического обслуживания автомобилей","Малярно-кузовной цех"};
            for (int i = 18; i < 20; i++) {
                XSSFCell cell = listNps.getRow(i).getCell(0);
                String findKey = String.valueOf(cell);
                if (findKey.equals(department[0])) {
                    if (Double.isNaN(npsReportWeek.get("OTOA"))) {
                        listNps.getRow(i).getCell(5).setCellValue("-");
                    } else {
                        listNps.getRow(i).getCell(5).setCellValue(String.format("%.2f", npsReportWeek.get("OTOA")));
                    }
                }
                if (findKey.equals(department[1])) {
                    if (Double.isNaN(npsReportWeek.get("МКЦ"))) {
                        listNps.getRow(i).getCell(5).setCellValue("-");
                    } else {
                        listNps.getRow(i).getCell(5).setCellValue(String.format("%.2f", npsReportWeek.get("МКЦ")));
                    }
                }
            }
            //Для заполнения информации по ключу Департамент!!! - месяц
            for (int i = 18; i < 20; i++) {
                XSSFCell cell = listNps.getRow(i).getCell(8);
                String findKey = String.valueOf(cell);
                if (findKey.equals(department[0])) {
                    if (Double.isNaN(npsReportMonth.get("OTOA"))) {
                        listNps.getRow(i).getCell(13).setCellValue("-");
                    } else {
                        listNps.getRow(i).getCell(13).setCellValue(String.format("%.2f", npsReportMonth.get("OTOA")));
                    }
                }
                if (findKey.equals(department[1])) {
                    if (Double.isNaN(npsReportMonth.get("МКЦ"))) {
                        listNps.getRow(i).getCell(13).setCellValue("-");
                    } else {
                        listNps.getRow(i).getCell(13).setCellValue(String.format("%.2f", npsReportMonth.get("МКЦ")));
                    }
                }
            }
            //Для заполнения информации по ключу Организация!!! - неделя
            String[] organisation = new String[]{"ООО \"ВитебскАвтоСити\"","Джи-моторс"};
            XSSFCell cell = listNps.getRow(23).getCell(0);
            String findKey = String.valueOf(cell);
            if (findKey.equals(organisation[0])) {
                if (Double.isNaN(npsReportWeek.get("ВитебскАвтоСити"))) {
                    listNps.getRow(23).getCell(5).setCellValue("-");
                } else {
                    listNps.getRow(23).getCell(5).setCellValue(String.format("%.2f", npsReportWeek.get("ВитебскАвтоСити")));
                }
            }
            if (findKey.equals(department[1])) {
                if (Double.isNaN(npsReportWeek.get("Джи-моторс"))) {
                    listNps.getRow(23).getCell(5).setCellValue("-");
                } else {
                    listNps.getRow(23).getCell(5).setCellValue(String.format("%.2f", npsReportWeek.get("Джи-моторс")));
                }
            }
            //Для заполнения информации по ключу Организация!!! - месяц
            cell = listNps.getRow(23).getCell(8);
            findKey = String.valueOf(cell);
            if (findKey.equals(organisation[0])) {
                if (Double.isNaN(npsReportMonth.get("ВитебскАвтоСити"))) {
                    listNps.getRow(23).getCell(13).setCellValue("-");
                } else {
                    listNps.getRow(23).getCell(13).setCellValue(String.format("%.2f", npsReportMonth.get("ВитебскАвтоСити")));
                }
            }
            if (findKey.equals(department[1])) {
                if (Double.isNaN(npsReportMonth.get("Джи-моторс"))) {
                    listNps.getRow(23).getCell(13).setCellValue("-");
                } else {
                    listNps.getRow(23).getCell(13).setCellValue(String.format("%.2f", npsReportMonth.get("Джи-моторс")));
                }
            }
            //Общее кол-во з/н за месяц
            listNps.getRow(25).getCell(5).setCellValue(vasManagerNpsRepository.countCurrentMonthOrder());
            //Общее кол-во закрытых з/н за месяц
            listNps.getRow(26).getCell(5).setCellValue(vasManagerNpsRepository.countCurrentMonthCloseOrder());
            //Процент отзвона KIA
            if (vasManagerNpsRepository.countCurrentMonthKiaOrder() == 0) {
                String percentKia = "-";
                listNps.getRow(28).getCell(7).setCellValue(percentKia);
            } else {
                String percentKia = String.format("%.2f", (vasManagerNpsRepository.countCurrentMonthCloseKiaOrder() / vasManagerNpsRepository.countCurrentMonthKiaOrder()) * 100);
                listNps.getRow(28).getCell(7).setCellValue(percentKia);
            }
            if (vasManagerNpsRepository.countCurrentMonthSkodaOrder() == 0) {
                String percentSkoda = "-";
                listNps.getRow(29).getCell(7).setCellValue(percentSkoda);
            } else {
                String percentSkoda = String.format("%.2f", (vasManagerNpsRepository.countCurrentMonthCloseSkodaOrder() / vasManagerNpsRepository.countCurrentMonthSkodaOrder()) * 100);
                listNps.getRow(29).getCell(7).setCellValue(percentSkoda);
            }
            if (vasManagerNpsRepository.countCurrentMonthCloseMultibrandOrder() == 0) {
                String percentMultibrand = "-";
                listNps.getRow(30).getCell(7).setCellValue(percentMultibrand);
            } else {
                String percentMultibrand = String.format("%.2f", (vasManagerNpsRepository.countCurrentMonthCloseMultibrandOrder() / vasManagerNpsRepository.countCurrentMonthMultibrandOrder()) * 100);
                listNps.getRow(30).getCell(7).setCellValue(percentMultibrand);
            }

            //Запись в файл
            SimpleDateFormat dateReport = new SimpleDateFormat("dd.MM.yyyy");
            String date = dateReport.format(new Date());
            //Дата отчёта
            listNps.getRow(30).getCell(12).setCellValue(date);

            //Второй лист
            XSSFSheet listData = report.getSheetAt(1);
            String[][] dataArray = vasManagerNpsRepository.currentMonthAllOrder();
            for (int i = 0; i < dataArray.length; i++){
                for (int j = 0; j < 4; j++){
                    listData.getRow(i+2).getCell(j+1).setCellValue(dataArray[i][j]);
                }
            }
            String file = ""+ date + "- currentWeekNPS.xlsx";
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Week NPS reports\\" + file);
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
            helper.setSubject("Отчёт по показателю NPS, организация ООО \"ВитебскАвтоСити\" в разрезе неделя/текущий месяц");
            helper.setText("""
                Добрый день! \s
  
                К письму прикреплен отчёт по показателю NPS.""");

            FileSystemResource currentWeekNpsReport = new FileSystemResource("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Week NPS reports\\" + file);
            helper.addAttachment(file, currentWeekNpsReport);
            javaMailSender.send(messageVasNpsMail);

            System.out.println("Отчёт NPS в разрезе неделя/месяц сформирован и отправлен.");
            LOGGER.log(Level.INFO, "Отчёт NPS в разрезе неделя/месяц сформирован и отправлен.");

        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            System.out.println("Отчёт NPS в разрезе неделя/месяц не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Отчёт NPS в разрезе неделя/месяц не сформирован, данных нет." + " Описание ошибки: ", e);
        }
    }
}
