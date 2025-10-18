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

import java.util.HashMap;
import java.util.Map;

import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.EchoNoise;

public class XMESFMap{
	public enum ESFType{BGM,SFX};
	public enum PSGRetriggerVolumeEnvelope{NEVER,INSTRUMENT_COLUMN,ALWAYS};
	public final static int DEFAULT_TEMPO=7;
	public final static boolean DEFAULT_LOOP=true;
	public final static int DEFAULT_XMSYNC=-1;
	public final static boolean DEFAULT_NOTE_OFF_PSG=false;
	
	public String xmFilePath;
	public String esfOutputPath;
	public String description;//optional, to make json more readable
	public ESFType type=ESFType.BGM;
	public PSGRetriggerVolumeEnvelope psgRetriggerVolumeEnvelope=PSGRetriggerVolumeEnvelope.NEVER;
	public boolean loop=DEFAULT_LOOP;
	public int loopPattern=-1;
	public int loopRow=-1;
	public int xmSync=DEFAULT_XMSYNC;
	public boolean ignoreNoteOffPSG=DEFAULT_NOTE_OFF_PSG;
	public int tempo=DEFAULT_TEMPO;
	public EchoNoise noiseType=EchoNoise.NONE;
	public int[] fmChannelMap={-1,-1,-1,-1,-1,-1};
	public int[] fmVolume={-1,-1,-1,-1,-1,-1};
	public int[] fmPitch={-1,-1,-1,-1,-1,-1};
	public int[] psgChannelMap={-1,-1,-1,-1};
	public int[] psgVolume={-1,-1,-1,-1};
	public int[] psgPitch={-1,-1,-1,-1};
	public int pcmChannel=-1;
	public Map<Integer,Integer> instrumentMap=new HashMap<Integer,Integer>();
}