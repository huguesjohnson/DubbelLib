/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.ByteComparerResult;

class TestByteComparerResult{

	@Test
	void test(){
		ByteComparerResult bcr1=new ByteComparerResult();
		bcr1.setFile1Line(666);
		bcr1.setFile1Value((byte)13);
		bcr1.setFile2Line(1989);
		bcr1.setFile2Value((byte)64);
		assertEquals(666,bcr1.getFile1Line());
		assertEquals(13,bcr1.getFile1Value());
		assertEquals(1989,bcr1.getFile2Line());
		assertEquals(64,bcr1.getFile2Value());
		assertEquals("0x29A",bcr1.getFile1LineHex());
		assertEquals("0xD",bcr1.getFile1ValueHex());
		assertEquals("0x7C5",bcr1.getFile2LineHex());
		assertEquals("0x40",bcr1.getFile2ValueHex());

		ByteComparerResult bcr2=new ByteComparerResult(667,(byte)14,1990,(byte)65);
		assertEquals(667,bcr2.getFile1Line());
		assertEquals(14,bcr2.getFile1Value());
		assertEquals(1990,bcr2.getFile2Line());
		assertEquals(65,bcr2.getFile2Value());
		assertEquals("0x29B",bcr2.getFile1LineHex());
		assertEquals("0xE",bcr2.getFile1ValueHex());
		assertEquals("0x7C6",bcr2.getFile2LineHex());
		assertEquals("0x41",bcr2.getFile2ValueHex());
	}
}
