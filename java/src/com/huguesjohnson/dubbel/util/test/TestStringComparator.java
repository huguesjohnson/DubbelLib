/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.StringComparator;

class TestStringComparator{

	@Test
	void testCompare(){
		ArrayList<String> list=new ArrayList<String>();
		list.add("q");
		list.add("w");
		list.add("e");
		list.add("r");
		list.add("t");
		list.add("y");
		list.add("Q");
		list.sort(new StringComparator());
		assertEquals(list.get(0),"e");
		assertEquals(list.get(1),"q");
		assertEquals(list.get(2),"Q");
		assertEquals(list.get(3),"r");
		assertEquals(list.get(4),"t");
		assertEquals(list.get(5),"w");
		assertEquals(list.get(6),"y");
	}

}