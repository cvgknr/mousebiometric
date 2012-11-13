package unittest;


import java.io.File;

import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.data.MouseUserProfile;
import edu.pace.mouse.biometric.features.MoveDragDropTrajectoryFeatures;

public class TestMouseDragDropTrajectoryFeatures {
	public static void printFeatures(File file){
		MouseLogParser parser = new MouseLogParser(file.getAbsolutePath());
		MouseUserProfile _p = parser.getUserProfile();
		System.out.println("File : " + file.getName());
		_p.print();
		MoveDragDropTrajectoryFeatures s = new MoveDragDropTrajectoryFeatures(parser);
		FeatureResult []f = s.extract();
		for (int i=0;i<f.length;i++){
			if (null != f[i])
				System.out.println(f[i].toString());
		}
		
	}
	public static void main(String []args){
		String inFolderPath = "C:\\Users\\Administrator\\git\\mouse\\MouseBiometric\\logsamples";
		File inFolder = new File(inFolderPath);
		if (inFolder.isDirectory()){
			File[] inFilesList = inFolder.listFiles();
			for(File file: inFilesList){
				if (file.getName().endsWith(".xml"))
					printFeatures(file);
			}
		}
	}
}
