/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.retailclerk.build.objects.Tile8x8;

class TestTile8x8{
	@Test
	void testToAsmLines(){
		Tile8x8 tile0=new Tile8x8();
		Tile8x8 tile1=new Tile8x8();
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				tile0.pixels[r][c]=0;
				tile1.pixels[r][c]=r+c;
			}
		}		
		String[] lines=tile0.toAsmLines().split(Tile8x8.newLine);
		assertEquals(8,lines.length);
		for(int i=0;i<8;i++){
			assertTrue(lines[i].indexOf("00000000")>0);
		}
		lines=tile1.toAsmLines().split(Tile8x8.newLine);
		assertEquals(8,lines.length);
		assertTrue(lines[0].indexOf("01234567")>0);
		assertTrue(lines[1].indexOf("12345678")>0);
		assertTrue(lines[2].indexOf("23456789")>0);
		assertTrue(lines[3].indexOf("3456789A")>0);
		assertTrue(lines[4].indexOf("456789AB")>0);
		assertTrue(lines[5].indexOf("56789ABC")>0);
		assertTrue(lines[6].indexOf("6789ABCD")>0);
		assertTrue(lines[7].indexOf("789ABCDE")>0);
	}
	
	@Test
	void testEquals(){
		Tile8x8 tile0=new Tile8x8();
		Tile8x8 tile1=new Tile8x8();
		Tile8x8 tile2=new Tile8x8();
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				tile0.pixels[r][c]=0;
				tile1.pixels[r][c]=r+c;
				tile2.pixels[r][c]=r+c;
			}
		}
		assertTrue(tile1.equals(tile2));
		assertTrue(tile2.equals(tile1));
		assertFalse(tile1.equals(tile0));
		assertFalse(tile2.equals(tile0));
		assertFalse(tile0.equals(tile1));
		assertFalse(tile0.equals(tile2));
	}
}