/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.opml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OPMLObject{
	private String version;
	private OPMLHead head;
	private List<OPMLOutline> body;

	public OPMLObject(){
		this.head=new OPMLHead();
		this.body=new ArrayList<OPMLOutline>();
	}
	
	public void sortOutlines(){
		this.sortOutlines(this.getBody());
	}

	private void sortOutlines(List<OPMLOutline> outlines){
		Collections.sort(outlines);
		for(OPMLOutline outline:outlines){
			List<OPMLOutline> children=outline.getChildren();
			if((children!=null)&&(children.size()>0)){
				sortOutlines(children);
			}
		}
	}
	
	public String toXml(){
		StringBuilder sb=new StringBuilder();
		String newLine=System.lineSeparator();
		sb.append(OPMLConstants.XML_VERSION);
		sb.append(newLine);
		//start of <opml>
		sb.append("<");
		sb.append(OPMLConstants.ELEMENT_OPML);
		sb.append(" version=\"");
		if((this.version!=null)&&(this.version.length()>0)){
			sb.append(this.version);
		}else{
			sb.append(OPMLConstants.OPML_DEFAULT_VERSION);
		}
		sb.append("\">");
		sb.append(newLine);
		//start of <head>
		sb.append("<");
		sb.append(OPMLConstants.ELEMENT_HEAD);
		sb.append(">");
		sb.append(newLine);
		//elements in the head section
		this.appendElementXml(sb,head.getTitle(),OPMLConstants.ELEMENT_TITLE,newLine);
		this.appendElementXml(sb,head.getDateCreated(),OPMLConstants.ELEMENT_DATECREATED,newLine);
		this.appendElementXml(sb,head.getDateModified(),OPMLConstants.ELEMENT_DATEMODIFIED,newLine);
		this.appendElementXml(sb,head.getOwnerName(),OPMLConstants.ELEMENT_OWNERNAME,newLine);
		this.appendElementXml(sb,head.getOwnerEmail(),OPMLConstants.ELEMENT_OWNEREMAIL,newLine);
		this.appendElementXml(sb,head.getOwnerId(),OPMLConstants.ELEMENT_OWNERID,newLine);
		this.appendElementXml(sb,head.getDocs(),OPMLConstants.ELEMENT_DOCS,newLine);
		this.appendElementXml(sb,head.getExpansionState(),OPMLConstants.ELEMENT_EXPANSIONSTATE,newLine);
		this.appendElementXml(sb,head.getVertScrollState(),OPMLConstants.ELEMENT_VERTSCROLLSTATE,newLine);
		this.appendElementXml(sb,head.getWindowTop(),OPMLConstants.ELEMENT_WINDOWTOP,newLine);
		this.appendElementXml(sb,head.getWindowLeft(),OPMLConstants.ELEMENT_WINDOWLEFT,newLine);
		this.appendElementXml(sb,head.getWindowRight(),OPMLConstants.ELEMENT_WINDOWRIGHT,newLine);
		this.appendElementXml(sb,head.getWindowBottom(),OPMLConstants.ELEMENT_WINDOWBOTTOM,newLine);
		//end of <head>
		sb.append("</");
		sb.append(OPMLConstants.ELEMENT_HEAD);
		sb.append(">");
		sb.append(newLine);
		//start of <body>
		sb.append("<");
		sb.append(OPMLConstants.ELEMENT_BODY);
		sb.append(">");
		sb.append(newLine);
		//outlines
		this.appendOutlines(sb,this.getBody(),newLine);
		//end of <body>
		sb.append("</");
		sb.append(OPMLConstants.ELEMENT_BODY);
		sb.append(">");
		sb.append(newLine);
		//end of <opml>
		sb.append("</");
		sb.append(OPMLConstants.ELEMENT_OPML);
		sb.append(">");
		//done
		return(sb.toString());
	}
	
	private void appendElementXml(StringBuilder sb,Integer element,String constant,String newLine){
		if(element!=null){
			appendElementXml(sb,element.toString(),constant,newLine);
		}
	}	
	
	private void appendElementXml(StringBuilder sb,String element,String constant,String newLine){
		if((element!=null)&&(element.length()>0)){
			sb.append("<");
			sb.append(constant);
			sb.append(">");
			sb.append(element);
			sb.append("</");
			sb.append(constant);
			sb.append(">");
			sb.append(newLine);
		}			
	}
	
	private void appendOutlines(StringBuilder sb,List<OPMLOutline> outlineList,String newLine){
		for(OPMLOutline outline:outlineList){
			sb.append("<");
			sb.append(OPMLConstants.ELEMENT_OUTLINE);
			//elements in the outline section
			this.appendAttribute(sb,outline.getTitle(),OPMLConstants.ATTRIBUTE_TITLE);
			this.appendAttribute(sb,outline.getText(),OPMLConstants.ATTRIBUTE_TEXT);
			this.appendAttribute(sb,outline.getType(),OPMLConstants.ATTRIBUTE_TYPE);
			this.appendAttribute(sb,outline.isComment(),OPMLConstants.ATTRIBUTE_ISCOMMENT);
			this.appendAttribute(sb,outline.isBreakpoint(),OPMLConstants.ATTRIBUTE_ISBREAKPOINT);
			this.appendAttribute(sb,outline.getCreated(),OPMLConstants.ATTRIBUTE_CREATED);
			this.appendAttribute(sb,outline.getLanguage(),OPMLConstants.ATTRIBUTE_LANGUAGE);
			this.appendAttribute(sb,outline.getCategory(),OPMLConstants.ATTRIBUTE_CATEGORY);
			this.appendAttribute(sb,outline.getXmlUrl(),OPMLConstants.ATTRIBUTE_XMLURL);
			this.appendAttribute(sb,outline.getDescription(),OPMLConstants.ATTRIBUTE_DESCRIPTION);
			this.appendAttribute(sb,outline.getHtmlUrl(),OPMLConstants.ATTRIBUTE_HTMLURL);
			this.appendAttribute(sb,outline.getUrl(),OPMLConstants.ATTRIBUTE_URL);
			this.appendAttribute(sb,outline.getVersion(),OPMLConstants.ATTRIBUTE_VERSION);
			//recursively add children if there are any
			List<OPMLOutline> children=outline.getChildren();
			if((children!=null)&&(children.size()>0)){
				sb.append(">");
				sb.append(newLine);
				//add child nodes
				this.appendOutlines(sb,children,newLine);
				//close out this outline
				sb.append("</");
				sb.append(OPMLConstants.ELEMENT_OUTLINE);
				sb.append(">");
				sb.append(newLine);
				//"</outline>\n" + 
			}else{
				sb.append("/>");
				sb.append(newLine);
			}
		}
	}
	
	private void appendAttribute(StringBuilder sb,String attribute,String constant){
		if((attribute!=null)&&(attribute.length()>0)){
			sb.append(" ");
			sb.append(constant);
			sb.append("=\"");
			sb.append(attribute);
			sb.append("\"");
		}		
	}
	
	private void appendAttribute(StringBuilder sb,Boolean attribute,String constant){
		if(attribute!=null){
			if(attribute.booleanValue()){
				appendAttribute(sb,"true",constant);
			}else{
				appendAttribute(sb,"false",constant);
			}
		}		
	}	
	
	/* autogenerated code below */

	public OPMLObject(OPMLHead head, List<OPMLOutline> body) {
		super();
		this.head = head;
		this.body = body;
	}


	public OPMLHead getHead() {
		return head;
	}


	public void setHead(OPMLHead head) {
		this.head = head;
	}


	public List<OPMLOutline> getBody() {
		return body;
	}


	public void setBody(List<OPMLOutline> body) {
		this.body = body;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}