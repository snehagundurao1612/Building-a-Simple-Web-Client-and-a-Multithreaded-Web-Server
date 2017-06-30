//Sneha Gundurao
//1001231532

//The code is referred from the skeleton code given with the project description

package computer_networks;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

final class HttpRequest implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;

	//  Constructor
	public HttpRequest(Socket socket) throws Exception {
		this.socket = socket;
	}

	//  Implement the run() method of the Runnable interface
	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception {
		//  Get a reference to the socket's input and output streams
		InputStream is = socket.getInputStream();
		OutputStream ss = socket.getOutputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		//  Set up input stream filters
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		//  Get the request line of the HTTP request message.
		String requestLine = br.readLine();
		//  Display the request line.
		System.out.println();
		System.out.println(requestLine);
		//  Get and display the header lines.
		String headerLine = null;
		while ((headerLine = br.readLine()).length() != 0) {
			System.out.println(headerLine);

		}

		//  Extract the filename from the request line.
		StringTokenizer tokens = new StringTokenizer(requestLine);
		//  skip over the method, which should be "GET"
		tokens.nextToken();
		String fileName = tokens.nextToken();
		// Prepend a "." so that file request is within the current directory.
		fileName = "." + fileName;
		// Open the requested file.
		FileInputStream fis1 = null;
		boolean fileExists = true;
		try {
			fis1 = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		}
		// Construct the response message.
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		if (fileExists) {
			statusLine = "HTTP/1.1 200 OK\n";
			contentTypeLine = "Content-type:" + contentType(fileName) + CRLF;
		} else {
			statusLine = "HTTP/1.1 404 Not Found\n";
			contentTypeLine = "Contents cannot be displayed since file is not found\n";
			entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + "<BODY>Not Found</BODY></HTML>";

		}
		// Send the status line.
		os.writeBytes(statusLine);
		// Send the content type line.
		os.writeBytes(contentTypeLine);
		// Send a blank line to indicate the end of the header lines.
		os.writeBytes(CRLF);

		//  Send the entity body.
		if (fileExists) {
			sendBytes(fis1, os);
			fis1.close();
		} else {
			os.writeBytes(entityBody);
		}
		//  Close streams and socket.
		os.close();
		br.close();
		socket.close();
	}

	private void sendBytes(FileInputStream fis1, DataOutputStream os) throws IOException {
		// TODO Auto-generated method stub
		// Construct a 1K buffer to hold bytes on their way to the socket.
		byte[] buffer = new byte[1024];
		int bytes = 0;
		// Copy requested file into the socket's output stream.
		while ((bytes = fis1.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}

	private String contentType(String fileName) {
		// TODO Auto-generated method stub
		if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
			return "text/html";
		}
		if (fileName.endsWith(".gif") || fileName.endsWith(".GIF")) {
			return "image/gif";
		}
		if (fileName.endsWith(".jpeg")) {
			return "image/jpeg";
		}

		return "application/octet-stream";
	}
}
