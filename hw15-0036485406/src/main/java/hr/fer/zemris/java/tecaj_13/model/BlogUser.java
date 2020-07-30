package hr.fer.zemris.java.tecaj_13.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class models sigle blog user with its unique id and nickname, first and last
 * name, email and password hash as string. Property passwordHash is used for
 * storing storing a hex-encoded hash value (calculated as SHA-1 hash) obtained
 * from users password.
 * 
 * @author nikola
 *
 */
@NamedQueries({ @NamedQuery(name = "BlogUser.getByNick", query = "select u from BlogUser as u where u.nick=:nick"),
		@NamedQuery(name = "BlogUser.getAllUsers", query = "select u from BlogUser as u") })
@Entity
@Table(name = "blog_users")
public class BlogUser {

	/**
	 * User's identifier
	 */
	private Long id;
	/**
	 * List of user's blogs
	 */
	private List<BlogEntry> entries;
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
	 * User's password hash
	 */
	private String passwordHash;

	/**
	 * Gets users identifier.
	 * 
	 * @return identifier
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets users identifier.
	 * 
	 * @param id identifier
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets list of user's blogs.
	 * 
	 * @return user's blogs
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Sets list of user's blogs.
	 * 
	 * @param entries user's blogs
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Column(nullable = false)
	/**
	 * Gets user's first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets user's first name.
	 * 
	 * @param firstName first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(nullable = false)
	/**
	 * Gets user's last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets user's last name.
	 * 
	 * @param lastName last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(nullable = false)
	/**
	 * Gets user's nickname.
	 * 
	 * @return nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets user's nickname.
	 * 
	 * @param nick nickname
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	@Column(nullable = false)
	/**
	 * Sets user's email.
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets user's email.
	 * 
	 * @param email email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(nullable = false)
	/**
	 * Gets user's password hash.
	 * 
	 * @return password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets user's password hash.
	 * 
	 * @param passwordHash password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
