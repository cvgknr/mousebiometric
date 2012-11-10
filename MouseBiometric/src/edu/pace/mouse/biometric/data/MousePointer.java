package edu.pace.mouse.biometric.data;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 * @author Venugopala C
 *
 */

public class MousePointer {
	/*
	 * <mousePointer id="0">
	 * 	<xpix>165</xpix>
	 * 	<ypix>409</ypix>
	 * 	<xfinalpix>16</xfinalpix>
	 * 	<yfinalpix>998</yfinalpix>
	 * 	<distancepix>607.55</distancepix>
	 * 	<lengthpix>611.53</lengthpix>
	 * 	<starttime>1348422442102</starttime>
	 * 	<endtime>1348422443258</endtime>
	 * 	<duration>1156</duration>
	 * 	<window>Control Fimbel Keylogger</window>
	 * 	<context/>
	 * </mousePointer>
	 */
	
	private int id;
	private int xpix;
	private int ypix;
	private int xfinalpix;
	private int yfinalpix;
	private float distancepix;
	private float lengthpix;
	private long starttime;
	private long endtime;
	private int duration;
	private String window;
	private String context;
	public MousePointer(Node n){
		parse(n);
	}
	public void parse(Node n){
		if (null != n){
			NodeList _list = n.getChildNodes();
			String idstr = n.getAttributes().getNamedItem("id").getNodeValue();
			id = Integer.parseInt(idstr);
			for (int i=0;i<_list.getLength();i++){
				String name = _list.item(i).getNodeName();
				NodeList _l = _list.item(i).getChildNodes();
				if (0 == _l.getLength()) continue;
				String value = _l.item(0).getNodeValue();
				if ("xpix".equals(name))
					xpix = Integer.parseInt(value);
				else if ("ypix".equals(name))
					ypix = Integer.parseInt(value);
				else if ("xfinalpix".equals(name))
					xfinalpix = Integer.parseInt(value);
				else if ("yfinalpix".equals(name))
					yfinalpix = Integer.parseInt(value);
				else if ("distancepix".equals(name))
					distancepix = Float.parseFloat(value);
				else if ("lengthpix".equals(name))
					lengthpix = Float.parseFloat(value);
				else if ("starttime".equals(name))
					starttime = Long.parseLong(value);
				else if ("endtime".equals(name))
					endtime = Long.parseLong(value);
				else if ("duration".equals(name))
					duration = Integer.parseInt(value);
				else if ("window".equals(name))
					window = value;
				else if ("context".equals(name))
					context = value;

			}
		}
	}
	public void print(){
		System.out.println("--------------------------------------------");
		System.out.println("id: " + id);
		System.out.println("xpix: " + xpix);
		System.out.println("ypix: " + ypix);
		System.out.println("xfinalpix: " + xfinalpix);
		System.out.println("yfinalpix: " + yfinalpix);
		System.out.println("distancepix: " + distancepix);
		System.out.println("lengthpix: " + lengthpix);
		System.out.println("starttime: " + starttime);
		System.out.println("endtime: " + endtime);
		System.out.println("duration: " + duration);
		System.out.println("window: " + window);
		System.out.println("context: " + context);
		System.out.println("--------------------------------------------");
	}
	public int getId() {
		return id;
	}
	public int getXpix() {
		return xpix;
	}
	public int getYpix() {
		return ypix;
	}
	public int getXfinalpix() {
		return xfinalpix;
	}
	public int getYfinalpix() {
		return yfinalpix;
	}
	public float getDistancepix() {
		return distancepix;
	}
	public float getLengthpix() {
		return lengthpix;
	}
	public long getStarttime() {
		return starttime;
	}
	public long getEndtime() {
		return endtime;
	}
	public int getDuration() {
		return duration;
	}
	public String getWindow() {
		return window;
	}
	public String getContext() {
		return context;
	}
	public double getSlope(){
		return (yfinalpix-ypix)/(xfinalpix-xpix);
	}

}
