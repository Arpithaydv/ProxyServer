
/*This class is to create threads and handle multiple requests
 * Author : Arpitha Anand (1001256428)
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Proxy_Threads implements Runnable{

		private final Socket client;

	    public Proxy_Threads(Socket client) {
	        this.client = client;
	    }
	    public void run() {
	    	Socket server = null;
	    	Request_Http req = null;
	    	Response_Http res = null;

	    	//request is processed if any exceptions it is ended - Request_http
	    	/* request to read */
	    	try {
	    	    BufferedReader frm_Client = new BufferedReader(new InputStreamReader(client.getInputStream()));
	    	    req = new Request_Http(frm_Client);
	    	} catch (IOException e) {
	    	    System.out.println("Reading request from Client ERROR" + e);
	    	    return;
	    	}
	    	// send request to server from client
	    	try {
	    	    //socket created from client to server and sent over it
	    		//1. Create Socket
	    	    server = new Socket(req.getHost(), req.getPort());
	    	    //2. Create Output stream
	    	    DataOutputStream to_Server = new DataOutputStream(server.getOutputStream());
	    	    //3. write request to server
	            to_Server.writeBytes(req.toString());
	    	} catch (UnknownHostException e) {
	    	    System.out.println("host UNKNOWN " + req.getHost());
	    	    System.out.println(e);
	    	    return;
	    	} catch (IOException e) {
	    	    return;
	    	}
	    	// send response form Server to client
	    	try {
	    		byte[] cache = Web_Proxy_Server.uncaching(req.URL);
	    		if(cache.length==0) {
	    			//server input stream
	    		    DataInputStream frm_Server = new DataInputStream(server.getInputStream());
	    		    // response creation
	    		    res = new Response_Http(frm_Server);
	    		    DataOutputStream to_Client = new DataOutputStream(client.getOutputStream());


	    		    // headers
	    		    to_Client.writeBytes(res.toString());
	    		    //body , written following an order of header first and then body
	    		    to_Client.write(res.response_body);
	    		    // cache it if already not available
	    		    Web_Proxy_Server.caching(req, res);

	    		    client.close();
	    		    server.close();
	    		}
	    		else{
	    			DataOutputStream to_Client = new DataOutputStream(client.getOutputStream());
	    			to_Client.write(cache);
	    			client.close();
	    			server.close();
	    		}

	    	} catch (IOException e) {
	    	    System.out.println("ERROR writing response to Client" + e);
	    	}
	        }


}
