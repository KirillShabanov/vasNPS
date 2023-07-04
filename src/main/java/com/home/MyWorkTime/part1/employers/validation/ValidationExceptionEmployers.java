/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Description of methods of working with the database
 * 
 *  Date of creation: 29/06/2023
 */

package com.home.MyWorkTime.part1.employers.validation;

public class ValidationExceptionEmployers extends Exception {

    private String message;

    public ValidationExceptionEmployers(String message) {
    }

    public String getMessage() {
        return message;
    }
}

