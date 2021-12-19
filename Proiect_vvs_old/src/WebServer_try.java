
import java.net.*;
import java.io.*;

public class WebServer_try extends Thread {


    public static void main(String[] args) throws Exception {

        try (ServerSocket serverSocket = new ServerSocket(10008)) {
            System.out.println("Connection Socket Created");

            while (true) {

                try (Socket clientSocket = serverSocket.accept()) {

                    //System.out.println("New Communication Thread Started for " + clientSocket.toString());

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    //  InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    StringBuilder request = new StringBuilder();
                    String inputLine = br.readLine();

                    while (!inputLine.isBlank()) {

                    /*    System.out.println("Server: " + inputLine);
                        out.println(inputLine);*/

                        request.append(inputLine + "\r\n");
                        inputLine = br.readLine();
                    }
                    System.out.println(request);

                    String firstLine = request.toString().split("\n")[0];
                    String resource = firstLine.split(" ")[1];

                    switch (resource) {
                        case "/a": {
                            OutputStream clientOutput = clientSocket.getOutputStream();
                            clientOutput.write(("HTTP/1.1 200 OK\r\n").getBytes());
                            clientOutput.write(("\r\n").getBytes());
                            clientOutput.write(("Hello world").getBytes());
                            clientOutput.flush();
                            System.out.println("case 1");
                            break;
                        }

                        case "/ab.html":
                            System.out.println("case *");
                            break;
                        //samd
                        default:
                            break;
                    }
                }
            }
        }
    }

}