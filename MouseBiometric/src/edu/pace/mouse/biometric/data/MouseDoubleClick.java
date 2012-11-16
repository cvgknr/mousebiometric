package edu.pace.mouse.biometric.data;

/**
 * 
 * @author Venugopala C
 *
 */

public class MouseDoubleClick {
	private MouseClick firstClick;
	private MouseClick secondClick;
	public MouseDoubleClick(MouseClick m1, MouseClick m2){
		firstClick = m1;
		secondClick = m2;
	}

	public void print(){
		System.out.println("First Mouse click");
		firstClick.print();
		System.out.println("Second Mouse click");
		secondClick.print();
	}

	public MouseClick getFirstClick() {
		return firstClick;
	}

	public MouseClick getSecondClick() {
		return secondClick;
	}
}
