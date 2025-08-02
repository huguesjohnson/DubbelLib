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
	 * Packing type 
	 */
	private byte packingType;
	public void setPackingType(byte b){this.packingType=b;}
	public int getPackingType(){return(this.packingType);}
	/*
	 * Number of rows
	 */
	private int numRows;
	public void setNumRows(byte[] b){this.numRows=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getNumRows(){return(this.numRows);}
	/*
	 * Packed data size
	 */
	private int packedDataSize;
	public void setPackedDataSize(byte[] b){this.packedDataSize=NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN);}
	public int getPackedDataSize(){return(this.packedDataSize);}
	/*
	 * Packed data
	 */
	private byte[] packedData;
	public void setPackedData(byte[] b){this.packedData=Arrays.copyOf(b,b.length);}
	public byte[] getPackedData(){return(this.packedData);}
	/*
	 * Unpack data
	 */
	public PatternData[][] unpack(int numChannels){
		PatternData[][] unpacked=new PatternData[numChannels][this.numRows];
		int currentRow=0;
		int currentChannel=0;
		int updIndex=0;
		while((currentRow<this.numRows)&&(updIndex<this.packedDataSize)){
			PatternData pd=new PatternData();
			byte firstByte=this.packedData[updIndex];
			if((firstByte&0x80)!=0){//test packing bit
				if((firstByte&0x01)!=0){
					updIndex++;
					pd.note=this.packedData[updIndex];
				}
				if((firstByte&0x02)!=0){
					updIndex++;
					pd.instrument=this.packedData[updIndex];
				}
				if((firstByte&0x04)!=0){
					updIndex++;
					pd.volumeColumn=this.packedData[updIndex];
				}
				if((firstByte&0x08)!=0){
					updIndex++;
					pd.effectType=this.packedData[updIndex];
				}
				if((firstByte&0x10)!=0){
					updIndex++;
					pd.effectParameter=this.packedData[updIndex];
				}
			}else{//((firstByte&0x80)==0)
				pd.note=this.packedData[updIndex];
				updIndex++;
				pd.instrument=this.packedData[updIndex];
				updIndex++;
				pd.volumeColumn=this.packedData[updIndex];
				updIndex++;
				pd.effectType=this.packedData[updIndex];
				updIndex++;
				pd.effectParameter=this.packedData[updIndex];
			}
			//update return array
			unpacked[currentChannel][currentRow]=pd;
			//advance to next channel or row
			currentChannel++;
	        if(currentChannel>=numChannels){
	        	currentChannel=0;
	        	currentRow++;
	        }
        }//while loop
		return(unpacked);
	}
}