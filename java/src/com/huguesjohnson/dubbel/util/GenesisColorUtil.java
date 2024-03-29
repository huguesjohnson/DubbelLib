/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.awt.Color;
import java.util.ArrayList;

public abstract class GenesisColorUtil{
	
	public static String hexStringToGenesisRgb(String hexString){
		int i=Integer.parseInt(hexString,16);
		i=i/32;
		String b=Integer.toBinaryString(i);
		if(b.length()>3){b=b.substring(0,3);return(b);}
		if(b.length()==2){b="0"+b;return(b);}
		if(b.length()==1){b="00"+b;return(b);}
		if(b.length()==0){return("000");}
		return(b);
	}
	
	public static String rgbStringToGenesisRgbString(String rgb){
		StringBuilder grgb=new StringBuilder();
		//ffrrggbb
		//01234567
		grgb.append("0000");
		//BBB
		grgb.append(hexStringToGenesisRgb(rgb.substring(6,8)));
		grgb.append("0");
		//GGG
		grgb.append(hexStringToGenesisRgb(rgb.substring(4,6)));
		grgb.append("0");
		//RRR
		grgb.append(hexStringToGenesisRgb(rgb.substring(2,4)));
		grgb.append("0");
		return(grgb.toString());
	}	
	
	public static Color genesisRgbStringToColor(String genRgb){
		int start=genRgb.indexOf("%0000")+5;
		String bStr=genRgb.substring(start,start+3);
		String gStr=genRgb.substring(start+4,start+7);
		String rStr=genRgb.substring(start+8,start+11);
		int b=Integer.parseInt(bStr,2)<<5;
		int g=Integer.parseInt(gStr,2)<<5;
		int r=Integer.parseInt(rStr,2)<<5;
		return(new Color(r,g,b));
	}
	
	public static String genesisRgbStringToHexString(String genRgb){
		Color c=genesisRgbStringToColor(genRgb);
		return(Integer.toHexString(c.getRGB()));
	}
	
	public static int findNearestColor(ArrayList<String> colorList,String color){
		int index=colorList.indexOf(color);
		if(index>-1){return(index);}
		int nearestColorIndex=0; //default to zero
		double nearestColorDistance=Math.sqrt((255^2)*3)+1D;
		for(int i=0;i<colorList.size();i++){
			double distance=colorDistance(colorList.get(i),color);
			if(distance==0D){return(i);} //this probably can't happen
			if(distance<nearestColorDistance){
				nearestColorIndex=i;
				nearestColorDistance=distance;
			}
		}
		return(nearestColorIndex);
	}
	
	//assumes both colors are hex strings like ff0e0e0e
	public static double colorDistance(String color1,String color2){
		int r1=Integer.parseInt(color1.substring(2,4),16);
		int g1=Integer.parseInt(color1.substring(4,6),16);
		int b1=Integer.parseInt(color1.substring(6,8),16);
		int r2=Integer.parseInt(color2.substring(2,4),16);
		int g2=Integer.parseInt(color2.substring(4,6),16);
		int b2=Integer.parseInt(color2.substring(6,8),16);
		int rd=Math.abs((r1-r2))^2;
		int gd=Math.abs((g1-g2))^2;
		int bd=Math.abs((b1-b2))^2;
		return(Math.sqrt(rd+gd+bd));
	}
	
}