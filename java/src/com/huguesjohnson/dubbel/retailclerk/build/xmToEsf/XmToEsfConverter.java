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

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.huguesjohnson.dubbel.audio.xm.EffectType;
import com.huguesjohnson.dubbel.audio.xm.PatternData;
import com.huguesjohnson.dubbel.audio.xm.XMConstants;
import com.huguesjohnson.dubbel.audio.xm.XMFile;
import com.huguesjohnson.dubbel.audio.xm.XMPattern;
import com.huguesjohnson.dubbel.audio.xm.XMReader;
import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.ESFEvent;
import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.EchoConst;
import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.EchoNoise;
import com.huguesjohnson.dubbel.retailclerk.build.xmToEsf.XMESFMap.ESFType;
import com.huguesjohnson.dubbel.retailclerk.build.xmToEsf.XMESFMap.PSGRetriggerVolumeEnvelope;

public abstract class XmToEsfConverter{

	public static void convert(XMESFMap map) throws IOException{
		//esf file writer
		DataOutputStream esfOut=null;
		try{
			int[] channelPresent=new int[EchoConst.MAX_CHANNELS];
			int[] pitchAdjust=new int[EchoConst.MAX_CHANNELS];
			double[] quotient=new double[EchoConst.MAX_CHANNELS];
			int[] currentInstrument=new int[EchoConst.MAX_CHANNELS];
			int[] currentNote=new int[EchoConst.MAX_CHANNELS];
			int[] currentVolume=new int[EchoConst.MAX_CHANNELS];
			long[] currentFrequency=new long[EchoConst.MAX_CHANNELS-1];
			int[] effectData=new int[EchoConst.MAX_CHANNELS];
			int[] effectValue=new int[EchoConst.MAX_CHANNELS];
			double[] slideStep=new double[EchoConst.MAX_CHANNELS]; 
			int[] slideTarget=new int[EchoConst.MAX_CHANNELS];
			int[] slideSpeed=new int[EchoConst.MAX_CHANNELS];
			double[] volslidepos=new double[EchoConst.MAX_CHANNELS]; 
			double[] volslidespeed=new double[EchoConst.MAX_CHANNELS];
			int[] arpnote1=new int[EchoConst.MAX_CHANNELS];
			int[] arpnote2=new int[EchoConst.MAX_CHANNELS];
			double[] vibratoStep=new double[EchoConst.MAX_CHANNELS];
			int[] vibratoSpeed=new int[EchoConst.MAX_CHANNELS];
			int[] vibratoDepth=new int[EchoConst.MAX_CHANNELS];
			double conversion; 
			int[] loopInstrument=new int[EchoConst.MAX_CHANNELS];
			int[] lastfx=new int[EchoConst.MAX_CHANNELS];
			int[] lastfd=new int[EchoConst.MAX_CHANNELS];
			byte[] fineslide=new byte[EchoConst.MAX_CHANNELS];

			//Initialize arrays from BASIC, which often clear them implicitly
			Arrays.fill(pitchAdjust,0);
			Arrays.fill(quotient,0.0);
			Arrays.fill(currentInstrument,0);
			Arrays.fill(currentNote,0);
			Arrays.fill(currentVolume,0);
			Arrays.fill(currentFrequency,0L);
			Arrays.fill(effectData,255);//set in a loop later on in original source 
			Arrays.fill(effectValue,255);//set in a loop later on in original source
			Arrays.fill(slideStep,0.0);
			Arrays.fill(slideTarget,0);
			Arrays.fill(slideSpeed,0);
			Arrays.fill(volslidepos,0.0);
			Arrays.fill(volslidespeed,0.0);
			Arrays.fill(arpnote1,0);
			Arrays.fill(arpnote2,0);
			Arrays.fill(vibratoStep,0.0);
			Arrays.fill(vibratoSpeed,0);
			Arrays.fill(vibratoDepth,0);
			Arrays.fill(fineslide,(byte)0);
			Arrays.fill(loopInstrument,0);
			Arrays.fill(lastfx,0);
			Arrays.fill(lastfd,0);

			//read the xm file
			XMFile xm=XMReader.read(map.xmFilePath);

			//create out file
			String outputEsfPath=map.esfOutputPath;
			esfOut=new DataOutputStream(new FileOutputStream(outputEsfPath));

			//0-5 => fm channels
			channelPresent[0]=(map.fmChannelMap[0]);
			channelPresent[1]=(map.fmChannelMap[1]);
			channelPresent[2]=(map.fmChannelMap[2]);
			channelPresent[3]=(map.fmChannelMap[3]);
			channelPresent[4]=(map.fmChannelMap[4]);
			channelPresent[5]=(map.fmChannelMap[5]);
			//6-8 => psg channels
			channelPresent[6]=(map.psgChannelMap[0]);
			channelPresent[7]=(map.psgChannelMap[1]);
			channelPresent[8]=(map.psgChannelMap[2]);
			//9 => pcm
			channelPresent[9]=(map.pcmChannel);
			//10 => noise which is really the 4th psg channel
			channelPresent[10]=(map.psgChannelMap[3]);

			//calculate quotients
			quotient[0]=XmToEsfUtil.calculateQuotient(map.fmVolume[0]);
			quotient[1]=XmToEsfUtil.calculateQuotient(map.fmVolume[1]);
			quotient[2]=XmToEsfUtil.calculateQuotient(map.fmVolume[2]);
			quotient[3]=XmToEsfUtil.calculateQuotient(map.fmVolume[3]);
			quotient[4]=XmToEsfUtil.calculateQuotient(map.fmVolume[4]);
			quotient[5]=XmToEsfUtil.calculateQuotient(map.fmVolume[5]);
			quotient[6]=XmToEsfUtil.calculateQuotient(map.psgVolume[0]);
			quotient[7]=XmToEsfUtil.calculateQuotient(map.psgVolume[1]);
			quotient[8]=XmToEsfUtil.calculateQuotient(map.psgVolume[2]);
			quotient[9]=XmToEsfUtil.calculateQuotient(map.psgVolume[3]);
			
			//setup pitchAdjust array
			pitchAdjust[0]=map.fmPitch[0];
			pitchAdjust[1]=map.fmPitch[1];
			pitchAdjust[2]=map.fmPitch[2];
			pitchAdjust[3]=map.fmPitch[3];
			pitchAdjust[4]=map.fmPitch[4];
			pitchAdjust[5]=map.fmPitch[5];
			pitchAdjust[6]=map.psgPitch[0];
			pitchAdjust[7]=map.psgPitch[1];
			pitchAdjust[8]=map.psgPitch[2];
			pitchAdjust[9]=map.psgPitch[3];

			//SFX handling
			if(map.type==ESFType.SFX){
				for(int channelIndex=0;channelIndex<EchoConst.MAX_CHANNELS;channelIndex++){
					if(channelPresent[channelIndex]>=0){
						if(channelIndex==(EchoConst.MAX_CHANNELS-1)){//last channel
							esfOut.write(ESFEvent.LOCK_FM6.getValue());
						}else{
							esfOut.write(ESFEvent.LOCK_FM1.getValue()+XmToEsfConst.ESF_CHANNELS[channelIndex]);
						}
					}
				}
			}

			//outer loop goes through patterns
			byte[] patternOrderTable=xm.getHeader().getPatternOrderTable();
			int numPatterns=xm.getHeader().getSongLength();
			for(int currentPattern=0;currentPattern<numPatterns;currentPattern++){
				XMPattern pattern=xm.getPatterns().get((int)patternOrderTable[currentPattern]);
				PatternData[][] unpacked=pattern.unpack(xm.getHeader().getNumChannels());
				int numRows=pattern.getNumRows();
				/*
				 * Assuming I understand the loop structure, and that is not a given...
				 * -This loops through each pattern row.
				 * -In the original implementation all the patterns are merged into one array.
				 * -It then loops through each channel in the row.
				 */
				for(int currentRow=0;currentRow<numRows;currentRow++){
					//check if this is the loop point
					if((currentPattern==map.loopPattern)&&(currentRow==map.loopRow)){
						if(map.loop){
							esfOut.write(ESFEvent.SET_LOOP.getValue()); 
							//store instruments for looping
							for(int i=0;i<EchoConst.MAX_CHANNELS;i++){
								loopInstrument[i]=currentInstrument[i];
							}
						}
					}
					/*
					 * None of this has been tested because I didn't create files with xmsync enabled.
					 * Since it's not on the xm2esf ui I didn't even know about it.
					 */
					if(map.xmSync>0){
						PatternData syncData=unpacked[currentRow][map.xmSync];
						int xmnote=syncData.note;
						if(xmnote==XMConstants.NOTE_OFF){//note 97 is a specific sync event for XMCSIDFA
							esfOut.write(ESFEvent.CLEAR_FLAGS.getValue());
						}
					}
					for(int currentChannel=0;currentChannel<EchoConst.MAX_CHANNELS;currentChannel++){
						if(channelPresent[currentChannel]>=0){
							PatternData channelData=unpacked[currentRow][channelPresent[currentChannel]];	
							int xmNote=channelData.note;
							int xmInstrument=channelData.instrument;
							int xmVolume=channelData.volumeColumn;
							int xmEffectType=channelData.effectType;
							int xmEffectParameter=channelData.effectParameter;
							//handle lastfx/lastfd updates for effects(related to tone portamento 3xx, Portamento Up/Down 1xx/2xx)
							if(effectData[currentChannel]!=255){
								lastfx[currentChannel]=effectData[currentChannel];
								lastfd[currentChannel]=effectValue[currentChannel];
							}
							//set speed fx
							if(xmEffectType==EffectType.SET_TEMPO.getValue()){
								map.tempo=xmEffectParameter;
							}
							//reset slide attributes when no related effect(1xx, 2xx, 3xx, 4xx) is in use
							if(xmEffectType>EffectType.VIBRATO.getValue()||xmEffectType==EffectType.ARPEGGIO.getValue()){
								slideStep[currentChannel]=0;
								slideSpeed[currentChannel]=0;
								slideTarget[currentChannel]=0;
							}
							//reset conversion values for effects when no effect is in use]
							if((xmEffectType==EffectType.ARPEGGIO.getValue())&&(xmEffectParameter==0)){
								effectData[currentChannel]=255;
								effectValue[currentChannel]=255;
							}
							//arpeggio
							if((xmEffectType==EffectType.ARPEGGIO.getValue())&&(xmEffectParameter!=0)){
								effectData[currentChannel]=EffectType.ARPEGGIO.getValue();
								effectValue[currentChannel]=xmEffectParameter;
								arpnote1[currentChannel]=(xmEffectParameter/16);
								arpnote2[currentChannel]=(xmEffectParameter%16);
								slideStep[currentChannel]=currentNote[currentChannel]; //used as base for arpeggio notes
							}
							//set panning 8xx
							if(xmEffectType==EffectType.SET_PANNING.getValue()){
								if((XmToEsfConst.channelType[currentChannel]!=ChannelType.FM)&&(XmToEsfConst.channelType[currentChannel]!=ChannelType.PCM)){
									//there is nothing to do here unless I decide to have this method return a log or messages or whatever
								}else{
									if(XmToEsfConst.channelType[currentChannel]==ChannelType.PCM){
										esfOut.write(ESFEvent.SET_PARAMETERS_FM6.getValue());
									}else{
										esfOut.write((byte)(XmToEsfConst.ESF_CHANNELS[currentChannel]+ESFEvent.SET_PARAMETERS_FM1.getValue()));
									}
									if(xmEffectParameter==XMConstants.CENTER_PAN){//center panning (0x80)
										esfOut.write(EchoConst.CENTER_PAN);
									}else if(xmEffectParameter>XMConstants.CENTER_PAN){//right panning (>0x80)
										esfOut.write(EchoConst.RIGHT_PAN);
									}else{ //left panning (<0x80)
										esfOut.write(EchoConst.LEFT_PAN);
									}
								}
							}
							//vibrato
							if(xmEffectType==EffectType.VIBRATO.getValue()){
								if(effectData[currentChannel]==EffectType.VIBRATO.getValue()){//continue vibrato if already active
									vibratoSpeed[currentChannel]=(xmEffectParameter/16);
									vibratoDepth[currentChannel]=(xmEffectParameter%16);
									effectData[currentChannel]=EffectType.VIBRATO.getValue();
									effectValue[currentChannel]=xmEffectParameter;
								}else{//start new vibrato
									effectData[currentChannel]=EffectType.VIBRATO.getValue();
									effectValue[currentChannel]=xmEffectParameter;
									vibratoSpeed[currentChannel]=(xmEffectParameter/16);
									vibratoDepth[currentChannel]=(xmEffectParameter%16);
									vibratoStep[currentChannel]=0; 
								}
							}
							//tone portamento 
							if(xmEffectType==EffectType.TONE_PORTAMENTO.getValue()){
								if(lastfx[currentChannel]>EffectType.VIBRATO.getValue()){
									//if previous effect was not pitch-related, set base
									slideStep[currentChannel]=currentNote[currentChannel]; 
								}
								if(effectData[currentChannel]==EffectType.TONE_PORTAMENTO.getValue()){//continue tone portamento
									if(slideSpeed[currentChannel]<0){
										slideSpeed[currentChannel]=((-1)*xmEffectParameter);
									}else{
										slideSpeed[currentChannel]=xmEffectParameter;
									}
								}
								effectData[currentChannel]=EffectType.TONE_PORTAMENTO.getValue();
								effectValue[currentChannel]=xmEffectParameter;
								if((xmNote>0)&&(xmNote<XMConstants.NOTE_OFF)){//new note plays a role in target
									if(slideStep[currentChannel]!=0){//if slidestep was already set(from previous note/effect)
										if((xmNote+pitchAdjust[currentChannel])<slideStep[currentChannel]){
											slideSpeed[currentChannel]=((-1)*xmEffectParameter);
										}else{
											slideSpeed[currentChannel]=xmEffectParameter; 
										}
									}else{//new tone portamento
										if((xmNote+pitchAdjust[currentChannel])<currentNote[currentChannel]){
											slideSpeed[currentChannel]=((-1)*xmEffectParameter);
										}else{
											slideSpeed[currentChannel]=xmEffectParameter;
										}
										slideStep[currentChannel]=currentNote[currentChannel];
									}
									slideTarget[currentChannel]=(xmNote+pitchAdjust[currentChannel]);
								}
							}
							if((xmNote<XMConstants.NOTE_OFF)&&(xmNote>0)){
								currentNote[currentChannel]=(xmNote+pitchAdjust[currentChannel]);
							}
							//portamento up / down
							if((xmEffectType==EffectType.PORTAMENTO_UP.getValue())||(xmEffectType==EffectType.PORTAMENTO_DOWN.getValue())){
								if(lastfx[currentChannel]>EffectType.VIBRATO.getValue()){
									//if no pitch effect before, use current note
									slideStep[currentChannel]=currentNote[currentChannel]; 
								}
								if((xmNote>0)&&(xmNote!=XMConstants.NOTE_OFF)){
									 //new note sets initial step
									slideStep[currentChannel]=xmNote+pitchAdjust[currentChannel];
								}
								effectData[currentChannel]=xmEffectType;
								effectValue[currentChannel]=xmEffectParameter;
								slideSpeed[currentChannel]=XmToEsfUtil.calculateSlideSpeed(xmEffectType,xmEffectParameter);
								slideTarget[currentChannel]=XmToEsfUtil.calculateSlideTarget(xmEffectType);
							}
							//set current note variable
							if((xmNote<XMConstants.NOTE_OFF)&&(xmNote>0)){
								currentNote[currentChannel]=(xmNote+pitchAdjust[currentChannel]);
							}
							//handle note off
							if(xmNote==(int)XMConstants.NOTE_OFF){
								//check for PSG ignore
								//TODO - this if statement is too long and maybe could go out to a utility method
								if(!(((XmToEsfConst.channelType[currentChannel]==ChannelType.PSG)||(XmToEsfConst.channelType[currentChannel]==ChannelType.NOISE))&&(map.psgRetriggerVolumeEnvelope.equals(PSGRetriggerVolumeEnvelope.NEVER)))){
									esfOut.write((byte)(XmToEsfConst.ESF_CHANNELS[currentChannel]+ESFEvent.NOTE_OFF_FM1.getValue()));
								}
							}
							//handle new note
							if((xmNote>0)&&(xmNote<XMConstants.NOTE_OFF)){
								if(XmToEsfConst.channelType[currentChannel]!=ChannelType.PCM){//not PCM channel
									if(xmEffectType!=EffectType.TONE_PORTAMENTO.getValue()){//if not tone portamento
										if((currentInstrument[currentChannel]!=xmInstrument)&&(xmInstrument!=0)){
											currentInstrument[currentChannel]=xmInstrument;
											esfOut.write((byte)(ESFEvent.SET_INSTRUMENT_FM1.getValue()+XmToEsfConst.ESF_CHANNELS[currentChannel]));
											byte instrument=(byte)map.instrumentMap.get(currentInstrument[currentChannel]).intValue();
											esfOut.write(instrument);
										}
										if((xmEffectType!=EffectType.TONE_PORTAMENTO.getValue())&&(xmEffectType!=EffectType.SET_VOLUME.getValue())&&(xmVolume!= 1)){//if not tone portamento, set volume, or volume column
											currentVolume[currentChannel]=XmToEsfConst.DEFAULT_VOLUME;//default volume
											XmToEsfUtil.writeVolume(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],currentVolume[currentChannel],quotient[currentChannel]);
											volslidepos[currentChannel]=currentVolume[currentChannel];
										}
										if(XmToEsfConst.channelType[currentChannel]==ChannelType.FM){
											esfOut.write((byte)XmToEsfConst.ESF_CHANNELS[currentChannel]); //note on
											byte fmNote=XmToEsfUtil.calculateFmNote(currentNote[currentChannel]);
											esfOut.write(fmNote);
										}else if(XmToEsfConst.channelType[currentChannel]==ChannelType.PSG){
											esfOut.write((byte)XmToEsfConst.ESF_CHANNELS[currentChannel]); //note on
											byte psgNote=XmToEsfUtil.calculatePsgNote(currentNote[currentChannel]);
											esfOut.write(psgNote);
											long psgFrequency=XmToEsfUtil.calculatePsgFrequency(currentNote[currentChannel]);
											currentFrequency[currentChannel]=psgFrequency;
										}else{//noise channel
											if(!map.noiseType.isPSG()){//stock noise
												esfOut.write((byte)ESFEvent.NOTE_PSG4.getValue());
												if(map.noiseType==EchoNoise.PERIODIC_HIGH){
													esfOut.write((byte)EchoNoise.PERIODIC_HIGH.getValue());
												}else{
													esfOut.write((byte)EchoNoise.WHITE_HIGH.getValue());
												}
											}else{//PSG3
												long psgFrequency=XmToEsfUtil.calculatePsgFrequency(currentNote[currentChannel]);
												currentFrequency[currentChannel]=psgFrequency;
												esfOut.write(ESFEvent.SET_FREQ_PSG3.ordinal());
												esfOut.write((byte)(currentFrequency[currentChannel]%16));
												esfOut.write((byte)(currentFrequency[currentChannel]/16));
												esfOut.write(ESFEvent.NOTE_PSG4.ordinal());
												if(map.noiseType==EchoNoise.PERIODIC_PSG){
													esfOut.write((byte)EchoNoise.PERIODIC_PSG.getValue());
												}else{
													esfOut.write((byte)EchoNoise.WHITE_PSG.getValue());
												}
											}
										}
									}
								}else if(XmToEsfConst.channelType[currentChannel]==ChannelType.PCM){
									currentInstrument[currentChannel]=xmInstrument;
									esfOut.write(ESFEvent.NOTE_PCM.getValue());
									byte instrument=(byte)map.instrumentMap.get(currentInstrument[currentChannel]).intValue();
									esfOut.write(instrument);
								}
							}
							//simulate XM behavior (PSG retriggers)
							if(((map.psgRetriggerVolumeEnvelope.equals(PSGRetriggerVolumeEnvelope.INSTRUMENT_COLUMN))&&(xmEffectType>0)&&(xmEffectType<5))||(map.psgRetriggerVolumeEnvelope.equals(PSGRetriggerVolumeEnvelope.ALWAYS))){
								if((xmInstrument>0)&&(XmToEsfConst.channelType[currentChannel]==ChannelType.PSG)){
									currentInstrument[currentChannel]=xmInstrument;
									esfOut.write((byte)(ESFEvent.SET_INSTRUMENT_FM1.getValue()+XmToEsfConst.ESF_CHANNELS[currentChannel])); 
									byte instrument=(byte)map.instrumentMap.get(currentInstrument[currentChannel]).intValue();
									esfOut.write(instrument);
									esfOut.write((byte)(ESFEvent.NOTE_OFF_FM1.getValue()+XmToEsfConst.ESF_CHANNELS[currentChannel]));
									esfOut.write((byte)XmToEsfConst.ESF_CHANNELS[currentChannel]); 
									esfOut.write((byte)0x00);//note 0 (likely retriggering with no new note) 
									currentVolume[currentChannel]=XmToEsfConst.DEFAULT_VOLUME;//default volume
									XmToEsfUtil.writeFrequency(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],currentFrequency[currentChannel],map.noiseType);
									XmToEsfUtil.writeVolume(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],currentVolume[currentChannel],quotient[currentChannel]);
								}
							}
							//volume slide
							if(xmEffectType==EffectType.VOLUME_SLIDE.getValue()){
								if(XmToEsfConst.channelType[currentChannel]!=ChannelType.PCM){ 
									if(effectData[currentChannel]!=EffectType.VOLUME_SLIDE.getValue()){ //start new volume slide 
										effectData[currentChannel]=EffectType.VOLUME_SLIDE.getValue();
										volslidepos[currentChannel]=currentVolume[currentChannel];
									}
									//fine slide detection 
									if(((xmEffectParameter/16)==0xF)&&((xmEffectParameter%6)>0)){ //FxxY - fine volume slide up
										fineslide[currentChannel]=1; 
									}else if(((xmEffectParameter%16)==0xF)&&((xmEffectParameter/16)>0)){ //XFxx - fine volume slide down
										fineslide[currentChannel]=1;
									}else{ //normal volume slide
										fineslide[currentChannel]=0;
										if((xmEffectParameter%16)>0){ //volume slide down
											volslidespeed[currentChannel]=-(double)(xmEffectParameter%16);
											effectValue[currentChannel]=(xmEffectParameter%16);
										}else if((xmEffectParameter/16)>0){ //volume slide up
											volslidespeed[currentChannel]=(double)(xmEffectParameter/16);
											effectValue[currentChannel]=xmEffectParameter/16; 
										}
									}
								}
							}
							//set volume 
							if(xmEffectType==EffectType.SET_VOLUME.getValue()){
								if(XmToEsfConst.channelType[currentChannel]==ChannelType.PCM){
									//ignore for pcm+noise 
									effectData[currentChannel]=255;
									effectValue[currentChannel]=255;
								}else{
									effectData[currentChannel]=EffectType.SET_VOLUME.getValue();
									effectValue[currentChannel]=xmEffectParameter;
									currentVolume[currentChannel]=xmEffectParameter;
									XmToEsfUtil.writeVolume(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],currentVolume[currentChannel],quotient[currentChannel]);
								}
							}
						}
					}
					//process current effects in-between rows (per tick)
					for(int tick=1;tick<=map.tempo;tick++){//1 is the lowest tick value (if I understand correctly) 
						for(int currentChannel=0;currentChannel<EchoConst.MAX_CHANNELS;currentChannel++){//loop over channels
							switch(effectData[currentChannel]){ 
							//TODO constants for all these - oh, but it's a switch statement so EffectType.getValue() is no good here, I really don't like the way this code was converted
							case 0x00: //arpeggio
								if(effectValue[currentChannel]!=0){ 
									if((XmToEsfConst.channelType[currentChannel]==ChannelType.FM)||(XmToEsfConst.channelType[currentChannel]==ChannelType.PSG)){
										switch(tick%3){ //Cycle through 3 notes for arpeggio
										case 0://base note
											slideStep[currentChannel]=currentNote[currentChannel];
											break;
										case 1://note+arpnote1 
											slideStep[currentChannel]=currentNote[currentChannel]+arpnote1[currentChannel];
											break;
										case 2://note+arpnote2
											slideStep[currentChannel]=currentNote[currentChannel]+arpnote2[currentChannel];
											break;
										}
										currentFrequency[currentChannel]=XmToEsfUtil.writeNote(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],slideStep[currentChannel],map.noiseType);
									}
								}
								break;
							case 0x0A://volume slide
								if(((fineslide[currentChannel]==1)&&(tick==1))||(fineslide[currentChannel]==0)){ 
									if(XmToEsfConst.channelType[currentChannel]!=ChannelType.PCM){ 
										if((volslidepos[currentChannel]<65)&&(volslidepos[currentChannel]>-1)){
											volslidepos[currentChannel]=(volslidepos[currentChannel]+volslidespeed[currentChannel]);
											if(volslidepos[currentChannel]<0){
												volslidepos[currentChannel]=0; 
											}
											currentVolume[currentChannel]=(int)volslidepos[currentChannel];
										}
										if((volslidepos[currentChannel]<65)&&(volslidepos[currentChannel]>-1)){
											XmToEsfUtil.writeVolume(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],(int)volslidepos[currentChannel],quotient[currentChannel]);
										}
									}
								}
								break;
							case 0x01://portamento up
							case 0x02://portamento down
							case 0x03://tone portamento
								slideStep[currentChannel]=(slideStep[currentChannel]+(double)slideSpeed[currentChannel]/16.0); 
								//clamp slidestep if target reached
								if((slideSpeed[currentChannel]<0)&&(slideTarget[currentChannel]>slideStep[currentChannel])){
									slideStep[currentChannel]=slideTarget[currentChannel]; 
								}
								if((slideSpeed[currentChannel]>0)&&(slideTarget[currentChannel]<slideStep[currentChannel])){
									slideStep[currentChannel]=slideTarget[currentChannel];
								}
								currentFrequency[currentChannel]=XmToEsfUtil.writeNote(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],slideStep[currentChannel],map.noiseType);
								break;
							case 0x04: //vibrato 
								vibratoStep[currentChannel]=(vibratoStep[currentChannel]+vibratoSpeed[currentChannel]*4);
								/* 
								 * Source code conversion notes from Gemini:
								 *  C++: SIN(pi/180 * vibstep(i)) -- This implies `pi` was actually used for trig in C++!
								 *  The BASIC used `fbmath.bi` which is FreeBASIC's math library.
								 *  `SIN` usually expects radians. `pi/180 * angle_in_degrees` converts to radians.
								 *  Or, if `pi` from `fbmath.bi` is defined as it would be used in trig, then `Math.PI` is fine.
								 *  Given `pi/180`, it's likely `vibstep` is treated as degrees.
								*/
								conversion=XmToEsfUtil.calculateVibratoSlideStepConversion(vibratoStep[currentChannel],vibratoDepth[currentChannel],currentNote[currentChannel]);
								slideStep[currentChannel]=conversion;
								currentFrequency[currentChannel]=XmToEsfUtil.writeNote(esfOut,XmToEsfConst.ESF_CHANNELS[currentChannel],XmToEsfConst.channelType[currentChannel],slideStep[currentChannel],map.noiseType);
								break;
							}
						}
						esfOut.write(ESFEvent.DELAY_TICKS_LONG.getValue());
						esfOut.write((byte)0x01);//1 delay tick (1/60th of a second)
					}
				}//end of main conversion loop
			}

			//end of Song/looping
			if(!map.loop){
				esfOut.write(ESFEvent.STOP.getValue());//end of song, no loop)
			}else if((map.loopPattern>=0)&&(map.loopRow>=0)){//if loop is enabled and restart point is defined
				//restore instruments on looping
				for(int i=0;i<=5;i++){//loop through FM channels
					if((loopInstrument[i]!=currentInstrument[i])){
						esfOut.write((byte)(ESFEvent.SET_INSTRUMENT_FM1.getValue()+XmToEsfConst.ESF_CHANNELS[i])); //instrument change
						byte instrument=(byte)map.instrumentMap.get(loopInstrument[i]).intValue();
						esfOut.write(instrument);
					}
				}
				esfOut.write(ESFEvent.GOTO_LOOP.getValue());
			}else{ //loop enabled but restart is 0 (loop from start)
				esfOut.write(ESFEvent.GOTO_LOOP.getValue());
			}
			//done
			esfOut.flush(); 
			esfOut.close();
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			if(esfOut!=null){
				try{esfOut.flush(); esfOut.close();}catch(Exception x){x.printStackTrace();}
			}
		}		
	}

}