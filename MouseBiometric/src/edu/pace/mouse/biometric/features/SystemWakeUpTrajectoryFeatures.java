package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseTrajectory;
import edu.pace.mouse.biometric.util.ComputStdMethodDouble;
import edu.pace.mouse.biometric.util.ComputStdMethodLong;
import edu.pace.mouse.biometric.util.Util;

public class SystemWakeUpTrajectoryFeatures implements Feature{
	private ArrayList<MouseTrajectory> trajectories;
	public SystemWakeUpTrajectoryFeatures(MouseLogParser parser){
		trajectories = parser.getSystemWakeUpTranjectories();
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
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Mean", Util.format(result.getMean()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Median", Util.format(result.getMedian()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Minimum", Util.format(result.getMin()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Maximum", Util.format(result.getMax()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Standard Deviation", Util.format(result.getStddev()), "Points"));

		result = new ComputStdMethodLong(time);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Mean", Util.format(result.getMean()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Median", Util.format(result.getMedian()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Minimum", Util.format(result.getMin()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Maximum", Util.format(result.getMax()), "Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Standard Deviation", Util.format(result.getStddev()), "Milliseconds"));

		ComputStdMethodDouble dblresult = new ComputStdMethodDouble(distance);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Mean", Util.format(dblresult.getMean()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Median", Util.format(dblresult.getMedian()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Minimum", Util.format(dblresult.getMin()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Maximum", Util.format(dblresult.getMax()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Standard Deviation", Util.format(dblresult.getStddev()), "Pixels"));

		dblresult = new ComputStdMethodDouble(length);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Mean", Util.format(dblresult.getMean()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Median", Util.format(dblresult.getMedian()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Minimum", Util.format(dblresult.getMin()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Maximum", Util.format(dblresult.getMax()), "Pixels"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Standard Deviation", Util.format(dblresult.getStddev()), "Pixels"));

		dblresult = new ComputStdMethodDouble(velocity);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Mean", Util.format(dblresult.getMean()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Median", Util.format(dblresult.getMedian()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Minimum", Util.format(dblresult.getMin()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Maximum", Util.format(dblresult.getMax()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Standard Deviation", Util.format(dblresult.getStddev()), "Pixels/Milliseconds"));

		
		dblresult = new ComputStdMethodDouble(acceleration);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Mean", Util.format(dblresult.getMean()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Median", Util.format(dblresult.getMedian()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Minimum", Util.format(dblresult.getMin()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Maximum", Util.format(dblresult.getMax()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Standard Deviation", Util.format(dblresult.getStddev()), "Pixels/Milliseconds"));
		
		dblresult = new ComputStdMethodDouble(directionangle);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Mean", Util.format(dblresult.getMean()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Median", Util.format(dblresult.getMedian()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Minimum", Util.format(dblresult.getMin()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Maximum", Util.format(dblresult.getMax()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Standard Deviation", Util.format(dblresult.getStddev()), "Degree"));
		
		dblresult = new ComputStdMethodDouble(changeangle);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Mean", Util.format(dblresult.getMean()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Median", Util.format(dblresult.getMedian()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Minimum", Util.format(dblresult.getMin()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Maximum", Util.format(dblresult.getMax()), "Degree"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Standard Deviation", Util.format(dblresult.getStddev()), "Degree"));
		
		dblresult = new ComputStdMethodDouble(inflection);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Mean", Util.format(dblresult.getMean()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Median", Util.format(dblresult.getMedian()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Minimum", Util.format(dblresult.getMin()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Maximum", Util.format(dblresult.getMax()), "Points"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Standard Deviation", Util.format(dblresult.getStddev()), "Points"));
		
		dblresult = new ComputStdMethodDouble(curviness);
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Mean", Util.format(dblresult.getMean()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Median", Util.format(dblresult.getMedian()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Minimum", Util.format(dblresult.getMin()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Maximum", Util.format(dblresult.getMax()), "Pixels/Milliseconds"));
		f.add(new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Standard Deviation", Util.format(dblresult.getStddev()), "Pixels/Milliseconds"));
		
		return f;
	}
}
