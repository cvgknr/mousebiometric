package unittest;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MouseMoveCurve;

public class DrawCurve extends JFrame{
	private MouseMoveCurve curve = null;
	ArrayList<MouseMoveCurve> curves = null;
	public DrawCurve(MouseMoveCurve _curve){
		this.curve = _curve;
		System.out.println("Started Draw curve");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Paint Curve");
		setSize(300,200);
		//setLocation(new Point(500,500));
		//setVisible(true);
		//setBackground(Color.black);
	}
	public DrawCurve(ArrayList<MouseMoveCurve> _curves){
		this.curves = _curves;
		System.out.println("Started Draw curve");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Paint Curve");
		setSize(300,200);
		//setLocation(new Point(500,500));
		//setVisible(true);
		//setBackground(Color.black);
	}
	private void paintCurve(Graphics g, MouseMoveCurve curve){
		ArrayList<MouseMove> points = curve.getPoints();
		MouseMove _pt = null;
		g.drawString("(" +points.get(0).getXpix() + ", " + points.get(0).getYpix() + ")" , points.get(0).getXpix(), points.get(0).getYpix());
		g.drawString("(" +points.get(points.size()-1).getXpix() + ", " + points.get(points.size()-1).getYpix() + ")" , points.get(points.size()-1).getXpix(), points.get(points.size()-1).getYpix());
		for (MouseMove mouseMove : points) {
			if (null != _pt){
				g.drawLine(_pt.getXpix(), _pt.getYpix(), mouseMove.getXpix(), mouseMove.getYpix());
			}
			_pt = mouseMove;
		}
	}
	public void paint(Graphics g){
		if (null != curve)
			paintCurve(g, curve);
		else if (null != curves){
			for (MouseMoveCurve mouseMoveCurve: curves) {
				paintCurve(g, mouseMoveCurve);
			}
		}
	}
}
