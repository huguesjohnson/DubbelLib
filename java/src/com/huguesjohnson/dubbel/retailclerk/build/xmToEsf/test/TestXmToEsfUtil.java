/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.xmToEsf.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.retailclerk.build.xmToEsf.XmToEsfUtil;

class TestXmToEsfUtil{

	@Test
	void testCalculateQuotient(){
		assertEquals(0.0,XmToEsfUtil.calculateQuotient(0));
		assertEquals(0.0,XmToEsfUtil.calculateQuotient(-1));
		assertEquals(1.0,XmToEsfUtil.calculateQuotient(64));
		assertEquals(0.75,XmToEsfUtil.calculateQuotient(48));
		assertEquals(2.0,XmToEsfUtil.calculateQuotient(128));
	}
	
	@Test
	void testCalculatePSGVolume(){
		assertEquals(0,XmToEsfUtil.calculatePSGVolume(64));
		assertEquals(15,XmToEsfUtil.calculatePSGVolume(0));
		assertEquals(0,XmToEsfUtil.calculatePSGVolume(48));
	}

	@Test
	void testCalculateDivRest(){
		assertEquals(0.0,XmToEsfUtil.calculateDivRest(0.0,0));
		assertEquals(0.0,XmToEsfUtil.calculateDivRest(0.0,12));
		assertEquals(2.0,XmToEsfUtil.calculateDivRest(14.0,12));
		assertEquals(8.0,XmToEsfUtil.calculateDivRest(212.0,12));
	}

	/*
	 * This produces slightly different results than what I would expect based on the ESF documentation.
	 * See - https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt
	 * The original implementation does not have unit tests so I'm uncertain if this is:
	 * 	A) how the original code works
	 *  B) a conversion problem
	 *  C) my complete inability to predict the expected values
	 */
	@Test
	void testCalculateFmFrequency(){
		assertEquals(644,XmToEsfUtil.calculateFmFrequency(0.0));
		assertEquals(682,XmToEsfUtil.calculateFmFrequency(1.0));
		assertEquals(722,XmToEsfUtil.calculateFmFrequency(2.0));
		assertEquals(765,XmToEsfUtil.calculateFmFrequency(3.0));
		assertEquals(811,XmToEsfUtil.calculateFmFrequency(4.0));
		assertEquals(859,XmToEsfUtil.calculateFmFrequency(5.0));
		assertEquals(910,XmToEsfUtil.calculateFmFrequency(6.0));
		assertEquals(964,XmToEsfUtil.calculateFmFrequency(7.0));
		assertEquals(1022,XmToEsfUtil.calculateFmFrequency(8.0));
		assertEquals(1083,XmToEsfUtil.calculateFmFrequency(9.0));
		assertEquals(1147,XmToEsfUtil.calculateFmFrequency(10.0));
		assertEquals(1215,XmToEsfUtil.calculateFmFrequency(11.0));
	}

	@Test
	void testCalculateFmVolume(){
		assertEquals(127,XmToEsfUtil.calculateFmVolume(0.0));
		assertEquals(0,XmToEsfUtil.calculateFmVolume(128.0));
		assertEquals(0,XmToEsfUtil.calculateFmVolume(64.0));
		assertEquals(6,XmToEsfUtil.calculateFmVolume(48.0));
		assertEquals(38,XmToEsfUtil.calculateFmVolume(12.0));
		assertEquals(48,XmToEsfUtil.calculateFmVolume(8.0));
		assertEquals(64,XmToEsfUtil.calculateFmVolume(4.0));
		assertEquals(80,XmToEsfUtil.calculateFmVolume(2.0));
		assertEquals(96,XmToEsfUtil.calculateFmVolume(1.0));
	}
}