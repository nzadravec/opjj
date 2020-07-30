package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Checks if parameter "bgcolor" was received and if it is valid hex-encoded
 * color (a string containing exactly 6 hex-digits). If yes, stores the received
 * value in persistent map under name "bgcolor"; generates HTML document with
 * link to "/index2.html" and messages that the color is updated. If no, ignores
 * everything; generates HTML document with link to "/index2.html" and messages
 * that the color is not updated.
 * 
 * @author nikola
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");
		String message;
		try {
		    Color.decode("#"+bgcolor);
		    context.setPersistentParameter("bgcolor", bgcolor);
		    message = "Color is updated";
		} catch (Exception e) {
			message = "Color is not updated";
		}
		
		StringBuilder sb = new StringBuilder(
				"<html>\r\n" +
				"  <head>\r\n" + 
				"    <title>Message</title>\r\n"+
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <h1>" + message + "</h1>\r\n" +
				"    <ul><li>go back to home page "
				+ "<a href=\"http://127.0.0.1:5721/index2.html\" target=\"_blank\">(here)</a></li>\r\n");
			sb.append(
				"  </body>\r\n" + 
				"</html>\r\n");
			
		context.write(sb.toString().getBytes(StandardCharsets.UTF_8));
	}

}
