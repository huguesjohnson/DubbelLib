/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.webpublisher.PageData;
import com.huguesjohnson.dubbel.webpublisher.SiteMapComparator;

class TestSiteMap{

	@Test
	void SiteMapEntryTitle(){
		PageData e=new PageData();
		e.addBreadcrumbLevel("Level 1");
		e.addBreadcrumbLevel("Level 2");
		e.addBreadcrumbLevel("Level 3");
		//breadcrumb does not appear anywhere in the title
		e.setTitle("Title");
		assertEquals("Title",e.getTitle());
		//1st breadcrumb is the beginning of the title
		e.setTitle("Level 1: Title");
		assertEquals("Title",e.getTrimTitle());
		//2nd breadcrumb is the beginning of the title
		e.setTitle("Level 2: Title");
		assertEquals("Title",e.getTrimTitle());
		//3rd breadcrumb is the beginning of the title
		e.setTitle("Level 3: Title");
		assertEquals("Title",e.getTrimTitle());
		//1st and 2nd breadcrumb is the beginning of the title
		e.setTitle("Level 1: Level 2: Title");
		assertEquals("Title",e.getTrimTitle());
		//1st and 3rd breadcrumb is the beginning of the title
		e.setTitle("Level 1: Level 3: Title");
		assertEquals("Title",e.getTrimTitle());
		//the entire breadcrumb is in title
		e.setTitle("Level 1: Level 2 - Level 3: Title");
		assertEquals("Title",e.getTrimTitle());
	}
	
	@Test
	void SiteMapEntryComparator(){
		ArrayList<PageData> pd=new ArrayList<PageData>();
		//setup a few dummy entries
		PageData entryRootA=new PageData();
		entryRootA.setTitle("root-entry-a");
		pd.add(entryRootA);
		PageData entryLevelBEntryA=new PageData();
		entryLevelBEntryA.addBreadcrumbLevel("level-b");
		entryLevelBEntryA.setTitle("entry-a");
		pd.add(entryLevelBEntryA);
		PageData entryRootB=new PageData();
		entryRootB.setTitle("root-entry-b");
		pd.add(entryRootB);
		PageData entryLevelAEntryA=new PageData();
		entryLevelAEntryA.addBreadcrumbLevel("level-a");
		entryLevelAEntryA.setTitle("entry-a");
		pd.add(entryLevelAEntryA);
		PageData entryLevelAEntryB=new PageData();
		entryLevelAEntryB.addBreadcrumbLevel("level-a");
		entryLevelAEntryB.setTitle("entry-b");
		pd.add(entryLevelAEntryB);
		//sort
		pd.sort(new SiteMapComparator());
		//test they are sorted correctly
		assertEquals("entry-a",pd.get(0).getTitle());
		assertEquals("level-a/",pd.get(0).getBreadcrumbString());
		assertEquals(1,pd.get(0).getDepth());
		assertEquals("entry-b",pd.get(1).getTitle());
		assertEquals("level-a/",pd.get(1).getBreadcrumbString());
		assertEquals(1,pd.get(1).getDepth());
		assertEquals("entry-a",pd.get(2).getTitle());
		assertEquals("level-b/",pd.get(2).getBreadcrumbString());
		assertEquals(1,pd.get(2).getDepth());
		assertEquals("root-entry-a",pd.get(3).getTitle());
		assertEquals("",pd.get(3).getBreadcrumbString());
		assertEquals(0,pd.get(3).getDepth());
		assertEquals("root-entry-b",pd.get(4).getTitle());
		assertEquals("",pd.get(4).getBreadcrumbString());
		assertEquals(0,pd.get(4).getDepth());
	}
}