package edu.pace.biometric.mouse.features.main;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.features.FeatureResult;
import edu.pace.biometric.mouse.features.MouseFeature;

public class FeatureExtractor {

	public static List<FeatureResult> extractFeaturesFrom(MouseLogParser _mParser) {
		List<FeatureResult> fr=new ArrayList<FeatureResult>();
		try {
			Reflections reflections = new Reflections("edu.pace.biometric.mouse.features");
			Set<Class<? extends MouseFeature>> classes = reflections.getSubTypesOf(MouseFeature.class);
			for (Class c : classes) {
				Constructor constructor = c.getConstructor(new Class[] { MouseLogParser.class,String.class });
				MouseFeature f = (MouseFeature) constructor.newInstance(_mParser,"Document1 - Microsoft Word");
				FeatureResult result = f.extract();
				if (null != result)
					fr.add(result);
			}
			return fr;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
