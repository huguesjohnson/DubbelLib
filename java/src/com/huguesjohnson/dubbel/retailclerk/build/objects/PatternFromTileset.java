/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class PatternFromTileset implements Serializable{
	private static final long serialVersionUID=1117L;
	
	public String name;
	public String tilesetName;
	public String paletteName;
	public String sourceFilePath;
	public String destinationFilePath;
}