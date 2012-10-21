package edu.pace.biometric.mouse.features;

import java.util.List;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.MousePointer;
import edu.pace.biometric.mouse.util.Util;

public class AverageRateOfChangeOfSlopeInCurve extends MouseFeature {

	public AverageRateOfChangeOfSlopeInCurve(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult[] extract() {
		double sum=0;
		MousePointer mp=null;
		List<MousePointer> points=mouseParser.getMousePointers();
		int length=points.size();
		for(MousePointer p:points){
			/*if(mp!=null && mp.getSlope()!=0){
				sum+=((p.getSlope()-mp.getSlope())/mp.getSlope())*100;//this is rate of change in slope between 2 points
			}*/
			mp=p;
		}
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Average Rate of Change in Slope of Trajectory", ""+Util.format(sum/length), "% change in slope")};
	}
/*
	@Override
	public FeatureResult extract() {
		double sum=0;
		MousePointer mp=null;
		List<MousePointer> points=data.getPointers();
		int length=points.size();
		for(MousePointer p:points){
			if(mp!=null && mp.getSlope()!=0){
				sum+=((p.getSlope()-mp.getSlope())/mp.getSlope())*100;//this is rate of change in slope between 2 points
			}
			mp=p;
			
			
		}
		
		
		return new FeatureResult(getClass().getName(), "Average Rate of Change in Slope of Trajectory", ""+Util.format(sum/length), "% change in slope");
	}
*/
}
