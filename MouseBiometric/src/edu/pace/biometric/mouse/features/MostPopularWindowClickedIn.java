package edu.pace.biometric.mouse.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;

public class MostPopularWindowClickedIn extends MouseFeature {

	public MostPopularWindowClickedIn(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		Map<String,Integer> map=new HashMap<String, Integer>();
		List<MouseClick> clicks=mouseParser.getMouseClicks(appName);
		for(MouseClick mc:clicks){	
			
			if(mc.getWindow()!=null && !mc.getWindow().equalsIgnoreCase("")){
				Integer val=map.get(mc.getWindow());
				if(val==null){
					val=new Integer(0);
					
					
				}
				
				int y=val.intValue()+1;
				map.put(mc.getWindow(),new Integer(y));	
				
			}	
		}
		String mostPopular="Could not calculate";
		int count=0;
		for(Map.Entry<String, Integer> me:map.entrySet()){
			
			if(me.getValue().intValue()>count){
				
				count=me.getValue();
				mostPopular=me.getKey();
				
			}
		}
		
		return new FeatureResult(getClass().getName(), "Most Popular Window Clicked In", mostPopular, "most popular");
	}
}
