/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.opml.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.opml.OPMLHead;
import com.huguesjohnson.dubbel.opml.OPMLObject;
import com.huguesjohnson.dubbel.opml.OPMLOutline;
import com.huguesjohnson.dubbel.opml.OPMLParser;

class TestOPMLParser {
	//based on - http://hosting.opml.org/dave/spec/subscriptionList.opml
	private final static String subscriptionExample="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + 
			"<opml version=\"2.0\">\n" + 
			"	<head>\n" + 
			"		<title>mySubscriptions.opml</title>\n" + 
			"		<dateCreated>Sat, 18 Jun 2005 12:11:52 GMT</dateCreated>\n" + 
			"		<dateModified>Tue, 02 Aug 2005 21:42:48 GMT</dateModified>\n" + 
			"		<ownerName>Joe Example</ownerName>\n" + 
			"		<ownerEmail>joe@example.com</ownerEmail>\n" + 
			"		<expansionState></expansionState>\n" + 
			"		<vertScrollState>1</vertScrollState>\n" + 
			"		<windowTop>61</windowTop>\n" + 
			"		<windowLeft>304</windowLeft>\n" + 
			"		<windowBottom>562</windowBottom>\n" + 
			"		<windowRight>842</windowRight>\n" + 
			"		</head>\n" + 
			"	<body>\n" + 
			"		<outline text=\"News site 1\" description=\"Description for news site #1.\" htmlUrl=\"http://example.com/1/\" language=\"unknown\" title=\"Site1\" type=\"rss\" version=\"RSS2\" xmlUrl=\"http://example.com/1.xml\"/>\n" + 
			"		<outline text=\"News site 2\" description=\"Description for news site #2.\" htmlUrl=\"http://example.com/2/\" language=\"unknown\" title=\"Site2\" type=\"rss\" version=\"RSS2\" xmlUrl=\"http://example.com/2.xml\"/>\n" + 
			"		<outline text=\"News site 3\" description=\"Description for news site #3.\" htmlUrl=\"http://example.com/3/\" language=\"unknown\" title=\"Site3\" type=\"rss\" version=\"RSS2\" xmlUrl=\"http://example.com/3.xml\"/>\n" + 
			"		<outline text=\"News site 4\" description=\"Description for news site #4.\" htmlUrl=\"http://example.com/4/\" language=\"unknown\" title=\"Site4\" type=\"rss\" version=\"RSS2\" xmlUrl=\"http://example.com/4.xml\"/>\n" + 
			"		<outline text=\"News site 5\" description=\"Description for news site #5.\" htmlUrl=\"http://example.com/5/\" language=\"unknown\" title=\"Site5\" type=\"rss\" version=\"RSS2\" xmlUrl=\"http://example.com/5.xml\"/>\n" + 
			"		</body>\n" + 
			"	</opml>";
	
	//based on - http://hosting.opml.org/dave/spec/states.opml
	private final static String statesExample="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + 
			"<opml version=\"2.0\">\n" + 
			"	<head>\n" + 
			"		<title>states.opml</title>\n" + 
			"		<dateCreated>Tue, 15 Mar 2005 16:35:45 GMT</dateCreated>\n" + 
			"		<dateModified>Thu, 14 Jul 2005 23:41:05 GMT</dateModified>\n" + 
			"		<ownerName>Joe Example</ownerName>\n" + 
			"		<ownerEmail>joe@example.com</ownerEmail>\n" + 
			"		<expansionState>1, 6, 13, 16, 18, 20</expansionState>\n" + 
			"		<vertScrollState>1</vertScrollState>\n" + 
			"		<windowTop>106</windowTop>\n" + 
			"		<windowLeft>106</windowLeft>\n" + 
			"		<windowBottom>558</windowBottom>\n" + 
			"		<windowRight>479</windowRight>\n" + 
			"		</head>\n" + 
			"	<body>\n" + 
			"		<outline text=\"United States\">\n" + 
			"			<outline text=\"Far West\">\n" + 
			"				<outline text=\"Alaska\"/>\n" + 
			"				<outline text=\"California\"/>\n" + 
			"				<outline text=\"Hawaii\"/>\n" + 
			"				<outline text=\"Nevada\">\n" + 
			"					<outline text=\"Reno\" created=\"Tue, 12 Jul 2005 23:56:35 GMT\"/>\n" + 
			"					<outline text=\"Las Vegas\" created=\"Tue, 12 Jul 2005 23:56:37 GMT\"/>\n" + 
			"					<outline text=\"Ely\" created=\"Tue, 12 Jul 2005 23:56:39 GMT\"/>\n" + 
			"					<outline text=\"Gerlach\" created=\"Tue, 12 Jul 2005 23:56:47 GMT\"/>\n" + 
			"					</outline>\n" + 
			"				<outline text=\"Oregon\"/>\n" + 
			"				<outline text=\"Washington\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Great Plains\">\n" + 
			"				<outline text=\"Kansas\"/>\n" + 
			"				<outline text=\"Nebraska\"/>\n" + 
			"				<outline text=\"North Dakota\"/>\n" + 
			"				<outline text=\"Oklahoma\"/>\n" + 
			"				<outline text=\"South Dakota\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Mid-Atlantic\">\n" + 
			"				<outline text=\"Delaware\"/>\n" + 
			"				<outline text=\"Maryland\"/>\n" + 
			"				<outline text=\"New Jersey\"/>\n" + 
			"				<outline text=\"New York\"/>\n" + 
			"				<outline text=\"Pennsylvania\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Midwest\">\n" + 
			"				<outline text=\"Illinois\"/>\n" + 
			"				<outline text=\"Indiana\"/>\n" + 
			"				<outline text=\"Iowa\"/>\n" + 
			"				<outline text=\"Kentucky\"/>\n" + 
			"				<outline text=\"Michigan\"/>\n" + 
			"				<outline text=\"Minnesota\"/>\n" + 
			"				<outline text=\"Missouri\"/>\n" + 
			"				<outline text=\"Ohio\"/>\n" + 
			"				<outline text=\"West Virginia\"/>\n" + 
			"				<outline text=\"Wisconsin\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Mountains\">\n" + 
			"				<outline text=\"Colorado\"/>\n" + 
			"				<outline text=\"Idaho\"/>\n" + 
			"				<outline text=\"Montana\"/>\n" + 
			"				<outline text=\"Utah\"/>\n" + 
			"				<outline text=\"Wyoming\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"New England\">\n" + 
			"				<outline text=\"Connecticut\"/>\n" + 
			"				<outline text=\"Maine\"/>\n" + 
			"				<outline text=\"Massachusetts\"/>\n" + 
			"				<outline text=\"New Hampshire\"/>\n" + 
			"				<outline text=\"Rhode Island\"/>\n" + 
			"				<outline text=\"Vermont\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"South\">\n" + 
			"				<outline text=\"Alabama\"/>\n" + 
			"				<outline text=\"Arkansas\"/>\n" + 
			"				<outline text=\"Florida\"/>\n" + 
			"				<outline text=\"Georgia\"/>\n" + 
			"				<outline text=\"Louisiana\"/>\n" + 
			"				<outline text=\"Mississippi\"/>\n" + 
			"				<outline text=\"North Carolina\"/>\n" + 
			"				<outline text=\"South Carolina\"/>\n" + 
			"				<outline text=\"Tennessee\"/>\n" + 
			"				<outline text=\"Virginia\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Southwest\">\n" + 
			"				<outline text=\"Arizona\"/>\n" + 
			"				<outline text=\"New Mexico\"/>\n" + 
			"				<outline text=\"Texas\"/>\n" + 
			"				</outline>\n" + 
			"			</outline>\n" + 
			"		</body>\n" + 
			"	</opml>";
	
	//based on - http://hosting.opml.org/dave/spec/simpleScript.opml
	private final static String scriptExample="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + 
			"<opml version=\"2.0\">\n" + 
			"	<head>\n" + 
			"		<title>workspace.userlandsamples.doSomeUpstreaming</title>\n" + 
			"		<dateCreated>Mon, 11 Feb 2002 22:48:02 GMT</dateCreated>\n" + 
			"		<dateModified>Sun, 30 Oct 2005 03:30:17 GMT</dateModified>\n" + 
			"		<ownerName>Joe Example</ownerName>\n" + 
			"		<ownerEmail>joe@example.com</ownerEmail>\n" + 
			"		<expansionState>1, 2, 4</expansionState>\n" + 
			"		<vertScrollState>1</vertScrollState>\n" + 
			"		<windowTop>74</windowTop>\n" + 
			"		<windowLeft>41</windowLeft>\n" + 
			"		<windowBottom>314</windowBottom>\n" + 
			"		<windowRight>475</windowRight>\n" + 
			"		</head>\n" + 
			"	<body>\n" + 
			"		<outline text=\"Changes\" isComment=\"true\">\n" + 
			"			<outline text=\"1/3/02; 4:54:25 PM by example\">\n" + 
			"				<outline text=\"Change &quot;playlist&quot; to &quot;radio&quot;.\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"2/12/01; 1:49:33 PM by example\" isComment=\"true\">\n" + 
			"				<outline text=\"Test upstreaming by sprinkling a few files in a nice new test folder.\"/>\n" + 
			"				</outline>\n" + 
			"			</outline>\n" + 
			"		<outline text=\"on writetestfile (f, size)\">\n" + 
			"			<outline text=\"file.surefilepath (f)\" isBreakpoint=\"true\"/>\n" + 
			"			<outline text=\"file.writewholefile (f, string.filledstring (&quot;x&quot;, size))\"/>\n" + 
			"			</outline>\n" + 
			"		<outline text=\"local (folder = user.radio.prefs.wwwfolder + &quot;test\\\\largefiles\\\\&quot;)\"/>\n" + 
			"		<outline text=\"for ch = 'a' to 'z'\">\n" + 
			"			<outline text=\"writetestfile (folder + ch + &quot;.html&quot;, random (1000, 16000))\"/>\n" + 
			"			</outline>\n" + 
			"		</body>\n" + 
			"	</opml>";
	
	//based on - http://hosting.opml.org/dave/spec/placesLived.opml
	private final static String placesExample="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + 
			"<opml version=\"2.0\">\n" + 
			"	<head>\n" + 
			"		<title>placesLived.opml</title>\n" + 
			"		<dateCreated>Mon, 27 Feb 2006 12:09:48 GMT</dateCreated>\n" + 
			"		<dateModified>Mon, 27 Feb 2006 12:11:44 GMT</dateModified>\n" + 
			"		<ownerName>Joe Example</ownerName>\n" + 
			"		<ownerId>http://www.example.com/profiles/sendMail?usernum=1</ownerId>\n" + 
			"		<expansionState>1, 2, 5, 10, 13, 15</expansionState>\n" + 
			"		<vertScrollState>1</vertScrollState>\n" + 
			"		<windowTop>242</windowTop>\n" + 
			"		<windowLeft>329</windowLeft>\n" + 
			"		<windowBottom>665</windowBottom>\n" + 
			"		<windowRight>547</windowRight>\n" + 
			"		</head>\n" + 
			"	<body>\n" + 
			"		<outline text=\"Places I've lived\">\n" + 
			"			<outline text=\"Boston\">\n" + 
			"				<outline text=\"Cambridge\"/>\n" + 
			"				<outline text=\"West Newton\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Bay Area\">\n" + 
			"				<outline text=\"Mountain View\"/>\n" + 
			"				<outline text=\"Los Gatos\"/>\n" + 
			"				<outline text=\"Palo Alto\"/>\n" + 
			"				<outline text=\"Woodside\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"New Orleans\">\n" + 
			"				<outline text=\"Uptown\"/>\n" + 
			"				<outline text=\"Metairie\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Wisconsin\">\n" + 
			"				<outline text=\"Madison\"/>\n" + 
			"				</outline>\n" + 
			"			<outline text=\"Florida\" type=\"include\" url=\"http://example.com/florida.opml\"/>\n" + 
			"			<outline text=\"New York\">\n" + 
			"				<outline text=\"Jackson Heights\"/>\n" + 
			"				<outline text=\"Flushing\"/>\n" + 
			"				<outline text=\"The Bronx\"/>\n" + 
			"				</outline>\n" + 
			"			</outline>\n" + 
			"		</body>\n" + 
			"	</opml>";

	//based on - http://hosting.opml.org/dave/spec/directory.opml
	private final static String directoryExample="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + 
			"<opml version=\"2.0\">\n" + 
			"	<head>\n" + 
			"		<title>scriptingNewsDirectory.opml</title>\n" + 
			"		<dateCreated>Thu, 13 Oct 2005 15:34:07 GMT</dateCreated>\n" + 
			"		<dateModified>Tue, 25 Oct 2005 21:33:57 GMT</dateModified>\n" + 
			"		<ownerName>Joe Example</ownerName>\n" + 
			"		<ownerEmail>joe@example.com</ownerEmail>\n" + 
			"		<expansionState></expansionState>\n" + 
			"		<vertScrollState>1</vertScrollState>\n" + 
			"		<windowTop>105</windowTop>\n" + 
			"		<windowLeft>466</windowLeft>\n" + 
			"		<windowBottom>386</windowBottom>\n" + 
			"		<windowRight>964</windowRight>\n" + 
			"		</head>\n" + 
			"	<body>\n" + 
			"		<outline text=\"Directory 1\" created=\"Sun, 16 Oct 2005 05:56:10 GMT\" type=\"link\" url=\"http://example.com/directory1.opml\"/>\n" + 
			"		<outline text=\"Directory 2\" created=\"Tue, 25 Oct 2005 21:33:28 GMT\" type=\"link\" url=\"http://example.com/directory2.opml\"/>\n" + 
			"		<outline text=\"Directory 3\" created=\"Mon, 24 Oct 2005 05:23:52 GMT\" type=\"link\" url=\"http://example.com/directory3.opml\"/>\n" + 
			"		<outline text=\"Directory 4\" type=\"link\" url=\"http://example.com/directory4.opml\"/>\n" + 
			"		</body>\n" + 
			"	</opml>";
	
	//based on - http://hosting.opml.org/dave/spec/category.opml
	private final static String categoryExample="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" + 
			"<opml version=\"2.0\">\n" + 
			"	<head>\n" + 
			"		<title>Illustrating the category attribute</title>\n" + 
			"		<dateCreated>Mon, 31 Oct 2005 19:23:00 GMT</dateCreated>\n" + 
			"		</head>\n" + 
			"	<body>\n" + 
			"		<outline text=\"The Cubs are the worst team in baseball.\" category=\"/Philosophy/Baseball/Cubs,/Tourism/Chicago\" created=\"Mon, 31 Oct 2005 18:21:33 GMT\"/>\n" + 
			"		</body>\n" + 
			"	</opml>";
	
	@Test
	void test(){
		try{
			/*
			 * subscriptionExample is a simple but common case
			 * if this passes it is likely the other examples will as well
			 */
			InputStream in=new ByteArrayInputStream(subscriptionExample.getBytes(StandardCharsets.UTF_8));
			OPMLObject opml=OPMLParser.parse(in);
			in.close();
			assertEquals(opml.getVersion(),"2.0");
			OPMLHead head=opml.getHead();
			assertEquals(head.getTitle(),"mySubscriptions.opml");
			assertEquals(head.getDateCreated(),"Sat, 18 Jun 2005 12:11:52 GMT");
			assertEquals(head.getDateModified(),"Tue, 02 Aug 2005 21:42:48 GMT");
			assertEquals(head.getOwnerName(),"Joe Example");
			assertEquals(head.getOwnerEmail(),"joe@example.com");
			assertTrue(head.getOwnerId()==null);
			assertTrue(head.getExpansionState()==null);
			assertEquals(head.getVertScrollState().intValue(),1);
			assertEquals(head.getWindowTop().intValue(),61);
			assertEquals(head.getWindowLeft().intValue(),304);
			assertEquals(head.getWindowBottom().intValue(),562);
			assertEquals(head.getWindowRight().intValue(),842);
			int bodySize=opml.getBody().size();
			assertEquals(bodySize,5);
			OPMLOutline outline=opml.getBody().get(0);
			int childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"News site 1");
			assertEquals(outline.getDescription(),"Description for news site #1.");
			assertEquals(outline.getHtmlUrl(),"http://example.com/1/");
			assertEquals(outline.getLanguage(),"unknown");
			assertEquals(outline.getTitle(),"Site1");
			assertEquals(outline.getType(),"rss");
			assertEquals(outline.getVersion(),"RSS2");
			assertEquals(outline.getXmlUrl(),"http://example.com/1.xml");
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"News site 2");
			assertEquals(outline.getDescription(),"Description for news site #2.");
			assertEquals(outline.getHtmlUrl(),"http://example.com/2/");
			assertEquals(outline.getLanguage(),"unknown");
			assertEquals(outline.getTitle(),"Site2");
			assertEquals(outline.getType(),"rss");
			assertEquals(outline.getVersion(),"RSS2");
			assertEquals(outline.getXmlUrl(),"http://example.com/2.xml");
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"News site 3");
			assertEquals(outline.getDescription(),"Description for news site #3.");
			assertEquals(outline.getHtmlUrl(),"http://example.com/3/");
			assertEquals(outline.getLanguage(),"unknown");
			assertEquals(outline.getTitle(),"Site3");
			assertEquals(outline.getType(),"rss");
			assertEquals(outline.getVersion(),"RSS2");
			assertEquals(outline.getXmlUrl(),"http://example.com/3.xml");
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"News site 4");
			assertEquals(outline.getDescription(),"Description for news site #4.");
			assertEquals(outline.getHtmlUrl(),"http://example.com/4/");
			assertEquals(outline.getLanguage(),"unknown");
			assertEquals(outline.getTitle(),"Site4");
			assertEquals(outline.getType(),"rss");
			assertEquals(outline.getVersion(),"RSS2");
			assertEquals(outline.getXmlUrl(),"http://example.com/4.xml");
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"News site 5");
			assertEquals(outline.getDescription(),"Description for news site #5.");
			assertEquals(outline.getHtmlUrl(),"http://example.com/5/");
			assertEquals(outline.getLanguage(),"unknown");
			assertEquals(outline.getTitle(),"Site5");
			assertEquals(outline.getType(),"rss");
			assertEquals(outline.getVersion(),"RSS2");
			assertEquals(outline.getXmlUrl(),"http://example.com/5.xml");
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);				
			/*
			 * statesExample has nested outlines
			 * if this passes it is likely the other examples will as well
			 * this is also super boring to code and I should have cut it back more
			 * whatever, it's at least making sure I didn't make a mistake handling nested outline elements
			 */
			in=new ByteArrayInputStream(statesExample.getBytes(StandardCharsets.UTF_8));
			opml=OPMLParser.parse(in);
			in.close();
			assertEquals(opml.getVersion(),"2.0");
			head=opml.getHead();
			assertEquals(head.getTitle(),"states.opml");
			assertEquals(head.getDateCreated(),"Tue, 15 Mar 2005 16:35:45 GMT");
			assertEquals(head.getDateModified(),"Thu, 14 Jul 2005 23:41:05 GMT");
			assertEquals(head.getOwnerName(),"Joe Example");
			assertEquals(head.getOwnerEmail(),"joe@example.com");
			assertTrue(head.getOwnerId()==null);
			assertEquals(head.getExpansionState(),"1, 6, 13, 16, 18, 20");
			assertEquals(head.getVertScrollState().intValue(),1);
			assertEquals(head.getWindowTop().intValue(),106);
			assertEquals(head.getWindowLeft().intValue(),106);
			assertEquals(head.getWindowBottom().intValue(),558);
			assertEquals(head.getWindowRight().intValue(),479);			
			bodySize=opml.getBody().size();
			assertEquals(bodySize,1);
			outline=opml.getBody().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,8);
			assertEquals(outline.getText(),"United States");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,6);
			assertEquals(outline.getText(),"Far West");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Alaska");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"California");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);	 
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Hawaii");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,4);
			assertEquals(outline.getText(),"Nevada");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(3).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Reno");
			assertEquals(outline.getCreated(),"Tue, 12 Jul 2005 23:56:35 GMT");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(3).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Las Vegas");
			assertEquals(outline.getCreated(),"Tue, 12 Jul 2005 23:56:37 GMT");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(3).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Ely");
			assertEquals(outline.getCreated(),"Tue, 12 Jul 2005 23:56:39 GMT");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(3).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Gerlach");
			assertEquals(outline.getCreated(),"Tue, 12 Jul 2005 23:56:47 GMT");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Oregon");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(5);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Washington");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,5);
			assertEquals(outline.getText(),"Great Plains");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Kansas");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Nebraska");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"North Dakota");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Oklahoma");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"South Dakota");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,5);
			assertEquals(outline.getText(),"Mid-Atlantic");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Delaware");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Maryland");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"New Jersey");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"New York");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Pennsylvania");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,10);
			assertEquals(outline.getText(),"Midwest");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Illinois");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Indiana");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Iowa");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Kentucky");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Michigan");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(5);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Minnesota");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(6);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Missouri");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(7);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Ohio");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(8);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"West Virginia");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(9);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Wisconsin");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,5);
			assertEquals(outline.getText(),"Mountains");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(4).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Colorado");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(4).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Idaho");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);		
			outline=opml.getBody().get(0).getChildren().get(4).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Montana");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(4).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Utah");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(4).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Wyoming");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5);
			childSize=outline.getChildren().size();
			assertEquals(childSize,6);
			assertEquals(outline.getText(),"New England");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Connecticut");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Maine");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Massachusetts");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"New Hampshire");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Rhode Island");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(5);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Vermont");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(6);
			childSize=outline.getChildren().size();
			assertEquals(childSize,10);
			assertEquals(outline.getText(),"South");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Alabama");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Arkansas");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Florida");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Georgia");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Louisiana");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(5);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Mississippi");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(6);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"North Carolina");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(7);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"South Carolina");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(8);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Tennessee");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(6).getChildren().get(9);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Virginia");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(7);
			childSize=outline.getChildren().size();
			assertEquals(childSize,3);
			assertEquals(outline.getText(),"Southwest");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(7).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Arizona");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(7).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"New Mexico");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(7).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Texas");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			/*
			 * scriptExample is the only that tests isBreakpoint and isComment
			 */
			in=new ByteArrayInputStream(scriptExample.getBytes(StandardCharsets.UTF_8));
			opml=OPMLParser.parse(in);
			in.close();
			assertEquals(opml.getVersion(),"2.0");
			head=opml.getHead();
			assertEquals(head.getTitle(),"workspace.userlandsamples.doSomeUpstreaming");
			assertEquals(head.getDateCreated(),"Mon, 11 Feb 2002 22:48:02 GMT");
			assertEquals(head.getDateModified(),"Sun, 30 Oct 2005 03:30:17 GMT");
			assertEquals(head.getOwnerName(),"Joe Example");
			assertEquals(head.getOwnerEmail(),"joe@example.com");
			assertTrue(head.getOwnerId()==null);
			assertEquals(head.getExpansionState(),"1, 2, 4");
			assertEquals(head.getVertScrollState().intValue(),1);
			assertEquals(head.getWindowTop().intValue(),74);
			assertEquals(head.getWindowLeft().intValue(),41);
			assertEquals(head.getWindowBottom().intValue(),314);
			assertEquals(head.getWindowRight().intValue(),475);				
			bodySize=opml.getBody().size();
			assertEquals(bodySize,4);
			outline=opml.getBody().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,2);
			assertEquals(outline.getText(),"Changes");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==Boolean.TRUE);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,1);
			assertEquals(outline.getText(),"1/3/02; 4:54:25 PM by example");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Change \"playlist\" to \"radio\".");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,1);
			assertEquals(outline.getText(),"2/12/01; 1:49:33 PM by example");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==Boolean.TRUE);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Test upstreaming by sprinkling a few files in a nice new test folder.");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,2);
			assertEquals(outline.getText(),"on writetestfile (f, size)");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(1).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"file.surefilepath (f)");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==Boolean.TRUE);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(1).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"file.writewholefile (f, string.filledstring (\"x\", size))");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"local (folder = user.radio.prefs.wwwfolder + \"test\\\\largefiles\\\\\")");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,1);
			assertEquals(outline.getText(),"for ch = 'a' to 'z'");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(3).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"writetestfile (folder + ch + \".html\", random (1000, 16000))");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			/*
			 * another example with nested outline
			 */			
			in=new ByteArrayInputStream(placesExample.getBytes(StandardCharsets.UTF_8));
			opml=OPMLParser.parse(in);
			in.close();
			assertEquals(opml.getVersion(),"2.0");
			head=opml.getHead();
			assertEquals(head.getTitle(),"placesLived.opml");
			assertEquals(head.getDateCreated(),"Mon, 27 Feb 2006 12:09:48 GMT");
			assertEquals(head.getDateModified(),"Mon, 27 Feb 2006 12:11:44 GMT");
			assertEquals(head.getOwnerName(),"Joe Example");
			assertTrue(head.getOwnerEmail()==null);
			assertEquals(head.getOwnerId(),"http://www.example.com/profiles/sendMail?usernum=1");
			assertEquals(head.getExpansionState(),"1, 2, 5, 10, 13, 15");
			assertEquals(head.getVertScrollState().intValue(),1);
			assertEquals(head.getWindowTop().intValue(),242);
			assertEquals(head.getWindowLeft().intValue(),329);
			assertEquals(head.getWindowBottom().intValue(),665);
			assertEquals(head.getWindowRight().intValue(),547);				
			bodySize=opml.getBody().size();
			assertEquals(bodySize,1);
			outline=opml.getBody().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,6);
			assertEquals(outline.getText(),"Places I've lived");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(0).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,2);
			assertEquals(outline.getText(),"Boston");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Cambridge");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(0).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"West Newton");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,4);
			assertEquals(outline.getText(),"Bay Area");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Mountain View");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Los Gatos");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Palo Alto");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(1).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Woodside");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,2);
			assertEquals(outline.getText(),"New Orleans");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Uptown");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(2).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Metairie");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,1);
			assertEquals(outline.getText(),"Wisconsin");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);				
			outline=opml.getBody().get(0).getChildren().get(3).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Madison");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(4);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Florida");
			assertEquals(outline.getType(),"include");
			assertEquals(outline.getUrl(),"http://example.com/florida.opml");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(5);
			childSize=outline.getChildren().size();
			assertEquals(childSize,3);
			assertEquals(outline.getText(),"New York");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Jackson Heights");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Flushing");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(0).getChildren().get(5).getChildren().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"The Bronx");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getType()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			/*
			 * directoryExample is a simple case
			 * if the tests have made it this far then this should pass
			 */
			in=new ByteArrayInputStream(directoryExample.getBytes(StandardCharsets.UTF_8));
			opml=OPMLParser.parse(in);
			in.close();
			assertEquals(opml.getVersion(),"2.0");
			head=opml.getHead();
			assertEquals(head.getTitle(),"scriptingNewsDirectory.opml");
			assertEquals(head.getDateCreated(),"Thu, 13 Oct 2005 15:34:07 GMT");
			assertEquals(head.getDateModified(),"Tue, 25 Oct 2005 21:33:57 GMT");
			assertEquals(head.getOwnerName(),"Joe Example");
			assertEquals(head.getOwnerEmail(),"joe@example.com");
			assertTrue(head.getOwnerId()==null);
			assertTrue(head.getExpansionState()==null);
			assertEquals(head.getVertScrollState().intValue(),1);
			assertEquals(head.getWindowTop().intValue(),105);
			assertEquals(head.getWindowLeft().intValue(),466);
			assertEquals(head.getWindowBottom().intValue(),386);
			assertEquals(head.getWindowRight().intValue(),964);				
			bodySize=opml.getBody().size();
			assertEquals(bodySize,4);
			outline=opml.getBody().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Directory 1");
			assertEquals(outline.getCreated(),"Sun, 16 Oct 2005 05:56:10 GMT");
			assertEquals(outline.getType(),"link");
			assertEquals(outline.getUrl(),"http://example.com/directory1.opml");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);			
			outline=opml.getBody().get(1);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Directory 2");
			assertEquals(outline.getCreated(),"Tue, 25 Oct 2005 21:33:28 GMT");
			assertEquals(outline.getType(),"link");
			assertEquals(outline.getUrl(),"http://example.com/directory2.opml");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(2);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Directory 3");
			assertEquals(outline.getCreated(),"Mon, 24 Oct 2005 05:23:52 GMT");
			assertEquals(outline.getType(),"link");
			assertEquals(outline.getUrl(),"http://example.com/directory3.opml");
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);	
			outline=opml.getBody().get(3);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"Directory 4");
			assertEquals(outline.getType(),"link");
			assertEquals(outline.getUrl(),"http://example.com/directory4.opml");
			assertTrue(outline.getCreated()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
			assertTrue(outline.getCategory()==null);
			/*
			 * categoryExample is very simple
			 * if the tests have made it this far then this should pass
			 */			
			in=new ByteArrayInputStream(categoryExample.getBytes(StandardCharsets.UTF_8));
			opml=OPMLParser.parse(in);
			in.close();
			assertEquals(opml.getVersion(),"2.0");
			head=opml.getHead();
			assertEquals(head.getTitle(),"Illustrating the category attribute");
			assertEquals(head.getDateCreated(),"Mon, 31 Oct 2005 19:23:00 GMT");
			assertTrue(head.getDateModified()==null);
			assertTrue(head.getOwnerName()==null);
			assertTrue(head.getOwnerEmail()==null);
			assertTrue(head.getOwnerId()==null);
			assertTrue(head.getExpansionState()==null);
			assertTrue(head.getVertScrollState()==null);
			assertTrue(head.getWindowTop()==null);
			assertTrue(head.getWindowLeft()==null);
			assertTrue(head.getWindowBottom()==null);
			assertTrue(head.getWindowRight()==null);
			bodySize=opml.getBody().size();
			assertEquals(bodySize,1);
			outline=opml.getBody().get(0);
			childSize=outline.getChildren().size();
			assertEquals(childSize,0);
			assertEquals(outline.getText(),"The Cubs are the worst team in baseball.");
			assertEquals(outline.getCreated(),"Mon, 31 Oct 2005 18:21:33 GMT");
			assertEquals(outline.getCategory(),"/Philosophy/Baseball/Cubs,/Tourism/Chicago");
			assertTrue(outline.getType()==null);
			assertTrue(outline.getUrl()==null);
			assertTrue(outline.getDescription()==null);
			assertTrue(outline.getHtmlUrl()==null);
			assertTrue(outline.getLanguage()==null);
			assertTrue(outline.getTitle()==null);
			assertTrue(outline.getVersion()==null);
			assertTrue(outline.getXmlUrl()==null);
			assertTrue(outline.isComment()==null);
			assertTrue(outline.isBreakpoint()==null);
		}catch(Exception x){
			fail(x);
		}
	}
}