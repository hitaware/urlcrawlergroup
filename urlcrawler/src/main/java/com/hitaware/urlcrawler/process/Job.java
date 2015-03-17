package com.hitaware.urlcrawler.process;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.UnmodifiableMap;
import org.slf4j.LoggerFactory;

import com.hitaware.urlcrawlercommons.util.UrlCrawlerUUID;

/**
 * Class <code>Job</code> resembles one submitted job, its metadata and
 * properties for processing the job.
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
public class Job {
	static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Job.class);

	/**
	 * Job submit date and time
	 */
	private Date date;
	/**
	 * Job system wide unique id
	 */
	private String id;
	/**
	 * A data structure for processing web pages sequentially.
	 */
	private List<Page> innerProcessingQueue;
	/**
	 * pointer to the web page which crawler is processing.
	 */
	private int innerProcessingQueueIndex;
	/**
	 * A data structure for storing domains and related web sites that were
	 * crawled.
	 */
	private Map<String, List<Page>> domains;
	/**
	 * Max link depth.
	 */
	private int maxDepth;
	/**
	 * Job initial seed url
	 */
	private URL searchUrl;
	/**
	 * Job status
	 */
	private boolean finished;

	private Job() {
		// Prevent default constructor to be called.
	}

	public int getInnerProcessingQueueIndex() {
		return innerProcessingQueueIndex;
	}

	public static Job setup(URL searchUrl, String domain, int maxDepth) {

		Job j = new Job();
		j.id = UrlCrawlerUUID.getUUID();
		j.date = new Date();
		j.innerProcessingQueue = new ArrayList<Page>();
		j.innerProcessingQueue.add(new Page(searchUrl, domain, 0));
		j.domains = new HashMap<String, List<Page>>();
		j.maxDepth = maxDepth;
		j.searchUrl = searchUrl;
		return j;
	}

	public String getId() {
		return id;
	}

	public Page getNextPageToProcess() {
		return innerProcessingQueue.get(innerProcessingQueueIndex++);

	}

	@SuppressWarnings("unchecked")
	public Map<String, List<Page>> getDomains() {

		// Return an unmodifiable map
		return UnmodifiableMap.decorate(domains);
	}

	public boolean addToInnerProcessingQueue(Page page) {
		if (innerProcessingQueue.contains(page))
			return false;

		addToDomainMap(page);
		return innerProcessingQueue.add(page);

	}

	private void addToDomainMap(Page page) {
		List<Page> pages = domains.get(page.getDomain());
		if (pages == null) {
			List<Page> tmpPages = new ArrayList<Page>();
			tmpPages.add(page);
			domains.put(page.getDomain(), tmpPages);
		} else {
			pages.add(page);
		}
	}

	public Date getDate() {
		return date;
	}

	public URL getSearchUrl() {
		return searchUrl;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public List<Page> getInnerProcessingQueue() {
		return Collections.unmodifiableList(innerProcessingQueue);
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", date=" + date
				+ ", innerProcessingQueue size=," + innerProcessingQueue.size()
				+ "+ , domain count=," + domains.size() + "]";
	}

	public static List<Job> parseJobsString(String cookie) {
		List<Job> jobs = new ArrayList<Job>();
		String[] st = cookie.split("searchend");

		for (String str : st) {

			// Create a Pattern object
			Pattern r = Pattern.compile("searchUrl:(.*)maxDepth:(.*)");

			// Now create matcher object.
			Matcher m = r.matcher(str);
			if (m.find()) {
				try {
					URL url = new URL(m.group(1));
					Job job = Job.setup(url, "", Integer.parseInt(m.group(2)));
					jobs.add(job);
				} catch (NumberFormatException e) {
					LOGGER.error("Error occured", e);
				} catch (MalformedURLException e) {
					LOGGER.error("Error occured", e);
				}
			}

		}
		Collections.reverse(jobs);
		return jobs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
