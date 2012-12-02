package unittest;


import java.io.File;

import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.MouseClickFeatures;

public class TestMouseClickFeatures extends BaseTest{
	public TestMouseClickFeatures(String path){
		super(path);
	}
	protected void print(File file){
		MouseLogParser parser = new MouseLogParser(file.getAbsolutePath());
		MouseUserProfile _p = parser.getUserProfile();
		System.out.println("File : " + file.getName());
		_p.print();
		MouseClickFeatures s = new MouseClickFeatures(parser);
		print(s.extract());
	}
}
