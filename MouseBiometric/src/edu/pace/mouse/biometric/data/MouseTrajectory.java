package edu.pace.mouse.biometric.data;

import java.util.ArrayList;

/**
 * 
 * @author Venugopala C
 *
 */

public class MouseTrajectory {
	private MousePointer mousePointer;
	private ArrayList<MouseMove> mouseMoves;
	public MouseTrajectory(MousePointer mp,ArrayList<MouseMove> mm ){
		this.mousePointer = mp;
		this.mouseMoves = mm;
	}
	public MousePointer getMousePointer() {
		return mousePointer;
	}
	public ArrayList<MouseMove> getMouseMoves() {
		return mouseMoves;
	}
	public long getNumberOfPoints(){
		long count = mouseMoves.size();
		if (noSkipFirstPoint())
			count++;
		if (noSkipLastPoint())
			count++;
		return count;
	}
	public long getTotalTime(){
		return mousePointer.getDuration();
	}
	
	public double getDistance(){
		return mousePointer.getDistancepix();
	}
	public double getLength(){
		return mousePointer.getLengthpix();
	}
	private double get2PointVelocity(long x1, long y1, long x2, long y2, double time){
		long x = x2-x1;
		long y = y2-y1;
		double sqrt = Math.sqrt((double)(x * x + y * y));
		if (0 == time)
			time = (double)1.0/40.0;
		return sqrt / (double)time;
	}
	private boolean noSkipFirstPoint(){
		return 	(0 == mouseMoves.size() || 
					(mousePointer.getXpix() != mouseMoves.get(0).getXpix() || 
					mousePointer.getYpix() != mouseMoves.get(0).getYpix() || 
					mousePointer.getStarttime() != mouseMoves.get(0).getStarttime()));
	}
	private boolean noSkipLastPoint(){
		if (0 == mouseMoves.size()) return true; 
		int lastindex = mouseMoves.size()-1;
		return (mousePointer.getXfinalpix() != mouseMoves.get(lastindex).getXpix() ||
				mousePointer.getYfinalpix() != mouseMoves.get(lastindex).getYpix() ||
				mousePointer.getEndtime() != mouseMoves.get(lastindex).getStarttime());
	}
	public double getVelocity(){
		double sum = 0;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		long preTime = mousePointer.getStarttime();
		int index = noSkipFirstPoint()?0:1;
		MouseMove mouseMove;
		for (;index<mouseMoves.size();index++) {
			 mouseMove = mouseMoves.get(index);
			sum += get2PointVelocity(preX, preY, mouseMove.getXpix(), mouseMove.getYpix(), mouseMove.getStarttime() - preTime);
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
			preTime = mouseMove.getStarttime();
		}
		if (noSkipLastPoint()){
			sum += get2PointVelocity(preX, preY, mousePointer.getXfinalpix(), mousePointer.getYfinalpix(), mousePointer.getEndtime() - preTime);
		}
		sum /= (getNumberOfPoints()-1);
		return sum;
	}
	public double getAcceleration(){
		double sum = 0;
		long preX = mousePointer.getXpix(), preY = mousePointer.getYpix();
		double preTime = mousePointer.getStarttime();
		double pre2Time = 0;
		double preVelocity=0,v;
		boolean isFirst = true;
		if (0 == mouseMoves.size()) return 0;
		int index = noSkipFirstPoint()?0:1;
		MouseMove mouseMove;
		for (;index<mouseMoves.size();index++) {
			 mouseMove = mouseMoves.get(index);
			v = get2PointVelocity(preX, preY, mouseMove.getXpix(), mouseMove.getYpix(), mouseMove.getStarttime() - preTime);
			if (isFirst){
				isFirst = false;
			}else{
				sum += 	(preVelocity - v)/((mouseMove.getStarttime()-pre2Time)/2);
			}
			preVelocity = v;
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
			pre2Time = preTime;
			preTime = mouseMove.getStarttime();
		}
		if (noSkipLastPoint()){
			v = get2PointVelocity(preX, preY, mousePointer.getXfinalpix(), mousePointer.getYfinalpix(), mousePointer.getEndtime() - preTime);
			sum += (preVelocity - v)/((mousePointer.getEndtime()-pre2Time)/2);
		}
		return sum/mouseMoves.size();
	}
	
	private double get2PointDirectionAngle(long x1, long y1, long x2, long y2){
		long dx = x2-x1;
		long dy = y2-y1;
		double m = (double)dy/(double)dx;
		if (dx > 0 && dy > 0){
			return Math.atan(m);
		}else if(dx<0 && dy>0){
			return 180 + Math.atan(m);
		}else if(dx<0 && dy<0){
			return 180 +Math.atan(m);
		}else if(dx>0 && dy<0){
			return 360 +Math.atan(m);
		}
		return 0;
	}
	/**
	 * Average point to point direction angle
	 * 
	 * @return
	 */
	public double getDirectionAngle(){
		double sum = 0;
		long preX = mousePointer.getXpix();
		long preY = mousePointer.getYpix();
		int index = noSkipFirstPoint()?0:1;
		MouseMove mouseMove;
		for (;index<mouseMoves.size();index++) {
			mouseMove = mouseMoves.get(index);
			sum += get2PointDirectionAngle(preX, preY, mouseMove.getXpix(),mouseMove.getYpix());
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
		}
		if (noSkipLastPoint())
			sum += get2PointDirectionAngle(preX, preY, mousePointer.getXfinalpix(),mousePointer.getYfinalpix()); 
		return sum /(getNumberOfPoints()-1);
	}
	public double getDirectionAngleChange(){
		double sum = 0;
		long preX = mousePointer.getXpix(), preY = mousePointer.getYpix();
		double preAngle=0,a,da;
		boolean isFirst = true;
		if (0 == mouseMoves.size()) return 0;
		int index = noSkipFirstPoint()?0:1;
		MouseMove mouseMove;
		for (;index<mouseMoves.size();index++) {
			mouseMove = mouseMoves.get(index);
			a = get2PointDirectionAngle(preX, preY, mouseMove.getXpix(), mouseMove.getYpix());
			if (isFirst){
				isFirst = false;
			}else{
				da = preAngle - a;
				if (da<-180)
					sum += da+360;
				else
					sum += da-360;
			}
			preAngle = a;
			preX = mouseMove.getXpix();
			preY = mouseMove.getYpix();
		}
		if (noSkipLastPoint()){
			a = get2PointDirectionAngle(preX, preY, mousePointer.getXfinalpix(), mousePointer.getYfinalpix());
			da = preAngle - a;
			if (da<-180)
				sum += da+360;
			else
				sum += da-360;
		}
		return sum/mouseMoves.size();
	}
	public long getInflectionPoints(){
		long x = mousePointer.getXpix(), y = mousePointer.getYpix();
		double newslope,slope=0;
		boolean isFirst = true;
		long inflectionpoints = 0;
		int index = noSkipFirstPoint()?0:1;
		MouseMove mouseMove;
		for (;index<mouseMoves.size();index++) {
			mouseMove = mouseMoves.get(index);
			newslope = getSlope(x,y,mouseMove.getXpix(),mouseMove.getYpix());
			if (isFirst)
				isFirst = false;
			else if(hasSignChanged(slope, newslope))
				inflectionpoints++;
			slope = newslope;
			x = mouseMove.getXpix();
			y = mouseMove.getYpix();
		}
		if (noSkipLastPoint()){
			newslope = getSlope(x,y,mousePointer.getXfinalpix(),mousePointer.getYfinalpix());
			if(hasSignChanged(slope, newslope))
				inflectionpoints++;
		}
		return inflectionpoints;
	}
	public double getCurviness(){
		double distance = getDistance();
		if (0 == distance)
			distance = 1.0/40.0;
		return getLength() / distance;
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
