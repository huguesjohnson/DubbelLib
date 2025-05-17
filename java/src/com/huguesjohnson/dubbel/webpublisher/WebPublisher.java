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
import com.huguesjohnson.dubbel.file.filter.HtmlFileFilter;

public class WebPublisher{
	public static WebPublisherResults runWebPublisher(Settings settings) throws Exception{
		WebPublisherResults results=new WebPublisherResults();
		FileWriter writer=null;
		BufferedReader reader=null;
		try{
			//pre-process replacement text
			SimpleStaticTemplater staticTemplater=new SimpleStaticTemplater(settings.staticTemplates);
			for(ReplacementBlock rb:settings.replacements){
				String replacementText=rb.getReplacementText();
				if((replacementText==null)||(replacementText.length()<1)){
					//try loading from file then
					String path=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,rb.getReplacementTextPath());
					replacementText=FileUtils.readString(path);
				}
				rb.setReplacementText(staticTemplater.process(replacementText));
			}
			//set directory locations
			File stagingDirectory=new File(settings.stagingDirectory);
			File publishDirectory=new File(settings.publishDirectoryAbs);
			int beginIndex=publishDirectory.getAbsolutePath().length();
			//check if publish directory exists
			if(!stagingDirectory.exists()){FileUtils.mkdirs(stagingDirectory);}
			//do these first so any changes are included in the link map
			if(settings.rebuildPagesFromOpml){BuildPagesFromOPML.writePages(settings);}
			if(settings.rebuildPagesFromCsv){BuildPagesFromCSV.writePages(settings);};
			//list of files to skip for the link map
			ArrayList<String> skipList=buildSkipList(settings);
			//get all html files
			ArrayList<File> files=FileUtils.getAllFilesRecursive(publishDirectory,new HtmlFileFilter());
			for(File file:files){
	        	String absolutePath=file.getAbsolutePath();
				String relativePath=absolutePath.substring(beginIndex);
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
						//first check for adding the line to the link map
			        	boolean skipLinkmap=false;
			        	if(skipList.contains(absolutePath)){
			        		skipLinkmap=true;
			        	}
			        	if(!skipLinkmap){
							int indexOf=line.toLowerCase().indexOf("href=\"");
							if(indexOf>0){
								int startIndex=indexOf+6; //href=" length
								int endIndex=line.indexOf("\"",startIndex);
								if((endIndex>0)&&(endIndex<line.length())){
									String url=line.substring(startIndex,endIndex);
									ArrayList<String> pageList=results.linkMap.get(url);
									if(pageList==null){
					        			pageList=new ArrayList<String>();
						        		pageList.add(relativePath);
						        		results.linkMap.put(url,pageList);
									}else{
					        			if(!pageList.contains(relativePath)){
							        		pageList.add(relativePath);
							        		results.linkMap.replace(url,pageList);
					        			}
									}
								}
							}
						}
						//now write the line
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
			//pages to rebuild after everything else has processed
			if(settings.rebuildSitemap){BuildSiteMap.buildSiteMap(settings);}
			if(settings.buildExternalLinksPage){BuildExternalLinksPage.writePage(settings,results.linkMap);}
			return(results);
		}finally{
			try{ writer.flush(); writer.close();}catch(Exception x){ }
			try{ reader.close(); }catch(Exception x){ }
		}
	}
	
	//build list of pages to skip for building link map or other audits
	public static ArrayList<String> buildSkipList(Settings settings){
		ArrayList<String> skipList=new ArrayList<String>();
		String externalLinksPagePathRel=settings.externalLinksPagePathRel;
		if((externalLinksPagePathRel!=null)&&(externalLinksPagePathRel.length()>0)){
			skipList.add(PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.externalLinksPagePathRel));
		}
		String siteMapPathRel=settings.siteMapPathRel;
		if((siteMapPathRel!=null)&&(siteMapPathRel.length()>0)){
			skipList.add(PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.siteMapPathRel)+".html");
		}
		if(settings.csvPages.size()>0){
			for(String csvPath:settings.csvPages.keySet()){
				String pagePath=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.csvPages.get(csvPath));
				skipList.add(pagePath);
			}		
		}
		if(settings.opmlPages.size()>0){
			for(String opmlPath:settings.opmlPages.keySet()){
				String pagePath=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,settings.opmlPages.get(opmlPath));
				skipList.add(pagePath);
			}
		}		
		return(skipList);
	}
}