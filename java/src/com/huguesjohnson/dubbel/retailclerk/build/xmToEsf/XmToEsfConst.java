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

public abstract class XmToEsfConst{
	/* Comments from xm2esf:
	 * The echo stream format definitions use direct YM2612/PSG channel values.
	 * These are not 0-10 but have gaps in them due to the way the chip is constructed.
	 */
	public static int[] ESF_CHANNELS={0,1,2,4,5,6,8,9,10,12,11};
	
	/*
	 * Echo has 11 channels defined.
	 * This is used to map a channel to which type (FM, PSG, PCM, noise) it is. 
	 */
	public static ChannelType[] channelType={
		ChannelType.FM,//0
		ChannelType.FM,//1
		ChannelType.FM,//2
		ChannelType.FM,//3
		ChannelType.FM,//4
		ChannelType.FM,//5
		ChannelType.PSG,//6
		ChannelType.PSG,//7
		ChannelType.PSG,//8
		ChannelType.PCM,//9
		ChannelType.NOISE//10
	};
	
	public static int DEFAULT_VOLUME=64;
}