/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.audio.xm;

public enum EffectType{
	ARPEGGIO(0x00),
	PORTAMENTO_UP(0x01),
	PORTAMENTO_DOWN(0x02),
	TONE_PORTAMENTO(0x03),
	VIBRATO(0x04),
	TONE_PORTAMENTO_VOLUME_SLIDE(0x05),
	VIBRATO_VOLUME_SLIDE(0x06),
	TREMOLO(0x07),
	SET_PANNING(0x08),
	SAMPLE_OFFSET(0x09),
	VOLUME_SLIDE(0x0A),
	POSITION_JUMP(0x0B),
	SET_VOLUME(0x0C),
	PATTERN_BREAK(0x0D),
	FINE_PORTAMENTO(0x0E),
	SET_TEMPO(0x0F),
	GLOBAL_VOLUME(0x10),
	GLOBAL_VOLUME_SLIDE(0x11),
	KEY_OFF(0x14),
	SET_ENVELOPE_POSITION(0x15),
	PANNING_SLIDE(0x18),
	RETRIGGER_NOTE(0x1A),
	TREMOR(0x1C),
	EXTRA_FINE_PORTAMENTO_DOWN(0x1F);
	
	private final int value;
	EffectType(int value){this.value=value;}
	public int getValue(){return(this.value);}
	
    @Override
	public String toString(){
    	if(this.value==ARPEGGIO.getValue()){return("Arpeggio");}
    	if(this.value==PORTAMENTO_UP.getValue()){return("Portamento up");}
    	if(this.value==PORTAMENTO_DOWN.getValue()){return("Portamento down");}
    	if(this.value==TONE_PORTAMENTO.getValue()){return("Tone portamento");}
    	if(this.value==VIBRATO.getValue()){return("Vibrato");}
    	if(this.value==TONE_PORTAMENTO_VOLUME_SLIDE.getValue()){return("Tone portamento + volume slide");}
    	if(this.value==VIBRATO_VOLUME_SLIDE.getValue()){return("Vibrato + volume slide");}
    	if(this.value==TREMOLO.getValue()){return("Tremolo");}
    	if(this.value==SET_PANNING.getValue()){return("Set panning");}
    	if(this.value==SAMPLE_OFFSET.getValue()){return("Sample offset");}
    	if(this.value==VOLUME_SLIDE.getValue()){return("Volume slide");}
    	if(this.value==POSITION_JUMP.getValue()){return("Position jump");}
    	if(this.value==PATTERN_BREAK.getValue()){return("Pattern break");}
    	if(this.value==FINE_PORTAMENTO.getValue()){return("Fine portamento up");}
    	if(this.value==SET_TEMPO.getValue()){return("Set tempo");}
    	if(this.value==GLOBAL_VOLUME.getValue()){return("Set global volume");}
    	if(this.value==GLOBAL_VOLUME_SLIDE.getValue()){return("Global volume slide");}
    	if(this.value==KEY_OFF.getValue()){return("Key off");}
    	if(this.value==SET_ENVELOPE_POSITION.getValue()){return("Set envelope position");}
    	if(this.value==PANNING_SLIDE.getValue()){return("Panning slide");}
    	if(this.value==RETRIGGER_NOTE.getValue()){return("Multi retrigger note");}
    	if(this.value==TREMOR.getValue()){return("Tremor");}
    	if(this.value==EXTRA_FINE_PORTAMENTO_DOWN.getValue()){return("Extra fine portamento up");}
    	
    	return("Invalid EffectType: "+this.value);
    }	

}