package com.serverweb.webserver.web;

import com.serverweb.web.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WebParserTest {

    private WebParser webParser;

    @BeforeAll
    public void beforeClass() {
        webParser =  new WebParser();
    }

    @Test
    void parseRequest() {
        WebRequest request = null;
        try {
            request = webParser.parseRequest(
                        generateValidGETTestCase()
            );
        } catch (ParsingException e) {
            fail(e);
        }

        assertEquals(request.getMethod(), WebMethod.GET);
    }

    @Test
    void parseRequestBadMethodTest() {
        try {
            WebRequest request = webParser.parseRequest(
                    generateBadValueTestCase1()
            );
            fail();
        } catch (ParsingException e) {
            assertEquals(e.getErrorCode(), StatusCode.SERVER_ERROR_501_METHOD_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseRequestBadNameTest() {
        try {
            WebRequest request = webParser.parseRequest(
                    generateBadValueTestCase2()
            );
            fail();
        } catch (ParsingException e) {
            assertEquals(e.getErrorCode(), StatusCode.SERVER_ERROR_501_METHOD_NOT_IMPLEMENTED);
        }
    }

    @Test
    void parseRequestBadTestInvalidNumberOfItem() {
        try {
            WebRequest request = webParser.parseRequest(
                    generateBadTestCaseInvalidNumOfItem()
            );
            fail();
        } catch (ParsingException e) {
            assertEquals(e.getErrorCode(), StatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseRequestBadTestEmptyLine() {
        try {
            WebRequest request = webParser.parseRequest(
                    generateBadTestCaseEmptyRequestLine()
            );
            fail();
        } catch (ParsingException e) {
            assertEquals(e.getErrorCode(), StatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    @Test
    void parseRequestBadTestNoLFInRequestLine() {
        try {
            WebRequest request = webParser.parseRequest(
                    generateBadTestCaseInvalidNumOfItemLFMissing()
            );
            fail();
        } catch (ParsingException e) {
            assertEquals(e.getErrorCode(), StatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
    }

    //perfect string request
    private InputStream generateValidGETTestCase() {
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

    //wrong string request
    private InputStream generateBadValueTestCase1() {
        String request = "GeT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                request.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //wrong name in the string request
    private InputStream generateBadValueTestCase2() {
        String request = "GETTTTTTTTTTTTTTTTTT / HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                request.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //more than three item
    private InputStream generateBadTestCaseInvalidNumOfItem() {
        String request = "GET / AAAAAAAAAAA HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                request.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //empty request lline
    private InputStream generateBadTestCaseEmptyRequestLine() {
        String request = "\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                request.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }

    //no LF in request line
    private InputStream generateBadTestCaseInvalidNumOfItemLFMissing() {
        String request = "GET / HTTP/1.1\r" +
                "Host: localhost:8080\r\n" +
                "Accept-Language: en-US,en;q=0.9,es;q=0.8,pt;q=0.7,de-DE;q=0.6,de;q=0.5,la;q=0.4\r\n" +
                "\r\n";

        InputStream inputStream = new ByteArrayInputStream(
                request.getBytes(
                        StandardCharsets.US_ASCII
                )
        );

        return inputStream;
    }
}