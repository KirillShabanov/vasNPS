package com.home.MyWorkTime.service;

import com.home.MyWorkTime.entity.VasNpsModel;
import com.home.MyWorkTime.entity.VasNpsModelDTO;
import com.home.MyWorkTime.repository.VasManagerNpsRepository;
import com.home.MyWorkTime.repository.VasNpsRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private VasManagerNpsRepository vasManagerNpsRepository;

    @Autowired
    public VasNpsService(VasNpsRepository vasNpsRepository, VasManagerNpsRepository vasManagerNpsRepository) {
        this.vasNpsRepository = vasNpsRepository;
        this.vasManagerNpsRepository = vasManagerNpsRepository;
    }

    public VasNpsModel saveOrder(VasNpsModelDTO vasNpsModelDTO) {

        VasNpsModel vasNpsModel = new VasNpsModel();

        try  {
            // Достал Фамилию и Имя
            String clientName = vasNpsModelDTO.getClientFullName();
            String[] ownerSurname = clientName.split(" ");
            String ownerFullName = ownerSurname[0];
            String clientFullName = ownerSurname[1];
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
            String[] checkedArrAuto = new String[]{"а/м", "автомобиль",
                    "легковой", "автобус", "киа",
                    "шкода", "суперб", "рапид",
                    "октавия", "шевроле", "kиa",
                    "чери", "тигго", "хонда",
                    "форд", "транзит", "фольксваген",
                    "wv", "wolkswagen", "vw",
                    "фиат", "брава", "уаз",
                    "спортейдж", "сид", "самара",
                    "саманд", "cheri", "специальный",
                    "альфа", "ромео", "ваз",
                    "калина", "соренто", "пиканто"};

            String checkedModelWithReplaces = checkedModelWithOutSpace
                    .replace("Š", "S")
                    .toLowerCase()
                    .replace(checkedArrAuto[0],"")
                    .replace(checkedArrAuto[1], "")
                    .replace(checkedArrAuto[2],"")
                    .replace(checkedArrAuto[3],"")
                    .replace(checkedArrAuto[4],"kia")
                    .replace(checkedArrAuto[5],"skoda")
                    .replace(checkedArrAuto[6],"superb")
                    .replace(checkedArrAuto[7],"rapid")
                    .replace(checkedArrAuto[8],"octavia")
                    .replace(checkedArrAuto[9],"chevrolet")
                    .replace(checkedArrAuto[10],"kia")
                    .replace(checkedArrAuto[11],"chery")
                    .replace(checkedArrAuto[12],"tiggo")
                    .replace(checkedArrAuto[13],"honda")
                    .replace(checkedArrAuto[14],"ford")
                    .replace(checkedArrAuto[15],"transit")
                    .replace(checkedArrAuto[16],"volkswagen")
                    .replace(checkedArrAuto[17],"volkswagen")
                    .replace(checkedArrAuto[18],"volkswagen")
                    .replace(checkedArrAuto[19],"volkswagen")
                    .replace(checkedArrAuto[20],"fiat")
                    .replace(checkedArrAuto[21],"brava")
                    .replace(checkedArrAuto[22],"uaz")
                    .replace(checkedArrAuto[23],"sportage")
                    .replace(checkedArrAuto[24],"ceed")
                    .replace(checkedArrAuto[25],"samara")
                    .replace(checkedArrAuto[26],"samand")
                    .replace(checkedArrAuto[27],"chery")
                    .replace(checkedArrAuto[28],"")
                    .replace(checkedArrAuto[29],"alfa")
                    .replace(checkedArrAuto[30],"romeo")
                    .replace(checkedArrAuto[31],"vaz")
                    .replace(checkedArrAuto[32],"kalina")
                    .replace(checkedArrAuto[33],"sorento")
                    .replace(checkedArrAuto[34],"picanto")
                    .replace("  "," ")
                    .replace("[а-я]/()-+,","")
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
                    .replace(",","");

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

            String organisation = null;
            String department = null;

            String[] nameFromOrganisation = vasManagerNpsRepository.organisationName();
            for (String value : nameFromOrganisation) {
                if (masterName.equals(value)) {
                    organisation = "ВитебскАвтоСити";
                    break;
                } else {
                    organisation = "Джи-Моторс";
                }
            }

            String[] nameFromDepartmentTechnical = vasManagerNpsRepository.gradeNpsNameTech();
            for (String s : nameFromDepartmentTechnical) {
                if (masterName.equals(s)) {
                    department = "OTOA";
                    break;
                } else {
                    department = "Джи-Моторс";
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

            //Категория заказ-наряда
            String category = vasNpsModelDTO.getCategory();
            vasNpsModel.setCategory(category); //Категория для модели

            //Обработка даты закрытия заказ-наряда
            String dateOrderClose = vasNpsModelDTO.getDateOrderClose(); //Date!!!!
            SimpleDateFormat closeDate = new SimpleDateFormat("dd.MM.yyyy");
            Date dateOrder = closeDate.parse(dateOrderClose);

            vasNpsModel.setDate_order(dateOrder); //Дата закрытия заказ-наряда для модели

            } catch (java.text.ParseException e) {
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
        if (checkNumOrder == null){
            System.err.println("Добавлен новый заказ-наряд в базу данных");
            VasNpsModel savedOrder = vasNpsRepository.save(vasNpsModel);
            return VasNpsRepository.saveOrder(savedOrder);
        } else {
            System.err.println("Попытка добавления неактуального по номеру заказ-наряда!");
        }
        return null;
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

        vasNpsRepository.updateCallDate(numOrder, mailDate, adminName);
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
        vasNpsRepository.outgoingCall(numOrder, nps, adminComment, adminName, callStatus, outgoingCallDate);
        return null;
    }
}
