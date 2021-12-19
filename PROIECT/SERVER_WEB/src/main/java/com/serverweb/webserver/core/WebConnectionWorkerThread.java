package com.serverweb.webserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder request = new StringBuilder();
            String inputLine = br.readLine();

            while (!inputLine.isBlank()) {
                    /*    System.out.println("Server: " + inputLine);
                        out.println(inputLine);*/
                request.append(inputLine + "\r\n");
                inputLine = br.readLine();
            }

            //System.out.println(request);

            String firstLine = request.toString().split("\n")[0];
            String resource = firstLine.split(" ")[1];

//            int _byte ;
//
//            while ( (_byte = inputStream.read() ) >= 0 ){
//                System.out.print((char)_byte);
//            }

            //String html = "src/main/resources/TestSite/a.html";
            //String html = "<html> <head>  <title>Welcome!</title>  </head> <body><h1> My page </h1> </body> </html>";

            String html_general =  "<html>\n" +
                    "<head>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<a href=\"/a.html\">back</a>\n" +
                    "</body>";

            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF +   //status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + html_general.getBytes().length + CRLF  +   //HEADER
                            CRLF +
                            html_general +
                            CRLF + CRLF ;

            switch(resource) {
                case "/hello": {
                    String hello =  "<html> <head>  <title>Hello Page!</title>  </head> <body><h1> Hellooooooo! </h1> </body> </html>";
                    String response_hello =
                            "HTTP/1.1 200 OK" + CRLF +   //status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                    "Content-Length: " + hello.getBytes().length + CRLF  +   //HEADER
                                    CRLF +
                                    hello +
                                    CRLF + CRLF ;

                    outputStream.write(response_hello.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/maintenance" : {
                    String maintenence_html = "<!doctype html>\n" +
                            "<title>Site Maintenance</title>\n" +
                            "<style>\n" +
                            "  body { text-align: center; padding: 150px; }\n" +
                            "  h1 { font-size: 50px; }\n" +
                            "  body { font: 20px Helvetica, sans-serif; color: #333; }\n" +
                            "  article { display: block; text-align: left; width: 650px; margin: 0 auto; }\n" +
                            "  a { color: #dc8100; text-decoration: none; }\n" +
                            "  a:hover { color: #333; text-decoration: none; }\n" +
                            "</style>\n" +
                            "\n" +
                            "<article>\n" +
                            "    <h1>We&rsquo;ll be back soon!</h1>\n" +
                            "    <div>\n" +
                            "        <p>Sorry for the inconvenience but we&rsquo;re performing some maintenance at the moment. If you need to you can always contact us, otherwise we&rsquo;ll be back online shortly!</p>\n" +
                            "        <p>&mdash; The Team</p>\n" +
                            "    </div>\n" +
                            "</article>" ;

                    String response_maintenence =
                            "HTTP/1.1 200 OK" + CRLF +   //status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                    "Content-Length: " + maintenence_html.getBytes().length + CRLF  +   //HEADER
                                    CRLF +
                                    maintenence_html +
                                    CRLF + CRLF ;

                    outputStream.write(response_maintenence.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/a.html": {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/b.html": {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/c.html": {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/aaa/b.html": {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/aaa/bbb/c.html": {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                    break;
                }

                case "a%20b.html" : {
                    outputStream.write(response.getBytes());
                    outputStream.flush();
                    break;
                }

                case "/first":  {

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

                    String response_def =
                            "HTTP/1.1 200 OK" + CRLF +   //status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                    "Content-Length: " + html.getBytes().length + CRLF  +   //HEADER
                                    CRLF +
                                    html +
                                    CRLF + CRLF ;

                    outputStream.write(response_def.getBytes());
                    outputStream.flush();
                    break;

                }

                default :  {
                    String error_case = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\" dir=\"ltr\">\n" +
                            "   <head>\n" +
                            "      <meta charset=\"utf-8\">\n" +
                            "      <title>404 Error Page </title>\n" +
                            "      <link rel=\"stylesheet\" href=\"style.css\">\n" +
                            "   </head>\n" +
                            "   <body>\n" +
                            "      <div id=\"error-page\">\n" +
                            "         <div class=\"content\">\n" +
                            "            <h2 class=\"header\" data-text=\"404\">\n" +
                            "               404\n" +
                            "            </h2>\n" +
                            "            <h4 data-text=\"Opps! Page not found\">\n" +
                            "               Opps! Page not found\n" +
                            "            </h4>\n" +
                            "            <p>\n" +
                            "               Sorry, the page you're looking for doesn't exist. If you think something is broken, report a problem.\n" +
                            "            </p>\n" +
                            "         </div>\n" +
                            "      </div>\n" +
                            "   </body>\n" +
                            "</html>";

                    String response_error =
                            "HTTP/1.1 200 OK" + CRLF +   //status line : HTTP VERSION RESPONSE_CODE RESPONSE_MESSAGE
                                    "Content-Length: " + error_case.getBytes().length + CRLF  +   //HEADER
                                    CRLF +
                                    error_case +
                                    CRLF + CRLF ;

                    outputStream.write(response_error.getBytes());
                    outputStream.flush();
                    break;
                }


            }

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
