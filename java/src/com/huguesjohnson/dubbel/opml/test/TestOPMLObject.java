package com.huguesjohnson.dubbel.opml.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.opml.OPMLObject;
import com.huguesjohnson.dubbel.opml.OPMLParser;

class TestOPMLObject{

	@Test
	void testToXml(){
		try{
			String originalOPML="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
					"<opml version=\"2.0\">\n" + 
					"<head>\n" + 
					"<title>Example subscriptions in feedly</title>\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"<outline text=\"Video Game Music\" title=\"Video Game Music\">\n" + 
					"<outline type=\"rss\" text=\"10 Latest OverClocked ReMixes\" title=\"10 Latest OverClocked ReMixes\" xmlUrl=\"http://ocremix.org/feeds/ten20/\" htmlUrl=\"https://ocremix.org\"/>\n" + 
					"<outline type=\"rss\" text=\"Nerd Noise Radio\" title=\"Nerd Noise Radio\" xmlUrl=\"https://feed.podbean.com/nerdnoiseradio/feed.xml\" htmlUrl=\"https://nerdnoiseradio.podbean.com\"/>\n" + 
					"</outline>\n" + 
					"<outline text=\"Retro Technology\" title=\"Retro Technology\">\n" + 
					"<outline type=\"rss\" text=\"The Old New Thing\" title=\"The Old New Thing\" xmlUrl=\"http://blogs.msdn.com/b/oldnewthing/rss.aspx\" htmlUrl=\"https://devblogs.microsoft.com/oldnewthing\"/>\n" + 
					"<outline type=\"rss\" text=\"Atari Projects\" title=\"Atari Projects\" xmlUrl=\"http://atariprojects.org/feed/\" htmlUrl=\"https://atariprojects.org\"/>\n" + 
					"</outline>\n" + 
					"<outline text=\"Retro Gaming\" title=\"Retro Gaming\">\n" + 
					"<outline type=\"rss\" text=\"Nerdly Pleasures\" title=\"Nerdly Pleasures\" xmlUrl=\"http://feeds.feedburner.com/NerdlyPleasures\" htmlUrl=\"http://nerdlypleasures.blogspot.com/\"/>\n" + 
					"<outline type=\"rss\" text=\"Nicole Express\" title=\"Nicole Express\" xmlUrl=\"https://nicole.express/feed.xml\" htmlUrl=\"https://nicole.express/\"/>\n" + 
					"<outline type=\"rss\" text=\"Midwest Gaming Classic\" title=\"Midwest Gaming Classic\" xmlUrl=\"http://www.midwestgamingclassic.com/feed/\" htmlUrl=\"https://www.midwestgamingclassic.com\"/>\n" + 
					"</outline>\n" + 
					"</body>\n" + 
					"</opml>";
			InputStream in=new ByteArrayInputStream(originalOPML.getBytes(StandardCharsets.UTF_8));
			OPMLObject opml=OPMLParser.parse(in);
			String xml=opml.toXml();
			//does this parse back to a valid object?
			in=new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
			OPMLObject testOpml=OPMLParser.parse(in);
			assertEquals(testOpml.getVersion(),"2.0");
			assertEquals(testOpml.getHead().getTitle(),"Example subscriptions in feedly");
			assertEquals(testOpml.getBody().get(0).getText(),"Video Game Music");
			assertEquals(testOpml.getBody().get(0).getTitle(),"Video Game Music");
			assertEquals(testOpml.getBody().get(0).getChildren().get(0).getTitle(),"10 Latest OverClocked ReMixes");
			assertEquals(testOpml.getBody().get(0).getChildren().get(0).getText(),"10 Latest OverClocked ReMixes");
			assertEquals(testOpml.getBody().get(0).getChildren().get(0).getType(),"rss");
			assertEquals(testOpml.getBody().get(0).getChildren().get(0).getXmlUrl(),"http://ocremix.org/feeds/ten20/");
			assertEquals(testOpml.getBody().get(0).getChildren().get(0).getHtmlUrl(),"https://ocremix.org");
			assertEquals(testOpml.getBody().get(0).getChildren().get(1).getTitle(),"Nerd Noise Radio");
			assertEquals(testOpml.getBody().get(0).getChildren().get(1).getText(),"Nerd Noise Radio");
			assertEquals(testOpml.getBody().get(0).getChildren().get(1).getType(),"rss");
			assertEquals(testOpml.getBody().get(0).getChildren().get(1).getXmlUrl(),"https://feed.podbean.com/nerdnoiseradio/feed.xml");
			assertEquals(testOpml.getBody().get(0).getChildren().get(1).getHtmlUrl(),"https://nerdnoiseradio.podbean.com");
			assertEquals(testOpml.getBody().get(1).getText(),"Retro Technology");
			assertEquals(testOpml.getBody().get(1).getTitle(),"Retro Technology");
			assertEquals(testOpml.getBody().get(1).getChildren().get(0).getTitle(),"The Old New Thing");
			assertEquals(testOpml.getBody().get(1).getChildren().get(0).getText(),"The Old New Thing");
			assertEquals(testOpml.getBody().get(1).getChildren().get(0).getType(),"rss");
			assertEquals(testOpml.getBody().get(1).getChildren().get(0).getXmlUrl(),"http://blogs.msdn.com/b/oldnewthing/rss.aspx");
			assertEquals(testOpml.getBody().get(1).getChildren().get(0).getHtmlUrl(),"https://devblogs.microsoft.com/oldnewthing");
			assertEquals(testOpml.getBody().get(1).getChildren().get(1).getTitle(),"Atari Projects");
			assertEquals(testOpml.getBody().get(1).getChildren().get(1).getText(),"Atari Projects");
			assertEquals(testOpml.getBody().get(1).getChildren().get(1).getType(),"rss");
			assertEquals(testOpml.getBody().get(1).getChildren().get(1).getXmlUrl(),"http://atariprojects.org/feed/");
			assertEquals(testOpml.getBody().get(1).getChildren().get(1).getHtmlUrl(),"https://atariprojects.org");
			assertEquals(testOpml.getBody().get(2).getText(),"Retro Gaming");
			assertEquals(testOpml.getBody().get(2).getTitle(),"Retro Gaming");
			assertEquals(testOpml.getBody().get(2).getChildren().get(0).getTitle(),"Nerdly Pleasures");
			assertEquals(testOpml.getBody().get(2).getChildren().get(0).getText(),"Nerdly Pleasures");
			assertEquals(testOpml.getBody().get(2).getChildren().get(0).getType(),"rss");
			assertEquals(testOpml.getBody().get(2).getChildren().get(0).getXmlUrl(),"http://feeds.feedburner.com/NerdlyPleasures");
			assertEquals(testOpml.getBody().get(2).getChildren().get(0).getHtmlUrl(),"http://nerdlypleasures.blogspot.com/");
			assertEquals(testOpml.getBody().get(2).getChildren().get(1).getTitle(),"Nicole Express");
			assertEquals(testOpml.getBody().get(2).getChildren().get(1).getText(),"Nicole Express");
			assertEquals(testOpml.getBody().get(2).getChildren().get(1).getType(),"rss");
			assertEquals(testOpml.getBody().get(2).getChildren().get(1).getXmlUrl(),"https://nicole.express/feed.xml");
			assertEquals(testOpml.getBody().get(2).getChildren().get(1).getHtmlUrl(),"https://nicole.express/");
			assertEquals(testOpml.getBody().get(2).getChildren().get(2).getTitle(),"Midwest Gaming Classic");
			assertEquals(testOpml.getBody().get(2).getChildren().get(2).getText(),"Midwest Gaming Classic");
			assertEquals(testOpml.getBody().get(2).getChildren().get(2).getType(),"rss");
			assertEquals(testOpml.getBody().get(2).getChildren().get(2).getXmlUrl(),"http://www.midwestgamingclassic.com/feed/");
			assertEquals(testOpml.getBody().get(2).getChildren().get(2).getHtmlUrl(),"https://www.midwestgamingclassic.com");
			//OK a simple case passed - should add more complex cases next
		}catch(Exception x){
			fail(x);
		}
	}
}