var restApiAddressNPS = "http://localhost:8080/";

function countReports(){
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            document.getElementById("countReports").innerHTML = reports;
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vasFullReportNps/countReport", true);
    xhttp.send();
}
countReports();

function showOrderKia(){
    var numOrder = document.getElementById('numOrder').value;
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            console.log(reports);
            if (reports.length == 0){
                document.getElementById('reportsList').style = 'display: none;';
                alert("Проверьте номер заказ-наряда!")
            } else {
                document.getElementById('reportsList').style = 'visibility: visible';
            }
            var reportTable = '<tr>\n' +
            '<td>ФИ</td>\n' +
            '<td>Категория</td>\n' +
            '<td>VIN</td>\n'+
            '<td>Телефон</td>\n'+
            '<td>Перезвонить: </td>\n'+
            '</tr>\n';
            for (let i=0; i<reports.length; i++){
                var report = reports[i];
                reportTable = reportTable + '\n' +
                '<tr><td>'+report.client_name+" "+report.client_surname+'</td>\n' +
                '<td>'+report.category+'</td>\n' +
                '<td>'+report.vehicle_identification_number+'</td>\n' +
                '<td>'+report.phone_1+'</td>\n'+
                '<td><input id="callDate" type="date" style="margin-left: 10px; width: 100px; background-color: grey;">\n' +
                '<button id="updateCallDate_btn" onclick="updateCallDate()" class="btn_user" style="margin: 1px; display: none;" type="submit">Сохранить</button></td>\n' +
                '</tr>\n';

                document.getElementById("reportsList").innerHTML = reportTable;
                document.getElementById("mileageDQ030").innerHTML = report.mileage + " км.";

                document.getElementById("vin").innerHTML = report.vehicle_identification_number;
                document.getElementById("dQ030").innerHTML = report.mileage;
                document.getElementById("idClient").innerHTML = report.idClient;

                if (report.call_status != 'call' && report.brand == 'Kia'){
                    document.getElementById('updateCallDate_btn').style = 'visibility: visible';
                    document.getElementById('saveReportKia_btn').style = 'visibility: visible; margin-left: 550px;';
                } else {
                    document.getElementById('saveReportKia_btn').style = 'display: none;';
                    alert("По данному заказ-наряду опрос проведен ранее!")
                }
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vasFullReportNps/numOrder/${numOrder}`, true);
    xhttp.send();
}

//Присылает как 1С
function updateCallDate(){
    var numOrder = "да-000000" + document.getElementById('numOrder').value; //Обход для определения организации ВАС или ДЖИ
    var adminName = "Воронова Александра Юрьевна";
    var callInput = document.getElementById('callDate').value;
    var callDate = new Date(callInput).toLocaleDateString();
    
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_nps/updateCallDate");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"numOrder":numOrder, "adminName":adminName, "callDate":callDate}));
        
    alert("Дата опроса изменена.");

    document.location.reload();
}

//Для просмотра выбранных элементов
function bq010(){
    var bq010 = document.getElementsByName('BQ010');
    
    for (var i=0; i<bq010.length; i++){
        bq010[i].onchange = listenerBq010;
    } 
    function listenerBq010(){
        var gradeBq010 = this.value;
        document.getElementById("bQ010").innerHTML = gradeBq010;
    }
}
bq010(); //Для запуска слушателя

function bq020(){
    var bq020 = document.getElementsByName('BQ020');
    
    for (var i=0; i<bq020.length; i++){
        bq020[i].onchange = listenerBq020;
    } 
    function listenerBq020(){
        var gradeBq020 = this.value;
        document.getElementById("bQ020").innerHTML = gradeBq020;

        if (gradeBq020 == 1){
            document.getElementById('bq040').style = 'visibility: visible'; //Изменение видимости комментария
        }

        if (gradeBq020 == 2){
            document.getElementById('bq030').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq020(); //Для запуска слушателя

function bq030(){
    var bq030 = document.getElementsByName('BQ030');
    
    for (var i=0; i<bq030.length; i++){
        bq030[i].onchange = listenerBq030;
    } 
    function listenerBq030(){
       var gradeBq030 = this.value;
       document.getElementById("bQ030").innerHTML = gradeBq030;

        if (gradeBq030 == 6){
            document.getElementById('bq030_comment').style = 'visibility: visible'; //Изменение видимости комментария
        }

        if (gradeBq030 >= 1){
            document.getElementById('bq040').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq030(); //Для запуска слушателя

function bq040(){
    var bq040 = document.getElementsByName('BQ040');
    
    for (var i=0; i<bq040.length; i++){
        bq040[i].onchange = listenerBq040;
    } 
    function listenerBq040(){
        var gradeBq040 = this.value;
        document.getElementById("bQ040").innerHTML = gradeBq040;

        var gradeBq010 = document.getElementById('bQ010').innerText; //Видимость ранее внесенного текста
        

        if (gradeBq010 <= 8 && gradeBq040 <= 8){
            document.getElementById('bq050').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq050_1').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq050_2').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq050_3').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq050_4').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq050_5').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq050_6').style = 'visibility: visible'; //Изменение видимости комментария
            document.getElementById('bq060').style = 'visibility: visible'; //Изменение видимости комментария
        }

        if (gradeBq010 >= 9 || gradeBq040 >= 9){
            document.getElementById('bq070').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq040(); //Для запуска слушателя

function bq050(){
    var BQ050_1_3 = document.getElementsByName('BQ050_1_3');
    var BQ050_4_8 = document.getElementsByName('BQ050_4_8');
    var BQ050_9_13 = document.getElementsByName('BQ050_9_13');
    var BQ050_14_17 = document.getElementsByName('BQ050_14_17');
    var BQ050_18_21 = document.getElementsByName('BQ050_18_21');
    var BQ050_98_99 = document.getElementsByName('BQ050_98_99');
    
    for (var i=0; i<BQ050_1_3.length; i++){
        BQ050_1_3[i].onchange = listenerBQ050_1_3;
    } 
    function listenerBQ050_1_3(){
        var gradeBQ050_1_3 = this.value;
        document.getElementById("bQ050_1_3").innerHTML = gradeBQ050_1_3;

        if (gradeBQ050_1_3 == 3){
            document.getElementById('bq050_comment_1_3').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }

    for (var i=0; i<BQ050_4_8.length; i++){
        BQ050_4_8[i].onchange = listenerBQ050_4_8;
    } 
    function listenerBQ050_4_8(){
        var gradeBQ050_4_8 = this.value;
        document.getElementById("bQ050_4_8").innerHTML = gradeBQ050_4_8;

        if (gradeBQ050_4_8 == 8){
            document.getElementById('bq050_comment_4_8').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }

    for (var i=0; i<BQ050_9_13.length; i++){
        BQ050_9_13[i].onchange = listenerBQ050_9_13;
    } 
    function listenerBQ050_9_13(){
        var gradeBQ050_9_13 = this.value;
        document.getElementById("bQ050_9_13").innerHTML = gradeBQ050_9_13;

        if (gradeBQ050_9_13 == 13){
            document.getElementById('bq050_comment_9_13').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }

    for (var i=0; i<BQ050_14_17.length; i++){
        BQ050_14_17[i].onchange = listenerBQ050_14_17;
    } 
    function listenerBQ050_14_17(){
        var gradeBQ050_14_17 = this.value;
        document.getElementById("bQ050_14_17").innerHTML = gradeBQ050_14_17;

        if (gradeBQ050_14_17 == 17){
            document.getElementById('bq050_comment_14_17').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }

    for (var i=0; i<BQ050_18_21.length; i++){
        BQ050_18_21[i].onchange = listenerBQ050_18_21;
    } 
    function listenerBQ050_18_21(){
        var gradeBQ050_18_21 = this.value;
        document.getElementById("bQ050_18_21").innerHTML = gradeBQ050_18_21;

        if (gradeBQ050_18_21 == 21){
            document.getElementById('bq050_comment_18_21').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }

    for (var i=0; i<BQ050_98_99.length; i++){
        BQ050_98_99[i].onchange = listenerBQ050_98_99;
    } 
    function listenerBQ050_98_99(){
        var gradeBQ050_98_99 = this.value;
        document.getElementById("bQ050_98_99").innerHTML = gradeBQ050_98_99;

        if (gradeBQ050_98_99 == 98){
            document.getElementById('bq060').style = 'display: none'; //Изменение видимости комментария
            document.getElementById('bq070').style = 'visibility: visible'; //Изменение видимости комментария
        }

        if (gradeBQ050_98_99 == 99){
            document.getElementById('bq050_comment_98_99').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq050(); //Для запуска слушателя

function bq060(){
    var bq060 = document.getElementsByName('BQ060');
    
    for (var i=0; i<bq060.length; i++){
        bq060[i].onchange = listenerBq060;
    } 
    function listenerBq060(){
       var gradeBq060 = this.value;
       document.getElementById("bQ060").innerHTML = gradeBq060;

        if (gradeBq060 > 0){
            document.getElementById('bq070').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq060(); //Для запуска слушателя

function bq070(){
    var bq070 = document.getElementsByName('BQ070');
    
    for (var i=0; i<bq070.length; i++){
        bq070[i].onchange = listenerBq070;
    } 
    function listenerBq070(){
       var gradeBq070 = this.value;
       document.getElementById("bQ070").innerHTML = gradeBq070;

        if (gradeBq070 <= 8){
            document.getElementById('bq080').style = 'visibility: visible'; //Изменение видимости комментария
        }
        if (gradeBq070 > 8){
            document.getElementById('se300').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq070(); //Для запуска слушателя

function bq080(){
    var bq080 = document.getElementsByName('BQ080');
    
    for (var i=0; i<bq080.length; i++){
        bq080[i].onchange = listenerBq080;
    } 
    function listenerBq080(){
       var gradeBq080 = this.value;
       document.getElementById("bQ080").innerHTML = gradeBq080;

        if (gradeBq080 == 5){
            document.getElementById('bq080_comment').style = 'visibility: visible'; //Изменение видимости комментария
        }
        if (gradeBq080 > 0){
            document.getElementById('se300').style = 'visibility: visible'; //Изменение видимости комментария
        }
    }
}
bq080(); //Для запуска слушателя

//Для просмотра выбранных элементов
function sq020(){
    var sq020 = document.getElementsByName('SQ020');
    
    for (var i=0; i<sq020.length; i++){
        sq020[i].onchange = listenerSq020;
    } 
    function listenerSq020(){
        var gradeSq020 = this.value;
        document.getElementById("sQ020").innerHTML = gradeSq020;
    }
}
sq020(); //Для запуска слушателя

function sq030(){
    var sq030 = document.getElementsByName('SQ030');
    
    for (var i=0; i<sq030.length; i++){
        sq030[i].onchange = listenerSq030;
    } 
    function listenerSq030(){
        var gradeSq030 = this.value;
        document.getElementById("sQ030").innerHTML = gradeSq030;
    }
}
sq030(); //Для запуска слушателя

function sq040(){
    var sq040 = document.getElementsByName('SQ040');
    
    for (var i=0; i<sq040.length; i++){
        sq040[i].onchange = listenerSq040;
    } 
    function listenerSq040(){
        var gradeSq040 = this.value;
        document.getElementById("sQ040").innerHTML = gradeSq040;
    }
}
sq040(); //Для запуска слушателя

function sq050(){
    var sq050 = document.getElementsByName('SQ050');
    
    for (var i=0; i<sq050.length; i++){
        sq050[i].onchange = listenerSq050;
    } 
    function listenerSq050(){
        var gradeSq050 = this.value;
        document.getElementById("sQ050").innerHTML = gradeSq050;
    }
}
sq050(); //Для запуска слушателя

function sq060(){
    var sq060 = document.getElementsByName('SQ060');
    
    for (var i=0; i<sq060.length; i++){
        sq060[i].onchange = listenerSq060;
    } 
    function listenerSq060(){
        var gradeSq060 = this.value;
        document.getElementById("sQ060").innerHTML = gradeSq060;
    }
}
sq060(); //Для запуска слушателя

function sq070(){
    var sq070 = document.getElementsByName('SQ070');
    
    for (var i=0; i<sq070.length; i++){
        sq070[i].onchange = listenerSq070;
    } 
    function listenerSq070(){
        var gradeSq070 = this.value;
        document.getElementById("sQ070").innerHTML = gradeSq070;
    }
}
sq070(); //Для запуска слушателя

function sq080(){
    var sq080 = document.getElementsByName('SQ080');
    
    for (var i=0; i<sq080.length; i++){
        sq080[i].onchange = listenerSq080;
    } 
    function listenerSq080(){
        var gradeSq080 = this.value;
        document.getElementById("sQ080").innerHTML = gradeSq080;
    }
}
sq080(); //Для запуска слушателя

function sq090(){
    var sq090 = document.getElementsByName('SQ090');
    
    for (var i=0; i<sq090.length; i++){
        sq090[i].onchange = listenerSq090;
    } 
    function listenerSq090(){
        var gradeSq090 = this.value;
        document.getElementById("sQ090").innerHTML = gradeSq090;
    }
}
sq090(); //Для запуска слушателя

function sq110(){
    var sq110 = document.getElementsByName('SQ110');
    
    for (var i=0; i<sq110.length; i++){
        sq110[i].onchange = listenerSq110;
    } 
    function listenerSq110(){
        var gradeSq110 = this.value;
        document.getElementById("sQ110").innerHTML = gradeSq110;
    }
}
sq110(); //Для запуска слушателя

function sq120(){
    var sq120 = document.getElementsByName('SQ120');
    
    for (var i=0; i<sq120.length; i++){
        sq120[i].onchange = listenerSq120;
    } 
    function listenerSq120(){
        var gradeSq120 = this.value;
        document.getElementById("sQ120").innerHTML = gradeSq120;
    }
}
sq120(); //Для запуска слушателя

function sq130(){
    var sq130 = document.getElementsByName('SQ130');
    
    for (var i=0; i<sq130.length; i++){
        sq130[i].onchange = listenerSq130;
    } 
    function listenerSq130(){
        var gradeSq130 = this.value;
        document.getElementById("sQ130").innerHTML = gradeSq130;
    }
}
sq130(); //Для запуска слушателя

function sq140(){
    var sq140 = document.getElementsByName('SQ140');
    
    for (var i=0; i<sq140.length; i++){
        sq140[i].onchange = listenerSq140;
    } 
    function listenerSq140(){
        var gradeSq140 = this.value;
        document.getElementById("sQ140").innerHTML = gradeSq140;
    }
}
sq140(); //Для запуска слушателя

function dq010(){
    var dq010 = document.getElementsByName('DQ010');
    
    for (var i=0; i<dq010.length; i++){
        dq010[i].onchange = listenerDq010;
    } 
    function listenerDq010(){
        var gradeDq010 = this.value;
        document.getElementById("dQ010").innerHTML = gradeDq010;
    }
}
dq010(); //Для запуска слушателя

function dq020(){
    var dq020 = document.getElementsByName('DQ020');
    
    for (var i=0; i<dq020.length; i++){
        dq020[i].onchange = listenerDq020;
    } 
    function listenerDq020(){
        var gradeDq020 = this.value;
        document.getElementById("dQ020").innerHTML = gradeDq020;
    }
}
dq020(); //Для запуска слушателя

function dq040(){
    var dq040 = document.getElementsByName('DQ040');
    
    for (var i=0; i<dq040.length; i++){
        dq040[i].onchange = listenerDq040;
    } 
    function listenerDq040(){
        var gradeDq040 = this.value;
        document.getElementById("dQ040").innerHTML = gradeDq040;
    }
}
dq040(); //Для запуска слушателя

//Сохранить опрос
function saveReportKia(){

    var adminName = "Воронова Александра Юрьевна";
    var outgoingCallDate = new Date();
    
    var vin = document.getElementById('vin').innerText;
    var idClient = document.getElementById('idClient').innerText;
    var bq010 = document.getElementById('bQ010').innerText;
    var bq020 = document.getElementById('bQ020').innerText;
    var bq030 = document.getElementById('bQ030').innerText;
    var bq030Comment = document.getElementById('bq030comment').value;
    var bq040 = document.getElementById('bQ040').innerText;
    //Собрать до кучи 
    var bQ050_1_3 = document.getElementById('bQ050_1_3').innerText;
    var bQ050_4_8 = document.getElementById('bQ050_4_8').innerText;
    var bQ050_9_13 = document.getElementById('bQ050_9_13').innerText;
    var bQ050_14_17 = document.getElementById('bQ050_14_17').innerText;
    var bQ050_18_21 = document.getElementById('bQ050_18_21').innerText;
    var bQ050_98_99 = document.getElementById('bQ050_98_99').innerText;
    
    if (bQ050_1_3 > 0){
        bQ050_1_3 = bQ050_1_3 + "; "
    }
    if (bQ050_4_8 > 0){
        bQ050_4_8 = bQ050_4_8 + "; "
    }
    if (bQ050_9_13 > 0){
        bQ050_9_13 = bQ050_9_13 + "; "
    }
    if (bQ050_14_17 > 0){
        bQ050_14_17 = bQ050_14_17 + "; "
    }
    if (bQ050_18_21 > 0){
        bQ050_18_21 = bQ050_18_21 + "; "
    }
    if (bQ050_98_99 > 0){
        bQ050_98_99 = bQ050_98_99 + "; "
    }
    var bq050 = bQ050_1_3 + bQ050_4_8 + bQ050_9_13 + bQ050_14_17 + bQ050_18_21 + bQ050_98_99;
 
    var bq050comment1_3 = document.getElementById('bq050comment1_3').value;
    var bq050comment4_8 = document.getElementById('bq050comment4_8').value;
    var bq050comment9_13 = document.getElementById('bq050comment9_13').value;
    var bq050comment14_17 = document.getElementById('bq050comment14_17').value;
    var bq050comment18_21 = document.getElementById('bq050comment18_21').value;
    var bq050comment98_99 = document.getElementById('bq050comment98_99').value;
    
    if (bq050comment1_3 != ""){
        bq050comment1_3 = bq050comment1_3 + "; "
    }
    if (bq050comment4_8 != ""){
        bq050comment4_8 = bq050comment4_8 + "; "
    }
    if (bq050comment9_13 != ""){
        bq050comment9_13 = bq050comment9_13 + "; "
    }
    if (bq050comment14_17 != ""){
        bq050comment14_17 = bq050comment14_17 + "; "
    }
    if (bq050comment18_21 != ""){
        bq050comment18_21 = bq050comment18_21 + "; "
    }
    if (bq050comment98_99 != ""){
        bq050comment98_99 = bq050comment98_99 + "; "
    }

    var bq050Comment = bq050comment1_3 + bq050comment4_8 + bq050comment9_13 + bq050comment14_17 + bq050comment18_21 + bq050comment98_99;
    //Куча собрана

    var bq060 = document.getElementById('bQ060').innerText;
    var bq070 = document.getElementById('bQ070').innerText;
    var bq080 = document.getElementById('bQ080').innerText;
    var bq080Comment = document.getElementById('bq080comment').value;
    var sq010 = 1;
    var sq020 = document.getElementById('sQ020').innerText;
    var sq030 = document.getElementById('sQ030').innerText;
    var sq040 = document.getElementById('sQ040').innerText;
    var sq050 = document.getElementById('sQ050').innerText;
    var sq060 = document.getElementById('sQ060').innerText;
    var sq070 = document.getElementById('sQ070').innerText;
    var sq080 = document.getElementById('sQ080').innerText;
    var sq090 = document.getElementById('sQ090').innerText;
    var sq110 = document.getElementById('sQ110').innerText;
    var sq120 = document.getElementById('sQ120').innerText;
    var sq130 = document.getElementById('sQ130').innerText;
    var sq140 = document.getElementById('sQ140').innerText;
    var dq010 = document.getElementById('dQ010').innerText;
    var dq020 = document.getElementById('dQ020').innerText;
    var dq030 = document.getElementById('dQ030').innerText;
    var dq040 = document.getElementById('dQ040').innerText;
    
    //Для ошибки
    var numOrder = document.getElementById('numOrder').value;
    if (numOrder == ""){
        alert("Не подгружен заказ-наряд для опроса!");
        return;
    }

    //Оприавка на два метода на сервере
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vasFullReportNps/saveReportKia");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"numOrder":numOrder, "outgoingCallDate":outgoingCallDate,
    "vin":vin, "idClient":idClient, 
    "bq010":bq010, "bq020":bq020, "bq030":bq030, "bq030Comment":bq030Comment, "bq040":bq040, "bq050":bq050, "bq050Comment":bq050Comment, "bq060":bq060, "bq070":bq070, "bq080":bq080, "bq080Comment":bq080Comment,
    "sq010":sq010, "sq020":sq020, "sq030":sq030, "sq040":sq040, "sq050":sq050, "sq060":sq060, "sq070":sq070, "sq080":sq080, "sq090":sq090, "sq110":sq110, "sq120":sq120, "sq130":sq130, "sq140":sq140,
    "dq010":dq010, "dq020":dq020, "dq030":dq030, "dq040":dq040}));
        
    alert("Опрос сохранён в базе.");

    var numOrders = "да-000000" + document.getElementById('numOrder').value; //Обход для определения организации ВАС или ДЖИ
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_nps/outgoingCall");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"numOrder":numOrders, "adminName":adminName, "nps":bq040}));

    document.location.reload();
}