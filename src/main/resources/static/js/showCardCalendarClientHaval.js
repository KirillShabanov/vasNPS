var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

var client_card_haval = document.getElementById('client_TO_card_haval');
var idForButton;

function showCardCalendarClientHaval(id){

    client_card_haval.className = ('popup open');

    idForButton = id;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var cardTOclientsHaval = JSON.parse(this.responseText);

            for (let i=0; i <cardTOclientsHaval.length; i++){
                var cardTOclientsHavalData = cardTOclientsHaval[i]
            }
            date = new Date(cardTOclientsHavalData.plannedDate).toLocaleDateString();
            document.getElementById('ownerCard_haval').innerHTML = cardTOclientsHavalData.owner;
            document.getElementById('vinCard_haval').innerHTML = cardTOclientsHavalData.vin;
            document.getElementById('phoneCard_haval').innerHTML = cardTOclientsHavalData.phone; 
            document.getElementById('dateTOCard_haval').innerHTML = date; 

            if (cardTOclientsHavalData.remarkCard != null) {
            document.getElementById('remarkCard_haval').innerHTML = cardTOclientsHavalData.remark;
            } else {
                document.getElementById('remarkCard_haval').innerHTML = " ";
            }

            if (cardTOclientsHavalData.toDateZero != null){
                dateTO0 = new Date(cardTOclientsHavalData.toDateZero).toLocaleDateString();
                var to0 = dateTO0 + ", при пробеге " + cardTOclientsHavalData.toMileageZero + " км."
                document.getElementById('to0Card_haval').innerHTML = to0; 
            } else {
                document.getElementById('to0Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateOne != null){
                dateTO1 = new Date(cardTOclientsHavalData.toDateOne).toLocaleDateString();
                var to1 = dateTO1 + ", при пробеге " + cardTOclientsHavalData.toMileageOne + " км."
                document.getElementById('to1Card_haval').innerHTML = to1; 
            } else {
                document.getElementById('to1Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateTwo != null){
                dateTO2 = new Date(cardTOclientsHavalData.toDateTwo).toLocaleDateString();
                var to2 = dateTO2 + ", при пробеге " + cardTOclientsHavalData.toMileageTwo + " км."
                document.getElementById('to2Card_haval').innerHTML = to2; 
            } else {
                document.getElementById('to2Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateThree != null){
                dateTO3 = new Date(cardTOclientsHavalData.toDateThree).toLocaleDateString();
                var to3 = dateTO3 + ", при пробеге " + cardTOclientsHavalData.toMileageThree + " км."
                document.getElementById('to3Card_haval').innerHTML = to3; 
            } else {
                document.getElementById('to3Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateFour != null){
                dateTO4 = new Date(cardTOclientsHavalData.toDateFour).toLocaleDateString();
                var to4 = dateTO4 + ", при пробеге " + cardTOclientsHavalData.toMileageFour + " км."
                document.getElementById('to4Card_haval').innerHTML = to4; 
            } else {
                document.getElementById('to4Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateFive != null){
                dateTO5 = new Date(cardTOclientsHavalData.toDateFive).toLocaleDateString();
                var to5 = dateTO5 + ", при пробеге " + cardTOclientsHavalData.toMileageFive + " км."
                document.getElementById('to5Card_haval').innerHTML = to5; 
            } else {
                document.getElementById('to5Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateSex != null){
                dateTO6 = new Date(cardTOclientsHavalData.toDateSex).toLocaleDateString();
                var to6 = dateTO6 + ", при пробеге " + cardTOclientsHavalData.toMileageSex + " км."
                document.getElementById('to6Card_haval').innerHTML = to6; 
            } else {
                document.getElementById('to6Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateSeven != null){
                dateTO7 = new Date(cardTOclientsHavalData.toDateSeven).toLocaleDateString();
                var to7 = dateTO7 + ", при пробеге " + cardTOclientsHavalData.toMileageSeven + " км."
                document.getElementById('to7Card_haval').innerHTML = to7; 
            } else {
                document.getElementById('to7Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateEight != null){
                dateTO8 = new Date(cardTOclientsHavalData.toDateEight).toLocaleDateString();
                var to8 = dateTO8 + ", при пробеге " + cardTOclientsHavalData.toMileageEight + " км."
                document.getElementById('to8Card_haval').innerHTML = to8; 
            } else {
                document.getElementById('to8Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateNine != null){
                dateTO9 = new Date(cardTOclientsHavalData.toDateNine).toLocaleDateString();
                var to9 = dateTO9 + ", при пробеге " + cardTOclientsHavalData.toMileageNine + " км."
                document.getElementById('to9Card_haval').innerHTML = to9; 
            } else {
                document.getElementById('to9Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.toDateTen != null){
                dateTO10 = new Date(cardTOclientsHavalData.toDateTen).toLocaleDateString();
                var to10 = dateTO10 + ", при пробеге " + cardTOclientsHavalData.toMileageTen + " км."
                document.getElementById('to10Card_haval').innerHTML = to10; 
            } else {
                document.getElementById('to10Card_haval').innerHTML = "-";
            }

            if (cardTOclientsHavalData.remmark != null) {
                document.getElementById('remarkCard_haval').innerHTML = cardTOclientsHavalData.remmark; 
            } else {
                document.getElementById('remarkCard_haval').innerHTML = "-";
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_calendar_client_haval/findThisMonthTO/${id}`, true);
    xhttp.send();  
}

function addRemarkCalendarHaval(){

    var remmark = document.getElementById('remmarkText_haval').value;

    if (remmark != ""){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + `vas_calendar_client_haval/addRemmark/${idForButton}`);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"id":idForButton, "remmark":remmark}));

    cleanRemmarkHaval();
    document.getElementById('remarkCard_haval').innerHTML = remmark;
    }
}

function cleanRemmarkHaval(){
    document.getElementById('remmarkText_haval').value = "";
}