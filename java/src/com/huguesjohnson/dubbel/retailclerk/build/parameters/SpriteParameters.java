/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

import com.huguesjohnson.dubbel.retailclerk.build.objects.Sprite;

public class SpriteParameters implements Serializable{
	private static final long serialVersionUID=19890604L;

	public Sprite[] sprites;
	public String palette;
	public String includeFilePath;
	public String characterDefinitionFilePath;
	public String constantDefinitionPath;
	public String baseId;
}