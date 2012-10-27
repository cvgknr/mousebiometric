package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveTotalCurveTime implements Feature{
	private MouseLogParser parser;
	private ArrayList<MouseMove> moves;
	public MouseMoveTotalCurveTime(MouseLogParser _Parser){
		parser = _Parser;
		moves = parser.getMouseMoves();
	}
	public FeatureResult[] extract(){
		MouseMove _pt = null;
		long sum = 0;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				sum += mouseMove.getStarttime() - _pt.getStarttime();
			}
			_pt = mouseMove;
		}
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Mouse Move Total Curve Time", ""+sum, "MilliSeconds")};
	}
}
