var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"
/** Для доп полей */
function showFullReport(){
    document.getElementById('dop').style = "display";
    document.getElementById('dop1').style = "display";
    document.getElementById('dop2').style = "display";
    document.getElementById('dop3').style = "display";
}

/** Функция полного сохранения Чек-листа аудита Механика KIA */
function saveCheckReportMechanicalKia(){

    /** Слушатели */
    var numOrderCheckKiaRepair = document.getElementById("numOrderCheckKiaRepair").value;
    var surnameMechanicalKia = document.getElementById("surnameMechanicalKia").value;
    var mechanicalKiaVin = document.getElementById("mechanicalKiaVin").value;
    var notesMaster = document.getElementById("notesMaster").value;
    var notesWork = document.getElementById("notesWork").value;
    var explanationsWork = document.getElementById("explanationsWork").value;
    
    
    var repairQualityControl1 = document.querySelector('input[name="repairQualityControl1"]:checked').value;
    var repairQualityControl2 = document.querySelector('input[name="repairQualityControl2"]:checked').value;
    var repairQualityControl3 = document.querySelector('input[name="repairQualityControl3"]:checked').value;
    var repairQualityControl4 = document.querySelector('input[name="repairQualityControl4"]:checked').value;
    var repairQualityControl5 = document.querySelector('input[name="repairQualityControl5"]:checked').value;
    var repairQualityControl6 = document.querySelector('input[name="repairQualityControl6"]:checked').value;
    var repairQualityControl7 = document.querySelector('input[name="repairQualityControl7"]:checked').value;
    var repairQualityControl8 = document.querySelector('input[name="repairQualityControl8"]:checked').value;
    var repairQualityControl9 = document.querySelector('input[name="repairQualityControl9"]:checked').value;
    var repairQualityControl10 = document.querySelector('input[name="repairQualityControl10"]:checked').value;
    
    var parkingLotCheck1 = document.querySelector('input[name="parkingLotCheck1"]:checked').value;
    var parkingLotCheck2 = document.querySelector('input[name="parkingLotCheck2"]:checked').value;
    var parkingLotCheck3 = document.querySelector('input[name="parkingLotCheck3"]:checked').value;
    var parkingLotCheck4 = document.querySelector('input[name="parkingLotCheck4"]:checked').value;
    var parkingLotCheck5 = document.querySelector('input[name="parkingLotCheck5"]:checked').value;
    var parkingLotCheck6 = document.querySelector('input[name="parkingLotCheck6"]:checked').value;
    var parkingLotCheck7 = document.querySelector('input[name="parkingLotCheck7"]:checked').value;
    var parkingLotCheck8 = document.querySelector('input[name="parkingLotCheck8"]:checked').value;
    var parkingLotCheck9 = document.querySelector('input[name="parkingLotCheck9"]:checked').value;
    var parkingLotCheck10 = document.querySelector('input[name="parkingLotCheck10"]:checked').value;
    var parkingLotCheck11 = document.querySelector('input[name="parkingLotCheck11"]:checked').value;
    
    var checkInMotion1 = document.querySelector('input[name="checkInMotion1"]:checked').value;
    var checkInMotion2 = document.querySelector('input[name="checkInMotion2"]:checked').value;
    var checkInMotion3 = document.querySelector('input[name="checkInMotion3"]:checked').value;
    var checkInMotion4 = document.querySelector('input[name="checkInMotion4"]:checked').value;
    
    var qualityControl1 = document.querySelector('input[name="qualityControl1"]:checked').value;
    var qualityControl5 = document.querySelector('input[name="qualityControl5"]:checked').value;

    var dateRepairInspection = new Date();
    var dateSaveRepairInspection = dateRepairInspection.toLocaleString();

    var inWorkRepair = "Cancel";
    if (repairQualityControl1 === "NP" || repairQualityControl2 === "NP"
    || repairQualityControl3 === "NP" || repairQualityControl4 === "NP"
    || repairQualityControl5 === "NP" || repairQualityControl6 === "NP"
    || repairQualityControl7 === "NP" || repairQualityControl8 === "NP"
    || repairQualityControl9 === "NP" || repairQualityControl10 === "NP"
    || parkingLotCheck1 === "NP" || parkingLotCheck2 === "NP"
    || parkingLotCheck3 === "NP" || parkingLotCheck4 === "NP"
    || parkingLotCheck5 === "NP" || parkingLotCheck6 === "NP"
    || parkingLotCheck7 === "NP" || parkingLotCheck8 === "NP"
    || parkingLotCheck9 === "NP" || parkingLotCheck10 === "NP"
    || parkingLotCheck11 === "NP" || checkInMotion1 === "NP"
    || checkInMotion2 === "NP" || checkInMotion3 === "NP"
    || checkInMotion4 === "NP" || qualityControl1 === "NP"){
        inWorkRepair = "NotCancel"
    }

    /** Создание JSON для базы */
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_check_list_mechanical_kia/saveCheckListMechanicalKia");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"numOrderCheckKiaRepair":numOrderCheckKiaRepair, "surnameMechanicalKia":surnameMechanicalKia,
    "mechanicalKiaVin":mechanicalKiaVin, "inWorkRepair":inWorkRepair,
    "dateSaveRepairInspection":dateSaveRepairInspection, 
    "repairQualityControl1":repairQualityControl1,
    "repairQualityControl2":repairQualityControl2, "repairQualityControl3":repairQualityControl3,
    "repairQualityControl4":repairQualityControl4, "repairQualityControl5":repairQualityControl5,
    "repairQualityControl6":repairQualityControl6, "repairQualityControl7":repairQualityControl7,
    "repairQualityControl8":repairQualityControl8, "repairQualityControl9":repairQualityControl9,
    "repairQualityControl10":repairQualityControl10, 
    "parkingLotCheck1":parkingLotCheck1, "parkingLotCheck2":parkingLotCheck2,
    "parkingLotCheck3":parkingLotCheck3, "parkingLotCheck4":parkingLotCheck4,
    "parkingLotCheck5":parkingLotCheck5, "parkingLotCheck6":parkingLotCheck6,
    "parkingLotCheck7":parkingLotCheck7, "parkingLotCheck8":parkingLotCheck8,
    "parkingLotCheck9":parkingLotCheck9, "parkingLotCheck10":parkingLotCheck10,
    "parkingLotCheck11":parkingLotCheck11, 
    "checkInMotion1":checkInMotion1, "checkInMotion2":checkInMotion2,
    "checkInMotion3":checkInMotion3, "checkInMotion4":checkInMotion4,
    "qualityControl1":qualityControl1, "notesMaster":notesMaster,
    "notesWork":notesWork, "explanationsWork":explanationsWork,
    "qualityControl5":qualityControl5}));

    alert("Отправлено в базу!!!");   
    location.reload();
};


function updateCheckReportMechanicalKia(numOrderCheckKia){
    /** Функция перезаписи сохранения Чек-листа аудита Механика */

    /** Слушатели */
    var numOrderCheckKia = document.getElementById("numOrderCheckKiaRepair").value;
    var surnameMechanicalKia = document.getElementById("surnameMechanicalKia").value;
    var mechanicalKiaVin = document.getElementById("mechanicalKiaVin").value;
    var notesMaster = document.getElementById("notesMaster").value;
    var notesWork = document.getElementById("notesWork").value;
    var explanationsWork = document.getElementById("explanationsWork").value;
    
    
    var repairQualityControl1 = document.querySelector('input[name="repairQualityControl1"]:checked').value;
    var repairQualityControl2 = document.querySelector('input[name="repairQualityControl2"]:checked').value;
    var repairQualityControl3 = document.querySelector('input[name="repairQualityControl3"]:checked').value;
    var repairQualityControl4 = document.querySelector('input[name="repairQualityControl4"]:checked').value;
    var repairQualityControl5 = document.querySelector('input[name="repairQualityControl5"]:checked').value;
    var repairQualityControl6 = document.querySelector('input[name="repairQualityControl6"]:checked').value;
    var repairQualityControl7 = document.querySelector('input[name="repairQualityControl7"]:checked').value;
    var repairQualityControl8 = document.querySelector('input[name="repairQualityControl8"]:checked').value;
    var repairQualityControl9 = document.querySelector('input[name="repairQualityControl9"]:checked').value;
    var repairQualityControl10 = document.querySelector('input[name="repairQualityControl10"]:checked').value;
    
    var parkingLotCheck1 = document.querySelector('input[name="parkingLotCheck1"]:checked').value;
    var parkingLotCheck2 = document.querySelector('input[name="parkingLotCheck2"]:checked').value;
    var parkingLotCheck3 = document.querySelector('input[name="parkingLotCheck3"]:checked').value;
    var parkingLotCheck4 = document.querySelector('input[name="parkingLotCheck4"]:checked').value;
    var parkingLotCheck5 = document.querySelector('input[name="parkingLotCheck5"]:checked').value;
    var parkingLotCheck6 = document.querySelector('input[name="parkingLotCheck6"]:checked').value;
    var parkingLotCheck7 = document.querySelector('input[name="parkingLotCheck7"]:checked').value;
    var parkingLotCheck8 = document.querySelector('input[name="parkingLotCheck8"]:checked').value;
    var parkingLotCheck9 = document.querySelector('input[name="parkingLotCheck9"]:checked').value;
    var parkingLotCheck10 = document.querySelector('input[name="parkingLotCheck10"]:checked').value;
    var parkingLotCheck11 = document.querySelector('input[name="parkingLotCheck11"]:checked').value;
    
    var checkInMotion1 = document.querySelector('input[name="checkInMotion1"]:checked').value;
    var checkInMotion2 = document.querySelector('input[name="checkInMotion2"]:checked').value;
    var checkInMotion3 = document.querySelector('input[name="checkInMotion3"]:checked').value;
    var checkInMotion4 = document.querySelector('input[name="checkInMotion4"]:checked').value;
    
    var qualityControl1 = document.querySelector('input[name="qualityControl1"]:checked').value;
    var qualityControl5 = document.querySelector('input[name="qualityControl5"]:checked').value;

    var dateRepairInspection = new Date();
    var dateSaveRepairInspection = dateRepairInspection.toLocaleString();

    var inWorkRepair = "Cancel";
    if (repairQualityControl1 === "NP" || repairQualityControl2 === "NP"
    || repairQualityControl3 === "NP" || repairQualityControl4 === "NP"
    || repairQualityControl5 === "NP" || repairQualityControl6 === "NP"
    || repairQualityControl7 === "NP" || repairQualityControl8 === "NP"
    || repairQualityControl9 === "NP" || repairQualityControl10 === "NP"
    || parkingLotCheck1 === "NP" || parkingLotCheck2 === "NP"
    || parkingLotCheck3 === "NP" || parkingLotCheck4 === "NP"
    || parkingLotCheck5 === "NP" || parkingLotCheck6 === "NP"
    || parkingLotCheck7 === "NP" || parkingLotCheck8 === "NP"
    || parkingLotCheck9 === "NP" || parkingLotCheck10 === "NP"
    || parkingLotCheck11 === "NP" || checkInMotion1 === "NP"
    || checkInMotion2 === "NP" || checkInMotion3 === "NP"
    || checkInMotion4 === "NP" || qualityControl1 === "NP"){
        inWorkRepair = "NotCancel"
    }

    /** Создание JSON для базы */
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", restApiAddressNPS + `vas_check_list_mechanical_kia/updateCheckListMechanicalKia/${numOrderCheckKia}`);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({
    "numOrderCheckKiaRepair":numOrderCheckKia, "surnameMechanicalKia":surnameMechanicalKia,
    "mechanicalKiaVin":mechanicalKiaVin, "inWorkRepair":inWorkRepair,
    "dateSaveRepairInspection":dateSaveRepairInspection, 
    "repairQualityControl1":repairQualityControl1,
    "repairQualityControl2":repairQualityControl2, "repairQualityControl3":repairQualityControl3,
    "repairQualityControl4":repairQualityControl4, "repairQualityControl5":repairQualityControl5,
    "repairQualityControl6":repairQualityControl6, "repairQualityControl7":repairQualityControl7,
    "repairQualityControl8":repairQualityControl8, "repairQualityControl9":repairQualityControl9,
    "repairQualityControl10":repairQualityControl10, 
    "parkingLotCheck1":parkingLotCheck1, "parkingLotCheck2":parkingLotCheck2,
    "parkingLotCheck3":parkingLotCheck3, "parkingLotCheck4":parkingLotCheck4,
    "parkingLotCheck5":parkingLotCheck5, "parkingLotCheck6":parkingLotCheck6,
    "parkingLotCheck7":parkingLotCheck7, "parkingLotCheck8":parkingLotCheck8,
    "parkingLotCheck9":parkingLotCheck9, "parkingLotCheck10":parkingLotCheck10,
    "parkingLotCheck11":parkingLotCheck11, 
    "checkInMotion1":checkInMotion1, "checkInMotion2":checkInMotion2,
    "checkInMotion3":checkInMotion3, "checkInMotion4":checkInMotion4,
    "qualityControl1":qualityControl1, "notesMaster":notesMaster,
    "notesWork":notesWork, "explanationsWork":explanationsWork,
    "qualityControl5":qualityControl5}));


    alert("Отправлено в базу!!!");   
    document.location.reload();
};
