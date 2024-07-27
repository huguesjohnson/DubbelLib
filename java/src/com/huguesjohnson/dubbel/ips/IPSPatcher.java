/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.ips;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public abstract class IPSPatcher{

	public static void copyAndPatch(String sourceFilePath,String destinationFilePath,String ipsPath) throws Exception{
		Files.copy((new File(sourceFilePath)).toPath(),(new File(destinationFilePath)).toPath(),StandardCopyOption.REPLACE_EXISTING);
		patchInPlace(destinationFilePath,ipsPath);
	}

	public static void patchInPlace(String fileToPatchPath,String ipsPath) throws Exception{
		List<IPSRecord> records=IPSReader.readFile(ipsPath);
		RandomAccessFile fout=null;
		try{
			fout=new RandomAccessFile(fileToPatchPath,"rw");
			for(IPSRecord record:records){
				fout.seek(record.getOffset());
				fout.write(record.getData());
			}
		}
		finally{
			if(fout!=null){fout.close();}
		}
	}
}