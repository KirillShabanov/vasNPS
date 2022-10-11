function createOrder(){
    var numOrder = document.getElementById("numOrderCreate").value;
    var dateOrder = document.getElementById("dateOrderCreate").value;
    var category = document.getElementById("categoryCreate").value;
    var masterName = document.getElementById("masterNameCreate").value;
    var callStatus = 'not call';

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "http://localhost:8080/vas_nps/saveOrder");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"num_order":numOrder, "date_order":dateOrder, "category":category, "master_name":masterName, "call_status":callStatus}));
    
    clearCreateOrder();
};

function updateCallDateOrder(){
    var numOrder = document.getElementById("numOrderUpdate").value;
    var callDate = document.getElementById("dateCallOrderUpdate").value;
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("GET", `http://localhost:8080/vas_nps/${numOrder}&${callDate}`);
    xmlhttp.send();

    clearUpdateCallDateOrder();
};

function nps(){
    var numOrder = document.getElementById("numOrderCall").value;
    var nps = document.getElementById("npsCall").value;
    var adminComment = document.getElementById("adminCommentCall").value;
    var adminName = document.getElementById("adminNameCall").value;
    var callStatus = 'call';

    if(nps == 0){
        adminComment = "Отказ от опроса"
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", `http://localhost:8080/vas_nps/${numOrder}/${nps}/${adminName}/${adminComment}/${callStatus}`);
        xmlhttp.send();
    } else if (nps != 0 && adminComment === "") {
        adminComment = "Комментариев не было"
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", `http://localhost:8080/vas_nps/${numOrder}/${nps}/${adminName}/${adminComment}/${callStatus}`);
        xmlhttp.send();
    } else {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.open("GET", `http://localhost:8080/vas_nps/${numOrder}/${nps}/${adminName}/${adminComment}/${callStatus}`);
        xmlhttp.send();
    }

    clearNps();
};

function showCallManagerTo(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var tableNPSto = JSON.parse(this.responseText);
            var tableNPStoTable = '<tr>\n' +
            '<td>ФИО</td>\n' +
            '<td style="padding-left: 15px;">Email</td>\n' +
            '<td style="padding-left: 15px;">Марка</td>\n' +
            '</tr>\n';
            for (let i=0; i<tableNPSto.length; i++){
                var tableNPS = tableNPSto[i];
                tableNPStoTable = tableNPStoTable + '\n' +
                '<tr><td>'+tableNPS.manager_name+'</td>\n' +
                '<td style="padding-left: 15px; font-style: oblique;">'+tableNPS.manager_email+'</td>\n' +
                '<td style="padding-left: 15px; text-decoration:underline; font-style: oblique;">'+tableNPS.brand+'</td>\n' +
                '<td><a class="button" style="margin: 5px" onclick="deleteCallManagerTo('+tableNPS.id+')" type="button"><i class="fa fa-trash" style="color: black"></i></a></td></tr>';
                document.getElementById("tableNPSto").innerHTML = tableNPStoTable;
            }
        }
    };
    xhttp.open("GET", "http://localhost:8080/vas_manager/findAllNpsTo", true);
    xhttp.send();
}
showCallManagerTo();

function showCallManagerCc(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var tableNPScc = JSON.parse(this.responseText);
            var tableNPSccTable = '<tr>\n' +
            '<td>ФИО</td>\n' +
            '<td style="padding-left: 15px;">Email</td>\n' +
            '</tr>\n';
            for (let i=0; i<tableNPScc.length; i++){
                var tableNPSc = tableNPScc[i];
                tableNPSccTable = tableNPSccTable + '\n' +
                '<tr><td>'+tableNPSc.manager_name+'</td>\n' +
                '<td style="padding-left: 15px; text-decoration:underline; font-style: oblique;">'+tableNPSc.manager_email+'</td>\n' +
                '<td><a class="button" style="margin: 5px" onclick="deleteCallManagerCc('+tableNPSc.id+')" type="button"><i class="fa fa-trash" style="color: black"></i></a></td></tr>';
                document.getElementById("tableNPScc").innerHTML = tableNPSccTable;
            }
        }
    };
    xhttp.open("GET", "http://localhost:8080/vas_manager/findAllNpsCc", true);
    xhttp.send();
}
showCallManagerCc();

function callManagerTo(){

    var managerName = document.getElementById("callManagerToName").value;
    var managerEmail = document.getElementById("callManagerToEmail").value;
    var managerBrand = document.getElementById("callManagerToBrand").value;
    var position = 'NPS call';

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "http://localhost:8080/vas_manager/saveNpsCall");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"manager_name":managerName, "manager_email":managerEmail, "position":position, "brand":managerBrand}));
    
    clearCallManagerTo();
    document.location.reload();
};

function callManagerCc(){

    var managerName = document.getElementById("callManagerCcName").value;
    var managerEmail = document.getElementById("callManagerCcEmail").value;
    var position = 'NPS call copy';

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", "http://localhost:8080/vas_manager/saveNpsCall");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"manager_name":managerName, "manager_email":managerEmail, "position":position}));

    clearCallManagerCc();
    document.location.reload();
};

function deleteCallManagerTo(id){
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", "http://localhost:8080/vas_manager/delete/" + id, true);
    xhttp.send();

    xhttp.onreadystatechange = function(){
        if (this.readyState == 4 && this.status == 200){
            document.location.reload();
        }
    };
}

function deleteCallManagerCc(id){
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", "http://localhost:8080/vas_manager/delete/" + id, true);
    xhttp.send();

    xhttp.onreadystatechange = function(){
        if (this.readyState == 4 && this.status == 200){
            document.location.reload();
        }
    };
}










function clearCreateOrder(){
    document.getElementById("numOrderCreate").value = "";
    document.getElementById("dateOrderCreate").value = "";
    document.getElementById("masterNameCreate").value = "";
    document.getElementById("categoryCreate").value = "";
}
function clearUpdateCallDateOrder(){
    document.getElementById("numOrderUpdate").value = "";
    document.getElementById("dateCallUpdate").value = "";
}
function clearNps(){
    document.getElementById("numOrderCall").value = "";
    document.getElementById("npsCall").value = "";
    document.getElementById("adminCommentCall").value = "";
    document.getElementById("adminNameCall").value = "";
}
function clearCallManagerTo(){
    document.getElementById("callManagerToName").value = "";
    document.getElementById("callManagerToEmail").value = "";
}
function clearCallManagerCc(){
    document.getElementById("callManagerCcName").value = "";
    document.getElementById("callManagerCcEmail").value = "";
}