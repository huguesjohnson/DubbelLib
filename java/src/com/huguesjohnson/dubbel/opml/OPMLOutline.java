/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.opml;

import java.util.ArrayList;
import java.util.List;

import com.huguesjohnson.dubbel.util.DateUtil;

public class OPMLOutline implements Comparable<OPMLOutline>{
	private	List<OPMLOutline> children;
	private String text;
	private String type;
	private Boolean isComment;
	private Boolean isBreakpoint;
	private String created;
	private long createdEpoch=0L;
	private String language;
	private String category;
	private String xmlUrl;
	private String description;
	private String htmlUrl;
	private String title;
	private String version;
	private String url;

	public OPMLOutline(){
		this.children=new ArrayList<OPMLOutline>();
	}

	public void setCreated(String created){
		this.created=created;
		this.createdEpoch=DateUtil.toEpochTime(created,DateUtil.DF_RFC822);
		if(this.createdEpoch==0L){
			//try again I guess, if still 0 after this then the date passed is definitely invalid
			this.createdEpoch=DateUtil.toEpochTime(created,DateUtil.DF_RFC822_OPML_ALT);
		}	
	}

	@Override
	public int compareTo(OPMLOutline arg0){
		String titleCompare=arg0.getTitle();
		if(titleCompare==null){
			if(this.title==null){
				return(0);
			}else{
				return(1);
			}
		}else{
			if(this.title==null){
				return(-1);
			}else{
				return(this.title.compareToIgnoreCase(titleCompare));
			}
		}
	}	
	
	/* autogenerated code below */

	public OPMLOutline(List<OPMLOutline> children, String text, String type, boolean isComment, boolean isBreakpoint,
			String created, String category, String xmlUrl, String description, String htmlUrl, String title,
			String version) {
		super();
		this.children = children;
		this.text = text;
		this.type = type;
		this.isComment = isComment;
		this.isBreakpoint = isBreakpoint;
		this.setCreated(created);//ok, not autogenerated
		this.category = category;
		this.xmlUrl = xmlUrl;
		this.description = description;
		this.htmlUrl = htmlUrl;
		this.title = title;
		this.version = version;
	}

	public List<OPMLOutline> getChildren() {
		return children;
	}

	public void setChildren(List<OPMLOutline> children) {
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean isComment() {
		return isComment;
	}

	public void setComment(Boolean isComment) {
		this.isComment = isComment;
	}

	public Boolean isBreakpoint() {
		return isBreakpoint;
	}

	public void setBreakpoint(Boolean isBreakpoint) {
		this.isBreakpoint = isBreakpoint;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getXmlUrl() {
		return xmlUrl;
	}

	public void setXmlUrl(String xmlUrl) {
		this.xmlUrl = xmlUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreated() {
		return created;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getCreatedEpoch() {
		return createdEpoch;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}