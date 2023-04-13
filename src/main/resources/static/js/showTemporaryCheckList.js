var restApiAddressNPS = "http://localhost:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

function showTemporaryCheckList(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var checkLists = JSON.parse(this.responseText);
            var checkListTable = '<tr>\n' +
            '<td>Номер заказ-наряда</td>\n' +
            '<td>Фамилия</td></tr>';
            
            for (let i=0; i<checkLists.length; i++){
                var checkList = checkLists[i];
                checkListTable = checkListTable + '\n' +
                '<tr><td>'+checkList.numOrderCheckKia+'</td>\n' +
                '<td>'+checkList.surnameEngineerKia+'</td></tr>';
                document.getElementById("temporaryCheckList").innerHTML = checkListTable;
            } 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_check_list_engineer_kia/findAllCheckListNotCancel", true);
    xhttp.send();
}
showTemporaryCheckList();