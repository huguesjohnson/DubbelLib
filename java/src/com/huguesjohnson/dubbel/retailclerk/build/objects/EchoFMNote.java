/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

//see https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt
public enum EchoFMNote{
	C(644),
	C_SHARP(681),
	D(722),
	D_SHARP(765),
	E(810),
	F(858),
	F_SHARP(910),
	G(964),
	G_SHARP(1021),
	A(1081),
	A_SHARP(1146),
	B(1214);
	
	private final int value;
	EchoFMNote(int value){this.value=value;}
	public int getValue(){return(this.value);}
	
    @Override
	public String toString(){
    	if(this.value==C.getValue()){return("C");}
    	if(this.value==C_SHARP.getValue()){return("C#");}
    	if(this.value==D.getValue()){return("D");}
    	if(this.value==D_SHARP.getValue()){return("D#");}
    	if(this.value==E.getValue()){return("E");}
    	if(this.value==F.getValue()){return("F");}
    	if(this.value==F_SHARP.getValue()){return("F#");}
    	if(this.value==G.getValue()){return("G");}
    	if(this.value==G_SHARP.getValue()){return("G#");}
    	if(this.value==A.getValue()){return("A");}
    	if(this.value==A_SHARP.getValue()){return("A#");}
    	if(this.value==B.getValue()){return("C");}
    	return("Invalid FM note: "+this.value);
    }	
}