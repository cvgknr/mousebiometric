package unittest;

import java.awt.Dimension;
import java.util.ArrayList;

import edu.pace.mouse.biometric.data.MouseClick;
import edu.pace.mouse.biometric.data.MouseDoubleClick;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MouseMoveCurve;
import edu.pace.mouse.biometric.data.MousePointer;
import edu.pace.mouse.biometric.data.MouseUserProfile;


public class TestFeature{
	public static void main(String []args){
		//MouseLogParser _mParser = new MouseLogParser("./test/python/NedBakelman_WordProcessor_001.xml");
		MouseLogParser _mParser = new MouseLogParser("./logsamples/Venugopala3_Browser_001.xml");
		MouseUserProfile _p = _mParser.getUserProfile();
		_p.print();
		/*ArrayList<MouseClick> mc = _mParser.getMouseClicks();
		for (MouseClick mouseClick : mc) {
			mouseClick.print();
		}
		ArrayList<MouseDoubleClick> mdc = _mParser.getMouseDoubleClicks();
		for (MouseDoubleClick mouseDoubleClick : mdc) {
			mouseDoubleClick.print();
		}
		ArrayList<MouseMove> mm = _mParser.getMouseMoves();
		for (MouseMove mouseMove : mm) {
			mouseMove.print();
		}*/
		/*ArrayList<MousePointer> mp = _mParser.getMousePointers();
		for (MousePointer mousePointer : mp) {
			mousePointer.print();
		}*/
		//MouseMoveCurveAngle _mc = new MouseMoveCurveAngle(_mParser);
		//_mc.extract();
		/*ArrayList<MouseMoveCurve> curves = _mParser.getMouseCurves();
		int i = 0;
		for (MouseMoveCurve mouseMoveCurve : curves) {
			System.out.println("Curve " + (++i) + " Points are: " + mouseMoveCurve.getPoints().size() );
		}
		//DrawCurve c = new DrawCurve(curves.get(238));
		DrawCurve c = new DrawCurve(curves);
        c.setSize(new Dimension(1500,1200));
        c.setVisible(true);*/

	}
}
