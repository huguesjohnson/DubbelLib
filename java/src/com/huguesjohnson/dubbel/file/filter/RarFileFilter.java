/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter;

import java.io.File;
import java.io.FileFilter;

//returns true for either .rar or .rsn files
//the latter because I added this for a utility that unpacks .rsn files
public class RarFileFilter implements FileFilter{

	@Override
	public boolean accept(File f){
		if(f.isDirectory()){
			return(true);
		}
		String lname=f.getName().toLowerCase();
		return(lname.endsWith(".rar")||lname.endsWith(".rsn"));
	}
}