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
	private ArrayList<MouseTrajectory> sysWakeupTrajectories=null;
	private ArrayList<MouseMoveClickTrajectory> moveClickTrajectories=null;
	private ArrayList<MouseTrajectory> trajectories=null;
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
	private boolean isDoubleClick(MouseClick first, MouseClick second){
		if( (second.getMousereleasetime() - first.getMousepresstime())<=500)
			return true;
		return false;
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
	private ArrayList<MouseMove> getTrajectoryMoves(MousePointer mp){
		ArrayList<MouseMove> tmm = new ArrayList<MouseMove>(5);
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		ArrayList<MouseMove> mms = getMouseMoves();
		for (MouseMove mouseMove : mms) {
			if (mouseMove.getStarttime()<starttime) continue;
			if (mouseMove.getStarttime() <= endtime)
				tmm.add(tmm.size(), mouseMove);
			else
				break;
		}
		return tmm;
	}
	private boolean isWakeup(MousePointer mp, ArrayList<MouseClick> clicks){
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (starttime<= mouseClick.getMousepresstime() && mouseClick.getMousepresstime() <= endtime)
				return false;
		}
		return true;
	}
	private MouseClick isMoveClick(MousePointer mp, ArrayList<MouseClick> clicks){
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
	private MouseClick isDragDrop(MousePointer mp, ArrayList<MouseClick> clicks){
		long starttime = mp.getStarttime();
		long endtime = mp.getEndtime();
		for (MouseClick mouseClick : clicks) {
			if (mouseClick.getMousepresstime() <= starttime && mouseClick.getMousereleasetime() >= endtime)
				return mouseClick;
		}
		return null;
	}
	private void processTrajectories(){
		if(null == moveClickTrajectories){
			moveClickTrajectories = new ArrayList<MouseMoveClickTrajectory>(5);
			mouseDragDrops = new ArrayList<MouseDragDropTrajectory>(10);	
			sysWakeupTrajectories = new ArrayList<MouseTrajectory>(5);
			trajectories = new ArrayList<MouseTrajectory>(5);
			ArrayList<MouseClick> clicks = getMouseClicks();
			ArrayList<MousePointer> pointers = getMousePointers();
			ArrayList<MouseMove> trajMoves = getMouseMoves();
			MouseClick preClick = null;
			for (MousePointer mp : pointers) {
				trajMoves = getTrajectoryMoves(mp);
				MouseClick click = isDragDrop(mp, clicks);
				if (null != click){
					//Check if the last trajectory is move click and click belongs to current drag and drop 
					if (click == preClick){
						MouseMoveClickTrajectory m = moveClickTrajectories.remove(moveClickTrajectories.size()-1);
						trajectories.add(trajectories.size(), new MouseTrajectory(m.getMousePointer(),m.getMouseMoves()));
					}
					mouseDragDrops.add(new MouseDragDropTrajectory(mp, trajMoves, click));
				}else{
					click = isMoveClick(mp, clicks);
					if (null != click){
						moveClickTrajectories.add(moveClickTrajectories.size(), new MouseMoveClickTrajectory(mp, trajMoves, click));
						preClick = click;
					}else{
						preClick = null;
						if (isWakeup(mp, clicks))
							sysWakeupTrajectories.add(sysWakeupTrajectories.size(), new MouseTrajectory(mp,trajMoves));
						else
							trajectories.add(trajectories.size(), new MouseTrajectory(mp,trajMoves));
					}
				}
			}
		}
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
	/**
	 * 
	 * @return return all System Wake up Mouse Move trajectories.
	 */
	public ArrayList<MouseMoveClickTrajectory> getMouseMoveAndClickTrajectory(){
		if (null == moveClickTrajectories)
			processTrajectories();
		return moveClickTrajectories;
	}
	/**
	 * 
	 * @return all Mouse DragDrop Trajectories.
	 */
	public ArrayList<MouseDragDropTrajectory> getMouseDragDropTrajectory(){
		if (null == mouseDragDrops)
			processTrajectories();
		return mouseDragDrops;
	}
	public ArrayList<MouseTrajectory> getOtherTrajectory(){
		if (null == trajectories)
			processTrajectories();
		return trajectories;
	}
}
