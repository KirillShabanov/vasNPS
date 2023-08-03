var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

function showCalendarClientHaval(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var clientsHaval = JSON.parse(this.responseText);
            var clientsHavalTableHead = 
            '<tr>\n' +
            '<td>ФИО клиента</td>\n' +
            '<td>VIN</td>\n' +
            '<td>Дата</td>\n' +
            '<td>Телефон</td>\n' +
            '<td>Действия</td>' +
            '</tr>\n';
            
            for (let i=0; i<clientsHaval.length; i++){
                var clientHaval = clientsHaval[i];
                date = new Date(clientHaval.plannedDate).toLocaleDateString();
                
                dateForIcon = new Date();
                dateRemmark = new Date(clientHaval.date_remmark);

                var countDay = ((dateForIcon - dateRemmark) / (60 * 60 * 24 * 1000));
                var comment;
                if (clientHaval.remmark != null && countDay < 3){
                    comment = '<i style="color: green;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else if (clientHaval.remmark != null && countDay > 3 && countDay < 7){
                    comment = '<i style="color: yellow;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                }
                else if (clientHaval.remmark != null && countDay > 7){
                    comment = '<i style="color: red;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else {
                    comment = '<i style="color: grey;" class="fa fa-asterisk" aria-hidden="true"></i>';
                }

                clientsHavalTableHead = clientsHavalTableHead + '\n' +
                '<tr><td>'+clientHaval.owner+'</td>\n' +
                '<td>'+clientHaval.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientHaval.phone+'</td>\n' +
                '<td><i style="margin: 5px" onclick="showCardCalendarClientHaval('+clientHaval.id+')" type="button"  class="fa fa-cog aria-hidden=true"></i></button>\n' +
                 comment + '</td></tr>';
                
            }

            document.getElementById("calendarClientHaval").innerHTML = clientsHavalTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_haval/findThisMonth", true);
    xhttp.send();
}

function showCalendarClientHavalPrevious(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var clientsHaval = JSON.parse(this.responseText);
            var clientsHavalTableHead = 
            '<tr>\n' +
            '<td>ФИО клиента</td>\n' +
            '<td>VIN</td>\n' +
            '<td>Дата</td>\n' +
            '<td>Телефон</td>\n' +
            '<td>Действия</td>' +
            '</tr>\n';
            
            for (let i=0; i<clientsHaval.length; i++){
                var clientHaval = clientsHaval[i];
                date = new Date(clientHaval.plannedDate).toLocaleDateString();
                
                dateForIcon = new Date();
                dateRemmark = new Date(clientHaval.date_remmark);

                var countDay = ((dateForIcon - dateRemmark) / (60 * 60 * 24 * 1000));
                var comment;
                if (clientHaval.remmark != null && countDay < 3){
                    comment = '<i style="color: green;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else if (clientHaval.remmark != null && countDay > 3 && countDay < 7){
                    comment = '<i style="color: yellow;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                }
                else if (clientHaval.remmark != null && countDay > 7){
                    comment = '<i style="color: red;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else {
                    comment = '<i style="color: grey;" class="fa fa-asterisk" aria-hidden="true"></i>';
                }

                clientsHavalTableHead = clientsHavalTableHead + '\n' +
                '<tr><td>'+clientHaval.owner+'</td>\n' +
                '<td>'+clientHaval.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientHaval.phone+'</td>\n' +
                '<td><i style="margin: 5px" onclick="showCardCalendarClientHaval('+clientHaval.id+')" type="button"  class="fa fa-cog aria-hidden=true"></i></button>\n' +
                 comment + '</td></tr>';
                
            }

            document.getElementById("calendarClientHavalPrevious").innerHTML = clientsHavalTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_haval/findPreviousMonth", true);
    xhttp.send();
}
