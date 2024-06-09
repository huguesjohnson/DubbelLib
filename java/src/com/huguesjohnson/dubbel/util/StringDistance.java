/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

public abstract class StringDistance{

	public static int hammingDistance(String s1,String s2) throws Exception{
		int length=s1.length();
		if(length!=s2.length()){
			throw(new Exception("Strings are different lengths. s1:"+length+" s2:"+s2.length()));
		}
		int hd=0;
		for(int i=0;i<length;i++){
			if(s1.charAt(i)!=s2.charAt(i)){
				hd++;
			}
		}
		return(hd);
	}
	
	public static int levenshteinDistance(String s1,String s2){
		int l1=s1.length();
		int l2=s2.length();
		char[] c1=s1.toCharArray();
		char[] c2=s2.toCharArray();
		int[][] distance=new int[l1+1][l2+1];
		//initialize distance array
		for(int i=0;i<(l1+1);i++){distance[i][0]=i;}
		for(int i=0;i<(l2+1);i++){distance[0][i]=i;}
	    for(int i=1;i<(l1+1);i++){
	    	for(int j=1;j<(l2+1);j++){
	    		int cost1=distance[i-1][j]+1;
	            int cost2=distance[i][j-1]+1;
	            int cost3=distance[i-1][j-1];
	            if(c1[i-1]!=c2[j-1]){
	            	cost3+=1;
	            }
	            distance[i][j]=Math.min(Math.min(cost1,cost2),cost3);
	        }
	    }
	    return distance[l1][l2];
	}
}