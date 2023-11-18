/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

//mostly, maybe exclusively, used for retro save RAM and ROM editing
public abstract class ChecksumUtils{

	//0 start value
	public static int sumBytes(byte[] b){
			return(sumBytes(b,0));
	}
	
	public static int sumBytes(byte[] b,int startValue){
		int sum=startValue;
		for(byte y:b){sum+=y;}
		return(sum);
	}

	public static byte xorBytes(byte[] b){
		byte y=b[0];
		int length=b.length;
		for(int i=1;i<length;i++){
			y=(byte)(y^b[i]);
		}
		return(y);
	}
	
}