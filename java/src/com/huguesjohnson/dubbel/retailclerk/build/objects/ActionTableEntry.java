/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class ActionTableEntry implements Serializable{
	private static final long serialVersionUID=641989L;

	public int day;
	public int scene;
	public int action;
	public String label;

	@Override
	public boolean equals(Object obj){
		if(obj==null){return(false);}
		if((obj instanceof ActionTableEntry)==false){return(false);}
		ActionTableEntry entry=(ActionTableEntry)obj;
		return((this.day==entry.day)&&(this.scene==entry.scene)&&(this.action==entry.action));
	}
	
	@Override
	public int hashCode(){
		return((this.day*1000)+(this.scene*10)+(this.action));
	}
	
	@Override
	public String toString(){
		return("day["+this.day+"] scene["+this.scene+"] action["+this.action+"] label["+this.label+"]");
	}
}