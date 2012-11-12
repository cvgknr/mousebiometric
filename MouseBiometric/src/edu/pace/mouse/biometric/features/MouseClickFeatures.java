package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseClick;
import edu.pace.mouse.biometric.data.MouseDoubleClick;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MousePointer;
import edu.pace.mouse.biometric.util.Util;

/**
 * 
 * @author Venugopala C
 *
 */

public class MouseClickFeatures implements Feature{
	private int totalCount = 0;
	private int leftCount = 0;
	private int rightCount = 0;
	private int doubleCount = 0;
	/*private int leftMinutes = 0;
	private int rightMinutes = 0;
	private int doubleMinutes = 0;*/
	private int leftMin = 0;
	private int leftMax = 0;
	private int leftAvg = 0;
	private int rightMin = 0;
	private int rightMax = 0;
	private int rightAvg = 0;
	private int doubleMin = 0;
	private int doubleMax = 0;
	private int doubleAvg = 0;
	
	
	
	private long totalSessionTimeMin = 0;
	public MouseClickFeatures(MouseLogParser _parser){
		ArrayList<MouseClick> mouseClicks;
		ArrayList<MouseDoubleClick> mouseDoubleClicks;
		ArrayList<MousePointer> mousePointers;
		mouseClicks = _parser.getMouseClicks();
		mouseDoubleClicks = _parser.getMouseDoubleClicks();
		mousePointers = _parser.getMousePointers();
		
		for (MouseClick ele : mouseClicks) {
			if ("left".equals(ele.getButton())){
				leftCount++;
				//leftMinutes += ele.getMouseduration();
				if (ele.getMouseduration() < leftMin)
					leftMin = ele.getMouseduration();
				if (ele.getMouseduration() > leftMax)
					leftMax = ele.getMouseduration();
				leftAvg += ele.getMouseduration();
			}
			else if ("right".equals(ele.getButton())){
				rightCount++;
				//rightMinutes += ele.getMouseduration();
				if (ele.getMouseduration() < rightMin)
					rightMin = ele.getMouseduration();
				if (ele.getMouseduration() > rightMax)
					rightMax = ele.getMouseduration();
				rightAvg += ele.getMouseduration();
			}
		}
		if (leftCount > 0)
			leftAvg /= leftCount;
		if (rightCount > 0)
			rightCount /= rightCount;
		//leftMinutes /=60;
		//rightMinutes/=60;
		int duration;
		for (MouseDoubleClick ele :mouseDoubleClicks){
			duration = ele.getFirstClick().getMouseduration() + ele.getSecondClick().getMouseduration();
			//doubleMinutes += duration; 
			if (duration < doubleMin)
				doubleMin = duration;
			if (duration > doubleMax)
				doubleMax = duration;
			doubleAvg += duration;
		}
		if (mouseDoubleClicks.size() > 0)
			doubleAvg/= mouseDoubleClicks.size();
		//doubleMin/=60;
		totalCount = mouseClicks.size() + mouseDoubleClicks.size();
		if (mousePointers.size() > 0)
			totalSessionTimeMin =  mousePointers.get(mousePointers.size()-1).getEndtime() - mousePointers.get(0).getStarttime();
		totalSessionTimeMin /= 60;
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
		FeatureResult[] results = new FeatureResult[20];
		results[0] = new FeatureResult(getClass().getName(), "Total Number of Mouse Clicks", ""+totalCount, "Click(s)");
		results[1] = new FeatureResult(getClass().getName(), "Number of Left Mouse Clicks", ""+leftCount, "Click(s)");
		results[2] = new FeatureResult(getClass().getName(), "Number of Right Mouse Clicks", ""+rightCount, "Click(s)");
		results[3] = new FeatureResult(getClass().getName(), "Number of Double Mouse Clicks", ""+doubleCount, "Double Click(s)");
		results[4] = new FeatureResult(getClass().getName(), "Percentage of Left Mouse Clicks", ""+Util.format(((double)leftCount/totalCount*100)), "Click(s)");
		results[5] = new FeatureResult(getClass().getName(), "Percentage of Right Mouse Clicks", ""+Util.format(((double)rightCount/totalCount*100)), "Click(s)");
		results[6] = new FeatureResult(getClass().getName(), "Percentage of Left Mouse Clicks", ""+Util.format(((double)doubleCount/totalCount*100)), "Click(s)");
		results[7] = new FeatureResult(getClass().getName(), "Average number of Mouse Clicks per Minute", ""+ Util.format(((double)totalCount/totalSessionTimeMin*100)), "Click(s)/Minute");
		results[8] = new FeatureResult(getClass().getName(), "Average number of Left Mouse Clicks per Minute", "" + Util.format(((double)leftCount/totalSessionTimeMin*100)), "Click(s)/Minute");
		results[9] = new FeatureResult(getClass().getName(), "Average number of Right Mouse Clicks per Minute", "" + Util.format(((double)rightCount/totalSessionTimeMin*100)), "Click(s)/Minute");
		results[10] = new FeatureResult(getClass().getName(), "Average number of Double Mouse Clicks per Minute", "" + Util.format(((double)doubleCount/totalSessionTimeMin*100)), "Click(s)/Minute");
		
		results[11] = new FeatureResult(getClass().getName(), "Maximum dwell of all left mouse click", ""+ leftMax , "Seconds");
		results[12] = new FeatureResult(getClass().getName(), "Minimum dwell of all left mouse click", ""+ leftMax , "Seconds");
		results[13] = new FeatureResult(getClass().getName(), "Average dwell of all left mouse click", ""+ leftAvg , "Seconds");

		results[14] = new FeatureResult(getClass().getName(), "Maximum dwell of all right mouse click", ""+ rightMax , "Seconds");
		results[15] = new FeatureResult(getClass().getName(), "Minimum dwell of all right mouse click", ""+ rightMin , "Seconds");
		results[16] = new FeatureResult(getClass().getName(), "Average dwell of all right mouse click", ""+ rightAvg , "Seconds");

		results[17] = new FeatureResult(getClass().getName(), "Maximum transition time of double click", ""+ doubleMax, "Seconds");
		results[18] = new FeatureResult(getClass().getName(), "Minimum transition time of double click", ""+ doubleMin , "Seconds");
		results[19] = new FeatureResult(getClass().getName(), "Average transition time of double click", ""+ doubleAvg , "Seconds");
		return results;
	}
}
