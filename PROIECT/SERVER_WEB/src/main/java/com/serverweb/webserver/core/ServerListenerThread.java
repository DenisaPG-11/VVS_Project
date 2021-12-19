package com.serverweb.webserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private int port;
    private String Webroot;
    private ServerSocket serverSocket;
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        Webroot = webroot;

        this. serverSocket = new ServerSocket(this.port);

    }

    @Override
    public void run() {

        try {

            while(serverSocket.isBound() && !serverSocket.isClosed()){

                Socket socket = serverSocket.accept();

                LOGGER.info("Connection accepted: " + socket.getInetAddress());

                WebConnectionWorkerThread webConnectionWorkerThread = new WebConnectionWorkerThread(socket);
                webConnectionWorkerThread.start();

            }

        } catch (IOException e) {
            LOGGER.error("Detected a problem with setting socket ", e);
        } finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
