package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveAverageAngleOfCurve implements Feature{
	private MouseLogParser parser;
	private ArrayList<MouseMove> moves;
	public MouseMoveAverageAngleOfCurve(MouseLogParser _Parser){
		parser = _Parser;
		moves = parser.getMouseMoves();
	}
	public FeatureResult[] extract(){
		MouseMove _pt = null;
		double sum = 0;
		//TODO
		/*long x, y;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				x = mouseMove.getXpix() - _pt.getXpix();
				y = mouseMove.getYpix() - _pt.getYpix();
				sum += Math.sqrt((double)(x * x + y * y));
			}
			_pt = mouseMove;
		}*/
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Mouse Move Curve Size", Util.format(sum), "px")};
	}
}
