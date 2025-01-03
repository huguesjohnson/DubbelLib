/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

public class ReplacementBlock{
	private String startTag;
	private String endTag;
	private String replacementText;

	public ReplacementBlock(){}

	/* autogenerated code */	
	
	public ReplacementBlock(String startTag, String endTag, String replacementText) {
		super();
		this.startTag = startTag;
		this.endTag = endTag;
		this.replacementText = replacementText;
	}

	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public String getEndTag() {
		return endTag;
	}

	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}

	public String getReplacementText() {
		return replacementText;
	}

	public void setReplacementText(String replacementText) {
		this.replacementText = replacementText;
	}
}