package edu.pace.mouse.biometric.data;

import java.util.ArrayList;

/**
 * 
 * @author Venugopala C
 *
 */
/**
 * Mouse Move Drag Drop Trajectory
 */
public class MouseDragDropTrajectory  extends MouseMoveTrajectory{
	private MouseClick click;
	public MouseDragDropTrajectory(MousePointer mp, ArrayList<MouseMove> moves, MouseClick click ){
		super(mp,moves);
		this.click = click;
	}
	public MouseDragDropTrajectory(MouseMoveTrajectory mmt, MouseClick click ){
		super(mmt.getMousePointer(), mmt.getMouseMoves());
		this.click = click;
	}
	public void print(){
		
	}
	public MouseClick getMouseClick() {
		return click;
	}
}
