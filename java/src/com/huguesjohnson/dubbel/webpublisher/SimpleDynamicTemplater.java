/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.text.DateFormat;
import java.util.ArrayList;

import com.huguesjohnson.dubbel.util.DateUtil;

public class SimpleDynamicTemplater{
	//list used to lookup tags
	private ArrayList<String> dynamicTemplates;

	//delimiters - can only be changed in constructor otherwise rebuilding the list is a headache
	public final static String DEFAULT_TEMPLATE_START="{";
	public final static String DEFAULT_TEMPLATE_END="}";
	private String templateStart=DEFAULT_TEMPLATE_START;
	private String templateEnd=DEFAULT_TEMPLATE_END;
	
	//the tags
	public final static String DEFAULT_TAG_DATETIME="DATETIME";
	private final static int TAG_DATETIME_INDEX=0;
	public void setTagDateTime(String tagDateTime){this.dynamicTemplates.set(TAG_DATETIME_INDEX,templateStart+tagDateTime+templateEnd);}
	public final static String DEFAULT_TAG_YEAR="YEAR";
	private final static int TAG_YEAR_INDEX=1;
	public void setTagYear(String tagYear){this.dynamicTemplates.set(TAG_YEAR_INDEX,templateStart+tagYear+templateEnd);}
	public final static String DEFAULT_TAG_CANONICAL="CANONICAL";
	private final static int TAG_CANONICAL_INDEX=2;
	public void setTagCanonical(String tagCanonical){this.dynamicTemplates.set(TAG_CANONICAL_INDEX,templateStart+tagCanonical+templateEnd);}

	//other settings
	private DateFormat dateTimeFormat=DateUtil.DF_YearMonthDayHourMinuteSecond;
	public void setDateTimeFormat(DateFormat df){this.dateTimeFormat=df;}
	
	
	public SimpleDynamicTemplater(){
		this.buildDynamicTemplatesList();
	}
	
	public SimpleDynamicTemplater(String templateStart,String templateEnd){
		this.templateStart=templateStart;
		this.templateEnd=templateEnd;
		this.buildDynamicTemplatesList();
	}
	
	
	private void buildDynamicTemplatesList(){
		this.dynamicTemplates=new ArrayList<String>(2);
		this.dynamicTemplates.add(TAG_DATETIME_INDEX,templateStart+DEFAULT_TAG_DATETIME+templateEnd);
		this.dynamicTemplates.add(TAG_YEAR_INDEX,templateStart+DEFAULT_TAG_YEAR+templateEnd);
		this.dynamicTemplates.add(TAG_CANONICAL_INDEX,templateStart+DEFAULT_TAG_CANONICAL+templateEnd);
	}	
	
	public String process(String s,String filePath,Settings settings){
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
				int dynamicIndex=this.dynamicTemplates.indexOf(match);
				if(dynamicIndex>=0){
					String dynamicReplace=match;//leave as-if something goes wrong
					//yes, I'm aware of switch statements
					if(dynamicIndex==TAG_DATETIME_INDEX){
						dynamicReplace=DateUtil.now(this.dateTimeFormat);
					}else if(dynamicIndex==TAG_YEAR_INDEX){
						dynamicReplace=DateUtil.now(DateUtil.DF_Year);
					}else if(dynamicIndex==TAG_CANONICAL_INDEX){
						if(filePath.endsWith((settings.rootDocument))){
							dynamicReplace=filePath.substring(settings.templateDirectory.length(),filePath.length()-(settings.rootDocument.length()-1));
						}else{
							dynamicReplace=filePath.substring(settings.templateDirectory.length(),filePath.length());
						}
					}
					//replace the template with the value
					sb.replace(startIndex,endIndex+1,dynamicReplace);
					//next starting point should be at the end of the block just replaced
					startIndex=startIndex+dynamicReplace.length();
					endIndex=startIndex+1;
				}else{//dynamicIndex<0
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