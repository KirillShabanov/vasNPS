var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"


/** Получение данных ИТ по периоду */
function periodCheckMechanical(){
    var periodCheckListMechanicalSurname = document.getElementById('periodCheckListMechanicalSurname').value;
    var periodCheckListMechanicalDateFrom = document.getElementById('periodCheckListMechanicalDateFrom').value;
    var periodCheckListMechanicalDateBy = document.getElementById('periodCheckListMechanicalDateBy').value;

    //Количество найденных совпадений
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            let countReportMechanical = reports.length;
            document.getElementById("countReportsKiaMechanicalPeriod").innerHTML = countReportMechanical;

            if(countReportMechanical >= 1){
                document.getElementById('downaloadReportMechanicalKia').style = "display: visible";
            } else {
                document.getElementById('downaloadReportMechanicalKia').style = "display: none";
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_mechanical_kia/countReportsKiaMechanicalPeriod/${periodCheckListMechanicalSurname}&${periodCheckListMechanicalDateFrom}&${periodCheckListMechanicalDateBy}`, true);
    xhttp.send();
}
//Скачать в EXCEL
function downaloadReportMechanicalKiaButton(){
    var periodCheckListMechanicalSurname = document.getElementById('periodCheckListMechanicalSurname').value;
    var periodCheckListMechanicalDateFrom = document.getElementById('periodCheckListMechanicalDateFrom').value;
    var periodCheckListMechanicalDateBy = document.getElementById('periodCheckListMechanicalDateBy').value;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            function downloadFile(url, fileName){
  		fetch(url, { method: 'get', mode: 'no-cors', referrerPolicy: 'no-referrer' })
    		.then(res => res.blob())
    		.then(res => {
      	const aElement = document.createElement('a');
      	aElement.setAttribute('download', fileName);
      	const href = URL.createObjectURL(res);
      	aElement.href = href;
      	// aElement.setAttribute('href', href);
      	aElement.setAttribute('target', '_blank');
      	aElement.click();
      	URL.revokeObjectURL(href);
    	});
	};

	downloadFile(`http://192.168.10.22:8080/vas_check_list_mechanical_kia/downaloadReportsKiaMechanicalPeriod/${periodCheckListMechanicalSurname}&${periodCheckListMechanicalDateFrom}&${periodCheckListMechanicalDateBy}`, 'Чек-лист автомеханика за период.xlsx');
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_mechanical_kia/downaloadReportsKiaMechanicalPeriod/${periodCheckListMechanicalSurname}&${periodCheckListMechanicalDateFrom}&${periodCheckListMechanicalDateBy}`, true);
    xhttp.send();
    
}