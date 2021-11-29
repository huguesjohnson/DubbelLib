/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

public class MemoryMapParameters implements Serializable{
	private static final long serialVersionUID=22122L;

	public String sourceFile;
	public String destinationFile;
	public String baseAddress;
}