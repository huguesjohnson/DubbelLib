/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;
import java.io.FileFilter;

public class GenericFileFilter implements FileFilter{
	private String[] validExtensions=null;

	public GenericFileFilter(String validExtension){
		this.validExtensions=new String[1];
		this.validExtensions[0]=validExtension;
	}

	public GenericFileFilter(String[] validExtensions){
		this.validExtensions=validExtensions;
	}
	
	@Override
	public boolean accept(File f){
		return(FileFilterUtil.accept(f,this.validExtensions));
	}
}