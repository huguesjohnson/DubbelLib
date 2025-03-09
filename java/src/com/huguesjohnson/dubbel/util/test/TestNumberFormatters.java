/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.Endianness;
import com.huguesjohnson.dubbel.util.NumberFormatters;

class TestNumberFormatters{

	@Test
	void testToHexWord(){
		assertEquals(NumberFormatters.toHexWord(0),"$0000");
		assertEquals(NumberFormatters.toHexWord(1),"$0001");
		assertEquals(NumberFormatters.toHexWord(666),"$029A");
		assertEquals(NumberFormatters.toHexWord(6489),"$1959");
		assertEquals(NumberFormatters.toHexWord(65535),"$FFFF");
		assertEquals(NumberFormatters.toHexWord(65536),"$10000");
	}

	@Test
	void testDecimalStringToAsciiString(){
		assertEquals(NumberFormatters.decimalStringToAsciiString("54 54 54"),"6 6 6 ");
		assertEquals(NumberFormatters.decimalStringToAsciiString("87 104 97 116 101 118 101 114 "),"W h a t e v e r ");
	}

	@Test
	void testDecimalStringToHexString(){
		assertEquals(NumberFormatters.decimalStringToHexString("54 54 54"),"36 36 36 ");
		assertEquals(NumberFormatters.decimalStringToHexString("87 104 97 116 101 118 101 114 "),"57 68 61 74 65 76 65 72 ");
	}

	@Test
	void testDecimalStringToOctalString(){
		assertEquals(NumberFormatters.decimalStringToOctalString("666"),"1232 ");
		assertEquals(NumberFormatters.decimalStringToOctalString("6 4 89"),"6 4 131 ");
	}

	@Test
	void testDecimalStringToBinaryString(){
		assertEquals(NumberFormatters.decimalStringToBinaryString("666"),"1010011010 ");
		assertEquals(NumberFormatters.decimalStringToBinaryString("6 4 89"),"110 100 1011001 ");
	}

	@Test
	void testAsciiStringToDecimalString(){
		assertEquals(NumberFormatters.asciiStringToDecimalString("666"),"54 54 54 ");
		assertEquals(NumberFormatters.asciiStringToDecimalString("Whatever"),"87 104 97 116 101 118 101 114 ");
	}

	@Test
	void testHexStringToDecimalString(){
		assertEquals(NumberFormatters.hexStringToDecimalString("36 36 36"),"54 54 54 ");
		assertEquals(NumberFormatters.hexStringToDecimalString("57 68 61 74 65 76 65 72"),"87 104 97 116 101 118 101 114 ");

	}

	@Test
	void testOctalStringToDecimalString(){
		assertEquals(NumberFormatters.octalStringToDecimalString("1232"),"666 ");
		assertEquals(NumberFormatters.octalStringToDecimalString("6 4 131"),"6 4 89 ");
	}

	@Test
	void testBinaryStringToDecimalString(){
		assertEquals(NumberFormatters.binaryStringToDecimalString("1010011010"),"666 ");
		assertEquals(NumberFormatters.binaryStringToDecimalString("110 100 1011001"),"6 4 89 ");
	}

	@Test
	void testByteArrayToString(){
		byte b[]=new byte[3];
		b[0]=6;
		b[1]=4;
		b[2]=89;
		assertEquals(NumberFormatters.byteArrayToString(b),"[0x6] [0x4] [0x59] ");
		
	}

	@Test
	void testByteArrayToInt(){
		byte b[]=new byte[3];
		b[0]=6;
		b[1]=4;
		b[2]=89;
		assertEquals(NumberFormatters.byteArrayToInt(b,Endianness.BIG_ENDIAN),394329);
		assertEquals(NumberFormatters.byteArrayToInt(b,Endianness.LITTLE_ENDIAN),5833734);
		assertEquals(NumberFormatters.byteArrayToInt(b,1,2,Endianness.BIG_ENDIAN),1113);
		assertEquals(NumberFormatters.byteArrayToInt(b,1,2,Endianness.LITTLE_ENDIAN),22788);
	}

	@Test
	void testIntToHex(){
		assertEquals(NumberFormatters.intToHex(0),"0x0");
		assertEquals(NumberFormatters.intToHex(1),"0x1");
		assertEquals(NumberFormatters.intToHex(666),"0x29A");
		assertEquals(NumberFormatters.intToHex(6489),"0x1959");
		assertEquals(NumberFormatters.intToHex(65535),"0xFFFF");
		assertEquals(NumberFormatters.intToHex(65536),"0x10000");
	}
}