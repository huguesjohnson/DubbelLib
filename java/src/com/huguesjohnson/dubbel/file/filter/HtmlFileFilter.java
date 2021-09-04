/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;
import java.io.FileFilter;

public class HtmlFileFilter implements FileFilter{

	@Override
	public boolean accept(File f){
		if(f.isDirectory()){
			return(true);
		} 
		String name=f.getName().toLowerCase();
		return(name.endsWith(".html")||name.endsWith(".htm"));
	}
}