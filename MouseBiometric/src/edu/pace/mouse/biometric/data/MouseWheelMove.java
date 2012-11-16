package edu.pace.mouse.biometric.data;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * 
 * @author Venugopala C
 *
 */

public class MouseWheelMove {
	/*
	 * <mouseWheel id="0">
	 * 	<xpix>1696</xpix>
	 * 	<ypix>530</ypix>
	 * 	<moved>4</moved>
	 * 	<number>4</number>
	 * 	<starttime>1350748852634</starttime>
	 * 	<endtime>1350748852925</endtime>
	 * 	<duration>0291</duration>
	 * 	<window>Google</window>
	 * 	<context/>
	 * </mouseWheel>
	 */
	
	private int id;
	private int xpix;
	private int ypix;
	private long moved;
	private long starttime;
	private long endtime;
	private long number;
	private int duration;
	private String window;
	private String context;
	public MouseWheelMove(Node n){
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
				else if ("moved".equals(name))
					moved = Long.parseLong(value);
				else if ("number".equals(name))
					number = Long.parseLong(value);
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
		System.out.println("id: " + id);
		System.out.println("xpix: " + xpix);
		System.out.println("ypix: " + ypix);
		System.out.println("moved: " + moved);
		System.out.println("number: " + number);
		System.out.println("startitme: " + starttime);
		System.out.println("endtime: " + endtime);
		System.out.println("mouseduration: " + duration);
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

	public int getDuration() {
		return duration;
	}

	public String getWindow() {
		return window;
	}

	public String getContext() {
		return context;
	}

	public long getMoved() {
		return moved;
	}

	public long getStarttime() {
		return starttime;
	}

	public long getEndtime() {
		return endtime;
	}

	public long getNumber() {
		return number;
	}
}
