/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

//blocks of html with default values that can be overridden 
public class HTMLBlocks{
	public final static String DEFAULT_DATA_START_TAG="<!-- start data from csv -->";
	private String dataStartTag=DEFAULT_DATA_START_TAG;
	public void setDataStartTag(String dataStartTag){this.dataStartTag=dataStartTag;}
	public String getDataStartTag(){return(this.dataStartTag);}
	
	public final static String DEFAULT_DATA_END_TAG="<!-- end data from csv -->";
	private String dataEndTag=DEFAULT_DATA_END_TAG;
	public void setDataEndTag(String dataEndTag){this.dataEndTag=dataEndTag;}
	public String getDataEndTag(){return(this.dataEndTag);}

	public final static String DEFAULT_RSSLINKS_START_TAG="<!-- start rss links -->";
	private String rssLinksStartTag=DEFAULT_RSSLINKS_START_TAG;
	public void setRssLinksStartTag(String rssLinksStartTag){this.rssLinksStartTag=rssLinksStartTag;}
	public String getRssLinksStartTag(){return(this.rssLinksStartTag);}

	public final static String DEFAULT_RSSLINKS_END_TAG="<!-- end rss links -->";
	private String rssLinksEndTag=DEFAULT_RSSLINKS_END_TAG;
	public void setRssLinksEndTag(String rssLinksEndTag){this.rssLinksEndTag=rssLinksEndTag;}
	public String getRssLinksEndTag(){return(this.rssLinksEndTag);}

	public final static String DEFAULT_SITEMAP_START_TAG="<!-- start sitemap -->";
	private String sitemapStartTag=DEFAULT_SITEMAP_START_TAG;
	public void setSitemapStartTag(String sitemapStartTag){this.sitemapStartTag=sitemapStartTag;}
	public String getSitemapStartTag(){return(this.sitemapStartTag);}

	public final static String DEFAULT_SITEMAP_END_TAG="<!-- end sitemap -->";
	private String sitemapEndTag=DEFAULT_SITEMAP_END_TAG;
	public void setSitemapEndTag(String sitemapEndTag){this.sitemapEndTag=sitemapEndTag;}
	public String getSitemapEndTag(){return(this.sitemapEndTag);}

	public final static String DEFAULT_SITEMAP_EXCLUDE_TAG="<!-- sitemap exclude -->";
	private String sitemapExcludeTag=DEFAULT_SITEMAP_EXCLUDE_TAG;
	public void setSitemapExcludeTag(String sitemapExcludeTag){this.sitemapExcludeTag=sitemapExcludeTag;}
	public String getSitemapExcludeTag(){return(this.sitemapExcludeTag);}

	public final static String DEFAULT_BREADCRUMB_START_TAG="<!-- start breadcrumb -->";
	private String breadcrumbStartTag=DEFAULT_BREADCRUMB_START_TAG;
	public void setBreadcrumbStartTag(String breadcrumbStartTag){this.breadcrumbStartTag=breadcrumbStartTag;}
	public String getBreadcrumbStartTag(){return(this.breadcrumbStartTag);}

	public final static String DEFAULT_BREADCRUMB_END_TAG="<!-- end breadcrumb -->";
	private String breadcrumbEndTag=DEFAULT_BREADCRUMB_END_TAG;
	public void setBreadcrumbEndTag(String breadcrumbEndTag){this.breadcrumbEndTag=breadcrumbEndTag;}
	public String getBreadcrumbEndTag(){return(this.breadcrumbEndTag);}
	
	public final static String DEFAULT_EXTERNALLINKS_START_TAG="<!-- start external links -->";
	private String externalLinksStartTag=DEFAULT_EXTERNALLINKS_START_TAG;
	public void setExternalLinksStartTag(String externalLinksStartTag){this.externalLinksStartTag=externalLinksStartTag;}
	public String getExternalLinksStartTag(){return(this.externalLinksStartTag);}

	public final static String DEFAULT_EXTERNALLINKS_END_TAG="<!-- end external links -->";
	private String externalLinksEndTag=DEFAULT_EXTERNALLINKS_END_TAG;
	public void setExternalLinksEndTag(String externalLinksEndTag){this.externalLinksEndTag=externalLinksEndTag;}
	public String getExternalLinksEndTag(){return(this.externalLinksEndTag);}

	public final static String DEFAULT_SITEMAP_XML_START="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
	private String siteMapXmlStart=DEFAULT_SITEMAP_XML_START;
	public void setSitemapXmlStart(String siteMapXmlStart){this.siteMapXmlStart=siteMapXmlStart;}
	public String getSitemapXmlStart(){return(this.siteMapXmlStart);}

	public final static String DEFAULT_TOPICS_START_TAG="<!-- start topics grid -->";
	private String topicsStartTag=DEFAULT_TOPICS_START_TAG;
	public void setTopicsStartTag(String topicsStartTag){this.topicsStartTag=topicsStartTag;}
	public String getTopicsStartTag(){return(this.topicsStartTag);}

	public final static String DEFAULT_TOPICS_END_TAG="<!-- end topics grid -->";
	private String topicsEndTag=DEFAULT_TOPICS_END_TAG;
	public void setTopicsEndTag(String topicsEndTag){this.topicsEndTag=topicsEndTag;}
	public String getTopicsEndTag(){return(this.topicsEndTag);}

	public final static String DEFAULT_TOPICLINK_START_TAG="<span class=\"badge\">";
	private String topicLinkStartTag=DEFAULT_TOPICLINK_START_TAG;
	public void setTopicLinkStartTag(String topicStartLinkTag){this.topicLinkStartTag=topicStartLinkTag;}
	public String getTopicLinkStartTag(){return(this.topicLinkStartTag);}

	public final static String DEFAULT_TOPICLINK_END_TAG="</a></span>";
	private String topicLinkEndTag=DEFAULT_TOPICLINK_END_TAG;
	public void setTopicLinkEndTag(String topicEndLinkTag){this.topicLinkEndTag=topicEndLinkTag;}
	public String getTopicLinkEndTag(){return(this.topicLinkEndTag);}
	
	public HTMLBlocks(){}
}