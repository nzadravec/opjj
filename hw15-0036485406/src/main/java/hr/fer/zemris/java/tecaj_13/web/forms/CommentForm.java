package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import static hr.fer.zemris.java.tecaj_13.web.forms.FormUtil.prepare;

/**
 * Model form corresponding to the web-representation od domain object
 * {@link CommentForm}. Its used in {@link SaveCommentServlet} for saving
 * comments.
 * 
 * @author nikola
 */
public class CommentForm {

	/**
	 * Comment's message
	 */
	private String message;

	/**
	 * The error map. It expects keys to be property names and values error texts.
	 */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * Gets error message for the requested property.
	 * 
	 * @param name
	 *            the name of the property for which an error message is required
	 * @return error message or <code>null</code> if the property has no associated
	 *         error
	 */
	public String getError(String name) {
		return errors.get(name);
	}

	/**
	 * Checks for at least one of the properties associated with the error.
	 * 
	 * @return <code>true</code> if there is at least one, <code>false</code>
	 *         otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * Checks if the requested property has an associated error.
	 * 
	 * @param name
	 *            the name of the property that is being tested for the existence of
	 *            an error
	 * @return <code>true</code> if is has, <code>false</code> otherwise
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Based on the parameters received through {@link HttpServletRequest}, fills in
	 * the properties of this form.
	 * 
	 * @param req
	 *            object with parameters
	 */
	public void fillFromHTTPRequest(HttpServletRequest req) {
		this.message = prepare(req.getParameter("message"));
	}

	/**
	 * The method validates the form. The form has to be pre-filled with
	 * {{@link #fillFromHTTPRequest()}. The method checks the semantic accuracy of
	 * all data and, if necessary, registers errors in the error map.
	 */
	public void validate() {
		errors.clear();
		
		if (this.message.isEmpty()) {
			errors.put("message", "Poruka je obavezna!");
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
