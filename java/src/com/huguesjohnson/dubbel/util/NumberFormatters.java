/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.util.StringTokenizer;

//I could have called this NumberConvertors but whatever
public abstract class NumberFormatters{
    /* (2^8) - java only defines a max signed byte */
    public final static int MAX_UNSIGNED_BYTE=256;
	
	//I heard a rumor jdk17 might have a built-in feature like this
	public final static String toHexWord(int i){
		StringBuilder hexWord=new StringBuilder(Integer.toHexString(i));
		while(hexWord.length()<4){
			hexWord.insert(0,"0");
		}
		hexWord.insert(0,"$");
		return(hexWord.toString().toUpperCase());
	}
	
	/*
	 * Everything below here was created for some iteration of Hapsby
	 * (https://huguesjohnson.com/hapsby.html)
	 * I started that project as an attempt to learn Java and therefore this code is likely ugly & inefficient
	 * */
	
    public static String decimalStringToAsciiString(String decimalString){
         String asciiString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(decimalString);
         while(tokenizer.hasMoreTokens()){
              try{     
                   byte b=Byte.valueOf(tokenizer.nextToken()).byteValue();
                   asciiString+=(char)b+" ";
              } catch(NumberFormatException nfe){
                   asciiString+=" ";
              }   
         }
         return(asciiString);
    }     

    public static String decimalStringToHexString(String decimalString){
         String hexString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(decimalString);
         while(tokenizer.hasMoreTokens()){
              try{
                   Long l=Long.valueOf(tokenizer.nextToken());
                   hexString+=Long.toHexString(l.longValue())+" ";
              } catch(NumberFormatException nfe){
                   hexString+=" ";
              }
         }
         return(hexString);
    }
    
    public static String decimalStringToOctalString(String decimalString){
         String octalString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(decimalString);
         while(tokenizer.hasMoreTokens()){
              try{
                   Long l=Long.valueOf(tokenizer.nextToken());
                   octalString+=Long.toOctalString(l.longValue())+" ";
              } catch(NumberFormatException nfe){
                   octalString+=" ";
              }
         }
         return(octalString);
    }     
    
    public static String decimalStringToBinaryString(String decimalString){
         String binaryString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(decimalString);
         while(tokenizer.hasMoreTokens()){
              try{
                   Long l=Long.valueOf(tokenizer.nextToken());
                   binaryString+=Long.toBinaryString(l.longValue())+" ";
              } catch(NumberFormatException nfe){
                   binaryString+=" ";
              }
         }
         return(binaryString);
    }

    public static String asciiStringToDecimalString(String asciiString){
         String decimalString=new String("");
         byte[] b=asciiString.getBytes();
         for(int index=0;index<b.length;index++){
              decimalString+=b[index]+" ";
         }
         return(decimalString);
    }
    
    public static String hexStringToDecimalString(String hexString){
         String decimalString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(hexString);
         while(tokenizer.hasMoreTokens()){
              try{
                   /* this next line looks confusing, but here's what going on:
                   1) add "0x" to the current token
                   2) use the Long decode method to convert the string to a long decimal
                   3) convert the long value to a String
                   4) append a blank space to the end
                   */
                   decimalString+=Long.toString(Long.decode("0x"+tokenizer.nextToken()).longValue())+" ";
              } catch(NumberFormatException nfe){
                   decimalString+=" ";
              }
         }
         return(decimalString);
    }
    
    public static String octalStringToDecimalString(String octalString){
         String decimalString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(octalString);
         while(tokenizer.hasMoreTokens()){
              char ch[]=tokenizer.nextToken().toCharArray();
              long decimalValue=0;
              for(int index=0;index<ch.length;index++){
                   long n;
                   try{
                        n=Long.valueOf(String.valueOf(ch[index])).longValue();
                        if(n>8L||n<0L){
                             throw(new NumberFormatException());
                        }
                   } catch(NumberFormatException nfe){
                        n=0L;
                   }
                   decimalValue+=n*(Math.pow(8.0D,(double)(ch.length-index-1)));
              }
              decimalString+=Long.toString(decimalValue)+" ";
         }
         return(decimalString);
    }
    
    public static String binaryStringToDecimalString(String binaryString){
         String decimalString=new String("");
         StringTokenizer tokenizer=new StringTokenizer(binaryString);
         while(tokenizer.hasMoreTokens()){
              char ch[]=tokenizer.nextToken().toCharArray();
              long decimalValue=0;
              for(int index=0;index<ch.length;index++){
                   long n;
                   try{
                        n=Long.valueOf(String.valueOf(ch[index])).longValue();
                        if(n>1L||n<0L){
                             throw(new NumberFormatException());
                        } else{
                             decimalValue+=n*(Math.pow(2.0D,(double)(ch.length-index-1)));
                        }
                   } catch(NumberFormatException nfe){
                        decimalString+=" ";
                   }
              }
              decimalString+=Long.toString(decimalValue)+" ";
         }
         return(decimalString);
    }
    
    public static String byteArrayToString(byte[] b){
    	if((b==null)||(b.length)<1){return("");}
    	StringBuilder sb=new StringBuilder();
		int length=b.length;
		for(int i=0;i<length;i++){
			sb.append("[0x");
			sb.append(Integer.toHexString(b[i]&0xFF).toUpperCase());
			sb.append("] ");
		}
    	return(sb.toString());
    }
    
    public static int byteArrayToInt(byte[] b,Endianness byteOrder){
    	return(byteArrayToInt(b,0,b.length,byteOrder));
    }
    
    public static int byteArrayToInt(byte[] b,Endianness byteOrder,boolean unsigned){
    	return(byteArrayToInt(b,0,b.length,byteOrder,unsigned));
    }    

    public static int byteArrayToInt(byte[] b,int startOffset,int length,Endianness byteOrder){
    	return(byteArrayToInt(b,startOffset,length,byteOrder,true));
    }
    
    public static int byteArrayToInt(byte[] b,int startOffset,int length,Endianness byteOrder,boolean unsigned){
    	int value=0;
    	for(int i=0;i<length;i++){
    		int bi=b[i+startOffset];
    		if(unsigned){bi=bi&0xFF;}
    		if(byteOrder==Endianness.LITTLE_ENDIAN){
    			value+=bi*(Math.pow(MAX_UNSIGNED_BYTE,i));
    		}else if(byteOrder==Endianness.BIG_ENDIAN){
    			value+=bi*(Math.pow(MAX_UNSIGNED_BYTE,(length-i-1)));
    		}
    	}
    	return(value);
    }
    
    public static String intToHex(int i){
    	return("0x"+Integer.toHexString(i).toUpperCase());
    }
}