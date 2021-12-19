package com.serverweb.webserver;

import com.serverweb.webserver.config.Configuration;
import com.serverweb.webserver.config.ConfigurationManager;
import com.serverweb.webserver.core.ServerListenerThread;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* You will find an eclipse project attached to this page: Attach:webserver.zip. It contains a WebServer class which answers its clients with the text of the received request.
*  In order to access it, build and start the server, and open address http://locahost:10008 or http://127.0.0.1:10008 in your browser.
* WORKING : http://127.0.0.1:10008/ !!!
* */
public class ServerWeb {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerWeb.class);

    public static void main(String[] args) {

        LOGGER.info("Server starting ...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/web.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + conf.getPort());
        LOGGER.info("Using Webroot: " + conf.getWebroot() );

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
