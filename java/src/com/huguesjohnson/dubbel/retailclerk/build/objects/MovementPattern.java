/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class MovementPattern implements Serializable{
	private static final long serialVersionUID=10101911L;

	public String name;
	public Direction[] steps;
}
