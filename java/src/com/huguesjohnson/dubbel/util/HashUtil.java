/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil{
	public final static String MDNAME_SHA3512="SHA3-512";
	public final static String MDNAME_MD5="MD5";

	public static String computeHash(String input,String mdName) throws NoSuchAlgorithmException{
    	MessageDigest md=MessageDigest.getInstance(mdName);
        byte[] data=md.digest(input.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<data.length;i++){
        	int di=data[i]&0xff;
        	if(di<16){sb.append("0");} 
        	sb.append(Integer.toHexString(di));
        }
        return(sb.toString());
	}
	
    public static String computeHashSHA3512(String input) throws NoSuchAlgorithmException{
    	return computeHash(input,MDNAME_SHA3512);
    }
    
    public static String computeHashMD5(String input) throws NoSuchAlgorithmException{
    	return computeHash(input,MDNAME_MD5);
    }
}