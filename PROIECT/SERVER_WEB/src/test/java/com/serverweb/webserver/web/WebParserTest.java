package com.serverweb.webserver.web;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WebParserTest {

    private WebParser webParser;

    @BeforeAll
    public void beforeClass() {
        webParser =  new WebParser();
    }

    @org.junit.jupiter.api.Test
    void parseRequest() {
        webParser.parseRequest(
              generateValidTestCase()
        );
    }

    private InputStream generateValidTestCase() {
        String request = "GET / HTTP/1.1\r\n" +
                "Host: 127.0.0.1:10008\n\n" +
                "Connection: keep-alive\n\n" +
                "Cache-Control: max-age=0\n\n" +
                "sec-ch-ua: \"Chromium\";v=\"94\", \"Google Chrome\";v=\"94\", \";Not A Brand\";v=\"99\"\n\n" +
                "sec-ch-ua-mobile: ?0\n\n" +
                "sec-ch-ua-platform: \"Linux\"\n\n" +
                "Upgrade-Insecure-Requests: 1\n\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36\n\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\n\n" +
                "Sec-Fetch-Site: none\n\n" +
                "Sec-Fetch-Mode: navigate\n\n" +
                "Sec-Fetch-User: ?1\n\n" +
                "Sec-Fetch-Dest: document\n\n" +
                "Accept-Encoding: gzip, deflate, br\n\n" +
                "Accept-Language: en-US,en;q=0.9,ro;q=0.8\r\n" +
                "\r\n" ;

        InputStream inputStream = new ByteArrayInputStream(
                request.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}