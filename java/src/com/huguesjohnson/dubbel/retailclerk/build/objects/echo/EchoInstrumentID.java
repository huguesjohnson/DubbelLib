/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects.echo;

//see https://github.com/sikthehedgehog/Echo/blob/master/tester/sound/list.68k
public enum EchoInstrumentID{
	FM_DISTORTION_GUITAR(1),
	FM_MIDI_PIANO(9),
	FM_MIDI_SQUARE(10),
	FM_MIDI_SAWTOOTH(11),
	FM_MIDI_FLUTE(12),
	FM_STRING_ENSEMBLE(4),
	FM_STANDARD_BASS(5),
	FM_MIDI_SYNTH_BASS(14),
	FM_MIDI_SQUARE_FILTERED(15),
	FM_MIDI_SAWTOOTH_FILTERED(16),
	FM_ELECTRIC_GUITAR(20),
	PSG_FLAT(0),
	PSG_SOFT_EVELOPE(6),
	PSG_PIANO(7),
	PSG_MIDI_SQUARE(8),
	PSG_NEPEL_FOUR(13),
	PSG_SEASHORE(17),
	PSG_HIT_HAT(18),
	PSG_STRING(19),
	PCM_SNARE_DRUM(2),
	PCM_BASS_DRUM(3);
	
	private final int value;
	EchoInstrumentID(int value){this.value=value;}
	public int getValue(){return(this.value);}
	
    @Override
	public String toString(){
    	//see comments in ESFEvent...
    	if(this.value==FM_DISTORTION_GUITAR.getValue()){return("[FM] Distortion guitar");}
    	if(this.value==FM_MIDI_PIANO.getValue()){return("[FM] MIDI piano");}
    	if(this.value==FM_MIDI_SQUARE.getValue()){return("[FM] MIDI square lead");}
    	if(this.value==FM_MIDI_SAWTOOTH.getValue()){return("[FM] MIDI sawtooth lead");}
    	if(this.value==FM_MIDI_FLUTE.getValue()){return("[FM] MIDI flute");}
    	if(this.value==FM_STRING_ENSEMBLE.getValue()){return("[FM] String ensemble");}
    	if(this.value==FM_STANDARD_BASS.getValue()){return("[FM] Standard bass");}
    	if(this.value==FM_MIDI_SYNTH_BASS.getValue()){return("[FM] MIDI synth bass");}
    	if(this.value==FM_MIDI_SQUARE_FILTERED.getValue()){return("[FM] MIDI square (filtered)");}
    	if(this.value==FM_MIDI_SAWTOOTH_FILTERED.getValue()){return("[FM] MIDI sawtooth (filtered)");}
    	if(this.value==FM_ELECTRIC_GUITAR.getValue()){return("[FM] Electric guitar");}
    	if(this.value==PSG_FLAT.getValue()){return("[PSG] Flat PSG instrument");}
    	if(this.value==PSG_SOFT_EVELOPE.getValue()){return("[PSG] Soft PSG envelope");}
    	if(this.value==PSG_PIANO.getValue()){return("[PSG] Piano PSG instrument");}
    	if(this.value==PSG_MIDI_SQUARE.getValue()){return("[PSG] MIDI square lead");}
    	if(this.value==PSG_NEPEL_FOUR.getValue()){return("[PSG] Nepel Four PSG instr.");}
    	if(this.value==PSG_SEASHORE.getValue()){return("[PSG] Seashore");}
    	if(this.value==PSG_HIT_HAT.getValue()){return("[PSG] Hit-hat");}
    	if(this.value==PSG_STRING.getValue()){return("[PSG] PSG string");}
    	if(this.value==PCM_SNARE_DRUM.getValue()){return("[PCM] Snare drum");}
    	if(this.value==PCM_BASS_DRUM.getValue()){return("[PCM] Bass drum (kick)");}
    	return("Invalid instrument ID: "+this.value);
    }	
}