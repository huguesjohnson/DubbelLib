/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

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
}