package hr.fer.zemris.java.webserver;

/**
 * Represents an interface toward any object that can process request: it gets
 * RequestContext as parameter and it is expected to create a content for
 * client.
 * 
 * @author nikola
 *
 */
public interface IWebWorker {
	/**
	 * Processes given request and creates a content for client.
	 * 
	 * @param context given request
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}
