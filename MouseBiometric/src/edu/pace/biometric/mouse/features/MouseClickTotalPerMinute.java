package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;

public class MouseClickTotalPerMinute extends MouseFeature{

	public MouseClickTotalPerMinute(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		List<MouseClick> clicks = mouseParser.getMouseClicks(appName);
		
		return new FeatureResult(getClass().getName(), "Total Mouse Clicks Per Minute", "", "Total Per Minute");
	}

}
