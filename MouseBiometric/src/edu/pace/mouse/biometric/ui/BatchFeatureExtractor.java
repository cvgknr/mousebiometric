package edu.pace.mouse.biometric.ui;
import java.io.*;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JLabel;
import javax.swing.JList;

import org.reflections.Reflections;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.util.CSVWriter;
public class BatchFeatureExtractor{
	public BatchFeatureExtractor(String inFilePath, String outFilePath, JList<String> filesList, JLabel outStatusLabel, JLabel outFileLabel){
		// get the list of files
		File inFolder = new File(inFilePath);
		File[] inFilesList = inFolder.listFiles();
		String []flist = new String[inFilesList.length];
		int count=0;
		for(File file: inFilesList){
			if (file.getName().endsWith(".xml"))
				flist[count++] = file.getName(); 
		}
		filesList.setListData(flist);
		filesList.repaint();
		MouseLogParser parser;
		boolean doneHead = false;
		SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date d = new Date();
		String filename = "Mousebiometri-" + simple.format(d).toString() + ".csv" ;
		outFileLabel.setText("Output File: "+ filename);
		outFileLabel.repaint();
		File csvFile = new File(outFilePath, filename);
		if (!csvFile.exists()){
			CSVWriter w=new CSVWriter(outFilePath, filename);
			List<String> l = new ArrayList<String>(1);
			l.add("Mouse Movement biometric feature extraction data created on " +d.toString());
			w.doWriteData(l);
			l.remove(0);
			l.add(""+count);
			w.doWriteData(l);
			
			for(File file: inFilesList){
				filename = file.getName();
				if (filename.endsWith(".xml")){
					outStatusLabel.setText("Processing File "+ filename);
					outStatusLabel.repaint();
					parser = new MouseLogParser(file.getPath());
					ArrayList<FeatureResult[]> features=extractFeaturesFrom(parser);
					features.add(0, new FeatureResult[]{new FeatureResult(getClass().getSimpleName(), "Filename", filename, "")});
					if(!doneHead){
						w.writeHeaders(features);
						doneHead = true;
					}
					w.writeValues(features);
				}
			}
			try {
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		outStatusLabel.setText("Successful");
		outStatusLabel.repaint();
	}
	public static ArrayList<FeatureResult[]> extractFeaturesFrom(MouseLogParser parser) {
		ArrayList<FeatureResult[]> fr=new ArrayList<FeatureResult[]>();
		fr.add(parser.getUserProfile().extract());
		try {
			// want to dynamically 'load' feature set
			//so we can add features without having to modify this code.
			//All features must implement the Feature interface
			Reflections reflections = new Reflections("edu.pace.mouse.biometric.features");
			Set<Class<? extends Feature>> setclasses = reflections.getSubTypesOf(Feature.class);
			Object []classes = (Object[])setclasses.toArray();
			String t1, t2;
			for(int i=0;i<classes.length;i++){
				int index = i; 
				t1 = classes[i].toString();
				for(int j=i+1;j<classes.length-1;j++){
					t2 = classes[j].toString();
					if (t1.compareTo(t2)>0){
						index = j;
						t1 = classes[index].toString();
					}
				}
				if (i != index){
					Object temp = classes[index];
					classes[index]=classes[i];
					classes[i] = temp;
				}
			}
			Constructor constructor;
			for (int i = 0; i < classes.length; i++) {
				constructor = ((Class)classes[i]).getConstructor(new Class[] { MouseLogParser.class });
				fr.add(((Feature) constructor.newInstance(parser)).extract());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return fr;
	}

}