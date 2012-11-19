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
	private ArrayList<MouseTrajectory> mouseMoveTrajectories=null;
	private ArrayList<MouseTrajectory> sysWakeupTrajectories=null;
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
	private ArrayList<MouseMove> getMouseMoves(){
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
	public ArrayList<MouseTrajectory> getMouseMoveTrajectories(){
		if (null == mouseMoveTrajectories){
			mouseMoveTrajectories = new ArrayList<MouseTrajectory>(10);
			ArrayList<MousePointer> mps = getMousePointers();
			ArrayList<MouseMove> mms = getMouseMoves();
			for (MousePointer mousePointer : mps) {
				mouseMoveTrajectories.add(new MouseTrajectory(mousePointer, getTrajectoryMoves(mousePointer, mms)));
			}
		}
		return mouseMoveTrajectories;
	}
	private boolean isWakeup(MouseTrajectory mmt, ArrayList<MouseClick> clicks){
		MousePointer mp = mmt.getMousePointer();
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (starttime<= mouseClick.getMousepresstime() && mouseClick.getMousepresstime() <= endtime)
				return false;
		}
		return true;
	}
	/**
	 * 
	 * @return return all System Wake up Mouse Move trajectories.
	 */
	public ArrayList<MouseTrajectory> getSystemWakeUpTranjectories(){
		if (null == sysWakeupTrajectories)
			processTrajectories();
		return sysWakeupTrajectories;
	}
	private MouseClick isMoveClick(MouseTrajectory mmt, ArrayList<MouseClick> clicks){
		MousePointer mp = mmt.getMousePointer();
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (starttime < mouseClick.getMousepresstime() && mouseClick.getMousepresstime() < endtime)
				break;
			if (endtime == mouseClick.getMousepresstime())
				return mouseClick;
		}
		return null;
	}
	/**
	 * 
	 * @return return all System Wake up Mouse Move trajectories.
	 */
	public ArrayList<MouseMoveClickTrajectory> getMoveMoveAndClickTrajectory(){
		if (null == moveClickTrajectories)
			processTrajectories();
		return moveClickTrajectories;
	}
	private MouseClick isDragDrop(MouseTrajectory mmt,ArrayList<MouseClick> clicks){
		ArrayList<MouseMove> mml = mmt.getMouseMoves();
		MousePointer mp = mmt.getMousePointer();
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (mouseClick.getMousepresstime() <= starttime && mouseClick.getMousereleasetime() >= endtime)
				return mouseClick;
		}
		return null;
	}
	public ArrayList<MouseDragDropTrajectory> getMouseDragDropTrajectory(){
		if (null == mouseDragDrops)
			processTrajectories();
		return mouseDragDrops;
	}
	private void processTrajectories(){
		moveClickTrajectories = new ArrayList<MouseMoveClickTrajectory>(5);
		mouseDragDrops = new ArrayList<MouseDragDropTrajectory>(10);	
		sysWakeupTrajectories = new ArrayList<MouseTrajectory>(5);
		ArrayList<MouseTrajectory> mmts = getMouseMoveTrajectories();
		ArrayList<MouseClick> clicks = getMouseClicks();
		for (MouseTrajectory mmt : mmts) {
			MouseClick click = isDragDrop(mmt, clicks);
			if (null != click)
				mouseDragDrops.add(new MouseDragDropTrajectory(mmt, click));
			else{
				click = isMoveClick(mmt, clicks);
				if (null != click)
					moveClickTrajectories.add(new MouseMoveClickTrajectory(mmt, click));
				else if (isWakeup(mmt, clicks))
					sysWakeupTrajectories.add(mmt);

			}
		}
		
	}
}
