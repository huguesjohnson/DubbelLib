/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.example.TokenBucket;

class TestTokenBucket{

	@Test
	void test(){
		assertThrows(IllegalArgumentException.class,()->{new TokenBucket(0,0);});
		assertThrows(IllegalArgumentException.class,()->{new TokenBucket(-1,-1);});
		assertThrows(IllegalArgumentException.class,()->{new TokenBucket(0,1);});
		assertThrows(IllegalArgumentException.class,()->{new TokenBucket(-1,1);});
		assertThrows(IllegalArgumentException.class,()->{new TokenBucket(1,0);});
		assertThrows(IllegalArgumentException.class,()->{new TokenBucket(1,-1);});
		TokenBucket tb=null;
		try{
			tb=new TokenBucket(4,100L);
		}catch(IllegalArgumentException x){
			fail(x.getMessage());
		}
		assertNotNull(tb);
		assertTrue(tb.hasAvailableTokens());
		assertEquals(0,tb.getTokens(0));
		assertEquals(4,tb.getTokens(100));
		try{
			Thread.sleep(100L);
		}catch(InterruptedException x){
			fail(x.getMessage());
		}
		assertTrue(tb.hasAvailableTokens());
		assertEquals(1,tb.getTokens(1));
		assertEquals(1,tb.getTokens(1));
		assertEquals(1,tb.getTokens(1));
		assertEquals(1,tb.getTokens(1));
		assertEquals(0,tb.getTokens(1));
		assertFalse(tb.hasAvailableTokens());
		try{
			Thread.sleep(10L);
			assertFalse(tb.hasAvailableTokens());
			Thread.sleep(90L);
			assertTrue(tb.hasAvailableTokens());
		}catch(InterruptedException x){
			fail(x.getMessage());
		}
	}

}