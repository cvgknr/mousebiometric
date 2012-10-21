package edu.pace.biometric.mouse.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

import edu.pace.biometric.mouse.MouseLogParser;
import edu.pace.biometric.mouse.UserProfile;
import edu.pace.biometric.mouse.features.FeatureResult;
import edu.pace.biometric.mouse.features.main.FeatureExtractor;
import edu.pace.biometric.mouse.persist.DataStore;
import edu.pace.biometric.mouse.persist.PersistentStore;
import edu.pace.biometric.mouse.util.CsvWriter;

public class Ui extends JFrame {
	private final int WIDTH = 850;
	private final int HEIGHT = 850;
	private JFileChooser jfc = null;
	private JTextPane pane = null;
	private JTextField filechosen = null;
	private JPanel basic =null;
	private JButton extractBtn =null;
	private UserProfile user = null;
	private JButton clearUserDataBtn=null;
	private JButton exportDataBtn=null;
	private JLabel label1 =null;
	private List<FeatureResult> frs=null;
	private static final String newline = "\n";

	public Ui() {
		initUI();
	}

	public final void initUI() {

		basic = new JPanel();
		basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
		add(basic);

		JPanel topPanel = new JPanel(new FlowLayout());
		topPanel.setMaximumSize(new Dimension(WIDTH, 0));
		JLabel label = new JLabel("Please upload the Biometric XML file");
		label.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));
		JButton showFileDialogBtn = new JButton("Select a File");
		filechosen = new JTextField();
		filechosen.setSize(new Dimension(10, 20));
		filechosen
				.setText("                                                    ");

		showFileDialogBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Set up the file chooser.
				if (jfc == null) {
					jfc = new JFileChooser();

				}

				// Show it.
				int returnVal = jfc.showDialog(pane, "Attach XML file");

				// Process the results.
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					if(!file.getName().toLowerCase().endsWith("xml")){
						JOptionPane.showConfirmDialog(basic, "You must select an XML file", 
								"Warning:  Wrong file type selected", JOptionPane.WARNING_MESSAGE);
					}else{
						filechosen.setText(file.getAbsolutePath());
						pane.setText("Attaching file: " + file.getName() + "."
								+ newline);
						extractBtn.setEnabled(true);
						clearUserDataBtn.setEnabled(false);
						label1.setVisible(false);
					}
					
				} else {
					pane.setText("Attachment cancelled by user." + newline);
				}

				// Reset the file chooser for the next time it's shown.
				jfc.setSelectedFile(null);
			}
		});

		topPanel.add(label, BorderLayout.NORTH);
		topPanel.add(showFileDialogBtn, BorderLayout.NORTH);
		topPanel.add(filechosen, BorderLayout.NORTH);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.gray);

		topPanel.add(separator, BorderLayout.SOUTH);

		basic.add(topPanel);

		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

		label1 = new JLabel(
				"Feature extraction results will appear here");
		label1.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

		pane = new JTextPane();
		pane.setSize(new Dimension(400, 700));
		pane.setContentType("text/html");
		String text = "";
		pane.setText(text);
		pane.setEditable(false);
		textPanel.add(label1, BorderLayout.NORTH);
		textPanel.add(pane, BorderLayout.CENTER);

		basic.add(textPanel);

		JPanel boxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));

		basic.add(boxPanel);

		JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		defineExtractButton();
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		
		defineClearUserDataButton();
		defineExportToCSVButton();
		bottom.add(exportDataBtn);
		bottom.add(clearUserDataBtn);
		bottom.add(extractBtn);
		bottom.add(close);
		basic.add(bottom);

		bottom.setMaximumSize(new Dimension(WIDTH, 0));

		setTitle("Mouse Feature Extractor");
		setSize(new Dimension(WIDTH, 450));
		setResizable(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	private void defineExtractButton(){
		extractBtn = new JButton("Extract");
		extractBtn.setEnabled(false);
		extractBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				extractBtn.setEnabled(false);
				try{
					String fileName = filechosen.getText();
					MouseLogParser _mParser = new MouseLogParser(fileName);
					user = _mParser.getUserProfile();
					String t="<b>User: "+user.getFirstName()+" "+user.getLastName()+"</b><br/>";
					frs=FeatureExtractor.extractFeaturesFrom(_mParser);
					pane.setText(t+createFeatureResultString(frs));
					try{
						DataStore ds=PersistentStore.getDataStore();
						ds.saveFeatures(frs,user.getFirstName(),user.getLastName());
						JOptionPane.showMessageDialog(basic, "Saved results to database", "Information", JOptionPane.INFORMATION_MESSAGE);
					}catch (Exception e) {
						JOptionPane.showMessageDialog(basic, "Error saving to database:"+e.getMessage(), "Information", JOptionPane.INFORMATION_MESSAGE);
					}
					clearUserDataBtn.setEnabled(true);
					exportDataBtn.setEnabled(true);
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}catch(Exception ex){
					ex.printStackTrace();
					pane.setText("<span style='color:red;'><b>Cannot proceed:  an error has occurred:"+ex.getMessage()+"</b></span>");
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				
			}
		});
	}
	private void defineClearUserDataButton(){
		clearUserDataBtn = new JButton("Clear Data For this User");
		clearUserDataBtn.setEnabled(false);
		clearUserDataBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int action=JOptionPane.showConfirmDialog(basic, "Are you sure you want to delete all Features for this user from the database?", "Clear Data", JOptionPane.OK_CANCEL_OPTION);
				if(action==JOptionPane.OK_OPTION){
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					try{
						DataStore ds=PersistentStore.getDataStore();
						ds.clearDataForThisUser(user.getFirstName(), user.getLastName());
						pane.setText("");
						filechosen.setText("                                                    ");
						label1.setVisible(true);
						exportDataBtn.setEnabled(false);
						clearUserDataBtn.setEnabled(false);
						JOptionPane.showMessageDialog(basic, "Data for "+user.getFirstName()+" "+user.getLastName()+" has been deleted", "Information", JOptionPane.INFORMATION_MESSAGE);
					}catch (Exception e) {
						JOptionPane.showMessageDialog(basic, "Error saving to database:"+e.getMessage(), "Information", JOptionPane.INFORMATION_MESSAGE);
					}
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
				
					
				
			}
		});
	}
	
	private void defineExportToCSVButton(){
		exportDataBtn = new JButton("Export to CSV");
		exportDataBtn.setEnabled(false);
		exportDataBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					// Set up the file chooser.
					
					JFileChooser fc = new JFileChooser();
					fc.setSelectedFile( new File("MouseCSV_"+System.currentTimeMillis()+".csv"));
					int actionDialog = fc.showSaveDialog(pane);
					if ( actionDialog == JFileChooser.APPROVE_OPTION ){
						try{
							File file = fc.getSelectedFile();
							System.out.println(file.getAbsolutePath());
							CsvWriter w=new CsvWriter(frs, file);
							w.toFile();
							JOptionPane.showMessageDialog(basic, "Feature Results have been exported to "+file.getAbsolutePath(), "Information", JOptionPane.INFORMATION_MESSAGE);
							exportDataBtn.setEnabled(false);
						}catch (Exception e) {
							JOptionPane.showMessageDialog(basic, "Error saving file:"+e.getMessage(), "Information", JOptionPane.INFORMATION_MESSAGE);
						}
						
						
					}
					
					// Reset the file chooser for the next time it's shown.
					fc.setSelectedFile(null);
						
				}
			});
	}
	
	private String createFeatureResultString(List<FeatureResult> frs){
		StringBuilder sb=new StringBuilder();
		for(FeatureResult fr:frs){
			sb.append(fr.getLabel()+":"+fr.getValue()+" "+fr.getUnit());
			sb.append("<br/>");
		}
		
		return sb.toString();
	}

}
