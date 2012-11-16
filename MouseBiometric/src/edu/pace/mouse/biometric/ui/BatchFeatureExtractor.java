package edu.pace.mouse.biometric.ui;
import java.io.*;
import java.lang.reflect.Constructor;
import java.sql.Timestamp;
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
	public BatchFeatureExtractor(String inFilePath, String outFilePath, JList filesList, JLabel outStatusLabel, JLabel outFileLabel){
		// get the list of files
		File inFolder = new File(inFilePath);
		File[] inFilesList = inFolder.listFiles();
		String []flist = new String[inFilesList.length];
		int i=0;
		for(File file: inFilesList){
			if (file.getName().endsWith(".xml"))
				flist[i++] = file.getName(); 
		}
		filesList.setListData(flist);
		filesList.repaint();
		MouseLogParser parser;
		boolean doneHead = false;
		Date d = new Date();
		SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSS");

		String filename = "Mousebiometri-" + simple.format(new Date()).toString() + ".csv" ;
		outFileLabel.setText("Output File: "+ filename);
		outFileLabel.repaint();
		File csvFile = new File(outFilePath, filename);
		if (!csvFile.exists()){
			CSVWriter w=new CSVWriter(outFilePath, filename);
			for(File file: inFilesList){
				filename = file.getName();
				if (filename.endsWith(".xml")){
					outStatusLabel.setText("Processing File "+ filename);
					outStatusLabel.repaint();
					parser = new MouseLogParser(file.getPath());
					ArrayList<FeatureResult[]> features=extractFeaturesFrom(parser);
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
			Set<Class<? extends Feature>> classes = reflections.getSubTypesOf(Feature.class);
			Constructor constructor;
			for (Class c : classes) {
				constructor = c.getConstructor(new Class[] { MouseLogParser.class });
				fr.add(((Feature) constructor.newInstance(parser)).extract());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return fr;
	}

}