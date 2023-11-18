/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.io.IOException;
import java.util.ArrayList;

import com.huguesjohnson.dubbel.file.FileUtils;

public abstract class ByteComparer{

	/*
	 * byte-by-byte comparison of two files 
	 * really intended to compare segments of files
	 * path1, path2 -> the two files to compare
	 * startByte1, startByte2 -> the starting byte to compare at
	 * length -> how many bytes to compare
	 * throws exceptions for things like
	 * -either of the files don't exist
	 * -either of the files can't be opened for some reason
	 * -either of the startByte values are bad (i.e. past end of file or negative)
	 * -length+either startByte is past EOF for the respective file
	 */
	public final static ArrayList<ByteComparerResult> compare(String path1,int startByte1,String path2,int startByte2,int length) throws IOException{
		ArrayList<ByteComparerResult> result=new ArrayList<ByteComparerResult>();
		/*
		 * there are two ways to tackle this, each with trade-offs
		 * 1) read the full set of bytes from each file, close them, then compare
		 * -this uses more memory and will explode for very large files 
		 * 2) point an input stream to each file and compare byte-by-byte
		 * -slower
		 * I went with the first option, changing to the second is trivial
		 */
		//read bytes
		byte[] b1=FileUtils.readBytes(path1,startByte1,length);
		byte[] b2=FileUtils.readBytes(path2,startByte2,length);
		//compare
		int i=0;
		while(i<length){
			if(b1[i]!=b2[i]){
				result.add(new ByteComparerResult(startByte1+i,b1[i],startByte2+i,b2[i]));
			}
			i++;
		}
		return(result);
	} 

}