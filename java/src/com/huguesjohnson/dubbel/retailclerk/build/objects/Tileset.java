/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Tileset implements Serializable{
	private static final long serialVersionUID=10131L;
	
	public String name;
	public String palette;
	public String sourceFilePath;
	public String destinationFilePath;
	public String allowDuplicateTiles;
	public String patternFilePath;
	public ArrayList<Tile8x8> tiles;
}