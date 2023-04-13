var restApiAddressNPS = "http://localhost:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

/** Загрузка времено сохранённого чек-листа аудита ИТ */
function openTemporaryCheckReportEngineerKia(){
    var numOrderCheckKia = document.getElementById("numOrderCheckKia").value;

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if (this.readyState === 4 && this.status === 200) {
            var reports = JSON.parse(this.responseText);

            for (let i=0; i<reports.length; i++){
                var report = reports[i]; 

                /**Для замены кнопки */
                document.getElementById('saveCheckReportEngineerKia').style = "display:none";
                document.getElementById('updateCheckReportEngineerKia').style = "display";
                /**Для замены кнопки */

            } 
            /** Данные по зн */
            document.getElementById("surnameEngineerKia").value = report.surnameEngineerKia;
            /** Обработка первой вкладки */
            /** Вопрос 1 */
            if (report.receptionQuestion1 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion1 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_1_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion1 === "NA"){
                let r = document.querySelector('input[id="carReceptionQuestion_1_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (report.receptionQuestion2 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion2 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_2_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.receptionQuestion3 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion3 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_3_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (report.receptionQuestion4 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion4 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_4_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (report.receptionQuestion5 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion5 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_5_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (report.receptionQuestion6 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion6 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_6_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 7 */
            if (report.receptionQuestion7 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_7_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion7 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_7_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 8 */
            if (report.receptionQuestion8 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_8_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion8 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_8_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 9 */
            if (report.receptionQuestion9 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_9_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion9 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_9_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 10 */
            if (report.receptionQuestion10 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_10_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion10 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_10_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 11 */
            if (report.receptionQuestion11 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_11_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion11 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_11_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 12 */
            if (report.receptionQuestion12 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_12_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion12 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_12_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 13 */
            if (report.receptionQuestion2 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_13_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion13 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_13_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 14 */
            if (report.receptionQuestion14 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_14_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion14 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_14_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 15 */
            if (report.receptionQuestion15 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_15_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion15 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_15_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 16 */
            if (report.receptionQuestion16 === "YES"){
                let r = document.querySelector('input[id="carReceptionQuestion_16_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.receptionQuestion16 === "NO"){
                let r = document.querySelector('input[id="carReceptionQuestion_16_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка второй вкладки */
            /** Вопрос 1 */
            if (report.inspectionQuestion1 === "YES"){
                let r = document.querySelector('input[id="carInspectionQuestion_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion1 === "NO"){
                let r = document.querySelector('input[id="carInspectionQuestion_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (report.inspectionQuestion2 === "YES"){
                let r = document.querySelector('input[id="carInspectionQuestion_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion2 === "NO"){
                let r = document.querySelector('input[id="carInspectionQuestion_2_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion2 === "NA"){
                let r = document.querySelector('input[id="carInspectionQuestion_2_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.inspectionQuestion3 === "YES"){
                let r = document.querySelector('input[id="carInspectionQuestion_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion3 === "NO"){
                let r = document.querySelector('input[id="carInspectionQuestion_3_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion3 === "NA"){
                let r = document.querySelector('input[id="carInspectionQuestion_3_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (report.inspectionQuestion4 === "YES"){
                let r = document.querySelector('input[id="carInspectionQuestion_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion4 === "NO"){
                let r = document.querySelector('input[id="carInspectionQuestion_4_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (report.inspectionQuestion5 === "YES"){
                let r = document.querySelector('input[id="carInspectionQuestion_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion5 === "NO"){
                let r = document.querySelector('input[id="carInspectionQuestion_5_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion5 === "NA"){
                let r = document.querySelector('input[id="carInspectionQuestion_5_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (report.inspectionQuestion6 === "YES"){
                let r = document.querySelector('input[id="carInspectionQuestion_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.inspectionQuestion6 === "NO"){
                let r = document.querySelector('input[id="carInspectionQuestion_6_no"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка третьей вкладки */
            /** Вопрос 1 */
            if (report.waitingQuestion1 === "YES"){
                let r = document.querySelector('input[id="carWaitingQuestion_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.waitingQuestion1 === "NO"){
                let r = document.querySelector('input[id="carWaitingQuestion_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (report.waitingQuestion2 === "YES"){
                let r = document.querySelector('input[id="carWaitingQuestion_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.waitingQuestion2 === "NO"){
                let r = document.querySelector('input[id="carWaitingQuestion_2_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.waitingQuestion2 === "NA"){
                let r = document.querySelector('input[id="carWaitingQuestion_2_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.waitingQuestion3 === "YES"){
                let r = document.querySelector('input[id="carWaitingQuestion_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.waitingQuestion3 === "NO"){
                let r = document.querySelector('input[id="carWaitingQuestion_3_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.waitingQuestion3 === "NA"){
                let r = document.querySelector('input[id="carWaitingQuestion_3_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }

            /** Обработка четвёртой вкладки */
            /** Вопрос 1 */
            if (report.issuingQuestion1 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_1_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion1 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_1_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 2 */
            if (report.issuingQuestion2 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_2_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion2 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_2_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 3 */
            if (report.issuingQuestion3 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_3_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion3 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_3_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion3 === "NA"){
                let r = document.querySelector('input[id="issuingCarQuestion_3_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 4 */
            if (report.issuingQuestion4 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_4_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion4 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_4_no"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion4 === "NA"){
                let r = document.querySelector('input[id="issuingCarQuestion_4_not_evaluated"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 5 */
            if (report.issuingQuestion5 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_5_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion5 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_5_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 6 */
            if (report.issuingQuestion6 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_6_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion6 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_6_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 7 */
            if (report.issuingQuestion7 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_7_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion7 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_7_no"]');
                r.setAttribute('checked', 'true');
            }
            /** Вопрос 8 */
            if (report.issuingQuestion8 === "YES"){
                let r = document.querySelector('input[id="issuingCarQuestion_8_yes"]');
                r.setAttribute('checked', 'true');
            } else if (report.issuingQuestion8 === "NO"){
                let r = document.querySelector('input[id="issuingCarQuestion_8_no"]');
                r.setAttribute('checked', 'true');
            }
        }
    };

    xhttp.open("GET", restApiAddressNPS + `vas_check_list_engineer_kia/findAllCheckListNotCancelFromNum/${numOrderCheckKia}`, true);
    xhttp.send();
    
};