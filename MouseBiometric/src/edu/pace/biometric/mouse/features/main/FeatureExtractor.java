package edu.pace.biometric.mouse.features.main;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;
//import org.reflections.Reflections;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.features.MouseFeature;
import edu.pace.biometric.mouse.features.MouseMoveAverageSpeed;

public class FeatureExtractor {

	public static void main(String []args) {
		MouseLogParser _mParser = new MouseLogParser("./python/NedBakelman_WordProcessor_001.xml");
		HashMap<String, String> features = new HashMap<String, String>();
		try {
			// want to dynamically 'load' feature set
			//so we can add features without having to modify this code.
			//All features must implement the Feature interface
			//Reflections reflections = new Reflections("com.biometric.mouse.features");
			//Set<Class<? extends MouseFeature>> classes = reflections.getSubTypesOf(MouseFeature.class);
			/*for (Class c : classes) {
				Constructor constructor = c.getConstructor(new Class[] { MouseLogParser.class,String.class });
				MouseFeature f = (MouseFeature) constructor.newInstance(_mParser,"Document1 - Microsoft Word");
				features.put(c.getSimpleName(), f.extract());
			}*/
			MouseMoveAverageSpeed s = new MouseMoveAverageSpeed(_mParser, "Document1 - Microsoft Word");
			System.out.println(s.extract());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		System.out.println(features.toString());
	}

}
