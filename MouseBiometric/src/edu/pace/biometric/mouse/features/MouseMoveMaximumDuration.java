package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MousePointer;

public class MouseMoveMaximumDuration extends MouseFeature {

	public MouseMoveMaximumDuration(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int max=0;
		List<MousePointer> points=mouseParser.getMousePointers();
		for(MousePointer mp:points){
			if(mp.getDuration() > max){
				max=mp.getDuration();
			}
		}
		return new FeatureResult(getClass().getName(), "Maximum Duration of a Mouse Movement", ""+max, "Millseconds");
	}
}
