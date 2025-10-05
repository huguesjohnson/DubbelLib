/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.audio.xm.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.audio.xm.PatternData;
import com.huguesjohnson.dubbel.audio.xm.XMConstants;
import com.huguesjohnson.dubbel.audio.xm.XMPattern;

public class TestXMPattern{

	/*
	 * This tests 2 rows of a 64 row pattern.
	 * The data for all 64 rows is present.
	 * This data contains both packed and unpacked data.
	 * If I run into new problems with my implementation of unpack() then I will include the remaining rows in testing.
	 * Unless I run into a problem with a different pattern then I will add that data here.
	 */
	@Test
	public void test_unpack(){
		try{
			XMPattern pattern=new XMPattern();
			//setup pattern
			byte[] headerSize={9};
			pattern.setHeaderSize(headerSize);
			assertEquals(9,pattern.getHeaderSize());
			byte[] rowCount={64};
			pattern.setNumRows(rowCount);
			int numRows=pattern.getNumRows();
			assertEquals(64,numRows);
			byte packingType=0;
			pattern.setPackingType(packingType);
			assertEquals(0,pattern.getPackingType());
			byte[] packedDataSize={(byte)158,6};
			pattern.setPackedDataSize(packedDataSize);
			assertEquals(1694,pattern.getPackedDataSize());
			byte[] packedData={ 
					-125, 49, 3, //row 0 - channel 0
					-125, 49, 2, //row 0 - channel 1
					-125, 37, 1, //row 0 - channel 2
					-125, 49, 6, //row 0 - channel 3
					-121, 61, 5, 64, //row 0 - channel 4
					-121, 61, 5, 32, //row 0 - channel 5 
					-101, 49, 11, 2, 32, //row 0 - channel 6
					73, 12, 80, 14, -62, //row 0 - channel 7 (unpacked)
					-125, 49, 4, //row 0 - channel 8 
					-127, 97, //row 0 - channel 9
					-125, 49, 3, //row 1 - channel 0
					-128, //row 1 - channel 1
					-127, 97, //row 1 - channel 2
					-127, 97, //row 1 - channel 3 
					-121, 61, 5, 48, //row 1 - channel 4
					-121, 61, 5, 48, //row 1 - channel 5
					-104, 2, 32, //row 1 - channel 6
					-124, 16, //row 1 - channel 7
					-128, //row 1 - channel 8
					-128, //row 1 - channel 9
					//rows 2 - 63 follow
					-125, 53, 3, -128, -125, 48, 1, -125, 48, 6, -121, 61, 5, 64, -121, 61, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 53, 3, -128, -127, 97, -127, 97, -121, 61, 5, 48, -121, 61, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 56, 3, -125, 49, 2, -125, 37, 1, -125, 53, 6, -121, 65, 5, 64, -121, 61, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 56, 3, -128, -127, 97, -127, 97, -121, 65, 5, 48, -121, 61, 5, 48, -128, -124, 16, -128, -128, -125, 60, 3, -125, 49, 2, -125, 48, 1, -125, 48, 6, -121, 65, 5, 64, -121, 65, 5, 32, -128, 73, 12, 80, 14, -62, -128, -128, -125, 60, 3, -128, -127, 97, -127, 97, -121, 65, 5, 48, -121, 65, 5, 48, -128, -124, 16, -128, -128, -125, 61, 3, -125, 49, 2, -125, 44, 1, -125, 53, 6, -121, 68, 5, 64, -121, 65, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -127, 97, -125, 53, 4, -125, 61, 3, -128, -127, 97, -127, 97, -121, 68, 5, 48, -121, 65, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 65, 3, -128, -125, 41, 1, -125, 56, 6, -121, 68, 5, 64, -121, 68, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 65, 3, -128, -127, 97, -127, 97, -121, 68, 5, 48, -121, 68, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 68, 3, -128, -125, 37, 1, -125, 53, 6, -121, 72, 5, 64, -121, 68, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 68, 3, -128, -127, 97, -127, 97, -121, 72, 5, 48, -121, 68, 5, 48, -128, -124, 16, -128, -128, -125, 72, 3, -125, 49, 2, -125, 36, 1, -125, 48, 6, -121, 72, 5, 64, -121, 72, 5, 32, -128, 73, 12, 80, 14, -62, -128, -128, -125, 72, 3, -125, 49, 2, -127, 97, -127, 97, -121, 72, 5, 48, -121, 72, 5, 48, -128, -124, 16, -128, -128, -125, 68, 3, -125, 49, 2, -125, 37, 1, -125, 49, 6, -121, 73, 5, 64, -121, 72, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -125, 56, 4, -127, 97, -125, 68, 3, -128, -127, 97, -127, 97, -121, 73, 5, 48, -121, 72, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 65, 3, -128, -125, 41, 1, -125, 56, 6, -121, 73, 5, 64, -121, 73, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 65, 3, -128, -127, 97, -127, 97, -121, 73, 5, 48, -121, 73, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 61, 3, -125, 49, 2, -125, 37, 1, -125, 53, 6, -121, 77, 5, 64, -121, 73, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 61, 3, -128, -127, 97, -127, 97, -121, 77, 5, 48, -121, 73, 5, 48, -128, -124, 16, -128, -128, -125, 60, 3, -128, -125, 48, 1, -125, 48, 6, -121, 77, 5, 64, -121, 77, 5, 32, -128, 73, 12, 80, 14, -62, -128, -128, -125, 60, 3, -128, -127, 97, -127, 97, -121, 77, 5, 48, -121, 77, 5, 48, -128, -124, 16, -128, -128, -125, 56, 3, -125, 61, 2, -125, 37, 1, -125, 49, 6, -121, 72, 5, 64, -121, 77, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -127, 97, -125, 60, 4, -125, 56, 3, -128, -127, 97, -127, 97, -121, 72, 5, 48, -121, 77, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 53, 3, -128, -125, 48, 1, -125, 48, 6, -121, 72, 5, 64, -121, 72, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 53, 3, -128, -127, 97, -127, 97, -121, 72, 5, 48, -121, 72, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 49, 3, -125, 49, 2, -125, 44, 1, -125, 53, 6, -121, 68, 5, 64, -121, 72, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 49, 3, -128, -127, 97, -127, 97, -121, 68, 5, 48, -121, 72, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 53, 3, -128, -125, 41, 1, -125, 48, 6, -121, 68, 5, 64, -121, 68, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 53, 3, -128, -127, 97, -127, 97, -121, 68, 5, 48, -121, 68, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 56, 3, -125, 49, 2, -125, 37, 1, -125, 53, 6, -121, 65, 5, 64, -121, 68, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -125, 61, 4, -127, 97, -125, 56, 3, -128, -127, 97, -127, 97, -121, 65, 5, 48, -121, 68, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 60, 3, -125, 49, 2, -125, 36, 1, -125, 56, 6, -121, 65, 5, 64, -121, 65, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 60, 3, -128, -127, 97, -127, 97, -121, 65, 5, 48, -121, 65, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 61, 3, -125, 49, 2, -125, 37, 1, -125, 53, 6, -121, 61, 5, 64, -121, 65, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 61, 3, -128, -127, 97, -127, 97, -121, 61, 5, 48, -121, 65, 5, 48, -128, -124, 16, -128, -128, -125, 65, 3, -128, -125, 41, 1, -125, 48, 6, -121, 61, 5, 64, -121, 61, 5, 32, -128, 73, 12, 80, 14, -62, -128, -128, -125, 65, 3, -128, -127, 97, -127, 97, -121, 61, 5, 48, -121, 61, 5, 48, -128, -124, 16, -128, -128, -125, 68, 3, -125, 49, 2, -125, 37, 1, -125, 49, 6, -121, 60, 5, 64, -121, 61, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -127, 97, -125, 60, 4, -125, 68, 3, -125, 49, 2, -127, 97, -127, 97, -121, 60, 5, 48, -121, 61, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 72, 3, -125, 49, 2, -125, 48, 1, -125, 56, 6, -121, 60, 5, 64, -121, 60, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 72, 3, -125, 49, 2, -127, 97, -127, 97, -121, 60, 5, 48, -121, 60, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 68, 3, -125, 49, 2, -125, 37, 1, -125, 53, 6, -121, 56, 5, 64, -121, 60, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 68, 3, -128, -127, 97, -127, 97, -121, 56, 5, 48, -121, 60, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 65, 3, -128, -125, 48, 1, -125, 48, 6, -121, 56, 5, 64, -121, 56, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 65, 3, -128, -127, 97, -127, 97, -121, 56, 5, 48, -121, 56, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 61, 3, -128, -125, 44, 1, -125, 49, 6, -121, 53, 5, 64, -121, 56, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -125, 56, 4, -127, 97, -125, 61, 3, -128, -127, 97, -127, 97, -121, 53, 5, 48, -121, 56, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 60, 3, -125, 61, 2, -125, 41, 1, -125, 48, 6, -121, 53, 5, 64, -121, 53, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 60, 3, -128, -127, 97, -127, 97, -121, 53, 5, 48, -121, 53, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 56, 3, -128, -125, 37, 1, -125, 53, 6, -121, 60, 5, 64, -121, 53, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 56, 3, -128, -127, 97, -127, 97, -121, 60, 5, 48, -121, 53, 5, 48, -128, -124, 16, -128, -128, -125, 53, 3, -128, -125, 36, 1, -125, 48, 6, -121, 60, 5, 64, -121, 60, 5, 32, -128, 73, 12, 80, 14, -62, -128, -128, -125, 53, 3, -128, -127, 97, -127, 97, -121, 60, 5, 48, -121, 60, 5, 48, -128, -124, 16, -128, -128, -125, 49, 3, -125, 49, 2, -125, 37, 1, -125, 53, 6, -121, 65, 5, 64, -121, 60, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -127, 97, -125, 53, 4, -125, 49, 3, -128, -127, 97, -127, 97, -121, 65, 5, 48, -121, 60, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 48, 3, -125, 61, 2, -125, 41, 1, -125, 56, 6, -121, 65, 5, 64, -121, 65, 5, 32, -101, 49, 11, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 48, 3, -128, -127, 97, -127, 97, -121, 65, 5, 48, -121, 65, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 44, 3, -125, 61, 2, -125, 37, 1, -125, 53, 6, -121, 61, 5, 64, -121, 65, 5, 32, -125, 61, 9, 73, 12, 80, 14, -62, -128, -128, -125, 44, 3, -128, -127, 97, -127, 97, -121, 61, 5, 48, -121, 65, 5, 48, -104, 2, 32, -124, 16, -128, -128, -125, 48, 3, -128, -125, 37, 1, -125, 48, 6, -121, 61, 5, 64, -121, 61, 5, 32, -104, 2, 32, 73, 12, 80, 14, -62, -128, -128, -125, 48, 3, -128, -127, 97, -127, 97, -121, 61, 5, 48, -121, 61, 5, 48, -104, 2, 32, -124, 16, -128, -128};
			pattern.setPackedData(packedData);
			//try unpacking
			int numChannels=10;
			PatternData[][] unpacked=pattern.unpack(numChannels);
			assertEquals(64,unpacked.length);
			assertEquals(10,unpacked[0].length);
			//row 0 - channel 0
			assertEquals(49,unpacked[0][0].note);
			assertEquals(3,unpacked[0][0].instrument);
			assertEquals(0,unpacked[0][0].volumeColumn);
			assertEquals(0,unpacked[0][0].effectType);
			assertEquals(0,unpacked[0][0].effectParameter);
			//row 0 - channel 1
			assertEquals(49,unpacked[0][1].note);
			assertEquals(2,unpacked[0][1].instrument);
			assertEquals(0,unpacked[0][1].volumeColumn);
			assertEquals(0,unpacked[0][1].effectType);
			assertEquals(0,unpacked[0][1].effectParameter);
			//row 0 - channel 2
			assertEquals(37,unpacked[0][2].note);
			assertEquals(1,unpacked[0][2].instrument);
			assertEquals(0,unpacked[0][2].volumeColumn);
			assertEquals(0,unpacked[0][2].effectType);
			assertEquals(0,unpacked[0][2].effectParameter);
			//row 0 - channel 3
			assertEquals(49,unpacked[0][3].note);
			assertEquals(6,unpacked[0][3].instrument);
			assertEquals(0,unpacked[0][3].volumeColumn);
			assertEquals(0,unpacked[0][3].effectType);
			assertEquals(0,unpacked[0][3].effectParameter);
			//row 0 - channel 4
			assertEquals(61,unpacked[0][4].note);
			assertEquals(5,unpacked[0][4].instrument);
			assertEquals(64,unpacked[0][4].volumeColumn);
			assertEquals(0,unpacked[0][4].effectType);
			assertEquals(0,unpacked[0][4].effectParameter);
			//row 0 - channel 5
			assertEquals(61,unpacked[0][5].note);
			assertEquals(5,unpacked[0][5].instrument);
			assertEquals(32,unpacked[0][5].volumeColumn);
			assertEquals(0,unpacked[0][5].effectType);
			assertEquals(0,unpacked[0][5].effectParameter);
			//row 0 - channel 6
			assertEquals(49,unpacked[0][6].note);
			assertEquals(11,unpacked[0][6].instrument);
			assertEquals(0,unpacked[0][6].volumeColumn);
			assertEquals(2,unpacked[0][6].effectType);
			assertEquals(32,unpacked[0][6].effectParameter);
			//row 0 - channel 7 (unpacked)
			assertEquals(73,unpacked[0][7].note);
			assertEquals(12,unpacked[0][7].instrument);
			assertEquals(80,unpacked[0][7].volumeColumn);
			assertEquals(14,unpacked[0][7].effectType);
			assertEquals(194,unpacked[0][7].effectParameter);
			//row 0 - channel 8
			assertEquals(49,unpacked[0][8].note);
			assertEquals(4,unpacked[0][8].instrument);
			assertEquals(0,unpacked[0][8].volumeColumn);
			assertEquals(0,unpacked[0][8].effectType);
			assertEquals(0,unpacked[0][8].effectParameter);
			//row 0 - channel 9
			assertEquals(XMConstants.NOTE_OFF,unpacked[0][9].note);
			assertEquals(0,unpacked[0][9].instrument);
			assertEquals(0,unpacked[0][9].volumeColumn);
			assertEquals(0,unpacked[0][9].effectType);
			assertEquals(0,unpacked[0][9].effectParameter);
			//row 1 - channel 0
			assertEquals(49,unpacked[1][0].note);
			assertEquals(3,unpacked[1][0].instrument);
			assertEquals(0,unpacked[1][0].volumeColumn);
			assertEquals(0,unpacked[1][0].effectType);
			assertEquals(0,unpacked[1][0].effectParameter);
			//row 1 - channel 1
			assertEquals(0,unpacked[1][1].note);
			assertEquals(0,unpacked[1][1].instrument);
			assertEquals(0,unpacked[1][1].volumeColumn);
			assertEquals(0,unpacked[1][1].effectType);
			assertEquals(0,unpacked[1][1].effectParameter);
			//row 1 - channel 2
			assertEquals(97,unpacked[1][2].note);
			assertEquals(0,unpacked[1][2].instrument);
			assertEquals(0,unpacked[1][2].volumeColumn);
			assertEquals(0,unpacked[1][2].effectType);
			assertEquals(0,unpacked[1][2].effectParameter);
			//row 1 - channel 3
			assertEquals(97,unpacked[1][3].note);
			assertEquals(0,unpacked[1][3].instrument);
			assertEquals(0,unpacked[1][3].volumeColumn);
			assertEquals(0,unpacked[1][3].effectType);
			assertEquals(0,unpacked[1][3].effectParameter);
			//row 1 - channel 4
			assertEquals(61,unpacked[1][4].note);
			assertEquals(5,unpacked[1][4].instrument);
			assertEquals(48,unpacked[1][4].volumeColumn);
			assertEquals(0,unpacked[1][4].effectType);
			assertEquals(0,unpacked[1][4].effectParameter);
			//row 1 - channel 5
			assertEquals(61,unpacked[1][5].note);
			assertEquals(5,unpacked[1][5].instrument);
			assertEquals(48,unpacked[1][5].volumeColumn);
			assertEquals(0,unpacked[1][5].effectType);
			assertEquals(0,unpacked[1][5].effectParameter);
			//row 1 - channel 6
			assertEquals(0,unpacked[1][6].note);
			assertEquals(0,unpacked[1][6].instrument);
			assertEquals(0,unpacked[1][6].volumeColumn);
			assertEquals(2,unpacked[1][6].effectType);
			assertEquals(32,unpacked[1][6].effectParameter);
			//row 1 - channel 7
			assertEquals(0,unpacked[1][7].note);
			assertEquals(0,unpacked[1][7].instrument);
			assertEquals(16,unpacked[1][7].volumeColumn);
			assertEquals(0,unpacked[1][7].effectType);
			assertEquals(0,unpacked[1][7].effectParameter);
			//row 1 - channel 8
			assertEquals(0,unpacked[1][8].note);
			assertEquals(0,unpacked[1][8].instrument);
			assertEquals(0,unpacked[1][8].volumeColumn);
			assertEquals(0,unpacked[1][8].effectType);
			assertEquals(0,unpacked[1][8].effectParameter);
			//row 1 - channel 9
			assertEquals(0,unpacked[1][9].note);
			assertEquals(0,unpacked[1][9].instrument);
			assertEquals(0,unpacked[1][9].volumeColumn);
			assertEquals(0,unpacked[1][9].effectType);
			assertEquals(0,unpacked[1][9].effectParameter);
			//also test the last two records just because
			assertEquals(0,unpacked[63][8].note);
			assertEquals(0,unpacked[63][8].instrument);
			assertEquals(0,unpacked[63][8].volumeColumn);
			assertEquals(0,unpacked[63][8].effectType);
			assertEquals(0,unpacked[63][8].effectParameter);
			assertEquals(0,unpacked[63][9].note);
			assertEquals(0,unpacked[63][9].instrument);
			assertEquals(0,unpacked[63][9].volumeColumn);
			assertEquals(0,unpacked[63][9].effectType);
			assertEquals(0,unpacked[63][9].effectParameter);
		}catch(Exception x){
			fail(x.getMessage());
		}		
	}
}