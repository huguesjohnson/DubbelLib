/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class PaletteMap implements Serializable{
	private static final long serialVersionUID=1641L;

	public String name;
	public String sourceFilePath;
	public String destinationFilePath;
	public String exclude;
	public String allowDuplicateColors;
	public ArrayList<String> colorsHex;
	public ArrayList<String> colorsGenesisRGB;
}