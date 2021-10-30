/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.audio;

import java.lang.String;
import java.lang.StringBuffer;

public abstract class AudioPlayer{
    public final static int INFINITE_LOOP=-1;
    //number of times to loop.
    protected int loop; 
    //list of tracks to play.
    protected String[] trackList;
    //index of the track currently being played.
    protected int trackIndex;

    public void play(String path){
        this.loop=0;
        this.trackIndex=0;
        this.trackList=new String[1];
        this.trackList[0]=path;    	
    }

    public void play(String path,int loop){
        this.loop=loop;
        this.trackIndex=0;
        this.trackList=new String[1];
        this.trackList[0]=path;
    }

    public void play(String[] paths){
        this.loop=0;
        this.trackIndex=0;
        int length=paths.length;
        this.trackList=new String[length];
        System.arraycopy(paths,0,this.trackList,0,length);
    }

    public void play(String[] paths,int loop){
        this.loop=loop;
        this.trackIndex=0;
        int length=paths.length;
        this.trackList=new String[length];
        System.arraycopy(paths,0,this.trackList,0,length);
    }
    
    public abstract void stop();
   
	@Override
    public String toString(){
		StringBuffer tostring=new StringBuffer(this.getClass().getName());
		tostring.append("\n  loop="+this.loop);
		tostring.append("\n  trackIndex="+this.trackIndex);
		int length=this.trackList.length;
		for(int index=0;index<length;index++){
			tostring.append("\n  trackList["+index+"]="+this.trackList[index]);
		}
        return(tostring.toString());
	}
}