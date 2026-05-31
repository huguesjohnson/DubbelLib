/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.util.StringUtil;

class TestStringUtil{

	@Test
	void testStripHtmlTags(){
		assertEquals("text",StringUtil.stripHtmlTags("text"));
		assertEquals(" | text",StringUtil.stripHtmlTags("<th>text</th>"));
		assertEquals(" | text | text",StringUtil.stripHtmlTags("<th>text</th><th>text</th>"));
		assertEquals(" | text",StringUtil.stripHtmlTags("<td>text</td>"));
		assertEquals(" | text | text",StringUtil.stripHtmlTags("<td>text</td><td>text</td>"));
		assertEquals("text |\n",StringUtil.stripHtmlTags("<tr>text</tr>"));
		assertEquals("\n• text",StringUtil.stripHtmlTags("<li>text</li>"));
		assertEquals("",StringUtil.stripHtmlTags("<br>"));
		assertEquals("",StringUtil.stripHtmlTags("<br class='whatever'>"));
		assertEquals("\ntext\n",StringUtil.stripHtmlTags("<h1>text</h1>"));
		assertEquals("\ntext\n",StringUtil.stripHtmlTags("<h2>text</h2>"));
		assertEquals("\ntext\n",StringUtil.stripHtmlTags("<h3>text</h3>"));
		assertEquals("\ntext\n",StringUtil.stripHtmlTags("<h4>text</h4>"));
		assertEquals("\ntext\n",StringUtil.stripHtmlTags("<h5>text</h5>"));
		assertEquals("\ntext\n",StringUtil.stripHtmlTags("<h6>text</h6>"));
		assertEquals(" text",StringUtil.stripHtmlTags("\ttext"));
		assertEquals(" text",StringUtil.stripHtmlTags("&nbsp;text"));
		assertEquals(" text ",StringUtil.stripHtmlTags("&nbsp;text&nbsp;"));
		assertEquals("&text",StringUtil.stripHtmlTags("&amp;text"));
		assertEquals("&text&",StringUtil.stripHtmlTags("&amp;text&amp;"));
		assertEquals("\"text\"",StringUtil.stripHtmlTags("&quot;text&quot;"));
		assertEquals("<text",StringUtil.stripHtmlTags("&lt;text"));
		assertEquals("<text<",StringUtil.stripHtmlTags("&lt;text&lt;"));
		assertEquals(">text",StringUtil.stripHtmlTags("&gt;text"));
		assertEquals(">text>",StringUtil.stripHtmlTags("&gt;text&gt;"));
		assertEquals("'text",StringUtil.stripHtmlTags("&apos;text"));
		assertEquals("'text'",StringUtil.stripHtmlTags("&apos;text&apos;"));
	}
	
	@Test
	void testParseHref(){
		/*
		 * single href in string
		 */
		ArrayList<String> hrefs=StringUtil.parseHref("<a href=\"https://example.com/\">whatever</a>");
		assertEquals(1,hrefs.size());
		assertEquals("https://example.com/",hrefs.get(0));
		hrefs=StringUtil.parseHref("<a href=\"https://example.com\">whatever</a>");
		assertEquals(1,hrefs.size());
		assertEquals("https://example.com",hrefs.get(0));
		hrefs=StringUtil.parseHref("<a href=\"https://eXample.com/\">whatever</a>");
		assertEquals(1,hrefs.size());
		assertEquals("https://eXample.com/",hrefs.get(0));
		hrefs=StringUtil.parseHref("<a href=\"http://example.com/whatever/\">whatever</a>");
		assertEquals(1,hrefs.size());
		assertEquals("http://example.com/whatever/",hrefs.get(0));
		hrefs=StringUtil.parseHref("<a href=\"https://example.com/whatever.html\">whatever</a>");
		assertEquals(1,hrefs.size());
		assertEquals("https://example.com/whatever.html",hrefs.get(0));
		/*
		 * no href in string
		 */
		hrefs=StringUtil.parseHref("whatever");
		assertEquals(0,hrefs.size());
		hrefs=StringUtil.parseHref("<a href=\"https://example.com/>whatever</a>");
		assertEquals(0,hrefs.size());
		/*
		 * multiple href in string
		 */
		hrefs=StringUtil.parseHref("<a href=\"https://example.com/\">whatever</a>blah<a href=\"https://eXample.com/\">whatever</a>blah");
		assertEquals(2,hrefs.size());
		assertEquals("https://example.com/",hrefs.get(0));
		assertEquals("https://eXample.com/",hrefs.get(1));
		hrefs=StringUtil.parseHref("<a href=\"https://example.com/\">whatever</a><a href=\"https://eXample.com/\">whatever</a>");
		assertEquals(2,hrefs.size());
		assertEquals("https://example.com/",hrefs.get(0));
		assertEquals("https://eXample.com/",hrefs.get(1));
	}
	
	@Test
	void testEnsureEndsWith(){
		assertEquals(null,StringUtil.ensureEndsWith(null,null));
		assertEquals("",StringUtil.ensureEndsWith("",null));
		assertEquals(null,StringUtil.ensureEndsWith(null,""));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwerty","qwerty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwerty","werty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwerty","erty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwerty","rty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwerty","ty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwerty","y"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwert","y"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwer","ty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qwe","rty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("qw","erty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("q","werty"));
		assertEquals("qwerty",StringUtil.ensureEndsWith("","qwerty"));
	}	
	
}