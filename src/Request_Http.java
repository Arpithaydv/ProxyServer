/*
 * Request_Http uses protocol HTTP request to request for a file
 * AUTHOR : Arpitha Anand 1001256428
 */

import java.io.*;
public class Request_Http {
	// variable to declare a new line carriage return and port number
	final static String new_line = "\r\n";
	final static int port_http = 80;
	// hold the http request parameters
	String method;
	String URL;
	String ver;
	String header = "";
	// specify server port and host it is generated from
	private String host;
	private int port;

	//Create HttpRequest by reading it from the client socket

	public Request_Http (BufferedReader frm) {
		String lineOne = "";
		try {
			lineOne = frm.readLine();
		} catch (IOException e) {
			System.out.println("Request Line" + e + "not read || ERROR ||");
		}
		String[] temp_val = lineOne.split(" ");

		/* GET = GET http://www.google.pt HTTP/1.0 format of get structuring */
		method = temp_val[0]; /* method GET */
		URL =  temp_val[1]; /* URL */
		ver =  temp_val[2]; /* HTTP version */

		System.out.println("URL requested is: " + URL);

		if (!method.equals("GET")) {
			// do nothing
		}
		try {
			String line = frm.readLine();
			while (line.length() != 0) {
				header += line + new_line;
				/* Just in case URL is not complete consider
				 * checking Host header to know which server to contact to */
				if (line.startsWith("Host:")) {
					temp_val = line.split(" ");
					if (temp_val[1].indexOf(':') > 0) {
						String[] temp_val2 = temp_val[1].split(":");
						host = temp_val2[0];
						port = Integer.parseInt(temp_val2[1]);
					} else {
						host = temp_val[1];
						port = port_http;
					}
				}
				line = frm.readLine();
			}
		} catch (IOException e) {
			System.out.println("Socket read from" + e + "failed");
			return;
		}
		System.out.println("Host to connect to" + host );
	}

	// host which is requested to returned on getHost()
	public String getHost() {
		return host;
	}

	// port on which the access is to be made returned on getPort()
	public int getPort() {
		return port;
	}
	// Convert request to string and format for easier re-sending purpose
	public String toString() {
		String request = "";

		request = method + " " + URL + " " + ver + new_line;
		request += header;
		request += "Connection: close" + new_line;
		request += new_line;

		return request;
	}

}
