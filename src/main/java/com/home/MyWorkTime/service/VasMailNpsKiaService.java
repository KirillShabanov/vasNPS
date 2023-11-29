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

    /* 
import com.home.MyWorkTime.entity.VasMailNpsModel;
import com.home.MyWorkTime.entity.VasManagerNpsModel;
import com.home.MyWorkTime.repository.VasMailNpsRepository;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;

import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;



@Service
public class VasMailNpsKiaService {

    private static final Logger LOGGER = Logger.getLogger(VasMailNpsKiaService.class.getName());

    private final VasMailNpsRepository vasMailNpsRepository;
    private final JavaMailSender javaMailSender;
    private final VasManagerNpsRepository vasManagerNpsRepository;

    public VasMailNpsKiaService(VasMailNpsRepository vasMailNpsRepository,
                                JavaMailSender javaMailSender,
                                VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasMailNpsRepository = vasMailNpsRepository;
        this.javaMailSender = javaMailSender;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

     
    @SneakyThrows
    @Scheduled(cron = "1 00 19 * * 1-5")
    @Scheduled(cron = "1 00 16 * * 6-7")
    private void SendVasNpsMail() {
        List<VasMailNpsModel> listKia = vasMailNpsRepository.npsListKia();

        List<VasManagerNpsModel> addressManagerKia = vasManagerNpsRepository.forMailKia();
        List<VasManagerNpsModel> addressManagerCopy = vasManagerNpsRepository.forMailCopy();

        ArrayList<String> addressListKia = new ArrayList<>();
        ArrayList<String> addressListCopy = new ArrayList<>();

        for (VasManagerNpsModel vasManagerNpsModel : addressManagerKia) {
            addressListKia.add(vasManagerNpsModel.getManager_email());
        }
        for (VasManagerNpsModel vasManagerNpsModel : addressManagerCopy) {
            addressListCopy.add(vasManagerNpsModel.getManager_email());
        }
        //Ниже код для адресата отправки
        String setTo;
        String setCc;


        String replace = addressListCopy.toString().replace("[", "").replace("]", ",");//Волшебная подсказка!!!!

        if (!(listKia.isEmpty())) {

            setTo = addressListKia.toString().replace("[", "").replace("]", "");
            setCc = replace;
            InternetAddress[] setCopy = InternetAddress.parse(setCc);

            List<VasMailNpsModel> numOrderList = vasMailNpsRepository.npsListKia();
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
            //System.out.println("Список KIA сформирован и отправлен.");
        } else {
            System.out.println("Список KIA не сформирован, данных нет.");
            //LOGGER.log(Level.INFO, "Список KIA не сформирован, данных нет.");
        }
    }
    
}
*/
