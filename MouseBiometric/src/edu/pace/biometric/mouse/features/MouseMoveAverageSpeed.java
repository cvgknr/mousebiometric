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
public class MouseMoveAverageSpeed extends MouseFeature{
	public MouseMoveAverageSpeed(MouseLogParser parser, String app){
		super(parser, app);
	}
	public FeatureResult extract(){
		List<MouseMove> moves = mouseParser.getMouseMoves(appName);
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
				time = Long.parseLong(mouseMove.getStarttime()) - Long.parseLong(_pt.getStarttime());
				sum = sum + (sqrt / (double)time);
			}
			_pt = mouseMove;
		}
		//return sum+"";
		//TODO
		return new FeatureResult(getClass().getName(), "Average Mouse Movement Speed", ""+sum, "Milli Seconds");
	}
}
