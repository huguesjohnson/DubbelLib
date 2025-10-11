package com.huguesjohnson.dubbel.audio.xm.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.audio.xm.PatternData;
import com.huguesjohnson.dubbel.audio.xm.XMConstants;
import com.huguesjohnson.dubbel.audio.xm.XMFile;
import com.huguesjohnson.dubbel.audio.xm.XMHeader;
import com.huguesjohnson.dubbel.audio.xm.XMInstrument;
import com.huguesjohnson.dubbel.audio.xm.XMPattern;
import com.huguesjohnson.dubbel.audio.xm.XMReader;

/* JUnit needs to have a classpath entry to:
/* DubbelLib/java/src/com/huguesjohnson/dubbel/audio/xm/test/resources
 * for this test to work
 */
class TestXMReader{

	@Test
	void testRead(){
		String filePath=null;
		try{
			ClassLoader cl=Thread.currentThread().getContextClassLoader();
			filePath=(cl.getResource("Drozerix_chip_overture.xm")).getFile();
			if(filePath==null){throw(new Exception());}
		}catch(Exception x){
			fail("Unable to find test .xm file. Please check JUnit classpath entries. This test needs to include [DubbelLib/java/src/com/huguesjohnson/dubbel/audio/xm/test/resources] in the classpath.");
		}
		try{
			XMFile xm=XMReader.read(filePath);
			/*
			 * Validate the header
			 */
			XMHeader header=xm.getHeader();
			assertEquals("Extended Module:",header.getId().trim());
			assertEquals("CHiP_OVERTURE",header.getName().trim());
			assertEquals("MilkyTracker",header.getTrackerName().trim());
			assertEquals("0104",header.getVersionNumber());
			assertEquals(276,header.getHeaderSize());
			assertEquals(23,header.getSongLength());
			assertEquals(0,header.getRestartPosition());
			assertEquals(10,header.getNumChannels());
			assertEquals(20,header.getNumPatterns());
			assertEquals(12,header.getNumInstruments());
			assertEquals(1,header.getFlags());
			assertEquals(6,header.getDefaultTempo());
			assertEquals(160,header.getDefaultBPM());
			byte[] patternOrderTable=header.getPatternOrderTable();
			assertEquals(2,patternOrderTable[0]);
			assertEquals(3,patternOrderTable[1]);
			assertEquals(4,patternOrderTable[2]);
			assertEquals(5,patternOrderTable[3]);
			assertEquals(6,patternOrderTable[4]);
			assertEquals(7,patternOrderTable[5]);
			assertEquals(7,patternOrderTable[6]);
			assertEquals(8,patternOrderTable[7]);
			assertEquals(9,patternOrderTable[8]);
			assertEquals(0,patternOrderTable[9]);
			assertEquals(0,patternOrderTable[10]);
			assertEquals(10,patternOrderTable[11]);
			assertEquals(11,patternOrderTable[12]);
			assertEquals(12,patternOrderTable[13]);
			assertEquals(13,patternOrderTable[14]);
			assertEquals(14,patternOrderTable[15]);
			assertEquals(15,patternOrderTable[16]);
			assertEquals(16,patternOrderTable[17]);
			assertEquals(17,patternOrderTable[18]);
			assertEquals(16,patternOrderTable[19]);
			assertEquals(17,patternOrderTable[20]);
			assertEquals(18,patternOrderTable[21]);
			assertEquals(19,patternOrderTable[22]);
			/*
			 * The patterns
			 */	
			List<XMPattern> patterns=xm.getPatterns();
			int size=patterns.size();
			assertEquals(20,size);
			XMPattern pattern=patterns.get(0);
			assertEquals(64,pattern.getNumRows());
			/*
			 * This tests 2 rows of a 64 row pattern.
			 * This data luckily contains both packed and unpacked data.
			 * If I run into new problems with my implementation of unpack() then I will include the remaining rows in testing.
			 */
			PatternData[][] unpacked=pattern.unpack(header.getNumChannels());
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
			/*
			 * The Instruments (which includes samples)
			 */	
			List<XMInstrument> instruments=xm.getInstruments();
			assertEquals(12,instruments.size());
			assertEquals("CHiP_OVERTURE",instruments.get(0).getName().trim());
			assertEquals("______________________",instruments.get(1).getName().trim());
			assertEquals("",instruments.get(2).getName().trim());
			assertEquals("Composed by:",instruments.get(3).getName().trim());
			assertEquals("",instruments.get(4).getName().trim());
			assertEquals("M0n50n",instruments.get(5).getName().trim());
			assertEquals("______________________",instruments.get(6).getName().trim());
			assertEquals("",instruments.get(7).getName().trim());
			assertEquals("2-17-2012",instruments.get(8).getName().trim());
			assertEquals("",instruments.get(9).getName().trim());
			assertEquals("",instruments.get(10).getName().trim());
			assertEquals("",instruments.get(11).getName().trim());
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}
}