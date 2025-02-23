package com.huguesjohnson.dubbel.opml.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.opml.OPMLObject;
import com.huguesjohnson.dubbel.opml.OPMLOutline;
import com.huguesjohnson.dubbel.opml.OPMLParser;

class TestOPMLObject{

	@Test
	void testToXml(){
		//this also inadvertently tests OPMLParser and I'm aware it's not a great idea to combine these things
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
			//OK a simple but common cast passed - let's make sure every field works even though that would be an edge case
			OPMLObject testObject=new OPMLObject();
			testObject.setVersion("version");
			testObject.getHead().setDateCreated("Sun, 04 Jun 1989 13:06:06 CDT");
			testObject.getHead().setDateModified("Sun, 04 Jun 1989 13:13:13 CDT");
			testObject.getHead().setDocs("docs");
			testObject.getHead().setExpansionState("expansionState");
			testObject.getHead().setOwnerEmail("ownerEmail");
			testObject.getHead().setOwnerId("ownerId");
			testObject.getHead().setOwnerName("ownerName");
			testObject.getHead().setTitle("title");
			testObject.getHead().setVertScrollState(Integer.valueOf(666));
			testObject.getHead().setWindowBottom(Integer.valueOf(616));
			testObject.getHead().setWindowTop(Integer.valueOf(616));
			testObject.getHead().setWindowRight(Integer.valueOf(13));
			testObject.getHead().setWindowLeft(Integer.valueOf(6489));
			OPMLOutline outline=new OPMLOutline();
			outline.setBreakpoint(Boolean.FALSE);
			outline.setCategory("category");
			outline.setComment(Boolean.FALSE);
			outline.setCreated("Sun, 04 Jun 1989 13:06:06 CDT");
			outline.setDescription("description");
			outline.setHtmlUrl("htmlUrl");
			outline.setLanguage("language");
			outline.setText("text");
			outline.setTitle("title");
			outline.setType("type");
			outline.setUrl("url");
			outline.setVersion("version");
			outline.setXmlUrl("xmlUrl");
			testObject.getBody().add(outline);
			xml=testObject.toXml();
			in=new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
			testOpml=OPMLParser.parse(in);
			assertEquals(testOpml.getVersion(),"version");
			assertEquals(testOpml.getHead().getDateCreated(),"Sun, 04 Jun 1989 13:06:06 CDT");
			assertEquals(testOpml.getHead().getDateModified(),"Sun, 04 Jun 1989 13:13:13 CDT");
			assertEquals(testOpml.getHead().getDocs(),"docs");
			assertEquals(testOpml.getHead().getExpansionState(),"expansionState");
			assertEquals(testOpml.getHead().getOwnerEmail(),"ownerEmail");
			assertEquals(testOpml.getHead().getOwnerId(),"ownerId");
			assertEquals(testOpml.getHead().getOwnerName(),"ownerName");
			assertEquals(testOpml.getHead().getTitle(),"title");
			assertEquals(testOpml.getHead().getVertScrollState().intValue(),666);
			assertEquals(testOpml.getHead().getWindowBottom().intValue(),616);
			assertEquals(testOpml.getHead().getWindowTop().intValue(),616);
			assertEquals(testOpml.getHead().getWindowRight().intValue(),13);
			assertEquals(testOpml.getHead().getWindowLeft().intValue(),6489);
			assertEquals(testOpml.getBody().get(0).isBreakpoint(),Boolean.FALSE);
			assertEquals(testOpml.getBody().get(0).isComment(),Boolean.FALSE);
			assertEquals(testOpml.getBody().get(0).getCategory(),"category");
			assertEquals(testOpml.getBody().get(0).getCreated(),"Sun, 04 Jun 1989 13:06:06 CDT");
			assertEquals(testOpml.getBody().get(0).getDescription(),"description");
			assertEquals(testOpml.getBody().get(0).getHtmlUrl(),"htmlUrl");
			assertEquals(testOpml.getBody().get(0).getXmlUrl(),"xmlUrl");
			assertEquals(testOpml.getBody().get(0).getUrl(),"url");
			assertEquals(testOpml.getBody().get(0).getLanguage(),"language");
			assertEquals(testOpml.getBody().get(0).getText(),"text");
			assertEquals(testOpml.getBody().get(0).getTitle(),"title");
			assertEquals(testOpml.getBody().get(0).getType(),"type");
			assertEquals(testOpml.getBody().get(0).getVersion(),"version");
		}catch(Exception x){
			fail(x);
		}
	}
}