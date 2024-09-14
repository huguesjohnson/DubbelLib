/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.huguesjohnson.dubbel.util.DateUtil;

/*
 * This is in the webpublisher package because it is built to support that project specifically.
 * This is not intended to be a general-purpose template engine but could work for simple cases.
 */
public class SimpleTemplater{
	//default delimiter
	public final static String DEFAULT_TEMPLATE_START="{";
	public final static String DEFAULT_TEMPLATE_END="}";

	//there's probably some enum-y way to accomplish this
	public final static String DYNAMIC_TAG_DATETIME=DEFAULT_TEMPLATE_START+"DATETIME"+DEFAULT_TEMPLATE_END;
	private final static int DYNAMIC_TAG_DATETIME_INDEX=0;
	public final static String DYNAMIC_TAG_YEAR=DEFAULT_TEMPLATE_START+"YEAR"+DEFAULT_TEMPLATE_END;
	private final static int DYNAMIC_TAG_YEAR_INDEX=1;
	
	private Map<String,String> staticTemplates;
	private ArrayList<String> dynamicTemplates;
	private String templateStart=DEFAULT_TEMPLATE_START;
	private String templateEnd=DEFAULT_TEMPLATE_END;
	private DateFormat dateTimeFormat=DateUtil.DF_YearMonthDayHourMinuteSecond;
	
	public SimpleTemplater(){
		//initialize to avoid null pointers later
		this.staticTemplates=new HashMap<String,String>();
		this.buildDynamicTemplatesList();
	}
	
	//the one I actually use
	public SimpleTemplater(Map<String,String> staticTemplates){
		this.staticTemplates=staticTemplates;
		this.buildDynamicTemplatesList();
	}

	//one with everything just in case
	public SimpleTemplater(Map<String,String> staticTemplates,String templateStart,String templateEnd,DateFormat dateTimeFormat){
		this.staticTemplates=staticTemplates;
		this.templateStart=templateStart;
		this.templateEnd=templateEnd;
		this.dateTimeFormat=dateTimeFormat;
		this.buildDynamicTemplatesList();
	}
	
	private void buildDynamicTemplatesList(){
		this.dynamicTemplates=new ArrayList<String>(2);
		this.dynamicTemplates.add(DYNAMIC_TAG_DATETIME_INDEX,DYNAMIC_TAG_DATETIME);
		this.dynamicTemplates.add(DYNAMIC_TAG_YEAR_INDEX,DYNAMIC_TAG_YEAR);
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
				}else{
					int dynamicIndex=this.dynamicTemplates.indexOf(match);
					if(dynamicIndex>=0){
						String dynamicReplace=match;//leave as-if something goes wrong
						//yes, I'm aware of switch statements
						if(dynamicIndex==DYNAMIC_TAG_DATETIME_INDEX){
							dynamicReplace=DateUtil.now(this.dateTimeFormat);
						}else if(dynamicIndex==DYNAMIC_TAG_YEAR_INDEX){
							dynamicReplace=DateUtil.now(DateUtil.DF_Year);
						}
						//replace the template with the value
						sb.replace(startIndex,endIndex+1,dynamicReplace);
						//next starting point should be at the end of the block just replaced
						startIndex=startIndex+dynamicReplace.length();
						endIndex=startIndex+1;
					}else{
						//increment indexes
						startIndex++;
						endIndex=startIndex+1;
					}
				}
			}else{ //endIndex<startIndex so there is a mismatched tag
				//startIndex++;
				endIndex=startIndex+1;
			}
			//search for next template match
			startIndex=sb.indexOf(this.templateStart,startIndex);
			endIndex=sb.indexOf(this.templateEnd,endIndex);
		}
		return(sb.toString());
	}
	
}