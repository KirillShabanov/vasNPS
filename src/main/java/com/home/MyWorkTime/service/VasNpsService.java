package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.GeeNpsModel;
import com.home.MyWorkTime.entity.VasCalendarModelDTO;
import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.entity.VasNpsModelDTO;
import com.home.MyWorkTime.repository.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;




@Slf4j
@Service
@NoArgsConstructor
public class VasNpsService {

    private VasNpsRepository vasNpsRepository;
    private GeeNpsRepository geeNpsRepository;
    private VasManagerNpsRepository vasManagerNpsRepository;
    private NullCategoryRepository nullCategoryRepository;
    private FixBrandModelRepository fixBrandModelRepository;
    private VasRedirectedCalendarService vasRedirectedCalendarService;

    private static final Logger LOGGER = Logger.getLogger(VasNpsService.class.getName());

    @Autowired
    public VasNpsService(VasNpsRepository vasNpsRepository,
                         GeeNpsRepository geeNpsRepository,
                         VasManagerNpsRepository vasManagerNpsRepository,
                         NullCategoryRepository nullCategoryRepository,
                         FixBrandModelRepository fixBrandModelRepository,
                         VasRedirectedCalendarService vasRedirectedCalendarService) {
        this.vasNpsRepository = vasNpsRepository;
        this.geeNpsRepository = geeNpsRepository;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
        this.nullCategoryRepository = nullCategoryRepository;
        this.fixBrandModelRepository = fixBrandModelRepository;
        this.vasRedirectedCalendarService = vasRedirectedCalendarService;
    }

    public VasNpsModel saveOrder(VasNpsModelDTO vasNpsModelDTO) {

        String[] notBase = nullCategoryRepository.nullCategoryFromBase();
        String checkedCategory = vasNpsModelDTO.getCategory();
        for (String s : notBase) {
            if (checkedCategory.equals(s)) {
                return null;
            }
        }

        VasNpsModel vasNpsModel = new VasNpsModel();

        try  {
            // Достал Фамилию и Имя
            String clientName = vasNpsModelDTO.getClientFullName();
            String[] ownerSurname = clientName.split(" ");
            String ownerFullName = ownerSurname[0];
            String clientFullName;//Имя для модели
            //Фамилия для модели
            if (ownerSurname.length > 1) {
                clientFullName = ownerSurname[1];
            } else {
                clientFullName = "";
            }
            vasNpsModel.setClient_surname(ownerFullName); //Фамилия для модели
            vasNpsModel.setClient_name(clientFullName); //Имя для модели

            //Обработка номера телефона
            ArrayList<String> phoneArr = new ArrayList<>();
            phoneArr.add(Arrays.toString(vasNpsModelDTO.getPhone1())
                    .replace("[","")
                    .replace("]","")
                    .replace("+","")
                    .replace(",", "").trim());

            String[] clientPhone = phoneArr.get(0).split(" ");
            String phone1 = clientPhone[0];
            String phone2 = "";

            if (clientPhone.length == 1) {
                clientPhone = new String[2];
                clientPhone[0] = phone1;
                clientPhone[1] = phone2;
            } else {
                phone2 = clientPhone[1]; //-Номер 2
            }

            if (!(phone1.isEmpty())){
                vasNpsModel.setPhone_1(phone2);
                vasNpsModel.setPhone_2("");
            }

            if (phone1.equals(phone2)){
                vasNpsModel.setPhone_1(phone1);
                vasNpsModel.setPhone_2("");
            }
            if (!(phone1.equals(phone2))){
                vasNpsModel.setPhone_1(phone1); //Номер телефона для модели
                vasNpsModel.setPhone_2(phone2); //Номер телефона для модели
            }

            //Обработка VIN
            ArrayList<String> vinArr = new ArrayList<>();
            vinArr.add(Arrays.toString(vasNpsModelDTO.getVin())
                    .replace("[","")
                    .replace("]","")
                    .replace(",", "")
                    .trim()
                    .toUpperCase());

            String[] vinCar = vinArr.get(0).split(" ");
            String vin1 = vinCar[0]; // Нужный VIN
            String vin2 = "";
            if (vinCar.length == 1){
                vinCar = new String[2];
                vinCar[0] = vin1;
                vinCar[1] = vin2;
            } else {
                vin2 = vinCar[1];
            }
            if (!(vin1.isEmpty())){
                vasNpsModel.setVehicle_identification_number(vin1);
            }

            //Для замены не нужных VIN, с исключением (если в карточке всего один vin)
            String check = vin1.substring(0,4);
            if (check.equals("XWEP")){
                vasNpsModel.setVehicle_identification_number(vin2);
                if (vin2.equals("")){
                    vasNpsModel.setVehicle_identification_number(vin1); //VIN для модели
                }
            }

            //Госс номер авто
            String regNum = vasNpsModelDTO.getRegNum();
            vasNpsModel.setReg_num(regNum); //Госс номер для модели

            //Обработка Бренда и Модели из модели (Приоритет Kia и Skoda)
            String checkedModel = vasNpsModelDTO.getModel();
            String checkedModelWithOutSpace = checkedModel.trim();


            HashMap<Integer, String> wordInBaseKey = new HashMap<>();
            HashMap<Integer, String> wordInBaseValue = new HashMap<>();

            String[] key = fixBrandModelRepository.keyFix();
            for (int i = 0; i < key.length; i++) {
                wordInBaseKey.put(i, key[i]);
            }
            String[] value = fixBrandModelRepository.valueFix();
            for (int i = 0; i < value.length; i++) {
                wordInBaseValue.put(i, value[i]);
            }

            HashMap<Integer, String> badBrand = new HashMap<>();
            String[] inputBrand = checkedModelWithOutSpace.toLowerCase(Locale.ROOT).split(" ");
            for (int i = 0; i < inputBrand.length; i++) {
                badBrand.put(i, inputBrand[i]);
            }

            StringBuilder sbTest = new StringBuilder();
            for (int i = 0; i < badBrand.size(); i++) {
                for (int j = 0; j < wordInBaseKey.size(); j++) {
                    if (badBrand.get(i).equals(wordInBaseKey.get(j))){
                        badBrand.replace(i,wordInBaseValue.get(j));
                    }
                }
                if (!(badBrand.get(i).trim().equals(""))) {
                    sbTest.append(badBrand.get(i).trim()).append(" ");
                } else {
                    sbTest.append(badBrand.get(i).trim());
                }
            }

            checkedModelWithOutSpace = sbTest.toString();

            String checkedModelWithReplaces = checkedModelWithOutSpace
                    .toUpperCase(Locale.ROOT)
                    .replace("Š", "S")
                    .toLowerCase()
                    .replace("_"," ")
                    .replace("  "," ")
                    .replaceAll("[а-я]","")
                    .trim();

            String[] checkedArrModel = checkedModelWithReplaces.split(" ");
            String[] checkedArrBrand = new String[3];
            if (checkedArrModel.length > 2) {
                checkedArrBrand[2] = checkedArrModel[2];
            } else {
                checkedArrBrand[2] = " ";
            }
            checkedArrBrand[0] = checkedArrModel[0];
            checkedArrBrand[1] = checkedArrModel[1];


            String brand = checkedArrBrand[0].substring(0,1).toUpperCase() + checkedArrBrand[0].substring(1);
            String model = (checkedArrBrand[1].substring(0,1).toUpperCase() + checkedArrBrand[1].substring(1) + " " + checkedArrBrand[2].toUpperCase())
                    .replace("(","")
                    .replace(")","")
                    .replace("-","")
                    .replace(",","")
                    .replace("_","")
                    .replace("/","")
                    .replace("+","");

            String model1 = checkedArrBrand[1].substring(0, 1).toUpperCase() + checkedArrBrand[1].substring(1);
            if (brand.equals("Skoda")){
                model = model1;
            }
            if (brand.equals("Kia")){
                model = model1;
            }

            String[] checkBrandSkoda = new String[] {"fabia", "octavia", "rapid", "kodiaq", "karoq", "roomster", "superb"};
            for (String skoda : checkBrandSkoda) {
                if (checkedArrBrand[0].equals(skoda)) {
                    String repairBrand = "skoda";
                    brand = repairBrand.substring(0,1).toUpperCase() + repairBrand.substring(1);
                    model = brand;
                    break;
                }
            }

            String[] checkBrandKia = new String[] {"rio", "sportage", "ceed", "sorento", "picanto", "carnival", "ka"};
            for (String kia : checkBrandKia){
                if (checkedArrBrand[0].equals(kia)){
                    String repairBrand = "kia";
                    brand = repairBrand.substring(0,1).toUpperCase() + repairBrand.substring(1);
                    model = brand;
                    break;
                }
            }

            vasNpsModel.setBrand(brand); //Бренд для модели
            vasNpsModel.setModel(model); //Модель авто для модели

            //ФИО приемщика
            String masterName = vasNpsModelDTO.getMasterName().replaceAll("[a-zA-Z,-/0-9]","").trim();
            vasNpsModel.setMaster_name(masterName); //Приемщик для модели

            String organisation;
            String department = null;

            //Обработка номера заказ-наряда
            String numOrderChecked = vasNpsModelDTO.getNumOrder();
            String orderCheck = numOrderChecked.toUpperCase()
                    .replaceAll("[А-Я]", "")
                    .replace("-", "");
            int arrayLength;
            char[] array = orderCheck.toCharArray();
            arrayLength = array.length;
            int firstNonZeroAt = 0;
            for(int i=0; i<array.length; i++) {
                if(!String.valueOf(array[i]).equalsIgnoreCase("0")) {
                    firstNonZeroAt = i;
                    break;
                }
            }
            char [] newArray = Arrays.copyOfRange(array, firstNonZeroAt,arrayLength);
            String numOrder = new String(newArray);
            vasNpsModel.setNum_order(numOrder); //Номер заказ-наряда для модели

            //Определение организации
            String orderCheckForOrganisation = numOrderChecked.toLowerCase(Locale.ROOT)
                    .replaceAll("[0-9]", "")
                    .replace("-", "");

            if (orderCheckForOrganisation.equals("да")){
                organisation = "ВитебскАвтоСити";
            } else {
                organisation = "Джи-Моторс";
            }

            //Разделение по департаментам
            String[] nameFromDepartmentTechnical = vasManagerNpsRepository.gradeNpsNameTech();
            for (String s : nameFromDepartmentTechnical) {
                if (masterName.equals(s)) {
                    department = "OTOA";
                    break;
                }
            }

            String[] nameFromDepartmentBodyRepair = vasManagerNpsRepository.gradeNpsNameBodyRepair();
            for (String s : nameFromDepartmentBodyRepair) {
                if (masterName.equals(s)) {
                    department = "МКЦ";
                    break;
                }
            }
            vasNpsModel.setOrganisation(organisation); //Название организации для модели
            vasNpsModel.setDepartment(department); //Департамент для модели

            //Пробег авто
            String promMileage = vasNpsModelDTO.getMileage();
            Long mileage = Long.parseLong(promMileage);
            vasNpsModel.setMileage(mileage); //Пробег авто

            //ID Клиента
            String idClientWrong = vasNpsModelDTO.getIdClient();
            String idClientWithOutSpace = idClientWrong
                    .trim();
            String idClient = idClientWithOutSpace.substring(idClientWithOutSpace.length() - 6);
            vasNpsModel.setIdClient(idClient); //ID Клиента для модели

            //Календарь клиента - ДОБАВИТЬ МЕТОДЫ РАБОТЫ С КАЛЕНДАРЕМ КЛИЕНТА
            String calendarClient = vasNpsModelDTO.getCalendarClient();
            vasNpsModel.setCalendarClient(calendarClient); //Ключ календаря клиента для модели

            //Обработка года выпуска
            String yearRelease;
            String year = vasNpsModelDTO.getYearRelease();
            String[] yearChecked = year.split("");
            if (yearChecked.length == 5){
                yearRelease = yearChecked[0] + yearChecked[2] + yearChecked[3] + yearChecked[4];
            } else {
                yearRelease = "Нет данных";
            }
            vasNpsModel.setYear_release(yearRelease); //Год выпуска для модели

            //Категория заказ-наряда
            String category = vasNpsModelDTO.getCategory();
            vasNpsModel.setCategory(category); //Категория для модели

            //Обработка даты закрытия заказ-наряда
            String dateOrderClose = vasNpsModelDTO.getDateOrderClose(); //Date!!!!
            SimpleDateFormat closeDate = new SimpleDateFormat("dd.MM.yyyy");
            Date dateOrder = closeDate.parse(dateOrderClose);

            vasNpsModel.setDate_order(dateOrder); //Дата закрытия заказ-наряда для модели

            //Направляю в календарь
            if (calendarClient.equals("selected")){
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                Date sale = format.parse(vasNpsModelDTO.getDateSale());

                VasCalendarModelDTO vasCalendarModelDTO = new VasCalendarModelDTO();
                vasCalendarModelDTO.setNumOrder(numOrder);
                vasCalendarModelDTO.setOwner(vasNpsModelDTO.getClientFullName());
                vasCalendarModelDTO.setBrand(brand);
                vasCalendarModelDTO.setModel(model);
                vasCalendarModelDTO.setVin(vin1);
                vasCalendarModelDTO.setYearRelease(yearRelease);
                vasCalendarModelDTO.setSale(sale);

                vasCalendarModelDTO.setPhone(vasNpsModel.getPhone_1() + " / " + vasNpsModel.getPhone_2());
                vasCalendarModelDTO.setDateRepair(dateOrder);
                vasCalendarModelDTO.setMileage(mileage);
                vasCalendarModelDTO.setMasterName(masterName);

                //vasRedirectedCalendarService.redirectedCalendar(vasCalendarModelDTO);
            }

            } catch (ParseException e) {
            e.printStackTrace();
        }


        Date dateOrder = vasNpsModel.getDate_order();
        Date dateCall = new Date();
        dateCall.setTime(dateOrder.getTime() + 2 * 24 * 60 * 60 * 1000);


        vasNpsModel.setMail_date(dateCall); //Дата звонка для модели
        String call = "not call";
        vasNpsModel.setCall_status(call);
        String numOrder = vasNpsModel.getNum_order();
        String checkNumOrder = vasManagerNpsRepository.checkedNumOrder(numOrder);
        if (vasNpsModel.getOrganisation().equals("ВитебскАвтоСити")) {
            if (checkNumOrder == null){
                System.out.println("Организация: " + vasNpsModel.getOrganisation() + ". " + "Сотрудник: " + vasNpsModel.getMaster_name() + ". " + "Добавлен новый з/н: " + numOrder);
                LOGGER.log(Level.INFO,"Организация: " + vasNpsModel.getOrganisation() + ". " + "Сотрудник: " + vasNpsModel.getMaster_name() + ". " + "Добавлен новый з/н: " + numOrder);
                VasNpsModel savedOrder = vasNpsRepository.save(vasNpsModel);
                return VasNpsRepository.saveOrder(savedOrder);
            } else {
                //System.out.println("Организация: " + vasNpsModel.getOrganisation() + ". " + "Сотрудник: " + vasNpsModel.getMaster_name() + ". " + "Попытка добавления неактуального з/н: " + numOrder);
                LOGGER.log(Level.INFO,"Организация: " + vasNpsModel.getOrganisation() + ". " + "Сотрудник: " + vasNpsModel.getMaster_name() + ". " + "Попытка добавления неактуального з/н: " + numOrder);
            }
        } else {
            saveOrderGee(vasNpsModel);
        }
        return null;
    }

    public void saveOrderGee(VasNpsModel vasNpsModel) {

        GeeNpsModel geeNpsModel = new GeeNpsModel();
        geeNpsModel.setClient_name(vasNpsModel.getClient_name());
        geeNpsModel.setClient_surname(vasNpsModel.getClient_surname());
        geeNpsModel.setPhone_1(vasNpsModel.getPhone_1());
        geeNpsModel.setPhone_2(vasNpsModel.getPhone_2());
        geeNpsModel.setVehicle_identification_number(vasNpsModel.getVehicle_identification_number());
        geeNpsModel.setReg_num(vasNpsModel.getReg_num());
        geeNpsModel.setBrand(vasNpsModel.getBrand());
        geeNpsModel.setModel(vasNpsModel.getModel());
        geeNpsModel.setYear_release(vasNpsModel.getYear_release());
        geeNpsModel.setNum_order(vasNpsModel.getNum_order());
        geeNpsModel.setOrganisation(vasNpsModel.getOrganisation());
        geeNpsModel.setDepartment(vasNpsModel.getDepartment());
        geeNpsModel.setCategory(vasNpsModel.getCategory());
        geeNpsModel.setMaster_name(vasNpsModel.getMaster_name());
        geeNpsModel.setNps(vasNpsModel.getNps());
        geeNpsModel.setMail_date(vasNpsModel.getMail_date());
        geeNpsModel.setCall_status(vasNpsModel.getCall_status());
        geeNpsModel.setOutgoing_call_date(vasNpsModel.getOutgoing_call_date());
        geeNpsModel.setAdmin_name(vasNpsModel.getAdmin_name());
        geeNpsModel.setAdmin_comment(vasNpsModel.getAdmin_comment());
        geeNpsModel.setMileage(vasNpsModel.getMileage());
        geeNpsModel.setCalendarClient(vasNpsModel.getCalendarClient());
        geeNpsModel.setDate_order(vasNpsModel.getDate_order());
        geeNpsModel.setIdClient(vasNpsModel.getIdClient());

        String numOrderGee = geeNpsModel.getNum_order();

        String checkNumOrderGee = geeNpsRepository.checkedNumOrderGee(numOrderGee);
        if (checkNumOrderGee == null) {
            System.out.println("Организация: " + geeNpsModel.getOrganisation() + ". " + "Сотрудник: " + geeNpsModel.getMaster_name() + ". " + "Добавлен новый з/н: " + geeNpsModel.getNum_order());
            LOGGER.log(Level.INFO,"Организация: " + geeNpsModel.getOrganisation() + ". " + "Сотрудник: " + geeNpsModel.getMaster_name() + ". " + "Добавлен новый з/н: " + geeNpsModel.getNum_order());
            geeNpsRepository.save(geeNpsModel);
        } else {
            System.out.println("Организация: " + geeNpsModel.getOrganisation() + ". " + "Сотрудник: " + vasNpsModel.getMaster_name() + ". " + "Попытка добавления неактуального з/н: " + geeNpsModel.getNum_order());
            LOGGER.log(Level.INFO,"Организация: " + geeNpsModel.getOrganisation() + ". " + "Сотрудник: " + vasNpsModel.getMaster_name() + ". " + "Попытка добавления неактуального з/н: " + geeNpsModel.getNum_order());
        }
    }

    public VasNpsModel updateCallDate(VasNpsModelDTO vasNpsModelDTO) {

        //Обработка номера заказ-наряда
        String numOrderChecked = vasNpsModelDTO.getNumOrder();
        String orderCheck = numOrderChecked.toUpperCase()
                .replaceAll("[А-Я]", "")
                .replace("-", "");
        int arrayLength;
        char[] array = orderCheck.toCharArray();
        arrayLength = array.length;
        int firstNonZeroAt = 0;
        for(int i=0; i<array.length; i++) {
            if(!String.valueOf(array[i]).equalsIgnoreCase("0")) {
                firstNonZeroAt = i;
                break;
            }
        }
        char [] newArray = Arrays.copyOfRange(array, firstNonZeroAt,arrayLength);
        String numOrder = new String(newArray);


        //Обработка даты звонка
        String checkCallDate = vasNpsModelDTO.getCallDate(); //Date!!!!
        SimpleDateFormat callDate = new SimpleDateFormat("dd.MM.yyyy");
        Date callDates = null;
        try {
            callDates = callDate.parse(checkCallDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date mailDate = new Date();
        if (callDates != null) {
            mailDate.setTime(callDates.getTime() - 24 * 60 * 60 * 1000);
        }

        String adminName = vasNpsModelDTO.getAdminName();

        //Определение организации
        String organisation;
        String orderCheckForOrganisation = numOrderChecked.toLowerCase(Locale.ROOT)
                .replaceAll("[0-9]", "")
                .replace("-", "");

        if (orderCheckForOrganisation.equals("да")){
            organisation = "ВитебскАвтоСити";
            vasNpsRepository.updateCallDate(numOrder, mailDate, adminName);
        } else {
            organisation = "Джи-Моторс";
            geeNpsRepository.updateCallDateGee(numOrder, mailDate, adminName);
        }

        System.out.println("Организация: " + organisation + ". " + "Сотрудник: " + adminName + ". " + "Изменена дата звонка в з/н: " + numOrder);
        LOGGER.log(Level.INFO,"Организация: " + organisation + ". " + "Сотрудник: " + adminName + ". " + "Изменена дата звонка в з/н: " + numOrder);
        return null;
    }

    public VasNpsModel outgoingCall(VasNpsModelDTO vasNpsModelDTO) {

        //Обработка номера заказ-наряда
        String numOrderChecked = vasNpsModelDTO.getNumOrder();
        String orderCheck = numOrderChecked.toUpperCase()
                .replaceAll("[А-Я]", "")
                .replace("-", "");
        int arrayLength;
        char[] array = orderCheck.toCharArray();
        arrayLength = array.length;
        int firstNonZeroAt = 0;
        for(int i=0; i<array.length; i++) {
            if(!String.valueOf(array[i]).equalsIgnoreCase("0")) {
                firstNonZeroAt = i;
                break;
            }
        }
        char [] newArray = Arrays.copyOfRange(array, firstNonZeroAt,arrayLength);
        String numOrder = new String(newArray);

        int nps = vasNpsModelDTO.getNps();
        String adminComment = vasNpsModelDTO.getAdminComment();
        String adminName = vasNpsModelDTO.getAdminName();
        String callStatus = "call";
        Date outgoingCallDate = new Date();


        //Определение организации
        String organisation;
        String orderCheckForOrganisation = numOrderChecked.toLowerCase(Locale.ROOT)
                .replaceAll("[0-9]", "")
                .replace("-", "");
        if (orderCheckForOrganisation.equals("да")){
            organisation = "ВитебскАвтоСити";
            vasNpsRepository.outgoingCall(numOrder, nps, adminComment, adminName, callStatus, outgoingCallDate);
        } else {
            organisation = "Джи-Моторс";
            geeNpsRepository.outgoingCallGee(numOrder, nps, adminComment, adminName, callStatus, outgoingCallDate);
        }

        System.out.println("Организация: " + organisation + ". " + "Сотрудник: " + adminName + ". " + "Закрыт NPS по з/н: " + numOrder);
        LOGGER.log(Level.INFO,"Организация: " + organisation + ". " + "Сотрудник: " + adminName + ". " + "Закрыт NPS по з/н: " + numOrder);

        return null;
    }
}
