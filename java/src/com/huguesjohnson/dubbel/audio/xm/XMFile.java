/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off 'The "Complete" XM module format specification v0.81' by by Matti "ccr" Hamalainen
 * Copy of the file is here: 
 *   https://github.com/milkytracker/MilkyTracker/blob/master/resources/reference/xm-form.txt
 * Although there are many other locations that host it
 */

package com.huguesjohnson.dubbel.audio.xm;

import java.util.List;

public class XMFile{
	/*
	 * The header - track and song info
	 */
	private XMHeader header;
	public void setHeader(XMHeader header){this.header=header;}
	public XMHeader getHeader(){return(this.header);}
	/*
	 * The patterns
	 */	
	private List<XMPattern> patterns;
	public void setPatterns(List<XMPattern> patterns){this.patterns=patterns;}
	public List<XMPattern> getPatterns(){return(this.patterns);}
	/*
	 * The Instruments (which includes samples)
	 */	
	private List<XMInstrument> instruments;
	public void setInstruments(List<XMInstrument> instruments){this.instruments=instruments;}
	public List<XMInstrument> getInstruments(){return(this.instruments);}	
}