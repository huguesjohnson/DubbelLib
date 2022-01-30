/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

import com.huguesjohnson.dubbel.retailclerk.build.objects.ActionTableEntry;

public class ActionTableParameters implements Serializable{
	private static final long serialVersionUID=-666L;
	
	public int dayCount;
	public int sceneCount;
	public ActionTableEntry[] entries;
	public String[] actions;
	public String[] defaultLabels;
	public String filePath;
}