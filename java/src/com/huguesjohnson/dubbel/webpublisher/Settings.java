/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.Map;

//effectively a struct
public class Settings{
	/*
	 * Configure which steps will run
	 */
	public boolean dryRun=false;//if true then staging files are created but not copied to the publish directory
	public boolean rebuildSitemap=true;//if true then the sitemap html+xml files are recreated in the publish directory
	public boolean rebuildPagesFromOpml=true;//if true then pages generated from opml are recreated in the publish directory
	public boolean rebuildPagesFromCsv=true;//if true then pages generated from csv are recreated in the publish directory
	public boolean buildExternalLinksPage=true;//if true then the page to audit external links will be built in the publish directory
	public boolean auditCompare=true;//if true then the original and staged files will be compared line by line
	public boolean auditTidy=true;//if true then html-tidy will be run against all pages
	public boolean auditLinks=true;//if true then an audit is run for any non-http links in pages
	public boolean auditPihole=false;//if true the the hosted pihole page is checked for duplicate links
	public boolean upgradeRSSLinksToHTTPS=true;//if true then any links generated from rss/opml will be upgraded to https
	/*
	 * Paths to things
	 */	
	public String stagingDirectory;//base absolute path to where working pages are copied and updated, date+time are appended to the path during execution
	public String publishDirectoryAbs;//absolute path to where the live files are
	public String siteMapPathRel;//publish location for sitemap, relative to publishDirectoryAbs
	public String auditReportPathAbs;//absolute path to where the audit report will be written
	public String externalLinksPagePathRel;//publish location for the external links page, relative to publishDirectoryAbs
	public Map<String,String> opmlPages;//source file -> destination file, relative to publish directory
	public Map<String,String> csvPages;//source file -> destination file, relative to publish directory
	/*
	 * URLs
	 */		
	public String siteMapBaseUrl;//first part of URL for sitemap html+rss
	public String rootDocument;//default root document (i.e. index.html)
	/*
	 * Text substitutions
	 */	
	public Map<String,String> staticTemplates;
	public ReplacementBlockList replacements;
	public HTMLBlocks htmlBlocks;
	/*
	 * I can't think of why this would be substituted though
	 */	
	public String newLine=System.lineSeparator();
}