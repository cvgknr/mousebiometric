package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveAverageAngleOfCurve implements Feature{
	private ArrayList<MouseMove> moves;
	public MouseMoveAverageAngleOfCurve(MouseLogParser _Parser){
		moves = _Parser.getMouseMoves();
	}
	public FeatureResult[] extract(){
		MouseMove _pt = null;
		double sum = 0, min = 0, max = 0, angle;
		long x, y;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				x = mouseMove.getXpix() - _pt.getXpix();
				y = mouseMove.getYpix() - _pt.getYpix();
				angle = Math.toDegrees(Math.atan2(x,y));
				sum += angle;
				if (angle < min)
					min = angle;
				if (angle > max)
					max = angle;
			}
			_pt = mouseMove;
		}
		double average = sum/moves.size();
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Average Angle of Mouse Move Curve ", Util.format(average), "degree"),
				new FeatureResult(getClass().getName(), "Minimum Angle of Mouse Move Curve ", Util.format(min), "degree"),
				new FeatureResult(getClass().getName(), "Maximum Angle of Mouse Move Curve ", Util.format(max), "degree")};
	}
}
