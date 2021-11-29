/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class ZipUtil{
	private final static int BUFFER_LENGTH=1024;

	public static boolean zip(String basePath,String[] includeFilePaths,String archiveFileName) throws Exception{
		boolean success=false;
		ZipOutputStream zout=null;
		FileInputStream fin=null;
		try{
			zout=new ZipOutputStream(new FileOutputStream(new File(basePath+archiveFileName)));
			zout.setMethod(ZipOutputStream.DEFLATED);
			for(int i=0;i<includeFilePaths.length;i++){
				String sourceFileName=basePath+includeFilePaths[i];
				File inputFile=new File(sourceFileName);
				byte buffer[]=new byte[BUFFER_LENGTH];
				/* generate CRC */
				CRC32 crc=new CRC32();
				fin=new FileInputStream(inputFile);
				int length;
				while((length=fin.read(buffer))>-1){
					crc.update(buffer,0,length);
				}
				fin.close();
				//create zip entry
				ZipEntry entry=new ZipEntry(sourceFileName.substring(sourceFileName.lastIndexOf(File.separator)+File.separator.length()));
				entry.setSize(inputFile.length());
				entry.setTime(inputFile.lastModified());
				entry.setCrc(crc.getValue());
				zout.putNextEntry(entry);
				fin=new FileInputStream(inputFile);
				/* write entry to zip file */
				while((length=fin.read(buffer))>-1){
					zout.write(buffer,0,length);
				}
			}
			success=true;
		}catch(Exception x){
			throw(x);
		}finally{
			try{if(zout!=null){zout.closeEntry();zout.close();}}catch(Exception x){}
			try{if(fin!=null){fin.close();}}catch(Exception x){}
		}
		return(success);
	}	

}