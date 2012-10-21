package edu.pace.biometric.mouse.features;

public class FeatureResult {
	
	
	private final String value;
	private final String unit;
	private final String className;
	private final String label;
	
	public FeatureResult( String className,String label, 
			String value, String unit) {
		super();
		
		this.value = value;
		this.unit = unit;
		this.className = className;
		this.label=label;
	}
	public String getLabel() {
		return label;
	}
	
	public String getValue() {
		return value;
	}
	public String getUnit() {
		return unit;
	}
	
	public String getClassName() {
		return className;
	}
	public String toString(){
		return className + " " + label + " " + value + " " + unit;
		
	}
	
	
	
}
