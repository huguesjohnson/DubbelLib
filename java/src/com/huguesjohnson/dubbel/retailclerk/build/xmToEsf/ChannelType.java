/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/* 
 * Based on xm2esf - https://github.com/oerg866/xm2esf
 * © 2010-2015 Eric Voirin alias Oerg866
 * 
 * xm2esf is released under a license similar to the MIT License but with restrictions on commercial use.
 * In the extraordinarily unlikely chance someone wants to use this conversion in a commercial project... have fun figuring that out.
 * 
 * Initial FreeBASIC -> Java conversion was done using Google Gemini.
 * I wonder if that also makes commercial use interesting from a license standpoint?
 * A non-trivial effort was required to make the resulting code actually work and be structured like a Java program.
 * I'm not positive if Gemini saved any time vs converting manually or building from scratch based on the ESF documentation.
 * Gemini initially thought the FreeBASIC code was trying to parse .xml if that's an indicator of how well it works.
 * It was good at explaining how math functions differ between FreeBASIC and Java at least.
 * 
 * This was all done to avoid writing a sound driver, which I will probably eventually do anyway.
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