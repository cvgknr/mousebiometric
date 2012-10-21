package edu.pace.biometric.mouse.features;

import java.util.List;
import java.util.Vector;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MouseMove;
/**
 * 
 * @author Venugopala C
 *
 */

public class MouseMoveCurveLength extends MouseFeature{
	public MouseMoveCurveLength(MouseLogParser parser, String app){
		super(parser, app);
	}
	public FeatureResult extract(){
		List<MouseMove> moves = mouseParser.getMouseMoves(appName);
		MouseMove _pt = null;
		double sum = 0;
		long x, y;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				x = mouseMove.getXpix() - _pt.getXpix();
				y = mouseMove.getYpix() - _pt.getYpix();
				sum += Math.sqrt((double)(x * x + y * y));
			}
			_pt = mouseMove;
		}
		return new FeatureResult(getClass().getName(), "Mouse Move Curve Length", ""+sum, "Pixels");
	}
}
