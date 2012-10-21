package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MousePointer;

public class MouseMoveAverageDuration extends MouseFeature {
	public MouseMoveAverageDuration(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int sum=0;
		List<MousePointer> points=mouseParser.getMousePointers(appName);
		int length=points.size();
		for(MousePointer p:points){
			sum=sum+p.getDuration();
		}
		return new FeatureResult(getClass().getName(), "Average Duration of a Mouse Movement", ""+(sum/length), "Milli Seconds");
	}
}