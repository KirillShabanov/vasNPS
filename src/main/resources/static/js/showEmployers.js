var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

function showEmployers(){
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var employers = JSON.parse(this.responseText);
            var employersTableHead = '<tr><td style="width: 450px">Руководители</td>\n' +
            '<td></td></tr>\n';
            var employersTableEngineerTehnical = '<tr><td style="width: 450px">Инженер технолог</td>\n' +
            '<td></td></tr>\n';

            for (let i=0; i<employers.length; i++){
                var employer = employers[i];

                if(employer.jobTitle === "Заместитель директора по развитию сервиса"){
                employersTableHead = employersTableHead + '\n' +
                '<tr><td>'+employer.fullName+'</td>\n' +
                '<td><button style="margin: 5px" onclick="updateEmployer('+employer.id+')" type="button" ><i class="fa fa-pencil aria-hidden=true"></i></button>\n' +
                '<button style="margin: 5px" onclick="deleteEmployer('+employer.id+')" type="button"><i class="fa fa-trash"></i></button></td></tr>';
                }

                if(employer.jobTitle === "Инженер технолог"){
                    employersTableEngineerTehnical = employersTableEngineerTehnical + '\n' +
                    '<tr><td>'+employer.fullName+'</td>\n' +
                    '<td><button style="margin: 5px" onclick="updateEmployer('+employer.id+')" type="button" ><i class="fa fa-pencil aria-hidden=true"></i></button>\n' +
                    '<button style="margin: 5px" onclick="deleteEmployer('+employer.id+')" type="button"><i class="fa fa-trash"></i></button></td></tr>';
                }

                document.getElementById("employersListHead").innerHTML = employersTableHead;
                document.getElementById("employersListEngineerTehnical").innerHTML = employersTableEngineerTehnical;
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + "vas_employers/findAll", true);
    xhttp.send();
}
showEmployers();