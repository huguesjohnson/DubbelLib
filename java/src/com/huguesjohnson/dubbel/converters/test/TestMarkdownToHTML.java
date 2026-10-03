/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.converters.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.converters.MarkdownToHTML;

class TestMarkdownToHTML{
	@Test
	void testConvert(){
		String md="This is an example document with all the formatting I want to convert. This is the first paragraph.\n"
				+ "\n"
				+ "This is the second paragraph.\n"
				+ "\n"
				+ "This is [a link](https://huguesjohnson.com/).\n"
				+ "\n"
				+ "This is an ampersand \\&.\n"
				+ "\n"
				+ "Here is some text in quotes with italics “*blah blah blah*”.\n"
				+ "\n"
				+ "Here’s some text with an apostrophe.\n"
				+ "\n"
				+ "## **Here’s an h2 line**\n"
				+ "\n"
				+ "Here are some greater than \\> and less than \\< symbols.\n"
				+ "\n"
				+ "Here is a bulleted list:\n"
				+ "\n"
				+ "* Bullet 1\n"
				+ "* Bullet 2\n"
				+ "\n"
				+ "Here is a numbered list:\n"
				+ "\n"
				+ "1. Item 1\n"
				+ "2. Item 2\n"
				+ "\n"
				+ "I think that is everything.";
		
		String html="<p>This is an example document with all the formatting I want to convert. This is the first paragraph.</p>\n"
				+ "\n"
				+ "<p>This is the second paragraph.</p>\n"
				+ "\n"
				+ "<p>This is <a href=\"https://huguesjohnson.com/\">a link</a>.</p>\n"
				+ "\n"
				+ "<p>This is an ampersand &amp;.</p>\n"
				+ "\n"
				+ "<p>Here is some text in quotes with italics &quot;<i>blah blah blah</i>&quot;.</p>\n"
				+ "\n"
				+ "<p>Here's some text with an apostrophe.</p>\n"
				+ "\n"
				+ "<h2>Here's an h2 line</h2>\n"
				+ "\n"
				+ "<p>Here are some greater than &gt; and less than &lt; symbols.</p>\n"
				+ "\n"
				+ "<p>Here is a bulleted list:</p>\n"
				+ "\n"
				+ "<ul>\n"
				+ "<li>Bullet 1</li>\n"
				+ "<li>Bullet 2</li>\n"
				+ "</ul>\n"
				+ "\n"
				+ "<p>Here is a numbered list:</p>\n"
				+ "\n"
				+ "<ol>\n"
				+ "<li>Item 1</li>\n"
				+ "<li>Item 2</li>\n"
				+ "</ol>\n"
				+ "\n"
				+ "<p>I think that is everything.</p>";
		
		assertEquals(html,MarkdownToHTML.convert(md));
	}
}