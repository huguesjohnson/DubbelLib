/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.GenesisColorUtil;

class TestGenesisColorUtil{

	@Test
	void testHexStringToGenesisRgb(){
		assertEquals(GenesisColorUtil.hexStringToGenesisRgb("0"),"000");
		assertEquals(GenesisColorUtil.hexStringToGenesisRgb("00000000"),"000");
		assertEquals(GenesisColorUtil.hexStringToGenesisRgb("FFF"),"111");
	}

	@Test
	void testRgbStringToGenesisRgbString(){
		assertEquals(GenesisColorUtil.rgbStringToGenesisRgbString("ff000000"),"0000000000000000");
		assertEquals(GenesisColorUtil.rgbStringToGenesisRgbString("FFFFFFFF"),"0000111011101110");
	}

	@Test
	void testGenesisRgbStringToHexString(){
		Color c=GenesisColorUtil.genesisRgbStringToColor("0000111011101110");
		assertEquals(c.getBlue(),224);
		assertEquals(c.getRed(),224);
		assertEquals(c.getGreen(),224);
	}


	@Test
	void testColorDistance(){
		assertEquals(GenesisColorUtil.colorDistance("ff0e0e0e","ff0c0c0c"),0D);
	}

	@Test
	void testFindNearestColor(){
		String s=GenesisColorUtil.hexStringToGenesisRgb("ff");
		assertEquals("111",s);
		s=GenesisColorUtil.rgbStringToGenesisRgbString("ffe0a040");
		assertEquals("0000010010101110",s);
		s=GenesisColorUtil.genesisRgbStringToHexString("%"+s);
		assertEquals("ffe0a040",s);
		s=GenesisColorUtil.genesisRgbStringToHexString("dc.w\t%0000010010101110 ; blah"+0000010010101110);
		assertEquals("ffe0a040",s);
		ArrayList<String> colors=new ArrayList<String>();
		colors.add("ffe000e0");//00
		colors.add("ff000000");//01
		colors.add("ff806440");//02
		colors.add("ffe0c8a0");//03
		colors.add("ff80a8c0");//04		
		colors.add("ff604020");//05
		colors.add("ffc0a880");//06
		colors.add("ffa06420");//07
		colors.add("ff608440");//08
		colors.add("ff802080");//09
		colors.add("ffc0c8c0");//0A
		colors.add("ffc06420");//0B
		colors.add("ffc00000");//0C
		colors.add("ffe0e8e0");//0D
		colors.add("ffe0c820");//0E
		colors.add("ff6084a0");//0F
		assertEquals(0,GenesisColorUtil.findNearestColor(colors,"ffe000e0"));
		assertEquals(1,GenesisColorUtil.findNearestColor(colors,"ff000000"));
		assertEquals(2,GenesisColorUtil.findNearestColor(colors,"ff806440"));
		assertEquals(3,GenesisColorUtil.findNearestColor(colors,"ffe0c8a0"));
		assertEquals(4,GenesisColorUtil.findNearestColor(colors,"ff80a8c0"));
		assertEquals(5,GenesisColorUtil.findNearestColor(colors,"ff604020"));
		assertEquals(6,GenesisColorUtil.findNearestColor(colors,"ffc0a880"));
		assertEquals(7,GenesisColorUtil.findNearestColor(colors,"ffa06420"));
		assertEquals(8,GenesisColorUtil.findNearestColor(colors,"ff608440"));
		assertEquals(9,GenesisColorUtil.findNearestColor(colors,"ff802080"));
		assertEquals(10,GenesisColorUtil.findNearestColor(colors,"ffc0c8c0"));
		assertEquals(11,GenesisColorUtil.findNearestColor(colors,"ffc06420"));
		assertEquals(12,GenesisColorUtil.findNearestColor(colors,"ffc00000"));
		assertEquals(13,GenesisColorUtil.findNearestColor(colors,"ffe0e8e0"));
		assertEquals(14,GenesisColorUtil.findNearestColor(colors,"ffe0c820"));
		assertEquals(15,GenesisColorUtil.findNearestColor(colors,"ff6084a0"));
		assertEquals(0,GenesisColorUtil.findNearestColor(colors,"ffe000e8"));
		assertEquals(1,GenesisColorUtil.findNearestColor(colors,"ff200000"));
		assertEquals(2,GenesisColorUtil.findNearestColor(colors,"ff806040"));
		assertEquals(3,GenesisColorUtil.findNearestColor(colors,"ffe0c0a0"));
		assertEquals(4,GenesisColorUtil.findNearestColor(colors,"ff80a0c0"));
		assertEquals(5,GenesisColorUtil.findNearestColor(colors,"ff602020"));
		assertEquals(6,GenesisColorUtil.findNearestColor(colors,"ffa0a880"));
		assertEquals(7,GenesisColorUtil.findNearestColor(colors,"ffa06400"));
		assertEquals(8,GenesisColorUtil.findNearestColor(colors,"ff608420"));
		assertEquals(9,GenesisColorUtil.findNearestColor(colors,"ff800080"));
		assertEquals(10,GenesisColorUtil.findNearestColor(colors,"ffc0c0c0"));
		assertEquals(11,GenesisColorUtil.findNearestColor(colors,"ffc06820"));
		assertEquals(12,GenesisColorUtil.findNearestColor(colors,"ffc00020"));
		assertEquals(13,GenesisColorUtil.findNearestColor(colors,"ffe0e8c0"));
		assertEquals(14,GenesisColorUtil.findNearestColor(colors,"ffe0c800"));
		assertEquals(15,GenesisColorUtil.findNearestColor(colors,"ff6080a0"));
	}	
	
}