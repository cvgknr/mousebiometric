package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MousePointer;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveRateOfChangeOfCurve implements Feature{
	private ArrayList<MousePointer> mpointers;
	public MouseMoveRateOfChangeOfCurve(MouseLogParser _Parser){
		mpointers = _Parser.getMousePointers();
	}
	public FeatureResult[] extract(){
		double sum = 0;
		MousePointer mp=null;
		int length=mpointers.size();
		for(MousePointer p:mpointers){
			if(mp!=null && mp.getSlope()!=0){
				sum+=((p.getSlope()-mp.getSlope())/mp.getSlope())*100;//this is rate of change in slope between 2 points
			}
			mp=p;
		}
		return new FeatureResult[]{new FeatureResult(getClass().getName(), "Average Rate of Change in Curve Slope", ""+Util.format(sum/length), "% change in slope")};
	}
}
