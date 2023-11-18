/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.ChecksumUtils;

class TestChecksumUtils{

	@Test
	void testSumBytes(){
		byte[] b=new byte[]{(byte)66,(byte)13,(byte)6,(byte)4,(byte)89};
		assertEquals(178,ChecksumUtils.sumBytes(b));
		assertEquals(179,ChecksumUtils.sumBytes(b,1));
	}

	@Test
	void testXorBytes(){
		byte[] b=new byte[]{(byte)1,(byte)2};
		assertEquals(3,ChecksumUtils.xorBytes(b));
		b=new byte[]{(byte)66,(byte)13};
		assertEquals(79,ChecksumUtils.xorBytes(b));
		b=new byte[]{(byte)66,(byte)13,(byte)6,(byte)4,(byte)89};
		assertEquals(20,ChecksumUtils.xorBytes(b));
	}

}