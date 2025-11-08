/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;
import java.io.FileFilter;

public class Mp3FileFilter implements FileFilter{
	@Override
	public boolean accept(File f){
		return(FileFilterUtil.accept(f,"mp3"));
	}
}