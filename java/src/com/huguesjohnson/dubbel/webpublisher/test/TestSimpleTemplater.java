/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.webpublisher.SimpleTemplater;

class TestSimpleTemplater{
	@Test
	void testStaticTemplates(){
		//setup some data
		Map<String,String> staticTemplates=new HashMap<String,String>();
		staticTemplates.put("{CSS_VERSION}","533");
		staticTemplates.put("{CSS_OVERRIDE}","2024-04-01");
		staticTemplates.put("{CAROUSEL_VERSION}","2020-08-07");
		//setup templater
		SimpleTemplater templater=new SimpleTemplater(staticTemplates);
		//simple tests
		String templateString="{CSS_VERSION}";
		String expectedResult="533";
		String resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="{CSS_VERSION";
		expectedResult="{CSS_VERSION";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="{XSS_VERSION}";
		expectedResult="{XSS_VERSION}";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="blah{CSS_VERSION}blah";
		expectedResult="blah533blah";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="{CSS_OVERRIDE}";
		expectedResult="2024-04-01";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="{CAROUSEL_VERSION}";
		expectedResult="2020-08-07";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		//longer tests
		templateString="blah{CSS_VERSION}blah{CSS_OVERRIDE}blah{CAROUSEL_VERSION}";
		expectedResult="blah533blah2024-04-01blah2020-08-07";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="blah{CSS_VERSIONblah{CSS_OVERRIDE}blah{CAROUSEL_VERSION}";
		expectedResult="blah{CSS_VERSIONblah2024-04-01blah2020-08-07";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="blah{CSS_VERSION}blahCSS_OVERRIDE}blah{CAROUSEL_VERSION}";
		expectedResult="blah533blahCSS_OVERRIDE}blah2020-08-07";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		//oddball tests
		templateString="blah{{CSS_VERSION}blah";
		expectedResult="blah{533blah";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="blah{CSS_VERSION}}blah";
		expectedResult="blah533}blah";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		templateString="blah{{CSS_VERSION}}blah";
		expectedResult="blah{533}blah";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
		//test what I actually use this class for
		templateString="<link href=\"https://huguesjohnson.com/bootstrap/{CSS_VERSION}/carousel-{CAROUSEL_VERSION}.css\" rel=\"stylesheet\">\n<link href=\"https://huguesjohnson.com/bootstrap/{CSS_VERSION}/carousel-override-{CAROUSEL_VERSION}.css\" rel=\"stylesheet\">";
		expectedResult="<link href=\"https://huguesjohnson.com/bootstrap/533/carousel-2020-08-07.css\" rel=\"stylesheet\">\n<link href=\"https://huguesjohnson.com/bootstrap/533/carousel-override-2020-08-07.css\" rel=\"stylesheet\">";
		resultString=templater.process(templateString);
		assertEquals(expectedResult,resultString);
	}
}
