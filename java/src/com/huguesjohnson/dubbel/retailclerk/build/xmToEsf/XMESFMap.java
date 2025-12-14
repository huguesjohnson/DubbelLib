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
	
	public String xmFilePath;//the path to the xm file being read
	public String esfOutputPath;//the path to the esf file being created
	public String label;//could also be called track name
	public String description;//optional, to make json more readable
	public ESFType type=ESFType.BGM;//is this background music or a sound effect
	public PSGRetriggerVolumeEnvelope psgRetriggerVolumeEnvelope=PSGRetriggerVolumeEnvelope.NEVER;//I don't know how this works yet
	public boolean loop=DEFAULT_LOOP;//simple whether or not this loops
	public int loopPattern=-1;//if loop==true, this is the pattern index to loop back to
	public int loopRow=-1;//if loop=true, this is the row in the pattern
	public int xmSync=DEFAULT_XMSYNC;//I don't know how this works yet
	public boolean ignoreNoteOffPSG=DEFAULT_NOTE_OFF_PSG;//I don't know how this works yet
	public int tempo=DEFAULT_TEMPO;//or ticks per second
	public EchoNoise noiseType=EchoNoise.NONE;//used for PSG audio with noise
	public int[] fmChannelMap={-1,-1,-1,-1,-1,-1};//map channels in the xm file to console fm channels
	public int[] fmVolume={-1,-1,-1,-1,-1,-1};//volume for each fm channel
	public int[] fmPitch={-1,-1,-1,-1,-1,-1};//pitch for each fm channel
	public int[] psgChannelMap={-1,-1,-1,-1};//map channels in the xm file to console psg channels
	public int[] psgVolume={-1,-1,-1,-1};//volume for each psg channel
	public int[] psgPitch={-1,-1,-1,-1};//pitch for each psg channel
	public int pcmChannel=-1;//map a channel in the xm file to the pcm channel
	public Map<Integer,Integer> instrumentMap=new HashMap<Integer,Integer>();//xm instrument to echo instrument
}