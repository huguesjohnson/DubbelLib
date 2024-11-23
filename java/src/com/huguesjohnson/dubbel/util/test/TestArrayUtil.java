/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.ArrayUtil;
import com.huguesjohnson.dubbel.util.StringComparator;

class TestArrayUtil{

	@Test
	public void test_xorBytes(){
		try{
			byte a[]=new byte[]{0,1,2,3,4};
			byte b[]=new byte[]{1,1,1,1,1};
			byte xor[]=ArrayUtil.xorBytes(a,b);
			assertEquals(xor[0],1);
			assertEquals(xor[1],0);
			assertEquals(xor[2],3);
			assertEquals(xor[3],2);
			assertEquals(xor[4],5);
		}catch(Exception x){
			fail(x.getMessage());
		}
		try{
			byte a[]=new byte[]{0,1,2,3,4};
			byte b[]=new byte[]{1,1,1,1};
			ArrayUtil.xorBytes(a,b);
			fail("This test should have failed.");
		}catch(Exception x){/*expected*/}		
		try{
			byte a[]=new byte[]{0,1,2,3};
			byte b[]=new byte[]{1,1,1,1,1};
			ArrayUtil.xorBytes(a,b);
			fail("This test should have failed.");
		}catch(Exception x){/*expected*/}		
	}

	@Test 
	public void test_pickIndex(){
		try{
			for(int max=1;max<10;max++){
				for(int i=0;i<20;i++){
					int index=ArrayUtil.pickIndex(i,max);
					if(i==max){
						assertEquals(index,0);
					}else if(i<max){
						assertEquals(index,i);
					}else{//i>max
						assertEquals(index,(i%max));
					}
				}
			}
		}catch(Exception x){
			x.printStackTrace();
			fail(x.getMessage());
		}
	}

	@Test 
	public void test_pickIndexWithNegatives(){
		try{
			ArrayUtil.pickIndex(-1,1);
			fail("This test should have failed.");
		}catch(Exception x){/*expected*/}		
		try{
			ArrayUtil.pickIndex(1,-1);
			fail("This test should have failed.");
		}catch(Exception x){/*expected*/}		
	}
	
	@Test
	public void test_maxElementIndex(){
		int maxIndex=ArrayUtil.maxElementIndex(new double[]{1.0d,2.0d,3.0d,4.0d,5.0d});
		assertEquals(4,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{1.0d,-2.0d,-3.0d,-4.0d,-5.0d});
		assertEquals(0,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{-1.0d,-2.0d,-3.0d,-4.0d,-5.0d});
		assertEquals(0,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{-1.0d,-2.0d,-3.0d,-4.0d,-5.0d});
		assertEquals(0,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{-1.0d,2.0d,-3.0d,-4.0d,-5.0d});
		assertEquals(1,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{-1.0d,-2.0d,3.0d,-4.0d,-5.0d});
		assertEquals(2,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{-1.0d,-2.0d,-3.0d,4.0d,-5.0d});
		assertEquals(3,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{-1.0d,-2.0d,-3.0d,-4.0d,5.0d});
		assertEquals(4,maxIndex);
		maxIndex=ArrayUtil.maxElementIndex(new double[]{0.9d,0.01d,1.0d,0.9999d,0.999999d});
		assertEquals(2,maxIndex);
	}
	
	@Test
	public void test_pickChar(){
		ArrayList<Character> list=new ArrayList<Character>();
		char[] chars={'q','w','e','r','t','y'};
		char c=ArrayUtil.pickChar(list,0,false,chars);
		assertEquals(list.get(0).charValue(),c);
		for(int i=0;i<chars.length;i++){
			assertEquals(list.get(i).charValue(),chars[i]);
		}
		c=ArrayUtil.pickChar(list,1,true,chars);
		assertEquals('w',c); //now {'q','e','r','t','y'}
		c=ArrayUtil.pickChar(list,1,true,chars);
		assertEquals('e',c); //now {'q','r','t','y'}
		c=ArrayUtil.pickChar(list,11,true,chars);
		assertEquals('y',c); //now {'q','r','t'}
		c=ArrayUtil.pickChar(list,0,true,chars);
		assertEquals('q',c); //now {'r','t'}
		c=ArrayUtil.pickChar(list,8,true,chars);
		assertEquals('r',c); //now {'t'}
		c=ArrayUtil.pickChar(list,6489,true,chars);
		assertEquals('t',c); //now {''}
		assertEquals(0,list.size()); //now {''}
		c=ArrayUtil.pickChar(list,5,false,chars);
		assertEquals(list.get(5).charValue(),c);
		for(int i=0;i<chars.length;i++){
			assertEquals(list.get(i).charValue(),chars[i]);
		}
	}
	
	@Test
	public void test_fillArrayList(){	
		char[] chars={'b','l','a','h'};
		ArrayList<Character> list=new ArrayList<Character>();
		ArrayUtil.fillArrayList(list,chars);
		assertEquals(list.size(),chars.length);
		for(int i=0;i<chars.length;i++){
			assertEquals(list.get(i).charValue(),chars[i]);
		}
	}
	
	@Test
	public void test_findDuplicateRows(){
		ArrayList<String> a1=new ArrayList<String>();
		a1.add("qqqq");
		a1.add("wwww");
		a1.add("eeee");
		a1.add("rrrr");
		a1.add("tttt");
		a1.add("yyyy");
		a1.add("uuuu");
		a1.add("iiii");
		a1.add("oooo");
		a1.add("pppp");
		ArrayList<String> a2=new ArrayList<String>();
		a2.add("aaaa");
		a2.add("ssss");
		a2.add("dddd");
		a2.add("ffff");
		a2.add("gggg");
		a2.add("hhhh");
		a2.add("jjjj");
		a2.add("kkkk");
		a2.add("llll");
		a1.add("zzzz");
		a2.add("zzzz");
		a1.add("xxxx");
		a2.add("xxxx");
		a1.add("cccc");
		a2.add("cccc");
		a1.add("vvvv");
		a2.add("vvvv");
		a1.add("bbbb");
		a2.add("bbbb");
		a1.add("nnnn");
		a2.add("nnnn");
		a1.add("mmmm");
		a2.add("mmmm");
		a1.sort(new StringComparator());
		a2.sort(new StringComparator());
		ArrayList<String> duplicates=ArrayUtil.findDuplicateRows(a1,a2);
		assertEquals(7,duplicates.size());
		assertTrue(duplicates.contains("zzzz"));
		assertTrue(duplicates.contains("xxxx"));
		assertTrue(duplicates.contains("cccc"));
		assertTrue(duplicates.contains("vvvv"));
		assertTrue(duplicates.contains("bbbb"));
		assertTrue(duplicates.contains("nnnn"));
		assertTrue(duplicates.contains("mmmm"));
		duplicates=ArrayUtil.findDuplicateRows(a2,a1);
		assertEquals(7,duplicates.size());
		assertTrue(duplicates.contains("zzzz"));
		assertTrue(duplicates.contains("xxxx"));
		assertTrue(duplicates.contains("cccc"));
		assertTrue(duplicates.contains("vvvv"));
		assertTrue(duplicates.contains("bbbb"));
		assertTrue(duplicates.contains("nnnn"));
		assertTrue(duplicates.contains("mmmm"));
	}
}