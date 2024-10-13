/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.file.PathResolver;

class TestPathResolver{

	@Test
	void testGetRelativePath(){
		String absolutePath1="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR;
		String absolutePath2="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		String relativePath=PathResolver.getRelativePath(absolutePath1,absolutePath2);
		String expectedRelativePath=PathResolver.SELF_PATH+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		assertEquals(expectedRelativePath,relativePath);
		
		absolutePath1="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR;
		absolutePath2="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR+"file.txt";
		relativePath=PathResolver.getRelativePath(absolutePath1,absolutePath2);
		expectedRelativePath=PathResolver.SELF_PATH+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR+"file.txt";
		assertEquals(expectedRelativePath,relativePath);

		absolutePath1="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		absolutePath2="c:"+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR;
		relativePath=PathResolver.getRelativePath(absolutePath1,absolutePath2);
		expectedRelativePath=PathResolver.PARENT_PATH+PathResolver.SEPARATOR+PathResolver.PARENT_PATH+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR;
		assertEquals(expectedRelativePath,relativePath);

		absolutePath1="drive-c"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR;
		absolutePath2="drive-d"+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR;
		relativePath=PathResolver.getRelativePath(absolutePath1,absolutePath2);
		expectedRelativePath="drive-d"+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR;
		assertEquals(expectedRelativePath,relativePath);
	}
	
	@Test
	void testGetAbsolutePath(){
		String absolutePath="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR;
		String relativePath=PathResolver.SELF_PATH+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		String expectedResult="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		String actualResult=PathResolver.getAbsolutePath(absolutePath,relativePath);
		assertEquals(expectedResult,actualResult);

		absolutePath="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR;
		relativePath=PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		expectedResult="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		actualResult=PathResolver.getAbsolutePath(absolutePath,relativePath);
		assertEquals(expectedResult,actualResult);

		absolutePath="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR;
		relativePath=PathResolver.PARENT_PATH+PathResolver.SEPARATOR+PathResolver.PARENT_PATH+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR;
		expectedResult="c:"+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR;
		actualResult=PathResolver.getAbsolutePath(absolutePath,relativePath);
		assertEquals(expectedResult,actualResult);

		absolutePath="c:"+PathResolver.SEPARATOR+"folder1"+PathResolver.SEPARATOR+"sub-folder1"+PathResolver.SEPARATOR;
		relativePath=PathResolver.PARENT_PATH+PathResolver.SEPARATOR+PathResolver.PARENT_PATH+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR+"file.txt";
		expectedResult="c:"+PathResolver.SEPARATOR+"folder2"+PathResolver.SEPARATOR+"file.txt";
		actualResult=PathResolver.getAbsolutePath(absolutePath,relativePath);
		assertEquals(expectedResult,actualResult);
	}	

}