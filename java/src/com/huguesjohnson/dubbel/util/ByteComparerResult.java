/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

public class ByteComparerResult{
	private int file1Line;
	private int file2Line;
	private byte file1Value;
	private byte file2Value;
	
	public ByteComparerResult(){}
	
	public ByteComparerResult(int file1Line,byte file1Value,int file2Line,byte file2Value){
		this.file1Line=file1Line;
		this.file1Value=file1Value;
		this.file2Line=file2Line;
		this.file2Value=file2Value;
	}

	public void setFile1Line(int file1Line){this.file1Line=file1Line;}
	public void setFile1Value(byte file1Value){this.file1Value=file1Value;}
	public void setFile2Line(int file2Line){this.file2Line=file2Line;}
	public void setFile2Value(byte file2Value){this.file2Value=file2Value;}
		
	public int getFile1Line(){return(file1Line);}
	public byte getFile1Value(){return(file1Value);}
	public int getFile2Line(){return(file2Line);}
	public byte getFile2Value(){return(file2Value);}

	public String getFile1LineHex(){return("0x"+Integer.toHexString(file1Line).toUpperCase());}
	public String getFile1ValueHex(){return("0x"+Integer.toHexString(file1Value).toUpperCase());}
	public String getFile2LineHex(){return("0x"+Integer.toHexString(file2Line).toUpperCase());}
	public String getFile2ValueHex(){return("0x"+Integer.toHexString(file2Value).toUpperCase());}
}