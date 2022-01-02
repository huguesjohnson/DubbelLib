/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

import com.huguesjohnson.dubbel.retailclerk.build.objects.PatternFromTileset;

public class PatternFromTilesetParameters implements Serializable{
	private static final long serialVersionUID=23337L;

	public PatternFromTileset[] patterns;
	public String patternIncludeFilePath;
}