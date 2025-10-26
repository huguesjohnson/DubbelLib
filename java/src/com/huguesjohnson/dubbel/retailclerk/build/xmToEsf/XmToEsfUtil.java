/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/* 
 * Based on xm2esf - https://github.com/oerg866/xm2esf
 * © 2010-2015 Eric Voirin alias Oerg866
 * 
 * xm2esf is released under a license similar to the MIT License but with restrictions on commercial use.
 * In the extraordinarily unlikely chance someone wants to use this conversion in a commercial project... have fun figuring that out.
 * 
 * Initial FreeBASIC -> Java conversion was done using Google Gemini.
 * I wonder if that also makes commercial use interesting from a license standpoint?
 * A non-trivial effort was required to make the resulting code actually work and be structured like a Java program.
 * I'm not positive if Gemini saved any time vs converting manually or building from scratch based on the ESF documentation.
 * Gemini initially thought the FreeBASIC code was trying to parse .xml if that's an indicator of how well it works.
 * It was good at explaining how math functions differ between FreeBASIC and Java at least.
 * 
 * This was all done to avoid writing a sound driver, which I will probably eventually do anyway.
 */

package com.huguesjohnson.dubbel.retailclerk.build.xmToEsf;

import java.io.IOException;
import java.io.OutputStream;

import com.huguesjohnson.dubbel.audio.xm.XMConstants;
import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.ESFEvent;
import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.EchoNoise;

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
		int b=(int)((10*(Math.log10(Math.pow(64.0/a,2))))/0.375);
		if(b>127){return(127);}
		if(b<0){return(0);}
		return((byte)b);
	}
	
	public static int calculateSlideSpeed(int xmEffectType,int xmEffectParameter){
		return(-((xmEffectType-1)*2-1)*xmEffectParameter); //speed calculation
	}
	
	public static int calculateSlideTarget(int xmEffectType){
		 //target is max/min note range (0 or 96 for XM)
		return(-(xmEffectType-2)*XMConstants.MAX_NOTE);
	}
	
	public static byte calculateFmNote(int currentNote){
		return((byte)(int)(32*Math.floor(currentNote/12.0)+(2*(currentNote%12))+1));
	}
	
	public static byte calculatePsgNote(int currentNote){
		return(((byte)(int)(24*Math.floor(currentNote/12.0)+(2*(currentNote%12)))));
	}

	public static long calculatePsgFrequency(int currentNote){
		return((long)Math.floor((Math.pow(0.5,((currentNote)/12.0-1.0)))/2.0*851.0));
	}
	
	public static double calculateVibratoSlideStepConversion(double vibratoStep,int vibratoDepth,int currentNote){
		/* 
		 * Source code conversion notes from Gemini:
		 *  C++: SIN(pi/180 * vibstep(i)) -- This implies `pi` was actually used for trig in C++!
		 *  The BASIC used `fbmath.bi` which is FreeBASIC's math library.
		 *  `SIN` usually expects radians. `pi/180 * angle_in_degrees` converts to radians.
		 *  Or, if `pi` from `fbmath.bi` is defined as it would be used in trig, then `Math.PI` is fine.
		 *  Given `pi/180`, it's likely `vibstep` is treated as degrees.
		*/
		double conversion=Math.sin(Math.toRadians(vibratoStep))*vibratoDepth/5.0+currentNote;
		return(conversion);
	}
	
	//FM and PSG logic are similar enough they could be grouped together instead of the current implementation
	public static void writeVolume(OutputStream out,int esfChannel,ChannelType channelType,int volume,double quotient) throws IOException{
		if(channelType==ChannelType.FM){
			out.write((byte)(esfChannel+ESFEvent.SET_VOL_FM1.getValue()));
			int fmVolume=(int)XmToEsfUtil.calculateFmVolume(quotient*(double)volume);
			out.write((byte)fmVolume);
		}else{
			if(channelType==ChannelType.NOISE){
				/*
				 * The original code writes 0x2A (SET_VOL_PSG3)...
				 * but I think it is supposed to be 0x2B (SET_VOL_PSG4).
				 * I may have to go back and fix this later after testing.
				 * It's possible I don't understand how this works of course.
				 */
				out.write(ESFEvent.SET_VOL_PSG3.getValue());
			}else{//PSG
				out.write((byte)(esfChannel+ESFEvent.SET_VOL_FM1.getValue()));
			}
			int psgVolume=(int)XmToEsfUtil.calculatePSGVolume(quotient*(double)volume);
			out.write((byte)psgVolume);
        }
	}
	
	//comments from the previous method more or less apply here too
	public static void writeFrequency(OutputStream out,int esfChannel,ChannelType channelType,long frequency,EchoNoise noiseType) throws IOException {
		if(channelType==ChannelType.FM){
			out.write((byte)(esfChannel+ESFEvent.SET_FREQ_FM1.getValue()));
			out.write((byte)(frequency/256));
			out.write((byte)(frequency%256));
		}else{
			if((channelType==ChannelType.NOISE)&&(noiseType.isPSG())){
				out.write((byte)ESFEvent.SET_FREQ_PSG3.getValue());
			}else{
				out.write((byte)(esfChannel+ESFEvent.SET_FREQ_FM1.getValue()));
			}
			out.write((byte)(frequency%16));
			out.write((byte)(frequency/16));
		}
	}
	
	/*
	 * The original implementation modifies the table of frequencies.
	 * To avoid a larger rewrite this is returning the frequency value.
	 * This is a good candidate of things to rewrite though.
	 */
	public static long writeNote(OutputStream out,int esfChannel,ChannelType channelType,double note,EchoNoise noiseType) throws IOException{ 
		if(channelType==ChannelType.FM){
			long fmFrequency=XmToEsfUtil.calculateFmFrequency(note);
			out.write((byte)(esfChannel+ESFEvent.SET_FREQ_FM1.getValue()));
			out.write((byte)(fmFrequency/256));
			out.write((byte)(fmFrequency%256));
			return(fmFrequency);
		}else{
			long psgFrequency=(long)Math.floor((Math.pow(0.5,((note)/12.0-1.0)))/2.0*851.0);
			if((channelType==ChannelType.NOISE)&&(noiseType.isPSG())){
				out.write((byte)ESFEvent.SET_FREQ_PSG3.getValue());
			}else{
				out.write((byte)(esfChannel+ESFEvent.SET_FREQ_FM1.getValue()));
			}
			out.write((byte)(psgFrequency%16));
			out.write((byte)(psgFrequency/16));
			return(psgFrequency);
		}
	}

	/* This was built for testing/debugging.
	 * Obsoleted by EsfCompare though. */
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