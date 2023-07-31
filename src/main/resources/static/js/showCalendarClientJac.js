var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

function showCalendarClientJac(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var clientsJac = JSON.parse(this.responseText);
            var clientsJacTableHead = 
            '<tr>\n' +
            '<td>ФИО клиента</td>\n' +
            '<td>VIN</td>\n' +
            '<td>Дата</td>\n' +
            '<td>Телефон</td>\n' +
            '<td>Действия</td>' +
            '</tr>\n';
            
            for (let i=0; i<clientsJac.length; i++){
                var clientJac = clientsJac[i];
                date = new Date(clientJac.plannedDate).toLocaleDateString();
                
                dateForIcon = new Date();
                dateRemmark = new Date(clientJac.date_remmark);

                var countDay = ((dateForIcon - dateRemmark) / (60 * 60 * 24 * 1000));
                console.log(countDay < 3)
                var comment;
                if (clientJac.remmark != null && countDay < 3){
                    comment = '<i style="color: green;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else if (clientJac.remmark != null && countDay > 3 && countDay < 7){
                    comment = '<i style="color: yellow;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                }
                else if (clientJac.remmark != null && countDay > 7){
                    comment = '<i style="color: red;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else {
                    comment = '<i style="color: grey;" class="fa fa-asterisk" aria-hidden="true"></i>';
                }

                clientsJacTableHead = clientsJacTableHead + '\n' +
                '<tr><td>'+clientJac.owner+'</td>\n' +
                '<td>'+clientJac.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientJac.phone+'</td>\n' +
                '<td><i style="margin: 5px" onclick="showCardCalendarClientJac('+clientJac.id+')" type="button"  class="fa fa-cog aria-hidden=true"></i></button>\n' +
                 comment + '</td></tr>';
                
            }

            document.getElementById("calendarClientJac").innerHTML = clientsJacTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_jac/findThisMonth", true);
    xhttp.send();
}

function showCalendarClientJacPrevious(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var clientsJac = JSON.parse(this.responseText);
            var clientsJacTableHead = 
            '<tr>\n' +
            '<td>ФИО клиента</td>\n' +
            '<td>VIN</td>\n' +
            '<td>Дата</td>\n' +
            '<td>Телефон</td>\n' +
            '<td>Действия</td>' +
            '</tr>\n';
            
            for (let i=0; i<clientsJac.length; i++){
                var clientJac = clientsJac[i];
                date = new Date(clientJac.plannedDate).toLocaleDateString();
                
                dateForIcon = new Date();
                dateRemmark = new Date(clientJac.date_remmark);

                var countDay = ((dateForIcon - dateRemmark) / (60 * 60 * 24 * 1000));
                var comment;
                if (clientJac.remmark != null && countDay < 3){
                    comment = '<i style="color: green;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else if (clientJac.remmark != null && countDay > 3 && countDay < 7){
                    comment = '<i style="color: yellow;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                }
                else if (clientJac.remmark != null && countDay > 7){
                    comment = '<i style="color: red;" type="button" class="fa fa-thumbs-up" aria-hidden="true"></i>';
                } else {
                    comment = '<i style="color: grey;" class="fa fa-asterisk" aria-hidden="true"></i>';
                }

                clientsJacTableHead = clientsJacTableHead + '\n' +
                '<tr><td>'+clientJac.owner+'</td>\n' +
                '<td>'+clientJac.vin+'</td>\n' +
                '<td>'+date+'</td>\n' +
                '<td>'+clientJac.phone+'</td>\n' +
                '<td><i style="margin: 5px" onclick="showCardCalendarClientJac('+clientJac.id+')" type="button"  class="fa fa-cog aria-hidden=true"></i></button>\n' +
                 comment + '</td></tr>';
                
            }

            document.getElementById("calendarClientJacPrevious").innerHTML = clientsJacTableHead; 
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_calendar_client_jac/findPreviousMonth", true);
    xhttp.send();
}
showCalendarClientJac();
showCalendarClientJacPrevious();