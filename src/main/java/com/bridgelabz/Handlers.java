package com.bridgelabz;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Handlers {

    public static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response="<h1>Server start success if you see this message</h1>" + "<h1>Port"+SimpleHttpServer.port+"</h1>";
            exchange.sendResponseHeaders(200,response.length());
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(response.getBytes());
            responseBody.close();
        }
    }

    public static class EchoHeaderHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            Headers requestHeaders = exchange.getRequestHeaders();
            Set<Map.Entry<String, List<String>>> entries=requestHeaders.entrySet();
            String response = "";
            for (Map.Entry<String, List<String>> entry : entries) response += entry.toString() + "\n";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }
    }
}
