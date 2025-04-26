/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.huguesjohnson.dubbel.util.Endianness;
import com.huguesjohnson.dubbel.util.NumberFormatters;

public class XMHeader{
	/*
	 * Module ID
	 */
	private String id;
	public void setId(byte[] b){this.id=(new String(b,StandardCharsets.UTF_8));}
	public String getId(){return(this.id);}
	/*
	 * Module name
	 */
	private String name;
	public void setName(byte[] b){this.name=(new String(b,StandardCharsets.UTF_8));}
	public String getName(){return(this.name);}
	/*
	 * Tracker name
	 */
	private String trackerName;
	public void setTrackerName(byte[] b){this.trackerName=(new String(b,StandardCharsets.UTF_8));}
	public String getTrackerName(){return(this.trackerName);}
	/*
	 * Version number, which is really a string here
	 */
	private String versionNumber;
	public void setVersionNumber(byte[] b){
		this.versionNumber=new String();
		//should only be 2 bytes though
		for(byte y:b){
			String s=Byte.toString(y);
			if(s.length()<2){
				this.versionNumber="0"+s+this.versionNumber;
			}else{
				this.versionNumber=s+this.versionNumber;
			}
		}
	}
	public String getVersionNumber(){return(this.versionNumber);}
	/*
	 * Header size
	 */
	private int headerSize;
	public void setHeaderSize(byte[] b){this.headerSize=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getHeaderSize(){return(this.headerSize);}
	/*
	 * Song length
	 */
	private int songLength;
	public void setSongLength(byte[] b){this.songLength=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getSongLength(){return(this.songLength);}
	/*
	 * Restart position
	 */
	private int restartPosition;
	public void setRestartPosition(byte[] b){this.restartPosition=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getRestartPosition(){return(this.restartPosition);}
	/*
	 * Number of channels
	 */
	private int numChannels;
	public void setNumChannels(byte[] b){this.numChannels=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getNumChannels(){return(this.numChannels);}
	/*
	 * Number of patterns
	 */
	private int numPatterns;
	public void setNumPatterns(byte[] b){this.numPatterns=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getNumPatterns(){return(this.numPatterns);}
	/*
	 * Number of instruments
	 */
	private int numInstruments;
	public void setNumInstruments(byte[] b){this.numInstruments=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getNumInstruments(){return(this.numInstruments);}
	/*
	 * Flags
	 */
	private int flags;
	public void setFlags(byte[] b){this.flags=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getFlags(){return(this.flags);}
	/*
	 * Default tempo
	 */
	private int defaultTempo;
	public void setDefaultTempo(byte[] b){this.defaultTempo=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getDefaultTempo(){return(this.defaultTempo);}
	/*
	 * Default BPM
	 */
	private int defaultBPM;
	public void setDefaultBPM(byte[] b){this.defaultBPM=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getDefaultBPM(){return(this.defaultBPM);}
	/*
	 * Pattern order table
	 */
	private byte[] patternOrderTable;
	public void setPatternOrderTable(byte[] b){this.patternOrderTable=Arrays.copyOf(b,b.length);}
	public byte[] getPatternOrderTable(){return(this.patternOrderTable);}
}