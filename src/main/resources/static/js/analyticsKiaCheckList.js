var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

//Аналитика ИТ - начало
function openTabsAnalyticsKiaEngineer(){

    // Количество закрытых проверок в текущий месяц
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            document.getElementById("countReportsKiaEngineer").innerHTML = reports;
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_engineer_kia/countReportAnalyticsCancel", true);
    xhttp.send();

    // Количество незавершённых проверок в текущий месяц
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            document.getElementById("countReportsKiaEngineerNotCancel").innerHTML = reports;
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_engineer_kia/countReportAnalyticsNotCancel", true);
    xhttp.send();

    // Средний балл по закрытым заявкам текущего месяца
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var generalIndicators = JSON.parse(this.responseText);
            var generalIndicatorTable = '<tr>\n' +
            '<td>Фамилия</td>\n' +
            '<td>Кол-во завершенных проверок</td>\n' +
            '<td>Средний балл</td>\n' +
            '</tr>\n';

            for (let i = 0; i < Object.keys(generalIndicators).length; i++){
                var generalIndicator = generalIndicators[i];
                generalIndicatorTable = generalIndicatorTable + '\n' +
                '<tr><td>' + generalIndicator.surnameReportKia + '</td>\n' +
                '<td>' + generalIndicator.countReportGeneralIndicator + '</td>\n' +
                '<td>' + generalIndicator.gpa + '</td></tr>';

                document.getElementById("analyticsKiaEngineerGeneral").innerHTML = generalIndicatorTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_engineer_kia/analyticsKiaEngineerGeneralIndicator", true);
    xhttp.send();

    // Список всех закрытых проверок
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var analyticsKiaEngineer = JSON.parse(this.responseText);
            var analyticsKiaEngineerTable = '<tr>\n' +
            '<td>Фамилия</td>\n' +
            '<td>№ З/Н</td>\n' +
            '<td>Дата завершения</td>\n' +
            '<td>Результат</td>\n' +
            '<td>Действия</td>\n' +
            '</tr>\n';

            for (let i = 0; i < analyticsKiaEngineer.length; i++){
                var analyticKiaEngineer = analyticsKiaEngineer[i];
                analyticsKiaEngineerTable = analyticsKiaEngineerTable + '\n' +
                '<tr><td>' + analyticKiaEngineer.surnameEngineerKia + '</td>\n' +
                '<td>' + analyticKiaEngineer.numOrderCheckKia + '</td>\n' +
                '<td>' + analyticKiaEngineer.dateSaveInspection + '</td>\n' + 
                '<td>' + analyticKiaEngineer.result + '</td>\n' + 
                '<td>'+'<button style="margin: 5px" onclick="openCkeckListForPrint('+analyticKiaEngineer.numOrderCheckKia+')" type="button"><i class="fa fa-file"></i></button></td></tr>';

                document.getElementById("analyticsKiaEngineer").innerHTML = analyticsKiaEngineerTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_engineer_kia/findAllCheckListCancel", true);
    xhttp.send();
};
 
function openCkeckListForPrint(numOrderCheckKia){
    document.getElementById("analyticForPrint").className = "popup open";
    
    // Количество закрытых проверок в текущий месяц с расшифровкой
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            
            for (let i = 0; i < reports.length; i++){
                var report = reports[i];

            document.getElementById("numOrderKia").innerHTML = report.numOrderCheckKia;
            document.getElementById("dateOrderKia").innerHTML = report.dateSaveInspection;
            document.getElementById("surnameOrderKia").innerHTML = report.surnameEngineerKia;
            document.getElementById("resultOrderKia").innerHTML = report.result;
            
            var text = "";
            if (report.receptionQuestion1 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion1 === 'NO'){
                text = 'Нет'
            } else if (report.receptionQuestion1 === 'NA'){
                text = 'Не применялось';
            }
            document.getElementById("receptionQuestion1").innerHTML = text;
            
            text = "";
            if (report.receptionQuestion2 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion2 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion2").innerHTML = text;

            text = "";
            if (report.receptionQuestion3 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion3 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion3").innerHTML = text;

            text = "";
            if (report.receptionQuestion4 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion4 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion4").innerHTML = text;

            text = "";
            if (report.receptionQuestion5 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion5 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion5").innerHTML = text;

            text = "";
            if (report.receptionQuestion6 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion6 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion6").innerHTML = text;

            text = "";
            if (report.receptionQuestion7 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion7 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion7").innerHTML = text;

            text = "";
            if (report.receptionQuestion8 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion8 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion8").innerHTML = text;

            text = "";
            if (report.receptionQuestion9 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion9 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion9").innerHTML = text;

            text = "";
            if (report.receptionQuestion10 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion10 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion10").innerHTML = text;

            text = "";
            if (report.receptionQuestion11 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion11 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion11").innerHTML = text;

            text = "";
            if (report.receptionQuestion12 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion12 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion12").innerHTML = text;

            text = "";
            if (report.receptionQuestion13 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion13 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion13").innerHTML = text;

            text = "";
            if (report.receptionQuestion14 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion14 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion14").innerHTML = text;

            text = "";
            if (report.receptionQuestion15 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion15 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion15").innerHTML = text;

            text = "";
            if (report.receptionQuestion16 === 'YES'){
                text = 'Да';
            } else if (report.receptionQuestion16 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("receptionQuestion16").innerHTML = text;


            text = "";
            if (report.inspectionQuestion1 === 'YES'){
                text = 'Да';
            } else if (report.inspectionQuestion1 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("inspectionQuestion1").innerHTML = text;
            
            text = "";
            if (report.inspectionQuestion2 === 'YES'){
                text = 'Да';
            } else if (report.inspectionQuestion2 === 'NO'){
                text = 'Нет'
            } else if (report.inspectionQuestion2 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("inspectionQuestion2").innerHTML = text;
            
            text = "";
            if (report.inspectionQuestion3 === 'YES'){
                text = 'Да';
            } else if (report.inspectionQuestion3 === 'NO'){
                text = 'Нет'
            } else if (report.inspectionQuestion3 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("inspectionQuestion3").innerHTML = text;
            
            text = "";
            if (report.inspectionQuestion4 === 'YES'){
                text = 'Да';
            } else if (report.inspectionQuestion4 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("inspectionQuestion4").innerHTML = text;
            
            text = "";
            if (report.inspectionQuestion5 === 'YES'){
                text = 'Да';
            } else if (report.inspectionQuestion5 === 'NO'){
                text = 'Нет'
            } else if (report.inspectionQuestion5 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("inspectionQuestion5").innerHTML = text;
            
            text = "";
            if (report.inspectionQuestion6 === 'YES'){
                text = 'Да';
            } else if (report.inspectionQuestion6 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("inspectionQuestion6").innerHTML = text;
            
            text = "";
            if (report.waitingQuestion1 === 'YES'){
                text = 'Да';
            } else if (report.waitingQuestion1 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("waitingQuestion1").innerHTML = text;
            
            text = "";
            if (report.waitingQuestion2 === 'YES'){
                text = 'Да';
            } else if (report.waitingQuestion2 === 'NO'){
                text = 'Нет'
            } else if (report.waitingQuestion2 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("waitingQuestion2").innerHTML = text;
            
            text = "";
            if (report.waitingQuestion3 === 'YES'){
                text = 'Да';
            } else if (report.waitingQuestion3 === 'NO'){
                text = 'Нет'
            } else if (report.waitingQuestion3 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("waitingQuestion3").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion1 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion1 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("issuingQuestion1").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion2 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion2 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("issuingQuestion2").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion3 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion3 === 'NO'){
                text = 'Нет'
            } else if (report.issuingQuestion3 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("issuingQuestion3").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion4 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion4 === 'NO'){
                text = 'Нет'
            } else if (report.issuingQuestion4 === 'NA'){
                text = 'Не применялось'
            }
            document.getElementById("issuingQuestion4").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion5 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion5 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("issuingQuestion5").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion6 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion6 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("issuingQuestion6").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion7 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion7 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("issuingQuestion7").innerHTML = text;
            
            text = "";
            if (report.issuingQuestion8 === 'YES'){
                text = 'Да';
            } else if (report.issuingQuestion8 === 'NO'){
                text = 'Нет'
            } 
            document.getElementById("issuingQuestion8").innerHTML = text;
            
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_engineer_kia/findAllCheckListCancelFromNum/${numOrderCheckKia}`, true);
    xhttp.send();
};
//Аналитика ИТ - конец

//Аналитика РемЗона - начало
function openTabsAnalyticsKiaMechanic(){

    // Количество закрытых проверок в текущий месяц
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            document.getElementById("countReportsKiaMechanical").innerHTML = reports;
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_mechanical_kia/countReportAnalyticsCancel", true);
    xhttp.send();

    // Количество незавершённых проверок в текущий месяц
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            document.getElementById("countReportsKiaMechanicalNotCancel").innerHTML = reports;
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_mechanical_kia/countReportAnalyticsNotCancel", true);
    xhttp.send();

    // Средний балл по закрытым заявкам текущего месяца
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var generalIndicators = JSON.parse(this.responseText);
            var generalIndicatorTable = '<tr>\n' +
            '<td>Фамилия</td>\n' +
            '<td>Кол-во завершенных проверок</td>\n' +
            '<td>Средний балл</td>\n' +
            '</tr>\n';

            for (let i = 0; i < Object.keys(generalIndicators).length; i++){
                var generalIndicator = generalIndicators[i];
                generalIndicatorTable = generalIndicatorTable + '\n' +
                '<tr><td>' + generalIndicator.surnameReportKia + '</td>\n' +
                '<td>' + generalIndicator.countReportGeneralIndicator + '</td>\n' +
                '<td>' + generalIndicator.gpa + '</td></tr>';

                document.getElementById("analyticsKiaMechanicalGeneral").innerHTML = generalIndicatorTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_mechanical_kia/analyticsKiaMechanicalGeneralIndicator", true);
    xhttp.send();

    // Список всех закрытых проверок
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var analyticsKiaMechanical = JSON.parse(this.responseText);
            var analyticsKiaMechanicalTable = '<tr>\n' +
            '<td>Фамилия</td>\n' +
            '<td>№ З/Н</td>\n' +
            '<td>Дата завершения</td>\n' +
            '<td>VIN</td>\n' +
            '<td>Результат</td>\n' +
            '<td>Действия</td>\n' +
            '</tr>\n';

            for (let i = 0; i < analyticsKiaMechanical.length; i++){
                var analyticKiaMechanical = analyticsKiaMechanical[i];
                analyticsKiaMechanicalTable = analyticsKiaMechanicalTable + '\n' +
                '<tr><td>' + analyticKiaMechanical.surnameMechanicalKia + '</td>\n' +
                '<td>' + analyticKiaMechanical.numOrderCheckKiaRepair + '</td>\n' +
                '<td>' + analyticKiaMechanical.dateSaveRepairInspection + '</td>\n' + 
                '<td>' + analyticKiaMechanical.mechanicalKiaVin + '</td>\n' + 
                '<td>' + analyticKiaMechanical.result + '</td>\n' + 
                '<td>'+'<button style="margin: 5px" onclick="openCkeckListForPrintMechanical('+analyticKiaMechanical.numOrderCheckKiaRepair+')" type="button"><i class="fa fa-file"></i></button></td></tr>';

                document.getElementById("analyticsKiaMechanical").innerHTML = analyticsKiaMechanicalTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_mechanical_kia/findAllCheckListCancel", true);
    xhttp.send();
};

function openCkeckListForPrintMechanical(numOrderCheckKiaRepair){

    document.getElementById("analyticForPrintMechanical").className = "popup open";
    
    // Количество закрытых проверок в текущий месяц с расшифровкой
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            
            for (let i = 0; i < reports.length; i++){
                var report = reports[i];
                
                document.getElementById("numOrderKiaMechanical").innerHTML = report.numOrderCheckKiaRepair;
                document.getElementById("dateOrderKiaMechanical").innerHTML = report.dateSaveRepairInspection;
                document.getElementById("surnameOrderKiaMechanical").innerHTML = report.surnameMechanicalKia;
                document.getElementById("vinOrderKiaMechanical").innerHTML = report.mechanicalKiaVin;
                document.getElementById("resultOrderKiaMechanical").innerHTML = report.result;
                
                // Первый лист - начало
                var text = "";
                if (report.repairQualityControl1 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl1 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl1").innerHTML = text;

                var text = "";
                if (report.repairQualityControl2 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl2 === 'NO'){
                    text = 'Нет'
                } else if (report.repairQualityControl2 === 'NA'){
                    text = 'Не применялось';
                }
                document.getElementById("repairQualityControl2").innerHTML = text;
                
                var text = "";
                if (report.repairQualityControl3 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl3 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl3").innerHTML = text;

                var text = "";
                if (report.repairQualityControl4 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl4 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl4").innerHTML = text;

                var text = "";
                if (report.repairQualityControl5 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl5 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl5").innerHTML = text;

                var text = "";
                if (report.repairQualityControl6 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl6 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl6").innerHTML = text;

                var text = "";
                if (report.repairQualityControl7 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl7 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl7").innerHTML = text;

                var text = "";
                if (report.repairQualityControl8 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl8 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl8").innerHTML = text;

                var text = "";
                if (report.repairQualityControl9 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl9 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl9").innerHTML = text;

                var text = "";
                if (report.repairQualityControl10 === 'YES'){
                    text = 'Да';
                } else if (report.repairQualityControl10 === 'NO'){
                    text = 'Нет'
                } 
                document.getElementById("repairQualityControl10").innerHTML = text;
                // Первый лист - конец
                // Второй лист - начало
                var text = "";
                if (report.parkingLotCheck1 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck1 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck1").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck2 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck2 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck2").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck3 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck3 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck3").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck4 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck4 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck4").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck5 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck5 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck5").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck6 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck6 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck6").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck7 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck7 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck7").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck8 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck8 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck8").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck9 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck9 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck9").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck10 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck10 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck10").innerHTML = text;

                var text = "";
                if (report.parkingLotCheck11 === 'YES'){
                    text = 'Норм.';
                } else if (report.parkingLotCheck11 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("parkingLotCheck1").innerHTML = text;
                // Второй лист - конец
                // Третий лист - начало
                var text = "";
                if (report.checkInMotion1 === 'YES'){
                    text = 'Норм.';
                } else if (report.checkInMotion1 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("checkInMotion1").innerHTML = text;

                var text = "";
                if (report.checkInMotion2 === 'YES'){
                    text = 'Норм.';
                } else if (report.checkInMotion2 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("checkInMotion2").innerHTML = text;

                var text = "";
                if (report.checkInMotion3 === 'YES'){
                    text = 'Норм.';
                } else if (report.checkInMotion3 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("checkInMotion3").innerHTML = text;

                var text = "";
                if (report.checkInMotion4 === 'YES'){
                    text = 'Норм.';
                } else if (report.checkInMotion4 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("checkInMotion4").innerHTML = text;
                // Третий лист - конец
                // Четвертый лист - начало
                var text = "";
                if (report.qualityControl1 === 'YES'){
                    text = 'Норм.';
                } else if (report.qualityControl1 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("qualityControl1").innerHTML = text;
                              
                document.getElementById("notesMasterReport").innerHTML = report.notesMaster;
                document.getElementById("notesWorkReport").innerHTML = report.notesWork;
                document.getElementById("explanationsWorkReport").innerHTML = report.explanationsWork;

                var text = "";
                if (report.qualityControl5 === 'YES'){
                    text = 'Норм.';
                } else if (report.qualityControl5 === 'NO'){
                    text = 'Деф.'
                } 
                document.getElementById("qualityControl5").innerHTML = text;
                // Четвертый лист - конец
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_mechanical_kia/findAllCheckListCancelFromNum/${numOrderCheckKiaRepair}`, true);
    xhttp.send();
};
//Аналитика РемЗона - конец

//Аналитика за период - начало
function openTabsanalyticsKiaPreviousPeriod(){
    
};
//Аналитика за период - конец

//Запуск функций
openTabsAnalyticsKiaEngineer();
openTabsAnalyticsKiaMechanic();
openTabsanalyticsKiaPreviousPeriod();