package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveTotalCurveTime implements Feature{
	private ArrayList<MouseMove> moves;
	public MouseMoveTotalCurveTime(MouseLogParser _Parser){
		moves = _Parser.getMouseMoves();
	}
	public MouseMoveTotalCurveTime( ArrayList<MouseMove> moves){
		this.moves = moves;
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
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Mouse Move Total Curve Time", ""+sum, "Milliseconds")};
	}
}
