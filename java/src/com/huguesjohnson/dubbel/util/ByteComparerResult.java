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

	@Override
	public boolean equals(Object obj){
		if(obj==null){return(false);}
		if(!obj.getClass().equals(this.getClass())){return(false);}
		ByteComparerResult bcr=(ByteComparerResult)obj;
		return( (this.file1Line==bcr.getFile1Line())&&
				(this.file1Value==bcr.getFile1Value())&&
				(this.file2Line==bcr.getFile2Line())&&
				(this.file2Value==bcr.getFile2Value()) );
	}

	@Override
	public String toString(){
		String newLine=System.lineSeparator();
		StringBuilder sb=new StringBuilder();
		sb.append("file1Line=");
		sb.append(file1Line);
		sb.append(" [");
		sb.append(this.getFile1LineHex());
		sb.append("]");
		sb.append(newLine);
		sb.append("file1Value=");
		sb.append(file1Value);
		sb.append(" [");
		sb.append(this.getFile1ValueHex());
		sb.append("]");
		sb.append(newLine);
		sb.append("file2Line=");
		sb.append(file2Line);
		sb.append(" [");
		sb.append(this.getFile2LineHex());
		sb.append("]");
		sb.append(newLine);
		sb.append("file2Value=");
		sb.append(file2Value);
		sb.append(" [");
		sb.append(this.getFile2ValueHex());
		sb.append("]");
		return(sb.toString());
	}

	public void setFile1Line(int file1Line){this.file1Line=file1Line;}
	public void setFile1Value(byte file1Value){this.file1Value=file1Value;}
	public void setFile2Line(int file2Line){this.file2Line=file2Line;}
	public void setFile2Value(byte file2Value){this.file2Value=file2Value;}
		
	public int getFile1Line(){return(file1Line);}
	public byte getFile1Value(){return(file1Value);}
	public int getFile2Line(){return(file2Line);}
	public byte getFile2Value(){return(file2Value);}

	public String getFile1LineHex(){return(NumberFormatters.intToHex(file1Line));}
	public String getFile1ValueHex(){return(NumberFormatters.intToHex(file1Value));}
	public String getFile2LineHex(){return(NumberFormatters.intToHex(file2Line));}
	public String getFile2ValueHex(){return(NumberFormatters.intToHex(file2Value));}
}