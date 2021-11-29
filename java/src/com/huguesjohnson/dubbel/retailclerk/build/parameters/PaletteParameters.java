/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

import com.huguesjohnson.dubbel.retailclerk.build.objects.PaletteMap;

public class PaletteParameters implements Serializable{
	private static final long serialVersionUID=1010011010L;

	public PaletteMap[] paletteMap;
	public String includeFilePath;
}