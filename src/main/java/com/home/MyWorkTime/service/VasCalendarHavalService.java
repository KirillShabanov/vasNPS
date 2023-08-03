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


import com.home.MyWorkTime.entity.VasCalendarHavalModel;
import com.home.MyWorkTime.entity.VasCalendarModelDTO;
import com.home.MyWorkTime.repository.VasCalendarHavalRepository;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;



@Service
public class VasCalendarHavalService {

    private final VasCalendarHavalRepository vasCalendarHavalRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCalendarHavalService.class.getName());

    public VasCalendarHavalService(VasCalendarHavalRepository vasCalendarHavalRepository) {
        this.vasCalendarHavalRepository = vasCalendarHavalRepository;
    }

    public void newRowForCalendarHaval(VasCalendarModelDTO vasCalendarModelDTO){
        //Разделение потока по модели авто
        String keyPeriodicity = vasCalendarModelDTO.getModel().strip();

        if (keyPeriodicity.equals("Jolion")){
            String vin = vasCalendarModelDTO.getVin();
            String vinInCalendar = vasCalendarHavalRepository.findVin(vin);
            if (vin.equals(vinInCalendar)){
                updateOldRowForCalendarHavalJolion(vasCalendarModelDTO);
            } else {
                addNewRowForCalendarHavalJolion(vasCalendarModelDTO);
                LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Добавлена новая строка в календарь клиента Haval модель Jolion, с номером кузова: "
                        + vasCalendarModelDTO.getVin());
                //System.out.println("Добавлена новая строка в календарь клиента Haval, с номером кузова: "
                //        + vasCalendarModelDTO.getVin());
            }

        } else {
            String vin = vasCalendarModelDTO.getVin();
            String vinInCalendar = vasCalendarHavalRepository.findVin(vin);
            if (vin.equals(vinInCalendar)){
                updateOldRowForCalendarHaval(vasCalendarModelDTO);
            } else {
                addNewRowForCalendarHaval(vasCalendarModelDTO);
                LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Добавлена новая строка в календарь клиента Haval, с номером кузова: "
                        + vasCalendarModelDTO.getVin());
                //System.out.println("Добавлена новая строка в календарь клиента Haval, с номером кузова: "
                //        + vasCalendarModelDTO.getVin());
            }
        }
    }

    public void addNewRowForCalendarHaval(VasCalendarModelDTO vasCalendarModelDTO){
        //Добавление нового автомобиля
        Date addDate = new Date();
        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);
        calendar.add(Calendar.MONTH,3);
        Date plannedDate = calendar.getTime();
        String activity = "active";

        VasCalendarHavalModel vasCalendarHavalModel = new VasCalendarHavalModel();
        vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
        vasCalendarHavalModel.setModel(vasCalendarModelDTO.getModel());
        vasCalendarHavalModel.setVin(vasCalendarModelDTO.getVin());
        vasCalendarHavalModel.setYearRelease(vasCalendarModelDTO.getYearRelease());
        vasCalendarHavalModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
        vasCalendarHavalModel.setAddDate(addDate);
        vasCalendarHavalModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
        vasCalendarHavalModel.setActivity(activity);

        if (vasCalendarModelDTO.getMileage() < 250){
            //Добавление нового автомобиля при продаже в ВАС
            vasCalendarHavalModel.setPlannedDate(plannedDate);
        }

        vasCalendarHavalRepository.save(vasCalendarHavalModel);

        if (vasCalendarModelDTO.getMileage() >= 250) {
            //Добавление нового автомобиля если авто с пробегом
            updateNewRowForCalendarHaval(vasCalendarModelDTO);
        }

    }

    public void updateNewRowForCalendarHaval(VasCalendarModelDTO vasCalendarModelDTO){
        //Запись в календарь авто с пробегом, который приобритался не у нас
        String vin = vasCalendarModelDTO.getVin();
        VasCalendarHavalModel vasCalendarHavalModel;
        vasCalendarHavalModel = vasCalendarHavalRepository.findCardByVin(vin);

        Date saleDate = vasCalendarModelDTO.getSale();
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        Duration diff = Duration.between(saleDate.toInstant(), repairDate.toInstant());
        long diffDays = diff.toDays();

        HashMap<String,Long> calendarHavalMileage = new HashMap<>();
        calendarHavalMileage.put("to0_mileage",vasCalendarHavalModel.getToMileageZero());
        calendarHavalMileage.put("to1_mileage",vasCalendarHavalModel.getToMileageOne());
        calendarHavalMileage.put("to2_mileage",vasCalendarHavalModel.getToMileageTwo());
        calendarHavalMileage.put("to3_mileage",vasCalendarHavalModel.getToMileageThree());
        calendarHavalMileage.put("to4_mileage",vasCalendarHavalModel.getToMileageFour());
        calendarHavalMileage.put("to5_mileage",vasCalendarHavalModel.getToMileageFive());
        calendarHavalMileage.put("to6_mileage",vasCalendarHavalModel.getToMileageSex());
        calendarHavalMileage.put("to7_mileage",vasCalendarHavalModel.getToMileageSeven());
        calendarHavalMileage.put("to8_mileage",vasCalendarHavalModel.getToMileageEight());
        calendarHavalMileage.put("to9_mileage",vasCalendarHavalModel.getToMileageNine());
        calendarHavalMileage.put("to10_mileage",vasCalendarHavalModel.getToMileageTen());

        String[] keyGen = new String[1];
        for (int i = 11; i > 0; i--){
            if (calendarHavalMileage.get("to"+ i +"_mileage") != null){
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

            vasCalendarHavalRepository.updateToZero(vin, to0_date, to0_mileage, plannedDate);
        }
        //ТО-1 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 395) && (vasCalendarModelDTO.getMileage() > 5000 & vasCalendarModelDTO.getMileage() < 11000))){
            Long to1_mileage = vasCalendarModelDTO.getMileage();
            Date to1_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToOne(vin, to1_date, to1_mileage, plannedDate);
        }
        //ТО-2 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 790) && (vasCalendarModelDTO.getMileage() > 11000 & vasCalendarModelDTO.getMileage() < 30000))){
            Long to2_mileage = vasCalendarModelDTO.getMileage();
            Date to2_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToTwo(vin, to2_date, to2_mileage, plannedDate);

        }
        //ТО-3 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1185) && (vasCalendarModelDTO.getMileage() > 30000 & vasCalendarModelDTO.getMileage() < 41000))){
            Long to3_mileage = vasCalendarModelDTO.getMileage();
            Date to3_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToThree(vin, to3_date, to3_mileage, plannedDate);
        }
        //ТО-4 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1580) && (vasCalendarModelDTO.getMileage() > 41000 & vasCalendarModelDTO.getMileage() < 52000))){
            Long to4_mileage = vasCalendarModelDTO.getMileage();
            Date to4_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToFour(vin, to4_date, to4_mileage, plannedDate);
        }
        //Попытка добавить ТО за рамками 1-4
        else if ((key).equals("") && diffDays > 1580 || (key).equals("") && vasCalendarModelDTO.getMileage() > 52000){
            LOGGER.log(Level.ERROR, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Попытка добавить ТО за рамками 1-4 в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
            //System.out.println("Попытка добавить ТО за рамками 1-4 в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
        }
    }

    public void updateOldRowForCalendarHaval(VasCalendarModelDTO vasCalendarModelDTO){
        // Работа с записью в календаре клиента (известный авто)

        String vin = vasCalendarModelDTO.getVin();
        VasCalendarHavalModel vasCalendarHavalModel;
        vasCalendarHavalModel = vasCalendarHavalRepository.findCardByVin(vin);
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        HashMap<String,Long> calendarHavalMileage = new HashMap<>();
        HashMap<String,Date> calendarHavalDate = new HashMap<>();


        if (vasCalendarModelDTO.getVin().equals(vasCalendarHavalModel.getVin())){
            calendarHavalMileage.put("to0_mileage",vasCalendarHavalModel.getToMileageZero());
            calendarHavalMileage.put("to1_mileage",vasCalendarHavalModel.getToMileageOne());
            calendarHavalMileage.put("to2_mileage",vasCalendarHavalModel.getToMileageTwo());
            calendarHavalMileage.put("to3_mileage",vasCalendarHavalModel.getToMileageThree());
            calendarHavalMileage.put("to4_mileage",vasCalendarHavalModel.getToMileageFour());
            calendarHavalMileage.put("to5_mileage",vasCalendarHavalModel.getToMileageFive());
            calendarHavalMileage.put("to6_mileage",vasCalendarHavalModel.getToMileageSex());
            calendarHavalMileage.put("to7_mileage",vasCalendarHavalModel.getToMileageSeven());
            calendarHavalMileage.put("to8_mileage",vasCalendarHavalModel.getToMileageEight());
            calendarHavalMileage.put("to9_mileage",vasCalendarHavalModel.getToMileageNine());
            calendarHavalMileage.put("to10_mileage",vasCalendarHavalModel.getToMileageTen());

            calendarHavalDate.put("to0_date",vasCalendarHavalModel.getToDateZero());
            calendarHavalDate.put("to1_date",vasCalendarHavalModel.getToDateOne());
            calendarHavalDate.put("to2_date",vasCalendarHavalModel.getToDateTwo());
            calendarHavalDate.put("to3_date",vasCalendarHavalModel.getToDateThree());
            calendarHavalDate.put("to4_date",vasCalendarHavalModel.getToDateFour());
            calendarHavalDate.put("to5_date",vasCalendarHavalModel.getToDateFive());
            calendarHavalDate.put("to6_date",vasCalendarHavalModel.getToDateSex());
            calendarHavalDate.put("to7_date",vasCalendarHavalModel.getToDateSeven());
            calendarHavalDate.put("to8_date",vasCalendarHavalModel.getToDateEight());
            calendarHavalDate.put("to9_date",vasCalendarHavalModel.getToDateNine());
            calendarHavalDate.put("to10_date",vasCalendarHavalModel.getToDateTen());
        }

        String[] keyGen = new String[1];
        keyGen[0] = "";
        String newMileageKey = "to0_mileage";

        String[] dateGen = new String[1];
        dateGen[0] = "";


        for (int i = 10; i >= 0; i--){
            if (calendarHavalMileage.get("to"+ i +"_mileage") != null){
                keyGen[0] = "to"+ i +"_mileage";
                newMileageKey = "to"+ (i + 1) +"_mileage";
                break;
            }
        }

        for (int i = 10; i >= 0; i--){
            if (calendarHavalDate.get("to"+ i +"_date") != null){
                dateGen[0] = "to"+ i +"_date";
                break;
            }
        }

        Long previousMileage = calendarHavalMileage.get(keyGen[0]);
        Date previousDate = calendarHavalDate.get(dateGen[0]);

        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);

        Calendar calendarTo = Calendar.getInstance();

        Date plannedDate;
        Duration diff = Duration.between(vasCalendarHavalModel.getDateSale().toInstant(), repairDate.toInstant());
        calendarTo.setTime(repairDate);

        if (newMileageKey.equals("to0_mileage") && vasCalendarModelDTO.getMileage() > 1000) {
            // Корректировка даты следующего визита
            repairDate = vasCalendarModelDTO.getDateRepair();

            long diffDays = diff.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / (vasCalendarModelDTO.getMileage() / diffDays);

            if (countDay > 365) {
                calendar.add(Calendar.MONTH, 12);
            } else {
                calendar.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendar.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToDateZero(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageZero(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }


        if (newMileageKey.equals("to1_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            // Корректировка даты следующего визита
            long diffDays = diff.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / (vasCalendarModelDTO.getMileage() / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateOne(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageOne(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }


        if (newMileageKey.equals("to2_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateTwo(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageTwo(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to3_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateThree(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageThree(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to4_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateFour(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageFour(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to5_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateFive(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageFive(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to6_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateSex(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageSex(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to7_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateSeven(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageSeven(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to8_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateEight(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageEight(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to9_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateNine(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageNine(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }

        if (newMileageKey.equals("to10_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            Duration diffs = Duration.between(previousDate.toInstant(), repairDate.toInstant());
            // Корректировка даты следующего визита
            long diffDays = diffs.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / ((vasCalendarModelDTO.getMileage() - previousMileage) / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateTen(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageTen(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalModel.setActivity("deactivated");
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }
    }

    public void addNewRowForCalendarHavalJolion(VasCalendarModelDTO vasCalendarModelDTO){
        //Добавление нового автомобиля
        Date addDate = new Date();
        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);
        calendar.add(Calendar.MONTH,6);
        Date plannedDate = calendar.getTime();
        String activity = "active";

        VasCalendarHavalModel vasCalendarHavalModel = new VasCalendarHavalModel();
        vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
        vasCalendarHavalModel.setModel(vasCalendarModelDTO.getModel());
        vasCalendarHavalModel.setVin(vasCalendarModelDTO.getVin());
        vasCalendarHavalModel.setYearRelease(vasCalendarModelDTO.getYearRelease());
        vasCalendarHavalModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
        vasCalendarHavalModel.setAddDate(addDate);
        vasCalendarHavalModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
        vasCalendarHavalModel.setActivity(activity);

        if (vasCalendarModelDTO.getMileage() < 250){
            //Добавление нового автомобиля при продаже в ВАС
            vasCalendarHavalModel.setPlannedDate(plannedDate);
        }

        vasCalendarHavalRepository.save(vasCalendarHavalModel);

        if (vasCalendarModelDTO.getMileage() >= 250) {
            //Добавление нового автомобиля если авто с пробегом
            updateNewRowForCalendarHavalJolion(vasCalendarModelDTO);
        }

    }

    public void updateNewRowForCalendarHavalJolion(VasCalendarModelDTO vasCalendarModelDTO){
        //Запись в календарь авто с пробегом, который приобритался не у нас
        String vin = vasCalendarModelDTO.getVin();
        VasCalendarHavalModel vasCalendarHavalModel;
        vasCalendarHavalModel = vasCalendarHavalRepository.findCardByVin(vin);

        Date saleDate = vasCalendarModelDTO.getSale();
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        Duration diff = Duration.between(saleDate.toInstant(), repairDate.toInstant());
        long diffDays = diff.toDays();

        HashMap<String,Long> calendarHavalMileage = new HashMap<>();
        calendarHavalMileage.put("to0_mileage",vasCalendarHavalModel.getToMileageZero());
        calendarHavalMileage.put("to1_mileage",vasCalendarHavalModel.getToMileageOne());
        calendarHavalMileage.put("to2_mileage",vasCalendarHavalModel.getToMileageTwo());
        calendarHavalMileage.put("to3_mileage",vasCalendarHavalModel.getToMileageThree());
        calendarHavalMileage.put("to4_mileage",vasCalendarHavalModel.getToMileageFour());
        calendarHavalMileage.put("to5_mileage",vasCalendarHavalModel.getToMileageFive());
        calendarHavalMileage.put("to6_mileage",vasCalendarHavalModel.getToMileageSex());
        calendarHavalMileage.put("to7_mileage",vasCalendarHavalModel.getToMileageSeven());
        calendarHavalMileage.put("to8_mileage",vasCalendarHavalModel.getToMileageEight());
        calendarHavalMileage.put("to9_mileage",vasCalendarHavalModel.getToMileageNine());
        calendarHavalMileage.put("to10_mileage",vasCalendarHavalModel.getToMileageTen());

        String[] keyGen = new String[1];
        for (int i = 11; i > 0; i--){
            if (calendarHavalMileage.get("to"+ i +"_mileage") != null){
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

            calendar.add(Calendar.MONTH,6);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToZero(vin, to0_date, to0_mileage, plannedDate);
        }
        //ТО-1 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 395) && (vasCalendarModelDTO.getMileage() > 5000 & vasCalendarModelDTO.getMileage() < 11000))){
            Long to1_mileage = vasCalendarModelDTO.getMileage();
            Date to1_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToOne(vin, to1_date, to1_mileage, plannedDate);
        }
        //ТО-2 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 790) && (vasCalendarModelDTO.getMileage() > 11000 & vasCalendarModelDTO.getMileage() < 26000))){
            Long to2_mileage = vasCalendarModelDTO.getMileage();
            Date to2_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToTwo(vin, to2_date, to2_mileage, plannedDate);

        }
        //ТО-3 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1185) && (vasCalendarModelDTO.getMileage() > 26000 & vasCalendarModelDTO.getMileage() < 51000))){
            Long to3_mileage = vasCalendarModelDTO.getMileage();
            Date to3_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToThree(vin, to3_date, to3_mileage, plannedDate);
        }
        //ТО-4 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1580) && (vasCalendarModelDTO.getMileage() > 51000 & vasCalendarModelDTO.getMileage() < 67000))){
            Long to4_mileage = vasCalendarModelDTO.getMileage();
            Date to4_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarHavalRepository.updateToFour(vin, to4_date, to4_mileage, plannedDate);
        }
        //Попытка добавить ТО за рамками 1-4
        else if ((key).equals("") && diffDays > 1580 || (key).equals("") && vasCalendarModelDTO.getMileage() > 67000){
            LOGGER.log(Level.ERROR, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Попытка добавить ТО за рамками 1-4 в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
            //System.out.println("Попытка добавить ТО за рамками 1-4 в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
        }
    }

    public void updateOldRowForCalendarHavalJolion(VasCalendarModelDTO vasCalendarModelDTO){
        // Работа с записью в календаре клиента (известный авто)

        String vin = vasCalendarModelDTO.getVin();
        VasCalendarHavalModel vasCalendarHavalModel;
        vasCalendarHavalModel = vasCalendarHavalRepository.findCardByVin(vin);
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        HashMap<String,Long> calendarHavalMileage = new HashMap<>();
        HashMap<String,Date> calendarHavalDate = new HashMap<>();


        if (vasCalendarModelDTO.getVin().equals(vasCalendarHavalModel.getVin())){
            calendarHavalMileage.put("to0_mileage",vasCalendarHavalModel.getToMileageZero());
            calendarHavalMileage.put("to1_mileage",vasCalendarHavalModel.getToMileageOne());
            calendarHavalMileage.put("to2_mileage",vasCalendarHavalModel.getToMileageTwo());
            calendarHavalMileage.put("to3_mileage",vasCalendarHavalModel.getToMileageThree());
            calendarHavalMileage.put("to4_mileage",vasCalendarHavalModel.getToMileageFour());
            calendarHavalMileage.put("to5_mileage",vasCalendarHavalModel.getToMileageFive());
            calendarHavalMileage.put("to6_mileage",vasCalendarHavalModel.getToMileageSex());
            calendarHavalMileage.put("to7_mileage",vasCalendarHavalModel.getToMileageSeven());
            calendarHavalMileage.put("to8_mileage",vasCalendarHavalModel.getToMileageEight());
            calendarHavalMileage.put("to9_mileage",vasCalendarHavalModel.getToMileageNine());
            calendarHavalMileage.put("to10_mileage",vasCalendarHavalModel.getToMileageTen());

            calendarHavalDate.put("to0_date",vasCalendarHavalModel.getToDateZero());
            calendarHavalDate.put("to1_date",vasCalendarHavalModel.getToDateOne());
            calendarHavalDate.put("to2_date",vasCalendarHavalModel.getToDateTwo());
            calendarHavalDate.put("to3_date",vasCalendarHavalModel.getToDateThree());
            calendarHavalDate.put("to4_date",vasCalendarHavalModel.getToDateFour());
            calendarHavalDate.put("to5_date",vasCalendarHavalModel.getToDateFive());
            calendarHavalDate.put("to6_date",vasCalendarHavalModel.getToDateSex());
            calendarHavalDate.put("to7_date",vasCalendarHavalModel.getToDateSeven());
            calendarHavalDate.put("to8_date",vasCalendarHavalModel.getToDateEight());
            calendarHavalDate.put("to9_date",vasCalendarHavalModel.getToDateNine());
            calendarHavalDate.put("to10_date",vasCalendarHavalModel.getToDateTen());
        }

        String[] keyGen = new String[1];
        keyGen[0] = "";
        String newMileageKey = "to0_mileage";

        String[] dateGen = new String[1];
        dateGen[0] = "";


        for (int i = 10; i >= 0; i--){
            if (calendarHavalMileage.get("to"+ i +"_mileage") != null){
                keyGen[0] = "to"+ i +"_mileage";
                newMileageKey = "to"+ (i + 1) +"_mileage";
                break;
            }
        }

        for (int i = 10; i >= 0; i--){
            if (calendarHavalDate.get("to"+ i +"_date") != null){
                dateGen[0] = "to"+ i +"_date";
                break;
            }
        }

        Long previousMileage = calendarHavalMileage.get(keyGen[0]);
        Date previousDate = calendarHavalDate.get(dateGen[0]);

        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);

        Calendar calendarTo = Calendar.getInstance();

        Date plannedDate;
        Duration diff = Duration.between(vasCalendarHavalModel.getDateSale().toInstant(), repairDate.toInstant());
        calendarTo.setTime(repairDate);

        if (newMileageKey.equals("to0_mileage") && vasCalendarModelDTO.getMileage() > 1000) {
            // Корректировка даты следующего визита
            repairDate = vasCalendarModelDTO.getDateRepair();

            long diffDays = diff.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / (vasCalendarModelDTO.getMileage() / diffDays);

            if (countDay > 365) {
                calendar.add(Calendar.MONTH, 12);
            } else {
                calendar.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendar.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToDateZero(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageZero(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }


        if (newMileageKey.equals("to1_mileage") && !previousMileage.equals(vasCalendarModelDTO.getMileage()) && previousDate != vasCalendarModelDTO.getDateRepair()) {
            // Корректировка даты следующего визита
            long diffDays = diff.toDays(); //Количество дней между предыдущем ТО и датой проведения текущего ТО

            long countDay = 10000 / (vasCalendarModelDTO.getMileage() / diffDays);

            if (countDay >= 365) {
                calendarTo.add(Calendar.MONTH, 12);
            } else {
                calendarTo.add(Calendar.DATE, (int) countDay);
            }
            plannedDate = calendarTo.getTime();
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateOne(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageOne(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateTwo(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageTwo(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateThree(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageThree(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateFour(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageFour(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateFive(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageFive(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateSex(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageSex(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: " + vasCalendarModelDTO.getVin());
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateSeven(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageSeven(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateEight(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageEight(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateNine(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageNine(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
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
            vasCalendarHavalModel.setPlannedDate(plannedDate);

            vasCalendarHavalModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarHavalModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarHavalModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setPlannedDate(plannedDate);
            vasCalendarHavalModel.setToDateTen(vasCalendarModelDTO.getDateRepair());
            vasCalendarHavalModel.setToMileageTen(vasCalendarModelDTO.getMileage());
            vasCalendarHavalModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarHavalModel.setRemmark(null);
            vasCalendarHavalModel.setActivity("deactivated");
            vasCalendarHavalRepository.save(vasCalendarHavalModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Haval Jolion, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Haval, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }
    }

    public List<VasCalendarHavalModel> findThisMonth() {
        return vasCalendarHavalRepository.findThisMonth();
    }

    public List<VasCalendarHavalModel> findPreviousMonth() {
            int months;
            int year;
            LocalDate date = LocalDate.now();

            int monthsCheck = date.getMonthValue();
            int yearCheck = date.getYear();

            if (monthsCheck == 1){
                months = 12;
                year = yearCheck - 1;
            } else {
                months = monthsCheck - 1;
                year = yearCheck;
            }
            return vasCalendarHavalRepository.findPreviousMonth(months, year);
        }

    public List<VasCalendarHavalModel> findThisMonthTO(Long id) {
        return vasCalendarHavalRepository.findThisMonthTO(id);
    }

    public Object addRemmark(VasCalendarHavalModel vasCalendarHavalModel) {
        Long id = vasCalendarHavalModel.getId();
        String remmark = vasCalendarHavalModel.getRemmark();
        Date dateRemmark = new Date();
        
        LOGGER.log(Level.INFO, "Добавлен комментарий в карточке ТО календаря Haval: " + vasCalendarHavalModel.getRemmark() + ". ID строки: " + vasCalendarHavalModel.getId() + ".");
        return vasCalendarHavalRepository.addRemmark(id, remmark, dateRemmark);
    }

}
