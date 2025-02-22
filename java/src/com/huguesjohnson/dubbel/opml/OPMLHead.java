/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.opml;

import com.huguesjohnson.dubbel.util.DateUtil;

public class OPMLHead{
	private String title;
	/* 
	 * OPML allows years to be four or two digits which is, whatever... 
	 * Easier to treat them as a Strings then and store a hidden epoch time that can be used for sorting.
	 */
	private String dateCreated;
	private long dateCreatedEpoch=0L;
	private String dateModified;
	private long dateModifiedEpoch=0L;
	private String ownerName;
	private String ownerEmail;
	private String ownerId; 
	private String docs; 
	private String expansionState;
	//These are Integer so they can be null to indicate there is no value for them, which is common.
	private Integer vertScrollState; 
	private Integer windowTop; 
	private Integer windowLeft;
	private Integer windowBottom;
	private Integer windowRight;

	public void setDateCreated(String dateCreated){
		this.dateCreated=dateCreated;
		this.dateCreatedEpoch=DateUtil.toEpochTime(dateCreated,DateUtil.DF_RFC822);
		if(this.dateCreatedEpoch==0L){
			//try again I guess, if still 0 after this then the date passed is definitely invalid
			this.dateCreatedEpoch=DateUtil.toEpochTime(dateCreated,DateUtil.DF_RFC822_OPML_ALT);
		}
	}
	
	public void setDateModified(String dateModified){
		this.dateModified=dateModified;
		this.dateModifiedEpoch=DateUtil.toEpochTime(dateModified,DateUtil.DF_RFC822);
		if(this.dateModifiedEpoch==0L){
			//try again I guess, if still 0 after this then the date passed is definitely invalid
			this.dateModifiedEpoch=DateUtil.toEpochTime(dateModified,DateUtil.DF_RFC822_OPML_ALT);
		}
	}
	
	/* autogenerated code below */

	public OPMLHead() {
		super();
	}	
	
	public OPMLHead(String title, String dateCreated, String dateModified,
			 String ownerName, String ownerEmail, String ownerId, String docs,
			String expansionState, Integer vertScrollState, Integer windowTop, Integer windowLeft, Integer windowBottom,
			Integer windowRight) {
		super();
		this.title = title;
		this.setDateCreated(dateCreated); //this and the next line were modified from the auto-generated code
		this.setDateModified(dateModified);
		this.ownerName = ownerName;
		this.ownerEmail = ownerEmail;
		this.ownerId = ownerId;
		this.docs = docs;
		this.expansionState = expansionState;
		this.vertScrollState = vertScrollState;
		this.windowTop = windowTop;
		this.windowLeft = windowLeft;
		this.windowBottom = windowBottom;
		this.windowRight = windowRight;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getDateCreated() {
		return dateCreated;
	}

	public String getDateModified() {
		return dateModified;
	}

	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getDocs() {
		return docs;
	}
	public void setDocs(String docs) {
		this.docs = docs;
	}
	public String getExpansionState() {
		return expansionState;
	}
	public void setExpansionState(String expansionState) {
		this.expansionState = expansionState;
	}
	public Integer getVertScrollState() {
		return vertScrollState;
	}
	public void setVertScrollState(Integer vertScrollState) {
		this.vertScrollState = vertScrollState;
	}
	public Integer getWindowTop() {
		return windowTop;
	}
	public void setWindowTop(Integer windowTop) {
		this.windowTop = windowTop;
	}
	public Integer getWindowLeft() {
		return windowLeft;
	}
	public void setWindowLeft(Integer windowLeft) {
		this.windowLeft = windowLeft;
	}
	public Integer getWindowBottom() {
		return windowBottom;
	}
	public void setWindowBottom(Integer windowBottom) {
		this.windowBottom = windowBottom;
	}
	public Integer getWindowRight() {
		return windowRight;
	}
	public void setWindowRight(Integer windowRight) {
		this.windowRight = windowRight;
	}
	public long getDateCreatedEpoch() {
		return dateCreatedEpoch;
	}
	public long getDateModifiedEpoch() {
		return dateModifiedEpoch;
	}

	
	
}