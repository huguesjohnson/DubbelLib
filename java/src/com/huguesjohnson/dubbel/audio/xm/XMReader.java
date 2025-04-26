/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.audio.xm;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import com.huguesjohnson.dubbel.audio.xm.XMConstants.FieldLengths;

public abstract class XMReader{

	public static XMFile read(String filePath) throws Exception{
		return(read(new File(filePath)));
	}
	
	public static XMFile read(File f) throws Exception{
		XMFile xm=new XMFile();
		/*
		 * This assumes most xm files are small and reading the entire file won't be a memory issue.
		 * I don't know why this would ever be used in a way this is an issue. 
		 * 
		 * This is also written to make debugging easier. 
		 * Yes, this could be written in fewer lines if that is a thing important to anyone.
		 */
		byte[] b=Files.readAllBytes(f.toPath());
		XMHeader header=new XMHeader();
		/*
		 * Header -> Module ID
		 */
		int start=0;
        int end=start+XMConstants.FieldLengths.HEADER_ID;
		header.setId(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Module name
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_MODULENAME;
		header.setName(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Tracker name
		 */		
		start=end+1;//+1 for fixed field after modulename
        end=start+XMConstants.FieldLengths.HEADER_TRACKERNAME;
		header.setTrackerName(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Version number
		 */			
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_VERSION;
		header.setVersionNumber(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Header size
		 */			
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SIZE;
		header.setHeaderSize(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Song length
		 */			
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_LENGTH;
		header.setSongLength(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Song restart position
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_RESTART;
		header.setRestartPosition(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Song number of channels
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_NUM_CHANNELS;
		header.setNumChannels(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Song number of patterns
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_NUM_PATTERNS;
		header.setNumPatterns(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Song number of instruments
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_NUM_INSTRUMENTS;
        header.setNumInstruments(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Flags
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_FLAGS;
		header.setFlags(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Default tempo
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_TEMPO;
		header.setDefaultTempo(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Default BPM
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_BPM;
		header.setDefaultBPM(Arrays.copyOfRange(b,start,end));
		/*
		 * Header -> Pattern order table
		 */
		start=end;
        end=start+XMConstants.FieldLengths.HEADER_SONG_PATTERN_ORDER;
		header.setPatternOrderTable(Arrays.copyOfRange(b,start,end));
		xm.setHeader(header);
		/*
		 * Now patterns
		 */
		int patternStart=end;
		ArrayList<XMPattern> patterns=new ArrayList<XMPattern>();
		int patternCount=header.getNumPatterns();
		for(int patternIndex=0;patternIndex<patternCount;patternIndex++){
			XMPattern pattern=new XMPattern();
			/*
			 * Pattern -> header length
			 */
			start=patternStart;
			end=start+FieldLengths.PATTERN_HEADER;
			pattern.setHeaderSize(Arrays.copyOfRange(b,start,end));
			/*
			 * Pattern -> number of rows
			 */
			start=patternStart+XMConstants.Offsets.PATTERN_NUM_ROWS;
			end=start+FieldLengths.PATTERN_NUM_ROWS;
			pattern.setNumRows(Arrays.copyOfRange(b,start,end));
			/*
			 * Pattern -> data size
			 */
			start=patternStart+XMConstants.Offsets.PATTERN_DATA_SIZE;
			end=start+FieldLengths.PATTERN_DATA_SIZE;
			pattern.setPatternDataSize(Arrays.copyOfRange(b,start,end));
			/*
			 * Pattern -> data
			 */
			int patternDataSize=pattern.getPatternDataSize();
			start=end;
			end=start+patternDataSize;
			pattern.setPatternData(Arrays.copyOfRange(b,start,end));
			patterns.add(pattern);
			patternStart=end;
		}
		xm.setPatterns(patterns);
		/*
		 * Now instruments
		 */
		ArrayList<XMInstrument> instruments=new ArrayList<XMInstrument>();
		int numInstruments=xm.getHeader().getNumInstruments();
		for(int instrumentNumber=0;instrumentNumber<numInstruments;instrumentNumber++){
			XMInstrument instrument=new XMInstrument();
			/*
			 * Instrument -> header size
			 */
			start=end;
	        end=start+XMConstants.FieldLengths.INSTRUMENT_HEADER_SIZE;
	        instrument.setHeaderSize(Arrays.copyOfRange(b,start,end));
			/*
			 * Instrument -> name
			 */
	        start=end;
	        end=start+XMConstants.FieldLengths.INSTRUMENT_NAME;
	        instrument.setName(Arrays.copyOfRange(b,start,end));
			/*
			 * Instrument -> type
			 */
	        start=end;
	        end=start+XMConstants.FieldLengths.INSTRUMENT_TYPE;
	        instrument.setType(b[start]);
			/*
			 * Instrument -> number of samples
			 */
	        start=end;
	        end=start+XMConstants.FieldLengths.INSTRUMENT_NUM_SAMPLES;
	        instrument.setNumSamples(Arrays.copyOfRange(b,start,end));
			instruments.add(instrument);
			int numSamples=instrument.getNumSamples();
			if(numSamples>0){
				XMInstrumentHeader instrumentHeader=new XMInstrumentHeader();
				/*
				 * Instrument -> header -> header size
				 */		
				start=end;
		        end=start+XMConstants.FieldLengths.INSTRUMENT_HEADER_SIZE;
		        instrumentHeader.setHeaderSize(Arrays.copyOfRange(b,start,end));
				/*
				 * Instrument -> header -> keymap
				 */		
				start=end;
			    end=start+XMConstants.FieldLengths.INSTRUMENT_SAMPLENUMBER_SIZE;
			    instrumentHeader.setNoteSampleNumbers(Arrays.copyOfRange(b,start,end));
				/*
				 * Instrument -> header -> volume envelope
				 */		
				start=end;
			    end=start+XMConstants.FieldLengths.INSTRUMENT_ENVELOPEPOINTS_SIZE;
			    instrumentHeader.setEnvelopeVolumePoints(Arrays.copyOfRange(b,start,end));
				/*
				 * Instrument -> header -> panning envelope
				 */		
				start=end;
			    end=start+XMConstants.FieldLengths.INSTRUMENT_ENVELOPEPOINTS_SIZE;
			    instrumentHeader.setEnvelopePanningPoints(Arrays.copyOfRange(b,start,end));
				/*
				 * Instrument -> header -> number of volume points
				 */		
				start=end;
				instrumentHeader.setNumVolumePoints(b[start]);
				/*
				 * Instrument -> header -> number of panning points
				 */		
				start++;
				instrumentHeader.setNumPanningPoints(b[start]);
				/*
				 * Instrument -> header -> volume sustain point
				 */		
				start++;
				instrumentHeader.setVolumeSustainPoint(b[start]);
				/*
				 * Instrument -> header -> volume loop start point
				 */		
				start++;
				instrumentHeader.setVolumeLoopStartPoint(b[start]);
				/*
				 * Instrument -> header -> volume loop end point
				 */	
				start++;
				instrumentHeader.setVolumeLoopEndPoint(b[start]);
				/*
				 * Instrument -> header -> panning sustain point
				 */		
				start++;
				instrumentHeader.setPanningSustainPoint(b[start]);
				/*
				 * Instrument -> header -> panning loop start point
				 */		
				start++;
				instrumentHeader.setPanningLoopStartPoint(b[start]);
				/*
				 * Instrument -> header -> panning loop end point
				 */	
				start++;
				instrumentHeader.setPanningLoopEndPoint(b[start]);
				/*
				 * Instrument -> header -> volume type
				 */	
				start++;
				instrumentHeader.setVolumeType(b[start]);
				/*
				 * Instrument -> header -> panning type
				 */	
				start++;
				instrumentHeader.setPanningType(b[start]);
				/*
				 * Instrument -> header -> vibrato type
				 */	
				start++;
				instrumentHeader.setVibratoType(b[start]);
				/*
				 * Instrument -> header -> vibrato sweep
				 */	
				start++;
				instrumentHeader.setVibratoSweep(b[start]);
				/*
				 * Instrument -> header -> vibrato depth
				 */	
				start++;
				instrumentHeader.setVibratoDepth(b[start]);
				/*
				 * Instrument -> header -> vibrato rate
				 */	
				start++;
				instrumentHeader.setVibratoRate(b[start]);
				/*
				 * Instrument -> header -> volume fadeout
				 */				        
				start++;
			    end=start+XMConstants.FieldLengths.INSTRUMENT_VOLUMEFADEOUT_SIZE;
			    instrumentHeader.setVolumeFadeout(Arrays.copyOfRange(b,start,end));
				/*
				 * Instrument -> header -> reserved
				 */		
				start=end;
			    end=start+XMConstants.FieldLengths.INSTRUMENT_RESERVED_SIZE;
			    instrumentHeader.setReserved(Arrays.copyOfRange(b,start,end));
				instrument.setInstrumentHeader(instrumentHeader);
				/*
				 * Sample headers
				 */
				ArrayList<XMSampleHeader> sampleHeaders=new ArrayList<XMSampleHeader>();
				for(int i=0;i<numSamples;i++){
					XMSampleHeader sampleHeader=new XMSampleHeader();
					/*
					 * Instrument -> sample header -> sample length
					 */	
					start=end;
				    end=start+XMConstants.FieldLengths.SAMPLE_HEADER_LENGTH;
				    sampleHeader.setSampleLength(Arrays.copyOfRange(b,start,end));
					/*
					 * Instrument -> sample header -> Loop start
					 */
					start=end;
				    end=start+XMConstants.FieldLengths.SAMPLE_HEADER_LOOPSTART;
				    sampleHeader.setSampleLoopStart(Arrays.copyOfRange(b,start,end));
					/*
					 * Instrument -> sample header -> Loop length
					 */
					start=end;
				    end=start+XMConstants.FieldLengths.SAMPLE_HEADER_LOOPLENGTH;
				    sampleHeader.setSampleLoopLength(Arrays.copyOfRange(b,start,end));
					/*
					 * Instrument -> sample header -> Volume
					 */
					start=end;
					sampleHeader.setVolume(b[start]);
					/*
					 * Instrument -> sample header -> Finetune
					 */
					start++;
					sampleHeader.setFinetune(b[start]);
					/*
					 * Instrument -> sample header -> Type
					 */
					start++;
					sampleHeader.setType(b[start]);
					/*
					 * Instrument -> sample header -> Panning
					 */
					start++;
					sampleHeader.setPanning(b[start]);
					/*
					 * Instrument -> sample header -> Relative note number
					 */
					start++;
					sampleHeader.setRelativeNoteNumber(b[start]);
					/*
					 * Instrument -> sample header -> Packing
					 */
					start++;
					sampleHeader.setPacking(b[start]);
					/*
					 * Instrument -> sample header -> Name
					 */
					start++;
				    end=start+XMConstants.FieldLengths.SAMPLE_HEADER_NAME;
				    sampleHeader.setName(Arrays.copyOfRange(b,start,end));
					sampleHeaders.add(sampleHeader);
				}
				instrument.setSampleHeaders(sampleHeaders);
				/*
				 * Read sample data
				 */
				ArrayList<XMSampleData> sampleData=new ArrayList<XMSampleData>();
				for(int i=0;i<numSamples;i++){
					int sampleLength=instrument.getSampleHeaders().get(i).getSampleLength();
					start=end;
					end=start+sampleLength;
					sampleData.add(new XMSampleData(Arrays.copyOfRange(b,start,end)));
				}
				instrument.setSampleData(sampleData);
			}//if(numSamples>0)
		}//for instrument loop
		xm.setInstruments(instruments);
		return(xm);
	}
}