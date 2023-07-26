/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  creat new employer
 * 
 *  Date of creation: 13/07/2023
 */

var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"



function createEmployers(){

    var checkCodeLevelAccess = document.getElementById('authorizationCodeForConfirmation').value;
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {

            var checkLevelAccess = JSON.parse(this.responseText);
            
            if(checkLevelAccess == "1"){
                var fullNameInput = document.getElementById("fullName").value;
                var subdivisionInput = document.getElementById("subdivision").options[subdivision.selectedIndex].text;
                var jobTitleInput = document.getElementById("jobTitle").options[jobTitle.selectedIndex].text;
                var scheduleInput = document.getElementById("schedule").options[schedule.selectedIndex].text;
                var bidInput = document.getElementById("bid").options[bid.selectedIndex].text;
                var codeLevelAccessInput = document.getElementById("codeLevelAccess").options[codeLevelAccess.selectedIndex].text;
                var authorizationCodeInput = document.getElementById("authorizationCode").value;
                var keyIdInput = document.getElementById("keyId").value;
                var emailInput = document.getElementById("email").value;
                var actinNewEmloyer = "active";

                var xmlhttp = new XMLHttpRequest();
                xmlhttp.open("POST", restApiAddressNPS + `vas_employers/addEmployer&${checkCodeLevelAccess}`);
                xmlhttp.setRequestHeader("Content-Type", "application/json");
                xmlhttp.send(JSON.stringify({
                    "fullName":fullNameInput, "subdivision":subdivisionInput,
                    "jobTitle":jobTitleInput, "schedule":scheduleInput,
                    "bid":bidInput, "codeLevelAccess":codeLevelAccessInput,
                    "authorizationCode":authorizationCodeInput, "keyId":keyIdInput,
                    "status":actinNewEmloyer, "email":emailInput
                }));

                xmlhttp.onreadystatechange = function(){
                    if (this.readyState === 4 && this.status === 200){
                        showEmployers(); 
                    }
                };

                clear();
                alert("Новый сотрудник добавлен в базу.")
            } else {
                alert("Недостаточно прав для проведения данного действия.")
            }

        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_employers/checkCodeLevelAccess/${checkCodeLevelAccess}`, true);
    xhttp.send();
}

function clear(){
    document.getElementById("fullName").value = "";
    document.getElementById("subdivision").value = "*";
    document.getElementById("jobTitle").value = "*";
    document.getElementById("schedule").value = "*";
    document.getElementById("bid").value = "*";
    document.getElementById("codeLevelAccess").value = "*";
    document.getElementById("authorizationCode").value = "";
    document.getElementById("keyId").value = "";
    document.getElementById("email").value = "";
    document.getElementById("authorizationCodeForConfirmation").value = "";
}