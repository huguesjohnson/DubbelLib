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
import com.huguesjohnson.dubbel.file.filter.HtmlFileFilter;

public class WebPublisher{
	public static void runWebPublisher(Settings settings) throws Exception{
		FileWriter writer=null;
		BufferedReader reader=null;
		try{
			//pre-process replacement text
			SimpleStaticTemplater staticTemplater=new SimpleStaticTemplater(settings.staticTemplates);
			for(ReplacementBlock rb:settings.replacements){
				rb.setReplacementText(staticTemplater.process(rb.getReplacementText()));
			}
			//set directory locations
			File templateDirectory=new File(settings.templateDirectory);
			File stagingDirectory=new File(settings.stagingDirectory);
			File publishDirectory=new File(settings.publishDirectory);
			int beginIndex=templateDirectory.getAbsolutePath().length();
			//check if publish directory exists
			if(!stagingDirectory.exists()){FileUtils.mkdirs(stagingDirectory);}
			//get all html files
			ArrayList<File> files=FileUtils.getAllFilesRecursive(templateDirectory,new HtmlFileFilter());
			for(File file:files){
				String relativePath=file.getAbsolutePath().substring(beginIndex);
				String stagingPath=stagingDirectory+relativePath;
				String publishPath=publishDirectory+relativePath;
				reader=new BufferedReader(new FileReader(file));
				File stagingFile=new File(stagingPath);
				FileUtils.mkdirs(stagingFile);
				if(stagingFile.exists()){
					stagingFile.delete();
				}
				writer=new FileWriter(stagingFile);
				String line;
				boolean inReplacementBlock=false;
				String nextEndTag="";
				SimpleDynamicTemplater dynamicTemplater=new SimpleDynamicTemplater();
				while((line=reader.readLine())!=null){
					if(inReplacementBlock){
						if(line.equals(nextEndTag)){
							writer.write(line);
							writer.write(settings.newLine);
							inReplacementBlock=false;
						}//else skip the line
					}else{
						/* The static and/or dynamic templates could be run against each line here.
						 * Right now I don't need that.
						 * However, this would allow the entire HTML document to support templates.
						 * That actually would be useful in a more robust system. 
						 * This would also mean templatePath and publishPath have to be different. */
						writer.write(line);
						writer.write(settings.newLine);
						ReplacementBlock rb=settings.replacements.findByStartTag(line);
						if(rb!=null){
							inReplacementBlock=true;
							writer.write(dynamicTemplater.process(rb.getReplacementText(),file.getPath(),settings));
							nextEndTag=rb.getEndTag();
						}
					}
				}
				writer.flush();
				writer.close();
				reader.close();
				if(!settings.dryRun){
					File publishFile=new File(publishPath);
					//copy files from staging to publish directory
					Files.copy(stagingFile.toPath(),publishFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
				}
			}//end for (list of files)
			//rebuild pages that are based on data
			if(settings.rebuildSitemap){
				try{
					BuildSiteMap.buildSiteMap(settings);
				}catch(Exception x){
					x.printStackTrace();
				}
			}
			BuildPagesFromOPML.writePages(settings);
			BuildPagesFromCSV.writePages(settings);
		}finally{
			try{ writer.flush(); writer.close();}catch(Exception x){ }
			try{ reader.close(); }catch(Exception x){ }
		}
	}
}