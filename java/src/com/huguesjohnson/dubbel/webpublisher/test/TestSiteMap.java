/* https://github.com/huguesjohnson/DubbelLib/blob/main/LICENSE */

package com.huguesjohnson.dubbel.webpublisher.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.huguesjohnson.dubbel.webpublisher.SiteMapEntry;
import com.huguesjohnson.dubbel.webpublisher.SiteMapEntryComparator;

class TestSiteMap{

	@Test
	void SiteMapEntryComparator(){
		//setup a few dummy entries
		SiteMapEntry entryRootA=new SiteMapEntry();
		entryRootA.title="root-entry-a";
		SiteMapEntry entryRootB=new SiteMapEntry();
		entryRootB.title="root-entry-b";
		SiteMapEntry entryLevelAEntryA=new SiteMapEntry();
		entryLevelAEntryA.breadcrumb="level-a";
		entryLevelAEntryA.title="entry-a";
		SiteMapEntry entryLevelAEntryB=new SiteMapEntry();
		entryLevelAEntryB.breadcrumb="level-a";
		entryLevelAEntryB.title="entry-b";
		SiteMapEntry entryLevelBEntryA=new SiteMapEntry();
		entryLevelBEntryA.breadcrumb="level-b";
		entryLevelBEntryA.title="entry-a";
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