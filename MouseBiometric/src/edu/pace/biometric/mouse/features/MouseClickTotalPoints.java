package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;

public class MouseClickTotalPoints extends MouseFeature{

	public MouseClickTotalPoints(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		List<MouseClick> clicks = mouseParser.getMouseClicks(appName);
		return new FeatureResult(getClass().getName(), "Total number of mouse clicks in a sample", ""+clicks.size(), "Points");
	}

}
