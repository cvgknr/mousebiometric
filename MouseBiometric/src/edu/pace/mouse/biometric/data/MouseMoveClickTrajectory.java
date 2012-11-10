package edu.pace.mouse.biometric.data;

import java.util.ArrayList;
/**
 * 
 * @author Venugopala C
 *
 */

public class MouseMoveClickTrajectory extends MouseMoveTrajectory{
	private MouseClick click;
	public MouseMoveClickTrajectory(MousePointer mp,ArrayList<MouseMove> mm, MouseClick click ){
		super(mp, mm);
		this.click = click;
	}
	public MouseMoveClickTrajectory(MouseMoveTrajectory mmt, MouseClick click ){
		super(mmt.getMousePointer(), mmt.getMouseMoves());
		this.click = click;
	}
	public void print(){
	}
	public MouseClick getClick() {
		return click;
	}
}
