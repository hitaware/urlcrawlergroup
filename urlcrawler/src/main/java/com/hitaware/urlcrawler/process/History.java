package com.hitaware.urlcrawler.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class <code>History</code> is a POJO for storing <code>Page</code>(a single
 * web page) related data, including url, link depth and domain of a web page.
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
public class History {

	private List<Job> jobs = new ArrayList<Job>();

	public List<Job> getJobs() {
		return jobs;
	}

	public Job getJob(String id) {
		Iterator<Job> iter = jobs.iterator();
		while (iter.hasNext()) {
			Job j = iter.next();
			if (j.getId().equals(id)) {
				return j;
			}
		}
		return null;
	}

	public void addJob(Job job) {
		jobs.add(job);
	}

}
