/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.retailclerk.build.parameters;

import java.io.Serializable;

public class TextParameters implements Serializable{
	private static final long serialVersionUID=25622083L;

	public String tableFilePath;//where to write the lookup table
	public String textFilePath;//where to write the strings
	public String[] filePaths;//the files with the source strings
}