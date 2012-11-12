package unittest;


import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.MouseDragDropFeatures;

public class TestMouseDragDropFeatures {
	public static void main(String []args){
		MouseLogParser parser = new MouseLogParser("./logsamples/NedBakelman_Browser_006.xml");
		MouseUserProfile _p = parser.getUserProfile();
		_p.print();
		MouseDragDropFeatures s = new MouseDragDropFeatures(parser);
		FeatureResult []f = s.extract();
		for (int i=0;i<f.length;i++){
			if (null != f[i])
				System.out.println(f[i].toString());
		}
	}
}
