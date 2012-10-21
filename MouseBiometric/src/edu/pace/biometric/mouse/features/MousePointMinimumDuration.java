package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MousePointer;

public class MousePointMinimumDuration extends MouseFeature {

	public MousePointMinimumDuration(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int min=Integer.MAX_VALUE;
		List<MousePointer> points=mouseParser.getMousePointers();
		for(MousePointer mp:points){			
			if(mp.getDuration() < min){
				min=mp.getDuration();
			}	
		}
		return new FeatureResult(getClass().getName(), "Minimum Duration of a Mouse Movement", ""+min, "Milliseconds");
	}
}
