/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;
import java.io.FileFilter;

public class CueFileFilter implements FileFilter{

	@Override
	public boolean accept(File f){
		if(f.isDirectory()){
			return(true);
		} 
		return(f.getName().toLowerCase().endsWith(".cue"));
	}
}