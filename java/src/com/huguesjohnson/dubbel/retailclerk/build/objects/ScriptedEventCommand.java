/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public enum ScriptedEventCommand{
	//TODO - need a better way to keep these in sync between this class and the asm code
	END(0xF000),
	DIALOG(0xF001),
	CHANGE_SPRITE(0xF002),
	CHANGE_SCENE(0xF003),
	CHANGE_PLAYER_SPRITE(0xF004),
	SET_PLAYER_SPRITE_X(0xF005),
	SET_PLAYER_SPRITE_Y(0xF006),
	SET_PLAYER_SPRITE_DIRECTION(0xF007),
	FADE_BLACK(0xF008),
	DRAW_SCENE(0xF009),
	TIMER(0xF00A),
	ENDGAME(0xF00B),
	LOADFONT(0xF00C),
	ADDITEM(0xF00D),
	REMOVEITEM(0xF00E),
	UPDATE_SCENE_NPCS(0xF00F),
	LOADPALETTE(0xF010);
	
	private final int value;
	ScriptedEventCommand(final int value){this.value=value;}
    public int getValue(){return(value);}

    @Override
	public String toString(){
    	//yes, I am aware of switch statements
    	//really this should be a map I think
    	//the map could even be defined in the build file
    	if(this.value==DIALOG.value){return("SCRIPTED_EVENT_DIALOG");}
    	if(this.value==CHANGE_SPRITE.value){return("SCRIPTED_EVENT_CHANGE_SPRITE");}
    	if(this.value==CHANGE_SCENE.value){return("SCRIPTED_EVENT_CHANGE_SCENE");}
    	if(this.value==CHANGE_PLAYER_SPRITE.value){return("SCRIPTED_EVENT_CHANGE_PLAYER_SPRITE");}
    	if(this.value==SET_PLAYER_SPRITE_X.value){return("SCRIPTED_EVENT_SET_PLAYER_SPRITE_X");}
    	if(this.value==SET_PLAYER_SPRITE_Y.value){return("SCRIPTED_EVENT_SET_PLAYER_SPRITE_Y");}
    	if(this.value==SET_PLAYER_SPRITE_DIRECTION.value){return("SCRIPTED_EVENT_SET_PLAYER_SPRITE_DIRECTION");}
    	if(this.value==FADE_BLACK.value){return("SCRIPTED_EVENT_FADE_BLACK");}
    	if(this.value==DRAW_SCENE.value){return("SCRIPTED_EVENT_DRAW_SCENE");}
    	if(this.value==TIMER.value){return("SCRIPTED_EVENT_TIMER");}
    	if(this.value==ENDGAME.value){return("SCRIPTED_EVENT_ENDGAME");}
    	if(this.value==LOADFONT.value){return("SCRIPTED_EVENT_LOADFONT");}
    	if(this.value==ADDITEM.value){return("SCRIPTED_EVENT_ADDITEM");}
    	if(this.value==REMOVEITEM.value){return("SCRIPTED_EVENT_REMOVEITEM");}
    	if(this.value==UPDATE_SCENE_NPCS.value){return("SCRIPTED_EVENT_UPDATE_SCENE_NPCS");}
    	if(this.value==LOADPALETTE.value){return("SCRIPTED_EVENT_LOADPALETTE");}
		return("SCRIPTED_EVENT_END");//safest default return value, although maybe throwing an error would prevent a debugging headache later
	}

}