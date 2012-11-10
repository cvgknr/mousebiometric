package edu.pace.mouse.biometric.data;

import java.util.ArrayList;

/**
 * 
 * @author Venugopala C
 *
 */

public class MouseMoveTrajectory {
	private MousePointer mousePointer;
	private ArrayList<MouseMove> mouseMoves;
	public MouseMoveTrajectory(MousePointer mp,ArrayList<MouseMove> mm ){
		this.mousePointer = mp;
		this.mouseMoves = mm;
	}
	
	public void print(){
	}

	public MousePointer getMousePointer() {
		return mousePointer;
	}

	public ArrayList<MouseMove> getMouseMoves() {
		return mouseMoves;
	}
	public long getNumnerOfPoints(){
		return mouseMoves.size()+2;
	}
	public long getTotalTime(){
		return mousePointer.getDuration();
	}
	public double getLength(){
		double sum = 0;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		long x, y;
		for (MouseMove mouseMove : mouseMoves) {
			x = mouseMove.getXpix() - preX;
			y = mouseMove.getYpix() - preY;
			sum += Math.sqrt((double)(x * x + y * y));
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
		}
		x = mousePointer.getXfinalpix() - preX;
		y = mousePointer.getYfinalpix() - preY;
		sum += Math.sqrt((double)(x * x + y * y));
		return sum;
	}
	public double getVelocity(){
		double sum = 0, sqrt;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		long preTime = mousePointer.getStarttime();
		long x, y, time;
		for (MouseMove mouseMove : mouseMoves) {
			x = mouseMove.getXpix() - preX;
			y = mouseMove.getYpix() - preY;
			time = mouseMove.getStarttime() - preTime;
			sqrt = Math.sqrt((double)(x * x + y * y));
			sum += sum + (sqrt / (double)time);
			
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
			preTime = mouseMove.getStarttime();
		}
		x = mousePointer.getXfinalpix() - preX;
		y = mousePointer.getYfinalpix() - preY;
		time = mousePointer.getEndtime() - preTime;
		sqrt = Math.sqrt((double)(x * x + y * y));
		sum += sum + (sqrt / (double)time);
		return sum;
	}
	public double getDistance(){
		return mousePointer.getDistancepix();
	}
	public double getAcceleration(){
		double sum = 0;
		long x = mousePointer.getXpix(), y = mousePointer.getYpix(), time = mousePointer.getStarttime();
		long tx, ty;
		double sqrt;
		for (MouseMove mouseMove : mouseMoves) {
			tx = mouseMove.getXpix() - x;
			ty = mouseMove.getYpix() - y;
			sqrt = Math.sqrt((double)(tx * tx + ty * ty));
			time = mouseMove.getStarttime() - time;
			sum += (sqrt / (double)time);
			x = mouseMove.getXpix();
			y = mouseMove.getYpix();
			time = mouseMove.getStarttime();
		}
		tx = mousePointer.getXfinalpix() - x;
		ty = mousePointer.getYfinalpix()- y;
		sqrt = Math.sqrt((double)(tx * tx + ty * ty));
		time = mousePointer.getEndtime() - time;
		sum += (sqrt / (double)time);
		return sum;
	}
	public double getDirectionAngle(){
		long x = mousePointer.getXpix() - mousePointer.getXfinalpix();
		long y = mousePointer.getYpix() - mousePointer.getYfinalpix();
		return Math.toDegrees(Math.atan2(x,y));
	}
	public double getDirectionAngleChange(){
		long x = mousePointer.getXpix();
		long y = mousePointer.getYpix();
		long tx, ty;
		double sumangle = 0;
		for (MouseMove mouseMove : mouseMoves) {
			tx = mouseMove.getXpix() - x;
			ty = mouseMove.getYpix() - y;
			sumangle += Math.toDegrees(Math.atan2(tx,ty));
		}
		tx = mousePointer.getXfinalpix() - x;
		ty = mousePointer.getYfinalpix() - y;
		sumangle += Math.toDegrees(Math.atan2(tx,ty));
		return sumangle/(mouseMoves.size()+2);
	}
	public long getInflectionPoints(){
		long x = mousePointer.getXpix(), y = mousePointer.getYpix();
		double newslope,slope=0;
		boolean isFirst = true;
		long inflectionpoints = 0;
		
		for (MouseMove mouseMove : mouseMoves) {
			newslope = getSlope(x,y,mouseMove.getXpix(),mouseMove.getYpix());
			if (isFirst)
				isFirst = false;
			else if(hasSignChanged(slope, newslope))
				inflectionpoints++;
			slope = newslope;
			x = mouseMove.getXpix();
			y = mouseMove.getYpix();
		}
		newslope = getSlope(x,y,mousePointer.getXfinalpix(),mousePointer.getYfinalpix());
		if(hasSignChanged(slope, newslope))
			inflectionpoints++;
		return inflectionpoints;
	}
	public double getCurviness(){
		return 0;
	}
	private double getSlope(long ax, long ay, long bx, long by){
		double ydiff = ay - by;
		if (ax == bx)
			return ydiff / 0.00000001;
		else
			return ydiff/ (ax - bx);
	}
	private boolean hasSignChanged(double f, double s){
		if ((f<0 && s<0) ||(f>=0 && s>=0))
			return false;
		else
			return true;
	}
}
