package edu.pace.mouse.biometric.data;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	private ArrayList<MouseDragDropTrajectory> mouseDragDrops= null;
	private ArrayList<MouseWheelMove> mouseWheelMoves = null;
	private ArrayList<MouseMoveTrajectory> mouseMoveTrajectories=null;
	private ArrayList<MouseMoveTrajectory> sysWakeupTrajectories=null;
	private ArrayList<MouseMoveClickTrajectory> moveClickTrajectories=null;
	
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
	/**
	 * Find all clicks (left, right) and double clicks.
	 *  
	 * @return
	 */
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
	private double getSlope(long ax, long ay, long bx, long by){
		double ydiff = ay - by;
		if (ax == bx)
			return ydiff / 0.00000001;
		else
			return ydiff/ (ax - bx);
	}
	private boolean hasSignChanged(double f, double s){
		if ((f<0 && s<0) ||(f>=0 && s>=0))
			return false;
		else
			return true;
	}
	/*public ArrayList<MouseMoveCurve> getMouseCurves(){
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
	}*/
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
	private ArrayList<MouseMove> getTrajectoryMoves(MousePointer mp,ArrayList<MouseMove> mms){
		ArrayList<MouseMove> tmm = new ArrayList<MouseMove>(5);
		boolean findStart = true;
		boolean findEnd = true;
		long mpx = mp.getXpix(), mpy=mp.getXpix(), mpxfinal = mp.getXfinalpix(), mpyfinal = mp.getYfinalpix();
		long mpstart = mp.getStarttime();
		
		for (MouseMove mouseMove : mms) {
			if (findStart){
				if (mouseMove.getXpix() == mpx && mouseMove.getYpix() == mpy && mouseMove.getStarttime() == mpstart){
					findStart = false;
				}else
					continue;
			}
			tmm.add(mouseMove);
			if (mouseMove.getXpix() == mpxfinal && mouseMove.getYpix() == mpyfinal)
				break;
			
		}
		
		return tmm;
	}
	public ArrayList<MouseMoveTrajectory> getMouseMoveTrajectories(){
		if (null == mouseMoveTrajectories){
			mouseMoveTrajectories = new ArrayList<MouseMoveTrajectory>(10);
			ArrayList<MousePointer> mps = getMousePointers();
			ArrayList<MouseMove> mms = getMouseMoves();
			for (MousePointer mousePointer : mps) {
				mouseMoveTrajectories.add(new MouseMoveTrajectory(mousePointer, getTrajectoryMoves(mousePointer, mms)));
			}
		}
		return mouseMoveTrajectories;
	}
	private boolean isWakeup(MouseMoveTrajectory mmt, ArrayList<MouseClick> clicks){
		ArrayList<MouseMove> mml = mmt.getMouseMoves();
		MousePointer mp = mmt.getMousePointer();
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (starttime < mouseClick.getMousepresstime() && mouseClick.getMousereleasetime() < endtime)
				return false;
		}
		return true;
	}
	/**
	 * 
	 * @return return all System Wake up Mouse Move trajectories.
	 */
	public ArrayList<MouseMoveTrajectory> getSystemWakeUpTranjectories(){
		if (null == sysWakeupTrajectories){
			sysWakeupTrajectories = new ArrayList<MouseMoveTrajectory>(5);
			ArrayList<MouseMoveTrajectory> mmts = getMouseMoveTrajectories();
			ArrayList<MouseClick> clicks = getMouseClicks();
			for (MouseMoveTrajectory mmt : mmts) {
				if (isWakeup(mmt, clicks))
					sysWakeupTrajectories.add(mmt);
			}
		}
		return sysWakeupTrajectories;
	}
	private MouseClick isMoveClick(MouseMoveTrajectory mmt, ArrayList<MouseClick> clicks){
		ArrayList<MouseMove> mml = mmt.getMouseMoves();
		MousePointer mp = mmt.getMousePointer();
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (starttime < mouseClick.getMousepresstime() && mouseClick.getMousereleasetime() < endtime)
				return null;
			if (starttime <mouseClick.getMousepresstime() && endtime <= mouseClick.getMousereleasetime())
				return mouseClick;
		}
		return null;
	}
	/**
	 * 
	 * @return return all System Wake up Mouse Move trajectories.
	 */
	public ArrayList<MouseMoveClickTrajectory> getMoveMoveAndClickTrajectory(){
		if (null == moveClickTrajectories){
			moveClickTrajectories = new ArrayList<MouseMoveClickTrajectory>(5);
			ArrayList<MouseMoveTrajectory> mmts = getMouseMoveTrajectories();
			ArrayList<MouseClick> clicks = getMouseClicks();
			for (MouseMoveTrajectory mmt : mmts) {
				MouseClick click = isMoveClick(mmt, clicks);
				if (null != click)
					moveClickTrajectories.add(new MouseMoveClickTrajectory(mmt, click));
			}
		}
		return moveClickTrajectories;
	}
	private MouseClick isDragDrop(MouseMoveTrajectory mmt,ArrayList<MouseClick> clicks){
		ArrayList<MouseMove> mml = mmt.getMouseMoves();
		MousePointer mp = mmt.getMousePointer();
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			//TODO:  Need some more thinking on this
		}
		return null;
	}
	public ArrayList<MouseDragDropTrajectory> getMouseDragDropTrajectory(){
		if (null == mouseDragDrops){
			mouseDragDrops = new ArrayList<MouseDragDropTrajectory>(10);			
			ArrayList<MouseMoveTrajectory> mmts = getMouseMoveTrajectories();
			ArrayList<MouseClick> clicks = getMouseClicks();
			for (MouseMoveTrajectory mmt : mmts) {
				MouseClick click = isDragDrop(mmt, clicks);
				if (null != click)
					mouseDragDrops.add(new MouseDragDropTrajectory(mmt, click));
			}
		}
		return mouseDragDrops;
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
