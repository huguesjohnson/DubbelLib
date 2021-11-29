/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class ScriptedEvent implements Serializable{
	private static final long serialVersionUID=32108L;

	public String name;
	public ScriptedEventAction[] actions;
}