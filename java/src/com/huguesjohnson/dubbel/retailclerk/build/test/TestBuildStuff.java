/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.huguesjohnson.dubbel.retailclerk.build.BuildScenes;
import com.huguesjohnson.dubbel.retailclerk.build.objects.ActionTableEntry;
import com.huguesjohnson.dubbel.retailclerk.build.objects.BuildInstructions;
import com.huguesjohnson.dubbel.retailclerk.build.objects.PaletteMap;
import com.huguesjohnson.dubbel.retailclerk.build.objects.PatternFromTileset;
import com.huguesjohnson.dubbel.retailclerk.build.objects.Sprite;
import com.huguesjohnson.dubbel.retailclerk.build.objects.Tileset;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.ActionTableParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.AssemblyParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.CollisionDataParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.ConstantFileParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.HeaderParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.MemoryMapParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.PackageParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.PaletteParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.PatternFromTilesetParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SceneParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SimpleSourceDestinationParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.SpriteParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.TextParameters;
import com.huguesjohnson.dubbel.retailclerk.build.parameters.TilesetParameters;
import com.huguesjohnson.dubbel.util.GenesisColorUtil;
import com.huguesjohnson.dubbel.util.NumberFormatters;

import junit.framework.TestCase;

class TestBuildStuff extends TestCase{

	@Test
	void testColorUtils(){
		String s=GenesisColorUtil.hexStringToGenesisRgb("ff");
		assertEquals("111",s);
		s=GenesisColorUtil.rgbStringToGenesisRgbString("ffe0a040");
		assertEquals("0000010010101110",s);
		s=GenesisColorUtil.genesisRgbStringToHexString("%"+s);
		assertEquals("ffe0a040",s);
		s=GenesisColorUtil.genesisRgbStringToHexString("dc.w\t%0000010010101110 ; blah"+0000010010101110);
		assertEquals("ffe0a040",s);
		ArrayList<String> colors=new ArrayList<String>();
		colors.add("ffe000e0");//00
		colors.add("ff000000");//01
		colors.add("ff806440");//02
		colors.add("ffe0c8a0");//03
		colors.add("ff80a8c0");//04		
		colors.add("ff604020");//05
		colors.add("ffc0a880");//06
		colors.add("ffa06420");//07
		colors.add("ff608440");//08
		colors.add("ff802080");//09
		colors.add("ffc0c8c0");//0A
		colors.add("ffc06420");//0B
		colors.add("ffc00000");//0C
		colors.add("ffe0e8e0");//0D
		colors.add("ffe0c820");//0E
		colors.add("ff6084a0");//0F
		assertEquals(0,GenesisColorUtil.findNearestColor(colors,"ffe000e0"));
		assertEquals(1,GenesisColorUtil.findNearestColor(colors,"ff000000"));
		assertEquals(2,GenesisColorUtil.findNearestColor(colors,"ff806440"));
		assertEquals(3,GenesisColorUtil.findNearestColor(colors,"ffe0c8a0"));
		assertEquals(4,GenesisColorUtil.findNearestColor(colors,"ff80a8c0"));
		assertEquals(5,GenesisColorUtil.findNearestColor(colors,"ff604020"));
		assertEquals(6,GenesisColorUtil.findNearestColor(colors,"ffc0a880"));
		assertEquals(7,GenesisColorUtil.findNearestColor(colors,"ffa06420"));
		assertEquals(8,GenesisColorUtil.findNearestColor(colors,"ff608440"));
		assertEquals(9,GenesisColorUtil.findNearestColor(colors,"ff802080"));
		assertEquals(10,GenesisColorUtil.findNearestColor(colors,"ffc0c8c0"));
		assertEquals(11,GenesisColorUtil.findNearestColor(colors,"ffc06420"));
		assertEquals(12,GenesisColorUtil.findNearestColor(colors,"ffc00000"));
		assertEquals(13,GenesisColorUtil.findNearestColor(colors,"ffe0e8e0"));
		assertEquals(14,GenesisColorUtil.findNearestColor(colors,"ffe0c820"));
		assertEquals(15,GenesisColorUtil.findNearestColor(colors,"ff6084a0"));
		assertEquals(0,GenesisColorUtil.findNearestColor(colors,"ffe000e8"));
		assertEquals(1,GenesisColorUtil.findNearestColor(colors,"ff200000"));
		assertEquals(2,GenesisColorUtil.findNearestColor(colors,"ff806040"));
		assertEquals(3,GenesisColorUtil.findNearestColor(colors,"ffe0c0a0"));
		assertEquals(4,GenesisColorUtil.findNearestColor(colors,"ff80a0c0"));
		assertEquals(5,GenesisColorUtil.findNearestColor(colors,"ff602020"));
		assertEquals(6,GenesisColorUtil.findNearestColor(colors,"ffa0a880"));
		assertEquals(7,GenesisColorUtil.findNearestColor(colors,"ffa06400"));
		assertEquals(8,GenesisColorUtil.findNearestColor(colors,"ff608420"));
		assertEquals(9,GenesisColorUtil.findNearestColor(colors,"ff800080"));
		assertEquals(10,GenesisColorUtil.findNearestColor(colors,"ffc0c0c0"));
		assertEquals(11,GenesisColorUtil.findNearestColor(colors,"ffc06820"));
		assertEquals(12,GenesisColorUtil.findNearestColor(colors,"ffc00020"));
		assertEquals(13,GenesisColorUtil.findNearestColor(colors,"ffe0e8c0"));
		assertEquals(14,GenesisColorUtil.findNearestColor(colors,"ffe0c800"));
		assertEquals(15,GenesisColorUtil.findNearestColor(colors,"ff6080a0"));
	}
	
	@Test
	void testToHexWord(){
		assertEquals("$0000",NumberFormatters.toHexWord(0));
		assertEquals("$000D",NumberFormatters.toHexWord(13));
		assertEquals("$029A",NumberFormatters.toHexWord(666));
		assertEquals("$FFFF",NumberFormatters.toHexWord(65535));
	}
	
	@Test
	void testBuildPatternString(){
		assertEquals("0000000000000001",BuildScenes.buildPatternString(1,0,false));
		assertEquals("1000000000001001",BuildScenes.buildPatternString(9,0,true));
		assertEquals("0010000000011101",BuildScenes.buildPatternString(29,1,false));
		assertEquals("1100000000101101",BuildScenes.buildPatternString(45,2,true));
		assertEquals("0110000010000010",BuildScenes.buildPatternString(130,3,false));
	}
	
	@Test
	void testBuildRowColumnString(){
		assertEquals("00000000",BuildScenes.buildRowColumnString(null));
		assertEquals("00000000",BuildScenes.buildRowColumnString("0"));
		assertEquals("03800000",BuildScenes.buildRowColumnString("03800000"));
		assertEquals("03800000",BuildScenes.buildRowColumnString("3800000"));
	}
	
	@Test
	void testBuildWHXYString(){
		assertEquals("1000111010001000",BuildScenes.buildWHXYString(71,136));
		assertEquals("0101000100001000",BuildScenes.buildWHXYString(40,264));
	}
	
	@Test
	void testActionTableEntry(){
		ActionTableEntry e1=new ActionTableEntry();
		e1.action="action1";
		e1.day=2;
		e1.scene="scene3";
		ActionTableEntry e2=new ActionTableEntry();
		e2.action="action1";
		e2.day=2;
		e2.scene="scene3";
		ActionTableEntry e3=new ActionTableEntry();
		e3.action="scene666";
		e3.day=13;
		e3.scene="scene8964";
		assertTrue(e1.equals(e2));
		assertTrue(e2.equals(e1));
		assertTrue(e1.hashCode()==e2.hashCode());
		assertFalse(e1.equals(e3));
		assertFalse(e2.equals(e3));
		assertFalse(e3.equals(e1));
		assertFalse(e3.equals(e2));
		assertFalse(e1.hashCode()==e3.hashCode());
		assertFalse(e2.hashCode()==e3.hashCode());
	}
	
/*
 * This is the worst-named thing I've created in a while because it's not really testing anything.
 * This is really just a simple way to avoid writing json by hand.
 */
	@Test
	void testBuildInstructions(){
		/*
		 * Test that everything gets to/from json correctly
		 * Also doubles as a way to build the initial instructions file
		 */
		BuildInstructions instructions=new BuildInstructions();
		instructions.basePath=".";
		
		/* ***********************************************************
		* Memory map parameters
		*********************************************************** */
		instructions.memoryMap=new MemoryMapParameters();
		instructions.memoryMap.sourceFiles=new String [2];
		instructions.memoryMap.sourceFiles[0]="src/MemoryMapRCBase.csv";
		instructions.memoryMap.sourceFiles[1]="src/MemoryMapRC90.csv";
		instructions.memoryMap.destinationFile="src/const_MemoryMap.X68";
		instructions.memoryMap.baseAddress="FFFF0000";

		/* ***********************************************************
		* Constant parameters
		*********************************************************** */
		instructions.constants=new ConstantFileParameters();
		instructions.constants.includeFilePath="src/inc_Constants.X68";
		instructions.constants.fileMap=new HashMap<String,String>();
		instructions.constants.fileMap.put(
				"design/constants/test.csv",
				"src/const_test.X68");
		
		/* ***********************************************************
		* Collision data parameters
		*********************************************************** */
		instructions.collision=new CollisionDataParameters();
		instructions.collision.includeFilePath="src/inc_CollisionMaps.X68";
		instructions.collision.collisionMap=new HashMap<String,String>();
		instructions.collision.collisionMap.put(
				"design/collision/store-00-512x512.bmp",
				"src/collision-maps/map_Store00Collision.X68");
		
		/* ***********************************************************
		* Palettes parameters
		*********************************************************** */
		instructions.palettes=new PaletteParameters();
		instructions.palettes.includeFilePath="src/inc_Palettes.X68";
		instructions.palettes.paletteMap=new PaletteMap[2];
		instructions.palettes.paletteMap[0]=new PaletteMap();
		instructions.palettes.paletteMap[0].sourceFilePath="design/img/swatches/people.png";
		instructions.palettes.paletteMap[0].destinationFilePath="src/palettes/People.X68";
		instructions.palettes.paletteMap[1]=new PaletteMap();
		instructions.palettes.paletteMap[1].sourceFilePath="design/img/swatches/people1.png";
		instructions.palettes.paletteMap[1].destinationFilePath="src/palettes/People1.X68";
		instructions.palettes.paletteMap[1].exclude="true";
		
		/* ***********************************************************
		* Tile parameters
		*********************************************************** */
		instructions.tiles=new TilesetParameters();
		instructions.tiles.tileIncludeFilePath="src/inc_SpriteTiles.X68";
		instructions.tiles.patternIncludeFilePath="src/inc_PatternsGenerated.X68";
		instructions.tiles.tilesets=new Tileset[2];
		instructions.tiles.tilesets[0]=new Tileset();
		instructions.tiles.tilesets[0].name="DialogFrame";
		instructions.tiles.tilesets[0].palette="src/palettes/People.X68";
		instructions.tiles.tilesets[0].sourceFilePath="design/img/font-dialog-tiles/frame.png";
		instructions.tiles.tilesets[0].destinationFilePath="src/tiles/font-tiles/dialog-frame.X68";
		instructions.tiles.tilesets[1]=new Tileset();
		instructions.tiles.tilesets[1].name="Font";
		instructions.tiles.tilesets[1].palette="src/palettes/People.X68";
		instructions.tiles.tilesets[1].sourceFilePath="design/img/font-dialog-tiles/font.png";
		instructions.tiles.tilesets[1].destinationFilePath="src/tiles/font-tiles/dwf.X68";

		/* ***********************************************************
		* PatternFromTilesetParameters parameters
		*********************************************************** */
		instructions.patterns=new PatternFromTilesetParameters();
		instructions.patterns.patternIncludeFilePath="src/inc_PatternsGenerated.X68";
		instructions.patterns.patterns=new PatternFromTileset[1];
		instructions.patterns.patterns[0]=new PatternFromTileset();
		instructions.patterns.patterns[0].name="FrameNWOpen";
		instructions.patterns.patterns[0].destinationFilePath="src/patterns/frame-nw-open.X68";
		instructions.patterns.patterns[0].paletteName="PaletteFrameRect";
		instructions.patterns.patterns[0].sourceFilePath="design/img/scene-tiles/frame-nw-open.png";
		instructions.patterns.patterns[0].tilesetName="FrameRect";
		
		/* ***********************************************************
		* Sprite definition parameters
		*********************************************************** */
		SpriteParameters sprites=new SpriteParameters();
		sprites.palette="src/palettes/People.X68";
		sprites.includeFilePath="src/inc_SpriteTiles.X68";
		sprites.characterDefinitionFilePath="src/data_CharacterDefinitions.X68";
		sprites.constantDefinitionPath="src/const_CharacterIDs.X68";
		sprites.baseId="2000";
		sprites.sprites=new Sprite[2];
		sprites.sprites[0]=new Sprite();
		sprites.sprites[0].name="Eryn";
		sprites.sprites[0].sourceFilePath="design/img/sprite-tiles/pc-eryn.png";
		sprites.sprites[0].destinationFilePath="src/tiles/sprite-tiles/pc-eryn.X68";
		sprites.sprites[1]=new Sprite();
		sprites.sprites[1].name="Carl";
		sprites.sprites[1].sourceFilePath="design/img/sprite-tiles/pc-carl.png";
		sprites.sprites[1].destinationFilePath="src/tiles/sprite-tiles/pc-carl.X68";
		instructions.sprites=sprites;

		/* ***********************************************************
		* Scene parameters
		*********************************************************** */
		SceneParameters scenes=new SceneParameters();
		scenes.includeFilePath="src/inc_Scenes_Generated.X68";
		scenes.scenePaths=new String[1];
		scenes.scenePaths[0]="/design/scene-json/tbooks.json";
		instructions.scenes=scenes;

		/* ***********************************************************
		* Text parameters
		*********************************************************** */
		TextParameters text=new TextParameters();
		text.tableFilePath="src/text/table_text_gen.X68";
		text.textFilePath="src/text/en-us/strings_gen.X68";
		text.filePaths=new String[1];
		text.filePaths[0]="design/text/characternames.json";
		instructions.text=text;
		
		/* ***********************************************************
		* MovementPattern parameters
		*********************************************************** */
		SimpleSourceDestinationParameters mpp=new SimpleSourceDestinationParameters();
		mpp.sourceFile="design/data/movementpatterns.json";
		mpp.destinationFile="src/data_SpriteMovementPatterns.X68";
		instructions.movementPatterns=mpp;

		/* ***********************************************************
		* ScriptedEvent parameters
		*********************************************************** */
		SimpleSourceDestinationParameters se=new SimpleSourceDestinationParameters();
		se.sourceFile="design/data/scriptedevents.json";
		se.destinationFile="src/data_ScriptedEvents.X68";
		instructions.scriptedEvents=se;

		/* ***********************************************************
		* Action table parameters
		*********************************************************** */
		ActionTableParameters actionTable=new ActionTableParameters();
		actionTable.dayCount=1;
		actionTable.sceneCount=4;
		actionTable.defaultLabels=new String[4];
		actionTable.defaultLabels[0]="ActionScriptDefaultInteract";
		actionTable.defaultLabels[1]="ActionScriptNullEvent";
		actionTable.defaultLabels[2]="DefaultExitScene";
		actionTable.defaultLabels[3]="DefaultEnterScene";
		actionTable.entries=new ActionTableEntry[1];
		actionTable.entries[0]=new ActionTableEntry();
		actionTable.entries[0].action="TestAction";
		actionTable.entries[0].day=0;
		actionTable.entries[0].scene="TestScene";
		actionTable.filePath="src/table_Actions.X68";
		instructions.actionTable=actionTable;
		
		/* ***********************************************************
		* Header parameters
		*********************************************************** */
		HeaderParameters header=new HeaderParameters();
		header.filePath="src/init_Header.X68";
		header.copyright="'(C)HUJO '";
		header.cartName="'Retail Clerk 90                                 '";
		header.romStart="$00000000";
		header.romEnd="RomEnd";
		header.ramStartEnd="$FFFF0000,$FFFFFFFF";
		header.sramType="'RA',$F8,$20";
		header.sramStart="SRAM_START";
		header.sramEnd="SRAM_END";
		header.comment="'http://huguesjohnson.com/               '";		
		instructions.header=header;

		/* ***********************************************************
		* Compile parameters
		*********************************************************** */
		AssemblyParameters assembly=new AssemblyParameters();
		instructions.assembly=new AssemblyParameters[3];
		assembly.assemblerPath="src/";
		assembly.arguments="vasmm68k_mot -o ../build/RetailClerk90.bin -Fbin -spaces -D_DEBUG_=0 -D_ATGAMES_HACKS_=0 RetailClerk90.X68";
		instructions.assembly[0]=assembly;
		assembly.arguments="vasmm68k_mot -o ../build/RetailClerk90_DEBUG.bin -Fbin -spaces -D_DEBUG_=1 -D_ATGAMES_HACKS_=0 RetailClerk90.X68";
		instructions.assembly[1]=assembly;
		assembly.arguments="vasmm68k_mot -o ../build/RetailClerk90_ATGAMES.bin -Fbin -spaces -D_DEBUG_=0 -D_ATGAMES_HACKS_=1 RetailClerk90.X68";
		instructions.assembly[2]=assembly;
		
		/* ***********************************************************
		* Package parameters
		*********************************************************** */
		PackageParameters[] packageParameters=new PackageParameters[3];
		packageParameters[0]=new PackageParameters();
		packageParameters[0].packagePath="/build/RetailClerk90.zip";
		packageParameters[0].includeFilePaths=new String[4];
		packageParameters[0].includeFilePaths[0]="/build/RetailClerk90.bin";
		packageParameters[0].includeFilePaths[1]="CREDITS";
		packageParameters[0].includeFilePaths[2]="LICENSE";
		packageParameters[0].includeFilePaths[3]="README.md";
		packageParameters[1]=new PackageParameters();
		packageParameters[1].packagePath="/build/RetailClerk90_DEBUG.zip";
		packageParameters[1].includeFilePaths=new String[4];
		packageParameters[1].includeFilePaths[0]="/build/RetailClerk90_DEBUG.bin";
		packageParameters[1].includeFilePaths[1]="CREDITS";
		packageParameters[1].includeFilePaths[2]="LICENSE";
		packageParameters[1].includeFilePaths[3]="README.md";
		packageParameters[2]=new PackageParameters();
		packageParameters[2].packagePath="/build/RetailClerk90_ATGAMES.zip";
		packageParameters[2].includeFilePaths=new String[4];
		packageParameters[2].includeFilePaths[0]="/build/RetailClerk90_ATGAMES.bin";
		packageParameters[2].includeFilePaths[1]="CREDITS";
		packageParameters[2].includeFilePaths[2]="LICENSE";
		packageParameters[2].includeFilePaths[3]="README.md";
		instructions.packageParameters=packageParameters;
	
		/* ***********************************************************
		* Print result
		*********************************************************** */
		String json=(new Gson()).toJson(instructions);
		System.out.println(json);
	}

}