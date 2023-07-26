/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  update data employer
 * 
 *  Date of creation: 15/07/2023
 */

var restApiAddressNPS = "http://192.168.10.22:8080/"; //"http://localhost:8080/" "http://192.168.10.22:8080/"

let popup_updateEmployers = document.getElementById('popup_updateEmployers')
let update_button_employer = document.getElementById('update_button_employer')
let popup_employers = document.getElementById('popup_employers')

function updateEmployer(id){
    
    popup_updateEmployers.className = ('popup open')
    popup_employers.className = ('popup close')
    
}