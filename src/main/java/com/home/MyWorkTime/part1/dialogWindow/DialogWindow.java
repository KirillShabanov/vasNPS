/** 
 *  Author: Shabanov Kirill Vladimirovich
 * 
 *  Republic of Belarus, Vitebsk
 *  Mobile: +375 29 5112110
 *
 *  Description:
 *  Server uptime display
 * 
 *  Date of creation: 28/06/2023
 */

package com.home.MyWorkTime.part1.dialogWindow;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class DialogWindow {
    public static void main(String[] args) {

      	System.setProperty("java.awt.headless", "false");

		SwingUtilities.invokeLater(() -> {
			
			JFrame frame = new JFrame("MyWorkTime server");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLocationRelativeTo(null);
			frame.setSize(300, 100);

				JLabel label = new JLabel();

				Timer timer = new Timer();

				timer.scheduleAtFixedRate(new TimerTask() {
					int sec = 0;
					int min = 0;
					int hour = 0;
					int day = 0;

						public void run() {
							label.setText("Время работы сервера: " + day + " д. " + hour + " ч. " + min + " мин. " + sec + " сек.");
							sec++;
							if (sec == 60){
								min++;
								sec = 0;
							}

							if (min == 60){
								hour++;
								min = 0;
							}

							if (hour == 24){
								day++;
								hour = 0;
							}
						}
				}, 0, 1000);

			frame.add(label);
			frame.setVisible(true);
		});  
    }
       
}
