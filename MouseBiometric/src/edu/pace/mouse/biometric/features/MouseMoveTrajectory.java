package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MouseMoveCurve;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveTrajectory implements Feature{
	private MouseLogParser parser;
	private ArrayList<MouseMoveCurve> curves;
	private long sumSize=0,sumLength =0,sumTime=0, minSize=0, maxSize=0, minLength=0, maxLength=0, minTime =0, maxTime=0;
	private double sumSpeed = 0,sumAngle = 0, minSpeed=0, maxSpeed=0, minAngle=0, maxAngle=0, minOfMinAngle=0, maxOfMaxAngle=0;
	public MouseMoveTrajectory(MouseLogParser _Parser){
		parser = _Parser;
		curves = parser.getMouseCurves();
	}
	void computeCurveSize(MouseMoveCurve curve){
		long size = curves.size();
		if (size < minSize)
			minSize = size;
		if (maxSize > size)
			maxSize = size;
		sumSize += size;
	}
	void computeCurveLength(MouseMoveCurve curve){
		long size = Long.parseLong(new MouseMoveCurveLength(curve.getPoints()).extract()[0].getValue());
		if (size < minLength)
			minLength = size;
		if (maxLength > size)
			maxLength = size;
		sumLength+= size;
	}
	void computeCurveTime(MouseMoveCurve curve){
		long size = Long.parseLong(new MouseMoveTotalCurveTime(curve.getPoints()).extract()[0].getValue());
		if (size < minTime)
			minTime = size;
		if (maxTime > size)
			maxTime = size;
		sumTime+= size;
	}
	void computeCurveSpeed(MouseMoveCurve curve){
		//TODO -- pending 
	}
	void computeCurveAngle(MouseMoveCurve curve){
		long size = Long.parseLong(new MouseMoveCurveAngle(curve.getPoints()).extract()[0].getValue());
		if (size < minAngle)
			minAngle = size;
		if (maxAngle > size)
			maxAngle = size;
		sumAngle+= size;
		
		FeatureResult [] f = new MouseMoveAverageAngleOfCurve(curve.getPoints()).extract();
		size = Long.parseLong(f[1].getValue());
		if (size < minOfMinAngle)
			minOfMinAngle = size;
		
		size = Long.parseLong(f[2].getValue());
		if (maxOfMaxAngle > size)
			maxOfMaxAngle = size;
	}
	
	public FeatureResult[] extract(){
		ArrayList<MouseMoveCurve> curves = new ArrayList<MouseMoveCurve>(10);
		for (MouseMoveCurve mouseMoveCurve : curves) {
			computeCurveSize(mouseMoveCurve);
			computeCurveLength(mouseMoveCurve);
			computeCurveTime(mouseMoveCurve);
			computeCurveSpeed(mouseMoveCurve);
			computeCurveAngle(mouseMoveCurve);
		}
		long curveNo = curves.size();
		long avgSize = sumSize / curveNo;
		long avgLength = sumLength / curveNo;
		long avgTime = sumTime / curveNo;
		double avgSpeed = sumSpeed / curveNo;
		double avgAngle = sumAngle / curveNo;
		
		return new FeatureResult[]{
				new FeatureResult(getClass().getName(), "Average size of a curve in a trajectory", Util.format(avgSize), "Points"),
				new FeatureResult(getClass().getName(), "Minimum size of a curve in a trajectory", Util.format(minSize), "Points"),
				new FeatureResult(getClass().getName(), "Maximum size of a curve in a trajectory", Util.format(maxSize), "Points"),

				new FeatureResult(getClass().getName(), "Average length of a curve in a trajectory", Util.format(avgLength), "Pixels"),
				new FeatureResult(getClass().getName(), "Minimum length of a curve in a trajectory", Util.format(minLength), "Pixels"),
				new FeatureResult(getClass().getName(), "Maximum length of a curve in a trajectory", Util.format(maxLength), "Pixels"),

				new FeatureResult(getClass().getName(), "Average Time of a curve in a trajectory", Util.format(avgTime), "Milliseconds"),
				new FeatureResult(getClass().getName(), "Minimum Time of a curve in a trajectory", Util.format(minTime), "Milliseconds"),
				new FeatureResult(getClass().getName(), "Maximum Time of a curve in a trajectory", Util.format(maxTime), "Milliseconds"),

				new FeatureResult(getClass().getName(), "Average Angle of a curve in a trajectory", Util.format(avgAngle), "Degree"),
				new FeatureResult(getClass().getName(), "Minimum Angle of a curve in a trajectory", Util.format(minAngle), "Degree"),
				new FeatureResult(getClass().getName(), "Maximum Angle of a curve in a trajectory", Util.format(maxAngle), "Degree"),
				new FeatureResult(getClass().getName(), "Minimum of Minimum Angle of a curve in a trajectory", Util.format(minOfMinAngle), "Degree"),
				new FeatureResult(getClass().getName(), "Maximum of Maximum Angle of a curve in a trajectory", Util.format(maxOfMaxAngle), "Degree"),

				};
	}
}
