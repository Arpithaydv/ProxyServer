------------------CN-1 Project 1 –Web Proxy Server--------------------
Name: Arpitha Anand	
Student ID: 1001256428
--------------------------------------------------------------------------------
--> Language Used : Java
--> Platform : Eclipse IDE

Program Module -->
Web_Proxy_Server
	--> Proxy_Threads.java (implements threads for multithreading)
	--> Request_Http.java
	--> Response_Http.java
	--> Web_Proxy_Server.java (main)

--> Proxy server is designed to run on port 8080 (changes can be made in the program if needed)

----------------------------------------------------------------------------------
Steps to Run : View Output_screenshots.doc for reference

- Step1: compile all the java files (javac *.java)
- Step2: run the java program (java ProxyServ 8080)
- Step3: Configure your browser proxy settings
- Step4: Open a site with http protocol (ex: http://www.msn.com)
- Step5: The code automatically creates a cache folder, also creates proxy_log.txt which logs the results

--------------------------------------------------------------------------------------

--> NOTE :
1. Due to the browser's incapability to handle objects of differnt format and send them across 
	a nullPointerException is thrown on the console. ( program is limited and desinged in such manner)
2. Try oly http:// websites for better results
3. Consider the proxy_log.txt file to check for cache hit and cache miss cases.

----------------------------------------------------------------------------------------

--> Sources :
 The main source of the entire project were extensive google search , you can find parts of the code implemented from other source. I hereby Cite them.





