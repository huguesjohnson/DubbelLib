/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.util.ArrayList;

public abstract class StringUtil {

	public static String stripHtmlTags(String htmlString){
		String basicText=htmlString.replaceAll("(?i)<th[^>]*>"," | ");
		basicText=basicText.replaceAll("(?i)<td[^>]*>"," | ");
		basicText=basicText.replaceAll("(?i)</tr>"," |\n");
		//handle lists
		basicText=basicText.replaceAll("(?i)<li[^>]*>","\n• ");
		//handle breaks
		basicText=basicText.replaceAll("(?i)<(/?(?:p|div|br|h[1-6]))[^>]*>","\n");
		//handle all remaining tags
		basicText=basicText.replaceAll("<[^>]*>","");
		basicText=basicText.replaceAll("[ \t]+"," ");
		basicText=basicText.replaceAll("(?m)^\\s+$","");
		basicText=basicText.replaceAll("\n{3,}", "\n\n");
		basicText=basicText.replace("&nbsp;", " ");
		basicText=basicText.replace("&amp;", "&");
		basicText=basicText.replace("&quot;", "\"");
		basicText=basicText.replace("&lt;", "<");
		basicText=basicText.replace("&gt;", ">");
		basicText=basicText.replace("&apos;", "'");
		return(basicText);
	}
	
	public final static ArrayList<String> parseHref(String s){
		ArrayList<String> hrefs=new ArrayList<String>();
		int indexOf=s.toLowerCase().indexOf("href=\"");
		while(indexOf>0){
			int startIndex=indexOf+6;//href=" length
			int endIndex=s.indexOf("\"",startIndex);//next "
			//found end
			if((endIndex>0)&&(endIndex<s.length())){
				hrefs.add(s.substring(startIndex,endIndex));
				if(endIndex<s.length()){
					int next=s.substring(endIndex).toLowerCase().indexOf("href=\"");
					if(next>0){
						indexOf=endIndex+next;
					}else{//there is not another occurrence
						indexOf=-1;
					}
				}else{//at the end of the string
					indexOf=-1;
				}
			}else{//did not find an end
				indexOf=-1;
			}
		}
		return(hrefs);
	}
	
	public final static String ensureEndsWith(String s,String end){
		if((s==null)||(end==null)){return(s);}
		if(s.endsWith(end)){return(s);}
		return(s+end);
	}
}