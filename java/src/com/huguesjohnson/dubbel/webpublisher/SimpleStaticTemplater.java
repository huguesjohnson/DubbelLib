/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

/*
 * This is in the webpublisher package because it is built to support that project specifically.
 * This is not intended to be a general-purpose template engine but could work for simple cases.
 */
public class SimpleStaticTemplater{
	//default delimiter
	public final static String DEFAULT_TEMPLATE_START="{";
	public final static String DEFAULT_TEMPLATE_END="}";

	private Map<String,String> staticTemplates;
	private String templateStart=DEFAULT_TEMPLATE_START;
	private String templateEnd=DEFAULT_TEMPLATE_END;
	
	public SimpleStaticTemplater(){
		//initialize to avoid null pointers later
		this.staticTemplates=new HashMap<String,String>();
	}
	
	//the one I actually use
	public SimpleStaticTemplater(Map<String,String> staticTemplates){
		this.staticTemplates=staticTemplates;
	}

	//one with everything just in case
	public SimpleStaticTemplater(Map<String,String> staticTemplates,String templateStart,String templateEnd,DateFormat dateTimeFormat){
		this.staticTemplates=staticTemplates;
		this.templateStart=templateStart;
		this.templateEnd=templateEnd;
	}
	
	public String process(String s){
		StringBuffer sb=new StringBuffer(s);
		int startIndex=sb.indexOf(this.templateStart);
		int endIndex=sb.indexOf(this.templateEnd);
		while(
		(startIndex>=0)&&
		(startIndex<sb.length())&&
		(endIndex>0)&&
		(endIndex<sb.length())
		)
		{
			if(endIndex>startIndex){
				//we have something
				String match=sb.substring(startIndex,endIndex+1);
				//test if this is a static template
				String staticReplace=this.staticTemplates.getOrDefault(match,null);
				if(staticReplace!=null){
					//replace the template with the value
					sb.replace(startIndex,endIndex+1,staticReplace);
					//next starting point should be at the end of the block just replaced
					startIndex=startIndex+staticReplace.length();
					endIndex=startIndex+1;
				}else{ //no match
					//increment indexes
					startIndex++;
					endIndex=startIndex+1;
				}
			}else{ //endIndex<startIndex so there is a mismatched tag
				endIndex=startIndex+1;
			}
			//search for next template match
			startIndex=sb.indexOf(this.templateStart,startIndex);
			endIndex=sb.indexOf(this.templateEnd,endIndex);
		}
		return(sb.toString());
	}
}