package edu.pace.biometric.mouse.features;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pace.biometric.mouse.MouseClick;
import edu.pace.biometric.mouse.MouseLogParser;


public class NumberOfWindowsClickedIn extends MouseFeature {

	public NumberOfWindowsClickedIn(MouseLogParser parser, String app) {
		super(parser, app);
	}

	@Override
	public FeatureResult extract() {
		int numOfWindows=0;
		Map<String,String> map=new HashMap<String, String>();
		List<MouseClick> clicks=mouseParser.getMouseClicks(appName);
		for(MouseClick mc:clicks){	
			if(mc.getWindow()!=null && !mc.getWindow().equalsIgnoreCase("")){
				String val=map.get(mc.getWindow());
				if(val==null){
					numOfWindows++;
					map.put(mc.getWindow(),"ADDED");//will not recount window
				}
			}
		}
		return new FeatureResult(getClass().getName(), "Number of Unique Windows clicked in", ""+numOfWindows, "unique windows");
	}
}
