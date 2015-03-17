package com.hitaware.urlcrawlercommons.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

import com.hitaware.urlcrawlercommons.util.URLUtil;

public class TestUrlCrawlerCommons {
	
	
	public void testExtract2LD() throws IOException {
		
		// Wrong top level domains
		Assert.assertNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://www.ddd.abc")));
		Assert.assertNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://www.ddd.cde")));
		Assert.assertNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://www.ddd.efe")));
		Assert.assertNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://www.ddd.grg")));
		// Correct top level domains
		Assert.assertNotNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://www.ddd.co")));
		Assert.assertNotNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://www.ddd.com")));
		Assert.assertNotNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"http://ddd.org")));
		Assert.assertNotNull(URLUtil.extract2LD(URLUtil.getPattern(), new URL(
				"https://www.ddd.net")));
	}

	
	public void testExtractTLD() throws IOException {
		// Wrong top level domains
		Assert.assertNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://www.ddd.abc")));
		Assert.assertNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://www.ddd.cde")));
		Assert.assertNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://www.ddd.efe")));
		Assert.assertNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://www.ddd.grg")));
		// Correct top level domains
		Assert.assertNotNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://www.ddd.co")));
		Assert.assertNotNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://www.ddd.com")));
		Assert.assertNotNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"http://ddd.org")));
		Assert.assertNotNull(URLUtil.extractTLD(URLUtil.getPattern(), new URL(
				"https://www.ddd.net")));

	}

}
