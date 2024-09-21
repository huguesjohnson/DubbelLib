/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.webpublisher.SiteMapEntry;
import com.huguesjohnson.dubbel.webpublisher.SiteMapEntryComparator;

class TestSiteMap{

	@Test
	void SiteMapEntryTitle(){
		SiteMapEntry e=new SiteMapEntry();
		e.addBreadcrumbLevel("Level 1");
		e.addBreadcrumbLevel("Level 2");
		e.addBreadcrumbLevel("Level 3");
		//breadcrumb does not appear anywhere in the title
		e.setTitle("Title");
		assertEquals("Title",e.getTitle());
		//1st breadcrumb is the beginning of the title
		e.setTitle("Level 1: Title");
		assertEquals("Title",e.getTitle());
		//2nd breadcrumb is the beginning of the title
		e.setTitle("Level 2: Title");
		assertEquals("Title",e.getTitle());
		//3rd breadcrumb is the beginning of the title
		e.setTitle("Level 3: Title");
		assertEquals("Title",e.getTitle());
		//1st and 2nd breadcrumb is the beginning of the title
		e.setTitle("Level 1: Level 2: Title");
		assertEquals("Title",e.getTitle());
		//1st and 3rd breadcrumb is the beginning of the title
		e.setTitle("Level 1: Level 3: Title");
		assertEquals("Title",e.getTitle());
		//the entire breadcrumb is in title
		e.setTitle("Level 1: Level 2 - Level 3: Title");
		assertEquals("Title",e.getTitle());
	}
	
	@Test
	void SiteMapEntryComparator(){
		//setup a few dummy entries
		SiteMapEntry entryRootA=new SiteMapEntry();
		entryRootA.setTitle("root-entry-a");
		SiteMapEntry entryRootB=new SiteMapEntry();
		entryRootB.setTitle("root-entry-b");
		SiteMapEntry entryLevelAEntryA=new SiteMapEntry();
		entryLevelAEntryA.addBreadcrumbLevel("level-a");
		entryLevelAEntryA.setTitle("entry-a");
		SiteMapEntry entryLevelAEntryB=new SiteMapEntry();
		entryLevelAEntryB.addBreadcrumbLevel("level-a");
		entryLevelAEntryB.setTitle("entry-b");
		SiteMapEntry entryLevelBEntryA=new SiteMapEntry();
		entryLevelBEntryA.addBreadcrumbLevel("level-b");
		entryLevelBEntryA.setTitle("entry-a");
		//test they are compared correctly
		SiteMapEntryComparator comparator=new SiteMapEntryComparator();
		assertEquals(-1,comparator.compare(entryRootA,entryRootB));
		assertEquals(-1,comparator.compare(entryRootA,entryLevelAEntryA));
		assertEquals(-1,comparator.compare(entryRootA,entryLevelAEntryB));
		assertEquals(-1,comparator.compare(entryRootA,entryLevelBEntryA));
		assertEquals(1,comparator.compare(entryLevelAEntryA,entryRootA));
		assertEquals(1,comparator.compare(entryLevelAEntryB,entryRootA));
		assertEquals(1,comparator.compare(entryLevelBEntryA,entryRootA));
		assertEquals(-1,comparator.compare(entryRootB,entryLevelAEntryA));
		assertEquals(-1,comparator.compare(entryRootB,entryLevelAEntryB));
		assertEquals(-1,comparator.compare(entryRootB,entryLevelBEntryA));
		assertEquals(1,comparator.compare(entryLevelAEntryA,entryRootB));
		assertEquals(1,comparator.compare(entryLevelAEntryB,entryRootB));
		assertEquals(1,comparator.compare(entryLevelBEntryA,entryRootB));
		assertEquals(-1,comparator.compare(entryLevelAEntryA,entryLevelAEntryB));
		assertEquals(-1,comparator.compare(entryLevelAEntryA,entryLevelBEntryA));
		assertEquals(1,comparator.compare(entryLevelAEntryB,entryLevelAEntryA));
		assertEquals(1,comparator.compare(entryLevelBEntryA,entryLevelAEntryA));
		assertEquals(-1,comparator.compare(entryLevelAEntryB,entryLevelBEntryA));
		assertEquals(1,comparator.compare(entryLevelAEntryB,entryLevelAEntryA));
	}
}