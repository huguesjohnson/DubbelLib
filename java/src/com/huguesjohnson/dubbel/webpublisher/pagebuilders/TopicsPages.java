/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.pagebuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Map;

import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.file.PathResolver;
import com.huguesjohnson.dubbel.util.StringUtil;
import com.huguesjohnson.dubbel.webpublisher.PageData;
import com.huguesjohnson.dubbel.webpublisher.PageDataComparator;
import com.huguesjohnson.dubbel.webpublisher.Settings;

public abstract class TopicsPages{

	public static void writePages(Settings settings,Map<String,ArrayList<PageData>> pageMap) throws Exception{
		if(!settings.buildTopicsPages){return;}//in case I do something silly
		String rootFolderPathAbs=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.topicsDirectoryRel);
		rootFolderPathAbs=StringUtil.ensureEndsWith(rootFolderPathAbs,PathResolver.SEPARATOR);
		for(String topic:pageMap.keySet()){
			ArrayList<PageData> pages=pageMap.get(topic);
			String topicName=topic.toLowerCase().replace(" ","-");
			String topicPagePathRel=topicName+settings.rootDocument;
			String topicPagePathAbs=PathResolver.getAbsolutePath(rootFolderPathAbs,topicPagePathRel);
			//does the page exist at all?
			File pageRead=(new File(topicPagePathAbs));
			if(pageRead.exists()){
				//writing to a temp file then swapping later
				File currentTopicPageFile=FileUtils.getTempFile(topicPagePathAbs);
				//setup reader and writer
				FileWriter currentTopicPageWriter=new FileWriter(currentTopicPageFile);
				BufferedReader pageReader=new BufferedReader(new FileReader(pageRead));
				//this should sort them into alphabetical order by title
				pages.sort(new PageDataComparator());
				//find location to write the links
				String line=moveToTag(settings,pageReader,currentTopicPageWriter);
				currentTopicPageWriter.write(line);
				currentTopicPageWriter.write(settings.newLine);
				currentTopicPageWriter.write(settings.newLine);
				//now iterate through pages that point to this topic
				int rowCount=0;
				for(PageData page:pages){
					if(rowCount==0){
						currentTopicPageWriter.write("<div class=\"row\">");
						currentTopicPageWriter.write(settings.newLine);
					}
					//lastPath is needed to determine related image
					String lastPath=null;
					String[] paths=page.getUrl().replace(settings.siteMapBaseUrl+"/","").toLowerCase().split("/");
					if(page.getUrl().endsWith("/")){
						lastPath=paths[paths.length-1];
					}else{
						if(paths.length==1){
							lastPath=paths[0].replace(".html","");
						}else{
							lastPath=paths[paths.length-2]+"-"+paths[paths.length-1].replace(".html","");
						}
					}
					StringBuilder row=new StringBuilder();
					row.append("<div class=\"col-sm\"><a href=\"");
					row.append(page.getUrl());
					row.append("\"><p><img class=\"img-thumbnail\" src=\"https://huguesjohnson.com/images/related/");
					row.append(lastPath);
					row.append(".png\" alt=\"");
					row.append(page.getTitle());
					row.append("\"></p><p>");
					row.append(page.getTitle());
					row.append("</p></a></div>");
					currentTopicPageWriter.write(row.toString());
					currentTopicPageWriter.write(settings.newLine);
					rowCount++;
					if(rowCount==3){
						rowCount=0;
						currentTopicPageWriter.write("</div> <!-- row -->");
						currentTopicPageWriter.write(settings.newLine);
						currentTopicPageWriter.write("<br class=\"clearall\">");
						currentTopicPageWriter.write(settings.newLine);
					}
				}//for PageData
				closeGrid(settings,rowCount,currentTopicPageWriter);
				//finish the page
				currentTopicPageWriter.write(settings.newLine);
				while(!line.contains(settings.htmlBlocks.getTopicsEndTag())){
					line=pageReader.readLine();
				}
				while(line!=null){
					currentTopicPageWriter.write(line);
					currentTopicPageWriter.write(settings.newLine);
					line=pageReader.readLine();
				}
				//close writer and reader
				currentTopicPageWriter.flush();
				currentTopicPageWriter.close();
				pageReader.close();
				//copy temp file to real path
				Files.copy(currentTopicPageFile.toPath(),pageRead.toPath(),StandardCopyOption.REPLACE_EXISTING);
				Files.delete(currentTopicPageFile.toPath());
			}else{
				System.err.println("Expecting topics page ["+topicPagePathAbs+"] to exist, yet it does not exist.");
			}
		}//for(String topic:pageMap.keySet())
		//now write the topics page itself
		String topicUrlBase=StringUtil.ensureEndsWith(settings.siteMapBaseUrl,"/")+StringUtil.ensureEndsWith(settings.topicsDirectoryRel,"/");
		String topicIndexPathAbs=PathResolver.getAbsolutePath(rootFolderPathAbs,settings.rootDocument);
		File pageRead=(new File(topicIndexPathAbs));
		if(pageRead.exists()){
			//writing to a temp file then swapping later
			File topicIndexFile=FileUtils.getTempFile(topicIndexPathAbs);
			//setup reader and writer
			FileWriter topicIndexWriter=new FileWriter(topicIndexFile);
			BufferedReader pageReader=new BufferedReader(new FileReader(pageRead));
			//find location to write the links
			String line=moveToTag(settings,pageReader,topicIndexWriter);
			topicIndexWriter.write(line);
			topicIndexWriter.write(settings.newLine);
			topicIndexWriter.write(settings.newLine);
			int rowCount=0;
			for(String topic:pageMap.keySet()){
				if(rowCount==0){
					topicIndexWriter.write("<div class=\"row\">");
					topicIndexWriter.write(settings.newLine);
				}
				StringBuilder row=new StringBuilder();
				row.append("<div class=\"col-sm\"><a href=\"");
				row.append(topicUrlBase+topic.toLowerCase().replace(" ","-")+"/");
				row.append("\"><p><img class=\"img-thumbnail\" src=\"https://huguesjohnson.com/images/topics/");
				row.append(topic.toLowerCase().replace(" ","-"));
				row.append(".png\" alt=\"");
				row.append(topic);
				row.append("\"></p><p>");
				row.append(topic);
				row.append("</p></a></div>");
				topicIndexWriter.write(row.toString());
				topicIndexWriter.write(settings.newLine);
				rowCount++;
				if(rowCount==3){
					rowCount=0;
					topicIndexWriter.write("</div> <!-- row -->");
					topicIndexWriter.write(settings.newLine);
					topicIndexWriter.write("<br class=\"clearall\">");
					topicIndexWriter.write(settings.newLine);
				}
			}//for topic
			closeGrid(settings,rowCount,topicIndexWriter);
			//finish the page
			topicIndexWriter.write(settings.newLine);
			while(!line.contains(settings.htmlBlocks.getTopicsEndTag())){
				line=pageReader.readLine();
			}
			while(line!=null){
				topicIndexWriter.write(line);
				topicIndexWriter.write(settings.newLine);
				line=pageReader.readLine();
			}
			//close writer and reader
			topicIndexWriter.flush();
			topicIndexWriter.close();
			pageReader.close();
			//copy temp file to real path
			Files.copy(topicIndexFile.toPath(),pageRead.toPath(),StandardCopyOption.REPLACE_EXISTING);
			Files.delete(topicIndexFile.toPath());
		}
	}
	
	private static void closeGrid(Settings settings,int rowCount,FileWriter writer) throws IOException{
		if((rowCount>0)&&(rowCount<3)){
			//empty rows
			while(rowCount<3){
				writer.write("<div class=\"col-sm\"><p>&nbsp;</p></div>");
				writer.write(settings.newLine);
				rowCount++;
			}
			writer.write("</div> <!-- row -->");
			writer.write(settings.newLine);
			writer.write("<br class=\"clearall\">");
			writer.write(settings.newLine);
		}		
	}
	
	private static String moveToTag(Settings settings,BufferedReader reader,FileWriter writer) throws IOException{
		String line=reader.readLine();
		while(!line.contains(settings.htmlBlocks.getTopicsStartTag())){
			writer.write(line);
			writer.write(settings.newLine);
			line=reader.readLine();
		}
		return(line);
	}
	
}