var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

function showEmployers(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var employers = JSON.parse(this.responseText);
            var employersTable = '<tr>\n' +
            '<td>ФИО</td>\n' +
            '<td>Действия</td>' +
            '</tr>\n';
            for (let i=0; i<employers.length; i++){
                var employer = employers[i];
                console.log(employer);
                employersTable = employersTable + '\n' +
                '<tr><td>'+employer.fullName+'</td>\n' +
                '<td><button style="margin: 5px" onclick="updateEmployer('+employers.id+')" type="button" ><i class="fa fa-pencil aria-hidden=true"></i></button>\n' +
                
                '<button style="margin: 5px" onclick="deleteEmployer('+employers.id+')" type="button"><i class="fa fa-trash"></i></button></td></tr>';
                document.getElementById("employersList").innerHTML = employersTable;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_employers/findAll", true);
    xhttp.send();
}
showEmployers();