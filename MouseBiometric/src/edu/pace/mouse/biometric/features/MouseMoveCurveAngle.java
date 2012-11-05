package edu.pace.mouse.biometric.features;

import java.util.ArrayList;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MouseMoveCurve;
import edu.pace.mouse.biometric.util.Util;


public class MouseMoveCurveAngle implements Feature{
	private ArrayList<MouseMove> moves;
	public MouseMoveCurveAngle(MouseLogParser _Parser){
		moves = _Parser.getMouseMoves();
	}
	public MouseMoveCurveAngle(ArrayList<MouseMove> moves){
		this.moves = moves;
	}
	
	private double getSlope(MouseMove a, MouseMove b){
		double ydiff = a.getYpix() - b.getYpix();
		if (a.getXpix() == b.getXpix())
			return ydiff / 0.00000001;
		else
			return ydiff/ (a.getXpix() - b.getXpix());
	}
	private boolean hasSignChanged(double f, double s){
		if ((f<0 && s<0) ||(f>=0 && s>=0))
			return false;
		else
			return true;
	}
	public FeatureResult[] extract(){
		MouseMove _pt = null;
		ArrayList<MouseMoveCurve> curves = new ArrayList<MouseMoveCurve>(10);
		ArrayList<MouseMove> points = new ArrayList<MouseMove>(10);
		double slope=0, newslope;
		int i = 0;
		for (MouseMove mouseMove : moves) {
			if (null != _pt){
				newslope = getSlope(_pt, mouseMove);
				if(points.size() > 5 && hasSignChanged(slope, newslope)){
					curves.add(new MouseMoveCurve(points));
					points = new ArrayList<MouseMove>(10);
				}
				points.add(mouseMove);
			}else
				points.add(mouseMove);
			_pt = mouseMove;
		}
		System.out.println("Number of curves :" + curves.size());
		return null;
		//return new FeatureResult[]{new FeatureResult(getClass().getName(), "", Util.format(sum), "px")};
	}
}
