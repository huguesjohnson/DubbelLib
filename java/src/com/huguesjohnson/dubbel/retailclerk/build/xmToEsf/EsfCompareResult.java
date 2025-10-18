/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.xmToEsf;

/*
 * Used for debugging.
 * also see https://github.com/sikthehedgehog/Echo/blob/master/doc/esf.txt 
 */
public class EsfCompareResult{
	public long event1byteIndex;
	public String event1Name;
	public byte value1byte1=-1;
	public byte value1byte2=-1;
	public long event2byteIndex;
	public String event2Name;
	public byte value2byte1=-1;
	public byte value2byte2=-1;
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		sb.append(event1byteIndex);
		sb.append("]");
		sb.append("[");
		sb.append(event1Name);
		sb.append("]");
		sb.append("[");
		sb.append(value1byte1);
		sb.append("]");
		sb.append("[");
		sb.append(value1byte2);
		sb.append("]");
		sb.append(" -- ");
		sb.append("[");
		sb.append(event2byteIndex);
		sb.append("]");
		sb.append("[");
		sb.append(event2Name);
		sb.append("]");
		sb.append("[");
		sb.append(value2byte1);
		sb.append("]");
		sb.append("[");
		sb.append(value2byte2);
		sb.append("]");
		return(sb.toString());
	}
}