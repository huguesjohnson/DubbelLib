/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.Map;

//effectively a struct
public class Settings{
	public boolean dryRun=false;
	public boolean rebuildSitemap=true;
	public boolean rebuildPagesFromOpml=true;  
	public boolean rebuildPagesFromCsv=true; 
	public boolean audit=true; 
	public String templateDirectory;
	public String stagingDirectory;
	public String publishDirectory;
	public String siteMapBasePath;
	public String siteMapBaseUrl;
	public String auditReportPath;
	public String rootDocument;
	public String newLine;
	public Map<String,String> opmlPages;
	public Map<String,String> csvPages;
	public Map<String,String> staticTemplates;
	public ReplacementBlockList replacements;
	public HTMLBlocks htmlBlocks;
}