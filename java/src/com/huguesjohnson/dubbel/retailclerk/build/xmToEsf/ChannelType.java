/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/* 
 * Based on xm2esf - https://github.com/oerg866/xm2esf
 * © 2010-2015 Eric Voirin alias Oerg866
 * 
 * xm2esf is released under a license very similar the MIT License.
 * It's probably compatible but I'm not a legal expert. 
 * 
 * Initial FreeBASIC -> Java conversion was done using Google Gemini.
 * Although a non-trivial effort was required to make the resulting code actually work and be structured like a Java program.
 * It was really a lot of work just to avoid writing a sound driver, which I will probably eventually do anyway.
 */

package com.huguesjohnson.dubbel.retailclerk.build.xmToEsf;

public enum ChannelType{
	FM(0),
	PSG(1),
	PCM(2),
	NOISE(3);
	
	private final int value;
	ChannelType(int value){this.value=value;}
	public int getValue(){return(this.value);}
	
    @Override
	public String toString(){
    	if(this.value==FM.getValue()){return("FM");}
    	if(this.value==PSG.getValue()){return("PSG");}
    	if(this.value==PCM.getValue()){return("PCM");}
    	if(this.value==NOISE.getValue()){return("Noise");}
    	return("Invalid channel: "+this.value);
    }	
}