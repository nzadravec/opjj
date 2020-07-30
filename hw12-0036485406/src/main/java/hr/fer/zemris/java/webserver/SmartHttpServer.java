package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.Util;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Simple and functional web server.
 * 
 * @author nikola
 *
 */
public class SmartHttpServer {

	/**
	 * Address on which server listens
	 */
	private String address;
	/**
	 * Domain name of our web server
	 */
	private String domainName;
	/**
	 * Port on which server listens
	 */
	private int port;
	/**
	 * How many threads server uses for thread pool
	 */
	private int workerThreads;
	/**
	 * Duration of user sessions in seconds
	 */
	private int sessionTimeout;
	/**
	 * Extension to mime-type mappings
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Url to worker mappings
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Server thread
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool of {@link ClientWorker}
	 */
	private ExecutorService threadPool;
	/**
	 * Root directory from which we serve files
	 */
	private Path documentRoot;
	/**
	 * SID to session mappings
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random generator
	 */
	private Random sessionRandom = new Random();
	/**
	 * Flag to indicate when to stop servers thread
	 */
	private volatile boolean stopRequested;

	/**
	 * Constructor.
	 * 
	 * @param configFileName
	 *            servers configuration file name
	 */
	public SmartHttpServer(String configFileName) {
		parseServerConfigFile(configFileName);
	}

	/**
	 * Parses servers configuration file.
	 * 
	 * @param configFileName
	 *            servers configuration file name
	 */
	private void parseServerConfigFile(String configFileName) {
		List<String> lines = readAllLinesFromFile(configFileName);

		for (String line : lines) {
			if (line.isEmpty() || line.charAt(0) == '#') {
				continue;
			}

			String property = line.substring(line.indexOf(".") + 1, line.indexOf(" "));
			String value = line.substring(line.indexOf("=") + 1).trim();
			switch (property) {
			case "address":
				address = value;
				break;
			case "domainName":
				domainName = value;
				break;
			case "port":
				port = Integer.parseInt(value);
				break;
			case "workerThreads":
				workerThreads = Integer.parseInt(value);
				break;
			case "documentRoot":
				documentRoot = Paths.get(value);
				break;
			case "mimeConfig":
				parseMimeConfigFile(value);
				break;
			case "timeout":
				sessionTimeout = Integer.parseInt(value);
				break;
			case "workers":
				parseWorkersConfigFile(value);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * Parses workers configuration file.
	 * 
	 * @param configFileName
	 *            workers configuration file name
	 */
	@SuppressWarnings("deprecation")
	private void parseWorkersConfigFile(String configFileName) {
		List<String> lines = readAllLinesFromFile(configFileName);

		for (String line : lines) {
			if (line.isEmpty() || line.charAt(0) == '#') {
				continue;
			}

			String[] keyValue = line.split("=");

			String path = keyValue[0].trim();
			if (workersMap.containsKey(path)) {
				throw new RuntimeException(
						"There are multiple lines with same path " + path + " in workers configuration file.");
			}

			String fqcn = keyValue[1].trim();
			Object newObject;
			try {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				newObject = referenceToClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			IWebWorker iww = (IWebWorker) newObject;
			workersMap.put(path, iww);
		}
	}

	/**
	 * Parses mimes configuration file.
	 * 
	 * @param configFileName
	 *            mimes configuration file name
	 */
	private void parseMimeConfigFile(String configFileName) {
		List<String> lines = readAllLinesFromFile(configFileName);

		for (String line : lines) {
			if (line.isEmpty() || line.charAt(0) == '#') {
				continue;
			}

			String[] keyValue = line.split("=");
			mimeTypes.put(keyValue[0].trim(), keyValue[1].trim());
		}
	}

	/**
	 * Reads all lines from given file name and returns list of them.
	 * 
	 * @param fileName
	 *            given file name.
	 * @return list of given file name lines.
	 */
	private List<String> readAllLinesFromFile(String fileName) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return lines;
	}

	/**
	 * Starts server thread if not already running and initializes threadpool.
	 */
	protected synchronized void start() {
		if (serverThread != null) {
			return;
		}

		serverThread = new ServerThread();
		serverThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Signals server thread to stop running and calls shutdown on threadpool.
	 */
	protected synchronized void stop() {
		stopRequested = true;
		threadPool.shutdown();
	}

	/**
	 * Servers thread.
	 * 
	 * @author nikola
	 *
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				System.out.println("Starting server...");
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

				new ExpiredSessionsCollector().start();

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			while (true) {
				Socket client;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					continue;
				}
				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);
				if (stopRequested) {
					break;
				}
			}

			try {
				serverSocket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Session map entry.
	 * 
	 * @author nikola
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * Sessions ID
		 */
		@SuppressWarnings("unused")
		String sid;
		/**
		 * Host name
		 */
		String host;
		/**
		 * Time in milliseconds till this session is valid
		 */
		long validUntil;
		Map<String, String> map = new ConcurrentHashMap<>();

		/**
		 * Constructor.
		 * 
		 * @param sid
		 *            sessions ID
		 * @param host
		 *            host name
		 * @param validUntil
		 *            time in milliseconds till this session is valid
		 */
		public SessionMapEntry(String sid, String host, long validUntil) {
			super();
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
		}
	}

	/**
	 * Object of this class removes expired sessions.
	 * 
	 * @author nikola
	 *
	 */
	private class ExpiredSessionsCollector extends Thread {

		/**
		 * Indicates threads cleaning period
		 */
		private final long sleepFor = 5 * 60 * 1000;

		/**
		 * Constructor.
		 */
		private ExpiredSessionsCollector() {
			super();
			setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				try {
					sleep(sleepFor);
				} catch (InterruptedException e) {
				}
				synchronized (SmartHttpServer.this) {
					long now = System.currentTimeMillis();
					for (Entry<String, SessionMapEntry> e : sessions.entrySet()) {
						if (e.getValue().validUntil < now) {
							sessions.remove(e.getKey());
						}
					}
				}
			}
		}
	}

	/**
	 * Client worker.
	 * 
	 * @author nikola
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * Clients socket
		 */
		private Socket csocket;
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		/**
		 * version of HTTP
		 */
		private String version;
		/**
		 * method of HTTP
		 */
		private String method;
		/**
		 * Host name
		 */
		private String host;
		/**
		 * HTTP parameters
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Temporary parameters
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Persistent parameters
		 */
		private Map<String, String> permPrams = new HashMap<String, String>();
		/**
		 * HTTP cookies
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID
		 */
		private String SID;
		/**
		 * Request context
		 */
		private RequestContext context;

		/**
		 * Constructor.
		 * 
		 * @param csocket
		 *            clients socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {

				istream = new PushbackInputStream(new BufferedInputStream(csocket.getInputStream()));
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				byte[] request = readRequest();
				if (request == null) {
					sendError(400, "Bad Request");
					return;
				}
				String requestStr = new String(request, StandardCharsets.US_ASCII);

				List<String> headers = extractHeaders(requestStr);

				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(400, "Bad Request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(405, "Method Not Allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")) {
					sendError(505, "HTTP Version Not Supported");
					return;
				}

				for (String s : headers) {
					if (s.startsWith("Host:")) {
						host = s.split(":")[1].trim();
					}
				}
				if (host == null) {
					host = domainName;
				}

				String requestedPath = firstLine[1];
				String urlPath;
				String paramString = null;
				if (requestedPath.contains("?")) {
					urlPath = requestedPath.split("\\?")[0];
					paramString = requestedPath.split("\\?")[1];
				} else {
					urlPath = requestedPath;
				}

				synchronized (SmartHttpServer.this) {
					checkSession(headers);
				}
				permPrams = sessions.get(SID).map;

				if (paramString != null) {
					parseParameters(paramString);
				}

				internalDispatchRequest(urlPath, true);
				ostream.flush();

			} catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			} finally {
				try {
					csocket.close();
				} catch (IOException ex) {
					System.out.println("Exception: " + ex.getMessage());
				}
			}
		}

		/**
		 * Creates new unique sid, stores new object in sessions map and add a cookie to
		 * response if there is no cookie named <code>"sid"</code> that is already in
		 * sessions map and is still valid.
		 * 
		 * @param headers lines od header
		 */
		private void checkSession(List<String> headers) {
			String sidCandidate = null;
			for (String line : headers) {
				if (!line.startsWith("Cookie:")) {
					continue;
				}

				String[] cookies = line.substring("Cookie:".length()).trim().split(";");
				for (String cookie : cookies) {
					if (cookie.startsWith("sid")) {
						sidCandidate = cookie.split("=")[1];
					}
				}
			}

			if (sidCandidate != null) {
				SessionMapEntry sme = sessions.get(sidCandidate);
				if (sme != null && sme.host.equals(host)) {
					long now = System.currentTimeMillis();
					if (sme.validUntil < now) {
						sessions.remove(sidCandidate);
					} else {
						sme.validUntil = now + sessionTimeout * 1000;
						SID = sidCandidate;
						return;
					}
				}
			}

			SID = createUniqueSid();
			long now = System.currentTimeMillis();
			SessionMapEntry sme = new SessionMapEntry(SID, host, now + sessionTimeout * 1000);
			sessions.put(SID, sme);
			outputCookies.add(new RCCookie("sid", SID, null, host, "/"));
		}

		/**
		 * Creates unique SID.
		 * 
		 * @return unique SID
		 */
		private String createUniqueSid() {
			while (true) {
				StringBuilder sidBuilder = new StringBuilder();
				for (int i = 0; i < 20; i++) {
					char c = (char) (65 + sessionRandom.nextInt(26));
					sidBuilder.append(c);
				}
				String sid = sidBuilder.toString();
				if (!sessions.containsKey(sid)) {
					return sid;
				}
			}
		}

		/**
		 * Fill map parameters with parameters given in HTTP request header.
		 * 
		 * @param paramString
		 *            parameters in HTTP request header
		 */
		private void parseParameters(String paramString) {
			for (String s : paramString.split("&")) {
				String[] keyValue = s.split("=");
				params.put(keyValue[0], keyValue[1]);
			}
		}

		/**
		 * Analyzes given path and determines how to process it.
		 * 
		 * @param urlPath
		 *            given path
		 * @param directCall
		 *            <code>true</code> if this method is called directly (ie from run
		 *            method of {@link ClientWorker}), otherwise <code>false</code>
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			if (directCall && urlPath.startsWith("/private")) {
				sendError(404, "Not Found");
				return;
			}
			
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this);
			}

			if (urlPath.startsWith("/ext/")) {
				String fqcn = "hr.fer.zemris.java.webserver.workers." + urlPath.substring(5).trim();
				Object newObject;
				try {
					Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
					newObject = referenceToClass.newInstance();
					IWebWorker iww = (IWebWorker) newObject;
					iww.processRequest(context);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				return;
			}

			IWebWorker iww = workersMap.get(urlPath);
			if (iww != null) {
				iww.processRequest(context);
				return;
			}

			Path requestedFile = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath();
			if (!requestedFile.startsWith(documentRoot)) {
				sendError(403, "File Forbidden");
				return;
			}

			if (!Files.isRegularFile(requestedFile) || !Files.isReadable(requestedFile)) {
				sendError(404, "File Not Found");
				return;
			}

			String fileName = requestedFile.getFileName().toString();
			String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);

			if (fileExtension.equals("smscr")) {
				String documentBody = Util.readFromDisk(requestedFile.toString());
				new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
						new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this)).execute();
				;

			} else {
				String mimeType = mimeTypes.get(fileExtension);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				context.setMimeType(mimeType);

				byte[] data = Files.readAllBytes(requestedFile);
				context.write(data);
			}
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Reads HTTP request header and returns it as byte array.
		 * 
		 * @return HTTP request header as byte array
		 * @throws IOException
		 */
		private byte[] readRequest() throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}

		/**
		 * Splits header represented as single {@link String} object by enter
		 * <code>\n</code> paying attention to multi-line attributes.
		 * 
		 * @param requestHeader
		 *            header represented as single
		 * @return list of headers attributes
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestHeader.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Sends error response.
		 * 
		 * @param statusCode
		 *            status code
		 * @param statusText
		 *            status text
		 * @throws IOException
		 */
		private void sendError(int statusCode, String statusText) throws IOException {

			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies);
			}

			context.setStatusCode(statusCode);
			context.setStatusText(statusText);
			context.write("");
			ostream.flush();
		}

	}

	/**
	 * Main function.
	 * 
	 * @param args
	 *            command line arguments - not used
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("./config/server.properties");
		server.start();
	}

}
