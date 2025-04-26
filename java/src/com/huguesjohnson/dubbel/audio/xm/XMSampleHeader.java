/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

import java.nio.charset.StandardCharsets;

import com.huguesjohnson.dubbel.util.Endianness;
import com.huguesjohnson.dubbel.util.NumberFormatters;

public class XMSampleHeader{
	/*
	 * Sample length
	 */
	private int sampleLength;
	public void setSampleLength(byte[] b){this.sampleLength=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getSampleLength(){return(this.sampleLength);}
	/*
	 * Loop start
	 */
	private int sampleLoopStart;
	public void setSampleLoopStart(byte[] b){this.sampleLoopStart=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getSampleLoopStart(){return(this.sampleLoopStart);}
	/*
	 * Loop length
	 */
	private int sampleLoopLength;
	public void setSampleLoopLength(byte[] b){this.sampleLoopLength=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getSampleLoopLength(){return(this.sampleLoopLength);}
	/*
	 * Volume
	 */
	private byte volume;
	public void setVolume(byte b){this.volume=b;}
	public byte getVolume(){return(this.volume);}
	/*
	 * Finetune
	 */
	private byte finetune;
	public void setFinetune(byte b){this.finetune=b;}
	public byte getFinetune(){return(this.finetune);}
	/*
	 * Type
	 */
	private byte type;
	public void setType(byte b){this.type=b;}
	public byte getType(){return(this.type);}
	/*
	 * Panning
	 */
	private byte panning;
	public void setPanning(byte b){this.panning=b;}
	public byte getPanning(){return(this.panning);}
	/*
	 * Relative note number
	 */
	private byte relativeNoteNumber;
	public void setRelativeNoteNumber(byte b){this.relativeNoteNumber=b;}
	public byte getRelativeNoteNumber(){return(this.relativeNoteNumber);}
	/*
	 * Packing
	 */
	private byte packing;
	public void setPacking(byte b){this.packing=b;}
	public byte getPacking(){return(this.packing);}
	/*
	 * Name
	 */
	private String name;
	public void setName(byte[] b){this.name=(new String(b,StandardCharsets.UTF_8));}
	public String getName(){return(this.name);}
}