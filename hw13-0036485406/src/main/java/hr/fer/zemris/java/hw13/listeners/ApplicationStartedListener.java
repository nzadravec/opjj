package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener adds information when it was called (when application starts) into
 * servlet context's attributes as "startTime". This attribute is used in
 * appinfo.jsp that displays how long is this web application running.
 * 
 * @author nikola
 *
 */
@WebListener
public class ApplicationStartedListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long startTime = System.currentTimeMillis();
		sce.getServletContext().setAttribute("startTime", startTime);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
