/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.ArrayList;
import java.util.List;

public class SiteMapEntry{
	public final static String DEFAULT_BREADCRUMB_PATH_SEPARATOR="/";
	
	//the actual URL this entry points to
	private String url;
	public void setUrl(String url){this.url=url;}
	public String getUrl(){return(this.url);}
	
	//the title of the page
	private String title;
	public String getTitle(){return(this.title);}
	public void setTitle(String title){
		this.title=title;
		this.trimTitle();
	}

	//the path to the entry in the site hierarchy
	private List<String> breadcrumb;
	public List<String> getBreadcumb(){return(this.breadcrumb);}
	public void addBreadcrumbLevel(String level){this.breadcrumb.add(level); this.trimTitle();}
	public int getDepth(){return(this.breadcrumb.size());}
	public String getBreadcrumbString(){return(this.getBreadcrumbString(DEFAULT_BREADCRUMB_PATH_SEPARATOR));}
	public String getBreadcrumbString(String pathSeparator){
		StringBuilder sb=new StringBuilder();
		int s=this.breadcrumb.size();
		for(int i=0;i<s;i++){
			sb.append(this.breadcrumb.get(i));
			sb.append(pathSeparator);
		}
		return(sb.toString());
	}

	//trim occurrences of the breadcrumb from the title
	private void trimTitle(){
		String shortTitle=this.title;
		if((shortTitle==null)||(shortTitle.length()<1)){return;}
		int s=this.breadcrumb.size();
		for(int i=0;i<s;i++){
			String bc=this.breadcrumb.get(i);
			int l=bc.length();
			if(shortTitle.length()>l){
				if(shortTitle.substring(0,l).equalsIgnoreCase(bc)){
					shortTitle=shortTitle.substring(l);
					char c=shortTitle.charAt(0);
					while((shortTitle.length()>0)&&(!Character.isLetterOrDigit(c))){
						shortTitle=shortTitle.substring(1);
						c=shortTitle.charAt(0);
					}
				}
			}
		}
		this.title=shortTitle;
	}
	
	public SiteMapEntry(){
		url="";
		title="";
		this.breadcrumb=new ArrayList<String>();
	}
}