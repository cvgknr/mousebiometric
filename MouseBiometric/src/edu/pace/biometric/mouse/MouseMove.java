package edu.pace.biometric.mouse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MouseMove {
	/*
	 * <mouseMove id="5">
	 * 	<xpix>100</xpix>
	 * 	<ypix>606</ypix>
	 * 	<wheel>0</wheel>
	 * 	<starttime>1348422442180</starttime>
	 * 	<window>FolderView</window>
	 * 	<context/>
	 * </mouseMove>
	 */
	
	public int id;
	public int xpix;
	public int ypix;
	public int wheel;
	public String starttime;
	public String window;
	public String context;
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
				else if ("wheel".equals(name))
					wheel = Integer.parseInt(value);
				else if ("starttime".equals(name))
					starttime = value;
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
		System.out.println("wheel: " + wheel);
		System.out.println("starttime: " + starttime);
		System.out.println("window: " + window);
		System.out.println("context: " + context);
		
		
		System.out.println("--------------------------------------------");
	}
}
