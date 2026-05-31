/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.Comparator;

public class PageDataComparator implements Comparator<PageData>{

	@Override
	public int compare(PageData arg0,PageData arg1){
		String url0=arg0.getUrl();
		String url1=arg1.getUrl();
		if((url0==null)&&(url1==null)){return(0);}
		if(url0==null){return(-1);}
		if(url1==null){return(1);}
		return(url0.compareToIgnoreCase(url1));
	}
}