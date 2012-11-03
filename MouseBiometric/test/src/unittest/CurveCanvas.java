package unittest;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class CurveCanvas extends Canvas{
	public void paint(Graphics g)
    {
        g.drawImage(DrawCurveOld.I, 0, 0, Color.red, null);
        Dimension s = getSize();
        g.drawOval(0, 0, s.width, s.height);
    }
}
