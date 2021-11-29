/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class SceneNpcLocation implements Serializable{
	private static final long serialVersionUID=60489L;

	public int x;
	public int y;
	public String direction;
	public int movementFrequency;
	public String movementPatternName;	
}