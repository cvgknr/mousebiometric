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
/**
 * Implementation to extract all MouseClick related features.
 */
public class MouseClickFeatures implements Feature{
	private long totalCount = 0;
	private long leftCount = 0;
	private long rightCount = 0;
	private long doubleCount = 0;
	private double leftTimeMin = 0;
	private double leftTimeMax = 0;
	private double leftTimeAvg = 0;
	private double rightTimeMin = 0;
	private double rightTimeMax = 0;
	private double rightTimeAvg = 0;
	private double doubleTimeMin = 0;
	private double doubleTimeMax = 0;
	private double doubleTimeAvg = 0;
	private double totalSessionTimeMin = 0;
	
	private double perLeft = 0;
	private double perRight = 0;
	private double perDouble = 0;
	private double avgClicks = 0;
	private double avgLeft = 0;
	private double avgRight = 0;
	private double avgDouble = 0;

	public MouseClickFeatures(MouseLogParser _parser){
		ArrayList<MouseClick> mouseClicks;
		ArrayList<MouseDoubleClick> mouseDoubleClicks;
		ArrayList<MousePointer> mousePointers;
		mouseClicks = _parser.getMouseClicks();
		mouseDoubleClicks = _parser.getMouseDoubleClicks();
		mousePointers = _parser.getMousePointers();
		long duration;
		for (MouseClick ele : mouseClicks) {
			duration = ele.getMouseduration();
			if ("left".equals(ele.getButton())){
				leftCount++;
				if (0 == leftTimeMin || duration < leftTimeMin)
					leftTimeMin = duration;
				
				if (duration > leftTimeMax)
					leftTimeMax = duration;
				leftTimeAvg += duration;
			}
			else if ("right".equals(ele.getButton())){
				rightCount++;
				if (0 == rightTimeMin || duration < rightTimeMin)
					rightTimeMin = duration;
				
				if (duration > rightTimeMax)
					rightTimeMax = duration;
				rightTimeAvg += duration;
			}
		}
		//Total number of mouse clicks
		totalCount = mouseClicks.size() + mouseDoubleClicks.size();
		doubleCount = mouseDoubleClicks.size();
		if (leftCount > 0)
			leftTimeAvg /= leftCount;
		
		if (rightCount > 0)
			rightCount /= rightCount;
		
		for (MouseDoubleClick ele :mouseDoubleClicks){
			duration = ele.getFirstClick().getMouseduration() + ele.getSecondClick().getMouseduration();
			if (0 == doubleTimeMin || duration < doubleTimeMin)
				doubleTimeMin = duration;
			if (duration > doubleTimeMax)
				doubleTimeMax = duration;
			doubleTimeAvg += duration;
		}
		if (mouseDoubleClicks.size() > 0)
			doubleTimeAvg/= mouseDoubleClicks.size();
		if (mousePointers.size() > 0)
			totalSessionTimeMin =  mousePointers.get(mousePointers.size()-1).getEndtime() - mousePointers.get(0).getStarttime();
		totalSessionTimeMin /= 1000.0*60;
		
		if (totalCount > 0){
			perLeft = ((double)leftCount/totalCount*100);
			perRight = ((double)rightCount/totalCount*100);
			perDouble = ((double)doubleCount/totalCount*100);
		}
		if (totalSessionTimeMin > 0){
			avgClicks = ((double)totalCount/totalSessionTimeMin);
			avgLeft = ((double)leftCount/totalSessionTimeMin);
			avgRight = ((double)rightCount/totalSessionTimeMin);
			avgDouble = ((double)doubleCount/totalSessionTimeMin);
		}
			
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
		results[0] = new FeatureResult(getClass().getSimpleName(), "Total Number of Mouse Clicks", ""+totalCount, "Click(s)");
		
		results[1] = new FeatureResult(getClass().getSimpleName(), "Number of Left Mouse Clicks", ""+leftCount, "Click(s)");
		results[2] = new FeatureResult(getClass().getSimpleName(), "Number of Right Mouse Clicks", ""+rightCount, "Click(s)");
		results[3] = new FeatureResult(getClass().getSimpleName(), "Number of Double Mouse Clicks", ""+doubleCount, "Double Click(s)");
		
		results[4] = new FeatureResult(getClass().getSimpleName(), "Percentage of Left Mouse Clicks", ""+Util.format(perLeft), "Click(s)");
		results[5] = new FeatureResult(getClass().getSimpleName(), "Percentage of Right Mouse Clicks", ""+Util.format(perRight), "Click(s)");
		results[6] = new FeatureResult(getClass().getSimpleName(), "Percentage of Double Mouse Clicks", ""+Util.format(perDouble), "Click(s)");
		
		results[7] = new FeatureResult(getClass().getSimpleName(), "Average number of Mouse Clicks per Minute", ""+ Util.format(avgClicks), "Click(s)/Minute");
		results[8] = new FeatureResult(getClass().getSimpleName(), "Average number of Left Mouse Clicks per Minute", "" + Util.format(avgLeft), "Click(s)/Minute");
		results[9] = new FeatureResult(getClass().getSimpleName(), "Average number of Right Mouse Clicks per Minute", "" + Util.format(avgRight), "Click(s)/Minute");
		results[10] = new FeatureResult(getClass().getSimpleName(), "Average number of Double Mouse Clicks per Minute", "" + Util.format(avgDouble), "Click(s)/Minute");
		
		results[11] = new FeatureResult(getClass().getSimpleName(), "Maximum dwell of all left mouse click", ""+ leftTimeMax , "Seconds");
		results[12] = new FeatureResult(getClass().getSimpleName(), "Minimum dwell of all left mouse click", ""+ leftTimeMin , "Seconds");
		results[13] = new FeatureResult(getClass().getSimpleName(), "Average dwell of all left mouse click", ""+ leftTimeAvg , "Seconds");

		results[14] = new FeatureResult(getClass().getSimpleName(), "Maximum dwell of all right mouse click", ""+ rightTimeMax , "Seconds");
		results[15] = new FeatureResult(getClass().getSimpleName(), "Minimum dwell of all right mouse click", ""+ rightTimeMin , "Seconds");
		results[16] = new FeatureResult(getClass().getSimpleName(), "Average dwell of all right mouse click", ""+ rightTimeAvg , "Seconds");

		results[17] = new FeatureResult(getClass().getSimpleName(), "Maximum transition time of double click", ""+ doubleTimeMax, "Seconds");
		results[18] = new FeatureResult(getClass().getSimpleName(), "Minimum transition time of double click", ""+ doubleTimeMin , "Seconds");
		results[19] = new FeatureResult(getClass().getSimpleName(), "Average transition time of double click", ""+ doubleTimeAvg , "Seconds");
		return results;
	}
}
