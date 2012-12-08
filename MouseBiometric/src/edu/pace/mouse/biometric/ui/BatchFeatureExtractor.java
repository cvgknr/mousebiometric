package edu.pace.mouse.biometric.ui;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

import javax.swing.JTextArea;

import org.reflections.Reflections;

import edu.pace.mouse.biometric.core.Feature;
import edu.pace.mouse.biometric.core.FeatureResult;
import edu.pace.mouse.biometric.data.MouseLogParser;
import edu.pace.mouse.biometric.util.CSVWriter;
public class BatchFeatureExtractor{
	private String inFolderPath;
	private String outFilePath;
	private boolean doneHead;
	private JTextArea processArea;
	public BatchFeatureExtractor(String inFolderPath, String outFilePath, JTextArea pArea, boolean doneHead){
		this.inFolderPath = inFolderPath;
		this.outFilePath = outFilePath;
		this.doneHead = doneHead;
		this.processArea = pArea;
	}
	public void generateFeatures(){
		// get the list of files
		File inFolder = new File(inFolderPath);
		File[] inFilesList = inFolder.listFiles();
		String []flist = new String[inFilesList.length];
		int count=0;
		for(File file: inFilesList){
			if (file.getName().endsWith(".xml"))
				flist[count++] = file.getName(); 
		}
		MouseLogParser parser;
		processArea.setText("Output File: "+ outFilePath);
		processArea.repaint();
		String filename;
		CSVWriter w=new CSVWriter(outFilePath);
		List<String> l = new ArrayList<String>(1);
		Date d = new Date();
		l.add("Mouse biometric data, created " +d.toString());
		w.doWriteData(l);
		l.remove(0);
		l.add(""+count);
		w.doWriteData(l);
		int i=0;
		for(File file: inFilesList){
			filename = file.getName();
			if (filename.endsWith(".xml")){
				processArea.append("\nProcessing File "+ file.getAbsolutePath());
				processArea.repaint();
				parser = new MouseLogParser(file.getPath());
				ArrayList<FeatureResult> features=extractFeaturesFrom(parser);
				features.add(3, new FeatureResult(getClass().getSimpleName(), "Filename", filename + " " + i++, ""));
				features.add(4, new FeatureResult(getClass().getSimpleName(), "Field Count", ""+features.size(), ""));
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
		processArea.append("Successful");
		processArea.repaint();
	}
	public ArrayList<FeatureResult> extractFeaturesFrom(MouseLogParser parser) {
		ArrayList<FeatureResult> fr=new ArrayList<FeatureResult>();
		fr.addAll(parser.getUserProfile().extract());
		try {
			// want to dynamically 'load' feature set
			//so we can add features without having to modify this code.
			//All features must implement the Feature interface
			Reflections reflections = new Reflections("edu.pace.mouse.biometric.features");
			Set<Class<? extends Feature>> setclasses = reflections.getSubTypesOf(Feature.class);
			Object []classes = (Object[])setclasses.toArray();
			String t1;
			for(int i=0;i<classes.length-1;i++){
				int index = i; 
				t1 = classes[i].toString();
				for(int j=i+1;j<classes.length;j++){
					if (t1.compareTo(classes[j].toString())>0){
						index = j;
						t1 = classes[j].toString();
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
				((Feature) constructor.newInstance(parser)).extract();
				fr.addAll(((Feature) constructor.newInstance(parser)).extract());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return fr;
	}
}