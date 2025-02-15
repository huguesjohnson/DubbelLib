/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher;

import java.util.ArrayList;
import java.util.TreeMap;

//effectively a struct
public class WebPublisherResults{
	//map links to the pages they appear one
	public TreeMap<String,ArrayList<String>> linkMap=new TreeMap<String,ArrayList<String>>();
}