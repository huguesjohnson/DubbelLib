/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.util;

import java.util.Comparator;

public class StringComparator implements Comparator<String>{
	@Override
	public int compare(String s0,String s1){
		return(s0.compareToIgnoreCase(s1));
	}
}