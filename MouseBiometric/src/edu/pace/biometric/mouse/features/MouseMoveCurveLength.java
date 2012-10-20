package edu.pace.biometric.mouse.features;

import java.util.Vector;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MouseMove;

public class MouseMoveCurveLength implements MouseFeature{
	private final MouseLogParser mouseParser;
	private final String appName;
	
	public MouseMoveCurveLength(MouseLogParser parser, String app){
		this.mouseParser = parser;
		this.appName = app;
	}
	public String extract(){
		Vector<MouseMove> moves = mouseParser.getMouseMoves(appName);
		MouseMove _pt = null;
		double sum = 0;
		long x, y;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				x = mouseMove.xpix - _pt.xpix;
				y = mouseMove.ypix - _pt.ypix;
				sum += Math.sqrt((double)(x * x + y * y));
			}
			_pt = mouseMove;
		}
		return ""+sum;
	}
}
