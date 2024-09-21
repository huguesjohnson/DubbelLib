/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.Comparator;

public class SiteMapEntryComparator implements Comparator<SiteMapEntry>{

	@Override
	public int compare(SiteMapEntry arg0,SiteMapEntry arg1){
		String bc0=arg0.getBreadcrumbString();
		String bc1=arg1.getBreadcrumbString();
		if((bc0==null)||(bc0.length()<1)){
			return(-1);
		}
		if((bc1==null)||(bc1.length()<1)){
			return(1);
		}
		String s0=bc0+arg0.getTitle();
		String s1=bc1+arg1.getTitle();
		return(s0.compareToIgnoreCase(s1));
	}
}