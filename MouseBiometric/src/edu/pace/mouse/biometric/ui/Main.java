package edu.pace.mouse.biometric.ui;

import javax.swing.SwingUtilities;

public class Main {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                Ui ui=new Ui();
	                ui.setVisible(true);
	            }
	        });
	    }
	 

}
