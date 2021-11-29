/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;
import java.util.Map;

public class ConstantFileParameters implements Serializable{
	private static final long serialVersionUID=556L;

	public Map<String,String> fileMap;
	public String includeFilePath;
}