package edu.pace.mouse.biometric.util;

import java.text.DecimalFormat;

public class Util {
	public static String format(double x){
		 DecimalFormat df = new DecimalFormat("#.##");
		 return df.format(x);
	}
}
