/*
 * Main program to run the proxy server functionalities
 * Author: Arpitha Anand (1001256428)
 */
import java.net.*;
import java.io.*;
import java.util.*;

public class Web_Proxy_Server {
	// proxy server port
    private static int port;
    // socket to connect to
    private static ServerSocket socket;
    // create proxy cache to cache requests and response thru proxy
    private static Map<String, String> cache = new Hashtable<String, String>();


    public synchronized static void caching(Request_Http request, Response_Http response) throws IOException{
    	//create file object
    	File file_obj1;
    	//output
    	DataOutputStream output;

    	//
    	file_obj1 = new File("cache/","cached@_"+System.currentTimeMillis());
    	output = new DataOutputStream( new FileOutputStream(file_obj1));
    	// write header
    	output.writeBytes(response.toString());
    	//write body
    	output.write(response.response_body);
    	output.close();
    	cache.put(request.URL, file_obj1.getAbsolutePath());
    	System.out.println("Caching from: "+request.URL+" para "+file_obj1.getAbsolutePath());

    }
    @SuppressWarnings("resource")
	public synchronized static byte[] uncaching(String cache_hit) throws IOException{
  		File file_obj2;
  		FileInputStream file_obj2_input;
  		String cachehash;
  		byte[] bytescached;

  		if((cachehash = cache.get(cache_hit))!=null){
  			file_obj2 = new File(cachehash);
  			file_obj2_input = new FileInputStream(file_obj2);
  			bytescached = new byte[(int)file_obj2.length()];
  			file_obj2_input.read(bytescached);
  			System.out.println("Cache HIT "+cache_hit+" returning cache to user");
  			return bytescached;
  		}
  		else {
  			System.out.println("Cache MISS on "+cache_hit);
  			return bytescached = new byte[0];
  		}

  	}
    public static void init(int p) {
    	port = p;
    		try {
    	    	socket = new ServerSocket(port);
    		} catch (IOException e) {
    	    		System.out.println("Error creating socket: " + e);
    	    		System.exit(-1);
    			}
      	}

   // parse command line attributes and starts proxy
    public static void main(String args[]) {
	int myPort = 0;

	// this will print the output to a log file
		PrintStream op;
		try {
			op = new PrintStream(new FileOutputStream("proxy_log.txt"));
			System.setOut(op);
		} catch (FileNotFoundException e1) {
			// blank
			e1.printStackTrace();
		}

		File cache_dir = new File("cache/");
		if (!cache_dir.exists()){cache_dir.mkdir();}

		try {

		// parse port number from command line
		    myPort = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
		    System.out.println("Input format : Java Web_Proxy_Server myPort");
		    System.exit(-1);
		} catch (NumberFormatException e) {
		    System.out.println("Port number should be a integer");
		    System.exit(-1);
		}
		init(myPort);
		// listen to requests over socket and create new thread for handling them
		Socket client = null;

		while (true) {
		    try {
		    	// client accept
			client = socket.accept();
			// create new thread to handle every client
			(new Thread(new Proxy_Threads(client))).start();
		    } catch (IOException e) {
			System.out.println("ERROR : cannot read request from client " + e);
			// skip this request and continue ahead.
			continue;
		    }
		}

	    }
}
