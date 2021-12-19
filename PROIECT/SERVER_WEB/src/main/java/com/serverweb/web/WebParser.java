package com.serverweb.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class WebParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebParser.class);

    private static final int SP = 0x20; //ASCII FOR 32
    private static final int CR = 0x0D; //ASCII FOR 13
    private static final int LF = 0x0A; //ASCII FOR 10

    public WebRequest parseRequest (InputStream inputStream) throws ParsingException {

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
        WebRequest webRequest = new WebRequest();

        try {
            parseRequestLine(inputStreamReader, webRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseRequestHeaders(inputStreamReader, webRequest);
        parseRequestBody(inputStreamReader, webRequest);

        return webRequest;
    }

    private void parseRequestBody(InputStreamReader inputStreamReader, WebRequest webRequest) {

    }

    private void parseRequestHeaders(InputStreamReader inputStreamReader, WebRequest webRequest) {

    }

    private void parseRequestLine(InputStreamReader inputStreamReader, WebRequest webRequest) throws IOException, ParsingException {

        StringBuilder stringBuilder = new StringBuilder();
        int _byte;
        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        while( (_byte = inputStreamReader.read() ) >= 0 ){
            if (_byte == CR) {
                _byte = inputStreamReader.read();
                if (_byte == LF) {
                    LOGGER.debug("request line version to process: {}", stringBuilder.toString());
                    if ( !methodParsed || !requestTargetParsed) {
                        throw new ParsingException(StatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }

                    return;
                }
            }

            if (_byte == SP) {
                if( !methodParsed){
                    LOGGER.debug("request line method to process: {}", stringBuilder.toString());
                    webRequest.setMethod(stringBuilder.toString());
                    methodParsed = true;
                } else {
                    if (!requestTargetParsed) {
                        LOGGER.debug("request line request target to process: {}", stringBuilder.toString());
                        requestTargetParsed = true;
                    } else {
                        throw new ParsingException(StatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                }
                LOGGER.debug("request line to process: {}", stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append((char)_byte);
                if(!methodParsed) {
                    if(stringBuilder.length() > WebMethod.MAXIM_LENGTH){
                        throw new ParsingException(StatusCode.SERVER_ERROR_501_METHOD_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }
}
