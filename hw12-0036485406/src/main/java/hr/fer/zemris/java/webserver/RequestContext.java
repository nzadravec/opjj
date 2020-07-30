package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class represents HTTP request context.
 * 
 * @author nikola
 *
 */
public class RequestContext {

	/**
	 * Stream where HTTP response is written
	 */
	private OutputStream outputStream;
	/**
	 * Contexts charset (not used for generating header)
	 */
	private Charset charset;
	/**
	 * Default value for encoding
	 */
	private static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * Charsets encoding (name)
	 */
	private String encoding = DEFAULT_ENCODING;
	/**
	 * Default value for status code
	 */
	private static final int DEFAULT_STATUS_CODE = 200;
	/**
	 * HTTP status code
	 */
	private int statusCode = DEFAULT_STATUS_CODE;
	/**
	 * Default value for status text
	 */
	private static final String DEFAULT_STATUS_TEXT = "OK";
	/**
	 * HTTP status text
	 */
	private String statusText = DEFAULT_STATUS_TEXT;
	/**
	 * Default value for mime type
	 */
	private static final String DEFAULT_MIME_TYPE = "text/html";
	/**
	 * HTTP mime type
	 */
	private String mimeType = DEFAULT_MIME_TYPE;

	/**
	 * URL parameters
	 */
	private Map<String, String> parameters;
	/**
	 * Temporary parameters
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();
	/**
	 * Persistent parameters
	 */
	private Map<String, String> persistentParameters;
	/**
	 * HTTP cookies
	 */
	private List<RCCookie> outputCookies;

	private static boolean DEFAULT_HEADER_GENERATED = false;
	/**
	 * Flag to indicate if header is already generated
	 */
	private boolean headerGenerated = DEFAULT_HEADER_GENERATED;
	/**
	 * Dispatcher
	 */
	private IDispatcher dispatcher;

	/**
	 * Constructor.
	 * 
	 * @param outputStream stream where HTTP context is written
	 * @param parameters URLs parameters
	 * @param persistentParameters persistent parameters
	 * @param outputCookies HTTP cookies
	 * @param temporaryParameters temporary parameters
	 * @param dispatcher dispatcher
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
	}

	/**
	 * Constructor.
	 * 
	 * @param outputStream stream where HTTP context is written
	 * @param parameters URLs parameters
	 * @param persistentParameters persistent parameters
	 * @param outputCookies HTTPs cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

		Objects.requireNonNull(outputStream, "outputStream must not be null");
		this.outputStream = outputStream;

		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
	}

	/**
	 * Sets encoding to given value.
	 * 
	 * @param encoding given value
	 */
	public void setEncoding(String encoding) {
		throwExcetionIfHeaderGenerated();

		this.encoding = encoding;
	}

	/**
	 * Sets status code to given value
	 * 
	 * @param statusCode given value
	 */
	public void setStatusCode(int statusCode) {
		throwExcetionIfHeaderGenerated();

		this.statusCode = statusCode;
	}

	/**
	 * Sets status text to given value
	 * 
	 * @param statusText given value
	 */
	public void setStatusText(String statusText) {
		throwExcetionIfHeaderGenerated();

		this.statusText = statusText;
	}

	/**
	 * Sets mime type to given value
	 * 
	 * @param mimeType given value
	 */
	public void setMimeType(String mimeType) {
		throwExcetionIfHeaderGenerated();

		this.mimeType = mimeType;
	}

	/**
	 * Adds cookie.
	 * 
	 * @param cookie cookie
	 */
	public void addRCCookie(RCCookie cookie) {
		throwExcetionIfHeaderGenerated();

		outputCookies.add(cookie);
	}

	/**
	 * Throws {@link RuntimeException} if header is generated.
	 */
	private void throwExcetionIfHeaderGenerated() {
		if (headerGenerated) {
			throw new RuntimeException("header is already generated");
		}
	}

	/**
	 * Retrieves value from parameters map (or null if no association exists).
	 * 
	 * @param name
	 *            map key
	 * @return value from parameters map
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in <code>parameters</code> map.
	 * 
	 * @return names of all parameters in <code>parameters</code> map
	 */
	public Set<String> getParameterNames() {
		return new HashSet<>(parameters.keySet());
	}

	/**
	 * Retrieves value from <code>persistentParameters</code> map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            map key
	 * @return value from <code>persistentParameters</code> map
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in <code>persistentParameters</code> map.
	 * 
	 * @return names of all parameters in <code>persistentParameters</code> map
	 */
	public Set<String> getPersistentParameterNames() {
		return new HashSet<>(persistentParameters.keySet());
	}

	/**
	 * Stores a value to <code>persistentParameters</code> map.
	 * 
	 * @param name
	 *            map key
	 * @param value
	 *            value to be stored
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes a value from <code>persistentParameters</code> map.
	 * 
	 * @param name
	 *            map key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Retrieves value from <code>temporaryParameters</code> map (or null if no
	 * association exists).
	 * 
	 * @param name
	 *            map key
	 * @return value from <code>temporaryParameters</code> map
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Retrieves names of all parameters in <code>temporaryParameters</code> map
	 * 
	 * @param name
	 *            map key
	 * @return names of all parameters in <code>temporaryParameters</code> map
	 */
	public Set<String> getTemporaryParameterNames() {
		return new HashSet<>(temporaryParameters.keySet());
	}

	/**
	 * Stores a value to <code>temporaryParameters</code> map
	 * 
	 * @param name
	 *            map key
	 * @param value
	 *            value to be stored
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes a value from <code>temporaryParameters</code> map.
	 * 
	 * @param name
	 *            map key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * Gets dispatcher.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Writes given byte array into output stream.
	 * 
	 * @param data given byte array
	 * @return this
	 * @throws IOException if an I/O error occurs
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			createAndwriteHeaderToOutputStream();
		}

		outputStream.write(data);
		return this;
	}

	/**
	 * Writes given string into output stream.
	 * 
	 * @param text given string
	 * @return this
	 * @throws IOException if an I/O error occurs
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			createAndwriteHeaderToOutputStream();
		}

		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Creates header and writes it into output stream.
	 * 
	 * @throws IOException if an I/O error occurs
	 */
	private void createAndwriteHeaderToOutputStream() throws IOException {
		charset = Charset.forName(encoding);

		StringBuilder headerBuilder = new StringBuilder();
		headerBuilder.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		headerBuilder.append(
				"Content-Type: " + mimeType + (mimeType.startsWith("text/") ? "; charset=" + charset : "") + "\r\n");
		for (RCCookie cookie : outputCookies) {
			headerBuilder.append("Set-Cookie: " + cookie + "\r\n");
		}
		headerBuilder.append("\r\n");
		
		outputStream.write(headerBuilder.toString().getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}

	/**
	 * Request context cookie.
	 * 
	 * @author nikola
	 *
	 */
	public static class RCCookie {
		/**
		 * Cookies name
		 */
		private String name;
		/**
		 * Cookies value
		 */
		private String value;
		/**
		 * Cookie is valid for this many milliseconds
		 */
		private Integer maxAge;
		/**
		 * Cookie is only valid for this domain
		 */
		private String domain;
		/**
		 * Cookie should be sent back to server only with requests whose path starts with this path
		 */
		private String path;

		/**
		 * Constructor.
		 * 
		 * @param name name
		 * @param value value
		 * @param maxAge maxAge
		 * @param domain domain
		 * @param path path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
		}

		/**
		 * Gets name.
		 * 
		 * @return name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets value.SID
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets domain.
		 * 
		 * @return domain.
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets path.
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets max age.
		 * 
		 * @return max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(name + "=" + value);
			if (domain != null) {
				sb.append("; " + "Domain=" + domain);
			}
			if (path != null) {
				sb.append("; " + "Path=" + path);
			}
			if (maxAge != null) {
				sb.append("; " + "Max-Age=" + maxAge);
			}
			return sb.toString();
		}
	}

}
