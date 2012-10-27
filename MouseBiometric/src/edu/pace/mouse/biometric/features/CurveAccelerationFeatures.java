package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseClick;
import edu.pace.mouse.biometric.data.MouseDoubleClick;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.util.Util;

/**
 * 
 * @author Venugopala C
 *
 */

public class CurveAccelerationFeatures implements Feature{
	private ArrayList<MouseClick> mouseClicks;
	private ArrayList<MouseDoubleClick> mouseDoubleClicks;
	private int totalCount = 0;
	private int leftCount = 0;
	private int rightCount = 0;
	private int leftSec = 0;
	private int rightSec = 0;
	private int doubleSec = 0;
	public CurveAccelerationFeatures(MouseLogParser _parser){
		this.mouseClicks = _parser.getMouseClicks();
		this.mouseDoubleClicks = _parser.getMouseDoubleClicks();
		MouseClick lm = null;
		MouseClick rm = null;
		for (MouseClick ele : mouseClicks) {
			if ("left".equals(ele.getButton()))
				leftCount++;
			else if ("right".equals(ele.getButton()))
				rightCount++;
		}
		totalCount = mouseClicks.size() + mouseDoubleClicks.size();
	}

	/*
	 * 1. Total number of mouse clicks in a sample
	 * 2. Number of left mouse clicks in a sample
	 * 3. Number of right mouse clicks in a sample
	 * 4. Number of double clicks in a sample
	 * 5. Percentage of left clicks compared to total number of mouse clicks in a sample
	 * 6. Percentage of right clicks compared to total number of mouse clicks in a sample
	 * 7. Percentage of double clicks compared to total number of mouse clicks in a sample
	 * 8. Average number of mouse clicks per minute
	 * 9. Average number of left mouse clicks per minute
	 * 10. Average number of right double clicks per minute
	 * 11. Average number of double clicks per minute
	*/
			
	@Override
	public FeatureResult[] extract() {
		FeatureResult[] results = new FeatureResult[11];
		results[0] = new FeatureResult(getClass().getName(), "Total Number of Mouse Clicks", ""+totalCount, "Click(s)");
		results[1] = new FeatureResult(getClass().getName(), "Number of Left Mouse Clicks", ""+leftCount, "Click(s)");
		results[2] = new FeatureResult(getClass().getName(), "Number of Right Mouse Clicks", ""+rightCount, "Click(s)");
		results[3] = new FeatureResult(getClass().getName(), "Number of Double Mouse Clicks", ""+mouseDoubleClicks.size(), "Double Click(s)");
		results[4] = new FeatureResult(getClass().getName(), "Percentage of Left Mouse Clicks", ""+Util.format(((double)leftCount/totalCount*100)), "Click(s)");
		results[5] = new FeatureResult(getClass().getName(), "Percentage of Right Mouse Clicks", ""+Util.format(((double)rightCount/totalCount*100)), "Click(s)");
		results[6] = new FeatureResult(getClass().getName(), "Percentage of Left Mouse Clicks", ""+Util.format(((double)mouseDoubleClicks.size()/totalCount*100)), "Click(s)");
		results[7] = new FeatureResult(getClass().getName(), "Average number of Mouse Clicks per Minute", "", "Click(s)/Minute");
		results[8] = new FeatureResult(getClass().getName(), "Average number of Left Mouse Clicks per Minute", "", "Click(s)/Minute");
		results[9] = new FeatureResult(getClass().getName(), "Average number of Right Mouse Clicks per Minute", "", "Click(s)/Minute");
		results[10] = new FeatureResult(getClass().getName(), "Average number of Double Mouse Clicks per Minute", "", "Click(s)/Minute");
		return results;
	}
}
