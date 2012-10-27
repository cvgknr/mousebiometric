package edu.pace.mouse.biometric.persist;

import java.util.ArrayList;
import java.util.List;

import edu.pace.mouse.biometric.core.FeatureResult;

public interface DataStore {
	public void saveFeatures(ArrayList<FeatureResult[]> fr,String firstName,String lastName);
	public void clearDataForThisUser(String firstName,String lastName);
}
