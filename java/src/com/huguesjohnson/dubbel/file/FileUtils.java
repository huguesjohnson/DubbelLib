/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class FileUtils{

	//recursively build a list of Files in a directory based on a FileFilter, sort the resulting list
	public final static ArrayList<File> getAllFilesRecursive(File path,FileFilter filter){
		ArrayList<File> files=new ArrayList<File>();
		File[] filelist=path.listFiles(filter);
		int l=filelist.length;
		for(int i=0;i<l;i++){
			if(filelist[i].isDirectory()){
				files.addAll(getAllFilesRecursive(filelist[i],filter));
			}else{
				files.add(filelist[i]);
			}
		}
		Collections.sort(files,(new FilePathComparator()));
		return(files);
	}
	
	//recursively build a list of Files in a directory based on a list of FileFilters, sort the resulting list
	public final static ArrayList<File> getAllFilesRecursive(File path,ArrayList<FileFilter> filters){
		ArrayList<File> files=new ArrayList<File>();
		ArrayList<File> fileList=new ArrayList<File>();
		for(FileFilter filter:filters){
			fileList.addAll(Arrays.asList(path.listFiles(filter)));
		}
		for(File f:fileList) {
			if(f.isDirectory()){
				files.addAll(getAllFilesRecursive(f,filters));
			}else{
				files.add(f);
			}
		}
		Collections.sort(files,(new FilePathComparator()));
		return(files);
	}
}