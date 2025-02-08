package com.huguesjohnson.dubbel.webpublisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.util.DateUtil;

public class BuildPagesFromOPML{
	public static void writePages(Settings settings) throws Exception{
		if(!settings.rebuildPagesFromOpml){return;}
		if(settings.opmlPages==null){return;}
		if(settings.opmlPages.size()<1){return;}
		for(String opmlPath:settings.opmlPages.keySet()){
			String pagePath=settings.opmlPages.get(opmlPath);
			writePage(pagePath,opmlPath,settings);
		}
	}
	
	private static void writePage(String pagePath,String opmlPath,Settings settings) throws Exception{
		SimpleStaticTemplater staticTemplater=new SimpleStaticTemplater(settings.staticTemplates);
		String ulStart=staticTemplater.process("<ul {UL_CLASS}>");
		String ulEnd="</ul>"+settings.newLine+"<br><br>"+settings.newLine;
		String liHeaderStart=staticTemplater.process("<li {LI_HEADER_CLASS}>");
		String liHeaderEnd="</li>"+settings.newLine;
		String liStart=staticTemplater.process("<li {LI_CLASS}><a href=\"");
		String liEnd="</a></li>"+settings.newLine;
		
		File pageWrite=FileUtils.getTempFile(pagePath);
		FileWriter pageWriter=new FileWriter(pageWrite);
		File pageRead=(new File(pagePath));
		BufferedReader pageReader=new BufferedReader(new FileReader(pageRead));
		File opmlFile=(new File(opmlPath));
		BufferedReader opmlReader=new BufferedReader(new FileReader(opmlFile));
		
		//find location to write the rss links
		String line=pageReader.readLine();
		while(!line.contains(settings.htmlBlocks.getRssLinksStartTag())){
			pageWriter.write(line);
			pageWriter.write(settings.newLine);
			line=pageReader.readLine();
		}
		pageWriter.write(line);
		pageWriter.write(settings.newLine);
		String date=DateUtil.now(DateUtil.DF_YearMonthDay);
		pageWriter.write("<p>Updated: "+date+"</p>");
		pageWriter.write(settings.newLine);
		boolean firstItem=true;
		String opmlLine="";
		while((opmlLine=opmlReader.readLine())!=null){
			if(opmlLine.indexOf("<outline")>-1){
				int titleIndex=opmlLine.indexOf("title");
				if(titleIndex>-1){
					int xmlUrlIndex=opmlLine.indexOf("xmlUrl");
					if(xmlUrlIndex<0){
						if(!firstItem){
							pageWriter.write(ulEnd);
						}else{
							firstItem=false;
						}
						pageWriter.write(ulStart);
						int startIndex=titleIndex+"title=\"".length();
						int endIndex=opmlLine.indexOf("\"",startIndex);
						String title=opmlLine.substring(startIndex,endIndex);
						pageWriter.write(liHeaderStart);
						pageWriter.write(title);
						pageWriter.write(liHeaderEnd);
					}else{
						pageWriter.write(liStart);
						int startIndex=xmlUrlIndex+"xmlUrl=\"".length();
						int endIndex=opmlLine.indexOf("\"",startIndex);
						String xmlUrl=opmlLine.substring(startIndex,endIndex);
						if(settings.upgradeRSSLinksToHTTPS){
							xmlUrl=xmlUrl.replace("http:","https:");
						}
						pageWriter.write(xmlUrl);
						pageWriter.write("\">");
						startIndex=titleIndex+"title=\"".length();
						endIndex=opmlLine.indexOf("\"",startIndex);
						String title=opmlLine.substring(startIndex,endIndex);
						if((title==null)||(title.length()<1)){
							title=xmlUrl;
						}
						pageWriter.write(title);
						pageWriter.write(liEnd);
					}
				}
			}
		}
		pageWriter.write(ulEnd);

		opmlReader.close();
	    		
		//finish the page
		while(!line.contains(settings.htmlBlocks.getRssLinksEndTag())){
			line=pageReader.readLine();
		}
		while(line!=null){
			pageWriter.write(line);
			pageWriter.write(settings.newLine);
			line=pageReader.readLine();
		}
		pageWriter.flush();
		pageWriter.close();
		pageReader.close();
		//copy temp file to real path
		Files.copy(pageWrite.toPath(),pageRead.toPath(),StandardCopyOption.REPLACE_EXISTING);
		Files.delete(pageWrite.toPath());
	}
	
}