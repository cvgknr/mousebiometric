package edu.pace.biometric.mouse.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import edu.pace.biometric.mouse.features.FeatureResult;

public class CsvWriter {
	private final List<FeatureResult> frs;
	private final File file;
	public CsvWriter(List<FeatureResult> frs, File file) {
		super();
		this.frs = frs;
		this.file = file;
	}

	public void toFile(){
		

        FileOutputStream fout=null;
        OutputStreamWriter writer = null;
       
		try {
			
			fout = new FileOutputStream(file);
			//writer= new OutputStreamWriter(fout, System.getProperty("file.encoding"));
			writer= new OutputStreamWriter(fout);
			
			 doWriteData(getHeaders(), writer);
			 doWriteData(getRows(), writer);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			if(writer!=null){
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fout!=null){
				try {
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		

       
	}
	
	
	private List<String> getHeaders(){
		List<String> result=new ArrayList<String>();
		
		for(FeatureResult fr:frs){
			result.add(fr.getLabel());
		}
		
		return result;
	}
	private List<String> getRows(){
		List<String> result=new ArrayList<String>();
		
		for(FeatureResult fr:frs){
			result.add(fr.getValue());
		}
		
		return result;
	}
	 private void doWriteData(List<String> values,OutputStreamWriter writer ) {
		 try {
			 int colCount=0;
			 	String newline = System.getProperty("line.separator");
		        for (String x:values) {
		            if (colCount > 0) {
		                writer.write(",");
		            }

		            if (x != null) {
		                writer.write("\"");
		                writer.write(toCsvValue(x));
		                writer.write("\"");
		            }
		            colCount++;
		        }

		        writer.write(newline);
		} catch (Exception e) {
			e.printStackTrace();
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
