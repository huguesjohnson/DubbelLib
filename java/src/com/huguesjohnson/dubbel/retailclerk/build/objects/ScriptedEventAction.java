/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public class ScriptedEventAction{
	public String comment;
	public ScriptedEventCommand command;
	public Direction direction;
	public Integer intValue;
	public Integer longValue; //longs are 32-bits hence Java Integer
	public String intConst;
	public String label;
	public String sceneId;
}