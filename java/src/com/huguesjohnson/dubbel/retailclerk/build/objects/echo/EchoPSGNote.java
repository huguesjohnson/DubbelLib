/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects.echo;

//see https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt
public enum EchoPSGNote{
	/* 
	 * I can't believe this works either, Java enums are wild
	 * In this structure it's possible to write code like:
	 *  int note=EchoPSGNote.C.getValue()[octave];
	 *  writeNote(note); 
	 */
	C(new int[]{851,425,212,106,53,26}),
	C_SHARP(new int[]{803,401,200,100,50,25}),
	D(new int[]{758,379,189,94,47,23}),
	D_SHARP(new int[]{715,357,178,89,44,22}),
	E(new int[]{675,337,168,84,42,21}),
	F(new int[]{637,318,159,79,39,19}),
	F_SHARP(new int[]{601,300,150,75,37,18}),
	G(new int[]{568,284,142,71,35,17}),
	G_SHARP(new int[]{536,268,134,67,33,16}),
	A(new int[]{506,253,126,63,31,15}),
	A_SHARP(new int[]{477,238,119,59,29,14}),
	B(new int[]{450,225,112,56,28,14});
	
	private final int[] value;
	EchoPSGNote(int[] value){this.value=value;}
	public int[] getValue(){return(this.value);}

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
    	return("Invalid PSG note: "+this.value);
    }	
}