package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MousePointer;
import edu.pace.biometric.mouse.util.Util;

public class MouseMoveAverageDistance extends MouseFeature{

	public MouseMoveAverageDistance(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		double sum=0;
		List<MousePointer> points=mouseParser.getMousePointers(appName);
		long length=points.size();
		for(MousePointer p:points){
			sum=sum+p.getDistancepix();
		}
		return new FeatureResult(getClass().getName(), "Average Distance of Mouse Movement", Util.format(sum/length), "Pixels");
	}
}
