/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public class Scene{
	public String name;
	public String id;
	public String destinationFilePath;
	public String[] tilesetNames;
	public String[] paletteNames;
	public SceneScenery[] scenery;
	public SceneText[] text;
	public SceneObject[] objects;
	public String collisionDataName;
	public String[] exitIds;
	public SceneNpcLocation[] npcLocations;
	public String bgmName;
}