package hr.fer.zemris.java.webserver.workers;

import java.nio.charset.StandardCharsets;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Outputs back to the user the parameters it obtained formatted as
 * an HTML table.
 * 
 * @author nikola
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		StringBuilder sb = new StringBuilder(
				"<html>\r\n" +
				"  <head>\r\n" + 
				"    <title>Ispis zaglavlja</title>\r\n"+
				"  </head>\r\n" + 
				"  <body>\r\n" + 
				"    <h1>Informacije o poslanim parametrima</h1>\r\n" +
				"    <p>Definirane varijable:</p>\r\n" + 
				"    <table border='1'>\r\n");
		for(String name : context.getParameterNames()) {
			sb.append("      <tr><td>")
				.append(name)
				.append("</td><td>")
				.append(context.getParameter(name))
				.append("</td></tr>\r\n");
		}
		sb.append(
			"    </table>\r\n" + 
			"  </body>\r\n" + 
			"</html>\r\n");

		context.write(sb.toString().getBytes(
				StandardCharsets.UTF_8
				));	
	}

}
