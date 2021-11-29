/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

public class SceneParameters implements Serializable{
	private static final long serialVersionUID=39L;

	public String[] scenePaths;
	public String includeFilePath;
	public String lookupTablePath;
}