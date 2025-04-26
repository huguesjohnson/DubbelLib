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

public class XMPattern{
	/*
	 * Header size
	 */
	private int headerSize;
	public void setHeaderSize(byte[] b){this.headerSize=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getHeaderSize(){return(this.headerSize);}
	/*
	 * Number of rows
	 */
	private int numRows;
	public void setNumRows(byte[] b){this.numRows=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getNumRows(){return(this.numRows);}
	/*
	 * Pattern data size
	 */
	private int patternDataSize;
	public void setPatternDataSize(byte[] b){this.patternDataSize=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getPatternDataSize(){return(this.patternDataSize);}
	/*
	 * Pattern data
	 */
	private byte[] patternData;
	public void setPatternData(byte[] b){this.patternData=Arrays.copyOf(b,b.length);}
	public byte[] getPatternData(){return(this.patternData);}
}