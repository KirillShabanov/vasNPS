var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"


/** Получение данных ИТ по периоду */
function periodCheckEngineer(){
    var periodCheckListEngineerSurname = document.getElementById('periodCheckListEngineerSurname').value;
    var periodCheckListEngineerDateFrom = document.getElementById('periodCheckListEngineerDateFrom').value;
    var periodCheckListEngineerDateBy = document.getElementById('periodCheckListEngineerDateBy').value;

    //Количество найденных совпадений
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            let countReportEngineer = reports.length;
            document.getElementById("countReportsKiaEngineerPeriod").innerHTML = countReportEngineer;

            if(countReportEngineer >= 1){
                document.getElementById('downaloadReportEngineerKia').style = "display: visible";
            } else {
                document.getElementById('downaloadReportEngineerKia').style = "display: none";
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_engineer_kia/countReportsKiaEngineerPeriod/${periodCheckListEngineerSurname}&${periodCheckListEngineerDateFrom}&${periodCheckListEngineerDateBy}`, true);
    xhttp.send();
}
//Скачать в EXCEL
function downaloadReportEngineerKiaButton(){
    var periodCheckListEngineerSurname = document.getElementById('periodCheckListEngineerSurname').value;
    var periodCheckListEngineerDateFrom = document.getElementById('periodCheckListEngineerDateFrom').value;
    var periodCheckListEngineerDateBy = document.getElementById('periodCheckListEngineerDateBy').value;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            window.location.href = "outputReports\\kiaEngineerReport\\Чек-лист ИТ за период.xlsx";  
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_engineer_kia/downaloadReportsKiaEngineerPeriod/${periodCheckListEngineerSurname}&${periodCheckListEngineerDateFrom}&${periodCheckListEngineerDateBy}`, true);
    xhttp.send();
    
}