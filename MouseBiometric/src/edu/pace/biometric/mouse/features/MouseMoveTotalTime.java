package edu.pace.biometric.mouse.features;

import java.util.Vector;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MouseMove;

public class MouseMoveTotalTime implements MouseFeature{
	private final MouseLogParser mouseParser;
	private final String appName;
	
	public MouseMoveTotalTime(MouseLogParser parser, String app){
		this.mouseParser = parser;
		this.appName = app;
	}
	public String extract(){
		Vector<MouseMove> moves = mouseParser.getMouseMoves(appName);
		MouseMove _pt = null;
		long sum = 0;
		for (MouseMove mouseMove : moves) {
			if (null != _pt)
				sum += Long.parseLong(mouseMove.starttime) - Long.parseLong(_pt.starttime);
			_pt = mouseMove;
		}
		return ""+sum;
	}
}
