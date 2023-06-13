var restApiAddressNPS = "http://localhost:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

/** Функция полного сохранения Чек-листа аудита ИТ */
function saveCheckReportEngineerKia(){

    /** Слушатели */
    var numOrderCheckKia = document.getElementById("numOrderCheckKia").value;
    var surnameEngineerKia = document.getElementById("surnameEngineerKia").value;

    var receptionQuestion1 = document.querySelector('input[name="carReceptionQuestion1"]:checked').value;
    var receptionQuestion2 = document.querySelector('input[name="carReceptionQuestion2"]:checked').value;
    var receptionQuestion3 = document.querySelector('input[name="carReceptionQuestion3"]:checked').value;
    var receptionQuestion4 = document.querySelector('input[name="carReceptionQuestion4"]:checked').value;
    var receptionQuestion5 = document.querySelector('input[name="carReceptionQuestion5"]:checked').value;
    var receptionQuestion6 = document.querySelector('input[name="carReceptionQuestion6"]:checked').value;
    var receptionQuestion7 = document.querySelector('input[name="carReceptionQuestion7"]:checked').value;
    var receptionQuestion8 = document.querySelector('input[name="carReceptionQuestion8"]:checked').value;
    var receptionQuestion9 = document.querySelector('input[name="carReceptionQuestion9"]:checked').value;
    var receptionQuestion10 = document.querySelector('input[name="carReceptionQuestion10"]:checked').value;
    var receptionQuestion11 = document.querySelector('input[name="carReceptionQuestion11"]:checked').value;
    var receptionQuestion12 = document.querySelector('input[name="carReceptionQuestion12"]:checked').value;
    var receptionQuestion13 = document.querySelector('input[name="carReceptionQuestion13"]:checked').value;
    var receptionQuestion14 = document.querySelector('input[name="carReceptionQuestion14"]:checked').value;
    var receptionQuestion15 = document.querySelector('input[name="carReceptionQuestion15"]:checked').value;
    var receptionQuestion16 = document.querySelector('input[name="carReceptionQuestion16"]:checked').value;

    var inspectionQuestion1 = document.querySelector('input[name="carInspectionQuestion1"]:checked').value;
    var inspectionQuestion2 = document.querySelector('input[name="carInspectionQuestion2"]:checked').value;
    var inspectionQuestion3 = document.querySelector('input[name="carInspectionQuestion3"]:checked').value;
    var inspectionQuestion4 = document.querySelector('input[name="carInspectionQuestion4"]:checked').value;
    var inspectionQuestion5 = document.querySelector('input[name="carInspectionQuestion5"]:checked').value;
    var inspectionQuestion6 = document.querySelector('input[name="carInspectionQuestion6"]:checked').value;

    var waitingQuestion1 = document.querySelector('input[name="carWaitingQuestion1"]:checked').value;
    var waitingQuestion2 = document.querySelector('input[name="carWaitingQuestion2"]:checked').value;
    var waitingQuestion3 = document.querySelector('input[name="carWaitingQuestion3"]:checked').value;

    var issuingQuestion1 = document.querySelector('input[name="issuingCarQuestion1"]:checked').value;
    var issuingQuestion2 = document.querySelector('input[name="issuingCarQuestion2"]:checked').value;
    var issuingQuestion3 = document.querySelector('input[name="issuingCarQuestion3"]:checked').value;
    var issuingQuestion4 = document.querySelector('input[name="issuingCarQuestion4"]:checked').value;
    var issuingQuestion5 = document.querySelector('input[name="issuingCarQuestion5"]:checked').value;
    var issuingQuestion6 = document.querySelector('input[name="issuingCarQuestion6"]:checked').value;
    var issuingQuestion7 = document.querySelector('input[name="issuingCarQuestion7"]:checked').value;
    var issuingQuestion8 = document.querySelector('input[name="issuingCarQuestion8"]:checked').value;

    var dateInspection = new Date();
    var dateSaveInspection = dateInspection.toLocaleString();

    var inWork = "Cancel";
    if (receptionQuestion1 === "NP" || receptionQuestion2 === "NP"
    || receptionQuestion3 === "NP" || receptionQuestion4 === "NP"
    || receptionQuestion5 === "NP" || receptionQuestion6 === "NP"
    || receptionQuestion7 === "NP" || receptionQuestion8 === "NP"
    || receptionQuestion9 === "NP" || receptionQuestion10 === "NP"
    || receptionQuestion11 === "NP" || receptionQuestion12 === "NP"
    || receptionQuestion13 === "NP" || receptionQuestion14 === "NP"
    || receptionQuestion15 === "NP" || receptionQuestion16 === "NP"
    || inspectionQuestion1 === "NP" || inspectionQuestion2 === "NP"
    || inspectionQuestion3 === "NP" || inspectionQuestion4 === "NP"
    || inspectionQuestion5 === "NP" || inspectionQuestion6 === "NP"
    || waitingQuestion1 === "NP" || waitingQuestion2 === "NP"
    || waitingQuestion3 === "NP" || issuingQuestion1 === "NP"
    || issuingQuestion2 === "NP" || issuingQuestion3 === "NP"
    || issuingQuestion4 === "NP" || issuingQuestion5 === "NP"
    || issuingQuestion6 === "NP" || issuingQuestion7 === "NP"
    || issuingQuestion8 === "NP"){
        inWork = "NotCancel"
    }
    
    /** Создание JSON для базы */
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_check_list_engineer_kia/saveCheckListEngineerKia");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({
        "numOrderCheckKia":numOrderCheckKia, "surnameEngineerKia":surnameEngineerKia,
        "dateSaveInspection":dateSaveInspection, "inWork":inWork,
    
        "receptionQuestion1":receptionQuestion1, "receptionQuestion2":receptionQuestion2, 
        "receptionQuestion3":receptionQuestion3, "receptionQuestion4":receptionQuestion4,
        "receptionQuestion5":receptionQuestion5, "receptionQuestion6":receptionQuestion6,
        "receptionQuestion7":receptionQuestion7, "receptionQuestion8":receptionQuestion8,
        "receptionQuestion9":receptionQuestion9, "receptionQuestion10":receptionQuestion10,
        "receptionQuestion11":receptionQuestion11, "receptionQuestion12":receptionQuestion12,
        "receptionQuestion13":receptionQuestion13, "receptionQuestion14":receptionQuestion14,
        "receptionQuestion15":receptionQuestion15, "receptionQuestion16":receptionQuestion16,
    
        "inspectionQuestion1":inspectionQuestion1, "inspectionQuestion2":inspectionQuestion2,
        "inspectionQuestion3":inspectionQuestion3, "inspectionQuestion4":inspectionQuestion4,
        "inspectionQuestion5":inspectionQuestion5, "inspectionQuestion6":inspectionQuestion6,
    
        "waitingQuestion1":waitingQuestion1, "waitingQuestion2":waitingQuestion2,
        "waitingQuestion3":waitingQuestion3,
        
        "issuingQuestion1":issuingQuestion1, "issuingQuestion2":issuingQuestion2,
        "issuingQuestion3":issuingQuestion3, "issuingQuestion4":issuingQuestion4,
        "issuingQuestion5":issuingQuestion5, "issuingQuestion6":issuingQuestion6,
        "issuingQuestion7":issuingQuestion7, "issuingQuestion8":issuingQuestion8}));
    

    alert("Отправлено в базу!!!");   
    document.location.reload();
};


function updateCheckReportEngineerKia(numOrderCheckKia){
    /** Функция перезаписи сохранения Чек-листа аудита ИТ */

    /** Слушатели */
    var numOrderCheckKia = document.getElementById("numOrderCheckKia").value;
    var surnameEngineerKia = document.getElementById("surnameEngineerKia").value;

    var receptionQuestion1 = document.querySelector('input[name="carReceptionQuestion1"]:checked').value;
    var receptionQuestion2 = document.querySelector('input[name="carReceptionQuestion2"]:checked').value;
    var receptionQuestion3 = document.querySelector('input[name="carReceptionQuestion3"]:checked').value;
    var receptionQuestion4 = document.querySelector('input[name="carReceptionQuestion4"]:checked').value;
    var receptionQuestion5 = document.querySelector('input[name="carReceptionQuestion5"]:checked').value;
    var receptionQuestion6 = document.querySelector('input[name="carReceptionQuestion6"]:checked').value;
    var receptionQuestion7 = document.querySelector('input[name="carReceptionQuestion7"]:checked').value;
    var receptionQuestion8 = document.querySelector('input[name="carReceptionQuestion8"]:checked').value;
    var receptionQuestion9 = document.querySelector('input[name="carReceptionQuestion9"]:checked').value;
    var receptionQuestion10 = document.querySelector('input[name="carReceptionQuestion10"]:checked').value;
    var receptionQuestion11 = document.querySelector('input[name="carReceptionQuestion11"]:checked').value;
    var receptionQuestion12 = document.querySelector('input[name="carReceptionQuestion12"]:checked').value;
    var receptionQuestion13 = document.querySelector('input[name="carReceptionQuestion13"]:checked').value;
    var receptionQuestion14 = document.querySelector('input[name="carReceptionQuestion14"]:checked').value;
    var receptionQuestion15 = document.querySelector('input[name="carReceptionQuestion15"]:checked').value;
    var receptionQuestion16 = document.querySelector('input[name="carReceptionQuestion16"]:checked').value;

    var inspectionQuestion1 = document.querySelector('input[name="carInspectionQuestion1"]:checked').value;
    var inspectionQuestion2 = document.querySelector('input[name="carInspectionQuestion2"]:checked').value;
    var inspectionQuestion3 = document.querySelector('input[name="carInspectionQuestion3"]:checked').value;
    var inspectionQuestion4 = document.querySelector('input[name="carInspectionQuestion4"]:checked').value;
    var inspectionQuestion5 = document.querySelector('input[name="carInspectionQuestion5"]:checked').value;
    var inspectionQuestion6 = document.querySelector('input[name="carInspectionQuestion6"]:checked').value;

    var waitingQuestion1 = document.querySelector('input[name="carWaitingQuestion1"]:checked').value;
    var waitingQuestion2 = document.querySelector('input[name="carWaitingQuestion2"]:checked').value;
    var waitingQuestion3 = document.querySelector('input[name="carWaitingQuestion3"]:checked').value;

    var issuingQuestion1 = document.querySelector('input[name="issuingCarQuestion1"]:checked').value;
    var issuingQuestion2 = document.querySelector('input[name="issuingCarQuestion2"]:checked').value;
    var issuingQuestion3 = document.querySelector('input[name="issuingCarQuestion3"]:checked').value;
    var issuingQuestion4 = document.querySelector('input[name="issuingCarQuestion4"]:checked').value;
    var issuingQuestion5 = document.querySelector('input[name="issuingCarQuestion5"]:checked').value;
    var issuingQuestion6 = document.querySelector('input[name="issuingCarQuestion6"]:checked').value;
    var issuingQuestion7 = document.querySelector('input[name="issuingCarQuestion7"]:checked').value;
    var issuingQuestion8 = document.querySelector('input[name="issuingCarQuestion8"]:checked').value;

    var dateInspection = new Date();
    var dateSaveInspection = dateInspection.toLocaleString();

    var inWork = "Cancel";
    if (receptionQuestion1 === "NP" || receptionQuestion2 === "NP"
    || receptionQuestion3 === "NP" || receptionQuestion4 === "NP"
    || receptionQuestion5 === "NP" || receptionQuestion6 === "NP"
    || receptionQuestion7 === "NP" || receptionQuestion8 === "NP"
    || receptionQuestion9 === "NP" || receptionQuestion10 === "NP"
    || receptionQuestion11 === "NP" || receptionQuestion12 === "NP"
    || receptionQuestion13 === "NP" || receptionQuestion14 === "NP"
    || receptionQuestion15 === "NP" || receptionQuestion16 === "NP"
    || inspectionQuestion1 === "NP" || inspectionQuestion2 === "NP"
    || inspectionQuestion3 === "NP" || inspectionQuestion4 === "NP"
    || inspectionQuestion5 === "NP" || inspectionQuestion6 === "NP"
    || waitingQuestion1 === "NP" || waitingQuestion2 === "NP"
    || waitingQuestion3 === "NP" || issuingQuestion1 === "NP"
    || issuingQuestion2 === "NP" || issuingQuestion3 === "NP"
    || issuingQuestion4 === "NP" || issuingQuestion5 === "NP"
    || issuingQuestion6 === "NP" || issuingQuestion7 === "NP"
    || issuingQuestion8 === "NP"){
        inWork = "NotCancel"
    }

    /** Создание JSON для базы */
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("PUT", restApiAddressNPS + `vas_check_list_engineer_kia/updateCheckListEngineerKia/${numOrderCheckKia}`);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({
        "numOrderCheckKia":numOrderCheckKia, "surnameEngineerKia":surnameEngineerKia,
        "dateSaveInspection":dateSaveInspection, "inWork":inWork,

        "receptionQuestion1":receptionQuestion1, "receptionQuestion2":receptionQuestion2, 
        "receptionQuestion3":receptionQuestion3, "receptionQuestion4":receptionQuestion4,
        "receptionQuestion5":receptionQuestion5, "receptionQuestion6":receptionQuestion6,
        "receptionQuestion7":receptionQuestion7, "receptionQuestion8":receptionQuestion8,
        "receptionQuestion9":receptionQuestion9, "receptionQuestion10":receptionQuestion10,
        "receptionQuestion11":receptionQuestion11, "receptionQuestion12":receptionQuestion12,
        "receptionQuestion13":receptionQuestion13, "receptionQuestion14":receptionQuestion14,
        "receptionQuestion15":receptionQuestion15, "receptionQuestion16":receptionQuestion16,

        "inspectionQuestion1":inspectionQuestion1, "inspectionQuestion2":inspectionQuestion2,
        "inspectionQuestion3":inspectionQuestion3, "inspectionQuestion4":inspectionQuestion4,
        "inspectionQuestion5":inspectionQuestion5, "inspectionQuestion6":inspectionQuestion6,

        "waitingQuestion1":waitingQuestion1, "waitingQuestion2":waitingQuestion2,
        "waitingQuestion3":waitingQuestion3,
        
        "issuingQuestion1":issuingQuestion1, "issuingQuestion2":issuingQuestion2,
        "issuingQuestion3":issuingQuestion3, "issuingQuestion4":issuingQuestion4,
        "issuingQuestion5":issuingQuestion5, "issuingQuestion6":issuingQuestion6,
        "issuingQuestion7":issuingQuestion7, "issuingQuestion8":issuingQuestion8}));


    alert("Отправлено в базу!!!");   
    document.location.reload();
};
