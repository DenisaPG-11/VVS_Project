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
            String html = "<html> <head>  <title>Welcome!</title>  </head> <body><h1> My page </h1> </body> </html>";

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
