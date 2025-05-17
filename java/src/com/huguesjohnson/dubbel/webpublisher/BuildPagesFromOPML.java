/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.file.PathResolver;
import com.huguesjohnson.dubbel.opml.OPMLObject;
import com.huguesjohnson.dubbel.opml.OPMLOutline;
import com.huguesjohnson.dubbel.opml.OPMLParser;
import com.huguesjohnson.dubbel.util.DateUtil;

public class BuildPagesFromOPML{
	public static void writePages(Settings settings) throws Exception{
		if(!settings.rebuildPagesFromOpml){return;}//in case I do something silly
		if(settings.opmlPages==null){return;}
		if(settings.opmlPages.size()<1){return;}
		for(String opmlPath:settings.opmlPages.keySet()){
			String pagePath=settings.opmlPages.get(opmlPath);
			opmlPath=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,opmlPath);
			pagePath=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,pagePath);
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
		OPMLObject opml=OPMLParser.parse(opmlFile);
		opml.sortOutlines();
		
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
		List<OPMLOutline> categories=opml.getBody();
		for(OPMLOutline category:categories){
			//start list
			pageWriter.write(ulStart);
			pageWriter.write(liHeaderStart);
			pageWriter.write(category.getTitle());
			pageWriter.write(liHeaderEnd);
			//go through the child nodes
			List<OPMLOutline> children=category.getChildren();
			for(OPMLOutline child:children){
				pageWriter.write(liStart);
				String xmlUrl=child.getXmlUrl();
				if(settings.upgradeRSSLinksToHTTPS){
					xmlUrl=xmlUrl.replace("http:","https:");
				}
				pageWriter.write(xmlUrl);
				pageWriter.write("\">");
				String title=child.getTitle();
				if((title==null)||(title.length()<1)){
					title=child.getText();
				}
				if((title==null)||(title.length()<1)){
					title=xmlUrl;
				}
				pageWriter.write(title);
				pageWriter.write(liEnd);				
			}
			//end list
			pageWriter.write(ulEnd);
		}
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