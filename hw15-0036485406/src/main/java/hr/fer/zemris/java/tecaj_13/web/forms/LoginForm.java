package hr.fer.zemris.java.tecaj_13.web.forms;

import static hr.fer.zemris.java.tecaj_13.web.forms.FormUtil.prepare;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.LoginServlet;

/**
 * Model form corresponding to the web-representation od domain object
 * {@link BlogUser} used for logging in. Its used in {@link LoginServlet}.
 * 
 * @author nikola
 */
public class LoginForm {

	/**
	 * User's nickname
	 */
	private String nick;
	/**
	 * User's password
	 */
	private String password;
	
	/**
	 * The error map. It expects keys to be property names and values error texts.
	 */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Object for hashing
	 */
	private MessageDigest md;
	
	/**
	 * Constructor.
	 */
	public LoginForm() {
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}
	
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
		this.nick = prepare(req.getParameter("nick"));
		this.password = prepare(req.getParameter("password"));
	}
	
	/**
	 * The method validates the form. The form has to be pre-filled with
	 * {{@link #fillFromHTTPRequest()}. The method checks the semantic accuracy of
	 * all data and, if necessary, registers errors in the error map.
	 */
	public void validate() {
		errors.clear();
		
		if (this.nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan!");
		}
		
		if (this.password.isEmpty()) {
			errors.put("password", "Lozinka je obavezna!");
		}
		
		BlogUser blogUser = DAOProvider.getDAO().getBlogUser(nick);
		if(blogUser == null) {
			errors.put("nick", "Korisnik s danim nadimkom ne postoji!");
			nick = "";
		} else {
			String passwordHash = FormUtil.hexEncode(md.digest(password.getBytes()));
			if(!blogUser.getPasswordHash().equals(passwordHash)) {
				errors.put("password", "Lozinka nije valjana.");
				passwordHash = "";
			}
		}
		
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
