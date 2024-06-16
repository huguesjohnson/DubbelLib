/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

import java.util.Map;

public class StringCollection{
	public String name; //name of the text set
	public String description=null; //longer description of the text set
	public String skipTable=null; //'true' = do not create a lookup table
	public int lineLength=22; //length before line feeds occur
	public int formLines=0; //how many lines before a form feed 
	public String defaultTerminator="ETX"; //how to end the final line of a text block
	public String defaultLineFeed="LF"; //default line feed character
	public String defaultFormFeed="FF"; //default form feed character
	public char defaultNextPageChar='^'; //default character to indicate there is another page of text
	public char defaultLineBreakChar='|'; //default character to indicate there is a line break
	public Map<String,TextLine> lines; //the actual lines of text
}