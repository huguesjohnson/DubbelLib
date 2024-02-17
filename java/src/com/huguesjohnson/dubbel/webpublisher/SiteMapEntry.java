/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

public class SiteMapEntry{
	public String url;
	public String title;
	public String breadcrumb;
	public int depth;
	
	public SiteMapEntry(){
		url="";
		title="";
		breadcrumb="";
		depth=0;
	}
}
