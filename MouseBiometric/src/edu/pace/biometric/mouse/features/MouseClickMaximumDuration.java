package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;

public class MouseClickMaximumDuration extends MouseFeature {

	public MouseClickMaximumDuration(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int max=0;
		List<MouseClick> clicks=mouseParser.getMouseClicks(appName);
		for(MouseClick mc:clicks){
			if(mc.getMouseduration() > max){
				max=mc.getMouseduration();
			}
		}
		return new FeatureResult(getClass().getName(), "Maximum Duration of a Mouse Click", ""+max, "ms");
	}
}
