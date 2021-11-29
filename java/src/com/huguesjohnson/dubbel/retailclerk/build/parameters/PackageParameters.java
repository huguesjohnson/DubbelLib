/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

public class PackageParameters implements Serializable{
	private static final long serialVersionUID=220200L;

	public String[] includeFilePaths;
	public String packagePath;
}