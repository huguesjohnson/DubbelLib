/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;
import java.util.Map;

public class CollisionDataParameters implements Serializable{
	private static final long serialVersionUID=820L;

	public Map<String,String> collisionMap;
	public String includeFilePath;
}