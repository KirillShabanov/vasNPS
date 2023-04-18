var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

/** Загрузка времено сохранённого чек-листа аудита Механика */
function openTemporaryCheckReportMechanicalKia(){
    var numOrderCheckKia = document.getElementById("numOrderCheckKiaRepair").value;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);

            for (let i=0; i<reports.length; i++){
                var report = reports[i]; 

                /**Для замены кнопки */
                document.getElementById('saveCheckReportMechanicalKia').style = "display:none";
                document.getElementById('updateCheckReportMechanicalKia').style = "display";
                /**Для замены кнопки */

            } 
            
            /** Данные по зн */
            document.getElementById("surnameMechanicalKia").value = report.surnameMechanicalKia;
            document.getElementById("mechanicalKiaVin").value = report.mechanicalKiaVin;

            /** Обработка первой вкладки */
            /** Вопрос 1 */
            if (report.repairQualityControl1 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl1 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_1_no"]');
                r.setAttribute('checked', 'true');
            } 
            /** Вопрос 2 */
            if (report.repairQualityControl2 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl2 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_2_no"]');
                r.setAttribute('checked', 'true');
            }else if (report.repairQualityControl2 === "NA"){
                let r = document.querySelector('input[id="repairQualityControl_2_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.repairQualityControl3 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl3 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (report.repairQualityControl4 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl4 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_4_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (report.repairQualityControl5 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl5 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_5_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (report.repairQualityControl6 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl6 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_6_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 7 */
            if (report.repairQualityControl7 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_7_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl7 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_7_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 8 */
            if (report.repairQualityControl8 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_8_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl8 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_8_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 9 */
            if (report.repairQualityControl9 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_9_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl9 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_9_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 10 */
            if (report.repairQualityControl10 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_10_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.repairQualityControl10 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_10_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка второй вкладки */
            /** Вопрос 1 */
            if (report.parkingLotCheck1 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck1 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (report.parkingLotCheck2 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck2 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_2_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.parkingLotCheck3 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck3 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (report.parkingLotCheck4 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck4 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_4_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (report.parkingLotCheck5 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck5 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_5_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (report.parkingLotCheck6 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck6 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_6_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 7 */
            if (report.parkingLotCheck7 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_7_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck7 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_7_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 8 */
            if (report.parkingLotCheck8 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_8_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck8 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_8_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 9 */
            if (report.parkingLotCheck9 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_9_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck9 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_9_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 10 */
            if (report.parkingLotCheck10 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_10_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck10 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_10_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 11 */
            if (report.parkingLotCheck11 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_11_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.parkingLotCheck11 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_11_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка третьей вкладки */
            /** Вопрос 1 */
            if (report.checkInMotion1 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.checkInMotion1 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (report.checkInMotion2 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.checkInMotion2 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_2_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.checkInMotion3 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.checkInMotion3 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (report.checkInMotion4 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.checkInMotion4 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_4_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка четвёртой вкладки */
            /** Вопрос 1 */
            if (report.qualityControl1 === "YES"){
                let r = document.querySelector('input[id="qualityControl_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.qualityControl1 === "NO"){
                let r = document.querySelector('input[id="qualityControl_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            document.getElementById("notesMaster").value = report.notesMaster;
            /** Вопрос 3 */
            document.getElementById("notesWork").value = report.notesWork;
            /** Вопрос 4 */
            document.getElementById("explanationsWork").value = report.explanationsWork;
        }
    };
    
    xhttp.open("GET", restApiAddressNPS + `vas_check_list_mechanical_kia/findAllCheckListNotCancelFromNumMechanical/${numOrderCheckKia}`, true);
    xhttp.send();
};