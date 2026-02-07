/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.test;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.retailclerk.build.BuildScenes;

import junit.framework.TestCase;

class TestBuildScenes extends TestCase{

	@Test
	void testBuildPatternString(){
		assertEquals("0000000000000001",BuildScenes.buildPatternString(1,0,false));
		assertEquals("1000000000001001",BuildScenes.buildPatternString(9,0,true));
		assertEquals("0010000000011101",BuildScenes.buildPatternString(29,1,false));
		assertEquals("1100000000101101",BuildScenes.buildPatternString(45,2,true));
		assertEquals("0110000010000010",BuildScenes.buildPatternString(130,3,false));
	}
	
	@Test
	void testBuildRowColumnString(){
		assertEquals("00000000",BuildScenes.buildRowColumnString(null));
		assertEquals("00000000",BuildScenes.buildRowColumnString("0"));
		assertEquals("03800000",BuildScenes.buildRowColumnString("03800000"));
		assertEquals("03800000",BuildScenes.buildRowColumnString("3800000"));
	}
	
	@Test
	void testBuildWHXYString(){
		assertEquals("1000111010001000",BuildScenes.buildWHXYString(71,136));
		assertEquals("0101000100001000",BuildScenes.buildWHXYString(40,264));
	}

}