/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.ChecksumUtil;

class TestChecksumUtils{

	@Test
	void testSumBytes(){
		byte[] b=new byte[]{(byte)66,(byte)13,(byte)6,(byte)4,(byte)89};
		assertEquals(178,ChecksumUtil.sumBytes(b));
		assertEquals(179,ChecksumUtil.sumBytes(b,1));
		b=new byte[]{(byte)66,(byte)-13,(byte)6,(byte)4,(byte)89};
		assertEquals(152,ChecksumUtil.sumBytes(b));
	}
	
	@Test
	void testSubBytes(){
		byte[] b=new byte[]{(byte)66,(byte)13,(byte)6,(byte)4,(byte)89};
		assertEquals(-178,ChecksumUtil.subBytes(b));
		assertEquals(-177,ChecksumUtil.subBytes(b,1));
		b=new byte[]{(byte)66,(byte)-13,(byte)6,(byte)4,(byte)89};
		assertEquals(-152,ChecksumUtil.subBytes(b));
	}	

	@Test
	void testXorBytes(){
		byte[] b=new byte[]{(byte)1,(byte)2};
		assertEquals(3,ChecksumUtil.xorBytes(b));
		b=new byte[]{(byte)66,(byte)13};
		assertEquals(79,ChecksumUtil.xorBytes(b));
		b=new byte[]{(byte)66,(byte)13,(byte)6,(byte)4,(byte)89};
		assertEquals(20,ChecksumUtil.xorBytes(b));
	}

}