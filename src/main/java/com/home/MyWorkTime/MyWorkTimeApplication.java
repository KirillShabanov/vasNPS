/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Main class
 * 
 *  Date of remmark: 27/07/2023
 */
package com.home.MyWorkTime;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


import com.home.MyWorkTime.part1.dialogWindow.DialogWindow;


@EnableScheduling
@SpringBootApplication
public class MyWorkTimeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyWorkTimeApplication.class, args);
		DialogWindow.main(args);
	}
}
