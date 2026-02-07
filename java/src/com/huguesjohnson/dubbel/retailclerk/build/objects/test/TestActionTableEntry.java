/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.retailclerk.build.objects.ActionTableEntry;

class TestActionTableEntry{
	
	@Test
	void testActionTableEntry(){
		ActionTableEntry e1=new ActionTableEntry();
		e1.action="action1";
		e1.day=2;
		e1.scene="scene3";
		ActionTableEntry e2=new ActionTableEntry();
		e2.action="action1";
		e2.day=2;
		e2.scene="scene3";
		ActionTableEntry e3=new ActionTableEntry();
		e3.action="scene666";
		e3.day=13;
		e3.scene="scene8964";
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		assertTrue(e1.hashCode()==e2.hashCode());
		assertFalse(e1.equals(e3));
		assertFalse(e2.equals(e3));
		assertFalse(e3.equals(e1));
		assertFalse(e3.equals(e2));
		assertFalse(e1.hashCode()==e3.hashCode());
		assertFalse(e2.hashCode()==e3.hashCode());
	}

}