package unittest;


import java.awt.Dimension;
import java.util.ArrayList;

import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseMoveTrajectory;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.SystemWakeUpTrajectoryFeatures;


public class TestSystemWakeUpTrajectoryFeature{
	public static void main(String []args){
		MouseLogParser parser = new MouseLogParser("./test/python/NedBakelman_WordProcessor_001.xml");
		//MouseLogParser parser = new MouseLogParser("./logsamples/Venugopala3_Browser_001.xml");
		MouseUserProfile _p = parser.getUserProfile();
		_p.print();
		SystemWakeUpTrajectoryFeatures s = new SystemWakeUpTrajectoryFeatures(parser);
		FeatureResult []f = s.extract();
		for (int i=0;i<f.length;i++){
			if (null != f[i])
				System.out.println(f[i].toString());
		}
		ArrayList<MouseMoveTrajectory> t = parser.getSystemWakeUpTranjectories();
		System.out.println("Number of Trajectories: " + t.size());
		DrawTrajectory c = new DrawTrajectory(t);
        c.setSize(new Dimension(1500,1200));
        c.setVisible(true);
		
	}
}
