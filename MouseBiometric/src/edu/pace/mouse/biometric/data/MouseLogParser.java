package edu.pace.mouse.biometric.data;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.pace.mouse.biometric.core.FeatureResult;

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
	private ArrayList<MouseClick> mouseClicks = null;
	private ArrayList<MouseDoubleClick> mouseDoubleClicks = null;
	private ArrayList<MouseMove> mouseMoves = null;
	private ArrayList<MousePointer> mousePointers = null;
	private ArrayList<MouseMoveCurve> mouseMoveCurves = null;
	private ArrayList<MouseDragDrop> mouseDragDrops= null;
	private ArrayList<MouseWheelMove> mouseWheelMoves = null;
	private MouseUserProfile userProfile= null;
	public MouseLogParser(String _path){
		path = _path;
		dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			dom = db.parse(path);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public MouseUserProfile getUserProfile(){
		if (null == userProfile){
			NodeList _list = dom.getElementsByTagName("user");
			if (0 != _list.getLength()){
				userProfile = new MouseUserProfile(_list.item(0));
			}
		}
		return userProfile;
	}
	private boolean isDoubleClick(MouseClick first, MouseClick second){
		if( (second.getMousereleasetime() - first.getMousepresstime())<=500)
			return true;
		return false;
	}
	public ArrayList<MouseClick> getMouseClicks(){
		if (null == mouseClicks){
			mouseClicks = new ArrayList<MouseClick>(10);
			mouseDoubleClicks = new ArrayList<MouseDoubleClick>(10);
			NodeList _list = dom.getElementsByTagName("mouseClicks");
			if (0 != _list.getLength()){
				_list = _list.item(0).getChildNodes();
				MouseClick m1=null,m2=null;
				for (int i=0;i<_list.getLength();i++){
					m2 = new MouseClick(_list.item(i)); 
					if (null == m1)
						m1 = m2;
					else if(isDoubleClick(m1,m2)){
						mouseDoubleClicks.add(new MouseDoubleClick(m1,m2));
						m1 = null;
					}else{
						mouseClicks.add(m1);
						m1 = m2;
					}
				}
				if (null != m1)
					mouseClicks.add(m1);
			}
		}
		return mouseClicks;
	}
	public ArrayList<MouseDoubleClick> getMouseDoubleClicks(){
		if(null == mouseDoubleClicks)
			getMouseClicks();
		return mouseDoubleClicks;
	}
	public ArrayList<MouseMove> getMouseMoves(){
		if (null == mouseMoves){
			mouseMoves = new ArrayList<MouseMove>();
			NodeList _list = dom.getElementsByTagName("mouseMoves");
			if (0 != _list.getLength()){
				_list = _list.item(0).getChildNodes();
				for (int i=0;i<_list.getLength();i++)
					mouseMoves.add(new MouseMove(_list.item(i))); 
			}
		}
		return mouseMoves;
	}
	public ArrayList<MousePointer> getMousePointers(){
		if (null == mousePointers){
			mousePointers = new ArrayList<MousePointer>(10);
			NodeList _list = dom.getElementsByTagName("mousePointers");
			if (0 != _list.getLength()){
				_list = _list.item(0).getChildNodes();
				for (int i=0;i<_list.getLength();i++)
					mousePointers.add(new MousePointer(_list.item(i))); 
			}
		}
		return mousePointers;
	}
	private double getSlope(MouseMove a, MouseMove b){
		double ydiff = a.getYpix() - b.getYpix();
		if (a.getXpix() == b.getXpix())
			return ydiff / 0.00000001;
		else
			return ydiff/ (a.getXpix() - b.getXpix());
	}
	private boolean hasSignChanged(double f, double s){
		if ((f<0 && s<0) ||(f>=0 && s>=0))
			return false;
		else
			return true;
	}
	public ArrayList<MouseMoveCurve> getMouseCurves(){
		if (null == mouseMoveCurves){
			mouseMoveCurves = new ArrayList<MouseMoveCurve>(10);
			ArrayList<MouseMove> moves = getMouseMoves();
			MouseMove _pt = null;
			ArrayList<MouseMove> points = new ArrayList<MouseMove>(10);
			double slope=0, newslope;
			for (MouseMove mouseMove : moves) {
				if (null != _pt){
					newslope = getSlope(_pt, mouseMove);
					if(points.size() > 5 && hasSignChanged(slope, newslope)){
						mouseMoveCurves.add(new MouseMoveCurve(points));
						points = new ArrayList<MouseMove>(10);
					}
					points.add(mouseMove);
				}else
					points.add(mouseMove);
				_pt = mouseMove;
			}
		}
		return mouseMoveCurves;
	}
	private MouseDragDrop checkDragDrop(MouseClick click,ArrayList<MouseMove> moves){
		long press = click.getMousepresstime();
		long release = click.getMousereleasetime();
		ArrayList<MouseMove> m = new ArrayList<MouseMove>(2);
		for (MouseMove mouseMove : moves) {
			if (press>=mouseMove.getStarttime() && release<=mouseMove.getStarttime())
				m.add(mouseMove);
		}
		if (m.size()>0)
			return new MouseDragDrop(click, m);
		return null;
	}
	public ArrayList<MouseDragDrop> getMouseDragDrop(){
		if (null == mouseDragDrops){
			mouseDragDrops = new ArrayList<MouseDragDrop>(10);
			ArrayList<MouseMove> moves = getMouseMoves();
			ArrayList<MouseClick> clicks = getMouseClicks();
			MouseDragDrop d = null;
			String hand = getUserProfile().getHanded();
			if (null == hand)
				hand = "left";
			for (MouseClick mouseClick : clicks) {
				if (hand.equals(mouseClick.getButton())){
					d = checkDragDrop(mouseClick,moves);
					if(null != d)
						mouseDragDrops.add(d);
				}
			}
		}
		return mouseDragDrops;
	}
	public ArrayList<MouseWheelMove> getMouseWheelMoves(){
		if (null == mouseWheelMoves){
			mouseWheelMoves = new ArrayList<MouseWheelMove>();
			NodeList _list = dom.getElementsByTagName("mouseWheels");
			if (0 != _list.getLength()){
				_list = _list.item(0).getChildNodes();
				for (int i=0;i<_list.getLength();i++)
					mouseWheelMoves.add(new MouseWheelMove(_list.item(i))); 
			}
		}
		return mouseWheelMoves;
	}
	/*public List<MouseMove> getMouseMoves(String window){
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
	}*/
}
