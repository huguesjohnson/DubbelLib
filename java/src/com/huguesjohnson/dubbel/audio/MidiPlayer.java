/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.audio;

import java.io.IOException;
import java.io.File;
import java.lang.String;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

public class MidiPlayer extends AudioPlayer implements MetaEventListener{
    //actually plays the midi
    private Sequencer sequencer;
   
	@Override
    public void play(String path){
		super.play(path);
        this.startSequencer();
    }

	@Override
    public void play(String path,int loop){
		super.play(path,loop);
        this.startSequencer();
    }

	@Override
    public void play(String[] paths){
		super.play(paths);
        this.startSequencer();
    }

	@Override
    public void play(String[] paths,int loop){
		super.play(paths,loop);
        this.startSequencer();
    }

	@Override
    public void stop(){
        if(this.sequencer!=null){
            if(this.sequencer.isRunning()){
                this.sequencer.stop();
            }
        }
    }
    
    /* Trap meta events. Why isn't there a simple way to loop midi? Let me get this straight.. the only way to loop a midi is to:
     * <ul>
     * <li>1) register a MetaEventListener</li>
     * <li>2) override the meta() method.. whatever that does</li>
     * <li>3) look for type 47, of course this is not enumerated anywhere</li>
     * <li>4) manually restart the sequencer</li>
     * Someone please explain to me why this is better than sequencer.setLoop(true)?
     */
    public void meta(MetaMessage metaMessage){
        if(metaMessage.getType()==47){
            //move to the next track
            this.trackIndex++;
            try{
                //are we at the end of the track list?
                if(this.trackIndex<this.trackList.length){
                    this.sequencer.setSequence(MidiSystem.getSequence(new File(this.trackList[this.trackIndex])));
                    this.sequencer.start();
                }else{ 
                    //are we supposed to loop? (if loop<=0 then do not loop)
                    if(this.loop>0){
                        //go back to the start of the track list
                        this.trackIndex=0;
                        //if loop>0 then decrement number of times we're supposed to loop
                        if(this.loop>0){this.loop--;}
                        //restart the sequencer
                        this.sequencer.setSequence(MidiSystem.getSequence(new File(this.trackList[0])));
                        this.sequencer.start();                        
                    }
                }
            }catch(InvalidMidiDataException imdx){
                imdx.printStackTrace();                
            }catch(IOException iox){
                iox.printStackTrace();
            }
        }
    }

    private void startSequencer(){
        try{
            /* declared final so anonymous MetaEventListener can be added */
            this.sequencer=MidiSystem.getSequencer();
            if(this.sequencer!=null){
                this.sequencer.addMetaEventListener(this);
                this.sequencer.open();
                this.sequencer.setSequence(MidiSystem.getSequence(new File(this.trackList[0])));
                this.sequencer.start();
            }else{
                throw new Exception("Can't obtain a midi sequencer");
            }
        }catch(InvalidMidiDataException imdx){
            imdx.printStackTrace();
        }catch(MidiUnavailableException mux){
            mux.printStackTrace();
        }catch(IOException iox){
            iox.printStackTrace();
        }catch(Exception x){
            x.printStackTrace();
        }
    }
}