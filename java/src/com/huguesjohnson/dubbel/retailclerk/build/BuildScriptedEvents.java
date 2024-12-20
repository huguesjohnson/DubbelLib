/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.huguesjohnson.dubbel.file.FileUtils;
import com.huguesjohnson.dubbel.file.PathResolver;
import com.huguesjohnson.dubbel.retailclerk.build.objects.ScriptedEvent;
import com.huguesjohnson.dubbel.retailclerk.build.objects.ScriptedEventAction;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SimpleSourceDestinationParameters;

public class BuildScriptedEvents extends BaseBuilder{
	
	@SuppressWarnings("resource") //resources are closed in finally block but Eclipse still warns
	public static void build(String basePath,SimpleSourceDestinationParameters parameters) throws Exception{
		FileWriter writer=null;
		ScriptedEvent[] events=null;
		int scriptedEventIndex=-1;
		int actionIndex=-1;
		ArrayList<String> labels=new ArrayList<String>();
		try{
			String destinationFilePath=PathResolver.getAbsolutePath(basePath,parameters.destinationFile);
			FileUtils.mkdirs(destinationFilePath);
			writer=new FileWriter(destinationFilePath);
			writer.write("; generated by build tools");
			writer.write(newLine);
			writer.write("; source file: "+parameters.sourceFile);
			writer.write(newLine);
			writer.write(newLine);
			String textJson=Files.readString(Paths.get(PathResolver.getAbsolutePath(basePath,parameters.sourceFile)));
			events=(new Gson()).fromJson(textJson,ScriptedEvent[].class);
			for(scriptedEventIndex=0;scriptedEventIndex<events.length;scriptedEventIndex++){
				String label=events[scriptedEventIndex].name;
				if(labels.contains(label)){
					throw(new Exception("BuildScriptedEvents - Duplicate label: "+label));
				}else{
					labels.add(label);
				}
				writer.write(label+"Start:");
				writer.write(newLine);
				ScriptedEventAction[] actions=events[scriptedEventIndex].actions;
				for(actionIndex=0;actionIndex<actions.length;actionIndex++){
					ScriptedEventAction action=actions[actionIndex];
					/*
					 * the order of these matters
					 * comments are written first
					 */
					if((action.comment!=null)&&(action.comment.length()>0)){
						writer.write("\t; "+action.comment);
						writer.write(newLine);
					}
					/*
					 * then the command, directions are effectively commands
					 */
					if(action.command!=null){
						writer.write("\tdc.w\t"+action.command.toString());
						writer.write(newLine);
					}
					if(action.direction!=null){
						writer.write("\tdc.w\t"+action.direction.toString());
						writer.write(newLine);
					}
					/* 
					 * if a command has a scene id it needs to be next
					 */
					if((action.sceneId!=null)&&(action.sceneId.length()>0)){
						//works the same as intConst - just nice to have a different label
						writer.write("\tdc.w\t"+action.sceneId);
						writer.write(newLine);
					}
					/* 
					 * last are values & constants
					 */					
					if(action.intValue!=null){
						//this is such a weird way to convert an Integer to a hexstring
						StringBuilder sb=new StringBuilder(Integer.toHexString(action.intValue.intValue()).toUpperCase());
						while(sb.length()<4){sb.insert(0,'0');}
						sb.insert(0,"\tdc.w\t$");
						writer.write(sb.toString());
						writer.write(newLine);
					}
					if(action.longValue!=null){
						//this is such a weird way to convert an Integer to a hexstring
						StringBuilder sb=new StringBuilder(Integer.toHexString(action.longValue.intValue()).toUpperCase());
						while(sb.length()<8){sb.insert(0,'0');}
						sb.insert(0,"\tdc.l\t$");
						writer.write(sb.toString());
						writer.write(newLine);
					}					
					if((action.intConst!=null)&&(action.intConst.length()>0)){
						writer.write("\tdc.w\t"+action.intConst);
						writer.write(newLine);
					}
					if((action.label!=null)&&(action.label.length()>0)){
						writer.write("\tdc.l\t"+action.label);
						writer.write(newLine);
					}
				}
				writer.write(events[scriptedEventIndex].name+"End:");
				writer.write(newLine);
				writer.write(newLine);
			}
		}catch(Exception x){
			System.err.println("Error in BuildScripted Events");
			System.err.println("parameters.sourceFile="+parameters.sourceFile);
			System.err.println("parameters.destinationFile="+parameters.destinationFile);
			if(events==null){
				System.err.println("events==null");
			}else{
				System.err.println("scriptedEventIndex="+scriptedEventIndex);
				System.err.println("actionIndex="+actionIndex);
				if(scriptedEventIndex<0){
					System.err.println("scriptedEventIndex<0 (which you already know)");
				}else if(scriptedEventIndex>=events.length){
					System.err.println("scriptedEventIndex>=events.length");
					System.err.println("events.length="+events.length);
				}else{
					if(events[scriptedEventIndex]==null){
						System.err.println("events[scriptedEventIndex]==null");
					}else{
						System.err.println("events[scriptedEventIndex].name="+events[scriptedEventIndex].name);
					}
				}
			}		
			throw(x);
		}finally{
			try{if(writer!=null){writer.flush();writer.close();}}catch(Exception x){ }
		}
	}
}
