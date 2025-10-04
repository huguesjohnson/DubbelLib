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
		PatternData[][] unpacked=new PatternData[this.numRows][numChannels];
		int currentRow=0;
		int currentChannel=0;
		int packedDataIndex=0;
		while((currentRow<this.numRows)&&(packedDataIndex<this.packedDataSize)){
			PatternData pd=new PatternData();
			byte firstByte=this.packedData[packedDataIndex];
			if((firstByte&XMConstants.PackingBits.PACKING_BIT_PRESENT)!=0){//test packing bit
				if((firstByte&XMConstants.PackingBits.PACKING_BIT_NOTE)!=0){
					packedDataIndex++;
					pd.note=(short)(this.packedData[packedDataIndex]&0xFF);
				}
				if((firstByte&XMConstants.PackingBits.PACKING_BIT_INSTRUMENT)!=0){
					packedDataIndex++;
					pd.instrument=(short)(this.packedData[packedDataIndex]&0xFF);
				}
				if((firstByte&XMConstants.PackingBits.PACKING_BIT_VOLUME)!=0){
					packedDataIndex++;
					pd.volumeColumn=(short)(this.packedData[packedDataIndex]&0xFF);
				}
				if((firstByte&XMConstants.PackingBits.PACKING_BIT_EFFECT_TYPE)!=0){
					packedDataIndex++;
					pd.effectType=(short)(this.packedData[packedDataIndex]&0xFF);
				}
				if((firstByte&XMConstants.PackingBits.PACKING_BIT_EFFECT_PARAMETER)!=0){
					packedDataIndex++;
					pd.effectParameter=(short)(this.packedData[packedDataIndex]&0xFF);
				}
				packedDataIndex++;
			}else{//((firstByte&XMConstants.PackingBits.PACKING_BIT_PRESENT)==0) -> data is not packed
				pd.note=(short)(this.packedData[packedDataIndex]&0xFF);
				packedDataIndex++;
				pd.instrument=(short)(this.packedData[packedDataIndex]&0xFF);
				packedDataIndex++;
				pd.volumeColumn=(short)(this.packedData[packedDataIndex]&0xFF);
				packedDataIndex++;
				pd.effectType=(short)(this.packedData[packedDataIndex]&0xFF);
				packedDataIndex++;
				pd.effectParameter=(short)(this.packedData[packedDataIndex]&0xFF);
				packedDataIndex++;
			}
			//update return array
			unpacked[currentRow][currentChannel]=pd;
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