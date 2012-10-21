package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;

public class MouseClickMinimumDuration extends MouseFeature {

	public MouseClickMinimumDuration(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int min=Integer.MAX_VALUE;
		List<MouseClick> clicks=mouseParser.getMouseClicks(appName);
		for(MouseClick mc:clicks){			
			if(mc.getMouseduration() < min){
				min=mc.getMouseduration();
			}	
		}
		return new FeatureResult(getClass().getName(), "Minimum Duration of a Mouse Click", ""+min, "Milli Seconds");
	}
}
