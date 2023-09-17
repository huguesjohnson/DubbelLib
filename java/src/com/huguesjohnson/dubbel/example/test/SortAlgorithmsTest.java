/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.example.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.example.SortAlgorithms;

class SortAlgorithmsTest{

	@Test
	void testInsertionSort(){
		int[] arrayToSort={592,550,838,25,945,550,789,86,945,134,240,13};
		SortAlgorithms.insertionSort(arrayToSort);
		assertEquals(arrayToSort[0],13);
		assertEquals(arrayToSort[1],25);
		assertEquals(arrayToSort[2],86);
		assertEquals(arrayToSort[3],134);
		assertEquals(arrayToSort[4],240);
		assertEquals(arrayToSort[5],550);
		assertEquals(arrayToSort[6],550);
		assertEquals(arrayToSort[7],592);
		assertEquals(arrayToSort[8],789);
		assertEquals(arrayToSort[9],838);
		assertEquals(arrayToSort[10],945);
		assertEquals(arrayToSort[11],945);
	}
	
	@Test
	void testMergeSort(){
		int[] arrayToSort={349,388,866,844,362,904,513,388,60,181,41,52};
		SortAlgorithms.mergeSort(arrayToSort);
		assertEquals(arrayToSort[0],41);
		assertEquals(arrayToSort[1],52);
		assertEquals(arrayToSort[2],60);
		assertEquals(arrayToSort[3],181);
		assertEquals(arrayToSort[4],349);
		assertEquals(arrayToSort[5],362);
		assertEquals(arrayToSort[6],388);
		assertEquals(arrayToSort[7],388);
		assertEquals(arrayToSort[8],513);
		assertEquals(arrayToSort[9],844);
		assertEquals(arrayToSort[10],866);
		assertEquals(arrayToSort[11],904);
	}	

}