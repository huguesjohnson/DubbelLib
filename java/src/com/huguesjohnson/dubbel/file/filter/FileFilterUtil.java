/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;

public abstract class FileFilterUtil{

	public static boolean accept(File f,String validExtension){
		String[] validExtensions=new String[1];
		validExtensions[0]=validExtension;
		return(accept(f,validExtensions));
	}
	
	public static boolean accept(File f,String[] validExtensions){
		if(f==null){
			return(false);
		}
		if((validExtensions==null)||(validExtensions.length<1)){
			return(false);
		}
		if(f.isHidden()){
			return(false);
		}
		if(f.isDirectory()){
			return(true);
		}
		String name=f.getName();
		int indexOf=name.lastIndexOf('.');
		if(indexOf<0){
			return(false);
		}
		if((indexOf+1)>=name.length()){
			return(false);
		}
		String ext=name.substring(indexOf+1);
		for(String validExtension:validExtensions){
			if(ext.equalsIgnoreCase(validExtension)){return(true);}
		}
		return(false);
	}
}