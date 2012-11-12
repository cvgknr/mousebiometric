package unittest;


import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.MouseClickFeatures;

public class TestMouseClickFeatures {
	public static void main(String []args){
		MouseLogParser parser = new MouseLogParser("./test/python/NedBakelman_WordProcessor_001.xml");
		MouseUserProfile _p = parser.getUserProfile();
		_p.print();
		MouseClickFeatures s = new MouseClickFeatures(parser);
		FeatureResult []f = s.extract();
		for (int i=0;i<f.length;i++){
			if (null != f[i])
				System.out.println(f[i].toString());
		}
	}
}
