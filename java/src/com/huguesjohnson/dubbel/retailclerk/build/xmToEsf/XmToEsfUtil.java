/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/* 
 * Based on xm2esf - https://github.com/oerg866/xm2esf
 * © 2010-2015 Eric Voirin alias Oerg866
 * 
 * xm2esf is released under a license very similar the MIT License.
 * It's probably compatible but I'm not a legal expert. 
 * 
 * Initial FreeBASIC -> Java conversion was done using Google Gemini.
 * Although a non-trivial effort was required to make the resulting code actually work and be structured like a Java program.
 * It was really a lot of work just to avoid writing a sound driver, which I will probably eventually do anyway.
 */

package com.huguesjohnson.dubbel.retailclerk.build.xmToEsf;

import com.huguesjohnson.dubbel.retailclerk.build.objects.ESFEvent;

public abstract class XmToEsfUtil{
	
	public static final double calculateQuotient(int volume){
		if(volume>0){return((double)volume/64.0);}
		return(0.0);
	}
	
	public static byte calculatePSGVolume(double a){
		if(a==64){return(0);}
		if(a==0){return(15);}
		int psgVolume;
		psgVolume=-((int)(Math.log10(a/64.0)*7));
		if(psgVolume>15){psgVolume=15;}
	    return((byte)psgVolume);
    }
	
	//public for unit testing - only used in this class
	public static double calculateDivRest(double a,int b){
		int last=0;
		for(int k=0;k<=a;k++){
			if(((double)k/b-(int)((double)k/b)==0)&&(k<=a)){ 
				last=k;
			}
		}
		return(a-last);
	}

	/*
	 * This produces slightly different results than what I would expect based on the ESF documentation.
	 * See - https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt
	 * The original implementation does not have unit tests so I'm uncertain if this is:
	 * 	A) how the original code works
	 *  B) a conversion problem
	 *  C) my complete inability to predict the expected values
	 */
	public static int calculateFmFrequency(double a){
		int fmfreq=(int)(644*(Math.pow(2,(calculateDivRest(a,12)/12.0))));
		int temp=((int)(a/12.0)*2048);
		fmfreq=(int)((fmfreq&2047)+temp); // AND 2047 is equivalent to MOD 2048
		return(fmfreq);
	}

	public static byte calculateFmVolume(double a){
		if(a==0){return(127);}
		if(a==64){return(0);}
		int b=(int) ((10 * (Math.log10(Math.pow(64.0 / a, 2)))) / 0.375);
		if(b>127){return(127);}
		if(b<0){return(0);}
		return((byte)b);
	}
	
	//this is just used for testing/debugging though
	public final static String listESFEvents(byte[] b,int start,int length) throws Exception{
		StringBuilder eventString=new StringBuilder();
		for(int i=start;i<length;i++){
			ESFEvent esfEvent=ESFEvent.fromByte(b[i]);
			if(esfEvent!=null){
				eventString.append(i);
				eventString.append(": ");
				eventString.append(esfEvent.toString());
				byte value=esfEvent.getValue();
				if((value>=ESFEvent.NOTE_FM1.getValue())&&(value<=ESFEvent.NOTE_PCM.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if((value>=ESFEvent.SET_VOL_FM1.getValue())&&(value<=ESFEvent.SET_VOL_PSG4.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if((value>=ESFEvent.SET_FREQ_FM1.getValue())&&(value<=ESFEvent.SET_FREQ_PSG3.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if(value==ESFEvent.SET_FREQ_PSG4.getValue()){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if((value>=ESFEvent.SET_INSTRUMENT_FM1.getValue())&&(value<=ESFEvent.SET_INSTRUMENT_PSG4.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if((value>=ESFEvent.SET_PARAMETERS_FM1.getValue())&&(value<=ESFEvent.SET_PARAMETERS_FM6.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if((value>=ESFEvent.SET_FM_REGISTER_0.getValue())&&(value<=ESFEvent.SET_FM_REGISTER_1.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if((value>=ESFEvent.SET_FLAGS.getValue())&&(value<=ESFEvent.CLEAR_FLAGS.getValue())){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}else if(value==ESFEvent.DELAY_TICKS_LONG.getValue()){
					i++;
					eventString.append(" {");
					eventString.append(b[i]);
					eventString.append("}");
				}
				eventString.append(System.lineSeparator());
			}		
		}
		return(eventString.toString());
	}	
}