var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

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
                
                dateForIcon = new Date();
                dateRemmark = new Date(clientKia.date_remmark);

                var countDay = ((dateForIcon - dateRemmark) / (60 * 60 * 24 * 1000));
                var comment;
                if (clientKia.remmark != null && countDay < 3){
                    comment = '<i style="color: green;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else if (clientKia.remmark != null && countDay > 3 && countDay < 7){
                    comment = '<i style="color: yellow;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                }
                else if (clientKia.remmark != null && countDay > 7){
                    comment = '<i style="color: red;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else {
                    comment = '<i style="color: grey;" class="fa fa-asterisk" aria-hidden="true"></i>';
                }

                clientsKiaTableHead = clientsKiaTableHead + '\n' +
                '<tr><td>'+clientKia.owner+'</td>\n' +
                '<td>'+clientKia.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientKia.phone+'</td>\n' +
                '<td><i style="margin: 5px" onclick="showCardCalendarClientKia('+clientKia.id+')" type="button"  class="fa fa-cog aria-hidden=true"></i></button>\n' +
                 comment + '</td></tr>';
                
            }

            document.getElementById("calendarClientKia").innerHTML = clientsKiaTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_kia/findThisMonth", true);
    xhttp.send();
}

function showCalendarClientKiaPrevious(){
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
                
                dateForIcon = new Date();
                dateRemmark = new Date(clientKia.date_remmark);

                var countDay = ((dateForIcon - dateRemmark) / (60 * 60 * 24 * 1000));
                var comment;
                if (clientKia.remmark != null && countDay < 3){
                    comment = '<i style="color: green;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else if (clientKia.remmark != null && countDay > 3 && countDay < 7){
                    comment = '<i style="color: yellow;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                }
                else if (clientKia.remmark != null && countDay > 7){
                    comment = '<i style="color: red;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else {
                    comment = '<i style="color: grey;" class="fa fa-asterisk" aria-hidden="true"></i>';
                }

                clientsKiaTableHead = clientsKiaTableHead + '\n' +
                '<tr><td>'+clientKia.owner+'</td>\n' +
                '<td>'+clientKia.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientKia.phone+'</td>\n' +
                '<td><i style="margin: 5px" onclick="showCardCalendarClientKia('+clientKia.id+')" type="button"  class="fa fa-cog aria-hidden=true"></i></button>\n' +
                 comment + '</td></tr>';
                
            }

            document.getElementById("calendarClientKiaPrevious").innerHTML = clientsKiaTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_kia/findPreviousMonth", true);
    xhttp.send();
}
showCalendarClientKia();
showCalendarClientKiaPrevious()