var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"


/** Получение данных ИТ по периоду */
function periodCheckEngineer(){
    var periodCheckListEngineerSurname = document.getElementById('periodCheckListEngineerSurname').value;
    var periodCheckListEngineerDateFrom = document.getElementById('periodCheckListEngineerDateFrom').value;
    var periodCheckListEngineerDateBy = document.getElementById('periodCheckListEngineerDateBy').value;

    //Количество найденных совпадений
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            let countReportEngineer = reports.length;
            document.getElementById("countReportsKiaEngineerPeriod").innerHTML = countReportEngineer;

            if(countReportEngineer >= 1){
                document.getElementById('downaloadReportEngineerKia').style = "display: visible";
            } else {
                document.getElementById('downaloadReportEngineerKia').style = "display: none";
            }
        }
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_engineer_kia/countReportsKiaEngineerPeriod/${periodCheckListEngineerSurname}&${periodCheckListEngineerDateFrom}&${periodCheckListEngineerDateBy}`, true);
    xhttp.send();
}
//Скачать в EXCEL
function downaloadReportEngineerKiaButton(){
    var periodCheckListEngineerSurname = document.getElementById('periodCheckListEngineerSurname').value;
    var periodCheckListEngineerDateFrom = document.getElementById('periodCheckListEngineerDateFrom').value;
    var periodCheckListEngineerDateBy = document.getElementById('periodCheckListEngineerDateBy').value;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            href = "http://192.168.10.22:8080/vas_check_list_engineer_kia/downaloadReportsKiaEngineerPeriod/${periodCheckListEngineerSurname}&${periodCheckListEngineerDateFrom}&${periodCheckListEngineerDateBy";	
		
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

	downloadFile(`http://192.168.10.22:8080/vas_check_list_engineer_kia/downaloadReportsKiaEngineerPeriod/${periodCheckListEngineerSurname}&${periodCheckListEngineerDateFrom}&${periodCheckListEngineerDateBy}`, 'Чек-лист ИТ за период.xlsx');
	}
    };
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_engineer_kia/downaloadReportsKiaEngineerPeriod/${periodCheckListEngineerSurname}&${periodCheckListEngineerDateFrom}&${periodCheckListEngineerDateBy}`, true);
    xhttp.send();

	  
}