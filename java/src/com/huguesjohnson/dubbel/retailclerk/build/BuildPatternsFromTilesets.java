/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.huguesjohnson.dubbel.file.PathResolver;
import com.huguesjohnson.dubbel.retailclerk.build.objects.PaletteMap;
import com.huguesjohnson.dubbel.retailclerk.build.objects.PatternFromTileset;
import com.huguesjohnson.dubbel.retailclerk.build.objects.Tile8x8;
import com.huguesjohnson.dubbel.retailclerk.build.objects.Tileset;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.PatternFromTilesetParameters;
import com.huguesjohnson.dubbel.util.GenesisColorUtil;

public class BuildPatternsFromTilesets extends BaseBuilder{

	@SuppressWarnings("resource") //everything is closed in finally block, Eclipse doesn't seem to understand that
	public static void build(String basePath,PatternFromTilesetParameters parameters,HashMap<String,Tileset> tileMap,HashMap<String,PaletteMap> paletteMap) throws Exception{
		String includeFilePath=basePath+parameters.patternIncludeFilePath;
		FileWriter patternWriter=null;
		FileWriter includeWriter=null;
		String currentPatternName=null;
		try{
			//setup include writers
			includeWriter=new FileWriter(includeFilePath,true);
			includeWriter.write(newLine);
			includeWriter.write("\t;---------------------------------------------------------------------------");
			includeWriter.write(newLine);
			includeWriter.write("\t; patterns generated from tilesets");
			includeWriter.write(newLine);
			includeWriter.write("\t;---------------------------------------------------------------------------");
			includeWriter.write(newLine);
			//loop through patterns
			for(PatternFromTileset pattern:parameters.patterns){
				currentPatternName=pattern.name;
				//setup the pattern writer
				String patternPath=basePath+pattern.destinationFilePath;
				patternWriter=new FileWriter(patternPath);
				//write pattern name
				patternWriter.write("Pattern"+pattern.name+":");
				patternWriter.write(newLine);
				//read the source file
				String sourceFilePath=basePath+pattern.sourceFilePath;
				File sourceFile=new File(sourceFilePath);
				BufferedImage image=ImageIO.read(sourceFile);
				//get & test image width
				int width=image.getWidth();
				if(width%8!=0){throw(new Exception("Image width must be a multiple of 8 - sourceFile="+sourceFile));}
				//get & test image height
				int height=image.getHeight();
				if(height%8!=0){throw(new Exception("Image width must be a multiple of 8 - sourceFile="+sourceFile));}
				//write pattern size
				int rowCount=height/8;
				patternWriter.write("\tdc.w\t$"+Integer.toHexString(rowCount-1).toUpperCase()+"\t; "+rowCount+" rows");
				patternWriter.write(newLine);
				int colCount=width/8;
				patternWriter.write("\tdc.w\t$"+Integer.toHexString(colCount-1).toUpperCase()+"\t; "+colCount+" columns");
				patternWriter.write(newLine);
				PaletteMap palette=paletteMap.get(pattern.paletteName);
				ArrayList<Tile8x8> baseTiles=tileMap.get(pattern.tilesetName).tiles;
				//loop through all the pixels and filter out duplicate tiles
				int row=0;
				while(row<height){
					int col=0;
					while(col<width){
						Tile8x8 tile8x8=new Tile8x8();
						//loop through each pixel of the next 8x8 cell
						for(int x=col;x<(col+8);x++){
							for(int y=row;y<(row+8);y++){
								int color=image.getRGB(x,y);
								String hexString=Integer.toHexString(color);
								int index=GenesisColorUtil.findNearestColor(palette.colorsHex,hexString);
								//yes, these are getting transposed on purpose
								tile8x8.pixels[y-row][x-col]=index;
							}
						}
						int tileIndex=baseTiles.indexOf(tile8x8);
						if(tileIndex<0){throw(new Exception("tile not found in base tileset - col="+col+" row="+row));}
						patternWriter.write("\tdc.w\t$"+Integer.toHexString(tileIndex).toUpperCase());
						patternWriter.write(newLine);
						col+=8;
					}
					row+=8;
				}	
				patternWriter.flush();
				patternWriter.close();
				//update the include files
				String includePathRel=PathResolver.getRelativePath(includeFilePath.substring(0,includeFilePath.lastIndexOf(File.separator)),patternPath);
				if(includePathRel.startsWith(PathResolver.PARENT_PATH)){
					includePathRel=includePathRel.substring(PathResolver.PARENT_PATH.length()+1);
				}
				if(includePathRel.startsWith(PathResolver.SELF_PATH)){
					includePathRel=includePathRel.substring(PathResolver.SELF_PATH.length()+1);
				}				
				StringBuffer includeString=new StringBuffer();
				includeString.append("\tinclude '");
				includeString.append(includePathRel);
				includeString.append("'");
				includeString.append(newLine);
				includeString.append(newLine);
				includeWriter.write(includeString.toString());
			}
		}catch(Exception x){
			if(currentPatternName==null){
				System.err.println("Error in BuildPatternsFromTilesets - currentPatternName==null");
			}else{
				System.err.println("Error in BuildPatternsFromTilesets - currentPatternName="+currentPatternName);
			}
			throw(x);
		}finally{
			try{if(patternWriter!=null){patternWriter.flush(); patternWriter.close();}}catch(Exception x){ }
			try{if(includeWriter!=null){includeWriter.flush(); includeWriter.close();}}catch(Exception x){ }
		}
	}
}