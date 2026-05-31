/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.ArrayList;
import java.util.List;

public class PageData{
	//TODO - put this in settings or whatever
	public final static String DEFAULT_BREADCRUMB_PATH_SEPARATOR="/";
	
	/*
	 * The title of the page
	 * And helper to return a version without duplicate breadcrumb parent paths
	 */
	private String title;
	public String getTitle(){return(this.title);}
	public void setTitle(String title){this.title=title;}
	public boolean hasTitle(){return((this.title!=null)&&(this.title.length()>0));}
	
	//trim occurrences of the breadcrumb from the title
	private String trimTitle=null;
	public String getTrimTitle(){
		if((this.trimTitle!=null)&&(this.trimTitle.length()>0)){return(this.trimTitle);}
		if(!this.hasTitle()){return("");}
		this.trimTitle=this.title;
		int s=this.breadcrumb.size();
		for(int i=0;i<s;i++){
			String bc=this.breadcrumb.get(i);
			int l=bc.length();
			if(this.trimTitle.length()>l){
				if(this.trimTitle.substring(0,l).equalsIgnoreCase(bc)){
					this.trimTitle=this.trimTitle.substring(l);
					char c=this.trimTitle.charAt(0);
					while((this.trimTitle.length()>0)&&(!Character.isLetterOrDigit(c))){
						this.trimTitle=this.trimTitle.substring(1);
						c=this.trimTitle.charAt(0);
					}
				}
			}
		}
		return(this.trimTitle);
	}

	/*
	 * The actual full url to the page
	 */
	private String url;
	public void setUrl(String url){this.url=url;}
	public String getUrl(){return(this.url);}
	
	/*
	 * The path to the entry in the site hierarchy
	 */
	private List<String> breadcrumb;
	public List<String> getBreadcumb(){return(this.breadcrumb);}
	public void addBreadcrumbLevel(String level){this.breadcrumb.add(level);}
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
	
	/*
	 * Whether to include this page in the sitemap
	 */
	private boolean sitemapInclude;
	public boolean getSitemapInclude(){return(this.sitemapInclude);}
	public void setSitemapInclude(boolean sitemapInclude){this.sitemapInclude=sitemapInclude;}
	
	/*
	 * Linked topics
	 */
	private ArrayList<String> topics;
	public List<String> getTopics(){return(this.topics);}
	public void addTopic(String topic){this.topics.add(topic);}
	
	/*
	 * Constructors
	 */
	
	public PageData(){
		this.url="";
		this.title="";
		this.sitemapInclude=true;
		this.topics=new ArrayList<String>();
		this.breadcrumb=new ArrayList<String>();
	}
}