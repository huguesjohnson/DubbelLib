/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

public class ByteRangeComparerResult{
	private int file1Line;
	private int file2Line;
	private byte[] file1Value;
	private byte[] file2Value;
	
	public ByteRangeComparerResult(){}
	
	public ByteRangeComparerResult(int file1Line,byte[] file1Value,int file2Line,byte[] file2Value){
		this.file1Line=file1Line;
		this.file1Value=file1Value;
		this.file2Line=file2Line;
		this.file2Value=file2Value;
	}

	@Override
	public boolean equals(Object obj){
		if(obj==null){return(false);}
		if(!obj.getClass().equals(this.getClass())){return(false);}
		ByteRangeComparerResult bcr=(ByteRangeComparerResult)obj;
		if(this.file1Line!=bcr.getFile1Line()){return(false);}
		if(this.file2Line!=bcr.getFile2Line()){return(false);}
		//compare file1 values
		byte[] b=bcr.getFile1Value();
		if(this.file1Value==null){
			if(b==null){return(true);}
			return(false);
		}else{
			if(b==null){return(false);}
			int length=this.file1Value.length;
			if(length!=b.length){return(false);}
			for(int i=0;i<length;i++){
				if(this.file1Value[i]!=b[i]){return(false);}
			}
		}
		//compare file2 values
		b=bcr.getFile2Value();
		if(this.file2Value==null){
			if(b==null){return(true);}
			return(false);
		}else{
			if(b==null){return(false);}
			int length=this.file2Value.length;
			if(length!=b.length){return(false);}
			for(int i=0;i<length;i++){
				if(this.file2Value[i]!=b[i]){return(false);}
			}
			return(true);
		}		
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
		sb.append(NumberFormatters.byteArrayToString(this.file1Value));
		sb.append(newLine);
		sb.append("file2Line=");
		sb.append(file2Line);
		sb.append(" [");
		sb.append(this.getFile1LineHex());
		sb.append("]");
		sb.append(newLine);
		sb.append(NumberFormatters.byteArrayToString(this.file2Value));
		return(sb.toString());
	}

	public void setFile1Line(int file1Line){this.file1Line=file1Line;}
	public void setFile1Value(byte[] file1Value){this.file1Value=file1Value;}
	public void setFile2Line(int file2Line){this.file2Line=file2Line;}
	public void setFile2Value(byte[] file2Value){this.file2Value=file2Value;}
		
	public int getFile1Line(){return(this.file1Line);}
	public byte[] getFile1Value(){return(this.file1Value);}
	public int getFile2Line(){return(this.file2Line);}
	public byte[] getFile2Value(){return(this.file2Value);}

	public String getFile1LineHex(){return(NumberFormatters.intToHex(this.file1Line));}
	public String getFile2LineHex(){return(NumberFormatters.intToHex(this.file2Line));}
}