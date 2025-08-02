/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
 * Based off this documentation:
 * https://www.fileformat.info/format/xm/corion.htm
 */

package com.huguesjohnson.dubbel.audio.xm;

//effectively a struct
public class PatternData{
	public short note=0;
	public short instrument=0;
	public short volumeColumn=0;
	public short effectType=0;
	public short effectParameter=0;
}