package com.hitaware.urlcrawler.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import org.slf4j.LoggerFactory;

import com.hitaware.urlcrawlercommons.util.URLUtil;

/**
 * Class <code>JobRunner</code> runs a specific job in a
 * <code>ExecutorService</code> thread.
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
public class JobRunner implements Runnable {
	static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(JobRunner.class);

	private Job job;

	private HttpSession session;

	private JobRunner(HttpSession session, Job job) {

		this.job = job;
		this.session = session;

		((ExecutorService) session.getServletContext().getAttribute("executor"))
				.submit(this);

	}

	public static void submit(HttpSession session, Job job) {
		new JobRunner(session, job);

	}

	@Override
	public void run() {
		Page page = null;
		while (true) {
			try {
				page = getNextPage();
				if (page == null) {
					break;
				}

				URLConnection connection = page.getUrl().openConnection();
				connection.setConnectTimeout(20000);
				connection.setReadTimeout(20000);
				InputStream is = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				process(br, page);
				br.close();
			} catch (IOException e) {
				LOGGER.error("Error occured > " + e.toString());
			}
		}
		job.setFinished(true);
		logResults();
	}

	private void logResults() {
		LOGGER.info("Job finished successfully with " + job.getDomains().size()
				+ " domains." + "queue index > "
				+ job.getInnerProcessingQueueIndex() + " innerlist size > "
				+ job.getInnerProcessingQueue().size());

		Set<String> domains = job.getDomains().keySet();
		LOGGER.info("Domains size :" + domains.size());
		Iterator<String> iter = domains.iterator();
		while (iter.hasNext()) {
			String domain = iter.next();
			LOGGER.info("DOMAIN >" + domain);
			List<Page> pages = job.getDomains().get(domain);
			Iterator<Page> iter2 = pages.iterator();
			while (iter2.hasNext()) {
				Page page2 = iter2.next();
				LOGGER.info(page2.getUrl().toString());
			}
		}

	}

	public Page getNextPage() {
		Page page = job.getNextPageToProcess();
		if (page == null || page.getDepth() > job.getMaxDepth())
			return null;
		return page;
	}

	public List<String> process(Reader reader, final Page page)
			throws IOException {
		LOGGER.info("Processing page > " + page);
		final ArrayList<String> list = new ArrayList<String>();
		ParserDelegator parserDelegator = new ParserDelegator();
		ParserCallback parserCallback = new ParserCallback() {
			protected void handleLink(Page base, String str) {
				try {
					URL newLink = new URL(base.getUrl(), str);
					String domain2ld = URLUtil.extract2LD((Pattern) session
							.getServletContext().getAttribute("pattern"),
							newLink);

					Page newPage = new Page(newLink, domain2ld,
							base.getDepth() + 1);
					if (job.addToInnerProcessingQueue(newPage)) {
						LOGGER.info("New page found > " + newPage);
					}
				} catch (MalformedURLException e) {
					LOGGER.error("Found malformed link > " + str);
				}
			}

			@Override
			public void handleStartTag(HTML.Tag t, MutableAttributeSet a,
					int pos) {
				handleSimpleTag(t, a, pos);
			}

			@Override
			public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a,
					int pos) {
				if ((t == HTML.Tag.A) || (t == HTML.Tag.FRAME)) {
					String href = null;
					if (t == HTML.Tag.FRAME) {
						href = (String) a.getAttribute(HTML.Attribute.SRC);
					}
					if (t == HTML.Tag.A) {
						href = (String) a.getAttribute(HTML.Attribute.HREF);
					}
					if (href != null) {
						int i = href.indexOf('#');
						if (i != -1) {
							href = href.substring(0, i);
						}
						if (href.toLowerCase().startsWith("mailto:")) {
							return;
						}
						handleLink(page, href);
					}
				}
			}

		};
		parserDelegator.parse(reader, parserCallback, true);
		return list;
	}

}