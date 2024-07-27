/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.ips;

import java.util.ArrayList;
import java.util.List;

import com.huguesjohnson.dubbel.file.FileUtils;

public abstract class IPSReader{
	public static List<IPSRecord> readFile(String filePath) throws Exception{
		ArrayList<IPSRecord> records=new ArrayList<IPSRecord>();
		byte[] b=FileUtils.readBytes(filePath);
		int length=b.length;
		/*
		 * Min size for IPS file with single record
		 * Header=5
		 * Address=3
		 * Length=2
		 * Data=1
		 * EOF=3
		 */
		if(b.length<13){
			throw(new Exception(IPSConstants.ERROR_INVALID_FILE));
		}
		if(
		(b[0]!=IPSConstants.HEADER[0])||
		(b[1]!=IPSConstants.HEADER[1])||
		(b[2]!=IPSConstants.HEADER[2])||
		(b[3]!=IPSConstants.HEADER[3])||
		(b[4]!=IPSConstants.HEADER[4])
		){
			throw(new Exception(IPSConstants.ERROR_INVALID_HEADER));
		}
		int i=5;
		while(i<length){
			//offset is three bytes
			int offset=0;
			offset+=(Byte.toUnsignedInt(b[i])<<16);
			i++;
			offset+=(Byte.toUnsignedInt(b[i])<<8);
			i++;
			offset+=Byte.toUnsignedInt(b[i]);
			i++;
			//record length is two bytes
			int recordLength=0;
			recordLength+=(Byte.toUnsignedInt(b[i])<<8);
			i++;
			recordLength+=Byte.toUnsignedInt(b[i]);
			i++;
			byte[] data=new byte[recordLength];
			for(int j=0;j<recordLength;j++){
				data[j]=b[i];
				i++;
			}
			records.add(new IPSRecord(offset,data));
			//i++;
			//check if EOF
			if(i+2>length){
				throw(new Exception(IPSConstants.ERROR_INVALID_EOF));
			}
			if(
			(b[i]==IPSConstants.EOF[0])&&
			(b[i+1]==IPSConstants.EOF[1])&&
			(b[i+2]==IPSConstants.EOF[2])
			){
				i=length;
			}
		}
		return(records);

	}
}