/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AsciiStringFinder{

	/**
	 * Looks for blocks of consecutive ascii characters in a file.
	 * Not optimized for memory use at all.
	 * @param filePath full path to the file to search
	 * @param minStringLength the minimum length a string needs to be before being added to the return map
	 * @param acceptLetters whether to accept letters when searching (a-z,A-Z)
	 * @param acceptNum whether to accept numbers when searching (0-9)
	 * @param acceptSpecial whether to accept special characters when searching
	 * @param acceptCustom array of bytes that should always be accepted - using this is slow, null is an acceptable value
	 * @return a map in the form of <address,string found>
	 */
	public final static Map<String,String> findInFile(String filePath,int minStringLength,boolean acceptLetters,boolean acceptNum,boolean acceptSpecial,byte[] acceptCustom){
		//using LinkedHashMap means the results will be sorted by address
		Map<String,String> map=new LinkedHashMap<String,String>();
		AsciiStringFinder.AcceptByte ab=new AsciiStringFinder.AcceptByte(acceptLetters,acceptNum,acceptSpecial,acceptCustom);
		try{
			byte[] f=Files.readAllBytes((new File(filePath)).toPath());
			int i=0;
			while(i<f.length){
				if(ab.acceptByte(f[i])){
					int end=+i;
						while((end<f.length)&&(ab.acceptByte(f[end]))){
							end++;
						}
						if((end-i)>=minStringLength){
							byte[] sub=(byte[])Arrays.copyOfRange(f,i,end);
							map.put("0x"+Integer.toHexString(i),((new String(sub)).toString()));
							i=end;
						}
				}
				i++;
			}
		}catch(Exception x){
			x.printStackTrace();			
		}	
		return(map);
	}
	
	protected static class AcceptByte{
		final boolean acceptLetters;
		final boolean acceptNum;
		final boolean acceptSpecial;
		final byte[] acceptCustom;
		
		AcceptByte(boolean acceptLetters,boolean acceptNum,boolean acceptSpecial,byte[] acceptCustom){
			this.acceptLetters=acceptLetters;
			this.acceptNum=acceptNum;
			this.acceptSpecial=acceptSpecial;
			this.acceptCustom=acceptCustom;
		}

		//cheat sheet
		//0-31 = non-printing characters & symbols
		//32 = space (always accepted)
		//33-47 = special characters
		//48-57 = numbers
		//58-64 = more special characters
		//65-90 = uppercase letters
		//91-96 = more special characters
		//97-122 = lowercase letters
		//123-126 = yet more special characters
		//127 = delete
		protected boolean acceptByte(byte b){
			if(this.acceptLetters){
				if((b>=65)&&(b<=90)){return(true);}
				if((b>=97)&&(b<=122)){return(true);}
			}
			if(this.acceptNum){
				if((b>=48)&&(b<=57)){return(true);}
			}
			if(this.acceptSpecial){
				if((b>=33)&&(b<=47)){return(true);}
				if((b>=58)&&(b<=64)){return(true);}
				if((b>=123)&&(b<=126)){return(true);}
			}
			if(b==32){return(true);}
			if(this.acceptCustom!=null){
				for(byte ac:this.acceptCustom){
					if(b==ac){return(true);}
				}
			}
			//if((b<32)||(b>126)){return(false);}
			return(false);
		}
	}

}