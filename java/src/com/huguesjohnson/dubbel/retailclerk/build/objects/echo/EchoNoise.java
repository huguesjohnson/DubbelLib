/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects.echo;

//see https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt
public enum EchoNoise{
	NONE(-1),
	PERIODIC_HIGH(0),
	PERIODIC_MEDIUM(1),
	PERIODIC_LOW(2),
	PERIODIC_PSG(3),
	WHITE_HIGH(4),
	WHITE_MEDIUM(5),
	WHITE_LOW(6),
	WHITE_PSG(7);
	
	private final int value;
	EchoNoise(int value){this.value=value;}
	public int getValue(){return(this.value);}
	
	public boolean isPSG(){
		return((this.value==PERIODIC_PSG.getValue())||(this.value==WHITE_PSG.getValue()));
	}
	
	public boolean isPeriodic(){
		return((this.value>=PERIODIC_HIGH.getValue())&&(this.value<=PERIODIC_PSG.getValue()));
	}
	
    @Override
	public String toString(){
    	if(this.value==NONE.getValue()){return("None");}
    	if(this.value==PERIODIC_HIGH.getValue()){return("Periodic noise, high pitch");}
    	if(this.value==PERIODIC_MEDIUM.getValue()){return("Periodic noise, medium pitch");}
    	if(this.value==PERIODIC_LOW.getValue()){return("Periodic noise, low pitch");}
    	if(this.value==PERIODIC_PSG.getValue()){return("Periodic noise, PSG3 frequency");}
    	if(this.value==WHITE_HIGH.getValue()){return("White noise, high pitch");}
    	if(this.value==WHITE_MEDIUM.getValue()){return("White noise, medium pitch");}
    	if(this.value==WHITE_LOW.getValue()){return("White noise, low pitch");}
    	if(this.value==WHITE_PSG.getValue()){return("White noise, PSG3 frequency");}
    	return("Invalid noise value: "+this.value);
    }	
}