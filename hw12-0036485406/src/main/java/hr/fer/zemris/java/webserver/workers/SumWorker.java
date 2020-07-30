package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Accepts two parameters: a and b and treat their values as integers. If user
 * passed something that is not integer, or if user did not send any of them, it
 * uses the default value of 1 for a and 2 for b. This worker will calculate
 * their sum, convert it into string, and place it as temporary parameter
 * "zbroj" into {@link RequestContext}. It will also create a temporary
 * parameter a and b with values that were actually used for calculation. Then
 * it will call the dispatcher and ask it to delegate the further processing to
 * the "/private/calc.smscr".
 * 
 * @author nikola
 *
 */
public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String aStr = context.getParameter("a");
		String bStr = context.getParameter("b");
		int a = 1;
		int b = 2;
		try {
			a = Integer.parseInt(aStr);
		} catch (NumberFormatException e) {
		}
		try {
			b = Integer.parseInt(bStr);
		} catch (NumberFormatException e) {
		}

		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter("zbroj", String.valueOf(a + b));
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

}
