/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayUtil{
	
	/*
	 * Find the element in an array of doubles with the highest value.
	 */
    public static int maxElementIndex(double[] d){
    	int maxIndex=0;
    	double maxValue=Double.MIN_VALUE;
    	for(int i=0;i<d.length;i++){
    		if(d[i]>maxValue){
    			maxIndex=i;
    			maxValue=d[i];
    		}
    	}
    	return(maxIndex);
    }
	
	/*
	 * Fill a Character array list with all the elements in a character array.
	 */
    public static void fillArrayList(ArrayList<Character> list,char[] chars){
    	for(int i=0;i<chars.length;i++){
    		list.add(chars[i]);
    	}
    }  
    
    /*
     * Used to pick indexes in arrays, created for NARPassword... might have other uses.
     * Throws an exception if either number is negative.
     */
    public static int pickIndex(int i,int max) throws Exception{
    	if(i<0){throw(new Exception("i is negative ["+i+"]"));};
    	if(max<0){throw(new Exception("max is negative ["+max+"]"));};
        if(i>=max){
            i=i-((int)Math.floor(i/max)*max);
        }
        return(i);
    }    
    
    /*
     * Also created for NARPassword with a small possibility of being otherwise useful.
     */
    public static char pickChar(ArrayList<Character> list,int index,boolean remove,char[] refill){
    	if(list.size()==0){
        	fillArrayList(list,refill);
    	}
    	try{
            index=pickIndex(index,list.size());
    	}catch(Exception x){
            index=0;
    	}
        char c=list.get(index);
        if(remove){
        	list.remove(index);
        }
        return(c);
    }
    
    /*
     * xors all the bytes in two arrays of the same size.
     * Throws exception if the arrarys are not the same size.
     */
    public static byte[] xorBytes(byte[] a,byte[] b) throws Exception{
    	int l=a.length;
    	if(l!=b.length){throw(new Exception("Arrays are different lengths. a.length="+l+"b.length="+b.length));};
		byte[] xor=new byte[l];
		for(int i=0;i<l;i++){
			xor[i]=(byte)((int)a[i]^(int)b[i]);
		}
		return(xor);
    }    
    
    /*
     * returns duplicate entries in two lists
     * assumes both lists are sorted, if they are not this will fail
     * this is case sensitive but is trivial to add a parameter to ignore case
     */
    public static ArrayList<String> findDuplicateRows(List<String> list1,List<String> list2){
    	ArrayList<String> duplicates=new ArrayList<String>();
    	int size1=list1.size();
    	int size2=list2.size();
    	int index1=0;
    	int index2=0;
    	while((index1<size1)&&(index2<size2)){
    		String item1=list1.get(index1);
    		String item2=list2.get(index2);
    		int compare=item1.compareTo(item2);
    		if(compare==0){
    			duplicates.add(item1);
    			index1++;
    			index2++;
    		}else if(compare<0){
    			index1++;
    		}else{//compare>0
    			index2++;
    		}
    	}
    	return(duplicates);
    }
    
}