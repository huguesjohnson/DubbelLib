/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.opml;

public class OPMLParseException extends Exception{
	private static final long serialVersionUID=666136489L;

	public OPMLParseException(String message,Throwable cause){
		super(message,cause);
	}

	public OPMLParseException(String message){
		super(message);
	}

	public OPMLParseException(Throwable cause){
		super(cause);
	}	
}