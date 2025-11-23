/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.huguesjohnson.dubbel.util.Endianness;
import com.huguesjohnson.dubbel.util.NumberFormatters;

public class XMInstrument{
	/*
	 * Instrument header size
	 */
	private int headerSize=0;
	public void setHeaderSize(byte[] b){this.headerSize=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getHeaderSize(){return(this.headerSize);}
	/*
	 * Instrument name
	 */
	private String name;
	public void setName(byte[] b){this.name=(new String(b,StandardCharsets.UTF_8));}
	public String getName(){return(this.name);}
	/*
	 * Instrument type
	 */
	private byte type=0;
	public void setType(byte b){this.type=b;}
	public byte getType(){return(this.type);}
	/*
	 * Number of samples
	 */
	private int numSamples=0;
	public void setNumSamples(byte[] b){this.numSamples=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getNumSamples(){return(this.numSamples);}
	/*
	 * Instrument headers
	 */
	private XMInstrumentHeader instrumentHeader;
	public void setInstrumentHeader(XMInstrumentHeader instrumentHeader){this.instrumentHeader=instrumentHeader;}
	public XMInstrumentHeader getInstrumentHeader(){return(this.instrumentHeader);}	
	/*
	 * Sample headers
	 */
	private List<XMSampleHeader> sampleHeaders;
	public void setSampleHeaders(List<XMSampleHeader> sampleHeaders){this.sampleHeaders=sampleHeaders;}
	public List<XMSampleHeader> getSampleHeaders(){return(this.sampleHeaders);}	
	/*
	 * Sample data
	 */
	private List<XMSampleData> sampleData;
	public void setSampleData(List<XMSampleData> sampleData){this.sampleData=sampleData;}
	public List<XMSampleData> getSampleData(){return(this.sampleData);}	
}