/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.pagebuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
			String topicPagePathRel=topic.toLowerCase().replace(" ","-")+settings.rootDocument;
			String topicPagePathAbs=PathResolver.getAbsolutePath(rootFolderPathAbs,topicPagePathRel);
			//does the page exist at all?
			File pageRead=(new File(topicPagePathAbs));
			if(pageRead.exists()){
				//writing to a temp file then swapping later
				File tempWrite=FileUtils.getTempFile(topicPagePathAbs);
				//setup reader and writer
				FileWriter tempWriter=new FileWriter(tempWrite);
				BufferedReader pageReader=new BufferedReader(new FileReader(pageRead));
				//this should sort them into alphabetical order by title
				pages.sort(new PageDataComparator());
				//find location to write the links
				String line=pageReader.readLine();
				while(!line.contains(settings.htmlBlocks.getTopicsStartTag())){
					tempWriter.write(line);
					tempWriter.write(settings.newLine);
					line=pageReader.readLine();
				}
				tempWriter.write(line);
				tempWriter.write(settings.newLine);
				tempWriter.write(settings.newLine);
				//now iterate through pages that point to this topic
				int rowCount=0;
				for(PageData page:pages){
					if(rowCount==0){
						tempWriter.write("<div class=\"row\">");
						tempWriter.write(settings.newLine);
					}
					//lastPath is needed to determine related image
					String lastPath=null;
					String[] paths=page.getUrl().split("/");
					if(page.getUrl().endsWith("/")){
						lastPath=paths[paths.length-1];
					}else{
						lastPath=paths[paths.length-2]+"-"+paths[paths.length-1].replace(".html",".png");
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
					tempWriter.write(row.toString());
					tempWriter.write(settings.newLine);
					rowCount++;
					if(rowCount==3){
						rowCount=0;
						tempWriter.write("</div> <!-- row -->");
						tempWriter.write(settings.newLine);
						tempWriter.write("<br class=\"clearall\">");
						tempWriter.write(settings.newLine);
					}
				}//for PageData
				if((rowCount>0)&&(rowCount<3)){
					//empty rows
					while(rowCount<3){
						tempWriter.write("<div class=\"col-sm\"><p>&nbsp;</p></div>");
						tempWriter.write(settings.newLine);
						rowCount++;
					}
					tempWriter.write("</div> <!-- row -->");
					tempWriter.write(settings.newLine);
					tempWriter.write("<br class=\"clearall\">");
					tempWriter.write(settings.newLine);
				}
				//finish the page
				tempWriter.write(settings.newLine);
				while(!line.contains(settings.htmlBlocks.getTopicsEndTag())){
					line=pageReader.readLine();
				}
				while(line!=null){
					tempWriter.write(line);
					tempWriter.write(settings.newLine);
					line=pageReader.readLine();
				}
				//close writer and reader
				tempWriter.flush();
				tempWriter.close();
				pageReader.close();
				//copy temp file to real path
				Files.copy(tempWrite.toPath(),pageRead.toPath(),StandardCopyOption.REPLACE_EXISTING);
				Files.delete(tempWrite.toPath());
			}else{
				System.err.println("Expecting topics page ["+topicPagePathAbs+"] to exist, yet it does not exist.");
			}
		}

	}
}