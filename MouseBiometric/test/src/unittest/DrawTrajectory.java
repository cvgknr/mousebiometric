package unittest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import edu.pace.mouse.biometric.data.MouseMove;
import edu.pace.mouse.biometric.data.MouseTrajectory;
import edu.pace.mouse.biometric.data.MousePointer;

public class DrawTrajectory extends JFrame{
	private MouseTrajectory t = null;
	private ArrayList<MouseTrajectory> ts = null;
	public DrawTrajectory(MouseTrajectory t){
		this.t = t;
		System.out.println("Started Draw Trajectory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1500,1200);
		setTitle("Paint Curve");
		setLocation(new Point(0,0));
		setBackground(Color.blue);
		setForeground(Color.red);
	}
	public DrawTrajectory(ArrayList<MouseTrajectory> ts){
		this.ts = ts;
		System.out.println("Started Draw Trajectory");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1500,1200);
		setTitle("Paint Curve");
		setLocation(new Point(0,0));
		setBackground(Color.blue);
		setForeground(Color.red);
	}
	private void paintCurve(Graphics g,MouseTrajectory t){
		ArrayList<MouseMove> points = t.getMouseMoves();
		MousePointer p = t.getMousePointer();
		g.drawString("(" +p.getXpix() + ", " + p.getYpix() + ")" , p.getXpix(), p.getYpix());
		System.out.println("(" +p.getXpix() + ", " + p.getYpix() + ")" );
		int x = p.getXpix(), y = p.getYpix();
		for (MouseMove mouseMove : points) {
			g.drawLine(x, y, mouseMove.getXpix(), mouseMove.getYpix());
			x = mouseMove.getXpix();
			y = mouseMove.getYpix();
		}
		g.drawLine(x, y, p.getXfinalpix(), p.getYfinalpix());
		g.drawString("(" +p.getXfinalpix() + ", " + p.getYfinalpix() + ")" , p.getXfinalpix(), p.getYfinalpix());
		System.out.println("(" +p.getXfinalpix() + ", " + p.getYfinalpix() + ")" );
		t = null;
	}
	public void paint(Graphics g){
		super.paint(g);
		if (null != t)
			paintCurve(g,t);
		else
			for (MouseTrajectory tt : ts) {
				paintCurve(g,tt);
				
			}
	}
}
