/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.StringDistance;

class TestStringDistance{
	@Test
	void test_hammingDistance(){
		try{
			String s1="qwerty";
			String s2="asdfgh";
			int hd=StringDistance.hammingDistance(s1,s2);
			assertEquals(6,hd);
			s2="qwerty";
			hd=StringDistance.hammingDistance(s1,s2);
			assertEquals(0,hd);
			s2="qxxrty";
			hd=StringDistance.hammingDistance(s1,s2);
			assertEquals(2,hd);
			s2="qwertY";
			hd=StringDistance.hammingDistance(s1,s2);
			assertEquals(1,hd);
		}catch(Exception x){
			fail(x.getMessage());
		}
		//test negative case
		try{
			String s1="blah";
			String s2="blah blah";
			StringDistance.hammingDistance(s1,s2);
			fail("Expected exception to be thrown");
		}catch(Exception x){ /* this is fine */ }
	}
	
	@Test
	void test_levenshteinDistance(){
		String s1="qwerty";
		String s2="qwerty";
		int ld=StringDistance.levenshteinDistance(s1,s2);
		assertEquals(0,ld);
		s2="qwertyu";
		ld=StringDistance.levenshteinDistance(s1,s2);
		assertEquals(1,ld);
		s2="qwert";
		ld=StringDistance.levenshteinDistance(s1,s2);
		assertEquals(1,ld);
		s2="qxxxty";
		ld=StringDistance.levenshteinDistance(s1,s2);
		assertEquals(3,ld);
		s2="qXwerty";
		ld=StringDistance.levenshteinDistance(s1,s2);
		assertEquals(1,ld);
	}
}