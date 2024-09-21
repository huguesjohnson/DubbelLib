package com.huguesjohnson.dubbel.webpublisher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;

import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.file.filter.HtmlFileFilter;
import com.huguesjohnson.dubbel.util.DateUtil;

public class BuildSiteMap{
	public static void buildSiteMap(Settings settings) throws Exception{
		ArrayList<SiteMapEntry> siteMapList=buildSiteMapList(settings);
		writeSitemap(settings,siteMapList);
	}
	
	private static ArrayList<SiteMapEntry> buildSiteMapList(Settings settings) throws Exception{
		ArrayList<SiteMapEntry> siteMapList=new ArrayList<SiteMapEntry>();
		File rootDir=new File(settings.publishDirectory);
		ArrayList<File> files=FileUtils.getAllFilesRecursive(rootDir,new HtmlFileFilter());
		for(File file:files){
			boolean siteMapInclude=true;
			SiteMapEntry entry=new SiteMapEntry();
			BufferedReader reader=new BufferedReader(new FileReader(file));
			String title="";
			String line;
			while((line=reader.readLine())!=null){
				if(line.equals(settings.htmlBlocks.getSitemapExcludeTag())){
					siteMapInclude=false;
				}else if(line.equals(settings.htmlBlocks.getBreadcrumbStartTag())&&siteMapInclude){
					while(!line.equals(settings.htmlBlocks.getBreadcrumbEndTag())){
						line=reader.readLine();
						int indexOf=line.indexOf("</a>");
						if(indexOf>0){
							line=line.substring(0,indexOf);
							indexOf=line.lastIndexOf(">");
							line=line.substring(indexOf+1);
							entry.addBreadcrumbLevel(line);
						}
					}
				}else if(line.contains("<title>")){
					title=line
						.replace("<title>","")
						.replace("</title>","");
				}
			}
			if((siteMapInclude)&&(entry.getDepth()>0)){
				String path=file.getPath();
				StringBuilder siteMapUrl=new StringBuilder();
				siteMapUrl.append(settings.siteMapBaseUrl);
				String subPath=path.substring(rootDir.getPath().length(),path.length());
				subPath=subPath.replace(settings.rootDocument,"/");
				siteMapUrl.append(subPath);
				entry.setUrl(siteMapUrl.toString());
				entry.setTitle(title);
				siteMapList.add(entry);
			}
			reader.close();
		}		
		Collections.sort(siteMapList,new SiteMapEntryComparator());
		return(siteMapList);
	}


	private static void writeSitemap(Settings settings,ArrayList<SiteMapEntry> siteMapList) throws Exception{
		FileWriter siteMapXMLWriter=new FileWriter(new File(settings.siteMapBasePath+".xml"));
		File htmlWrite=FileUtils.getTempFile(settings.siteMapBasePath);
		FileWriter siteMapHTMLWriter=new FileWriter(htmlWrite);
		File htmlRead=(new File(settings.siteMapBasePath+".html"));
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
			SiteMapEntry entry=siteMapList.get(i);
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
				SiteMapEntry prev=siteMapList.get(i-1);
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
			htmlBuilder.append(entry.getTitle());
			htmlBuilder.append("</a>");
			if(i>=(size-1)){
				//last element
				htmlBuilder.append("</li>");
				htmlBuilder.append(settings.newLine);
				for(int d=0;d<entry.getDepth();d++){
					htmlBuilder.append("</ul>");
					htmlBuilder.append(settings.newLine);
				}
			}else{
				SiteMapEntry next=siteMapList.get(i+1);
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
				
		}
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