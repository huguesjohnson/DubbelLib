/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;
import java.io.FileFilter;

public class ImageFileFilter implements FileFilter{
	    private final String[] exts=new String[]{"png","jpg","jpeg","gif","bmp"};

	    public boolean accept(File file){
			if(file.isDirectory()){
				return(true);
			} 	    	
			String fl=file.getName().toLowerCase();
	        for(String ext:exts){
	        	if(fl.endsWith(ext)){
	        		return(true);
	            }
	        }
	        return(false);
	    }
	}