package edu.pace.biometric.mouse.features;

import edu.pace.biometric.mouse.MouseLogParser;

/**
 * 
 * @author Venugopala C
 *
 */

public abstract class MouseFeature {
	protected final MouseLogParser mouseParser;
	protected final String appName;
	public MouseFeature(MouseLogParser parser, String app){
		this.mouseParser = parser;
		this.appName = app;
	}
	public abstract FeatureResult extract();
}
