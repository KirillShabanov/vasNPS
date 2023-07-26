var restApiAddressNPS = "http://localhost:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

function showCalendarClientKia(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var clientsKia = JSON.parse(this.responseText);
            var clientsKiaTableHead = 
            '<tr>\n' +
            '<td>ФИО клиента</td>\n' +
            '<td>VIN</td>\n' +
            '<td>Дата</td>\n' +
            '<td>Телефон</td>\n' +
            '<td>Действия</td>' +
            '</tr>\n';
            
            for (let i=0; i<clientsKia.length; i++){
                var clientKia = clientsKia[i];
                date = new Date(clientKia.plannedDate).toLocaleDateString();
                
                clientsKiaTableHead = clientsKiaTableHead + '\n' +
                '<tr><td>'+clientKia.owner+'</td>\n' +
                '<td>'+clientKia.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientKia.phone+'</td>\n' +
                '<td><button style="margin: 5px" onclick="showCardCalendarClientKia('+clientKia.id+')" type="button" ><i class="fa fa-cog aria-hidden=true"></i></button></td></tr>';
                }

            document.getElementById("calendarClientKia").innerHTML = clientsKiaTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_kia/findThisMonth", true);
    xhttp.send();
}
showCalendarClientKia();