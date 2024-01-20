/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.GenesisColorUtil;

class TestGenesisColorUtils{

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

}