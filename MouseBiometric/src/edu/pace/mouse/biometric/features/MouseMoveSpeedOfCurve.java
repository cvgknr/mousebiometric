package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveSpeedOfCurve implements Feature{
	private ArrayList<MouseMove> moves;
	public MouseMoveSpeedOfCurve(MouseLogParser _Parser){
		moves = _Parser.getMouseMoves();
	}
	public MouseMoveSpeedOfCurve(ArrayList<MouseMove> moves){
		this.moves = moves;
	}
	public FeatureResult[] extract(){
		MouseMove _pt = null;
		double sum = 0;
		long x, y;
		double sqrt;
		long time;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				x = mouseMove.getXpix() - _pt.getXpix();
				y = mouseMove.getYpix() - _pt.getYpix();
				sqrt = Math.sqrt((double)(x * x + y * y));
				time = mouseMove.getStarttime() - _pt.getStarttime();
				sum = sum + (sqrt / (double)time);
			}
			_pt = mouseMove;
		}
		//TODO: Minimum and Maximum is pending
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Mouse Move Curve Average Speed", Util.format(sum), "Pixels/Milliseconds"),
				new FeatureResult(getClass().getName(), "Mouse Move Curve Minimum Speed", "", "Pixels/Milliseconds"),
				new FeatureResult(getClass().getName(), "Mouse Move Curve Maximum Speed", "", "Pixels/Milliseconds")};
	}
}
