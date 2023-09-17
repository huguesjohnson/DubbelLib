/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.example.WildcardRouterExample;


class TestWildcardRouterExample{

	@Test
	void testAddRoute(){
		WildcardRouterExample example=new WildcardRouterExample();
		try{
			example.addRoute("/route1/","response1");
			example.addRoute("/route2/","response2");
			example.addRoute("/route3/","response3");
			example.addRoute("/route4/","response4");
			example.addRoute("/wildcard1*","wildcardresponse1");
			example.addRoute("/wildcard2*","wildcardresponse2");
			example.addRoute("/wildcard3*","wildcardresponse3");
			example.addRoute("/wildcard4*","wildcardresponse4");
		}catch(Exception x){
			fail(x.getMessage());
		}
		try{
			example.addRoute("/route1/","response");
			fail("This request is supposed to fail");
		}catch(Exception x){
			//expected
		}
		try{
			example.addRoute("/wildcard1*","wildcardresponse");
			fail("This request is supposed to fail");
		}catch(Exception x){
			//expected
		}
	}

	@Test
	void testDoRoute(){
		WildcardRouterExample example=new WildcardRouterExample();
		try{
			example.addRoute("/route1/","response1");
			example.addRoute("/route2/","response2");
			example.addRoute("/route3/","response3");
			example.addRoute("/route4/","response4");
			example.addRoute("/wildcard1*","wildcardresponse1");
			example.addRoute("/wildcard2*","wildcardresponse2");
			example.addRoute("/wildcard3*","wildcardresponse3");
			example.addRoute("/wildcard4*","wildcardresponse4");
			assertEquals(example.doRoute("/route1/"),"response1");
			assertEquals(example.doRoute("/route2/"),"response2");
			assertEquals(example.doRoute("/route3/"),"response3");
			assertEquals(example.doRoute("/route4/"),"response4");
			assertEquals(example.doRoute("/wildcard1/whatever"),"wildcardresponse1");
			assertEquals(example.doRoute("/wildcard2/whatever"),"wildcardresponse2");
			assertEquals(example.doRoute("/wildcard3/whatever"),"wildcardresponse3");
			assertEquals(example.doRoute("/wildcard4/whatever"),"wildcardresponse4");
		}catch(Exception x){
			fail(x.getMessage());
		}		
		try{
			example.doRoute("/route5/");
			fail("This request is supposed to fail");
		}catch(Exception x){
			//expected
		}			
		try{
			example.doRoute("/wildcard");
			fail("This request is supposed to fail");
		}catch(Exception x){
			//expected
		}			
	}

}
