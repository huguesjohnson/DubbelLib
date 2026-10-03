/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.converters;

import java.io.File;
import java.io.IOException;

import com.huguesjohnson.dubbel.file.FileUtils;

//this, by design, does not create an entire html document
//it is built for what I need, which is converting markdown to an html fragment and escaping characters
public abstract class MarkdownToHTML{

	public static String convertFromPath(String path) throws IOException{
		return(convert(FileUtils.readString(path)));
	}
	
	public static String convertFromFile(File f) throws IOException{
		return(convert(FileUtils.readString(f)));
	}
	
	//this is not super efficient
	public static String convert(String md){
		if((md==null)||(md.length()<1)){return("");}
		String html=new String(md);
		/*
		 * escape stuff
		 */
		html=html.replace("\\&","&");//deal with inconsistent export behavior
		html=html.replace("&","&amp;");//first for obvious reasons
		html=html.replace("\\>","&gt;");
		html=html.replace("\\<","&lt;");
		html=html.replace("“","&quot;");
		html=html.replace("”","&quot;");
		html=html.replace("\"","&quot;");
		/*
		 * fix other characters
		 */		
		html=html.replace("’","'");
		html=html.replace("‘","'");
		html=html.replace("\\-","-");
		html=html.replace("\\.",".");
		html=html.replace("\\$","$");
		html=html.replace("\\=","=");
		html=html.replace("…","...");
		/*
		 * stuff with *s has to handled in the right order
		 */
		html=html.replace("## **","<h2>");
		html=html.replace("**","</h2>");
		html=html.replace("* ","<li>");
		html=html.replace("&quot;*","&quot;<i>");//specific to how I format things, not very reusable to others
		html=html.replace("*&quot;","</i>&quot;");//same
		/*
		 * convert links
		 * this will fail if the markdown isn't structured as expected
		 */
		html=html.replaceAll("\\[([^\\]]+)\\]\\((https?://[^\\)]+)\\)","<a href=\"$2\">$1</a>");		
		/*
		 * tagging
		 */
		String lf=System.lineSeparator();
		String[] lines=html.split(lf);
		StringBuilder sb=new StringBuilder();
		for(int lineNumber=0;lineNumber<lines.length;lineNumber++){
			String line=lines[lineNumber];
			if(line.length()>0){
				if(line.startsWith("<h2>")){
					sb.append(lines[lineNumber]);//no changes to h2 lines
				}else{
					//start of bullet list?
					if(line.startsWith("<li>")){
						sb.append("<ul>");
						sb.append(lf);
						sb.append(line);
						sb.append("</li>");
						sb.append(lf);
						//work to end of list
						while(lines[lineNumber+1].startsWith("<li>")){
							lineNumber++;
							line=lines[lineNumber];
							sb.append(line);
							sb.append("</li>");
							sb.append(lf);
						}
						sb.append("</ul>");
					}else if(line.matches("\\d+\\..*")){//check for ordered list
						sb.append("<ol>");
						sb.append(lf);
						sb.append("<li>");
						int indexOf=line.indexOf(". ");
						sb.append(line.substring(indexOf+2));
						sb.append("</li>");
						sb.append(lf);
						//work to end of list
						while(lines[lineNumber+1].matches("\\d+\\..*")){
							lineNumber++;
							line=lines[lineNumber];
							sb.append("<li>");
							indexOf=line.indexOf(". ");
							sb.append(line.substring(indexOf+2));
							sb.append("</li>");
							sb.append(lf);
						}
						sb.append("</ol>");
						
					}else{
						//regular line
						sb.append("<p>");
						sb.append(lines[lineNumber]);
						sb.append("</p>");
					}
				}
			}
			//line feed after each line
			if(lineNumber<(lines.length-1)){
				sb.append(lf);
			}
		}
		return(sb.toString());
	}
	
}