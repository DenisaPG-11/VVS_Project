package com.serverweb.webserver.config;

/*
* singleton - need just one configuration manager
* */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.serverweb.webserver.ui.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager(){

    }

    public static ConfigurationManager getInstance() {

        if ( myConfigurationManager == null )
            myConfigurationManager = new ConfigurationManager();

        return myConfigurationManager;
    }

    public void loadConfigurationFile (String filePath) {

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new WebConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();

        int i;
        try {
            while(  (i = fileReader.read() ) != -1) {
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new WebConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new WebConfigurationException("Error parsing the configuration file", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new WebConfigurationException("Error parsing the configuration file, internal", e);
        }
    }

    public Configuration getCurrentConfiguration () {
        if ( myCurrentConfiguration ==  null){
            throw new WebConfigurationException("No current configuration set!");
        } else
        {
            return myCurrentConfiguration;
        }
    }
}
