var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

var client_card = document.getElementById('client_TO_card_kia');
var idForButton;

function showCardCalendarClientKia(id){

    client_card.className = ('popup open');

    idForButton = id;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var cardTOclientsKia = JSON.parse(this.responseText);

            for (let i=0; i <cardTOclientsKia.length; i++){
                var cardTOclientsKiaData = cardTOclientsKia[i]
            }
            date = new Date(cardTOclientsKiaData.plannedDate).toLocaleDateString();
            document.getElementById('ownerCard').innerHTML = cardTOclientsKiaData.owner;
            document.getElementById('vinCard').innerHTML = cardTOclientsKiaData.vin;
            document.getElementById('phoneCard').innerHTML = cardTOclientsKiaData.phone; 
            document.getElementById('dateTOCard').innerHTML = date; 

            if (cardTOclientsKiaData.remarkCard != null) {
            document.getElementById('remarkCard').innerHTML = cardTOclientsKiaData.remark;
            } else {
                document.getElementById('remarkCard').innerHTML = " ";
            }

            if (cardTOclientsKiaData.toDateZero != null){
                dateTO0 = new Date(cardTOclientsKiaData.toDateZero).toLocaleDateString();
                var to0 = dateTO0 + ", при пробеге " + cardTOclientsKiaData.toMileageZero + " км."
                document.getElementById('to0Card').innerHTML = to0; 
            } else {
                document.getElementById('to0Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateOne != null){
                dateTO1 = new Date(cardTOclientsKiaData.toDateOne).toLocaleDateString();
                var to1 = dateTO1 + ", при пробеге " + cardTOclientsKiaData.toMileageOne + " км."
                document.getElementById('to1Card').innerHTML = to1; 
            } else {
                document.getElementById('to1Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateTwo != null){
                dateTO2 = new Date(cardTOclientsKiaData.toDateTwo).toLocaleDateString();
                var to2 = dateTO2 + ", при пробеге " + cardTOclientsKiaData.toMileageTwo + " км."
                document.getElementById('to2Card').innerHTML = to2; 
            } else {
                document.getElementById('to2Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateThree != null){
                dateTO3 = new Date(cardTOclientsKiaData.toDateThree).toLocaleDateString();
                var to3 = dateTO3 + ", при пробеге " + cardTOclientsKiaData.toMileageThree + " км."
                document.getElementById('to3Card').innerHTML = to3; 
            } else {
                document.getElementById('to3Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateFour != null){
                dateTO4 = new Date(cardTOclientsKiaData.toDateFour).toLocaleDateString();
                var to4 = dateTO4 + ", при пробеге " + cardTOclientsKiaData.toMileageFour + " км."
                document.getElementById('to4Card').innerHTML = to4; 
            } else {
                document.getElementById('to4Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateFive != null){
                dateTO5 = new Date(cardTOclientsKiaData.toDateFive).toLocaleDateString();
                var to5 = dateTO5 + ", при пробеге " + cardTOclientsKiaData.toMileageFive + " км."
                document.getElementById('to5Card').innerHTML = to5; 
            } else {
                document.getElementById('to5Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateSex != null){
                dateTO6 = new Date(cardTOclientsKiaData.toDateSex).toLocaleDateString();
                var to6 = dateTO6 + ", при пробеге " + cardTOclientsKiaData.toMileageSex + " км."
                document.getElementById('to6Card').innerHTML = to6; 
            } else {
                document.getElementById('to6Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateSeven != null){
                dateTO7 = new Date(cardTOclientsKiaData.toDateSeven).toLocaleDateString();
                var to7 = dateTO7 + ", при пробеге " + cardTOclientsKiaData.toMileageSeven + " км."
                document.getElementById('to7Card').innerHTML = to7; 
            } else {
                document.getElementById('to7Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateEight != null){
                dateTO8 = new Date(cardTOclientsKiaData.toDateEight).toLocaleDateString();
                var to8 = dateTO8 + ", при пробеге " + cardTOclientsKiaData.toMileageEight + " км."
                document.getElementById('to8Card').innerHTML = to8; 
            } else {
                document.getElementById('to8Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateNine != null){
                dateTO9 = new Date(cardTOclientsKiaData.toDateNine).toLocaleDateString();
                var to9 = dateTO9 + ", при пробеге " + cardTOclientsKiaData.toMileageNine + " км."
                document.getElementById('to9Card').innerHTML = to9; 
            } else {
                document.getElementById('to9Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.toDateTen != null){
                dateTO10 = new Date(cardTOclientsKiaData.toDateTen).toLocaleDateString();
                var to10 = dateTO10 + ", при пробеге " + cardTOclientsKiaData.toMileageTen + " км."
                document.getElementById('to10Card').innerHTML = to10; 
            } else {
                document.getElementById('to10Card').innerHTML = "-";
            }

            if (cardTOclientsKiaData.remmark != null) {
                document.getElementById('remarkCard').innerHTML = cardTOclientsKiaData.remmark; 
            } else {
                document.getElementById('remarkCard').innerHTML = "-";
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_calendar_client_kia/findThisMonthTO/${id}`, true);
    xhttp.send();

    
}

function addRemarkCalendarKia(){

    var remmark = document.getElementById('remmarkText_kia').value;

    if (remmark != ""){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + `vas_calendar_client_kia/addRemmark/${idForButton}`);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"id":idForButton, "remmark":remmark}));

    cleanRemmarkKia();
    document.getElementById('remarkCard').innerHTML = remmark;
    }
}

function cleanRemmarkKia(){
    document.getElementById('remmarkText_kia').value = "";
}