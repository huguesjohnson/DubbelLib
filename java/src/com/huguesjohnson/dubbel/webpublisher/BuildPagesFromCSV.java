/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

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

public class BuildPagesFromCSV{
	public static void writePages(Settings settings) throws Exception{
		if(!settings.rebuildPagesFromCsv){return;}//in case I do something silly
		if(settings.csvPages==null){return;}
		if(settings.csvPages.size()<1){return;}
		for(String csvPath:settings.csvPages.keySet()){
			String pagePath=settings.csvPages.get(csvPath);
			csvPath=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,csvPath);
			pagePath=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,pagePath);
			writePage(pagePath,csvPath,settings);
		}		
	}
	
	private static void writePage(String pagePath,String csvPath,Settings settings) throws Exception{
		SimpleStaticTemplater staticTemplater=new SimpleStaticTemplater(settings.staticTemplates);
		String ulStart=staticTemplater.process("<ul {UL_CLASS}>")+settings.newLine;
		String ulEnd="</ul>"+settings.newLine+"<br>"+settings.newLine+"<br>"+settings.newLine+settings.newLine;
		String liHeaderStart=staticTemplater.process("<li {LI_HEADER_CLASS}>");
		String liHeaderEnd="</li>";
		String liStart=staticTemplater.process("<li {LI_CLASS}>");
		String liEnd="</li>";

		//read the csv file
		ArrayList<String> lines=FileUtils.readLines(csvPath);
		//go through each line
		String[] temp=lines.get(0).split(",");
		int colCount=temp.length;
		int rowCount=lines.size();
		String[][] allEntries=new String[colCount][rowCount];
		for(int row=0;row<rowCount;row++){
			String line=lines.get(row);
			String[] cols=line.split(",");
			int length=cols.length;
			for(int col=0;col<length;col++){
				allEntries[col][row]=cols[col];
			}
		}
		File pageWrite=FileUtils.getTempFile(pagePath);
		FileWriter pageWriter=new FileWriter(pageWrite);
		File pageRead=(new File(pagePath));
		BufferedReader pageReader=new BufferedReader(new FileReader(pageRead));
		//find location to write the tables
		String line=pageReader.readLine();
		while(!line.contains(settings.htmlBlocks.getDataStartTag())){
			pageWriter.write(line);
			pageWriter.write(settings.newLine);
			line=pageReader.readLine();
		}
		pageWriter.write(line);
		pageWriter.write(settings.newLine);
		String date=DateUtil.now(DateUtil.DF_YearMonthDay);
		pageWriter.write("<p>Updated: "+date+"</p>");
		pageWriter.write(settings.newLine);
		for(int table=0;table<colCount;table++){
			//start the list
			pageWriter.write(ulStart);
			//write the header row
			pageWriter.write(liHeaderStart);
			pageWriter.write(allEntries[table][0].replace("&","&amp;"));
			pageWriter.write(liHeaderEnd);
			pageWriter.write(settings.newLine);
			//write the remaining rows
			int row=1;
			int length=allEntries[table].length;
			while(row<length){
				String entry=allEntries[table][row];
				if((entry==null)||(entry.isEmpty())){
					row=Integer.MAX_VALUE;
				}else{
					pageWriter.write(liStart);
					pageWriter.write(entry.replace("&","&amp;"));
					pageWriter.write(liEnd);
					pageWriter.write(settings.newLine);
					row++;
				}
			}
			//close the list
			pageWriter.write(ulEnd);
		}
		
		//finish the page
		while(!line.contains(settings.htmlBlocks.getDataEndTag())){
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