/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.Comparator;

public class SiteMapComparator implements Comparator<PageData>{

	@Override
	public int compare(PageData arg0,PageData arg1){
		String bc0=arg0.getBreadcrumbString();
		String bc1=arg1.getBreadcrumbString();
		if((bc0==null)&&(bc1==null)){return(0);}
		if(bc0==null){return(-1);}
		if(bc1==null){return(1);}
		String s0=bc0+arg0.getTrimTitle();
		String s1=bc1+arg1.getTrimTitle();
		return(s0.compareToIgnoreCase(s1));
	}
}