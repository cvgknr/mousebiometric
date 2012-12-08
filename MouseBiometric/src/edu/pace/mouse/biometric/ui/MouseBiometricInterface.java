package edu.pace.mouse.biometric.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class MouseBiometricInterface extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private JPanel basic =null;
	private JButton inPathBut = null;
	private JButton outPathBut = null;
	private JFileChooser inFC;
	private JFileChooser outFC;
	private JTextField inPathTxtField;
	private JTextField outPathTxtField;
	private JPanel loadFilesPanel;
	private JButton exitBut;
	private JButton extractBut;
	private JTextArea processArea;
	private JCheckBox isView;

	public MouseBiometricInterface() {
		super();
		initGUI();
	}
	public final void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		basic = new JPanel();
		GridLayout FilesPanelLayout = new GridLayout(3, 1);
		FilesPanelLayout.setHgap(5);
		FilesPanelLayout.setVgap(5);
		FilesPanelLayout.setColumns(1);
		basic.setLayout(FilesPanelLayout);
		getContentPane().add(basic);
		basic.setBounds(0, 0, 230, 80);
		basic.setBorder(BorderFactory.createEmptyBorder(10,5,0,0));

		inPathBut = new JButton("PKBS Data Direcotry Path");
		basic.add(inPathBut);
		inPathBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				inputPathButtonActionPerformed(evt);
			}
		});
		inFC = new JFileChooser();
		inFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		outPathBut = new JButton("Output CSV File");
		basic.add(outPathBut);
		outPathBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				outputPathButtonActionPerformed(evt);				
			}
		});
		outFC = new JFileChooser();
		outFC.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		loadFilesPanel = new JPanel();
		GridLayout loadFilesPanelLayout = new GridLayout(3, 1);
		loadFilesPanelLayout.setHgap(5);
		loadFilesPanelLayout.setVgap(5);
		loadFilesPanelLayout.setColumns(1);
		loadFilesPanel.setLayout(loadFilesPanelLayout);
		getContentPane().add(loadFilesPanel);
		loadFilesPanel.setBounds(240, 0, 550, 80);
		loadFilesPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 0));

		inPathTxtField = new JTextField();
		loadFilesPanel.add(inPathTxtField);
		inPathTxtField.setPreferredSize(new java.awt.Dimension(350, 31));
		
		outPathTxtField = new JTextField();
		loadFilesPanel.add(outPathTxtField);
		outPathTxtField.setPreferredSize(new java.awt.Dimension(350, 31));

		isView = new JCheckBox("Generate Headers");
		getContentPane().add(isView);
		isView.setBounds(10, 80, 200, 20);


		processArea = new JTextArea();
		getContentPane().add(processArea);
		processArea.setOpaque(true);
		processArea.setBounds(10, 125,(int) WIDTH-25, HEIGHT-200);
		processArea.setFont(new Font(Font.SERIF, Font.BOLD, 12));
		processArea.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		processArea.setEditable(false);
		processArea.setAutoscrolls(true);

		extractBut = new JButton();
		getContentPane().add(extractBut);
		extractBut.setText("Extract Features");
		extractBut.setBounds(WIDTH-400, HEIGHT-75, 165, 35);
		extractBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				convertButtonActionPerformed(evt);
			}
		});
			
		exitBut = new JButton();
		getContentPane().add(exitBut);
		exitBut.setText("Exit");
		exitBut.setBounds(WIDTH-200, HEIGHT-75, 165, 35);
		exitBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				exitButtonActionPerformed(evt);
			}
		});
		pack();
		this.setSize(800, 500);			
		this.setVisible(true);
		this.setResizable(false); //cannot resize the window
		this.setLocationRelativeTo(null); //Centers the window on the screen.
		this.setExtendedState(MAXIMIZED_BOTH - 50);//Forces the window to stay open, instead of minimizing
		this.setTitle("Extract Mouse Biometric Features from KBS XML");
	}
	private void inputPathButtonActionPerformed(ActionEvent evt) {
		int returnVal = inFC.showDialog(MouseBiometricInterface.this,"Attach"); //changes the key log button to attach
		if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = inFC.getSelectedFile();
            inPathTxtField.setText(file.getAbsolutePath());
	   }
	}
	private void outputPathButtonActionPerformed(ActionEvent evt) {
		int returnVal = outFC.showDialog(MouseBiometricInterface.this,"Attach"); //changes the kpc log button to attach
		if(returnVal == JFileChooser.APPROVE_OPTION){
            File file = outFC.getSelectedFile();
            outPathTxtField.setText(file.getAbsolutePath());
	   }
	}
	private void exitButtonActionPerformed(ActionEvent evt) {
		dispose();
	}
	
	private void convertButtonActionPerformed(ActionEvent evt) {
		if (inPathTxtField.getText().isEmpty() || !(new File(inPathTxtField.getText())).isDirectory()){
			String msg = "Please specify valid Input folder path!";
			JOptionPane.showMessageDialog(this, msg, "Required", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (outPathTxtField.getText().isEmpty()){
			String msg = "Please specify valid Output file path!";
			JOptionPane.showMessageDialog(this, msg, "Required", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (new File(outPathTxtField.getText()).exists()){
			String msg = "The Output file already exist!";
			JOptionPane.showMessageDialog(this, msg, "Required", JOptionPane.ERROR_MESSAGE);
			return;

		}
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				BatchFeatureExtractor extractor = new BatchFeatureExtractor(inPathTxtField.getText(), outPathTxtField.getText(), processArea, !isView.isSelected());
				extractor.generateFeatures();
			}
		});
	}
}
