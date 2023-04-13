/** Загрузка времено сохранённого чек-листа аудита Механика */
function openTemporaryCheckReportMechanicalKia(){
    var numOrderCheckKiaRepair = document.getElementById("numOrderCheckKiaRepair").value;

    console.log(numOrderCheckKiaRepair);

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);
            
            /** Данные по зн */
            document.getElementById("surnameMechanicalKia").value = reports.surnameMechanicalKia;
            document.getElementById("mechanicalKiaVin").value = reports.mechanicalKiaVin;

            /** Обработка первой вкладки */
            /** Вопрос 1 */
            if (reports.repairQualityControl1 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl1 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_1_no"]');
                r.setAttribute('checked', 'true');
            } 
            /** Вопрос 2 */
            if (reports.repairQualityControl2 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl2 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_2_no"]');
                r.setAttribute('checked', 'true');
            }else if (reports.repairQualityControl2 === "NA"){
                let r = document.querySelector('input[id="repairQualityControl_2_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (reports.repairQualityControl3 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl3 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (reports.repairQualityControl4 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl4 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_4_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (reports.repairQualityControl5 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl5 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_5_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (reports.repairQualityControl6 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl6 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_6_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 7 */
            if (reports.repairQualityControl7 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_7_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl7 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_7_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 8 */
            if (reports.repairQualityControl8 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_8_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl8 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_8_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 9 */
            if (reports.repairQualityControl9 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_9_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl9 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_9_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 10 */
            if (reports.repairQualityControl10 === "YES"){
                let r = document.querySelector('input[id="repairQualityControl_10_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.repairQualityControl10 === "NO"){
                let r = document.querySelector('input[id="repairQualityControl_10_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка второй вкладки */
            /** Вопрос 1 */
            if (reports.parkingLotCheck1 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck1 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (reports.parkingLotCheck2 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck2 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_2_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (reports.parkingLotCheck3 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck3 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (reports.parkingLotCheck4 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck4 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_4_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (reports.parkingLotCheck5 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck5 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_5_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (reports.parkingLotCheck6 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck6 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_6_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 7 */
            if (reports.parkingLotCheck7 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_7_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck7 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_7_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 8 */
            if (reports.parkingLotCheck8 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_8_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck8 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_8_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 9 */
            if (reports.parkingLotCheck9 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_9_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck9 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_9_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 10 */
            if (reports.parkingLotCheck10 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_10_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck10 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_10_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 11 */
            if (reports.parkingLotCheck11 === "YES"){
                let r = document.querySelector('input[id="parkingLotCheck_11_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.parkingLotCheck11 === "NO"){
                let r = document.querySelector('input[id="parkingLotCheck_11_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка третьей вкладки */
            /** Вопрос 1 */
            if (reports.checkInMotion1 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.checkInMotion1 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (reports.checkInMotion2 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.checkInMotion2 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_2_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (reports.checkInMotion3 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.checkInMotion3 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (reports.checkInMotion4 === "YES"){
                let r = document.querySelector('input[id="checkInMotion_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.checkInMotion4 === "NO"){
                let r = document.querySelector('input[id="checkInMotion_4_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка четвёртой вкладки */
            /** Вопрос 1 */
            if (reports.qualityControl1 === "YES"){
                let r = document.querySelector('input[id="qualityControl_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (reports.qualityControl1 === "NO"){
                let r = document.querySelector('input[id="qualityControl_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            document.getElementById("notesMaster").value = reports.notesMaster;
            /** Вопрос 3 */
            document.getElementById("notesWork").value = reports.notesWork;
            /** Вопрос 4 */
            document.getElementById("explanationsWork").value = reports.explanationsWork;
        }
    };
    
    xhttp.open("GET", "JSON/testRepairAuto.json", true);
    xhttp.send();
};