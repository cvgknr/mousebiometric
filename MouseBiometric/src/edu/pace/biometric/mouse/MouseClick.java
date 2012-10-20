package edu.pace.biometric.mouse;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MouseClick {
	/*
	 * <mouseClick id="0">
	 * 	<xpix>16</xpix>
	 * 	<ypix>998</ypix>
	 * 	<mousepresstime>1348422443258</mousepresstime>
	 * 	<mousereleasetime>1348422443415</mousereleasetime>
	 * 	<mouseduration>157</mouseduration>
	 * 	<button>left</button>
	 * 	<window>Start</window>
	 * 	<context/>
	 * </mouseClick>
	 */
	
	public int id;
	public int xpix;
	public int ypix;
	public int button;
	public String mousepresstime;
	public String mousereleasetime;
	public int mouseduration;
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
				else if ("button".equals(name))
					button = Integer.parseInt(value);
				else if ("mousepresstime".equals(name))
					mousepresstime = value;
				else if ("mousereleasetime".equals(name))
					mousereleasetime = value;
				else if ("mouseduration".equals(name))
					mouseduration = Integer.parseInt(value);
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
		System.out.println("button: " + button);
		System.out.println("mousepresstime: " + mousepresstime);
		System.out.println("mousereleasetime: " + mousereleasetime);
		System.out.println("mouseduration: " + mouseduration);
		System.out.println("window: " + window);
		System.out.println("context: " + context);
		System.out.println("--------------------------------------------");
	}
}
