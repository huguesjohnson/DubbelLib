/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.huguesjohnson.dubbel.file.PathResolver;
import com.huguesjohnson.dubbel.retailclerk.build.objects.PaletteMap;
import com.huguesjohnson.dubbel.retailclerk.build.objects.Sprite;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SpriteParameters;
import com.huguesjohnson.dubbel.util.GenesisColorUtils;

public abstract class BuildSprites extends BaseBuilder{
	
	@SuppressWarnings("resource") //everything is closed in finally block, Eclipse doesn't seem to understand that
	public static void build(String basePath,SpriteParameters sprites,HashMap<String,PaletteMap> paletteMap){
		//if the palette is missing then bail
		PaletteMap palette=paletteMap.get(sprites.palette);
		//setup sprite id
		int id=Integer.parseInt(sprites.baseId,16);
		FileWriter includeWriter=null;
		FileWriter definitionWriter=null;
		FileWriter constantWriter=null;
		FileWriter tileWriter=null;
		Sprite currentSprite=null;
		try{
			//setup writers
			String includeFilePath=basePath+sprites.includeFilePath;
			includeWriter=new FileWriter(includeFilePath);
			includeWriter.write("; generated by build tools");
			includeWriter.write(newLine);
			includeWriter.write(newLine);
			String definitionFilePath=basePath+sprites.characterDefinitionFilePath;
			definitionWriter=new FileWriter(definitionFilePath);
			definitionWriter.write("; generated by build tools");
			definitionWriter.write(newLine);
			definitionWriter.write(newLine);
			definitionWriter.write("CharacterDefinitionStart:");
			definitionWriter.write(newLine);
			definitionWriter.write(newLine);
			String constantFilePath=basePath+sprites.constantDefinitionPath;
			constantWriter=new FileWriter(constantFilePath);
			constantWriter.write("; generated by build tools");
			constantWriter.write(newLine);
			constantWriter.write(newLine);
			constantWriter.write("OBJ_NPC_BASE=$"+sprites.baseId.toUpperCase());
			constantWriter.write(newLine);
			//loop through all the sprites
			for(int i=0;i<sprites.sprites.length;i++){
				id++;	//increment id first
				currentSprite=sprites.sprites[i];
				String spriteName=currentSprite.name.replace(" ","");
				String hexId=Integer.toHexString(id);
				String sourceFilePath=basePath+currentSprite.sourceFilePath;
				String outputFilePath=basePath+currentSprite.destinationFilePath;
				tileWriter=new FileWriter(outputFilePath);
				tileWriter.write("; generated by build tools");
				tileWriter.write(newLine);
				tileWriter.write(newLine);
				//generate the tiles
				//open the image
				File sourceFile=new File(sourceFilePath);
				BufferedImage image=ImageIO.read(sourceFile);
				//get & test image width
				int width=image.getWidth();
				if(width%8!=0){throw(new Exception("Image width must be a multiple of 8"));}
				//get & test image height
				int height=image.getHeight();
				if(height%8!=0){throw(new Exception("Image width must be a multiple of 8"));}
				//setup variables used to track reading the image
				int totalFrames=12;
				int currentFrame=0;
				//loop through sprite sheet
				while(currentFrame<totalFrames){
					tileWriter.write(newLine);
					tileWriter.write(spriteName+"_Frame"+currentFrame+"Start:");
					tileWriter.write(newLine);
					tileWriter.write(newLine);
					for(int col=0;col<=1;col++){
						for(int row=0;row<=3;row++){
							//loop through each pixel of the next 8x8 cell
							for(int y=0;y<8;y++){
								StringBuffer line=new StringBuffer();
								line.append("\tdc.l\t$");
								for(int x=0;x<8;x++){
									int pixelX=(col*8)+x;
									int pixelY=(row*8)+(currentFrame*32)+y;
									int color=image.getRGB(pixelX,pixelY); 
									String hexString=Integer.toHexString(color);
									int index=GenesisColorUtils.findNearestColor(palette.colorsHex,hexString);
									line.append(Integer.toHexString(index).toUpperCase());
								}
								line.append(newLine);
								tileWriter.write(line.toString());
							}
							tileWriter.write(newLine);
						}
					}
					tileWriter.write(spriteName+"_Frame"+currentFrame+"End:");
					tileWriter.write(newLine);
					currentFrame++;
				}
				tileWriter.flush();
				tileWriter.close();
				//update constants
				StringBuffer constLine=new StringBuffer("OBJ_NPC_");
				constLine.append(spriteName.toUpperCase());
				constLine.append("=$");
				constLine.append(hexId.toUpperCase());
				constLine.append(newLine);
				constantWriter.write(constLine.toString());
				//update include file
				String includePathRel=PathResolver.getRelativePath(includeFilePath,outputFilePath);
				if(includePathRel.startsWith("..")){
					includePathRel=includePathRel.substring(3);
				}
				StringBuffer includeString=new StringBuffer();
				String label=spriteName+"SpriteTiles";
				includeString.append(label);
				includeString.append("Start:");
				includeString.append(newLine);
				includeString.append("\tinclude '");
				includeString.append(includePathRel);
				includeString.append("'");
				includeString.append(newLine);
				includeString.append(label);
				includeString.append("End:");
				includeString.append(newLine);
				includeString.append(newLine);
				includeWriter.write(includeString.toString());
				//update character definition
				StringBuffer line=new StringBuffer("; ");
				line.append(hexId);
				line.append(" - ");
				line.append(currentSprite.name);
				line.append(newLine);
				definitionWriter.write(line.toString());
				line=new StringBuffer("CharacterDefinition");
				line.append(spriteName);
				line.append("Start:");
				line.append(newLine);
				definitionWriter.write(line.toString());
				line=new StringBuffer("\tdc.l\t");
				line.append(spriteName);
				line.append("SpriteTilesStart");
				line.append(newLine);
				definitionWriter.write(line.toString());
				line=new StringBuffer("CharacterDefinition");
				line.append(spriteName);
				line.append("End:");
				line.append(newLine);
				line.append(newLine);
				definitionWriter.write(line.toString());
				//update character name lookup table
				line=new StringBuffer("\tdc.l\t");
				line.append("CharacterName");
				line.append(spriteName);
				line.append(newLine);
				//add character name
				line=new StringBuffer("CharacterName");
				line.append(spriteName);
				line.append(":");
				line.append(newLine);
				line.append("\tdc.b\t\"");
				line.append(currentSprite.name);
				line.append("\",NPCNAMEEND");
				line.append(newLine);
				line.append(newLine);
			}
			definitionWriter.write("CharacterDefinitionEnd:");
			definitionWriter.write(newLine);
		}catch(Exception x){
			x.printStackTrace();
			if(currentSprite==null){
				System.err.println("currentSprite==null");
			}else{
				System.err.println("currentSprite.name="+currentSprite.name);
			}
		}finally{
			try{if(includeWriter!=null){includeWriter.flush(); includeWriter.close();}}catch(Exception x){ }
			try{if(definitionWriter!=null){definitionWriter.flush(); definitionWriter.close();}}catch(Exception x){ }
			try{if(constantWriter!=null){constantWriter.flush(); constantWriter.close();}}catch(Exception x){ }
			try{if(tileWriter!=null){tileWriter.flush(); tileWriter.close();}}catch(Exception x){ }
		}
	}
	
}