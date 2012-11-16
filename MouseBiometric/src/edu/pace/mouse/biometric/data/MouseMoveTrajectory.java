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
	
	public double getDistance(){
		/*double sum = 0;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		for (MouseMove mouseMove : mouseMoves) {
			sum += getDistance(preX, preY, mouseMove.getXpix(), mouseMove.getYpix());
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
		}
		sum += getDistance(preX, preY, mousePointer.getXfinalpix(), mousePointer.getYfinalpix());
		sum /= getNumnerOfPoints()-1*/
		return mousePointer.getDistancepix();
	}
	public double getLength(){
		/*double sum = 0;
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
		sum += Math.sqrt((double)(x * x + y * y));*/
		return mousePointer.getLengthpix();
	}
	public double getVelocity(){
		double sum = 0, sqrt;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		long preTime = mousePointer.getStarttime();
		long x, y;
		double time;
		for (MouseMove mouseMove : mouseMoves) {
			x = mouseMove.getXpix() - preX;
			y = mouseMove.getYpix() - preY;
			time = mouseMove.getStarttime() - preTime;
			sqrt = Math.sqrt((double)(x * x + y * y));
			if (0 == time)
				time = (double)1.0/40.0;
			sum += (sqrt / (double)time);
			
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
			preTime = mouseMove.getStarttime();
		}
		x = mousePointer.getXfinalpix() - preX;
		y = mousePointer.getYfinalpix() - preY;
		time = mousePointer.getEndtime() - preTime;
		sqrt = Math.sqrt((double)(x * x + y * y));
		if (0 == time)
			time = (double)1.0/40.0;
		sum += (sqrt / (double)time);
		sum /= (getNumnerOfPoints()-1);
		return sum;
	}
	//TODO -- Need to validate this.
	public double getAcceleration(){

		double sum = 0;
		long x = mousePointer.getXpix(), y = mousePointer.getYpix();
		double time = mousePointer.getStarttime();
		long tx, ty;
		double sqrt;
		for (MouseMove mouseMove : mouseMoves) {
			tx = mouseMove.getXpix() - x;
			ty = mouseMove.getYpix() - y;
			sqrt = Math.sqrt((double)(tx * tx + ty * ty));
			time = mouseMove.getStarttime() - time;
			if (0 == time)
				time = (double)1.0/40.0;
			sum += (sqrt / (double)time);
			x = mouseMove.getXpix();
			y = mouseMove.getYpix();
			time = mouseMove.getStarttime();
		}
		tx = mousePointer.getXfinalpix() - x;
		ty = mousePointer.getYfinalpix()- y;
		sqrt = Math.sqrt((double)(tx * tx + ty * ty));
		time = mousePointer.getEndtime() - time;
		if (0 == time)
			time = (double)1.0/40.0;
		sum += (sqrt / (double)time);
		return sum;
	}
	/**
	 * Average point to point direction angle
	 * @return
	 */
	public double getDirectionAngle(){
		double sum = 0;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		for (MouseMove mouseMove : mouseMoves) {
			sum += Math.toDegrees(Math.atan2(mouseMove.getXpix() - preX,mouseMove.getYpix() - preY));
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
		}
		sum += Math.toDegrees(Math.atan2(mousePointer.getXfinalpix()- preX,mousePointer.getYfinalpix() - preY));
		return sum /(getNumnerOfPoints()-1);
	}
	public double getDirectionAngleChange(){
		double []dirchange = new double[mouseMoves.size()+1];
		long x = mousePointer.getXpix();
		long y = mousePointer.getYpix();
		long tx, ty;
		int i = 0;
		for (MouseMove mouseMove : mouseMoves) {
			tx = mouseMove.getXpix() - x;
			ty = mouseMove.getYpix() - y;
			dirchange[i++] = Math.toDegrees(Math.atan2(tx,ty));
		}
		tx = mousePointer.getXfinalpix() - x;
		ty = mousePointer.getYfinalpix() - y;
		dirchange[i++] = Math.toDegrees(Math.atan2(tx,ty));
		double sum = 0;
		for (i = 1; i < dirchange.length; i++) {
			sum += dirchange[i] - dirchange[i-1];
		}
		if (1 <= dirchange.length)
			return 0;
		return sum/(dirchange.length - 1);
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
		if (0 == getDistance())
			return getLength()/((double)1/40);
		return getLength() / getDistance();
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
	/*private double getDistance(long x1, long y1, long x2, long y2){
		return Math.sqrt(((x2-x1)*(x2-x1)) + ((y2-y1) * (y2-y1)));
	}*/
}
