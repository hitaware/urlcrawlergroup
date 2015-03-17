package com.hitaware.urlcrawlercommons.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.hitaware.urlcrawlercommons.util.UrlCrawlerUUID;

public class TestUUID {
	/*
	 * This unit test tests uniqueness of generated UUIDs.
	 */
	@Test
	public void testUUID() {
		Set<String> set = new HashSet<String>();

		for (int i = 0; i < 100000; i++) {
			set.add(UrlCrawlerUUID.getUUID().toString());
		}

		Assert.assertEquals(100000, set.size());

	}

}
