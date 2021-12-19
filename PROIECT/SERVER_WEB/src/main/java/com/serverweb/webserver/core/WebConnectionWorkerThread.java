package com.serverweb.webserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLOutput;

public class WebConnectionWorkerThread extends Thread{

    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(WebConnectionWorkerThread.class);

    public WebConnectionWorkerThread (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

//            int _byte ;
//
//            while ( (_byte = inputStream.read() ) >= 0 ){
//                System.out.print((char)_byte);
//            }

            //String html = "src/main/resources/TestSite/a.html";
            //String html = "<html> <head>  <title>Welcome!</title>  </head> <body><h1> My page </h1> </body> </html>";

        String html = "<html>\n" +
                    "<head>\n" +
                    " <title>Welcome!</title>\n" +
                    " </head>\n" +
                    " <body BGCOLOR=\"#FFFFFF\" leftMargin=0 topMargin=0 rightMargin=0 marginheight=\"0\" marginwidth=\"0\">\n" +
                    "  <center>\n" +
                    "   Hello, It works !!! <br />\n" +
                    "   <table>\n" +
                    "   \n" +
                    "   <tr><td>  <a href=\"http://google.com\">do external links work?</a>\n" +
                    "   </td><td> <img src=\"http://www.loose.upt.ro/~mihai/yes.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "   \n" +
                    "   <tr><td>  <a href=\"b.html\">do simple relative internal links work?</a> <br />\n" +
                    "   </td><td> <img src=\"yes.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "   <tr><td>  <a href=\"aaa/b.html\">do general relative links work</a> <br />\n" +
                    "   </td><td> <img src=\"aaa/yes.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "   <tr><td>  <a href=\"/c.html\">do simple absolute links work</a> <br />\n" +
                    "   </td><td> <img src=\"/yes.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "   <tr><td>  <a href=\"/aaa/bbb/c.html\">do general absolute links work</a> <br />\n" +
                    "   </td><td> <img src=\"/aaa/bbb/yes.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "   <tr><td>  <a href=\"a b.html\">do URLs with spaces work</a> <br />\n" +
                    "   </td><td> <img src=\"ye s.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "   <tr><td>  <a href=\"a%20b.html\">do URLs with %20 work</a> <br />\n" +
                    "   </td><td> <img src=\"ye%20s.jpg\" height=\"30px\" />\n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "   <tr><td>  <a href=\"a.txt\">do TXT files work</a> <br />\n" +
                    "   </td><td> \n" +
                    "   </td></tr>\n" +
                    "\n" +
                    "  </center>\n" +
                    "</body>\n" +
                    "</html>";

            final String CRLF = "\n\r";
            String response =
                    "HTTP/1.1 200 OK" + CRLF +   //status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html.getBytes().length + CRLF  +   //HEADER
                            CRLF +
                            html +
                            CRLF + CRLF ;

            outputStream.write(response.getBytes());

            LOGGER.info("Connection processing finished");

        } catch (IOException e) {
            LOGGER.error("Detected a problem with the comunication ", e);
        } finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
