/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
FilePathComparator - used for sorting lists of files
*/

package com.huguesjohnson.dubbel.file;

import java.io.File;
import java.util.Comparator;

public class FilePathComparator implements Comparator<File>{

	@Override
	public int compare(File arg0,File arg1){
		if((arg0==null)||(arg1==null)){return(-1);}
		if((arg0.getPath()==null)||(arg1.getPath()==null)){return(-1);}
		return(arg0.getPath().compareTo(arg1.getPath()));
	}
}