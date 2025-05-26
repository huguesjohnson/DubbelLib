/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

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
import com.huguesjohnson.dubbel.util.DateUtil;

public class BuildExternalLinksPage{
	public static void writePage(Settings settings,Map<String,ArrayList<String>> linkMap) throws Exception{
		if(!settings.buildExternalLinksPage){return;}//in case I do something silly
		if(settings.externalLinksPagePathRel==null){return;}
		if(settings.externalLinksPagePathRel.length()==0){return;}
		//now build the page - could move this to a subroutine I suppose
		String baseSiteLower=settings.siteMapBaseUrl.toLowerCase();
		//TODO - use templater here for the class
		String tableStart="<table  class=\"table\">"+settings.newLine;
		String tableEnd="</table>"+settings.newLine;
		String trStart="<tr>"+settings.newLine;
		String trEnd="</tr>"+settings.newLine;
		String tdStart="<td>"+settings.newLine;
		String tdEnd="</td>"+settings.newLine;
		//find where to insert the table
		String externalLinksPagePathAbs=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.externalLinksPagePathRel);
		File pageWrite=FileUtils.getTempFile(externalLinksPagePathAbs);
		FileWriter pageWriter=new FileWriter(pageWrite);
		File pageRead=(new File(externalLinksPagePathAbs));
		BufferedReader pageReader=new BufferedReader(new FileReader(pageRead));
		//find location to write link table
		String line=pageReader.readLine();
		while(!line.contains(settings.htmlBlocks.getExternalLinksStartTag())){
			pageWriter.write(line);
			pageWriter.write(settings.newLine);
			line=pageReader.readLine();
		}
		pageWriter.write(line);
		pageWriter.write(settings.newLine);
		String date=DateUtil.now(DateUtil.DF_YearMonthDay);
		pageWriter.write("<p>Updated: "+date+"</p>");
		pageWriter.write(settings.newLine);
		//start the table here
		//TODO header style
		pageWriter.write(tableStart);
		pageWriter.write(trStart);//new row
		pageWriter.write(tdStart);//new column
		pageWriter.write("External Link"+settings.newLine);
		pageWriter.write(tdEnd);//close 1st column
		pageWriter.write(tdStart);//2nd column
		pageWriter.write("Page(s)"+settings.newLine);
		pageWriter.write(tdEnd);//close 2nd column
		pageWriter.write(trEnd);//close row
		//go through all the links
		for(String link:linkMap.keySet()){
			String linkLower=link.toLowerCase();
			boolean skip=false;
			if(!linkLower.startsWith("http")){//only care about external links
				skip=true;
			}else{
				//don't care about internal links
				if(linkLower.startsWith(baseSiteLower)){
					skip=true;
				}
			}
			if(!skip){
				pageWriter.write(trStart);//new row
				pageWriter.write(tdStart);//new column
				pageWriter.write("<p><a href=\""+link.replace("&","&amp;")+"\" rel=\"nofollow\" target=\"_blank\">"+link+"</a></p>");
				pageWriter.write(tdEnd);//close 1st column
				pageWriter.write(tdStart);//start 2nd column
				ArrayList<String> linkedPages=linkMap.get(link);
				if(linkedPages==null){//should never happen
					pageWriter.write("(null)");
				}else{
					for(String page:linkedPages){
						pageWriter.write("<p><a href=\"."+page+"\">"+page+"</a></p>");
					}
				}
				pageWriter.write(tdEnd);//close 2nd column
				pageWriter.write(trEnd);//close row
			}
			
		}
		//close the table
		pageWriter.write(tableEnd);
		//finish the page
		while(!line.contains(settings.htmlBlocks.getExternalLinksEndTag())){
			line=pageReader.readLine();
		}
		while(line!=null){
			pageWriter.write(line);
			pageWriter.write(settings.newLine);
			line=pageReader.readLine();
		}
		//close out
		pageWriter.flush();
		pageWriter.close();
		pageReader.close();
		//copy temp file to real path
		Files.copy(pageWrite.toPath(),pageRead.toPath(),StandardCopyOption.REPLACE_EXISTING);
		Files.delete(pageWrite.toPath());
	}
}