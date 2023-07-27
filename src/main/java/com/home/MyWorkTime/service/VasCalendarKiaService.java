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


import com.home.MyWorkTime.entity.VasCalendarKiaModel;
import com.home.MyWorkTime.entity.VasCalendarModelDTO;
import com.home.MyWorkTime.repository.VasCalendarKiaRepository;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;


@Service
public class VasCalendarKiaService {

    private final VasCalendarKiaRepository vasCalendarKiaRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCalendarKiaService.class.getName());

    public VasCalendarKiaService(VasCalendarKiaRepository vasCalendarKiaRepository) {
        this.vasCalendarKiaRepository = vasCalendarKiaRepository;
    }

    public void newRowForCalendarKia(VasCalendarModelDTO vasCalendarModelDTO){
        String vin = vasCalendarModelDTO.getVin();
        String vinInCalendar = vasCalendarKiaRepository.findVin(vin);
        if (vin.equals(vinInCalendar)){
            updateOldRowForCalendarKia(vasCalendarModelDTO);
        } else {
            addNewRowForCalendarKia(vasCalendarModelDTO);
            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Добавлена новая строка в календарь клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Добавлена новая строка в календарь клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }
    }

    public void addNewRowForCalendarKia(VasCalendarModelDTO vasCalendarModelDTO){
        //Добавление нового автомобиля
        Date addDate = new Date();
        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);
        calendar.add(Calendar.MONTH,3);
        Date plannedDate = calendar.getTime();
        String activity = "active";

        VasCalendarKiaModel vasCalendarKiaModel = new VasCalendarKiaModel();
        vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
        vasCalendarKiaModel.setModel(vasCalendarModelDTO.getModel());
        vasCalendarKiaModel.setVin(vasCalendarModelDTO.getVin());
        vasCalendarKiaModel.setYearRelease(vasCalendarModelDTO.getYearRelease());
        vasCalendarKiaModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
        vasCalendarKiaModel.setAddDate(addDate);
        vasCalendarKiaModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
        vasCalendarKiaModel.setActivity(activity);

        if (vasCalendarModelDTO.getMileage() < 250){
            //Добавление нового автомобиля при продаже в ВАС
            vasCalendarKiaModel.setPlannedDate(plannedDate);
        }

        vasCalendarKiaRepository.save(vasCalendarKiaModel);

        if (vasCalendarModelDTO.getMileage() >= 250) {
            //Добавление нового автомобиля если авто с пробегом
            updateNewRowForCalendarKia(vasCalendarModelDTO);
        }

    }

    public void updateNewRowForCalendarKia(VasCalendarModelDTO vasCalendarModelDTO){
        //Запись в календарь авто с пробегом, который приобритался не у нас
        String vin = vasCalendarModelDTO.getVin();
        VasCalendarKiaModel vasCalendarKiaModel;
        vasCalendarKiaModel = vasCalendarKiaRepository.findCardByVin(vin);

        Date saleDate = vasCalendarModelDTO.getSale();
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        Duration diff = Duration.between(saleDate.toInstant(), repairDate.toInstant());
        long diffDays = diff.toDays();

        HashMap<String,Long> calendarKiaMileage = new HashMap<>();
        calendarKiaMileage.put("to0_mileage",vasCalendarKiaModel.getToMileageZero());
        calendarKiaMileage.put("to1_mileage",vasCalendarKiaModel.getToMileageOne());
        calendarKiaMileage.put("to2_mileage",vasCalendarKiaModel.getToMileageTwo());
        calendarKiaMileage.put("to3_mileage",vasCalendarKiaModel.getToMileageThree());
        calendarKiaMileage.put("to4_mileage",vasCalendarKiaModel.getToMileageFour());
        calendarKiaMileage.put("to5_mileage",vasCalendarKiaModel.getToMileageFive());
        calendarKiaMileage.put("to6_mileage",vasCalendarKiaModel.getToMileageSex());
        calendarKiaMileage.put("to7_mileage",vasCalendarKiaModel.getToMileageSeven());
        calendarKiaMileage.put("to8_mileage",vasCalendarKiaModel.getToMileageEight());
        calendarKiaMileage.put("to9_mileage",vasCalendarKiaModel.getToMileageNine());
        calendarKiaMileage.put("to10_mileage",vasCalendarKiaModel.getToMileageTen());

        String[] keyGen = new String[1];
        for (int i = 11; i > 0; i--){
            if (calendarKiaMileage.get("to"+ i +"_mileage") != null){
                keyGen[0] = "to"+ i +"_mileage";
                break;
            }
        }

        String key = "";

        if (keyGen[0] != null) {
            key = keyGen[0];
        }

        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);

        Date plannedDate;

        //ТО-0 если авто впервые с пробегом
        if ((key).equals("") && diffDays < 200 && vasCalendarModelDTO.getMileage() < 5000){
            Long to0_mileage = vasCalendarModelDTO.getMileage();
            Date to0_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.add(Calendar.MONTH,3);
            plannedDate = calendar.getTime();

            vasCalendarKiaRepository.updateToZero(vin, to0_date, to0_mileage, plannedDate);
        }
        //ТО-1 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 395) && (vasCalendarModelDTO.getMileage() > 5000 & vasCalendarModelDTO.getMileage() < 16000))){
            Long to1_mileage = vasCalendarModelDTO.getMileage();
            Date to1_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarKiaRepository.updateToOne(vin, to1_date, to1_mileage, plannedDate);
        }
        //ТО-2 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 790) && (vasCalendarModelDTO.getMileage() > 16000 & vasCalendarModelDTO.getMileage() < 32000))){
            Long to2_mileage = vasCalendarModelDTO.getMileage();
            Date to2_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarKiaRepository.updateToTwo(vin, to2_date, to2_mileage, plannedDate);

        }
        //ТО-3 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1185) && (vasCalendarModelDTO.getMileage() > 32000 & vasCalendarModelDTO.getMileage() < 48000))){
            Long to3_mileage = vasCalendarModelDTO.getMileage();
            Date to3_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarKiaRepository.updateToThree(vin, to3_date, to3_mileage, plannedDate);
        }
        //ТО-4 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1580) && (vasCalendarModelDTO.getMileage() > 48000 & vasCalendarModelDTO.getMileage() < 64000))){
            Long to4_mileage = vasCalendarModelDTO.getMileage();
            Date to4_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarKiaRepository.updateToFour(vin, to4_date, to4_mileage, plannedDate);
        }
        //Попытка добавить ТО за рамками 1-4
        else if ((key).equals("") && diffDays > 1580 || (key).equals("") && vasCalendarModelDTO.getMileage() > 64000){
            LOGGER.log(Level.ERROR, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Попытка добавить ТО за рамками 1-4 в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
            //System.out.println("Попытка добавить ТО за рамками 1-4 в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
        }
    }

    public void updateOldRowForCalendarKia(VasCalendarModelDTO vasCalendarModelDTO){
        // Работа с записью в календаре клиента (известный авто)

        String vin = vasCalendarModelDTO.getVin();
        VasCalendarKiaModel vasCalendarKiaModel;
        vasCalendarKiaModel = vasCalendarKiaRepository.findCardByVin(vin);
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        HashMap<String,Long> calendarKiaMileage = new HashMap<>();
        HashMap<String,Date> calendarKiaDate = new HashMap<>();


        if (vasCalendarModelDTO.getVin().equals(vasCalendarKiaModel.getVin())){
            calendarKiaMileage.put("to0_mileage",vasCalendarKiaModel.getToMileageZero());
            calendarKiaMileage.put("to1_mileage",vasCalendarKiaModel.getToMileageOne());
            calendarKiaMileage.put("to2_mileage",vasCalendarKiaModel.getToMileageTwo());
            calendarKiaMileage.put("to3_mileage",vasCalendarKiaModel.getToMileageThree());
            calendarKiaMileage.put("to4_mileage",vasCalendarKiaModel.getToMileageFour());
            calendarKiaMileage.put("to5_mileage",vasCalendarKiaModel.getToMileageFive());
            calendarKiaMileage.put("to6_mileage",vasCalendarKiaModel.getToMileageSex());
            calendarKiaMileage.put("to7_mileage",vasCalendarKiaModel.getToMileageSeven());
            calendarKiaMileage.put("to8_mileage",vasCalendarKiaModel.getToMileageEight());
            calendarKiaMileage.put("to9_mileage",vasCalendarKiaModel.getToMileageNine());
            calendarKiaMileage.put("to10_mileage",vasCalendarKiaModel.getToMileageTen());

            calendarKiaDate.put("to0_date",vasCalendarKiaModel.getToDateZero());
            calendarKiaDate.put("to1_date",vasCalendarKiaModel.getToDateOne());
            calendarKiaDate.put("to2_date",vasCalendarKiaModel.getToDateTwo());
            calendarKiaDate.put("to3_date",vasCalendarKiaModel.getToDateThree());
            calendarKiaDate.put("to4_date",vasCalendarKiaModel.getToDateFour());
            calendarKiaDate.put("to5_date",vasCalendarKiaModel.getToDateFive());
            calendarKiaDate.put("to6_date",vasCalendarKiaModel.getToDateSex());
            calendarKiaDate.put("to7_date",vasCalendarKiaModel.getToDateSeven());
            calendarKiaDate.put("to8_date",vasCalendarKiaModel.getToDateEight());
            calendarKiaDate.put("to9_date",vasCalendarKiaModel.getToDateNine());
            calendarKiaDate.put("to10_date",vasCalendarKiaModel.getToDateTen());
        }

        String[] keyGen = new String[1];
        keyGen[0] = "";
        String newMileageKey = "to0_mileage";

        String[] dateGen = new String[1];
        dateGen[0] = "";


        for (int i = 10; i >= 0; i--){
            if (calendarKiaMileage.get("to"+ i +"_mileage") != null){
                keyGen[0] = "to"+ i +"_mileage";
                newMileageKey = "to"+ (i + 1) +"_mileage";
                break;
            }
        }

        for (int i = 10; i >= 0; i--){
            if (calendarKiaDate.get("to"+ i +"_date") != null){
                dateGen[0] = "to"+ i +"_date";
                break;
            }
        }

        Long previousMileage = calendarKiaMileage.get(keyGen[0]);
        Date previousDate = calendarKiaDate.get(dateGen[0]);

        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);

        Calendar calendarTo = Calendar.getInstance();

        Date plannedDate;
        Duration diff = Duration.between(vasCalendarKiaModel.getDateSale().toInstant(), repairDate.toInstant());
        calendarTo.setTime(repairDate);

        if (newMileageKey.equals("to0_mileage") && vasCalendarModelDTO.getMileage() > 1000) {
            // Корректировка даты следующего визита
            repairDate = vasCalendarModelDTO.getDateRepair();

            long diffDays = diff.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / (vasCalendarModelDTO.getMileage() / diffDays);

            if (countDay > 365) {
                calendar.add(Calendar.MONTH, 12);
            } else {
                calendar.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendar.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToDateZero(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageZero(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }


        if (newMileageKey.equals("to1_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            // Корректировка даты следующего визита
            long diffDays = diff.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / (vasCalendarModelDTO.getMileage() / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateOne(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageOne(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }


        if (newMileageKey.equals("to2_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateTwo(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageTwo(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to3_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateThree(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageThree(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to4_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateFour(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageFour(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to5_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateFive(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageFive(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to6_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateSex(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageSex(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to7_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateSeven(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageSeven(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to8_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateEight(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageEight(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to9_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateNine(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageNine(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to10_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 15000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarKiaModel.setPlannedDate(plannedDate);

            vasCalendarKiaModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarKiaModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarKiaModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setPlannedDate(plannedDate);
            vasCalendarKiaModel.setToDateTen(vasCalendarModelDTO.getDateRepair());
            vasCalendarKiaModel.setToMileageTen(vasCalendarModelDTO.getMileage());
            vasCalendarKiaModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarKiaModel.setRemmark(null);
            vasCalendarKiaModel.setActivity("deactivated");
            vasCalendarKiaRepository.save(vasCalendarKiaModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента KIA, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента KIA, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }
    }

    public List<VasCalendarKiaModel> findThisMonth() {
        return vasCalendarKiaRepository.findThisMonth();
    }

    public List<VasCalendarKiaModel> findThisMonthTO(Long id) {
        return vasCalendarKiaRepository.findThisMonthTO(id);
    }

    public Object addRemmark(VasCalendarKiaModel vasCalendarKiaModel) {
        Long id = vasCalendarKiaModel.getId();
        String remmark = vasCalendarKiaModel.getRemmark();
        Date dateRemmark = new Date();
        
        LOGGER.log(Level.INFO, "Добавлен комментарий в карточке ТО календаря KIA: " + vasCalendarKiaModel.getRemmark() + ". ID строки: " + vasCalendarKiaModel.getId() + ".");
        return vasCalendarKiaRepository.addRemmark(id, remmark, dateRemmark);
    }
}
