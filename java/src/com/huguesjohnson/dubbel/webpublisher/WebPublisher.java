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
import com.huguesjohnson.dubbel.util.StringUtil;
import com.huguesjohnson.dubbel.webpublisher.pagebuilders.ExternalLinksPage;
import com.huguesjohnson.dubbel.webpublisher.pagebuilders.PagesFromCSV;
import com.huguesjohnson.dubbel.webpublisher.pagebuilders.PagesFromOPML;
import com.huguesjohnson.dubbel.webpublisher.pagebuilders.SiteMap;
import com.huguesjohnson.dubbel.webpublisher.pagebuilders.TopicsPages;

public class WebPublisher{
	private Settings settings;
	SimpleStaticTemplater staticTemplater;
	
	public WebPublisher(Settings settings) throws Exception{
		this.settings=settings;
		/*
		 * sanity checks
		 */
		settings.stagingDirectoryAbs=StringUtil.ensureEndsWith(settings.stagingDirectoryAbs,PathResolver.SEPARATOR);
		settings.extractTextDirectoryAbs=StringUtil.ensureEndsWith(settings.extractTextDirectoryAbs,PathResolver.SEPARATOR);
		settings.publishDirectoryAbs=StringUtil.ensureEndsWith(settings.publishDirectoryAbs,PathResolver.SEPARATOR);
		/*
		 * pre-process replacement text
		 */
		this.staticTemplater=new SimpleStaticTemplater(settings.staticTemplates);
		for(ReplacementBlock rb:settings.replacements){
			String replacementText=rb.getReplacementText();
			if((replacementText==null)||(replacementText.length()<1)){
				//try loading from file then
				String path=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,rb.getReplacementTextPath());
				replacementText=FileUtils.readString(path);
			}
			rb.setReplacementText(staticTemplater.process(replacementText));
		}		
	}
	
	public WebPublisherResults publish() throws Exception{
		WebPublisherResults results=new WebPublisherResults();
		FileWriter writer=null;
		BufferedReader reader=null;
		try{
			//set directory locations
			File publishDirectory=new File(settings.publishDirectoryAbs);
			//do these first so any changes are included in the link map
			if(settings.rebuildPagesFromOpml){PagesFromOPML.writePages(settings);}
			if(settings.rebuildPagesFromCsv){PagesFromCSV.writePages(settings);};
			//list of files to skip for the link map
			ArrayList<String> skipList=buildSkipList(settings);
			//get all html files
			ArrayList<File> files=FileUtils.getAllFilesRecursive(publishDirectory,new HtmlFileFilter());
			PageDataCollection allPages=new PageDataCollection();
			for(File file:files){
				PageData currentPage=new PageData();
				//set paths
				String currentFilePathAbs=file.getAbsolutePath();
				String currentFilePathRel=PathResolver.getRelativePath(publishDirectory.getAbsolutePath(),currentFilePathAbs);
				String currentFileStagingPathAbs=PathResolver.getAbsolutePath(settings.stagingDirectoryAbs,currentFilePathRel);
				String currentFilePublishPathAbs=PathResolver.getAbsolutePath(settings.publishDirectoryAbs,currentFilePathRel);
				//save full url of current page (used later)
				currentPage.setUrl(settings.siteMapBaseUrl+currentFilePathRel.substring(1).replaceAll(settings.rootDocument,"/"));
				//ensure staging file path exists
				File stagingFile=new File(currentFileStagingPathAbs);
				FileUtils.mkdirs(stagingFile);
				if(stagingFile.exists()){//very unlikely to happen
					stagingFile.delete();
				}
				//setup reader & writer
				reader=new BufferedReader(new FileReader(file));
				writer=new FileWriter(stagingFile);
				//setup things used while processing the file
				String line;//current line being read
				StringBuilder textExtract=new StringBuilder();//the plain text from the page
				boolean inReplacementBlock=false;//track whether in a replacement block
				String nextEndTag="";//if in replacement block, the tag to exist
				SimpleDynamicTemplater dynamicTemplater=new SimpleDynamicTemplater();
				//read the file line by line
				while((line=reader.readLine())!=null){
					if(inReplacementBlock){
						//skip lines until the end tag is reached
						if(line.equals(nextEndTag)){
							writer.write(line);
							writer.write(settings.newLine);
							inReplacementBlock=false;
						}//else skip the line
					}else{//writing a regular line - not in a replacement block
						/* The static and/or dynamic templates could be run against each line here.
						 * Right now I don't need that.
						 * However, this would allow the entire HTML document to support templates.
						 * That actually would be useful in a more robust system. 
						 * This would also mean templatePath and publishPath have to be different. */
						//first check for adding the line to the link map
			        	boolean skipLinkmap=false;
			        	if(skipList.contains(currentFilePathAbs)){
			        		skipLinkmap=true;
			        	}
			        	if(!skipLinkmap){
			        		ArrayList<String> urls=StringUtil.parseHref(line);
			        		for(String url:urls){
			        			String externalUrl=url.toLowerCase();
			        			String currentPageUrl=currentPage.getUrl().toLowerCase();
				        		ArrayList<String> pageList=results.linkMap.get(externalUrl);
								if(pageList==null){
									pageList=new ArrayList<String>();
							        pageList.add(currentPageUrl);
							        results.linkMap.put(externalUrl,pageList);
								}else{
									if(!pageList.contains(currentPageUrl)){
										pageList.add(currentPageUrl);
										results.linkMap.replace(externalUrl,pageList);
									}
								}
							}
						}
			        	//second check if for title
			        	if(line.contains("<title>")){
			        		currentPage.setTitle(line.replace("<title>","").replace("</title>",""));
						}
			        	//third check is for sitemap exclude tag 
						if(line.contains(settings.htmlBlocks.getSitemapExcludeTag())){
							currentPage.setSitemapInclude(false);
						}
						//fourth check is for topics
						if(line.contains(settings.htmlBlocks.getTopicLinkStartTag())){
							int startIndex=line.indexOf(settings.htmlBlocks.getTopicLinkStartTag())+settings.htmlBlocks.getTopicLinkStartTag().length();
							int endIndex=line.substring(startIndex).indexOf(settings.htmlBlocks.getTopicLinkEndTag());
							if(endIndex>-1){endIndex+=startIndex;}
							while((endIndex>0)&&(endIndex<line.length())){
								String topic=StringUtil.stripHtmlTags(line.substring(startIndex,endIndex));
								currentPage.addTopic(topic);
								startIndex=line.substring(endIndex).indexOf(settings.htmlBlocks.getTopicLinkStartTag());
								if(startIndex>-1){
									startIndex+=settings.htmlBlocks.getTopicLinkStartTag().length();
									endIndex=line.substring(startIndex).indexOf(settings.htmlBlocks.getTopicLinkEndTag());
									if(endIndex>-1){endIndex+=startIndex;}
								}else{
									endIndex=-1;
								}
							}
						}
						//fifth check is for breadcrumb
						if(line.equals(settings.htmlBlocks.getBreadcrumbStartTag())&&currentPage.getSitemapInclude()){
							while(!line.equals(settings.htmlBlocks.getBreadcrumbEndTag())){
								writer.write(line);
								writer.write(settings.newLine);
								int indexOf=line.indexOf("</a>");
								if(indexOf>0){//this should always fail for the first line
									line=line.substring(0,indexOf);
									indexOf=line.lastIndexOf(">");
									line=line.substring(indexOf+1);
									currentPage.addBreadcrumbLevel(line);
								}
								line=reader.readLine();
							}//exiting while -> line should be BreadcrumbEndTag which should fall into the next line
						}
						/*
						 * now write the line
						 * note: any other temporary text substitutions could go here
						 */
						writer.write(line);
						writer.write(settings.newLine);
						//extract the text if that's a thing that's supposed to happen
						if(settings.extractText){
							/*
							 * doing these in individual lines for debugging and because I don't care about performance
							 */
							//handle tables
							textExtract.append(StringUtil.stripHtmlTags(line));
							textExtract.append(settings.newLine);
						}
						//check if the line that was just written is the start of a replacement block
						ReplacementBlock rb=settings.replacements.findByStartTag(line);
						if(rb!=null){//using null checks for application logic FTW
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
					File publishFile=new File(currentFilePublishPathAbs);
					//copy files from staging to publish directory
					Files.copy(stagingFile.toPath(),publishFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
				}
				if(settings.extractText){
					String relPath=PathResolver.getRelativePath(settings.publishDirectoryAbs,file.getAbsolutePath());
					relPath=relPath.replace(PathResolver.SEPARATOR,"_");
					relPath=relPath.replace(".","_");
					while(relPath.startsWith("_")){
						relPath=relPath.substring(1);
					}
					String extractTextDirectoryAbs=settings.extractTextDirectoryAbs;
					if(!extractTextDirectoryAbs.endsWith(PathResolver.SEPARATOR)){
						extractTextDirectoryAbs=extractTextDirectoryAbs+PathResolver.SEPARATOR;
					}
					String txtFilePath=PathResolver.getAbsolutePath(extractTextDirectoryAbs,relPath);
					FileUtils.writeStringToFile(txtFilePath,textExtract.toString());
				}
				allPages.add(currentPage);
			}//end for (list of files)
			/*
			 * pages to rebuild after everything else has processed
			 */
			if(settings.buildExternalLinksPage){ExternalLinksPage.writePage(settings,results.linkMap);}
			if(settings.rebuildSitemap){SiteMap.writeSitemap(settings,allPages.getSitemapPages());}
			if(settings.buildTopicsPages){TopicsPages.writePages(settings,allPages.getTopicMap());}
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