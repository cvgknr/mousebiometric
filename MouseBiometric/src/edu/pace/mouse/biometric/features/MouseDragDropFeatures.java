package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseDragDropTrajectory;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.util.Util;

/**
 * 
 * @author Venugopala C
 *
 */

public class MouseDragDropFeatures implements Feature{
	private double min = 0;
	private double max = 0;
	private double avg = 0;
	public MouseDragDropFeatures(MouseLogParser _parser){
		ArrayList<MouseDragDropTrajectory> ddt = _parser.getMouseDragDropTrajectory();
		double distance=0;
		for (MouseDragDropTrajectory dd : ddt) {
			distance = dd.getDistance() ;
			if(distance < min);
				min = distance;
			if (distance > max)
				max = distance;
			avg += distance;
		}
		if (ddt.size() > 0 )
		avg /= ddt.size();
	}

	/**
	 * III.	Average distance for a mouse click (Drag and Drop length)
	 * A drag and drop event occurs when a user presses a mouse button and moves the mouse over a distance or pattern before releasing the button. 
	 * The average of the distance travelled during all the mouse click events in the sample is calculated.
	 * Average distance for drag and drop = 1/n sumOf(sqrt((Xi-Xi-1)^2 + (Yi - Yi-1)^ 2), 
	 * Where Xi ,Yi is the point coordinate at the drop location and Xi - 1, Yi – 1 is the coordinate at the start of drag.
	 * 
	 * Below mentioned are associated derived features from drag and drop length which will be implemented.
	 * Maximum distance travelled during a mouse click
	 * Minimum distance travelled during a mouse click
	 */
	
			
	@Override
	public FeatureResult[] extract() {
		FeatureResult[] results = new FeatureResult[3];
		
		results[0] = new FeatureResult(getClass().getName(), "Maximum distance travelled (drag-drop) during mouse click", ""+ Util.format(max) , "Pixels");
		results[1] = new FeatureResult(getClass().getName(), "Minimum distance travelled (drag-drop) during mouse click", ""+ Util.format(min) , "Pixels");
		results[2] = new FeatureResult(getClass().getName(), "Average distance travelled (drag-drop) during mouse click", ""+ Util.format(avg) , "Pixels");
		return results;
	}
}
