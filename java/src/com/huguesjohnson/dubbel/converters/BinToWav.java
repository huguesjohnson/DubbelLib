/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

/*
BinToWav - see https://huguesjohnson.com/programming/java/BinToWav/
*/

package com.huguesjohnson.dubbel.converters;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

//this is not meant to be enterprise-y code - it's quick utility class to for a specific purpose
//this was written and tested in 45 minutes - most of that time spent debating whether I wanted to do any error handling at all
public abstract class BinToWav{
	private final static String SEPARATOR=File.separator;
	//these settings assume the .bin is a redbook audio file
	private final static float SAMPLE_RATE=44100;
	private final static int SAMPLE_BITS=16;
	private final static int CHANNELS=2;
	private final static boolean SIGNED=true;
	private final static boolean BIG_ENDIAN=false;
	private final static AudioFormat FORMAT=new AudioFormat(SAMPLE_RATE,SAMPLE_BITS,CHANNELS,SIGNED,BIG_ENDIAN);

	//this expects two arguments
	//cuePath = path to a .cue file
	//outputPath = path (directory) to write wav files
	public static void binToWav(String cuePath,String outputPath){
		BufferedReader cueReader=null;
		AudioInputStream audioIn=null;
		try{
			File cueFile=new File(cuePath);
			//some minimal validation
			if(!cueFile.exists()){
				throw(new Exception(cuePath+" doesn't appear to be a file that exists"));
			}
			cueReader=new BufferedReader(new InputStreamReader(new FileInputStream(cueFile)));
			//cueDir is used to build the path to each .bin file entry
			String cueDir=cueFile.getPath();
			cueDir=cueDir.substring(0,cueDir.lastIndexOf(SEPARATOR)+1);
			//this is about all the validation in this class
			String outputDir=outputPath;
			//more minimal validation
			if(!outputDir.endsWith(SEPARATOR)){outputDir=outputDir+SEPARATOR;}
			if(!(new File(outputDir)).exists()){
				throw(new Exception(outputPath+" doesn't appear to be a directory that exists"));
			}
			String currentLine=null;
			while((currentLine=cueReader.readLine())!=null){
				if(currentLine.toUpperCase().startsWith("FILE")){
					//yes, this will error out if the .cue ends in a FILE line
					//since it's therefore not a valid .cue sheet in the first place this is the least of your problems if you encounter it
					String nextLine=cueReader.readLine();
					if(nextLine.toUpperCase().endsWith("AUDIO")){
						//hooray, we have an audio file
						//next build the path to it
						String fileName=currentLine.substring(currentLine.indexOf("\"")+1,currentLine.lastIndexOf("\""));
						String binPath=cueDir+fileName;
						//now get all the bytes - I'll reserve commentary on the weirdness of not having an overload for getAllBytes that takes a string
						byte[] bytes=Files.readAllBytes(Paths.get(binPath));
						audioIn=new AudioInputStream((new ByteArrayInputStream(bytes)),FORMAT,bytes.length/FORMAT.getFrameSize());
						//build the output file name
						int indexOf=fileName.lastIndexOf('.');
						if(indexOf>0){
							fileName=fileName.substring(0,indexOf);
						}
						String outPath=outputDir+fileName+".wav";
						AudioSystem.write(audioIn,AudioFileFormat.Type.WAVE,new File(outPath));
						audioIn.close();
					}
				}
			}
		}catch(Exception x){
			x.printStackTrace();
		}finally{
			//don't care about errors closing open files
			if(cueReader!=null){try{cueReader.close();}catch(Exception x){}};
			if(audioIn!=null){try{audioIn.close();}catch(Exception x){}};
		}
	}
}