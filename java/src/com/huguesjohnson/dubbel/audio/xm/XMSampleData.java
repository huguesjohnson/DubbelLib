/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

import java.util.Arrays;

public class XMSampleData{
	private byte[] rawData;
	public void setRawData(byte[] b){this.rawData=Arrays.copyOf(b,b.length);}
	public byte[] getRawData(){return(this.rawData);}
	//constructor that accepts the raw data
	public XMSampleData(byte[] b){super(); this.setRawData(b);};
}