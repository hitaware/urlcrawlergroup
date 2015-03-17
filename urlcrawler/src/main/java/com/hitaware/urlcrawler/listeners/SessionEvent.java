package com.hitaware.urlcrawler.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.LoggerFactory;

import com.hitaware.urlcrawler.process.History;

/**
 * Class <code>SessionEvent</code> listens to session events. When a session
 * starts
 * <ul>
 * <li>We initialize an empty array for search results for the session.</li>
 * <li>We configure session timeout to 1 hour.</li>
 * </ul>
 * 
 * @author Ozgen Gunay
 * @since 1.0
 */
@WebListener
public class SessionEvent implements HttpSessionListener {
	static org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(SessionEvent.class);

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		LOGGER.info("Session started");
		event.getSession().setMaxInactiveInterval(3600);
		event.getSession().setAttribute("searchresults", new History());

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//

	}

}
