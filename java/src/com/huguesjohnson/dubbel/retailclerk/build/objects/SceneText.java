/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class SceneText implements Serializable{
	private static final long serialVersionUID=9710L;

	public String stringLabel;
	public String comment;
	public boolean highPriority;
	public int paletteNumber;
	//I will regret making these strings if I ever want these tools to support anything other than the Mega Drive/Genesis
	public String layer;
	public String row;
	public String column;
}