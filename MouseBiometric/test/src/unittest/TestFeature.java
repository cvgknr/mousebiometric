package unittest;

import java.util.ArrayList;

import edu.pace.mouse.biometric.data.MouseClick;
import edu.pace.mouse.biometric.data.MouseDoubleClick;
import edu.pace.mouse.biometric.data.MouseDragDropTrajectory;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMoveClickTrajectory;
import edu.pace.mouse.biometric.data.MouseTrajectory;


public class TestFeature{
	public static void main(String []args){
		//MouseLogParser _mParser = new MouseLogParser("./logsamples/sample/systemwakeup/VenugopalaChannarayappa_Browser_005.xml");
		MouseLogParser _mParser = new MouseLogParser("./logsamples/RobertoXavier_Other_002.xml");
		
		ArrayList<MouseTrajectory> t = _mParser.getSystemWakeUpTranjectories();
		for (MouseTrajectory mouseTrajectory : t) {
			System.out.println("SW: " + mouseTrajectory.getMousePointer().getId());
		}
		System.out.println("SystemWakeUp Count: " + t.size());
		ArrayList<MouseMoveClickTrajectory> mv = _mParser.getMoveMoveAndClickTrajectory();
		for (MouseMoveClickTrajectory mouseMoveClickTrajectory : mv) {
			System.out.println("MMC: " + mouseMoveClickTrajectory.getMousePointer().getId());
		}
		System.out.println("Move and Click Count: " + mv.size());
		ArrayList<MouseDragDropTrajectory> dd = _mParser.getMouseDragDropTrajectory();
		for (MouseDragDropTrajectory mouseDragDropTrajectory : dd) {
			System.out.println("DD: " + mouseDragDropTrajectory.getMousePointer().getId());
		}
		System.out.println("Drag and drop Count: " + dd.size());
		ArrayList<MouseClick> mc = _mParser.getMouseClicks();
		System.out.println("Click Count: " + mc.size());
		
		ArrayList<MouseDoubleClick> dc = _mParser.getMouseDoubleClicks();
		System.out.println("Double Click Count: " + dc.size());
	}
}
