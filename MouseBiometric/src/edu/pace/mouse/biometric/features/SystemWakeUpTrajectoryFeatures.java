package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMoveTrajectory;
import edu.pace.mouse.biometric.util.ComputStdMethodDouble;
import edu.pace.mouse.biometric.util.ComputStdMethodLong;
import edu.pace.mouse.biometric.util.Util;

public class SystemWakeUpTrajectoryFeatures implements Feature{
	private ArrayList<MouseMoveTrajectory> trajectories;
	public SystemWakeUpTrajectoryFeatures(MouseLogParser parser){
		trajectories = parser.getSystemWakeUpTranjectories();
	}
	public FeatureResult[] extract(){
		FeatureResult[] f = new FeatureResult[50];
		int index = 0;
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
		for (MouseMoveTrajectory t : trajectories) {
			points[i] = t.getNumnerOfPoints();
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
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Mean", Util.format(result.getMean()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Median", Util.format(result.getMedian()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Minimum", Util.format(result.getMin()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Maximum", Util.format(result.getMax()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Points Minimum", Util.format(result.getStddev()), "Points");

		result = new ComputStdMethodLong(time);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Mean", Util.format(result.getMean()), "Milliseconds");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Median", Util.format(result.getMedian()), "Milliseconds");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Minimum", Util.format(result.getMin()), "Milliseconds");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Maximum", Util.format(result.getMax()), "Milliseconds");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Time Minimum", Util.format(result.getStddev()), "Milliseconds");

		ComputStdMethodDouble dblresult = new ComputStdMethodDouble(distance);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Mean", Util.format(dblresult.getMean()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Median", Util.format(dblresult.getMedian()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Minimum", Util.format(dblresult.getMin()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Maximum", Util.format(dblresult.getMax()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Distance Minimum", Util.format(dblresult.getStddev()), "Pixels");

		dblresult = new ComputStdMethodDouble(length);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Mean", Util.format(dblresult.getMean()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Median", Util.format(dblresult.getMedian()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Minimum", Util.format(dblresult.getMin()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Maximum", Util.format(dblresult.getMax()), "Pixels");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Length Minimum", Util.format(dblresult.getStddev()), "Pixels");

		dblresult = new ComputStdMethodDouble(velocity);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Mean", Util.format(dblresult.getMean()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Median", Util.format(dblresult.getMedian()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Minimum", Util.format(dblresult.getMin()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Maximum", Util.format(dblresult.getMax()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Velocity Minimum", Util.format(dblresult.getStddev()), "Pixels/Second");

		
		dblresult = new ComputStdMethodDouble(acceleration);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Mean", Util.format(dblresult.getMean()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Median", Util.format(dblresult.getMedian()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Minimum", Util.format(dblresult.getMin()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Maximum", Util.format(dblresult.getMax()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Acceleration Minimum", Util.format(dblresult.getStddev()), "Pixels/Second");
		
		dblresult = new ComputStdMethodDouble(directionangle);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Mean", Util.format(dblresult.getMean()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Median", Util.format(dblresult.getMedian()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Minimum", Util.format(dblresult.getMin()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Maximum", Util.format(dblresult.getMax()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle Minimum", Util.format(dblresult.getStddev()), "Degree");
		
		dblresult = new ComputStdMethodDouble(changeangle);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Mean", Util.format(dblresult.getMean()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Median", Util.format(dblresult.getMedian()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Minimum", Util.format(dblresult.getMin()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Maximum", Util.format(dblresult.getMax()), "Degree");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Direction Angle change Minimum", Util.format(dblresult.getStddev()), "Degree");
		
		dblresult = new ComputStdMethodDouble(inflection);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Mean", Util.format(dblresult.getMean()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Median", Util.format(dblresult.getMedian()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Minimum", Util.format(dblresult.getMin()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Maximum", Util.format(dblresult.getMax()), "Points");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory Inflection Points Minimum", Util.format(dblresult.getStddev()), "Points");
		
		dblresult = new ComputStdMethodDouble(curviness);
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Mean", Util.format(dblresult.getMean()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Median", Util.format(dblresult.getMedian()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Minimum", Util.format(dblresult.getMin()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Maximum", Util.format(dblresult.getMax()), "Pixels/Second");
		f[index++] = new FeatureResult(getClass().getSimpleName(), "System Wake up Trajectory curviness  Minimum", Util.format(dblresult.getStddev()), "Pixels/Second");
		
		return f;
	}
}
