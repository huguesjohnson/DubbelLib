/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.huguesjohnson.dubbel.retailclerk.build.objects.Direction;
import com.huguesjohnson.dubbel.retailclerk.build.objects.MovementPattern;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SimpleSourceDestinationParameters;

public class BuildMovementPatterns extends BaseBuilder{

	public static void build(String basePath,SimpleSourceDestinationParameters parameters) throws Exception{
		FileWriter writer=null;
		MovementPattern[] patterns=null;
		int patternIndex=-1;
		try{
			writer=new FileWriter(basePath+parameters.destinationFile);
			writer.write("; generated by build tools");
			writer.write(newLine);
			writer.write(newLine);
			//read the movement patterns
			String textJson=Files.readString(Paths.get(basePath+parameters.sourceFile));
			patterns=(new Gson()).fromJson(textJson,MovementPattern[].class);
			for(patternIndex=0;patternIndex<patterns.length;patternIndex++){
				writer.write(patterns[patternIndex].name+"Start:");
				writer.write(newLine);
				Direction[] steps=patterns[patternIndex].steps;
				for(int stepIndex=0;stepIndex<steps.length;stepIndex++){
					StringBuilder line=new StringBuilder();
					line.append("\tdc.w\t");
					line.append(steps[stepIndex].toString());
					line.append(newLine);
					writer.write(line.toString());
				}
				writer.write(patterns[patternIndex].name+"End:");
				writer.write(newLine);
				writer.write(newLine);
			}
		}catch(Exception x){
			System.err.println("Error in BuildMovementPatterns");
			if(patterns==null){
				System.err.println("patterns==null");
			}else{
				System.err.println("patternIndex="+patternIndex);
				if(patternIndex<0){
					System.err.println("patternIndex<0 (which you already know)");
				}else if(patternIndex>=patterns.length){
					System.err.println("patternIndex>=patterns.length");
					System.err.println("patterns.length="+patterns.length);
				}else{
					if(patterns[patternIndex]==null){
						System.err.println("patterns[patternIndex]==null");
					}else{
						System.err.println("patterns[patternIndex].name="+patterns[patternIndex].name);
					}
				}
			}
			throw(x);
		}finally{
			try{if(writer!=null){writer.flush(); writer.close();}}catch(Exception x){ }
		}
	}
}