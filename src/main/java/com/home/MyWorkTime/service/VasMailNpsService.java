package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasMailNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasMailNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
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

    private final VasMailNpsRepository vasMailNpsRepository;
    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;
    private final HashMap<String, Double> npsReportWeek = new HashMap<>();
    private final HashMap<String, Double> npsReportMonth = new HashMap<>();
    private final HashMap<String, Double> npsReportingMonth = new HashMap<>();

    public VasMailNpsService(VasMailNpsRepository vasMailNpsRepository,
                             JavaMailSender javaMailSender,
                             VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasMailNpsRepository = vasMailNpsRepository;
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

    @SneakyThrows
    @Scheduled(cron = "1 00 21 * * *")
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

        HSSFWorkbook workbookNPSCall; // книга
        HSSFSheet sheetNPSCall;
        HSSFRow rowHeadNPSCall;

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
                      
                    Прошу Вас провести обратную связь с клиентами и заполнить вложенный файл. \s
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
        } else {
            System.err.println("Список KIA пустой!!!");
        }

        if (!(listSkoda.isEmpty())){
            String file = "NPSskoda.xls";
            String npsSheet = "NPS_SKODA";

            workbookNPSCall = new HSSFWorkbook(); // книга
            sheetNPSCall = workbookNPSCall.createSheet(npsSheet); // лист
            rowHeadNPSCall = sheetNPSCall.createRow((short)0); // строка

            rowHeadNPSCall.createCell(0).setCellValue("Обращение");
            rowHeadNPSCall.createCell(1).setCellValue("Фамилия");
            rowHeadNPSCall.createCell(2).setCellValue("Имя");
            rowHeadNPSCall.createCell(3).setCellValue("Телефон 1");
            rowHeadNPSCall.createCell(4).setCellValue("Телефон 2");
            rowHeadNPSCall.createCell(5).setCellValue("Компания");
            rowHeadNPSCall.createCell(6).setCellValue("Предпочитаемый способ связи");
            rowHeadNPSCall.createCell(7).setCellValue("Номер шасси");
            rowHeadNPSCall.createCell(8).setCellValue("Гос.номер");
            rowHeadNPSCall.createCell(9).setCellValue("Марка");
            rowHeadNPSCall.createCell(10).setCellValue("Модель");
            rowHeadNPSCall.createCell(11).setCellValue("Год модели");
            rowHeadNPSCall.createCell(12).setCellValue("Срок мастерской");
            rowHeadNPSCall.createCell(13).setCellValue("Тип задания");
            rowHeadNPSCall.createCell(14).setCellValue("Номер З/Н");
            rowHeadNPSCall.createCell(15).setCellValue("Язык общения");
            rowHeadNPSCall.createCell(16).setCellValue("ID сервисного консультанта");
            rowHeadNPSCall.createCell(17).setCellValue("Номер импортёра");
            rowHeadNPSCall.createCell(18).setCellValue("Номер дилера");
            rowHeadNPSCall.createCell(19).setCellValue("");

            for (int i=0; i< listSkoda.size(); i++) {
                HSSFRow rowNpsCall = sheetNPSCall.createRow((short) i+1);
                rowNpsCall.createCell(0).setCellValue("");
                rowNpsCall.createCell(1).setCellValue(listSkoda.get(i).getClient_surname());
                rowNpsCall.createCell(2).setCellValue(listSkoda.get(i).getClient_name());
                rowNpsCall.createCell(3).setCellValue(listSkoda.get(i).getPhone_1());
                if (listSkoda.get(i).getPhone_2() != null) {
                    rowNpsCall.createCell(4).setCellValue(listSkoda.get(i).getPhone_2());
                } else {
                    rowNpsCall.createCell(4).setCellValue("");
                }
                rowNpsCall.createCell(5).setCellValue("VitebskAutoCity");
                rowNpsCall.createCell(6).setCellValue("");
                rowNpsCall.createCell(7).setCellValue(listSkoda.get(i).getVehicle_identification_number());
                rowNpsCall.createCell(8).setCellValue(listSkoda.get(i).getReg_num());
                rowNpsCall.createCell(9).setCellValue(listSkoda.get(i).getBrand());
                rowNpsCall.createCell(10).setCellValue(listSkoda.get(i).getModel());
                rowNpsCall.createCell(11).setCellValue(listSkoda.get(i).getYear_release());
                rowNpsCall.createCell(12).setCellValue(formatDate.format(listSkoda.get(i).getDate_order()));
                rowNpsCall.createCell(13).setCellValue("N");
                rowNpsCall.createCell(14).setCellValue(listSkoda.get(i).getNum_order());
                rowNpsCall.createCell(15).setCellValue("ru");
                rowNpsCall.createCell(16).setCellValue("N");
                rowNpsCall.createCell(17).setCellValue("000059");
                rowNpsCall.createCell(18).setCellValue("296");
                rowNpsCall.createCell(19).setCellValue("41007");
                sheetNPSCall.autoSizeColumn(0);
                sheetNPSCall.autoSizeColumn(1);
                sheetNPSCall.autoSizeColumn(2);
                sheetNPSCall.autoSizeColumn(3);
                sheetNPSCall.autoSizeColumn(4);
                sheetNPSCall.autoSizeColumn(5);
                sheetNPSCall.autoSizeColumn(6);
                sheetNPSCall.autoSizeColumn(7);
                sheetNPSCall.autoSizeColumn(8);
                sheetNPSCall.autoSizeColumn(9);
                sheetNPSCall.autoSizeColumn(10);
                sheetNPSCall.autoSizeColumn(11);
                sheetNPSCall.autoSizeColumn(12);
                sheetNPSCall.autoSizeColumn(13);
                sheetNPSCall.autoSizeColumn(14);
                sheetNPSCall.autoSizeColumn(15);
                sheetNPSCall.autoSizeColumn(16);
                sheetNPSCall.autoSizeColumn(17);
                sheetNPSCall.autoSizeColumn(18);
                sheetNPSCall.autoSizeColumn(19);

            }
            FileOutputStream fileOut = new FileOutputStream(file);
            workbookNPSCall.write(fileOut);
            fileOut.close();

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

            FileSystemResource npsCall = new FileSystemResource(new File(file));
            helper.addAttachment(file, npsCall);
            javaMailSender.send(messageVasNpsMail);
        } else {
            System.err.println("Список SKODA пустой!!!");
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
                      
                    Прошу Вас провести обратную связь с клиентами и заполнить вложенный файл. \s
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
        } else {
            System.err.println("Список Multibrand пустой!");
        }
    }

    @Scheduled(cron = "1 30 20 * * 0")
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
            FileInputStream npsReport = new FileInputStream("C:\\Users\\User\\Desktop\\VAS-NPS\\src\\main\\resources\\templates\\currentWeekNPS.xlsx");
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
            helper.setSubject("Отчёт по показателю NPS, организация ООО \"ВитебскАвтоСити\" в разрезе неделя/текущий месяц");
            helper.setText("""
                Добрый день! \s
  
                К письму прикреплен отчёт по показателю NPS.""");

            FileSystemResource currentWeekNpsReport = new FileSystemResource(new File(file));
            helper.addAttachment(file, currentWeekNpsReport);
            javaMailSender.send(messageVasNpsMail);

        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
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
            FileInputStream npsReport = new FileInputStream("C:\\Users\\User\\Desktop\\VAS-NPS\\src\\main\\resources\\templates\\currentMonthNPS.xlsx");
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

            FileSystemResource currentWeekNpsReport = new FileSystemResource(new File(file));
            helper.addAttachment(file, currentWeekNpsReport);
            javaMailSender.send(messageVasNpsMail);

        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
    }

}
