/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.ips;

import java.io.FileOutputStream;
import java.util.List;

public abstract class IPSWriter{
	public final static void writeIPSFile(String filePath,List<IPSRecord> records) throws Exception{
		FileOutputStream fout=null;
		try{
			fout=new FileOutputStream(filePath);
			fout.write(IPSConstants.HEADER);
			for(IPSRecord record:records){
				byte[] offset=intToBytes(record.getOffset(),3);
				fout.write(offset);
				byte[] length=intToBytes(record.length(),2);
				fout.write(length);
				fout.write(record.getData());
			}
			fout.write(IPSConstants.EOF);
		}
		finally{
			if(fout!=null){fout.flush();fout.close();}
		}
	}
	
	public final static byte[] intToBytes(int i,int numBytes){
		byte[] b=new byte[numBytes];
		for(int j=0;j<numBytes;j++){
			int shift=8*j;
			b[numBytes-1-j]=(byte)((i>>shift)&0xFF);
		}
		return(b);
	}
}