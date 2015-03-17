package com.hitaware.urlcrawler.process;

import java.net.URL;

/**
 * Class <code>Page</code> is a POJO for storing <code>Page</code>(a web page)
 * related data, including url, link depth and domain of a web page.
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
public class Page {

	private URL url;
	private int depth;
	private String domain;

	public Page() {

	}

	public Page(URL url, String domain, int depth) {
		this.url = url;
		this.depth = depth;
		this.domain = domain;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;

	}

	@Override
	public String toString() {
		return "Page [url=" + url + ", domain=" + domain + ", depth=" + depth
				+ "]";
	}

}
