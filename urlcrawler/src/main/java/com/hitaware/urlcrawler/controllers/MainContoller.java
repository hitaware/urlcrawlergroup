package com.hitaware.urlcrawler.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hitaware.urlcrawler.process.History;
import com.hitaware.urlcrawler.process.Job;
import com.hitaware.urlcrawler.process.JobRunner;
import com.hitaware.urlcrawler.process.Page;
import com.hitaware.urlcrawlercommons.util.CookieUtil;
import com.hitaware.urlcrawlercommons.util.URLUtil;

/**
 * Class <code>MainController</code> is the controller component in MVC pattern.
 * It is responsible for controlling requests coming from url crawler website.
 * <code>GET</code>,<code>POST</code> and <code>XMLHttpRequest</code> are
 * handled by the class.
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
@Controller
public class MainContoller {
	static org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(MainContoller.class);

	/**
	 * This method handles Home page <code>GET</code> requests.
	 *
	 * @since 1.0
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {

		return "index.jsp";

	}

	/**
	 * This method handles submit search <code>XMLHTTPRequest</code>
	 * <code>POST</code> requests.
	 *
	 * @since 1.0
	 */

	@RequestMapping(value = "/submitsearch_", method = RequestMethod.POST)
	public @ResponseBody String submitSearch_(Model model,
			@RequestParam String searchUrl, @RequestParam int maxDepth,
			HttpServletRequest request, HttpServletResponse response) {

		URL url;
		try {
			url = new URL(searchUrl);
			String domain2ld = URLUtil.extract2LD((Pattern) request
					.getSession().getServletContext().getAttribute("pattern"),
					url);

			Job job = Job.setup(url, domain2ld, maxDepth);
			JobRunner.submit(request.getSession(), job);
			((History) request.getSession().getAttribute("searchresults"))
					.addJob(job);
			String cookie = CookieUtil.getCookie(request,
					"cookieprevioussearches");
			String cookieAppend = "searchUrl:" + job.getSearchUrl()
					+ "maxDepth:" + job.getMaxDepth() + "searchend";
			if (cookie == null) {
				cookie = "";
			}
			CookieUtil.setCookie(response, "cookieprevioussearches", cookie
					+ cookieAppend);

			return "Job submitted ID > " + job.getId();
		} catch (MalformedURLException e) {
			return "Malformed URL. It should be like (http://www.your_url.com) > "
					+ searchUrl;
		}
	}

	/**
	 * This method handles submit search page <code>GET</code> requests.
	 *
	 * @since 1.0
	 */
	@RequestMapping(value = "/submitsearch", method = RequestMethod.GET)
	public String submitSearch(Model model) {

		return "submitsearch.jsp";

	}

	/**
	 * This method handles search results <code>GET</code> requests.
	 *
	 * @since 1.0
	 */
	@RequestMapping(value = "/searchresults", method = RequestMethod.GET)
	public String searchResults(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		List<Job> list = ((History) request.getSession().getAttribute(
				"searchresults")).getJobs();
		ArrayList<Job> alist = (ArrayList<Job>) list;
		@SuppressWarnings("unchecked")
		ArrayList<Job> cloneAList = (ArrayList<Job>) alist.clone();
		Collections.reverse(cloneAList);
		model.addAttribute("jobs", cloneAList);
		return "searchresults.jsp";

	}

	/**
	 * This method handles statistics <code>GET</code> requests.
	 *
	 * @since 1.0
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public String statistics(Model model, HttpServletRequest request,
			HttpServletResponse response, @RequestParam String id) {
		Job job = ((History) request.getSession().getAttribute("searchresults"))
				.getJob(id);

		model.addAttribute("domains", job.getDomains().keySet());
		model.addAttribute("domainmap", job.getDomains());

		model.addAttribute("imageurl", getImageUrl(job.getDomains()));
		return "statistics.jsp";

	}

	private String getImageUrl(Map<String, List<Page>> mapDomains) {
		Set<String> domains = mapDomains.keySet();
		Iterator<String> iter = domains.iterator();

		StringBuffer sbValue = new StringBuffer();
		StringBuffer sbLabel = new StringBuffer();
		while (iter.hasNext()) {
			String domain = iter.next();
			List<Page> pages = mapDomains.get(domain);
			sbLabel.append(domain + "|");
			sbValue.append(pages.size() + ",");
		}
		String labels = sbLabel.toString();
		String values = sbValue.toString();
		if (labels.endsWith("|")) {
			labels = labels.substring(0, labels.length() - 1);
			values = values.substring(0, values.length() - 1);
		}

		String url = "https://chart.googleapis.com/chart?cht=bvs&chd=t:"
				+ values + "&chbh=80,20,0&chs=1000x300&chl=" + labels;
		return url;
	}

	/**
	 * This method handles previous searches <code>GET</code> requests.
	 *
	 * @since 1.0
	 */
	@RequestMapping(value = "/previoussearches", method = RequestMethod.GET)
	public String previousSearches(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String cookie = CookieUtil.getCookie(request, "cookieprevioussearches");
		if (cookie == null) {
			model.addAttribute("jobs", new ArrayList<Job>());
		} else {
			model.addAttribute("jobs", Job.parseJobsString(cookie));

		}
		return "previoussearches.jsp";

	}

}
