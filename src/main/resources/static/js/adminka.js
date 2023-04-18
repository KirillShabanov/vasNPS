var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

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
    xhttp.open("GET", restApiAddressNPS + "vas_manager/findAllNpsTo", true);
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
    xhttp.open("GET", restApiAddressNPS + "vas_manager/findAllNpsCc", true);
    xhttp.send();
}
showCallManagerCc();

function showManagerTechnical(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var tableNPSManagerTechnical = JSON.parse(this.responseText);
            var tableNPSTechnicalTable = '<tr>\n' +
            '<td>ФИО</td>\n' +
            '<td style="padding-left: 15px;">Email</td>\n' +
            '</tr>\n';
            for (let i=0; i<tableNPSManagerTechnical.length; i++){
                var tableNPSTechnical = tableNPSManagerTechnical[i];
                tableNPSTechnicalTable = tableNPSTechnicalTable + '\n' +
                '<tr><td>'+tableNPSTechnical.manager_name+'</td>\n' +
                '<td style="padding-left: 15px; font-style: oblique;">'+tableNPSTechnical.manager_email+'</td>\n' +
                '<td><a class="button" style="margin: 5px" onclick="deleteCallManagerTo('+tableNPSTechnical.id+')" type="button"><i class="fa fa-trash" style="color: black"></i></a></td></tr>';
                document.getElementById("tableNPSNechnicalTable").innerHTML = tableNPSTechnicalTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_manager/findAllTechnical", true);
    xhttp.send();
}
showManagerTechnical();

function showManagerBodyRepair(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var tableNPSManagerBodyRepair = JSON.parse(this.responseText);
            var tableNPSBodyRepairTable = '<tr>\n' +
            '<td>ФИО</td>\n' +
            '<td style="padding-left: 15px;">Email</td>\n' +
            '</tr>\n';
            for (let i=0; i<tableNPSManagerBodyRepair.length; i++){
                var tableNPSBodyRepair = tableNPSManagerBodyRepair[i];
                tableNPSBodyRepairTable = tableNPSBodyRepairTable + '\n' +
                '<tr><td>'+tableNPSBodyRepair.manager_name+'</td>\n' +
                '<td style="padding-left: 15px; font-style: oblique;">'+tableNPSBodyRepair.manager_email+'</td>\n' +
                '<td><a class="button" style="margin: 5px" onclick="deleteCallManagerTo('+tableNPSBodyRepair.id+')" type="button"><i class="fa fa-trash" style="color: black"></i></a></td></tr>';
                document.getElementById("tableNPSBodyRepairTable").innerHTML = tableNPSBodyRepairTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_manager/findAllBodyRepair", true);
    xhttp.send();
}
showManagerBodyRepair();

function createManager(){
    var managerName = document.getElementById("nameManager").value;
    var managerEmail = document.getElementById("emailManager").value;
    var managerPosition = document.getElementById("managerPosition").value;
    var organisation = "VitebskAutoCity";
        
    if (managerPosition == 1){
        var position = "Technical"
    } else if (managerPosition == 2) {
        position = "BodyRepair"
    } 

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_manager/saveManager");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"manager_name":managerName, "manager_email":managerEmail, "position_departament":position, "organisation":organisation}));
        
    clearManager();
    document.location.reload();
}

function callManagerTo(){

    var managerName = document.getElementById("callManagerToName").value;
    var managerEmail = document.getElementById("callManagerToEmail").value;
    var managerBrand = document.getElementById("callManagerToBrand").value;
    var position = 'NPS call';

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_manager/saveNpsCall");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"manager_name":managerName, "manager_email":managerEmail, "position_nps_call":position, "brand":managerBrand}));
    
    clearCallManagerTo();
    document.location.reload();
};

function callManagerCc(){

    var managerName = document.getElementById("callManagerCcName").value;
    var managerEmail = document.getElementById("callManagerCcEmail").value;
    var position = 'NPS call copy';

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.open("POST", restApiAddressNPS + "vas_manager/saveNpsCall");
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    xmlhttp.send(JSON.stringify({"manager_name":managerName, "manager_email":managerEmail, "position_nps_call_copy":position}));

    clearCallManagerCc();
    document.location.reload();
};

function deleteCallManagerTo(id){
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", restApiAddressNPS + "vas_manager/delete/" + id, true);
    xhttp.send();

    xhttp.onreadystatechange = function(){
        if (this.readyState == 4 && this.status == 200){
            document.location.reload();
        }
    };
}

function deleteCallManagerCc(id){
    var xhttp = new XMLHttpRequest();
    xhttp.open("DELETE", restApiAddressNPS + "vas_manager/delete/" + id, true);
    xhttp.send();

    xhttp.onreadystatechange = function(){
        if (this.readyState == 4 && this.status == 200){
            document.location.reload();
        }
    };
}










function clearCreateOrder(){
    document.getElementById("ownerFullName").value = "";
    document.getElementById("clientFullName").value = "";
    document.getElementById("phone1").value = "";
    document.getElementById("phone2").value = "";
    document.getElementById("vin").value = "";
    document.getElementById("regNum").value = "";
    document.getElementById("brand").value = "";
    document.getElementById("model").value = "";
    document.getElementById("masterName").value = "";
    document.getElementById("yearRelease").value = "";
    document.getElementById("numOrder").value = "";
    document.getElementById("dateOrder").value = "";
    document.getElementById("category").value = "";
}
function clearUpdateCallDateOrder(){
    document.getElementById("numOrderUpdate").value = "";
    document.getElementById("dateCallOrderUpdate").value = "";
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
function clearManager(){
    document.getElementById("nameManager").value = "";
    document.getElementById("emailManager").value = "";
}