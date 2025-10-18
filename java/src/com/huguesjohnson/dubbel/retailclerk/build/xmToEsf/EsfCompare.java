/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.xmToEsf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.huguesjohnson.dubbel.retailclerk.build.objects.echo.ESFEvent;

/*
 * Used for debugging.
 * also see https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt 
 */
public class EsfCompare{
	private class SingleEvent{
		public String name;
		public byte byte1=-1;
		public byte byte2=-1;
		public int bytesRead=-1;
	}
	
	private SingleEvent getNextEvent(FileInputStream in) throws IOException{
		SingleEvent se=new SingleEvent();
		ESFEvent esfEvent=ESFEvent.fromByte((byte)in.read());
		if(esfEvent!=null){
			se.name=esfEvent.toString();
			byte value=esfEvent.getValue();
			if((value>=ESFEvent.NOTE_FM1.getValue())&&(value<=ESFEvent.NOTE_PCM.getValue())){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else if((value>=ESFEvent.SET_VOL_FM1.getValue())&&(value<=ESFEvent.SET_VOL_PSG4.getValue())){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else if((value>=ESFEvent.SET_FREQ_FM1.getValue())&&(value<=ESFEvent.SET_FREQ_PSG3.getValue())){
				se.byte1=(byte)in.read();
				se.byte2=(byte)in.read();
				se.bytesRead=3;
			}else if(value==ESFEvent.SET_FREQ_PSG4.getValue()){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else if((value>=ESFEvent.SET_INSTRUMENT_FM1.getValue())&&(value<=ESFEvent.SET_INSTRUMENT_PSG4.getValue())){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else if((value>=ESFEvent.SET_PARAMETERS_FM1.getValue())&&(value<=ESFEvent.SET_PARAMETERS_FM6.getValue())){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else if((value>=ESFEvent.SET_FM_REGISTER_0.getValue())&&(value<=ESFEvent.SET_FM_REGISTER_1.getValue())){
				se.byte1=(byte)in.read();
				se.byte2=(byte)in.read();
				se.bytesRead=3;
			}else if((value>=ESFEvent.SET_FLAGS.getValue())&&(value<=ESFEvent.CLEAR_FLAGS.getValue())){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else if(value==ESFEvent.DELAY_TICKS_LONG.getValue()){
				se.byte1=(byte)in.read();
				se.bytesRead=2;
			}else{
				se.bytesRead=1;
			}
		}			
		return(se);
	}
	
	public List<EsfCompareResult> compare(String esfPath1,String esfPath2) throws Exception{
		ArrayList<EsfCompareResult> diffs=new ArrayList<EsfCompareResult>();
		File f1=new File(esfPath1);
		File f2=new File(esfPath2);
        FileInputStream f1in=null;
        FileInputStream f2in=null;
        try{
        	f1in=new FileInputStream(f1);
        	f2in=new FileInputStream(f2);
        	long byte1Index=0;
        	long byte2Index=0;
        	while((byte1Index<f1.length())&&(byte2Index<f2.length())){
        		SingleEvent se1=getNextEvent(f1in);
        		byte1Index+=se1.bytesRead;
        		SingleEvent se2=getNextEvent(f2in);
        		byte2Index+=se2.bytesRead;
        		if((!se1.name.equals(se2.name))||(se1.byte1!=se2.byte1)||(se1.byte2!=se2.byte2)){
        			EsfCompareResult result=new EsfCompareResult();
        			result.event1byteIndex=byte1Index;
        			result.event1Name=se1.name;
        			result.value1byte1=se1.byte1;
        			result.value1byte2=se1.byte2;
        			result.event2byteIndex=byte2Index;
        			result.event2Name=se2.name;
        			result.value2byte1=se2.byte1;
        			result.value2byte2=se2.byte2;
    				diffs.add(result);
        		}
        	}
    		return(diffs);
      }catch(Exception x){
        	throw(x);
      }finally{
          try{if(f1in!=null){f1in.close();}}catch(Exception x){x.printStackTrace();}
          try{if(f2in!=null){f2in.close();}}catch(Exception x){x.printStackTrace();}
      }
  	}
	
}