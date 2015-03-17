package com.hitaware.urlcrawler.listeners;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.LoggerFactory;

import com.hitaware.urlcrawlercommons.util.URLUtil;


/**
 * Class <code>ContextEvent</code> listens to context events. When the web
 * application starts
 * <ul>
 * <li>We initialize application thread pool with initial size of 10 for job
 * submit threads</li>
 * <li>We cache tld(top level domains)file for domain name resolution.</li>
 * </ul>
 * When the web application ends, we shutdown thread pool explicitly.
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
@WebListener
public class ContextEvent implements ServletContextListener {
	static org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(ContextEvent.class);

	private ExecutorService executor;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		executor.shutdown();

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			Pattern p = URLUtil.getPattern();
			event.getServletContext().setAttribute("pattern", p);

			executor = Executors.newFixedThreadPool(10);

			event.getServletContext().setAttribute("executor", executor);
			LOGGER.info("Context started");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Error occured > ", e);
		}

	}
}
