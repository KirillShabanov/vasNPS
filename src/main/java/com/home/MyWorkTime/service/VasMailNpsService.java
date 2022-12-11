package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasFullReportNpsModel;
import com.home.MyWorkTime.entity.VasMailNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasFullReportNpsRepository;
import com.home.MyWorkTime.repository.VasMailNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;


@Slf4j
@Service
public class VasMailNpsService {

    private static final Logger LOGGER = Logger.getLogger(VasMailNpsService.class.getName());

    private final VasMailNpsRepository vasMailNpsRepository;
    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;
    private final VasFullReportNpsRepository vasFullReportNpsRepository;
    private final HashMap<String, Double> npsReportWeek = new HashMap<>();
    private final HashMap<String, Double> npsReportMonth = new HashMap<>();
    private final HashMap<String, Double> npsReportingMonth = new HashMap<>();

    public VasMailNpsService(VasMailNpsRepository vasMailNpsRepository,
                             JavaMailSender javaMailSender,
                             VasManagerNpsRepository vasManagerNpsRepository,
                             VasFullReportNpsRepository vasFullReportNpsRepository) {
        this.vasMailNpsRepository = vasMailNpsRepository;
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
        this.vasFullReportNpsRepository = vasFullReportNpsRepository;
    }


    @SneakyThrows
    @Scheduled(cron = "1 00 16 * * *")
    private void SendVasNpsMail() {
        List<VasMailNpsModel> listKia = vasMailNpsRepository.npsListKia();
        List<VasMailNpsModel> listSkoda = vasMailNpsRepository.npsListSkoda();
        List<VasMailNpsModel> listMultibrand = vasMailNpsRepository.npsListMultibrand();

        List<VasManagerNpsModel> addressManagerKia = vasManagerNpsRepository.forMailKia();
        List<VasManagerNpsModel> addressManagerSkoda = vasManagerNpsRepository.forMailSkoda();
        List<VasManagerNpsModel> addressManagerMultibrand = vasManagerNpsRepository.forMailMultibrand();
        List<VasManagerNpsModel> addressManagerCopy = vasManagerNpsRepository.forMailCopy();

        ArrayList<String> addressListKia = new ArrayList<>();
        ArrayList<String> addressListSkoda = new ArrayList<>();
        ArrayList<String> addressListMultibrand = new ArrayList<>();
        ArrayList<String> addressListCopy = new ArrayList<>();


        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy"); //Для формата вывода даты в excel

        for (VasManagerNpsModel vasManagerNpsModel : addressManagerKia){
            addressListKia.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerSkoda){
            addressListSkoda.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerMultibrand){
            addressListMultibrand.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerCopy){
            addressListCopy.add(vasManagerNpsModel.getManager_email());
        }
        //Ниже код для адресата отправки
        String setTo;
        String setCc;


        String replace = addressListCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!

        if (!(listKia.isEmpty())){

            setTo = addressListKia.toString().replace("[","").replace("]","");
            setCc = replace;
            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            List<VasMailNpsModel> numOrderList = vasMailNpsRepository.npsListKia();
            ArrayList<Long> list = new ArrayList<>();
            for (VasMailNpsModel vasMailNpsModel : numOrderList) {
                list.add(Long.valueOf(vasMailNpsModel.getNum_order()));
            }
            String numOrder = list.toString().replace("[","").replace("]","");

            MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
            helper.setFrom("info@vitautocity.by");
            helper.setTo(setTo);  //-получатель
            helper.setCc(setCopy); //-копия
            helper.setSubject("NPS KIA");
            helper.setText("""
                 Добрый день! \s
                      
                    Прошу Вас провести обратную связь с клиентами. \s
                    Номера заказ-нарядов приведены ниже: \s
                    \s"""
                    + numOrder +
                    """
                                
                    
                    Исходящий звонок осуществляется с ПН по ПТ, временная рамка с 10.00 до 17.00. \s
                    
                    При общении с клиентами придерживайтесь данного регламента разговора: \s
                    Добрый день, ..........! \s
                    Меня зовут ........ . Я представляю Автоцентр ВитебсАвтоСити. \s
                    В настоящее время мы проводим опрос, чтобы оценить степень удовлетворенности клиентов. \s
                    Это займет не более 2-х минут. \s
                    Мы будем очень признательны, если Вы примете участие в нашем опросе. \s
                    
                    ПРИ СОГЛАСИИ: \s
                    1. Как бы Вы оценили уровень сервисного обслуживания в целом? \s
                    2. Какова вероятность, что Вы порекомендуете данный автоцентр другим? (Данную оценку вносим в отчётный файл). \s
                    3. Будите ли Вы посещать наш автоцентр снова? \s
                    
                    ПОЗВОНИТЬ ПОЗЖЕ: \s
                    Благодарим Вас за участие в опросе. Наши сотрудники позвонят Вам позже. Когда Вам будет удобно, чтобы мы перезвонили? (Озвученное время записать в поле "Комментарий"). \s
                    
                    ОТКАЗ: \s
                    Я прошу прощения, если я Вас побеспокоил. Хорошего Вам дня. (Оценка ставим НОЛЬ, в поле "Комментарий" записываем - ОТКАЗ ОТ ОПРОСА). \s
                    
                    Нет ответа: \s
                    Не звонить более 3-х раз. (Оценка ставим НОЛЬ, в поле "Комментарий" записываем - АБОНЕНТ НЕ ОТВЕЧАЕТ). \s
                    
                    P.S. При наличие негативного отзыва, прошу заполнять поле "Коментарий". \s
                    P.S.S. Убедительная просьба! Вносить ту оценку, которую озвучивает Клиент, ни в коем случае не пытаться изменить его мнение!!!""");

            javaMailSender.send(messageVasNpsMail);

            LOGGER.log(Level.INFO, "Список KIA сформирован и отправлен.");
            System.out.println("Список KIA сформирован и отправлен.");
        } else {
            System.out.println("Список KIA не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Список KIA не сформирован, данных нет.");
        }

        if (!(listSkoda.isEmpty())){

            FileInputStream templateSkoda = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\templateSkoda.xlsx");
            // "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\reports\\temlpateSkoda.xlsx"
            XSSFWorkbook reportSkoda = new XSSFWorkbook(templateSkoda);
            XSSFSheet listReportSkoda = reportSkoda.getSheetAt(0);

            for (int i = 0; i < listSkoda.size(); i++) {
                XSSFRow rowCall = listReportSkoda.getRow(i+1);
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

            String newFile = ""+ date + "- Skoda.xlsx";
            FileOutputStream fileOuts = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\NPS Skoda\\" + newFile);
            // "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\NPS Skoda\\"
            reportSkoda.write(fileOuts);
            fileOuts.close();



            setTo = addressListSkoda.toString().replace("[","").replace("]","");
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

            FileSystemResource reportsSkoda = new FileSystemResource(new File( "C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\NPS Skoda\\" + newFile));
            // "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\NPS Skoda\\"
            helper.addAttachment(newFile, reportsSkoda);
            javaMailSender.send(messageVasNpsMail);

            LOGGER.log(Level.INFO, "Список SKODA сформирован и отправлен.");
            System.out.println("Список SKODA сформирован и отправлен.");
        } else {
            System.out.println("Список SKODA не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Список SKODA не сформирован, данных нет.");
        }

        //Письмо multibrand
        if (!(listMultibrand.isEmpty())) {
            setTo = addressListMultibrand.toString().replace("[", "").replace("]", "");
            setCc = replace;

            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            List<VasMailNpsModel> numOrderList = vasMailNpsRepository.npsListMultibrand();
            ArrayList<Long> list = new ArrayList<>();
            for (VasMailNpsModel vasMailNpsModel : numOrderList) {
                list.add(Long.valueOf(vasMailNpsModel.getNum_order()));
            }
            String numOrder = list.toString().replace("[", "").replace("]", "");

            MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
            helper.setFrom("info@vitautocity.by");
            helper.setTo(setTo);  //-получатель
            helper.setCc(setCopy); //-копия
            helper.setSubject("NPS Multibrand");
            helper.setText("""
                    Добрый день! \s
                      
                    Прошу Вас провести обратную связь с клиентами. \s
                    Номера заказ-нарядов приведены ниже: \s
                    \s"""
                    + numOrder +
                    """
                                        
                                            
                            Исходящий звонок осуществляется с ПН по ПТ, временная рамка с 10.00 до 17.00. \s
                                            
                            При общении с клиентами придерживайтесь данного регламента разговора: \s
                            Добрый день, ..........! \s
                            Меня зовут ........ . Я представляю Автоцентр ВитебсАвтоСити. \s
                            В настоящее время мы проводим опрос, чтобы оценить степень удовлетворенности клиентов. \s
                            Это займет не более 2-х минут. \s
                            Мы будем очень признательны, если Вы примете участие в нашем опросе. \s
                                            
                            ПРИ СОГЛАСИИ: \s
                            1. Как бы Вы оценили уровень сервисного обслуживания в целом? \s
                            2. Какова вероятность, что Вы порекомендуете данный автоцентр другим? (Данную оценку вносим в отчётный файл). \s
                            3. Будите ли Вы посещать наш автоцентр снова? \s
                                            
                            ПОЗВОНИТЬ ПОЗЖЕ: \s
                            Благодарим Вас за участие в опросе. Наши сотрудники позвонят Вам позже. Когда Вам будет удобно, чтобы мы перезвонили? (Озвученное время записать в поле "Комментарий"). \s
                                            
                            ОТКАЗ: \s
                            Я прошу прощения, если я Вас побеспокоил. Хорошего Вам дня. (Оценка ставим НОЛЬ, в поле "Комментарий" записываем - ОТКАЗ ОТ ОПРОСА). \s
                                            
                            Нет ответа: \s
                            Не звонить более 3-х раз. (Оценка ставим НОЛЬ, в поле "Комментарий" записываем - АБОНЕНТ НЕ ОТВЕЧАЕТ). \s
                                            
                            P.S. При наличие негативного отзыва, прошу заполнять поле "Коментарий". \s
                            P.S.S. Убедительная просьба! Вносить ту оценку, которую озвучивает Клиент, ни в коем случае не пытаться изменить его мнение!!!""");
            javaMailSender.send(messageVasNpsMail);

            System.out.println("Список Multibrand сформирован и отправлен.");
            LOGGER.log(Level.INFO, "Список Multibrand сформирован и отправлен.");
        } else {
            System.out.println("Список Multibrand не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Список Multibrand не сформирован, данных нет.");
        }
    }

    @Scheduled(cron = "1 00 16 * * 0")
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
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Week reports\\" + file);
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

            FileSystemResource currentWeekNpsReport = new FileSystemResource(new File(file));
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

    @Scheduled(cron = "1 00 09 4 * *")
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
            // "C:\\Users\\User\\Desktop\\VAS-NPS\\src\\main\\resources\\reports\\currentMonthNPS.xlsx"
            XSSFWorkbook report = new XSSFWorkbook(npsReport);
            XSSFSheet listNps = report.getSheetAt(0);

            //Для заполнения информации по ключу ИТ!!! - месяц
            for (int i = 5; i < 15; i++) {
                XSSFCell cell = listNps.getRow(i).getCell(4);
                String findKey = String.valueOf(cell);
                if (Double.isNaN(npsReportingMonth.get(findKey))) {
                    listNps.getRow(i).getCell(9).setCellValue("-");
                } else  {
                    listNps.getRow(i).getCell(9).setCellValue(String.format("%.2f", npsReportingMonth.get(findKey)));
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
                for (int j = 0; j < 4; j++){
                    listData.getRow(i+2).getCell(j+1).setCellValue(dataArray[i][j]);
                }
            }

            String file = "" + dateReportMonth + "- currentMonthNPS.xlsx";
            FileOutputStream fileOut = new FileOutputStream(file);
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

            FileSystemResource currentWeekNpsReport = new FileSystemResource(new File("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Month reports\\" + file));
            helper.addAttachment(file, currentWeekNpsReport);
            javaMailSender.send(messageVasNpsMail);

            System.out.println("Отчёт NPS за отчётный месяц сформирован и отправлен.");
            LOGGER.log(Level.INFO, "Отчёт NPS за отчётный месяц сформирован и отправлен.");

        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            System.out.println("Отчёт NPS за отчётный месяц не сформирован, данных нет.");
            LOGGER.log(Level.INFO, "Отчёт NPS за отчётный месяц не сформирован, данных нет." + " Описание ошибки: ", e);
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 09 25 * *")
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

            FileInputStream templateSatisfactionKia = null;
            try {
                templateSatisfactionKia = new FileInputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\ReportTemplates\\customerSatisfactionReport.xlsx");
                // "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\reports\\customerSatisfactionReport.xlsx"
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: " + e);
                LOGGER.log(Level.INFO, "Ошибка: " + e);
            }
            XSSFWorkbook reportSatisfactionKia = null;
            try {
                assert templateSatisfactionKia != null;
                reportSatisfactionKia = new XSSFWorkbook(templateSatisfactionKia);
            } catch (IOException e) {
                System.out.println("Ошибка: " + e);
                LOGGER.log(Level.INFO, "Ошибка: " + e);
            }
            assert reportSatisfactionKia != null;

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
            FileOutputStream fileOuts = null;
            try {
                fileOuts = new FileOutputStream("C:\\Users\\Shabanov\\Desktop\\Shabanov\\Output reports\\Kia reports\\Feedback\\" + newFile);
                //   "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\SatisfactionReport\\"
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: " + e);
                LOGGER.log(Level.INFO, "Ошибка: " + e);
            }

            try {
                reportSatisfactionKia.write(fileOuts);
            } catch (IOException e) {
                System.out.println("Ошибка: " + e);
                LOGGER.log(Level.INFO, "Ошибка: " + e);
            }
            try {
                assert fileOuts != null;
                fileOuts.close();
            } catch (IOException e) {
                System.out.println("Ошибка: " + e);
                LOGGER.log(Level.INFO, "Ошибка: " + e);
            }


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
            //  "C:\\Users\\User\\Desktop\\vasNPS\\src\\main\\resources\\outputReports\\SatisfactionReport\\"
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
