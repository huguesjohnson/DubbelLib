/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.swing;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class VB6InspiredFileChooserPanel extends JPanel{
	private static final long serialVersionUID=666136489L;
	private JComboBox<File> driveListBox;
	private JTree dirListBox;
	private DefaultTreeModel dirTreeModel;
	private JList<File> fileListBox;
	private DefaultListModel<File> fileListModel;

	public VB6InspiredFileChooserPanel(){
		setLayout(new BorderLayout());
		initComponents();
	}

	private void initComponents(){
		//left column - imitation DriveListBox + imitation DirListBox
		JPanel leftPanel=new JPanel(new BorderLayout(0,5));
		//imitation DriveListBox(JComboBox)
		File[] roots=File.listRoots();
		driveListBox=new JComboBox<>(roots!=null?roots:new File[0]);
		driveListBox.addItemListener(e->{
			if(e.getStateChange()==ItemEvent.SELECTED){
				File selectedDrive=(File)e.getItem();
				populateDirListBox(selectedDrive);
			}
		});
		//imitation DirListBox(JTree)
		DefaultMutableTreeNode dummyRoot=new DefaultMutableTreeNode("Drives");
		dirTreeModel=new DefaultTreeModel(dummyRoot);
		dirListBox=new JTree(dirTreeModel);
		dirListBox.setShowsRootHandles(true);
		//lazy-load subdirectories on node expansion
		dirListBox.addTreeWillExpandListener(new TreeWillExpandListener(){
			@Override
			public void treeWillExpand(TreeExpansionEvent event){
				DefaultMutableTreeNode node=(DefaultMutableTreeNode)event.getPath().getLastPathComponent();
				if(node.getUserObject()instanceof File){
					loadSubdirectories(node);
				}
			}
			@Override
			public void treeWillCollapse(TreeExpansionEvent event){}
		});
		//update FileListBox when folder selection changes
		dirListBox.addTreeSelectionListener(e->{
			DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode)dirListBox.getLastSelectedPathComponent();
			if(selectedNode!=null&&selectedNode.getUserObject()instanceof File){
				File selectedFolder=(File)selectedNode.getUserObject();
				populateFileListBox(selectedFolder);
			} else{
				fileListModel.clear();
			}
		});
		leftPanel.add(driveListBox,BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(dirListBox),BorderLayout.CENTER);
		//right column - imitation FileListBox
		fileListModel=new DefaultListModel<>();
		fileListBox=new JList<>(fileListModel);
		//enable multiple file selection
		fileListBox.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane rightScrollPane=new JScrollPane(fileListBox);
		//2-column split container
		JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,rightScrollPane);
		splitPane.setResizeWeight(0.5);
		add(splitPane,BorderLayout.CENTER);
		//initialize with the first drive root if available
		if(roots!=null&&roots.length>0){
			populateDirListBox(roots[0]);
		}
	}

	//populates the imitation DirListBox(JTree)for the selected drive
	private void populateDirListBox(File driveRoot){
		DefaultMutableTreeNode rootNode=new FolderTreeNode(driveRoot);
		dirTreeModel.setRoot(rootNode);
		loadSubdirectories(rootNode);
		dirTreeModel.reload();
		dirListBox.setSelectionRow(0);//select root folder
	}

	//dynamically load subdirectories for a tree node
	private void loadSubdirectories(DefaultMutableTreeNode parentNode){
		parentNode.removeAllChildren();
		File folder=(File)parentNode.getUserObject();
		File[] subDirs=folder.listFiles(File::isDirectory);
		if(subDirs!=null){
			//sort alphabetically
			Arrays.sort(subDirs,(a,b)->a.getName().compareToIgnoreCase(b.getName()));
			for(File subDir:subDirs){
				FolderTreeNode childNode=new FolderTreeNode(subDir);
				parentNode.add(childNode);
			}
		}
	}

	//populate the FileListBox(JList)
	private void populateFileListBox(File folder){
		fileListModel.clear();
		File[] files=folder.listFiles(File::isFile);
		if(files!=null){
			//sort alphabetically
			Arrays.sort(files,(a,b)->a.getName().compareToIgnoreCase(b.getName()));
			for(File file:files){
				fileListModel.addElement(file);
			}
		}
	}

	//returns absolute paths of all files selected in the imitation FileListBox
	public List<String> getSelectedFilePaths(){
		List<String> paths=new ArrayList<>();
		List<File> selectedFiles=fileListBox.getSelectedValuesList();
		for(File file:selectedFiles){
			paths.add(file.getAbsolutePath());
		}
		return(paths);
	}

	//custom node class - for loading child folder indicators
	private static class FolderTreeNode extends DefaultMutableTreeNode{
		private static final long serialVersionUID=666136489L;

		public FolderTreeNode(File file){
			super(file);
			//pre-add a dummy child if subdirectories exist to show the expand indicator
			File[] files=file.listFiles(File::isDirectory);
			if(files!=null&&files.length>0){
				add(new DefaultMutableTreeNode("Loading..."));
			}
		}

		@Override
		public String toString(){
			File f=(File)getUserObject();
			String name=f.getName();
			return(name==null||name.isEmpty())?f.getPath():name;
		}
	}

}