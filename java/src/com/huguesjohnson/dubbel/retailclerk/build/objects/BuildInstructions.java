/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.io.Serializable;

import com.huguesjohnson.dubbel.retailclerk.build.parameters.AssemblyParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.CollisionDataParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.ConstantFileParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.HeaderParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.MemoryMapParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.PackageParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.PaletteParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SceneParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SimpleSourceDestinationParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SpriteParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.TextParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.TilesetParameters;


public class BuildInstructions implements Serializable{
	private static final long serialVersionUID=666L;

	/* ***********************************************************
	* Base path
	*********************************************************** */
	public String basePath;

	/* ***********************************************************
	* Backup path
	*********************************************************** */
	public String backupPath;

	/* ***********************************************************
	* Memory map
	*********************************************************** */
	public MemoryMapParameters memoryMap;

	/* ***********************************************************
	* Constants
	*********************************************************** */
	public ConstantFileParameters constants;
	
	/* ***********************************************************
	* Collision data
	*********************************************************** */
	public CollisionDataParameters collision;
	
	/* ***********************************************************
	* Palettes
	*********************************************************** */
	public PaletteParameters palettes;
	
	/* ***********************************************************
	* Tiles
	*********************************************************** */
	public TilesetParameters tiles;
	
	/* ***********************************************************
	* Sprite definitions
	*********************************************************** */
	public SpriteParameters sprites;
	
	/* ***********************************************************
	* Scene definitions
	*********************************************************** */
	public SceneParameters scenes;

	/* ***********************************************************
	* Text
	*********************************************************** */
	public TextParameters text;

	/* ***********************************************************
	* Scripted events
	*********************************************************** */
	public SimpleSourceDestinationParameters scriptedEvents;
	
	/* ***********************************************************
	* Movement patterns
	*********************************************************** */
	public SimpleSourceDestinationParameters movementPatterns;
	
	/* ***********************************************************
	* Header
	*********************************************************** */
	public HeaderParameters header;
	
	/* ***********************************************************
	* Assembler
	*********************************************************** */	
	public AssemblyParameters[] assembly;
	
	/* ***********************************************************
	* Package
	*********************************************************** */	
	public PackageParameters[] packageParameters;
}