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


import com.home.MyWorkTime.entity.VasCalendarJacModel;
import com.home.MyWorkTime.entity.VasCalendarModelDTO;
import com.home.MyWorkTime.repository.VasCalendarJacRepository;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;



@Service
public class VasCalendarJacService {

    private final VasCalendarJacRepository vasCalendarJacRepository;

    private static final Logger LOGGER = Logger.getLogger(VasCalendarJacService.class.getName());

    public VasCalendarJacService(VasCalendarJacRepository vasCalendarJacRepository) {
        this.vasCalendarJacRepository = vasCalendarJacRepository;
    }

    public void newRowForCalendarJac(VasCalendarModelDTO vasCalendarModelDTO){
        String vin = vasCalendarModelDTO.getVin();
        String vinInCalendar = vasCalendarJacRepository.findVin(vin);
        if (vin.equals(vinInCalendar)){
            updateOldRowForCalendarJac(vasCalendarModelDTO);
        } else {
            addNewRowForCalendarJac(vasCalendarModelDTO);
            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Добавлена новая строка в календарь клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Добавлена новая строка в календарь клиента Jac, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }
    }

    public void addNewRowForCalendarJac(VasCalendarModelDTO vasCalendarModelDTO){
        //Добавление нового автомобиля
        Date addDate = new Date();
        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);
        calendar.add(Calendar.MONTH,3);
        Date plannedDate = calendar.getTime();
        String activity = "active";

        VasCalendarJacModel vasCalendarJacModel = new VasCalendarJacModel();
        vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
        vasCalendarJacModel.setModel(vasCalendarModelDTO.getModel());
        vasCalendarJacModel.setVin(vasCalendarModelDTO.getVin());
        vasCalendarJacModel.setYearRelease(vasCalendarModelDTO.getYearRelease());
        vasCalendarJacModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
        vasCalendarJacModel.setAddDate(addDate);
        vasCalendarJacModel.setDateSale(vasCalendarModelDTO.getSale());
        vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
        vasCalendarJacModel.setActivity(activity);

        if (vasCalendarModelDTO.getMileage() < 250){
            //Добавление нового автомобиля при продаже в ВАС
            vasCalendarJacModel.setPlannedDate(plannedDate);
        }

        vasCalendarJacRepository.save(vasCalendarJacModel);

        if (vasCalendarModelDTO.getMileage() >= 250) {
            //Добавление нового автомобиля если авто с пробегом
            updateNewRowForCalendarJac(vasCalendarModelDTO);
        }

    }

    public void updateNewRowForCalendarJac(VasCalendarModelDTO vasCalendarModelDTO){
        //Запись в календарь авто с пробегом, который приобритался не у нас
        String vin = vasCalendarModelDTO.getVin();
        VasCalendarJacModel vasCalendarJacModel;
        vasCalendarJacModel = vasCalendarJacRepository.findCardByVin(vin);

        Date saleDate = vasCalendarModelDTO.getSale();
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        Duration diff = Duration.between(saleDate.toInstant(), repairDate.toInstant());
        long diffDays = diff.toDays();

        HashMap<String,Long> calendarJacMileage = new HashMap<>();
        calendarJacMileage.put("to0_mileage",vasCalendarJacModel.getToMileageZero());
        calendarJacMileage.put("to1_mileage",vasCalendarJacModel.getToMileageOne());
        calendarJacMileage.put("to2_mileage",vasCalendarJacModel.getToMileageTwo());
        calendarJacMileage.put("to3_mileage",vasCalendarJacModel.getToMileageThree());
        calendarJacMileage.put("to4_mileage",vasCalendarJacModel.getToMileageFour());
        calendarJacMileage.put("to5_mileage",vasCalendarJacModel.getToMileageFive());
        calendarJacMileage.put("to6_mileage",vasCalendarJacModel.getToMileageSex());
        calendarJacMileage.put("to7_mileage",vasCalendarJacModel.getToMileageSeven());
        calendarJacMileage.put("to8_mileage",vasCalendarJacModel.getToMileageEight());
        calendarJacMileage.put("to9_mileage",vasCalendarJacModel.getToMileageNine());
        calendarJacMileage.put("to10_mileage",vasCalendarJacModel.getToMileageTen());

        String[] keyGen = new String[1];
        for (int i = 11; i > 0; i--){
            if (calendarJacMileage.get("to"+ i +"_mileage") != null){
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

            vasCalendarJacRepository.updateToZero(vin, to0_date, to0_mileage, plannedDate);
        }
        //ТО-1 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 395) && (vasCalendarModelDTO.getMileage() > 5000 & vasCalendarModelDTO.getMileage() < 11000))){
            Long to1_mileage = vasCalendarModelDTO.getMileage();
            Date to1_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarJacRepository.updateToOne(vin, to1_date, to1_mileage, plannedDate);
        }
        //ТО-2 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 790) && (vasCalendarModelDTO.getMileage() > 11000 & vasCalendarModelDTO.getMileage() < 30000))){
            Long to2_mileage = vasCalendarModelDTO.getMileage();
            Date to2_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarJacRepository.updateToTwo(vin, to2_date, to2_mileage, plannedDate);

        }
        //ТО-3 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1185) && (vasCalendarModelDTO.getMileage() > 30000 & vasCalendarModelDTO.getMileage() < 41000))){
            Long to3_mileage = vasCalendarModelDTO.getMileage();
            Date to3_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarJacRepository.updateToThree(vin, to3_date, to3_mileage, plannedDate);
        }
        //ТО-4 если авто впервые с пробегом
        else if ((key).equals("") && ((diffDays > 120 & diffDays < 1580) && (vasCalendarModelDTO.getMileage() > 41000 & vasCalendarModelDTO.getMileage() < 52000))){
            Long to4_mileage = vasCalendarModelDTO.getMileage();
            Date to4_date = vasCalendarModelDTO.getDateRepair();
            vin = vasCalendarModelDTO.getVin();

            calendar.setTime(vasCalendarModelDTO.getDateRepair());
            calendar.add(Calendar.MONTH,12);
            plannedDate = calendar.getTime();

            vasCalendarJacRepository.updateToFour(vin, to4_date, to4_mileage, plannedDate);
        }
        //Попытка добавить ТО за рамками 1-4
        else if ((key).equals("") && diffDays > 1580 || (key).equals("") && vasCalendarModelDTO.getMileage() > 52000){
            LOGGER.log(Level.ERROR, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Попытка добавить ТО за рамками 1-4 в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
            //System.out.println("Попытка добавить ТО за рамками 1-4 в календаре клиента Jac, с номером кузова: "
            //        + vasCalendarModelDTO.getVin() + ". Номер заказ-наряда: " + vasCalendarModelDTO.getNumOrder());
        }
    }

    public void updateOldRowForCalendarJac(VasCalendarModelDTO vasCalendarModelDTO){
        // Работа с записью в календаре клиента (известный авто)

        String vin = vasCalendarModelDTO.getVin();
        VasCalendarJacModel vasCalendarJacModel;
        vasCalendarJacModel = vasCalendarJacRepository.findCardByVin(vin);
        Date repairDate = vasCalendarModelDTO.getDateRepair();

        HashMap<String,Long> calendarJacMileage = new HashMap<>();
        HashMap<String,Date> calendarJacDate = new HashMap<>();


        if (vasCalendarModelDTO.getVin().equals(vasCalendarJacModel.getVin())){
            calendarJacMileage.put("to0_mileage",vasCalendarJacModel.getToMileageZero());
            calendarJacMileage.put("to1_mileage",vasCalendarJacModel.getToMileageOne());
            calendarJacMileage.put("to2_mileage",vasCalendarJacModel.getToMileageTwo());
            calendarJacMileage.put("to3_mileage",vasCalendarJacModel.getToMileageThree());
            calendarJacMileage.put("to4_mileage",vasCalendarJacModel.getToMileageFour());
            calendarJacMileage.put("to5_mileage",vasCalendarJacModel.getToMileageFive());
            calendarJacMileage.put("to6_mileage",vasCalendarJacModel.getToMileageSex());
            calendarJacMileage.put("to7_mileage",vasCalendarJacModel.getToMileageSeven());
            calendarJacMileage.put("to8_mileage",vasCalendarJacModel.getToMileageEight());
            calendarJacMileage.put("to9_mileage",vasCalendarJacModel.getToMileageNine());
            calendarJacMileage.put("to10_mileage",vasCalendarJacModel.getToMileageTen());

            calendarJacDate.put("to0_date",vasCalendarJacModel.getToDateZero());
            calendarJacDate.put("to1_date",vasCalendarJacModel.getToDateOne());
            calendarJacDate.put("to2_date",vasCalendarJacModel.getToDateTwo());
            calendarJacDate.put("to3_date",vasCalendarJacModel.getToDateThree());
            calendarJacDate.put("to4_date",vasCalendarJacModel.getToDateFour());
            calendarJacDate.put("to5_date",vasCalendarJacModel.getToDateFive());
            calendarJacDate.put("to6_date",vasCalendarJacModel.getToDateSex());
            calendarJacDate.put("to7_date",vasCalendarJacModel.getToDateSeven());
            calendarJacDate.put("to8_date",vasCalendarJacModel.getToDateEight());
            calendarJacDate.put("to9_date",vasCalendarJacModel.getToDateNine());
            calendarJacDate.put("to10_date",vasCalendarJacModel.getToDateTen());
        }

        String[] keyGen = new String[1];
        keyGen[0] = "";
        String newMileageKey = "to0_mileage";

        String[] dateGen = new String[1];
        dateGen[0] = "";


        for (int i = 10; i >= 0; i--){
            if (calendarJacMileage.get("to"+ i +"_mileage") != null){
                keyGen[0] = "to"+ i +"_mileage";
                newMileageKey = "to"+ (i + 1) +"_mileage";
                break;
            }
        }

        for (int i = 10; i >= 0; i--){
            if (calendarJacDate.get("to"+ i +"_date") != null){
                dateGen[0] = "to"+ i +"_date";
                break;
            }
        }

        Long previousMileage = calendarJacMileage.get(keyGen[0]);
        Date previousDate = calendarJacDate.get(dateGen[0]);

        Date planDate = vasCalendarModelDTO.getSale();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(planDate);

        Calendar calendarTo = Calendar.getInstance();

        Date plannedDate;
        Duration diff = Duration.between(vasCalendarJacModel.getDateSale().toInstant(), repairDate.toInstant());
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToDateZero(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageZero(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateOne(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageOne(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateTwo(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageTwo(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateThree(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageThree(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateFour(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageFour(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateFive(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageFive(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateSex(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageSex(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateSeven(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageSeven(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateEight(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageEight(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateNine(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageNine(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
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
            vasCalendarJacModel.setPlannedDate(plannedDate);

            vasCalendarJacModel.setOwner(vasCalendarModelDTO.getOwner());
            vasCalendarJacModel.setPhone(vasCalendarModelDTO.getPhone());
            vasCalendarJacModel.setUpdateDate(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setPlannedDate(plannedDate);
            vasCalendarJacModel.setToDateTen(vasCalendarModelDTO.getDateRepair());
            vasCalendarJacModel.setToMileageTen(vasCalendarModelDTO.getMileage());
            vasCalendarJacModel.setMasterName(vasCalendarModelDTO.getMasterName());
            vasCalendarJacModel.setRemmark(null);
            vasCalendarJacModel.setActivity("deactivated");
            vasCalendarJacRepository.save(vasCalendarJacModel);

            LOGGER.log(Level.INFO, "Сотрудник: " + vasCalendarModelDTO.getMasterName() + ". Обновлена строка в календаре клиента Jac, с номером кузова: "
                    + vasCalendarModelDTO.getVin());
            //System.out.println("Обновлена строка в календаре клиента Jac, с номером кузова: "
            //        + vasCalendarModelDTO.getVin());
        }
    }

    public List<VasCalendarJacModel> findThisMonth() {
        return vasCalendarJacRepository.findThisMonth();
    }

    public List<VasCalendarJacModel> findThisMonthTO(Long id) {
        return vasCalendarJacRepository.findThisMonthTO(id);
    }

    public Object addRemmark(VasCalendarJacModel vasCalendarJacModel) {
        Long id = vasCalendarJacModel.getId();
        String remmark = vasCalendarJacModel.getRemmark();
        Date dateRemmark = new Date();
        
        LOGGER.log(Level.INFO, "Добавлен комментарий в карточке ТО календаря Jac: " + vasCalendarJacModel.getRemmark() + ". ID строки: " + vasCalendarJacModel.getId() + ".");
        return vasCalendarJacRepository.addRemmark(id, remmark, dateRemmark);
    }
}
