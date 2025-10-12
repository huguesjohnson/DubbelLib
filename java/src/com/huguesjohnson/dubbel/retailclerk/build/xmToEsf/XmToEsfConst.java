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

import com.huguesjohnson.dubbel.retailclerk.build.objects.EchoFMNote;

public abstract class XmToEsfConst{
	/* Comments from xm2esf:
	 * The echo stream format definitions use direct YM2612/PSG channel values.
	 * These are not 0-10 but have gaps in them due to the way the chip is constructed.
	 */
	public static int[] ESF_CHANNELS={0,1,2,4,5,6,8,9,10,12,11};
	
	/*
	 * Turns out this wasn't even used in xm2esf.
	 * Perhaps this will come in handy for some future thing.
	 * When/if that days comes this would likely be moved to a different class.
	 */
	public static int[] FM_NOTES={
		EchoFMNote.C.getValue(),
		EchoFMNote.C_SHARP.getValue(),
		EchoFMNote.D.getValue(),
		EchoFMNote.D_SHARP.getValue(),
		EchoFMNote.E.getValue(),
		EchoFMNote.F.getValue(),
		EchoFMNote.F_SHARP.getValue(),
		EchoFMNote.G.getValue(),
		EchoFMNote.G_SHARP.getValue(),
		EchoFMNote.A.getValue(),
		EchoFMNote.A_SHARP.getValue(),
		EchoFMNote.B.getValue()
	};


}