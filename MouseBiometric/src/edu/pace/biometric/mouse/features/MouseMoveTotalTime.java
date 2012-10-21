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

public class MouseMoveTotalTime extends MouseFeature{
	public MouseMoveTotalTime(MouseLogParser parser, String app) {
		super(parser, app);
	}

	public FeatureResult extract(){
		List<MouseMove> moves = mouseParser.getMouseMoves(appName);
		MouseMove _pt = null;
		long sum = 0;
		for (MouseMove mouseMove : moves) {
			if (null != _pt)
				sum += Long.parseLong(mouseMove.getStarttime()) - Long.parseLong(_pt.getStarttime());
			_pt = mouseMove;
		}
		return new FeatureResult(getClass().getName(), "Total Mouse Move Time", ""+sum, "Milliseconds");
	}
}
