/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/* 
 * Based on xm2esf - https://github.com/oerg866/xm2esf
 * © 2010-2015 Eric Voirin alias Oerg866
 * 
 * xm2esf is released under a license very similar the MIT License.
 * It's probably compatible but I'm not an expert. 
 * This is not a port of xm2esf though.
 * This is part of an attempt to build XM -> ESF conversion in Java... 
 *  with a longer-term goal to support other formats beyond XM.
 * Whatever the case, noting this seems like the right thing to do.
 */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.util.HashMap;
import java.util.Map;

public class XMESFMap{
	public enum ESFType{BGM,SFX};
	public enum PSGRetriggerVolumeEnvelope{NEVER,INSTRUMENT_COLUMN,ALWAYS};
	public final static int DEFAULT_TEMPO=7;
	public final static boolean DEFAULT_LOOP=true;
	public final static boolean DEFAULT_NOTE_OFF_PSG=false;
	
	public String xmFilePath;
	public String esfOutputPath;
	public String description;//optional, to make json more readable
	public ESFType type=ESFType.BGM;
	public PSGRetriggerVolumeEnvelope psgRetriggerVolumeEnvelope=PSGRetriggerVolumeEnvelope.NEVER;
	public boolean loop=DEFAULT_LOOP;
	public boolean ignoreNoteOffPSG=DEFAULT_NOTE_OFF_PSG;
	public int tempo=DEFAULT_TEMPO;
	public int[] fmChannelMap={-1,-1,-1,-1,-1,-1};
	public int[] fmVolume={-1,-1,-1,-1,-1,-1};
	public int[] fmFrequency={-1,-1,-1,-1,-1,-1};
	public int[] psgChannelMap={-1,-1,-1,-1};
	public int[] psgVolume={-1,-1,-1,-1};
	public int[] psgFrequency={-1,-1,-1,-1};
	public int pcmChannelMap=-1;
	public Map<Integer,Integer> instrumentMap=new HashMap<Integer,Integer>();
}