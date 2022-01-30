/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

public class ActionTableEntry implements Serializable{
	private static final long serialVersionUID=641989L;

	public int day;
	public String scene;
	public String action;
	public String label;

	@Override
	public boolean equals(Object obj){
		if(obj==null){return(false);}
		if((obj instanceof ActionTableEntry)==false){return(false);}
		ActionTableEntry entry=(ActionTableEntry)obj;
		return((this.day==entry.day)&&(this.scene.equals(entry.scene))&&(this.action.equals(entry.action)));
	}
	
	@Override
	public int hashCode(){
		int hashCode=(this.scene+this.action).hashCode();
		if(hashCode<0){
			hashCode+=(this.day*10000);
		}else{
			hashCode-=(this.day*10000);
		}
		return(hashCode);
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("day[");
		sb.append(this.day);
		sb.append("] scene[");
		sb.append(this.scene);
		sb.append("] action[");
		sb.append(this.action);
		sb.append("] label[");
		sb.append(this.label);
		sb.append("] hashcode[");
		sb.append(this.hashCode());
		sb.append("]");
		return(sb.toString());
	}
}