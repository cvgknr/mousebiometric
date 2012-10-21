package edu.pace.biometric.mouse.persist;

import java.util.List;

import edu.pace.biometric.mouse.features.FeatureResult;

public interface DataStore {
	public void saveFeatures(List<FeatureResult> fr,String firstName,String lastName);
	public void clearDataForThisUser(String firstName,String lastName);
}
