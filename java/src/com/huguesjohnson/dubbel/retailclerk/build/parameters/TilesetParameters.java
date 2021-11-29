/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

import com.huguesjohnson.dubbel.retailclerk.build.objects.Tileset;

public class TilesetParameters implements Serializable{
	private static final long serialVersionUID=666L;

	public Tileset[] tilesets;
	public String tileIncludeFilePath;
	public String patternIncludeFilePath;
}