/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

import java.util.Arrays;

import com.huguesjohnson.dubbel.util.Endianness;
import com.huguesjohnson.dubbel.util.NumberFormatters;

public class XMInstrumentHeader{
	/*
	 * Header size
	 */
	private int headerSize;
	public void setHeaderSize(byte[] b){this.headerSize=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getHeaderSize(){return(this.headerSize);}
	/*
	 * "Sample number for all notes" or "sample keymap assignments" in other documentation
	 * As I understand this maps samples to notes... there are 96 notes in the .xm virtual keyboard at least
	 * I might rename it to something that is easier for me to understand
	 */
	private byte[] noteSampleNumbers;
	public void setNoteSampleNumbers(byte[] b){this.noteSampleNumbers=Arrays.copyOf(b,b.length);}
	public byte[] getNoteSampleNumbers(){return(this.noteSampleNumbers);}
	/*
	 * Points for volume envelope
	 */
	private byte[] envelopeVolumePoints;
	public void setEnvelopeVolumePoints(byte[] b){this.envelopeVolumePoints=Arrays.copyOf(b,b.length);}
	public byte[] getEnvelopeVolumePoints(){return(this.envelopeVolumePoints);}
	/*
	 * Points for panning envelope
	 */
	private byte[] envelopePanningPoints;
	public void setEnvelopePanningPoints(byte[] b){this.envelopePanningPoints=Arrays.copyOf(b,b.length);}
	public byte[] getEnvelopePanningPoints(){return(this.envelopePanningPoints);}
	/*
	 * Number of volume points
	 */
	private byte numVolumePoints;
	public void setNumVolumePoints(byte b){this.numVolumePoints=b;}
	public byte getNumVolumePoints(){return(this.numVolumePoints);}
	/*
	 * Number of panning points
	 */
	private byte numPanningPoints;
	public void setNumPanningPoints(byte b){this.numPanningPoints=b;}
	public byte getNumPanningPoints(){return(this.numPanningPoints);}
	/*
	 * Volume sustain point
	 */
	private byte volumeSustainPoint;
	public void setVolumeSustainPoint(byte b){this.volumeSustainPoint=b;}
	public byte getVolumeSustainPoint(){return(this.volumeSustainPoint);}
	/*
	 * Volume loop start point
	 */
	private byte volumeLoopStartPoint;
	public void setVolumeLoopStartPoint(byte b){this.volumeLoopStartPoint=b;}
	public byte getVolumeLoopStartPoint(){return(this.volumeLoopStartPoint);}
	/*
	 * Volume loop end point
	 */
	private byte volumeLoopEndPoint;
	public void setVolumeLoopEndPoint(byte b){this.volumeLoopEndPoint=b;}
	public byte getVolumeLoopEndPoint(){return(this.volumeLoopEndPoint);}
	/*
	 * Panning sustain point
	 */
	private byte panningSustainPoint;
	public void setPanningSustainPoint(byte b){this.panningSustainPoint=b;}
	public byte getPanningSustainPoint(){return(this.panningSustainPoint);}
	/*
	 * Panning loop start point
	 */
	private byte panningLoopStartPoint;
	public void setPanningLoopStartPoint(byte b){this.panningLoopStartPoint=b;}
	public byte getPanningLoopStartPoint(){return(this.panningLoopStartPoint);}
	/*
	 * Panning loop end point
	 */
	private byte panningLoopEndPoint;
	public void setPanningLoopEndPoint(byte b){this.panningLoopEndPoint=b;}
	public byte getPanningLoopEndPoint(){return(this.panningLoopEndPoint);}
	/*
	 * Volume type
	 */
	private byte volumeType;
	public void setVolumeType(byte b){this.volumeType=b;}
	public byte getVolumeType(){return(this.volumeType);}
	/*
	 * Panning type
	 */
	private byte panningType;
	public void setPanningType(byte b){this.panningType=b;}
	public byte getPanningType(){return(this.panningType);}
	/*
	 * Vibrato type
	 */
	private byte vibratoType;
	public void setVibratoType(byte b){this.vibratoType=b;}
	public byte getVibratoType(){return(this.vibratoType);}
	/*
	 * Vibrato sweep
	 */
	private byte vibratoSweep;
	public void setVibratoSweep(byte b){this.vibratoSweep=b;}
	public byte getVibratoSweep(){return(this.vibratoSweep);}
	/*
	 * Vibrato depth
	 */
	private byte vibratoDepth;
	public void setVibratoDepth(byte b){this.vibratoDepth=b;}
	public byte getVibratoDepth(){return(this.vibratoDepth);}
	/*
	 * Vibrato rate
	 */
	private byte vibratoRate;
	public void setVibratoRate(byte b){this.vibratoRate=b;}
	public byte getVibratoRate(){return(this.vibratoRate);}
	/*
	 * Volume fadeout
	 */
	private int volumeFadeout;
	public void setVolumeFadeout(byte[] b){this.volumeFadeout=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getVolumeFadeout(){return(this.volumeFadeout);}
	/*
	 * Reserved block
	 */
	private byte[] reserved;
	public void setReserved(byte[] b){this.reserved=Arrays.copyOf(b,b.length);}
	public byte[] getReserved(){return(this.reserved);}

}