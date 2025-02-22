/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.opml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/*
 * This will also parse an OPML file that isn't perfectly structured.
 * 
 * For example:
 * -This doesn't care what order the <head> and <body> elements are in.
 * -Actually this doesn't care whether there is <body> element at all.
 * --A document with a bunch of <outline> elements and no <body> would be handled.
 * --It could also read documents missing <head> and/or <outline> without any issues.
 * -The specification has rules about <ownerId> that this does not care about.
 * 
 * Reminder, this is called "Parser" and not "Validator".
 */
public class OPMLParser{
	
	public static OPMLObject parse(File f) throws OPMLParseException{
		try{
			return(parse(new FileInputStream(f)));
		}catch(FileNotFoundException fnfx){
			OPMLParseException opmlEx=new OPMLParseException(fnfx.getMessage(),fnfx);
			throw(opmlEx);
		}
	}
	
	public static OPMLObject parse(InputStream in) throws OPMLParseException{
		OPMLObject opml=new OPMLObject();
		try{
			XMLInputFactory xin=XMLInputFactory.newInstance();
			XMLStreamReader xmlReader=xin.createXMLStreamReader(in);
			while(xmlReader.hasNext()){
				int eventType=xmlReader.next(); 
				if(eventType==XMLStreamConstants.START_ELEMENT){
					if(xmlReader.hasName()){
						String name=xmlReader.getName().toString();
						if(name.equals(OPMLConstants.ELEMENT_OPML)){//start of <opml>
							//find the version string
							int attributeCount=xmlReader.getAttributeCount();
							for(int i=0;i<attributeCount;i++){
								String attributeName=xmlReader.getAttributeName(i).toString();
								if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_VERSION)){
									opml.setVersion(xmlReader.getAttributeValue(i));
								}
							}
						}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_HEAD)){//start of <head>
							opml.setHead(parseHead(xmlReader));
						}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_OUTLINE)){//start of <head>
							opml.getBody().add(parseOutline(xmlReader));
						}
	            	}
	            }
	        }
			return(opml);
		}catch(Exception x){
			OPMLParseException opmlEx=new OPMLParseException(x.getMessage(),x);
			throw(opmlEx);
		}
	}
	
	private static OPMLHead parseHead(XMLStreamReader xmlReader) throws XMLStreamException{
		OPMLHead head=new OPMLHead();
		boolean atEnd=false;
        while((xmlReader.hasNext())&&(!atEnd)){
        	int eventType=xmlReader.next();
            if(eventType==XMLStreamConstants.END_ELEMENT){//end of <head>?
            	if(xmlReader.hasName()){
            		String name=xmlReader.getName().toString();
            		if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_HEAD)){//yes, at the end of <head>
            			atEnd=true;
            		}
            	}
            }else if(eventType==XMLStreamConstants.START_ELEMENT){//start of element within <head>
            	if(xmlReader.hasName()){
            		String name=xmlReader.getName().toString();
            		if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_TITLE)){
            			head.setTitle(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_DATECREATED)){
            			head.setDateCreated(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_DATEMODIFIED)){
            			head.setDateModified(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_OWNERNAME)){
            			head.setOwnerName(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_OWNEREMAIL)){
            			head.setOwnerEmail(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_OWNERID)){
            			head.setOwnerId(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_EXPANSIONSTATE)){
            			head.setExpansionState(parseString(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_VERTSCROLLSTATE)){
            			head.setVertScrollState(parseInteger(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_WINDOWTOP)){
            			head.setWindowTop(parseInteger(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_WINDOWLEFT)){
            			head.setWindowLeft(parseInteger(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_WINDOWBOTTOM)){
            			head.setWindowBottom(parseInteger(xmlReader));
            		}else if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_WINDOWRIGHT)){
            			head.setWindowRight(parseInteger(xmlReader));
            		}
            	}            	
            }
        }		
		return(head);
	}
	
	private static OPMLOutline parseOutline(XMLStreamReader xmlReader) throws XMLStreamException{
		OPMLOutline outline=new OPMLOutline();
		//read the attributes
		int attributeCount=xmlReader.getAttributeCount();
		for(int i=0;i<attributeCount;i++){
			String attributeName=xmlReader.getAttributeName(i).toString();
			if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_TEXT)){
				outline.setText(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_VERSION)){
				outline.setVersion(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_DESCRIPTION)){
				outline.setDescription(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_HTMLURL)){
				outline.setHtmlUrl(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_XMLURL)){
				outline.setXmlUrl(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_LANGUAGE)){
				outline.setLanguage(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_TITLE)){
				outline.setTitle(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_TYPE)){
				outline.setType(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_CREATED)){
				outline.setCreated(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_URL)){
				outline.setUrl(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_CATEGORY)){
				outline.setCategory(xmlReader.getAttributeValue(i));
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_ISCOMMENT)){
				String s=xmlReader.getAttributeValue(i);
				if(s!=null){
					outline.setComment(s.equalsIgnoreCase("true"));
				}
			}else if(attributeName.equalsIgnoreCase(OPMLConstants.ATTRIBUTE_ISBREAKPOINT)){
				String s=xmlReader.getAttributeValue(i);
				if(s!=null){
					outline.setBreakpoint(s.equalsIgnoreCase("true"));
				}
			}		
		}
		//now look for either the end of the outline or the start of a child outline
		boolean atEnd=false;
        while((xmlReader.hasNext())&&(!atEnd)){
        	int eventType=xmlReader.next();
            if(eventType==XMLStreamConstants.END_ELEMENT){//end of <outline>?
            	if(xmlReader.hasName()){
            		String name=xmlReader.getName().toString();
            		if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_OUTLINE)){//yes, at the end of <outline>
            			atEnd=true;
            		}
            	}
            }else if(eventType==XMLStreamConstants.START_ELEMENT){//start of nested <outline>?
            	if(xmlReader.hasName()){
            		String name=xmlReader.getName().toString();
            		if(name.equalsIgnoreCase(OPMLConstants.ELEMENT_OUTLINE)){//yes, new <outline>
            			outline.getChildren().add(parseOutline(xmlReader));
            		}
            	}
            }
        }//while
		
		
		return(outline);
	}
	
	private static String parseString(XMLStreamReader xmlReader) throws XMLStreamException{
		int eventType=xmlReader.next();
		if(eventType==XMLStreamConstants.CHARACTERS){
			String s=(new String(xmlReader.getText()));
			return(s);
		}
		return(null);
	}
	
	private static Integer parseInteger(XMLStreamReader xmlReader) throws XMLStreamException{
		int eventType=xmlReader.next();
		if(eventType==XMLStreamConstants.CHARACTERS){
			String s=(new String(xmlReader.getText()));
			return(Integer.decode(s));
		}
		return(null);
	}	
}