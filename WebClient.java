//Sneha Gundurao
//1001231532

//this code is referred from a few youtube tutorials on socket programming.
//https://www.youtube.com/watch?v=R-KWeKTVCJo
package computer_networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebClient {
	public static void main(String arg[]) {
		try {
			Socket socket = new Socket("127.0.0.1", 8889);
			BufferedReader ir;

			PrintStream printstream = new PrintStream(socket.getOutputStream());

			InputStreamReader is = new InputStreamReader(socket.getInputStream());
			ir = new BufferedReader(is);

			// send request
			printstream.println("GET /mydoc.html HTTP/1.0\r\n\r\n");
			printstream.println();
			String contents = null;
			// reading the contents from the printstream object
			// this code is referred from stackoverflow.com
			do {
				contents = ir.readLine();
				System.out.println(contents);
			} while (contents != null);
			printstream.flush();
			// Close socket.
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
