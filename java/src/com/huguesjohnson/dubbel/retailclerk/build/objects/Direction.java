/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public enum Direction{
	DOWN(0),
	UP(1),
	LEFT(2),
	RIGHT(3),
	NULL(-1);
	
	private final int value;
    Direction(final int value){this.value=value;}
    public int getValue(){return(value);}

    @Override
	public String toString(){
    	if(this.value==DOWN.getValue()){return("DIRECTION_DOWN");}
    	if(this.value==UP.getValue()){return("DIRECTION_UP");}
    	if(this.value==LEFT.getValue()){return("DIRECTION_LEFT");}
    	if(this.value==RIGHT.getValue()){return("DIRECTION_RIGHT");}
		return("DIRECTION_NULL");
	}
}