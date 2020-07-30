package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Checks if in persistent map exists "bgcolor": if it does, it takes its value
 * and copys it under name "background" into temporary parameters. If it does
 * not exist, it puts value "7F7F7F" into temporary parameters under name
 * "background". Then, it delegates further processing to script
 * "/private/home.smscr".
 * 
 * @author nikola
 *
 */
public class Home implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getPersistentParameter("bgcolor");
		if (bgcolor == null) {
			bgcolor = "7F7F7F";
		}

		context.setTemporaryParameter("background", bgcolor);
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
