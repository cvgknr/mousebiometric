package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseWheelMove;
import edu.pace.mouse.biometric.util.Util;

/**
 * 
 * @author Venugopala C
 *
 */

public class MouseWheelScrollFeatures implements Feature{
	private int totalScroll = 0;
	private int scrollUp = 0;
	private int scrollDown = 0;

	private long totalScrollTime = 0;
	private double avgScrollTime = 0;
	private long maxScrollTime = 0;
	private long minScrollTime = 0;
	
	private double avgScrollUpTime = 0;
	private long maxScrollUpTime = 0;
	private long minScrollUpTime = 0;
	
	private double avgScrollDownTime = 0;
	private long maxScrollDownTime = 0;
	private long minScrollDownTime = 0;
	
	private double avgScrollDist = 0;
	private long maxScrollDist = 0;
	private long minScrollDist = 0;
	private double avgScrollUpDist = 0;
	private long maxScrollUpDist = 0;
	private long minScrollUpDist = 0;
	private double avgScrollDownDist = 0;
	private long maxScrollDownDist = 0;
	private long minScrollDownDist = 0;

	private double avgScrollSpeed = 0;
	private double maxScrollSpeed = 0;
	private double minScrollSpeed = 0;
	private double avgScrollUpSpeed = 0;
	private double maxScrollUpSpeed = 0;
	private double minScrollUpSpeed = 0;
	private double avgScrollDownSpeed = 0;
	private double maxScrollDownSpeed = 0;
	private double minScrollDownSpeed = 0;
	public MouseWheelScrollFeatures(MouseLogParser _parser){
		ArrayList<MouseWheelMove> mwm = _parser.getMouseWheelMoves();
		totalScroll = mwm.size();
		double speed = 0;
		for (MouseWheelMove mw : mwm) {
			speed = mw.getNumber() / (double)mw.getDuration();
			if (mw.getMoved() >=0 ){
				scrollUp++;
				if (0 == minScrollUpTime || mw.getDuration() < minScrollUpTime)
					minScrollUpTime = mw.getDuration();
				if (mw.getDuration() > maxScrollUpTime)
					maxScrollUpTime = mw.getDuration();	
				avgScrollUpTime += mw.getDuration();
				
				if (0 == minScrollUpDist || mw.getNumber() < minScrollUpDist)
					minScrollUpDist = mw.getNumber();
				if (mw.getNumber() > maxScrollUpDist)
					maxScrollUpDist = mw.getNumber();	
				avgScrollUpDist += mw.getNumber();

				if (0 == minScrollUpSpeed || speed < minScrollUpSpeed)
					minScrollUpSpeed = speed;
				if (speed > maxScrollUpSpeed)
					maxScrollUpSpeed = speed;	
				avgScrollUpSpeed += speed;

				
			}else{
				scrollDown++;
				if (0 == minScrollDownTime || mw.getDuration() < minScrollDownTime)
					minScrollDownTime = mw.getDuration();
				if (mw.getDuration() > maxScrollDownTime)
					maxScrollDownTime = mw.getDuration();	
				avgScrollDownTime += mw.getDuration();
				
				if (0 == minScrollDownDist || mw.getNumber() < minScrollDownDist)
					minScrollDownDist = mw.getNumber();
				if (mw.getNumber() > maxScrollDownDist)
					maxScrollDownDist = mw.getNumber();	
				avgScrollDownDist += mw.getNumber();
				
				if (0 == minScrollDownSpeed || speed < minScrollDownSpeed)
					minScrollDownSpeed = speed;
				if (speed > maxScrollDownSpeed)
					maxScrollDownSpeed = speed;	
				avgScrollDownSpeed += speed;
			}
			totalScrollTime += mw.getDuration();
			if (0 == minScrollTime || mw.getDuration() < minScrollTime)
				minScrollTime = mw.getDuration();
			if (mw.getDuration() > maxScrollTime)
				maxScrollTime = mw.getDuration();		

			avgScrollDist += mw.getNumber();
			if (0 == minScrollDist || mw.getDuration() < minScrollDist)
				minScrollDist = mw.getDuration();
			if (mw.getDuration() > maxScrollDist)
				maxScrollDist = mw.getDuration();		

			avgScrollSpeed += mw.getNumber();
			if (0 == minScrollSpeed || speed < minScrollSpeed)
				minScrollSpeed = speed;
			if (speed > maxScrollSpeed)
				maxScrollSpeed = speed;		
			
		}
		if (avgScrollTime > 0){
			avgScrollTime = totalScrollTime/(double)totalScroll;
			avgScrollDist /= totalScroll;
		}
		if (scrollUp > 0){
			avgScrollUpTime /= scrollUp;
			avgScrollUpDist /= scrollUp;
		}
		if (scrollDown > 0){
			avgScrollDownTime /= scrollDown;
			avgScrollDownDist /= scrollDown;
		}
		
	}

	/**
	 * Total number of wheel spin (scroll up and down) events in a sample
	 * Number of scroll up events in a sample
	 * Number of scroll down events in a sample
	 * Total time spent (duration) in wheel spin (scroll up and down) events in a sample – this is the sum of time for every scroll up and scroll down event extracted.
	 * Average duration for a wheel spin event – this is the average time of all the wheel spins events in a sample.
	 * Maximum duration for a wheel spin event – this is the maximum time of all the wheel spins events in a sample.
	 * Minimum duration for a wheel spin event - – this is the minimum time of all the wheel spins events in a sample
	 * Average duration for a scroll up event
	 * Maximum duration for a scroll up event
	 * Minimum duration for a scroll down event
	 * Average duration for a scroll down event
	 * Maximum duration for a scroll down event
	 * Minimum duration for a scroll down event
	 * Average scrolled distance for a wheel spin event (both scroll up and down)
	 * Maximum scrolled distance for a wheel spin event (both scroll up and down)
	 * Minimum scrolled distance for a wheel spin event (both scroll up and down)
	 * Average scrolled distance for a scroll up event
	 * Maximum scrolled distance for a scroll up event
	 * Minimum scrolled distance for a scroll up event
	 * Average scrolled distance for a scroll down event
	 * Maximum scrolled distance for a scroll down event
	 * Minimum scrolled distance for a scroll down event
	 * Average speed of a wheel spin event – this is the average of scrolled distance by time (duration) of a scroll.
	 * Maximum speed of a wheel spin event - this is the maximum of scrolled distance by time (duration) of a scroll.
	 * Minimum speed of a wheel spin event - this is the minimum of scrolled distance by time (duration) of a scroll.
	 * Average speed for a scroll up event
	 * Maximum speed for a scroll up event
	 * Minimum speed for a scroll up event
	 * Average speed for a scroll down event
	 * Maximum speed for a scroll down event
	 * Minimum speed for a scroll down event
	 */
	
			
	@Override
	public ArrayList<FeatureResult> extract() {
		ArrayList<FeatureResult> results = new ArrayList<FeatureResult>(31);
		results.add(new FeatureResult(getClass().getSimpleName(), "Total number of wheel spin (scroll up and down) events in a sample", ""+ totalScroll , "Scrolls"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Number of scroll up events in a sample", ""+ scrollUp , "Scrolls"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Number of scroll down events in a sample", ""+ scrollDown , "Scrolls"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Total time spent (duration) in wheel spin (scroll up and down)", ""+ totalScrollTime , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Average duration for a wheel spin event", Util.format(avgScrollTime) , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum duration for a wheel spin event", ""+ maxScrollTime , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum duration for a wheel spin event", ""+ minScrollTime , "Seconds"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average duration for a scroll up event", Util.format(avgScrollUpTime) , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum duration for a scroll up event", ""+ maxScrollUpTime , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum duration for a scroll down event", ""+ minScrollUpTime , "Seconds"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average duration for a scroll down event", Util.format(avgScrollDownTime) , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum duration for a scroll down event", ""+ maxScrollDownTime , "Seconds"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum duration for a scroll down event", ""+ minScrollDownTime, "Seconds"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average scrolled distance for a wheel spin event (both scroll up and down)", Util.format(avgScrollDist) , "Pixels"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum scrolled distance for a wheel spin event (both scroll up and down)", ""+ maxScrollDist , "Pixels"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum scrolled distance for a wheel spin event (both scroll up and down)", ""+ minScrollDist , "Pixels"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average scrolled distance for a scroll up event", Util.format(avgScrollUpDist) , "Pixels"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum scrolled distance for a scroll up event", ""+ maxScrollUpDist , "Pixels"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum scrolled distance for a scroll up event", ""+ minScrollUpDist , "Pixels"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average scrolled distance for a scroll down event", Util.format(avgScrollDownDist) , "Pixels"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum scrolled distance for a scroll down event", ""+ maxScrollDownDist , "Pixels"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum scrolled distance for a scroll down event", ""+ minScrollDownDist , "Pixels"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average speed of a wheel scroll event ", Util.format(avgScrollSpeed) , "Pixels Per Second"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum speed of a wheel scroll event", Util.format(maxScrollSpeed) , "Pixels Per Second"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum speed of a wheel scroll event ", Util.format(minScrollSpeed) , "Pixels Per Second"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average speed for a scroll up event", Util.format(avgScrollUpSpeed) , "Pixels Per Second"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum speed for a scroll up event", Util.format(maxScrollUpSpeed) , "Pixels Per Second"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum speed for a scroll up event", Util.format(minScrollUpSpeed) , "Pixels Per Second"));
		
		results.add(new FeatureResult(getClass().getSimpleName(), "Average speed for a scroll down event", Util.format(avgScrollDownSpeed) , "Pixels Per Second"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Maximum speed for a scroll down event", Util.format(maxScrollDownSpeed) , "Pixels Per Second"));
		results.add(new FeatureResult(getClass().getSimpleName(), "Minimum speed for a scroll down event", Util.format(minScrollDownSpeed) , "Pixels Per Second"));
		return results;
	}
}
