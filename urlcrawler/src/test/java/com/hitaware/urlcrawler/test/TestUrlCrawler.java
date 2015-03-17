package com.hitaware.urlcrawler.test;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.hitaware.urlcrawler.process.Page;

public class TestUrlCrawler {
	@Test
	public void pageUniqueness() throws IOException {

		Set<Page> set = new HashSet<Page>();
		Page p = null;

		p = new Page();
		p.setUrl(new URL("http://www.google.com"));
		set.add(p);

		p = new Page();
		p.setUrl(new URL("http://www.google.com"));
		set.add(p);

		p = new Page();
		p.setUrl(new URL("http://www.google.com/"));
		set.add(p);

		p = new Page();
		p.setUrl(new URL("http://www.google.com/?"));
		set.add(p);

		Assert.assertEquals(3, set.size());
	}

}
