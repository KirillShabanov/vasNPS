var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

let client_TO_card_jac = document.getElementById('client_TO_card_jac');
var idForButton;

function showCardCalendarClientJac(id){

    client_TO_card_jac.className = ('popup open');

    idForButton = id;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var cardTOclientsJac = JSON.parse(this.responseText);

            for (let i=0; i <cardTOclientsJac.length; i++){
                var cardTOclientsJacData = cardTOclientsJac[i]
            }
            date = new Date(cardTOclientsJacData.plannedDate).toLocaleDateString();
            document.getElementById('ownerCard_jac').innerHTML = cardTOclientsJacData.owner;
            document.getElementById('vinCard_jac').innerHTML = cardTOclientsJacData.vin;
            document.getElementById('phoneCard_jac').innerHTML = cardTOclientsJacData.phone; 
            document.getElementById('dateTOCard_jac').innerHTML = date; 

            if (cardTOclientsJacData.remarkCard != null) {
            document.getElementById('remarkCard_jac').innerHTML = cardTOclientsJacData.remark;
            } else {
                document.getElementById('remarkCard_jac').innerHTML = " ";
            }

            if (cardTOclientsJacData.toDateZero != null){
                dateTO0 = new Date(cardTOclientsJacData.toDateZero).toLocaleDateString();
                var to0 = dateTO0 + ", при пробеге " + cardTOclientsJacData.toMileageZero + " км."
                document.getElementById('to0Card_jac').innerHTML = to0; 
            } else {
                document.getElementById('to0Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateOne != null){
                dateTO1 = new Date(cardTOclientsJacData.toDateOne).toLocaleDateString();
                var to1 = dateTO1 + ", при пробеге " + cardTOclientsJacData.toMileageOne + " км."
                document.getElementById('to1Card_jac').innerHTML = to1; 
            } else {
                document.getElementById('to1Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateTwo != null){
                dateTO2 = new Date(cardTOclientsJacData.toDateTwo).toLocaleDateString();
                var to2 = dateTO2 + ", при пробеге " + cardTOclientsJacData.toMileageTwo + " км."
                document.getElementById('to2Card_jac').innerHTML = to2; 
            } else {
                document.getElementById('to2Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateThree != null){
                dateTO3 = new Date(cardTOclientsJacData.toDateThree).toLocaleDateString();
                var to3 = dateTO3 + ", при пробеге " + cardTOclientsJacData.toMileageThree + " км."
                document.getElementById('to3Card_jac').innerHTML = to3; 
            } else {
                document.getElementById('to3Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateFour != null){
                dateTO4 = new Date(cardTOclientsJacData.toDateFour).toLocaleDateString();
                var to4 = dateTO4 + ", при пробеге " + cardTOclientsJacData.toMileageFour + " км."
                document.getElementById('to4Card_jac').innerHTML = to4; 
            } else {
                document.getElementById('to4Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateFive != null){
                dateTO5 = new Date(cardTOclientsJacData.toDateFive).toLocaleDateString();
                var to5 = dateTO5 + ", при пробеге " + cardTOclientsJacData.toMileageFive + " км."
                document.getElementById('to5Card_jac').innerHTML = to5; 
            } else {
                document.getElementById('to5Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateSex != null){
                dateTO6 = new Date(cardTOclientsJacData.toDateSex).toLocaleDateString();
                var to6 = dateTO6 + ", при пробеге " + cardTOclientsJacData.toMileageSex + " км."
                document.getElementById('to6Card_jac').innerHTML = to6; 
            } else {
                document.getElementById('to6Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateSeven != null){
                dateTO7 = new Date(cardTOclientsJacData.toDateSeven).toLocaleDateString();
                var to7 = dateTO7 + ", при пробеге " + cardTOclientsJacData.toMileageSeven + " км."
                document.getElementById('to7Card_jac').innerHTML = to7; 
            } else {
                document.getElementById('to7Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateEight != null){
                dateTO8 = new Date(cardTOclientsJacData.toDateEight).toLocaleDateString();
                var to8 = dateTO8 + ", при пробеге " + cardTOclientsJacData.toMileageEight + " км."
                document.getElementById('to8Card_jac').innerHTML = to8; 
            } else {
                document.getElementById('to8Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateNine != null){
                dateTO9 = new Date(cardTOclientsJacData.toDateNine).toLocaleDateString();
                var to9 = dateTO9 + ", при пробеге " + cardTOclientsJacData.toMileageNine + " км."
                document.getElementById('to9Card_jac').innerHTML = to9; 
            } else {
                document.getElementById('to9Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.toDateTen != null){
                dateTO10 = new Date(cardTOclientsJacData.toDateTen).toLocaleDateString();
                var to10 = dateTO10 + ", при пробеге " + cardTOclientsJacData.toMileageTen + " км."
                document.getElementById('to10Card_jac').innerHTML = to10; 
            } else {
                document.getElementById('to10Card_jac').innerHTML = "-";
            }

            if (cardTOclientsJacData.remmark != null) {
                document.getElementById('remarkCard_jac').innerHTML = cardTOclientsJacData.remmark; 
            } else {
                document.getElementById('remarkCard_jac').innerHTML = "-";
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_calendar_client_jac/findThisMonthTO/${id}`, true);
    xhttp.send();

    
}

function addRemarkCalendarJac(){

    var remmark = document.getElementById('remmarkText_jac').value;

    if (remmark != ""){
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + `vas_calendar_client_jac/addRemmark/${idForButton}`);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"id":idForButton, "remmark":remmark}));

    cleanRemmark();
    document.getElementById('remarkCard_jac').innerHTML = remmark;
    }
}

function cleanRemmark(){
    document.getElementById('remmarkText_jac').value = "";
}