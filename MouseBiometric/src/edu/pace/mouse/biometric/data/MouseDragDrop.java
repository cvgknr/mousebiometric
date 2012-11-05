package edu.pace.mouse.biometric.data;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 * @author Venugopala C
 *
 */

public class MouseDragDrop {
	private MouseClick click;
	private ArrayList<MouseMove> moves;
	public MouseDragDrop(MouseClick click, ArrayList<MouseMove> moves ){
		this.click = click;
		this.moves = moves;
	}
	public void print(){
		
	}

	public MouseClick getClick() {
		return click;
	}

	public ArrayList<MouseMove> getMouseMoves() {
		return moves;
	}
}
