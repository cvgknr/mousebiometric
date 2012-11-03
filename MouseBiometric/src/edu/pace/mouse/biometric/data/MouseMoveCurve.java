package edu.pace.mouse.biometric.data;

import java.util.ArrayList;

public class MouseMoveCurve {
	private ArrayList<MouseMove> points;
	public MouseMoveCurve(ArrayList<MouseMove> p){
		this.points = p;
	}
	public ArrayList<MouseMove> getPoints() {
		return points;
	}
}
