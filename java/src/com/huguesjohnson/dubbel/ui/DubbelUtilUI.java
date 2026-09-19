/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.huguesjohnson.dubbel.audio.MidiPlayer;
import com.huguesjohnson.dubbel.converters.BinToWav;
import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.file.filter.ImageFileFilter;
import com.huguesjohnson.dubbel.file.filter.Mp3FileFilter;
import com.huguesjohnson.dubbel.ips.IPSPatcher;
import com.huguesjohnson.dubbel.util.AsciiStringFinder;
import com.huguesjohnson.dubbel.util.ByteComparer;
import com.huguesjohnson.dubbel.util.ByteComparerResult;

public class DubbelUtilUI extends JFrame{
	private static final long serialVersionUID=666136489L;
	//global objects
	private MidiPlayer midiPlayer=new MidiPlayer();
	private FileFilter uuidRenamefilter;
	private String currentDir=System.getProperty("user.home");
	//global controls
	private JTabbedPane tabbedPane;
	private JTextArea logTextArea;
	//controls for BinToWav tab
	private JTextField txtCuePath;
	private JTextField txtOutputPath;
	private JButton btnConvertBinToWav;
	//controls for MIDI player tab
	private JTextField txtMidiPath;
	private JCheckBox chkMidiLoop;
	private JButton btnMidiPlay;
	private JButton btnMidiStop;	
	//controls for compare files tab
	private JTextField txtOriginalPath;
	private JTextField txtComparePath;
	private JButton btnCompareFiles;	
	//controls for UUID Rename tab
	private JTextField txtUuidPath;
	private JComboBox<String> cmbUuidFilter;
	private JCheckBox chkUuidPreserveExt;
	private JCheckBox chkUuidDryRun;
	private JButton btnUuidRename;	
	//controls for ASCII String Finder tab
	private JTextField txtAsciiPath;
	private JTextField txtAsciiMinLength;
	private JCheckBox chkAsciiLetters;
	private JCheckBox chkAsciiNumbers;
	private JCheckBox chkAsciiSpecial;
	private JButton btnAsciiFind;	
	//controls for IPS Patcher tab
	private JTextField txtIpsSourcePath;
	private JTextField txtIpsDestPath;
	private JTextField txtIpsPatchPath;
	private JButton btnIpsPatch;	
	//controls for Byte Comparer tab
	private JTextField txtBytePath1;
	private JTextField txtByteStart1;
	private JTextField txtBytePath2;
	private JTextField txtByteStart2;
	private JTextField txtByteLength;
	private JButton btnByteCompare;
	
	public DubbelUtilUI(){
		super("Dubbel Utilities 2026-09-19");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setLocationRelativeTo(null);
		initUI();
	}

	private void initUI(){
		//setup tabs
		tabbedPane=new JTabbedPane();
		//TODO - icons?
		tabbedPane.addTab("MIDI player",this.getMidiPlayerPanel());
		tabbedPane.addTab("BinToWav",this.getBinToWavPanel());
		tabbedPane.addTab("Compare files",this.getCompareFilesPanel());
		tabbedPane.addTab("UUID rename",this.getUUIDRenamePanel());
		tabbedPane.addTab("IPS patcher",this.getIPSPatcherPanel());
		tabbedPane.addTab("ASCII string finder",this.getASCIIStringFinderPanel());
		tabbedPane.addTab("Byte comparer",this.getByteComparerPanel());
		//setup logging area
		logTextArea=new JTextArea(5,50);
		logTextArea.setEditable(false);
		JScrollPane logScrollPane=new JScrollPane(logTextArea);
		JPanel bottomPanel=new JPanel(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
		bottomPanel.add(logScrollPane,BorderLayout.CENTER);
		setupLogContextMenu();
		//add everything
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabbedPane,BorderLayout.CENTER);
		getContentPane().add(bottomPanel,BorderLayout.SOUTH);
	}
	
	/*
	 * context menu for log area
	 */
	
	private void setupLogContextMenu(){
		JPopupMenu popupMenu=new JPopupMenu();
		//clear
		JMenuItem menuItemClear=new JMenuItem("Clear");
		menuItemClear.addActionListener(e->logTextArea.setText(""));
		//copy to clipboard
		JMenuItem menuItemCopy=new JMenuItem("Copy to clipboard");
		menuItemCopy.addActionListener(e->{
			String text=logTextArea.getText();
			if(!text.isEmpty()){
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
			}
		});
		//save as
		JMenuItem menuItemSaveAs=new JMenuItem("Save as...");
		menuItemSaveAs.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				try(FileWriter writer=new FileWriter(chooser.getSelectedFile())){
					writer.write(logTextArea.getText());
				} catch(IOException x){
					showErrorDialog(x);
				}
			}
		});
		//add them
		popupMenu.add(menuItemClear);
		popupMenu.add(menuItemCopy);
		popupMenu.add(menuItemSaveAs);
		logTextArea.setComponentPopupMenu(popupMenu);
	}	
	
	/*
	 * BinToWav stuff
	 */
	
	private JPanel getBinToWavPanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - cue file selection
		JLabel lblCue=new JLabel(".cue file path:");
		txtCuePath=new JTextField(255);
		JButton btnBrowseCue=new JButton("Browse...");
		btnBrowseCue.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			//TODO filter
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtCuePath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblCue,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtCuePath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseCue,gbc);
		//row 2 - output directory selection
		JLabel lblOutput=new JLabel("output directory:");
		txtOutputPath=new JTextField(255);
		JButton btnBrowseOutput=new JButton("Browse...");
		btnBrowseOutput.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtOutputPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.0;
		mainPanel.add(lblOutput,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtOutputPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseOutput,gbc);
		//row 3 - output directory selection
		btnConvertBinToWav=new JButton("Convert");
		btnConvertBinToWav.setEnabled(false);
		btnConvertBinToWav.addActionListener(e->runBinToWav());
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnConvertBinToWav,gbc);
		//toggle button state based on filled paths
		DocumentListener inputListener=new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkBinToWavInputs();}
			public void removeUpdate(DocumentEvent e){checkBinToWavInputs();}
			public void changedUpdate(DocumentEvent e){checkBinToWavInputs();}
		};
		txtCuePath.getDocument().addDocumentListener(inputListener);
		txtOutputPath.getDocument().addDocumentListener(inputListener);
		//push contents up to the top
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}	

	private void runBinToWav(){
		String cuePath=txtCuePath.getText().trim();
		String outputPath=txtOutputPath.getText().trim();
		btnConvertBinToWav.setEnabled(false);
		SwingWorker<ArrayList<String>,Void> worker=new SwingWorker<ArrayList<String>,Void>(){
			@Override
			protected ArrayList<String> doInBackground() throws Exception{
				appendLog("Running BinToWav.binToWav");
				appendLog("cuePath="+cuePath);
				appendLog("outputPath="+outputPath);
				return(BinToWav.binToWav(cuePath,outputPath));
			}
			@Override
			protected void done(){
				btnConvertBinToWav.setEnabled(true);
				try{
					appendLog("BinToWav.binToWav done");
					ArrayList<String> outputLogs=get();
					if(outputLogs!=null){
						appendLog("Files created:");
						for(String line:outputLogs){
							appendLog(line);
						}
					}else{
						appendLog("No files created");
					}
				}catch(Exception ex){
					showErrorDialog(ex);
				}
			}
		};
		worker.execute();
	}	
	
	private void checkBinToWavInputs(){
		boolean ready=(!txtCuePath.getText().trim().isEmpty())&&(!txtOutputPath.getText().trim().isEmpty());
		btnConvertBinToWav.setEnabled(ready);
	}	
	
	/*
	 * MIDI player stuff
	 */

	private JPanel getMidiPlayerPanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - file selection
		JLabel lblMidi=new JLabel("MIDI file path:");
		txtMidiPath=new JTextField(255);
		JButton btnBrowseMidi=new JButton("Browse...");
		btnBrowseMidi.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			//TODO - filter
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtMidiPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblMidi,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtMidiPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseMidi,gbc);
		//row 2 - loop checkbox
		chkMidiLoop=new JCheckBox("Loop");
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=3;
		gbc.weightx=0.0;
		mainPanel.add(chkMidiLoop,gbc);
		//row 3 - play/stop buttons
		JPanel btnPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		btnMidiPlay=new JButton("Play");
		btnMidiStop=new JButton("Stop");
		btnMidiPlay.setEnabled(false);
		btnMidiStop.setEnabled(false);
		btnMidiPlay.addActionListener(e->startMidiPlayback());
		btnMidiStop.addActionListener(e->stopMidiPlayback());
		btnPanel.add(btnMidiPlay);
		btnPanel.add(Box.createRigidArea(new Dimension(8,0)));
		btnPanel.add(btnMidiStop);
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnPanel,gbc);
		//toggle play button state
		txtMidiPath.getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkMidiInput();}
			public void removeUpdate(DocumentEvent e){checkMidiInput();}
			public void changedUpdate(DocumentEvent e){checkMidiInput();}
		});
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}

	private void checkMidiInput(){
		//toggle button state
		if(!btnMidiStop.isEnabled()){
			btnMidiPlay.setEnabled(!txtMidiPath.getText().trim().isEmpty());
		}
	}

	private void startMidiPlayback(){
		String midiPath=txtMidiPath.getText().trim();
		btnMidiPlay.setEnabled(false);
		btnMidiStop.setEnabled(true);
		SwingWorker<Void,Void> worker=new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception{
				appendLog("Playing MIDI file: "+ midiPath+" (Loop: "+chkMidiLoop.isSelected()+ ")");
				int loop=-1;
				if(chkMidiLoop.isSelected()){
					loop=Integer.MAX_VALUE;
				}
				midiPlayer.play(midiPath,loop);
				return(null);
			}
			@Override
			protected void done(){
				try{
					get();
				}catch(Exception x){
					showErrorDialog(x);
					btnMidiStop.setEnabled(false);
					btnMidiPlay.setEnabled(!txtMidiPath.getText().trim().isEmpty());
				}
			}
		};
		worker.execute();
	}

	private void stopMidiPlayback(){
		SwingWorker<Void,Void> worker=new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception{
				appendLog("Stopping MIDI playback...");
				midiPlayer.stop();
				return(null);
			}
			@Override
			protected void done(){
				btnMidiStop.setEnabled(false);
				btnMidiPlay.setEnabled(!txtMidiPath.getText().trim().isEmpty());
				try{
					get();
					appendLog("MIDI playback stopped.");
				}catch(Exception x){
					showErrorDialog(x);
				}
			}
		};
		worker.execute();
	}	
	
	/*
	 * Compare files stuff 	
	 */

	private JPanel getCompareFilesPanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - original file selection
		JLabel lblOriginal=new JLabel("Original file path:");
		txtOriginalPath=new JTextField(255);
		JButton btnBrowseOriginal=new JButton("Browse...");
		btnBrowseOriginal.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtOriginalPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblOriginal,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtOriginalPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseOriginal,gbc);
		//row 2 - compare file selection
		JLabel lblCompare=new JLabel("Compare file path:");
		txtComparePath=new JTextField(255);
		JButton btnBrowseCompare=new JButton("Browse...");
		btnBrowseCompare.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtComparePath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.0;
		mainPanel.add(lblCompare,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtComparePath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseCompare,gbc);
		//row 3 - compare button
		btnCompareFiles=new JButton("Compare");
		btnCompareFiles.setEnabled(false);
		btnCompareFiles.addActionListener(e->runCompareFiles());
		gbc.gridx=0; gbc.gridy=2; gbc.gridwidth=3; gbc.fill=GridBagConstraints.NONE; gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnCompareFiles,gbc);
		//validate input state
		DocumentListener inputListener=new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkCompareInputs();}
			public void removeUpdate(DocumentEvent e){checkCompareInputs();}
			public void changedUpdate(DocumentEvent e){checkCompareInputs();}
		};
		txtOriginalPath.getDocument().addDocumentListener(inputListener);
		txtComparePath.getDocument().addDocumentListener(inputListener);
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}

	private void checkCompareInputs(){
		boolean ready=(!txtOriginalPath.getText().trim().isEmpty())&&(!txtComparePath.getText().trim().isEmpty());
		btnCompareFiles.setEnabled(ready);
	}

	private void runCompareFiles(){
		String originalPath=txtOriginalPath.getText().trim();
		String comparePath=txtComparePath.getText().trim();
		btnCompareFiles.setEnabled(false);
		SwingWorker<Boolean,Void> worker=new SwingWorker<Boolean,Void>(){
			@Override
			protected Boolean doInBackground() throws Exception{
				appendLog("Comparing: "+originalPath+" AND "+comparePath);
				return(FileUtils.compareFiles(originalPath,comparePath));
			}
			@Override
			protected void done(){
				btnCompareFiles.setEnabled(true);
				try{
					boolean identical=get();
					if(identical){
						appendLog("Files are identical");
					} else{
						appendLog("Files are different");
					}
				} catch(Exception x){
					showErrorDialog(x);
				}
			}
		};
		worker.execute();
	}	
	
	/*
	 * UUID rename stuff 	
	 */	
	
	private JPanel getUUIDRenamePanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - path selection
		JLabel lblPath=new JLabel("Path:");
		txtUuidPath=new JTextField(255);
		JButton btnBrowseUuid=new JButton("Browse...");
		btnBrowseUuid.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtUuidPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblPath,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtUuidPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseUuid,gbc);
		//row 2 - file filter
		JLabel lblFilter=new JLabel("File filter:");
		String[] filterOptions={"ImageFileFilter","MP3FileFilter"};
		cmbUuidFilter=new JComboBox<>(filterOptions);
		cmbUuidFilter.setEditable(false);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.0;
		mainPanel.add(lblFilter,gbc);
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.weightx=1.0;
		mainPanel.add(cmbUuidFilter,gbc);
		//row 3 - preserve extension checkbox
		chkUuidPreserveExt=new JCheckBox("Preserve file extension",true);
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=3;
		gbc.weightx=0.0;
		mainPanel.add(chkUuidPreserveExt,gbc);
		//row 4 - dry run checkbox
		chkUuidDryRun=new JCheckBox("Dry run",true);
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=3;
		gbc.weightx=0.0;
		mainPanel.add(chkUuidDryRun,gbc);
		//row 5 - action button
		btnUuidRename=new JButton("Rename Files");
		btnUuidRename.setEnabled(false);
		btnUuidRename.addActionListener(e->runUuidRename());
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnUuidRename,gbc);
		//toggle button state based on path presence
		txtUuidPath.getDocument().addDocumentListener(new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkUuidInput();}
			public void removeUpdate(DocumentEvent e){checkUuidInput();}
			public void changedUpdate(DocumentEvent e){checkUuidInput();}
		});
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}

	private void checkUuidInput(){
		btnUuidRename.setEnabled(!txtUuidPath.getText().trim().isEmpty());
	}

	private void runUuidRename(){
		String path=txtUuidPath.getText().trim();
		boolean preserveExtension=chkUuidPreserveExt.isSelected();
		boolean dryRun=chkUuidDryRun.isSelected();
		//map selection string to FileFilter instance
		String txFilter=(String) cmbUuidFilter.getSelectedItem();
		//make this the default in case I did something silly
		uuidRenamefilter=new Mp3FileFilter();
		if(txFilter.equals("ImageFileFilter")){
			uuidRenamefilter=new ImageFileFilter();
		}
		btnUuidRename.setEnabled(false);
		SwingWorker<Map<String,String>,Void> worker=new SwingWorker<Map<String,String>,Void>(){
			@Override
			protected Map<String,String> doInBackground() throws Exception{
				appendLog("Starting UUID Rename on: "+path+" [DryRun="+dryRun+"]");
				return FileUtils.uuidRenamer(path,uuidRenamefilter,preserveExtension,dryRun);
			}
			@Override
			protected void done(){
				btnUuidRename.setEnabled(true);
				try{
					Map<String,String> resultMap=get();
					if(resultMap!=null){
						for(Map.Entry<String,String> entry:resultMap.entrySet()){
							appendLog(entry.getKey()+":"+entry.getValue());
						}
					}
				} catch(Exception x){
					showErrorDialog(x);
				}
			}
		};
		worker.execute();
	}
	
	/*
	 * ASCII string finder stuff 	
	 */	
	
	private JPanel getASCIIStringFinderPanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - File path selection
		JLabel lblPath=new JLabel("File path:");
		txtAsciiPath=new JTextField(255);
		JButton btnBrowseAscii=new JButton("Browse...");
		btnBrowseAscii.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtAsciiPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblPath,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtAsciiPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseAscii,gbc);
		//row 2 - minimum length
		JLabel lblMinLength=new JLabel("Minimum length:");
		txtAsciiMinLength=new JTextField("10",10);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.0;
		mainPanel.add(lblMinLength,gbc);
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.weightx=1.0;
		mainPanel.add(txtAsciiMinLength,gbc);
		//row 3 - accept letters checkbox
		chkAsciiLetters=new JCheckBox("Accept letters",true);
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=3;
		gbc.weightx=0.0;
		mainPanel.add(chkAsciiLetters,gbc);
		//row 4 - accept numbers
		chkAsciiNumbers=new JCheckBox("Accept numbers",true);
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=3;
		gbc.weightx=0.0;
		mainPanel.add(chkAsciiNumbers,gbc);
		//row 5 - accept special characters
		chkAsciiSpecial=new JCheckBox("Accept Special characters",true);
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=3;
		gbc.weightx=0.0;
		mainPanel.add(chkAsciiSpecial,gbc);
		//row 6 - action button
		btnAsciiFind=new JButton("Find Strings");
		btnAsciiFind.setEnabled(false);
		btnAsciiFind.addActionListener(e->runAsciiFinder());
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnAsciiFind,gbc);
		//validate inputs
		DocumentListener inputListener=new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkAsciiInputs();}
			public void removeUpdate(DocumentEvent e){checkAsciiInputs();}
			public void changedUpdate(DocumentEvent e){checkAsciiInputs();}
		};
		txtAsciiPath.getDocument().addDocumentListener(inputListener);
		txtAsciiMinLength.getDocument().addDocumentListener(inputListener);
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}

	private void checkAsciiInputs(){
		boolean pathValid=!txtAsciiPath.getText().trim().isEmpty();
		boolean lengthValid=false;
		try{
			int minLen=Integer.parseInt(txtAsciiMinLength.getText().trim());
			if(minLen>0){
				lengthValid=true;
			}
		}catch(NumberFormatException ignored){}
		btnAsciiFind.setEnabled(pathValid&&lengthValid);
	}

	private void runAsciiFinder(){
		String filePath=txtAsciiPath.getText().trim();
		int minStringLength=Integer.parseInt(txtAsciiMinLength.getText().trim());
		boolean acceptLetters=chkAsciiLetters.isSelected();
		boolean acceptNum=chkAsciiNumbers.isSelected();
		boolean acceptSpecial=chkAsciiSpecial.isSelected();
		btnAsciiFind.setEnabled(false);
		SwingWorker<Map<String,String>,Void> worker=new SwingWorker<Map<String,String>,Void>(){
			@Override
			protected Map<String,String> doInBackground() throws Exception{
				appendLog("Searching ASCII strings in: "+filePath+"(Min len: "+minStringLength+")");
				return AsciiStringFinder.findInFile(filePath,minStringLength,acceptLetters,acceptNum,acceptSpecial,null);
			}
			@Override
			protected void done(){
				btnAsciiFind.setEnabled(true);
				try{
					Map<String,String> resultMap=get();
					if(resultMap!=null){
						for(Map.Entry<String,String> entry:resultMap.entrySet()){
							appendLog(entry.getKey()+":"+entry.getValue());
						}
					}
				}catch(Exception x){
					showErrorDialog(x);
				}
			}
		};
		worker.execute();
	}	

	/*
	 * IPS patcher stuff 	
	 */	
		
	private JPanel getIPSPatcherPanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - original file selection
		JLabel lblSource=new JLabel("Original file path:");
		txtIpsSourcePath=new JTextField(255);
		JButton btnBrowseSource=new JButton("Browse...");
		btnBrowseSource.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtIpsSourcePath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblSource,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtIpsSourcePath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseSource,gbc);
		//row 2 - patched file selection(Save Dialog)
		JLabel lblDest=new JLabel("Patched file path:");
		txtIpsDestPath=new JTextField(255);
		JButton btnBrowseDest=new JButton("Browse...");
		btnBrowseDest.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtIpsDestPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.0;
		mainPanel.add(lblDest,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtIpsDestPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowseDest,gbc);
		//row 3 - .ips file selection
		JLabel lblPatch=new JLabel(".ips file path:");
		txtIpsPatchPath=new JTextField(255);
		JButton btnBrowsePatch=new JButton("Browse...");
		btnBrowsePatch.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtIpsPatchPath.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.weightx=0.0;
		mainPanel.add(lblPatch,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtIpsPatchPath,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowsePatch,gbc);
		//row 4 - action button
		btnIpsPatch=new JButton("Patch File");
		btnIpsPatch.setEnabled(false);
		btnIpsPatch.addActionListener(e->runIpsPatcher());
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnIpsPatch,gbc);
		//check input field validity
		DocumentListener inputListener=new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkIpsInputs();}
			public void removeUpdate(DocumentEvent e){checkIpsInputs();}
			public void changedUpdate(DocumentEvent e){checkIpsInputs();}
		};
		txtIpsSourcePath.getDocument().addDocumentListener(inputListener);
		txtIpsDestPath.getDocument().addDocumentListener(inputListener);
		txtIpsPatchPath.getDocument().addDocumentListener(inputListener);
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}

	private void checkIpsInputs(){
		boolean ready=!txtIpsSourcePath.getText().trim().isEmpty()&&
		                !txtIpsDestPath.getText().trim().isEmpty()&&
		                !txtIpsPatchPath.getText().trim().isEmpty();
		btnIpsPatch.setEnabled(ready);
	}

	private void runIpsPatcher(){
		String sourceFilePath=txtIpsSourcePath.getText().trim();
		String destinationFilePath=txtIpsDestPath.getText().trim();
		String ipsPath=txtIpsPatchPath.getText().trim();
		btnIpsPatch.setEnabled(false);
		SwingWorker<Void,Void> worker=new SwingWorker<Void,Void>(){
			@Override
			protected Void doInBackground() throws Exception{
				appendLog("Applying IPS patch...");
				appendLog("Source: "+sourceFilePath);
				appendLog("Patch: "+ipsPath);
				appendLog("Destination: "+destinationFilePath);
				IPSPatcher.copyAndPatch(sourceFilePath,destinationFilePath,ipsPath);
				return(null);
			}
			@Override
			protected void done(){
				btnIpsPatch.setEnabled(true);
				try{
					get();
					appendLog("Successfully patched file to: "+destinationFilePath);
				} catch(Exception x){
					showErrorDialog(x);
				}
			}
		};
		worker.execute();
	}	
	
	/*
	 * byte comparer stuff 	
	 */	
		
	private JPanel getByteComparerPanel(){
		JPanel mainPanel=new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.insets=new Insets(4,4,4,4);
		gbc.fill=GridBagConstraints.HORIZONTAL;
		//row 1 - path 1
		JLabel lblPath1=new JLabel("Path 1:");
		txtBytePath1=new JTextField(255);
		JButton btnBrowsePath1=new JButton("Browse...");
		btnBrowsePath1.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtBytePath1.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.weightx=0.0;
		mainPanel.add(lblPath1,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtBytePath1,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowsePath1,gbc);
		//row 2 - start byte 1
		JLabel lblStart1=new JLabel("Start byte 1:");
		txtByteStart1=new JTextField("0",10);
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.weightx=0.0;
		mainPanel.add(lblStart1,gbc);
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.weightx=1.0;
		mainPanel.add(txtByteStart1,gbc);
		//row 3 - path 2
		JLabel lblPath2=new JLabel("Path 2:");
		txtBytePath2=new JTextField(255);
		JButton btnBrowsePath2=new JButton("Browse...");
		btnBrowsePath2.addActionListener(e->{
			JFileChooser chooser=new JFileChooser(currentDir);
			if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
				currentDir=chooser.getCurrentDirectory().getPath();
				txtBytePath2.setText(chooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		gbc.weightx=0.0;
		mainPanel.add(lblPath2,gbc);
		gbc.gridx=1;
		gbc.weightx=1.0;
		mainPanel.add(txtBytePath2,gbc);
		gbc.gridx=2;
		gbc.weightx=0.0;
		mainPanel.add(btnBrowsePath2,gbc);
		//row 4 - start byte 2
		JLabel lblStart2=new JLabel("Start byte 2:");
		txtByteStart2=new JTextField("0",10);
		gbc.gridx=0; 
		gbc.gridy=3; 
		gbc.gridwidth=1; 
		gbc.weightx=0.0;
		mainPanel.add(lblStart2,gbc);
		gbc.gridx=1; 
		gbc.gridwidth=2; 
		gbc.weightx=1.0;
		mainPanel.add(txtByteStart2,gbc);
		//row 5 - length
		JLabel lblLength=new JLabel("Length:");
		txtByteLength=new JTextField("1024",10);
		gbc.gridx=0;
		gbc.gridy=4;
		gbc.gridwidth=1;
		gbc.weightx=0.0;
		mainPanel.add(lblLength,gbc);
		gbc.gridx=1;
		gbc.gridwidth=2;
		gbc.weightx=1.0;
		mainPanel.add(txtByteLength,gbc);
		//row 6 - action button
		btnByteCompare=new JButton("Compare Bytes");
		btnByteCompare.setEnabled(false);
		btnByteCompare.addActionListener(e->runByteCompare());
		gbc.gridx=0;
		gbc.gridy=5;
		gbc.gridwidth=3;
		gbc.fill=GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.WEST;
		mainPanel.add(btnByteCompare,gbc);
		//validate inputs
		DocumentListener inputListener=new DocumentListener(){
			public void insertUpdate(DocumentEvent e){checkByteComparerInputs();}
			public void removeUpdate(DocumentEvent e){checkByteComparerInputs();}
			public void changedUpdate(DocumentEvent e){checkByteComparerInputs();}
		};
		txtBytePath1.getDocument().addDocumentListener(inputListener);
		txtByteStart1.getDocument().addDocumentListener(inputListener);
		txtBytePath2.getDocument().addDocumentListener(inputListener);
		txtByteStart2.getDocument().addDocumentListener(inputListener);
		txtByteLength.getDocument().addDocumentListener(inputListener);
		JPanel containerPanel=new JPanel(new BorderLayout());
		containerPanel.add(mainPanel,BorderLayout.NORTH);
		return(containerPanel);
	}

	private void checkByteComparerInputs(){
		boolean pathsValid=!txtBytePath1.getText().trim().isEmpty()&&
							!txtBytePath2.getText().trim().isEmpty();
		boolean numbersValid=isNonNegativeInteger(txtByteStart1.getText().trim())&&
		                       isNonNegativeInteger(txtByteStart2.getText().trim())&&
		                       isNonNegativeInteger(txtByteLength.getText().trim());

		btnByteCompare.setEnabled(pathsValid && numbersValid);
	}

	private boolean isNonNegativeInteger(String text){
		if(text.isEmpty()){return(false);}
		try{
			int value=Integer.parseInt(text);
			return(value>=0);
		} catch(NumberFormatException e){
			return(false);
		}
	}

	private void runByteCompare(){
		String path1=txtBytePath1.getText().trim();
		int startByte1=Integer.parseInt(txtByteStart1.getText().trim());
		String path2=txtBytePath2.getText().trim();
		int startByte2=Integer.parseInt(txtByteStart2.getText().trim());
		int length=Integer.parseInt(txtByteLength.getText().trim());
		btnByteCompare.setEnabled(false);
		SwingWorker<List<ByteComparerResult>,Void> worker=new SwingWorker<List<ByteComparerResult>,Void>(){
			@Override
			protected List<ByteComparerResult> doInBackground() throws Exception{
				appendLog("Comparing bytes: "+path1+" ["+startByte1+"] vs "+path2+" ["+startByte2+"](Len: "+length+")");
				return ByteComparer.compare(path1,startByte1,path2,startByte2,length);
			}
			@Override
			protected void done(){
				btnByteCompare.setEnabled(true);
				try{
					List<ByteComparerResult> results=get();
					if(results!=null){
						for(ByteComparerResult result:results){
							appendLog(result.toString());
						}
					}
				} catch(Exception x){
					showErrorDialog(x);
				}
			}
		};
		worker.execute();
	}
	
	/*
	 * Global UI stuff
	 */
	
	public void appendLog(String message){
		SwingUtilities.invokeLater(()->{
			logTextArea.append(message+"\n");
			logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
		});
	}

	private void showErrorDialog(Throwable throwable){
		StringWriter sw=new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		JTextArea textArea=new JTextArea(sw.toString(),12,50);
		textArea.setEditable(false);
		appendLog(sw.toString());
		JScrollPane scrollPane=new JScrollPane(textArea);
		JOptionPane.showMessageDialog(this,scrollPane,"Error: "+throwable.getMessage(),JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(()->{
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			}catch(Exception x){/* ignoring this exception */}
			(new DubbelUtilUI()).setVisible(true);
		});
	}
}