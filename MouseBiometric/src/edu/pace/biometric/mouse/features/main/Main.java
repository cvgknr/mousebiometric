package edu.pace.biometric.mouse.features.main;

import javax.swing.SwingUtilities;

import edu.pace.biometric.mouse.ui.Ui;

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
