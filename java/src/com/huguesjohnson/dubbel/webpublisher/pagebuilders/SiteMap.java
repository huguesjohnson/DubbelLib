/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.pagebuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.file.PathResolver;
import com.huguesjohnson.dubbel.util.DateUtil;
import com.huguesjohnson.dubbel.webpublisher.PageData;
import com.huguesjohnson.dubbel.webpublisher.Settings;

public abstract class SiteMap{
	
	public static void writeSitemap(Settings settings,ArrayList<PageData> siteMapList) throws Exception{
		String siteMapBasePathAbs=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.siteMapPathRel);
		FileWriter siteMapXMLWriter=new FileWriter(new File(siteMapBasePathAbs+".xml"));
		File htmlWrite=FileUtils.getTempFile(siteMapBasePathAbs);
		FileWriter siteMapHTMLWriter=new FileWriter(htmlWrite);
		File htmlRead=(new File(siteMapBasePathAbs+".html"));
		BufferedReader siteMapHTMLReader=new BufferedReader(new FileReader(htmlRead));
		//write start of xml sitemap
		siteMapXMLWriter.write(settings.htmlBlocks.getSitemapXmlStart());
		siteMapXMLWriter.write("<url>\n");
		siteMapXMLWriter.write("  <loc>");
		siteMapXMLWriter.write(settings.siteMapBaseUrl);
		siteMapXMLWriter.write("/</loc>\n");
		siteMapXMLWriter.write("</url>\n");
		int size=siteMapList.size();
		//find location to write to sitemap html
		String line=siteMapHTMLReader.readLine();
		while(!line.contains(settings.htmlBlocks.getSitemapStartTag())){
			siteMapHTMLWriter.write(line);
			siteMapHTMLWriter.write(settings.newLine);
			line=siteMapHTMLReader.readLine();
		}
		siteMapHTMLWriter.write(line);
		siteMapHTMLWriter.write(settings.newLine);
		String date=DateUtil.now(DateUtil.DF_YearMonthDay);
		siteMapHTMLWriter.write("<p>Updated: "+date+"</p>");
		siteMapHTMLWriter.write(settings.newLine);		
		String htmlDir="";
		//start the html list
		siteMapHTMLWriter.write("<ul>");
		siteMapHTMLWriter.write(settings.newLine);
		for(int i=0;i<size;i++){
			//update XML sitemap
			PageData entry=siteMapList.get(i);
			if(entry.getSitemapInclude()){
				siteMapXMLWriter.write("<url>\n");
				StringBuilder url=new StringBuilder();
				url.append("  <loc>");
				url.append(entry.getUrl());
				url.append("</loc>\n");
				siteMapXMLWriter.write(url.toString());
				siteMapXMLWriter.write("</url>\n");
				//update HTML sitemap
				StringBuilder htmlBuilder=new StringBuilder();
				String currentDir=entry.getBreadcrumbString();
				int firstIndexOf=currentDir.indexOf("/");
				if(firstIndexOf<0){
					currentDir="";
				}
				if(!currentDir.equals(htmlDir)){
					htmlDir=currentDir;
				}
				//test if closing a nested ul
				if(i>0){
					PageData prev=siteMapList.get(i-1);
					if(prev.getDepth()>entry.getDepth()){
						for(int d=entry.getDepth();d<prev.getDepth();d++){
							for(int d1=0;d1<prev.getDepth();d1++){
								siteMapHTMLWriter.write("  ");
							}						
							siteMapHTMLWriter.write("</ul>");
							siteMapHTMLWriter.write(settings.newLine);
							for(int d1=0;d1<entry.getDepth();d1++){
								siteMapHTMLWriter.write("  ");
							}						
							siteMapHTMLWriter.write("</li>");
							siteMapHTMLWriter.write(settings.newLine);
						}
					}
				}
				for(int d=0;d<entry.getDepth();d++){
					htmlBuilder.append("  ");
				}
				htmlBuilder.append("<li>");
				htmlBuilder.append("<a href=\"");
				htmlBuilder.append(entry.getUrl());
				htmlBuilder.append("\">");
				htmlBuilder.append(entry.getTrimTitle());
				htmlBuilder.append("</a>");
				htmlBuilder.append(settings.newLine);
				if(i>=(size-1)){
					//last element
					htmlBuilder.append("</li>");
					htmlBuilder.append(settings.newLine);
					for(int d=0;d<entry.getDepth();d++){
						htmlBuilder.append("</ul>");
						htmlBuilder.append(settings.newLine);
					}
				}else{
					PageData next=siteMapList.get(i+1);
					if(next.getDepth()<=entry.getDepth()){
						htmlBuilder.append("</li>");
					}else{
						htmlBuilder.append(settings.newLine);
						for(int d=0;d<next.getDepth();d++){
							htmlBuilder.append("  ");
						}
						htmlBuilder.append("<ul>");
					}
				}
				htmlBuilder.append(settings.newLine);
				siteMapHTMLWriter.write(htmlBuilder.toString());
			}//if(entry.getSitemapInclude())
		}//for(int i=0;i<size;i++)
		//finish the xml sitemap
		siteMapXMLWriter.write("</urlset>");
		siteMapXMLWriter.flush();
		siteMapXMLWriter.close();		
		//finish the html sitemap
		siteMapHTMLWriter.write(settings.newLine);
		while(!line.contains(settings.htmlBlocks.getSitemapEndTag())){
			line=siteMapHTMLReader.readLine();
		}
		while(line!=null){
			siteMapHTMLWriter.write(line);
			siteMapHTMLWriter.write(settings.newLine);
			line=siteMapHTMLReader.readLine();
		}
		siteMapHTMLWriter.flush();
		siteMapHTMLWriter.close();
		siteMapHTMLReader.close();
		//copy temp site map to real path
		Files.copy(htmlWrite.toPath(),htmlRead.toPath(),StandardCopyOption.REPLACE_EXISTING);
		Files.delete(htmlWrite.toPath());
	}

}