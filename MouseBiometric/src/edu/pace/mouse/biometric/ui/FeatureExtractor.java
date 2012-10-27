package edu.pace.mouse.biometric.ui;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;

public class FeatureExtractor {

	public static ArrayList<FeatureResult[]> extractFeaturesFrom(MouseLogParser parser) {
		ArrayList<FeatureResult[]> fr=new ArrayList<FeatureResult[]>();
		try {
			// want to dynamically 'load' feature set
			//so we can add features without having to modify this code.
			//All features must implement the Feature interface
			Reflections reflections = new Reflections("edu.pace.it691.features");
			Set<Class<? extends Feature>> classes = reflections
					.getSubTypesOf(Feature.class);
			for (Class c : classes) {
				Constructor constructor = c
						.getConstructor(new Class[] { MouseLogParser.class });
				Feature f = (Feature) constructor.newInstance(parser);
				fr.add(f.extract());
				
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return fr;
	}
}
