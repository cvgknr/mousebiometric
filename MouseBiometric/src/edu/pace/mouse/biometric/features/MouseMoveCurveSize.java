package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveCurveSize implements Feature{
	private ArrayList<MouseMove> moves;
	public MouseMoveCurveSize(MouseLogParser _Parser){
		moves = _Parser.getMouseMoves();
	}
	public FeatureResult[] extract(){
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Mouse Move Curve Size", Util.format(moves.size()), "px")};
	}
}
