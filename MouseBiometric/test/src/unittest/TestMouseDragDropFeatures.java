package unittest;


import java.io.File;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.MouseDragDropFeatures;

public class TestMouseDragDropFeatures extends BaseTest{
	public TestMouseDragDropFeatures(String path) {
		super(path);
	}

	public void print(File file){
		MouseLogParser parser = new MouseLogParser(file.getAbsolutePath());
		MouseUserProfile _p = parser.getUserProfile();
		System.out.println("File : " + file.getName());
		_p.print();
		MouseDragDropFeatures s = new MouseDragDropFeatures(parser);
		print(s.extract());
	}
}
