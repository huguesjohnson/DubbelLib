/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
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
	
    public static boolean compareFiles(File originalFile,File compareFile){
        /* compare file lengths first */
        long fileLength=originalFile.length();
        if(fileLength!=compareFile.length()){
            return(false);
        }else{
            boolean verified=true;
            FileInputStream fInOriginal=null;
            FileInputStream fInCompare=null;
            try{
                fInOriginal=new FileInputStream(originalFile);
                fInCompare=new FileInputStream(compareFile);
                int originalByte=fInOriginal.read();
                int compareByte=fInCompare.read();
                while((originalByte==compareByte)&&(originalByte!=-1)&&verified){
                    originalByte=fInOriginal.read();
                    compareByte=fInCompare.read();
                    verified=originalByte==compareByte;
                }
            }catch(Exception x){
            	x.printStackTrace();
            	verified=false;
            }finally{
                try{if(fInOriginal!=null){fInOriginal.close();}}catch(Exception x){x.printStackTrace();}
                try{if(fInCompare!=null){fInCompare.close();}}catch(Exception x){x.printStackTrace();}
            }
            return(verified);
        }
    }
    
	public static byte[] readBytes(String path,int startByte,int length) throws IOException{
		File f=new File(path);
		FileInputStream fis=new FileInputStream(f);
		byte[] b=new byte[length];
		fis.skip(startByte);
		fis.read(b,0,length);
		fis.close();
		return(b);
	}

}