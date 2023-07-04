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




 