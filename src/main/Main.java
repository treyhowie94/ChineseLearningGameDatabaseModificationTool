package main;
 
import gui.LoginWindow; 

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {	
		runProgram();
	}
	
	public static void runProgram() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
