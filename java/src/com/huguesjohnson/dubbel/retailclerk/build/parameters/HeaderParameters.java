/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

public class HeaderParameters implements Serializable{
	private static final long serialVersionUID=476L;
	
	public String filePath;
	public String copyright;
	public String cartName;
	public String romStart;
	public String romEnd;
	public String ramStartEnd;
	public String sramType;
	public String sramStart;
	public String sramEnd;
	public String comment;
}