package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;

public class MouseClickAverageDuration extends MouseFeature {

	public MouseClickAverageDuration(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int sum=0;
		List<MouseClick> clicks=mouseParser.getMouseClicks(appName);
		int length=clicks.size();
		for(MouseClick mc:clicks)
			sum=sum+mc.getMouseduration();
		//TODO
		//return new FeatureResult(getClass().getName(), "Average Duration of a Mouse Click", ""+(sum/length), "Milli Seconds");
		return new FeatureResult(getClass().getName(), "Average Duration of a Mouse Click", "", "Milli Seconds");
	}
}
