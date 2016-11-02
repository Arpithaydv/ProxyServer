/*
 * responseponse_Http to handle http reply and return with reply status codes
 * Author : Arpitha Anand (1001256428)
 */
import java.io.*;
public class Response_Http {
	final static String new_line = "\r\n";
    // Read Buffer
    final static int BUFFER_SIZE = 8192;
    // max size of obj that proxy server can handle can be changed now set to 200KB
    final static int MAX_OBJ_SIZE = 200000;
    /** Reply status and headers */
    String ver;
    int status;
    String status_Line = "";
    String header = "";
    //array to hold the body of reply
    byte[] response_body = new byte[MAX_OBJ_SIZE];

    // read the response
   	@SuppressWarnings("deprecation")
	public Response_Http(DataInputStream frm_Server) {
	int clength = -1;
	boolean getStatusLine = false;

	// First read status line and response headers
	try {
		// read input stream from server and process response

	    String line =frm_Server.readLine();
	    while (line.length() != 0) {
		if (!getStatusLine) {
		    status_Line = line;
		    getStatusLine = true;
		} else {
		    header += line + new_line;
		}

		/* length of content is got by reading the
		 * Content-Length or Content-length returned
		 * in the header of the response form server */
		if (line.startsWith("Content-Length:") ||
			    line.startsWith("Content-length:")) {
			    String[] temp_val = line.split(" ");
			    clength = Integer.parseInt(temp_val[1]);
			}
			line = frm_Server.readLine();
		    }
		} catch (IOException e) {
		}
	try {
	    int bytes_Read = 0;
	    byte buffer[] = new byte[BUFFER_SIZE];
	    boolean loop_end = false;

	    /* If we didn't get Content-Length header, just loop until
	     * the connection is closed. */
	    if (clength == -1) {
		loop_end = true;
	    }
	    /* BUFFER_SIZE is used as chunk size to read the body and write
	     * into the body.
	     * While loop ends iteration on hitting end of Content-Length
	     * or connection is closed */

	    while (bytes_Read < clength || loop_end) {
		int response = frm_Server.read(buffer, 0, BUFFER_SIZE);
		if (response == -1) {
		    break;
		}
		//Copy the bytes into body and not exceed maximum object size
		for (int i = 0;
		     i < response && (i + bytes_Read) < MAX_OBJ_SIZE;
		     i++) {
		    response_body[bytes_Read + i] = buffer[i]; // copy the read bytes to the body
		}
		bytes_Read += response;
	    }
 	} catch (IOException e) {
 	    System.out.println("Reading response ERROR" + e);
 	    return;
 	}


    }
    // convert response header to string, excluding body for easy sending
    public String toString() {
	String response = "";

	response = status_Line + new_line;
	response += header;
	response += new_line;

	return response;
    }


}
