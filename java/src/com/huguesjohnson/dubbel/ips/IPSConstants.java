/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.ips;

public abstract class IPSConstants{
	public final static byte[] HEADER={'P','A','T','C','H'};
	public final static byte[] EOF={'E','O','F'};
	public final static String ERROR_INVALID_HEADER="Missing required header value";
	public final static String ERROR_INVALID_EOF="Missing required end of file terminator";
}