package edu.pace.mouse.biometric.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import edu.pace.mouse.biometric.core.FeatureResult;

public class CSVWriter extends File{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FileOutputStream fout=null;
    OutputStreamWriter writer = null;
	public CSVWriter(String folder, String filename) {
		super(folder,filename);
		try {
			fout = new FileOutputStream(this);
			writer= new OutputStreamWriter(fout);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public void close() throws IOException{
		if ( null != writer)
			writer.close();
		if (null != fout)
			fout.close();
	}
	public void writeHeaders(ArrayList<FeatureResult[]> features){
		List<String> result=new ArrayList<String>();
		for(FeatureResult[] f:features){
			for (int i = 0; i < f.length; i++) {
				result.add(f[i].getLabel());
			}
		}
		doWriteData(result);
	}
	public void writeValues(ArrayList<FeatureResult[]> features){
		List<String> result=new ArrayList<String>();
		for(FeatureResult[] f:features){
			for (int i = 0; i < f.length; i++) {
				result.add(f[i].getValue());
			}
		}
		doWriteData(result);
	}
	 public void doWriteData(List<String> values) {
		 try {
			int colCount=0;
		 	String newline = System.getProperty("line.separator");
	        for (String x:values) {
	            if (colCount > 0)
	                writer.write(",");
	            if (x != null) {
	                writer.write("\"");
	                writer.write(toCsvValue(x));
	                writer.write("\"");
	            }
	            colCount++;
	        }
	        writer.write(newline);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	 }
	 private String toCsvValue(String str ) {
		 StringBuilder sb = new StringBuilder();
	     for (int i = 0; i < str.length(); i++) {
	     	char c = str.charAt(i);
	        sb.append(c);
	        switch (c) {
	        	case '"' :
	        		sb.append('"');
	                break;
	        }
	     }
	     return sb.toString();
	}
}