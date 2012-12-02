package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseDragDropTrajectory;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseTrajectory;
import edu.pace.mouse.biometric.util.ComputStdMethodDouble;
import edu.pace.mouse.biometric.util.ComputStdMethodLong;
import edu.pace.mouse.biometric.util.Util;

public class MoveDragDropTrajectoryFeatures implements Feature{
	private ArrayList<MouseDragDropTrajectory> trajectories;
	public MoveDragDropTrajectoryFeatures(MouseLogParser parser){
		trajectories = parser.getMouseDragDropTrajectory();
	}
	public ArrayList<FeatureResult> extract(){
		ArrayList<FeatureResult> f = new ArrayList<FeatureResult>(50);
		long []points = new long[trajectories.size()];
		long []time = new long[trajectories.size()];
		double []distance = new double[trajectories.size()];
		double []length = new double[trajectories.size()];
		double []velocity = new double[trajectories.size()];
		double []acceleration = new double[trajectories.size()];
		double []directionangle = new double[trajectories.size()];
		double []changeangle = new double[trajectories.size()];
		double []inflection = new double[trajectories.size()];
		double []curviness = new double[trajectories.size()];
		
		int i =0;
		for (MouseTrajectory t : trajectories) {
			points[i] = t.getNumberOfPoints();
			time[i] = t.getTotalTime();
			distance[i] = t.getDistance();
			length[i] = t.getLength();
			velocity[i] = t.getVelocity();
			acceleration[i] = t.getAcceleration();
			directionangle[i] = t.getDirectionAngle();
			changeangle[i] = t.getDirectionAngleChange();
			inflection[i] = t.getInflectionPoints();
			curviness[i] = t.getCurviness();
			i++;
		}
		ComputStdMethodLong result = new ComputStdMethodLong(points);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Points Mean", Util.format(result.getMean()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Points Median", Util.format(result.getMedian()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Points Minimum", Util.format(result.getMin()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Points Maximum", Util.format(result.getMax()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Points Minimum", Util.format(result.getStddev()), "Points"));

		result = new ComputStdMethodLong(time);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Time Mean", Util.format(result.getMean()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Time Median", Util.format(result.getMedian()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Time Minimum", Util.format(result.getMin()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Time Maximum", Util.format(result.getMax()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Time Minimum", Util.format(result.getStddev()), "Milliseconds"));

		ComputStdMethodDouble dblresult = new ComputStdMethodDouble(distance);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Distance Mean", Util.format(dblresult.getMean()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Distance Median", Util.format(dblresult.getMedian()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Distance Minimum", Util.format(dblresult.getMin()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Distance Maximum", Util.format(dblresult.getMax()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Distance Minimum", Util.format(dblresult.getStddev()), "Pixels"));

		dblresult = new ComputStdMethodDouble(length);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Length Mean", Util.format(dblresult.getMean()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Length Median", Util.format(dblresult.getMedian()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Length Minimum", Util.format(dblresult.getMin()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Length Maximum", Util.format(dblresult.getMax()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Length Minimum", Util.format(dblresult.getStddev()), "Pixels"));

		dblresult = new ComputStdMethodDouble(velocity);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Velocity Mean", Util.format(dblresult.getMean()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Velocity Median", Util.format(dblresult.getMedian()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Velocity Minimum", Util.format(dblresult.getMin()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Velocity Maximum", Util.format(dblresult.getMax()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Velocity Minimum", Util.format(dblresult.getStddev()), "Pixels/Second"));

		
		dblresult = new ComputStdMethodDouble(acceleration);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Acceleration Mean", Util.format(dblresult.getMean()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Acceleration Median", Util.format(dblresult.getMedian()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Acceleration Minimum", Util.format(dblresult.getMin()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Acceleration Maximum", Util.format(dblresult.getMax()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Acceleration Minimum", Util.format(dblresult.getStddev()), "Pixels/Second"));
		
		dblresult = new ComputStdMethodDouble(directionangle);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle Mean", Util.format(dblresult.getMean()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle Median", Util.format(dblresult.getMedian()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle Minimum", Util.format(dblresult.getMin()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle Maximum", Util.format(dblresult.getMax()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle Minimum", Util.format(dblresult.getStddev()), "Degree"));
		
		dblresult = new ComputStdMethodDouble(changeangle);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle change Mean", Util.format(dblresult.getMean()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle change Median", Util.format(dblresult.getMedian()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle change Minimum", Util.format(dblresult.getMin()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle change Maximum", Util.format(dblresult.getMax()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Direction Angle change Minimum", Util.format(dblresult.getStddev()), "Degree"));
		
		dblresult = new ComputStdMethodDouble(inflection);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Inflection Points Mean", Util.format(dblresult.getMean()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Inflection Points Median", Util.format(dblresult.getMedian()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Inflection Points Minimum", Util.format(dblresult.getMin()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Inflection Points Maximum", Util.format(dblresult.getMax()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory Inflection Points Minimum", Util.format(dblresult.getStddev()), "Points"));
		
		dblresult = new ComputStdMethodDouble(curviness);
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory curviness  Mean", Util.format(dblresult.getMean()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory curviness  Median", Util.format(dblresult.getMedian()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory curviness  Minimum", Util.format(dblresult.getMin()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory curviness  Maximum", Util.format(dblresult.getMax()), "Pixels/Second"));
		f.add(new FeatureResult(getClass().getSimpleName(), "Mouse drag and drop Trajectory curviness  Minimum", Util.format(dblresult.getStddev()), "Pixels/Second"));
		
		return f;
	}
}
