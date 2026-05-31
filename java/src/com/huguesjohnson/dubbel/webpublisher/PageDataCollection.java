/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PageDataCollection extends ArrayList<PageData>{
	private static final long serialVersionUID=666138964L;

	public ArrayList<PageData> getSitemapPages(){
		ArrayList<PageData> siteMapPages=new ArrayList<PageData>();
		for(PageData pd:this){
			if(pd.getSitemapInclude()&&pd.hasTitle()&&pd.getDepth()>0){
				siteMapPages.add(pd);
			}
		}
		Collections.sort(siteMapPages,new SiteMapComparator());
		return(siteMapPages);
	}
	
	public Map<String,ArrayList<PageData>> getTopicMap(){
		Map<String,ArrayList<PageData>> topicMap=new HashMap<String,ArrayList<PageData>>();
		for(PageData pd:this){
			for(String topic:pd.getTopics()){
				if(topicMap.containsKey(topic)){
					topicMap.get(topic).add(pd);
				}else{
					ArrayList<PageData> a=new ArrayList<PageData>();
					a.add(pd);
					topicMap.put(topic,a);
				}
			}
		}
		return(topicMap);
	}
	
}