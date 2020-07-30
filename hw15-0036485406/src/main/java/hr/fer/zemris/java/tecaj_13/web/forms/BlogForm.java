package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import static hr.fer.zemris.java.tecaj_13.web.forms.FormUtil.prepare;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.web.servlets.SaveBlogServlet;

/**
 * Model form corresponding to the web-representation od domain object
 * {@link BlogEntry}. Its used in {@link SaveBlogServlet} for saving new and
 * edited blogs.
 * 
 * @author nikola
 */
public class BlogForm {

	/**
	 * String representation of the identifier
	 */
	private String id;
	/**
	 * Blog's title
	 */
	private String title;
	/**
	 * Blog's text
	 */
	private String text;

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
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}

	/**
	 * Based on the given {@link BlogEntry}, fills out this form.
	 * 
	 * @param blog
	 *            object that keeps the original data
	 */
	public void fillFromBlogEntry(BlogEntry blog) {
		this.id = blog.getId().toString();
		this.title = blog.getTitle();
		this.text = blog.getText();
	}

	/**
	 * Based on the content of this form, fills in the properties of a dedicated
	 * domain object. The method should not be invoked if the form has not
	 * previously been validated and no errors have been found.
	 * 
	 * @param blog
	 *            domain object to be filled
	 */
	public void fillBlogEntry(BlogEntry blog) {
		blog.setTitle(title);
		blog.setText(text);
	}

	/**
	 * The method validates the form. The form has to be pre-filled in some way. The
	 * method checks the semantic accuracy of all data and, if necessary, registers
	 * errors in the error map.
	 */
	public void validate() {
		errors.clear();

		if (this.title.isEmpty()) {
			errors.put("title", "Naslov je obavezan!");
		}

		if (this.text.isEmpty()) {
			errors.put("text", "Tekst je obavezan!");
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
