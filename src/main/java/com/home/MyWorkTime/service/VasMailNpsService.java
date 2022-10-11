package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasMailNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasMailNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class VasMailNpsService {

    private final VasMailNpsRepository vasMailNpsRepository;
    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;

    public VasMailNpsService(VasMailNpsRepository vasMailNpsRepository,
                             JavaMailSender javaMailSender,
                             VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasMailNpsRepository = vasMailNpsRepository;
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

    @SneakyThrows
    @Scheduled(cron = "1 00 20 * * *")
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

        HSSFWorkbook workbookNPSCall = new HSSFWorkbook(); // книга
        HSSFSheet sheetNPSCall;
        HSSFRow rowHeadNPSCall;

        String replace = addressListCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!
        System.out.println(replace);
        if (!(listKia.isEmpty())){
            String file = "NPSkia.xls";

            String npsSheet = "NPS_KIA";

            sheetNPSCall = workbookNPSCall.createSheet(npsSheet); // лист
            rowHeadNPSCall = sheetNPSCall.createRow((short)0); // строка

            rowHeadNPSCall.createCell(0).setCellValue("No.");
            rowHeadNPSCall.createCell(1).setCellValue("Фамилия");
            rowHeadNPSCall.createCell(2).setCellValue("Имя");
            rowHeadNPSCall.createCell(3).setCellValue("Телефон 1");
            rowHeadNPSCall.createCell(4).setCellValue("Телефон 2");
            rowHeadNPSCall.createCell(5).setCellValue("Номер З/Н");
            rowHeadNPSCall.createCell(6).setCellValue("Оценка");
            rowHeadNPSCall.createCell(7).setCellValue("Комментарий");
            rowHeadNPSCall.createCell(8).setCellValue("Дата звонка");
            rowHeadNPSCall.createCell(9).setCellValue("ФИО администратора");
                for (int i=0; i< listKia.size(); i++) {
                    HSSFRow rowNpsCall = sheetNPSCall.createRow((short) i+1);
                    rowNpsCall.createCell(0).setCellValue(i + 1);
                    rowNpsCall.createCell(1).setCellValue(listKia.get(i).getClient_surname());
                    rowNpsCall.createCell(2).setCellValue(listKia.get(i).getClient_name());
                    rowNpsCall.createCell(3).setCellValue(listKia.get(i).getPhone_1());
                    if (listKia.get(i).getPhone_2() != null) {
                        rowNpsCall.createCell(4).setCellValue(listKia.get(i).getPhone_2());
                    } else {
                        rowNpsCall.createCell(4).setCellValue("");
                    }
                    rowNpsCall.createCell(5).setCellValue(listKia.get(i).getNum_order());
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
                }
            FileOutputStream fileOut = new FileOutputStream(file);
            workbookNPSCall.write(fileOut);
            fileOut.close();

            setTo = addressListKia.toString().replace("[","").replace("]","");
            setCc = replace;
            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
            helper.setFrom("info@vitautocity.by");
            helper.setTo(setTo);  //-получатель
            helper.setCc(setCopy); //-копия
            helper.setSubject("NPS KIA");
            helper.setText("""
                Добрый день! \s
  
                Прошу Вас провести обратную связь с клиентами и заполнить вложенный файл. \s
                После заполнения - отправить файл на электронную почту: k.shabanov@vitautocity.by \s
                
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

            FileSystemResource npsCall = new FileSystemResource(new File(file));
            helper.addAttachment(file, npsCall);
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
            rowHeadNPSCall.createCell(20).setCellValue("Оценка");
            rowHeadNPSCall.createCell(21).setCellValue("Комментарий");
            rowHeadNPSCall.createCell(22).setCellValue("Дата звонка");
            rowHeadNPSCall.createCell(23).setCellValue("ФИО администратора");

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
                rowNpsCall.createCell(7).setCellValue(listSkoda.get(i).getVin());
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
                sheetNPSCall.autoSizeColumn(20);
                sheetNPSCall.autoSizeColumn(21);
                sheetNPSCall.autoSizeColumn(22);
                sheetNPSCall.autoSizeColumn(23);

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
  
                Прошу Вас провести обратную связь с клиентами и заполнить вложенный файл. \s
                После заполнения - отправить файл на электронную почту: k.shabanov@vitautocity.by \s
                
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

        if (!(listMultibrand.isEmpty())){
            String file = "NPSmultibrand.xls";
            String npsSheet = "NPS_Multibrand";

            sheetNPSCall = workbookNPSCall.createSheet(npsSheet); // лист
            rowHeadNPSCall = sheetNPSCall.createRow((short)0); // строка

            rowHeadNPSCall.createCell(0).setCellValue("No.");
            rowHeadNPSCall.createCell(1).setCellValue("Фамилия");
            rowHeadNPSCall.createCell(2).setCellValue("Имя");
            rowHeadNPSCall.createCell(3).setCellValue("Телефон 1");
            rowHeadNPSCall.createCell(4).setCellValue("Телефон 2");
            rowHeadNPSCall.createCell(5).setCellValue("Номер З/Н");
            rowHeadNPSCall.createCell(6).setCellValue("Номер шасси");
            rowHeadNPSCall.createCell(7).setCellValue("Гос.номер");
            rowHeadNPSCall.createCell(8).setCellValue("Марка");
            rowHeadNPSCall.createCell(9).setCellValue("Модель");
            rowHeadNPSCall.createCell(10).setCellValue("Срок мастерской");
            rowHeadNPSCall.createCell(11).setCellValue("Оценка");
            rowHeadNPSCall.createCell(12).setCellValue("Комментарий");
            rowHeadNPSCall.createCell(13).setCellValue("Дата звонка");
            rowHeadNPSCall.createCell(14).setCellValue("ФИО администратора");
            for (int i=0; i< listMultibrand.size(); i++) {
                HSSFRow rowNpsCall = sheetNPSCall.createRow((short) i+1);
                rowNpsCall.createCell(0).setCellValue(i + 1);
                rowNpsCall.createCell(1).setCellValue(listMultibrand.get(i).getClient_surname());
                rowNpsCall.createCell(2).setCellValue(listMultibrand.get(i).getClient_name());
                rowNpsCall.createCell(3).setCellValue(listMultibrand.get(i).getPhone_1());
                if (listMultibrand.get(i).getPhone_2() != null) {
                    rowNpsCall.createCell(4).setCellValue(listMultibrand.get(i).getPhone_2());
                } else {
                    rowNpsCall.createCell(4).setCellValue("");
                }
                rowNpsCall.createCell(5).setCellValue(listMultibrand.get(i).getNum_order());
                rowNpsCall.createCell(6).setCellValue(listMultibrand.get(i).getVin());
                rowNpsCall.createCell(7).setCellValue(listMultibrand.get(i).getReg_num());
                rowNpsCall.createCell(8).setCellValue(listMultibrand.get(i).getBrand());
                rowNpsCall.createCell(9).setCellValue(listMultibrand.get(i).getModel());
                rowNpsCall.createCell(10).setCellValue(formatDate.format(listMultibrand.get(i).getDate_order()));
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
            }
            FileOutputStream fileOut = new FileOutputStream(file);
            workbookNPSCall.write(fileOut);
            fileOut.close();

            setTo = addressListMultibrand.toString().replace("[","").replace("]","");
            setCc = replace;

            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            MimeMessage messageVasNpsMail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageVasNpsMail, true, "UTF-8");
            helper.setFrom("info@vitautocity.by");
            helper.setTo(setTo);  //-получатель
            helper.setCc(setCopy); //-копия
            helper.setSubject("NPS Multibrand");
            helper.setText("""
                Добрый день! \s
  
                Прошу Вас провести обратную связь с клиентами и заполнить вложенный файл. \s
                После заполнения - отправить файл на электронную почту: k.shabanov@vitautocity.by \s
                
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

            FileSystemResource npsCall = new FileSystemResource(new File(file));
            helper.addAttachment(file, npsCall);
            javaMailSender.send(messageVasNpsMail);
        } else {
            System.err.println("Список Multibrand пустой!!!");
        }

    }

    /*
      В данном блоке выборка только по номеру - разберешься!!!!
     List<VasMailNpsModel> numOrderList = vasMailNpsRepository.npsCall();
     ArrayList<Long> list = new ArrayList<>();
     for (VasMailNpsModel vasMailNpsModel : numOrderList) {
     list.add(vasMailNpsModel.getNum_order());
     }
     String numOrder = list.toString().replace("[","").replace("]","");
     */
}
