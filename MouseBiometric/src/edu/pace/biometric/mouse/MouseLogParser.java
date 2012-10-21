package edu.pace.biometric.mouse;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pace.biometric.mouse.UserProfile;
/**
 * 
 * @author Venugopala C
 *
 */

public class MouseLogParser {
	private String path = null;
	private DocumentBuilderFactory dbf = null;
	private DocumentBuilder db = null;
	private Document dom = null;
	public MouseLogParser(String _path){
		path = _path;
		dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			dom = db.parse(path);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public UserProfile getUserProfile(){
		NodeList _list = dom.getElementsByTagName("user");
		if (0 != _list.getLength()){
			Node mouseMoves = _list.item(0);
			_list = mouseMoves.getChildNodes();
			return new UserProfile(_list.item(0));
		}
		return null;
	}
	public List<MouseMove> getMouseMoves(){
		NodeList _list = dom.getElementsByTagName("mouseMoves");
		if (0 != _list.getLength()){
			Node mouseMoves = _list.item(0);
			_list = mouseMoves.getChildNodes();
			Vector<MouseMove> _m = new Vector<MouseMove>(10);
			MouseMove m;
			for (int i=0;i<_list.getLength();i++){
				//System.out.println(_list.item(i).getNodeName());
				m = new MouseMove(_list.item(i)); 
				_m.add(m);
			}
			return _m;
		}
		return null;
	}
	public List<MouseMove> getMouseMoves(String window){
		NodeList _list = dom.getElementsByTagName("mouseMoves");
		if (0 != _list.getLength()){
			Node mouseMoves = _list.item(0);
			_list = mouseMoves.getChildNodes();
			Vector<MouseMove> _m = new Vector<MouseMove>(10);
			MouseMove m;
			for (int i=0;i<_list.getLength();i++){
				m = new MouseMove(_list.item(i)); 
				if (window.equals(m.getWindow()))
					_m.add(m);
			}
			return _m;
		}
		return null;
	}
	public List<MouseClick> getMouseDoubleClicks(){
		NodeList _list = dom.getElementsByTagName("mouseClicks");
		if (0 != _list.getLength()){
			Node mouseClicks = _list.item(0);
			_list = mouseClicks.getChildNodes();
			Vector<MouseClick> _m = new Vector<MouseClick>(10);
			MouseClick pclick = null; 
			MouseClick m;
			for (int i=0;i<_list.getLength();i++){
				m = new MouseClick(_list.item(i));
				if (null != pclick){
					if ((Long.parseLong(m.getMousepresstime()) - Long.parseLong(pclick.getMousepresstime())) <=500)
						_m.add(m);
				}
				pclick = m;
			}
			return _m;
		}
		return null;
	}
	public List<MouseClick> getMouseClicks(){
		NodeList _list = dom.getElementsByTagName("mouseClicks");
		if (0 != _list.getLength()){
			MouseDoubleClick dClick =null;
			Node mouseClicks = _list.item(0);
			_list = mouseClicks.getChildNodes();
			Vector<MouseClick> _m = new Vector<MouseClick>(10);
			MouseClick m;
			for (int i=0;i<_list.getLength();i++){
				m = new MouseClick(_list.item(i)); 
				_m.add(m);
			}
			return _m;
		}
		return null;
	}
	public List<MouseClick> getMouseClicks(String window){
		NodeList _list = dom.getElementsByTagName("mouseClicks");
		if (0 != _list.getLength()){
			Node mouseClicks = _list.item(0);
			_list = mouseClicks.getChildNodes();
			List<MouseClick> _m = new Vector<MouseClick>(10);
			MouseClick m;
			for (int i=0;i<_list.getLength();i++){
				m = new MouseClick(_list.item(i)); 
				if (window.equals(m.getWindow()))
					_m.add(m);
			}
			return _m;
		}
		return null;
	}
	public List<MousePointer> getMousePointers(){
		NodeList _list = dom.getElementsByTagName("mousePointers");
		if (0 != _list.getLength()){
			Node mousePointers= _list.item(0);
			_list = mousePointers.getChildNodes();
			Vector<MousePointer> _m = new Vector<MousePointer>(10);
			MousePointer m;
			for (int i=0;i<_list.getLength();i++){
				m = new MousePointer(_list.item(i)); 
				_m.add(m);
			}
			return _m;
		}
		return null;
	}
	public List<MousePointer> getMousePointers(String window){
		NodeList _list = dom.getElementsByTagName("mousePointers");
		if (0 != _list.getLength()){
			Node mousePointers = _list.item(0);
			_list = mousePointers.getChildNodes();
			Vector<MousePointer> _m = new Vector<MousePointer>(10);
			MousePointer m;
			for (int i=0;i<_list.getLength();i++){
				m = new MousePointer(_list.item(i)); 
				if (window.equals(m.getWindow()))
					_m.add(m);
			}
			return _m;
		}
		return null;
	}
	public String[] getMousePointersApps(){
		NodeList _list = dom.getElementsByTagName("mousePointers");
		if (0 != _list.getLength()){
			Node mousePointers = _list.item(0);
			_list = mousePointers.getChildNodes();
			HashSet<String> _m = new HashSet<String>(10);
			MousePointer m;
			for (int i=0;i<_list.getLength();i++){
				Node n = _list.item(i);
				NodeList _clist = n.getChildNodes();
				for (int j=0;j<_clist.getLength();j++){
					String name = _clist.item(j).getNodeName();
					NodeList _l = _clist.item(j).getChildNodes();
					if (0 == _l.getLength()) continue;
					String value = _l.item(0).getNodeValue();
					if ("window".equals(name))
						_m.add(value);
				}
			}
			Object []_objs = _m.toArray();
			String []_out = new String[_objs.length];
			for (int i=0;i<_objs.length;i++)
				_out[i] = (String)_objs[i];
			return _out;
		}
		return null;
	}
}
