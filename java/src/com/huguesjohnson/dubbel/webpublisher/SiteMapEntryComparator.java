/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.Comparator;

public class SiteMapEntryComparator implements Comparator<SiteMapEntry>{

	@Override
	public int compare(SiteMapEntry arg0,SiteMapEntry arg1){
		if((arg0.breadcrumb==null)||(arg0.breadcrumb.length()<1)){
			return(-1);
		}
		if((arg1.breadcrumb==null)||(arg1.breadcrumb.length()<1)){
			return(1);
		}
		String s0=arg0.breadcrumb+arg0.title;
		String s1=arg1.breadcrumb+arg1.title;
		return(s0.compareToIgnoreCase(s1));
	}

}