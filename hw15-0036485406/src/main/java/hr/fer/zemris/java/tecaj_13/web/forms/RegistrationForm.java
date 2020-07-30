package hr.fer.zemris.java.tecaj_13.web.forms;

import static hr.fer.zemris.java.tecaj_13.web.forms.FormUtil.prepare;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.NewUserServlet;

/**
 * Model form corresponding to the web-representation od domain object
 * {@link BlogUser} used for registration. Its used in {@link NewUserServlet}.
 * 
 * @author nikola
 */
public class RegistrationForm {

	/**
	 * String representation of user's identifier
	 */
	private String id;
	/**
	 * User's first name
	 */
	private String firstName;
	/**
	 * User's last name
	 */
	private String lastName;
	/**
	 * User's nickname
	 */
	private String nick;
	/**
	 * User's email
	 */
	private String email;
	/**
	 * User's password
	 */
	private String password;
	/**
	 * User's password hash
	 */
	private String passwordHash;
	
	/**
	 * Object for hashing
	 */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * The error map. It expects keys to be property names and values error texts.
	 */
	private MessageDigest md;
	
	/**
	 * Constructor.
	 */
	public RegistrationForm() {
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
		this.id = prepare(req.getParameter("id"));
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
		this.password = prepare(req.getParameter("password"));
		this.passwordHash = FormUtil.hexEncode(md.digest(password.getBytes()));
	}
	
	/**
	 * The method validates the form. The form has to be pre-filled with
	 * {{@link #fillFromHTTPRequest()}. The method checks the semantic accuracy of
	 * all data and, if necessary, registers errors in the error map.
	 */
	public void validate() {
		errors.clear();

		if (!this.id.isEmpty()) {
			try {
				Long.parseLong(this.id);
			} catch (NumberFormatException ex) {
				errors.put("id", "Vrijednost identifikatora nije valjana.");
			}
		}

		if (this.firstName.isEmpty()) {
			errors.put("firstName", "Ime je obavezno!");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastName", "Prezime je obavezno!");
		}
		
		if (this.nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan!");
		}

		if (this.email.isEmpty()) {
			errors.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail nije ispravnog formata.");
			}
		}
		
		if (this.password.isEmpty()) {
			errors.put("password", "Lozinka je obavezna!");
		}
		
		BlogUser blogUser = DAOProvider.getDAO().getBlogUser(nick);
		if(blogUser != null) {
			errors.put("nick", "Nadimak je zauzet.");
		}
		
	}
	
	/**
	 * Based on the content of this form, fills in the properties of a dedicated
	 * domain object. The method should not be invoked if the form has not
	 * previously been validated and no errors have been found.
	 * 
	 * @param blog
	 *            domain object to be filled
	 */
	public void fillBlogUser(BlogUser user) {
		if (this.id.isEmpty()) {
			user.setId(null);
		} else {
			user.setId(Long.valueOf(this.id));
		}
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		user.setPasswordHash(passwordHash);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
}
