//Sneha Gundurao
//1001231532


//The code is referred from the skeleton code given with the project description

package computer_networks;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer {

	public static void main(String arg[]) throws Exception {

		int port = 8889;
		//  Establish the listen socket.
		ServerSocket server_soc = new ServerSocket(port);

		//  Process HTTP service requests in an infinite loop.
		while (true) {

			//  Listen for a TCP connection request
			Socket client_soc = server_soc.accept();
			//  Construct an object to process the HTTP request message
			HttpRequest request = new HttpRequest(client_soc);
			//  Create a new thread to process the request.
			Thread thread = new Thread(request);
			//  Start the thread.
			thread.start();

			//server_soc.close();
		}

	}
}
