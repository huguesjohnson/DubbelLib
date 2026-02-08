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
	void testToRLELines(){
		Tile8x8 tile0=new Tile8x8();
		Tile8x8 tile1=new Tile8x8();
		for(int r=0;r<8;r++){
			for(int c=0;c<8;c++){
				tile0.pixels[r][c]=0;
				tile1.pixels[r][c]=r+c;
			}
		}		
		String[] lines=tile0.toRLELines().split(Tile8x8.newLine);
		assertEquals(1,lines.length);
		assertTrue(lines[0].indexOf("$3F,0")>0);
		lines=tile1.toRLELines().split(Tile8x8.newLine);
		assertEquals(64,lines.length);
		assertTrue(lines[0].indexOf("$0,0")>0);
		assertTrue(lines[1].indexOf("$0,1")>0);
		assertTrue(lines[2].indexOf("$0,2")>0);
		assertTrue(lines[3].indexOf("$0,3")>0);
		assertTrue(lines[4].indexOf("$0,4")>0);
		assertTrue(lines[5].indexOf("$0,5")>0);
		assertTrue(lines[6].indexOf("$0,6")>0);
		assertTrue(lines[7].indexOf("$0,7")>0);
		assertTrue(lines[8].indexOf("$0,1")>0);
		assertTrue(lines[9].indexOf("$0,2")>0);
		assertTrue(lines[10].indexOf("$0,3")>0);
		assertTrue(lines[11].indexOf("$0,4")>0);
		assertTrue(lines[12].indexOf("$0,5")>0);
		assertTrue(lines[13].indexOf("$0,6")>0);
		assertTrue(lines[14].indexOf("$0,7")>0);
		assertTrue(lines[15].indexOf("$0,8")>0);
		assertTrue(lines[16].indexOf("$0,2")>0);
		assertTrue(lines[17].indexOf("$0,3")>0);
		assertTrue(lines[18].indexOf("$0,4")>0);
		assertTrue(lines[19].indexOf("$0,5")>0);
		assertTrue(lines[20].indexOf("$0,6")>0);
		assertTrue(lines[21].indexOf("$0,7")>0);
		assertTrue(lines[22].indexOf("$0,8")>0);
		assertTrue(lines[23].indexOf("$0,9")>0);
		assertTrue(lines[24].indexOf("$0,3")>0);
		assertTrue(lines[25].indexOf("$0,4")>0);
		assertTrue(lines[26].indexOf("$0,5")>0);
		assertTrue(lines[27].indexOf("$0,6")>0);
		assertTrue(lines[28].indexOf("$0,7")>0);
		assertTrue(lines[29].indexOf("$0,8")>0);
		assertTrue(lines[30].indexOf("$0,9")>0);
		assertTrue(lines[31].indexOf("$0,A")>0);
		assertTrue(lines[32].indexOf("$0,4")>0);
		assertTrue(lines[33].indexOf("$0,5")>0);
		assertTrue(lines[34].indexOf("$0,6")>0);
		assertTrue(lines[35].indexOf("$0,7")>0);
		assertTrue(lines[36].indexOf("$0,8")>0);
		assertTrue(lines[37].indexOf("$0,9")>0);
		assertTrue(lines[38].indexOf("$0,A")>0);
		assertTrue(lines[39].indexOf("$0,B")>0);
		assertTrue(lines[40].indexOf("$0,5")>0);
		assertTrue(lines[41].indexOf("$0,6")>0);
		assertTrue(lines[42].indexOf("$0,7")>0);
		assertTrue(lines[43].indexOf("$0,8")>0);
		assertTrue(lines[44].indexOf("$0,9")>0);
		assertTrue(lines[45].indexOf("$0,A")>0);
		assertTrue(lines[46].indexOf("$0,B")>0);
		assertTrue(lines[47].indexOf("$0,C")>0);
		assertTrue(lines[48].indexOf("$0,6")>0);
		assertTrue(lines[49].indexOf("$0,7")>0);
		assertTrue(lines[50].indexOf("$0,8")>0);
		assertTrue(lines[51].indexOf("$0,9")>0);
		assertTrue(lines[52].indexOf("$0,A")>0);
		assertTrue(lines[53].indexOf("$0,B")>0);
		assertTrue(lines[54].indexOf("$0,C")>0);
		assertTrue(lines[55].indexOf("$0,D")>0);
		assertTrue(lines[56].indexOf("$0,7")>0);
		assertTrue(lines[57].indexOf("$0,8")>0);
		assertTrue(lines[58].indexOf("$0,9")>0);
		assertTrue(lines[59].indexOf("$0,A")>0);
		assertTrue(lines[60].indexOf("$0,B")>0);
		assertTrue(lines[61].indexOf("$0,C")>0);
		assertTrue(lines[62].indexOf("$0,D")>0);
		assertTrue(lines[63].indexOf("$0,E")>0);
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