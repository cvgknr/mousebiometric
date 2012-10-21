package unittest;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.features.FeatureResult;
import edu.pace.biometric.mouse.features.MouseClickTotalPerMinute;
import edu.pace.biometric.mouse.features.MouseFeature;

public class TestFeature{
	public static void main(String []args){
		MouseLogParser _mParser = new MouseLogParser("./python/NedBakelman_WordProcessor_001.xml");
		MouseFeature f = new MouseClickTotalPerMinute(_mParser, "Document1 - Microsoft Word");
		FeatureResult result = f.extract(); 
		System.out.println(result);
	}
}
