/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util.test;

import static org.junit.jupiter.api.Assertions.*;

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
}