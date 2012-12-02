package unittest;



import java.io.File;

import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.MouseWheelScrollFeatures;

public class TestMouseWheelScrollFeatures extends BaseTest{
	public TestMouseWheelScrollFeatures(String path) {
		super(path);
	}

	@Override
	protected void print(File file) {
		MouseLogParser parser = new MouseLogParser(file.getAbsolutePath());
		MouseUserProfile _p = parser.getUserProfile();
		System.out.println("File : " + file.getName());
		_p.print();
		MouseWheelScrollFeatures s = new MouseWheelScrollFeatures(parser);
		print(s.extract());		
	}
}
