/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects.echo;

//see -  https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt
public enum ESFEvent{
	NOTE_FM1((byte)0x00),
	NOTE_FM2((byte)0x01),
	NOTE_FM3((byte)0x02),
	NOTE_FM4((byte)0x04),
	NOTE_FM5((byte)0x05),
	NOTE_FM6((byte)0x06),
	NOTE_PSG1((byte)0x08),
	NOTE_PSG2((byte)0x09),
	NOTE_PSG3((byte)0x0A),
	NOTE_PSG4((byte)0x0B),
	NOTE_PCM((byte)0x0C),
	NOTE_OFF_FM1((byte)0x10),
	NOTE_OFF_FM2((byte)0x11),
	NOTE_OFF_FM3((byte)0x12),
	NOTE_OFF_FM4((byte)0x14),
	NOTE_OFF_FM5((byte)0x15),
	NOTE_OFF_FM6((byte)0x16),
	NOTE_OFF_PSG1((byte)0x18),
	NOTE_OFF_PSG2((byte)0x19),
	NOTE_OFF_PSG3((byte)0x1A),
	NOTE_OFF_PSG4((byte)0x1B),
	NOTE_OFF_PCM((byte)0x1C),
	SET_VOL_FM1((byte)0x20),
	SET_VOL_FM2((byte)0x21),
	SET_VOL_FM3((byte)0x22),
	SET_VOL_FM4((byte)0x24),
	SET_VOL_FM5((byte)0x25),
	SET_VOL_FM6((byte)0x26),
	SET_VOL_PSG1((byte)0x28),
	SET_VOL_PSG2((byte)0x29),
	SET_VOL_PSG3((byte)0x2A),
	SET_VOL_PSG4((byte)0x2B),
	SET_FREQ_FM1((byte)0x30),
	SET_FREQ_FM2((byte)0x31),
	SET_FREQ_FM3((byte)0x32),
	SET_FREQ_FM4((byte)0x34),
	SET_FREQ_FM5((byte)0x35),
	SET_FREQ_FM6((byte)0x36),
	SET_FREQ_PSG1((byte)0x38),
	SET_FREQ_PSG2((byte)0x39),
	SET_FREQ_PSG3((byte)0x3A),
	SET_FREQ_PSG4((byte)0x3B),
	SET_INSTRUMENT_FM1((byte)0x40),
	SET_INSTRUMENT_FM2((byte)0x41),
	SET_INSTRUMENT_FM3((byte)0x42),
	SET_INSTRUMENT_FM4((byte)0x44),
	SET_INSTRUMENT_FM5((byte)0x45),
	SET_INSTRUMENT_FM6((byte)0x46),
	SET_INSTRUMENT_PSG1((byte)0x48),
	SET_INSTRUMENT_PSG2((byte)0x49),
	SET_INSTRUMENT_PSG3((byte)0x4A),
	SET_INSTRUMENT_PSG4((byte)0x4B),
	DELAY_TICKS_SHORT_0((byte)0xD0),
	DELAY_TICKS_SHORT_1((byte)0xD1),
	DELAY_TICKS_SHORT_2((byte)0xD2),
	DELAY_TICKS_SHORT_3((byte)0xD3),
	DELAY_TICKS_SHORT_4((byte)0xD4),
	DELAY_TICKS_SHORT_5((byte)0xD5),
	DELAY_TICKS_SHORT_6((byte)0xD6),
	DELAY_TICKS_SHORT_7((byte)0xD7),
	DELAY_TICKS_SHORT_8((byte)0xD8),
	DELAY_TICKS_SHORT_9((byte)0xD9),
	DELAY_TICKS_SHORT_A((byte)0xDA),
	DELAY_TICKS_SHORT_B((byte)0xDB),
	DELAY_TICKS_SHORT_C((byte)0xDC),
	DELAY_TICKS_SHORT_D((byte)0xDD),
	DELAY_TICKS_SHORT_E((byte)0xDE),
	DELAY_TICKS_SHORT_F((byte)0xDF),
	LOCK_FM1((byte)0xE0),
	LOCK_FM2((byte)0xE1),
	LOCK_FM3((byte)0xE2),
	LOCK_FM4((byte)0xE4),
	LOCK_FM5((byte)0xE5),
	LOCK_FM6((byte)0xE6),
	LOCK_PSG1((byte)0xE8),
	LOCK_PSG2((byte)0xE9),
	LOCK_PSG3((byte)0xEA),
	LOCK_PSG4((byte)0xEB),
	SET_PARAMETERS_FM1((byte)0xF0),
	SET_PARAMETERS_FM2((byte)0xF1),
	SET_PARAMETERS_FM3((byte)0xF2),
	SET_PARAMETERS_FM4((byte)0xF4),
	SET_PARAMETERS_FM5((byte)0xF5),
	SET_PARAMETERS_FM6((byte)0xF6),
	SET_FM_REGISTER_0((byte)0xF8),
	SET_FM_REGISTER_1((byte)0xF9),
	SET_FLAGS((byte)0xFA),
	CLEAR_FLAGS((byte)0xFB),
	GOTO_LOOP((byte)0xFC),
	SET_LOOP((byte)0xFD),
	DELAY_TICKS_LONG((byte)0xFE),
	STOP((byte)0xFF);
	
	private final byte value;
	ESFEvent(byte value){this.value=value;}
	public byte getValue(){return(this.value);}

    public static ESFEvent fromByte(byte b){
        for(ESFEvent e:ESFEvent.values()){
            if(e.getValue()==b){
                return(e);
            }
        }
        return(null);
    }
	
    @Override
	public String toString(){
    	/* 
    	 * Good luck making this work as a switch statement instead.
    	 * Something like: case NOTE_FM1.getValue() simply won't work.
    	 * There's probably some convoluted code that someone will argue is "cleaner".
    	 * I am going with a simple solution instead.
    	 * 
    	 * The strings here are directly from the ESF documentation.
    	 */
    	if(this.value==NOTE_FM1.getValue()){return("Note on FM channel #1");}
    	if(this.value==NOTE_FM2.getValue()){return("Note on FM channel #2");}
    	if(this.value==NOTE_FM3.getValue()){return("Note on FM channel #3");}
    	if(this.value==NOTE_FM4.getValue()){return("Note on FM channel #4");}
    	if(this.value==NOTE_FM5.getValue()){return("Note on FM channel #5");}
    	if(this.value==NOTE_FM6.getValue()){return("Note on FM channel #6");}
    	if(this.value==NOTE_PSG1.getValue()){return("Note on PSG channel #1");}
    	if(this.value==NOTE_PSG2.getValue()){return("Note on PSG channel #2");}
    	if(this.value==NOTE_PSG3.getValue()){return("Note on PSG channel #3");}
    	if(this.value==NOTE_PSG4.getValue()){return("Note on PSG channel #4");}
    	if(this.value==NOTE_PCM.getValue()){return("Note on PCM channel");}
    	if(this.value==NOTE_OFF_FM1.getValue()){return("Note off FM channel #1");}
    	if(this.value==NOTE_OFF_FM2.getValue()){return("Note off FM channel #2");}
    	if(this.value==NOTE_OFF_FM3.getValue()){return("Note off FM channel #3");}
    	if(this.value==NOTE_OFF_FM4.getValue()){return("Note off FM channel #4");}
    	if(this.value==NOTE_OFF_FM5.getValue()){return("Note off FM channel #5");}
    	if(this.value==NOTE_OFF_FM6.getValue()){return("Note off FM channel #6");}
    	if(this.value==NOTE_OFF_PSG1.getValue()){return("Note off PSG channel #1");}
    	if(this.value==NOTE_OFF_PSG2.getValue()){return("Note off PSG channel #2");}
    	if(this.value==NOTE_OFF_PSG3.getValue()){return("Note off PSG channel #3");}
    	if(this.value==NOTE_OFF_PSG4.getValue()){return("Note off PSG channel #4");}
    	if(this.value==NOTE_OFF_PCM.getValue()){return("Note off PCM channel");}
    	if(this.value==SET_VOL_FM1.getValue()){return("Set volume FM channel #1");}
    	if(this.value==SET_VOL_FM2.getValue()){return("Set volume FM channel #2");}
    	if(this.value==SET_VOL_FM3.getValue()){return("Set volume FM channel #3");}
    	if(this.value==SET_VOL_FM4.getValue()){return("Set volume FM channel #4");}
    	if(this.value==SET_VOL_FM5.getValue()){return("Set volume FM channel #5");}
    	if(this.value==SET_VOL_FM6.getValue()){return("Set volume FM channel #6");}
    	if(this.value==SET_VOL_PSG1.getValue()){return("Set volume PSG channel #1");}
    	if(this.value==SET_VOL_PSG2.getValue()){return("Set volume PSG channel #2");}
    	if(this.value==SET_VOL_PSG3.getValue()){return("Set volume PSG channel #3");}
    	if(this.value==SET_VOL_PSG4.getValue()){return("Set volume PSG channel #4");}
    	if(this.value==SET_FREQ_FM1.getValue()){return("Set frequency FM channel #1");}
    	if(this.value==SET_FREQ_FM2.getValue()){return("Set frequency FM channel #2");}
    	if(this.value==SET_FREQ_FM3.getValue()){return("Set frequency FM channel #3");}
    	if(this.value==SET_FREQ_FM4.getValue()){return("Set frequency FM channel #4");}
    	if(this.value==SET_FREQ_FM5.getValue()){return("Set frequency FM channel #5");}
    	if(this.value==SET_FREQ_FM6.getValue()){return("Set frequency FM channel #6");}
    	if(this.value==SET_FREQ_PSG1.getValue()){return("Set frequency PSG channel #1");}
    	if(this.value==SET_FREQ_PSG2.getValue()){return("Set frequency PSG channel #2");}
    	if(this.value==SET_FREQ_PSG3.getValue()){return("Set frequency PSG channel #3");}
    	if(this.value==SET_FREQ_PSG4.getValue()){return("Set noise type PSG channel #4");}
    	if(this.value==SET_INSTRUMENT_FM1.getValue()){return("Set instrument FM channel #1");}
    	if(this.value==SET_INSTRUMENT_FM2.getValue()){return("Set instrument FM channel #2");}
    	if(this.value==SET_INSTRUMENT_FM3.getValue()){return("Set instrument FM channel #3");}
    	if(this.value==SET_INSTRUMENT_FM4.getValue()){return("Set instrument FM channel #4");}
    	if(this.value==SET_INSTRUMENT_FM5.getValue()){return("Set instrument FM channel #5");}
    	if(this.value==SET_INSTRUMENT_FM6.getValue()){return("Set instrument FM channel #6");}
    	if(this.value==SET_INSTRUMENT_PSG1.getValue()){return("Set instrument PSG channel #1");}
    	if(this.value==SET_INSTRUMENT_PSG2.getValue()){return("Set instrument PSG channel #2");}
    	if(this.value==SET_INSTRUMENT_PSG3.getValue()){return("Set instrument PSG channel #3");}
    	if(this.value==SET_INSTRUMENT_PSG4.getValue()){return("Set instrument PSG channel #4");}
    	if(this.value==DELAY_TICKS_SHORT_0.getValue()){return("Delay ticks (short) [0]");}
    	if(this.value==DELAY_TICKS_SHORT_1.getValue()){return("Delay ticks (short) [1]");}
    	if(this.value==DELAY_TICKS_SHORT_2.getValue()){return("Delay ticks (short) [2]");}
    	if(this.value==DELAY_TICKS_SHORT_3.getValue()){return("Delay ticks (short) [3]");}
    	if(this.value==DELAY_TICKS_SHORT_4.getValue()){return("Delay ticks (short) [4]");}
    	if(this.value==DELAY_TICKS_SHORT_5.getValue()){return("Delay ticks (short) [5]");}
    	if(this.value==DELAY_TICKS_SHORT_6.getValue()){return("Delay ticks (short) [6]");}
    	if(this.value==DELAY_TICKS_SHORT_7.getValue()){return("Delay ticks (short) [7]");}
    	if(this.value==DELAY_TICKS_SHORT_8.getValue()){return("Delay ticks (short) [8]");}
    	if(this.value==DELAY_TICKS_SHORT_9.getValue()){return("Delay ticks (short) [9]");}
    	if(this.value==DELAY_TICKS_SHORT_A.getValue()){return("Delay ticks (short) [A]");}
    	if(this.value==DELAY_TICKS_SHORT_B.getValue()){return("Delay ticks (short) [B]");}
    	if(this.value==DELAY_TICKS_SHORT_C.getValue()){return("Delay ticks (short) [C]");}
    	if(this.value==DELAY_TICKS_SHORT_D.getValue()){return("Delay ticks (short) [D]");}
    	if(this.value==DELAY_TICKS_SHORT_E.getValue()){return("Delay ticks (short) [E]");}
    	if(this.value==DELAY_TICKS_SHORT_F.getValue()){return("Delay ticks (short) [F]");}
    	if(this.value==LOCK_FM1.getValue()){return("[SFX] Lock FM channel #1");}
    	if(this.value==LOCK_FM2.getValue()){return("[SFX] Lock FM channel #2");}
    	if(this.value==LOCK_FM3.getValue()){return("[SFX] Lock FM channel #3");}
    	if(this.value==LOCK_FM4.getValue()){return("[SFX] Lock FM channel #4");}
    	if(this.value==LOCK_FM5.getValue()){return("[SFX] Lock FM channel #5");}
    	if(this.value==LOCK_FM6.getValue()){return("[SFX] Lock FM channel #6");}
    	if(this.value==LOCK_PSG1.getValue()){return("[SFX] Lock PSG channel #1");}
    	if(this.value==LOCK_PSG2.getValue()){return("[SFX] Lock PSG channel #2");}
    	if(this.value==LOCK_PSG3.getValue()){return("[SFX] Lock PSG channel #3");}
    	if(this.value==LOCK_PSG4.getValue()){return("[SFX] Lock PSG channel #4");}
    	if(this.value==SET_PARAMETERS_FM1.getValue()){return("Set parameters FM channel #1");}
    	if(this.value==SET_PARAMETERS_FM2.getValue()){return("Set parameters FM channel #2");}
    	if(this.value==SET_PARAMETERS_FM3.getValue()){return("Set parameters FM channel #3");}
    	if(this.value==SET_PARAMETERS_FM4.getValue()){return("Set parameters FM channel #4");}
    	if(this.value==SET_PARAMETERS_FM5.getValue()){return("Set parameters FM channel #5");}
    	if(this.value==SET_PARAMETERS_FM6.getValue()){return("Set parameters FM channel #6");}
    	if(this.value==SET_FM_REGISTER_0.getValue()){return("Set FM register in bank 0");}
    	if(this.value==SET_FM_REGISTER_1.getValue()){return("Set FM register in bank 1");}
    	if(this.value==SET_FLAGS.getValue()){return("Set flags");}
    	if(this.value==CLEAR_FLAGS.getValue()){return("Clear flags");}
    	if(this.value==GOTO_LOOP.getValue()){return("[BGM] Go to loop");}
    	if(this.value==SET_LOOP.getValue()){return("[BGM] Set loop point");}
    	if(this.value==DELAY_TICKS_LONG.getValue()){return("Delay ticks (long)");}
    	if(this.value==STOP.getValue()){return("Stop playback");}
    	return("Invalid event: "+this.value);
	}
}