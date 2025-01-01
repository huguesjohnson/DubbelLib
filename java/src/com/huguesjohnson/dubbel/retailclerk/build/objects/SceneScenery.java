/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public class SceneScenery{
	public String patternName;
	public String label;
	public String comment;
	public int tilesetIndex;
	public int tilesetOffset;
	public boolean highPriority;
	public int paletteNumber;
	public int repeat;
	public String layer;
	//I will regret making these strings if I ever want these tools to support anything other than the Mega Drive/Genesis
	public String row;
	public String column;
}