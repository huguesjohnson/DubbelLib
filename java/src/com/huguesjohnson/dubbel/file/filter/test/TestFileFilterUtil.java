/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.file.filter.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.file.filter.FileFilterUtil;

class TestFileFilterUtil{

	/*
	 * Does not test hidden files or directories though
	 * AKA "the things that are usually problems"
	 */
	@Test
	void test(){
		String[] validExtensions={"ABC","123","xyz"};
		//null cases
		assertFalse(FileFilterUtil.accept(null,validExtensions));
		assertFalse(FileFilterUtil.accept((new File("test.ABC")),new String[0]));
		//valid cases
		assertTrue(FileFilterUtil.accept((new File("test.ABC")),validExtensions));
		assertTrue(FileFilterUtil.accept((new File("test.abc")),validExtensions));
		assertTrue(FileFilterUtil.accept((new File("test.123")),validExtensions));
		assertTrue(FileFilterUtil.accept((new File("test.XyZ")),validExtensions));
		assertTrue(FileFilterUtil.accept((new File("test.1.abc")),validExtensions));
		assertTrue(FileFilterUtil.accept((new File("test.ABC")),"abc"));
		assertTrue(FileFilterUtil.accept((new File("test.abc")),"abc"));
		assertTrue(FileFilterUtil.accept((new File("test.123")),"123"));
		assertTrue(FileFilterUtil.accept((new File("test.XyZ")),"XYZ"));
		assertTrue(FileFilterUtil.accept((new File("test.1.abc")),"abc"));
		//invalid cases
		assertFalse(FileFilterUtil.accept((new File("test.whatever")),validExtensions));
		assertFalse(FileFilterUtil.accept((new File("test.abc.backup")),validExtensions));
		assertFalse(FileFilterUtil.accept((new File(".abc")),validExtensions));
		assertFalse(FileFilterUtil.accept((new File("123.")),validExtensions));
		assertFalse(FileFilterUtil.accept((new File("test.123.")),validExtensions));
		assertFalse(FileFilterUtil.accept((new File("test.")),validExtensions));
		assertFalse(FileFilterUtil.accept((new File("test.whatever")),"abc"));
		assertFalse(FileFilterUtil.accept((new File("test.abc.backup")),"abc"));
		assertFalse(FileFilterUtil.accept((new File(".abc")),"abc"));
		assertFalse(FileFilterUtil.accept((new File("123.")),"123"));
		assertFalse(FileFilterUtil.accept((new File("test.123.")),"123"));
		assertFalse(FileFilterUtil.accept((new File("test.")),""));
	}

}