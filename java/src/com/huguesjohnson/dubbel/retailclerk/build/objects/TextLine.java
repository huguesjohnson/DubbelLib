/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.objects;

public class TextLine{
	public String text=""; //the actual text
	public String comment=null; //optional comment
	public String dialogTitle=null; //if the dialog has a title this is where it goes
	public String terminator=null; //override of default terminator
	public String lineFeed=null; //override of default line feed
	public String formFeed=null; //override of default form feed
	public char nextPageChar='^'; //override of default character to indicate there is another page of text
}