package com.bridgelabz;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
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

    public static class EchoGetHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            HashMap<String, Object> parameters = new HashMap<>();
            URI requestURI = exchange.getRequestURI();
            String rawQuery = requestURI.getRawQuery();
            parseQuery(rawQuery, parameters);
            String response = "";
            for (String key : parameters.keySet()) response += key + " = " + parameters.get(key) + "\n";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream outputStream = exchange.getResponseBody();
            outputStream.write(response.getBytes());
            outputStream.close();
        }

        private static void parseQuery(String query, HashMap<String, Object> parameters) throws UnsupportedEncodingException {
            if (query != null) {
                String[] pairs = query.split("[&]");
                for (String pair : pairs) {
                    String[] param = pair.split("[=]");
                    String key=null;
                    String value = null;
                    if (param.length > 0) key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
                    if (param.length > 1) {
                        value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
                    }
                    parameters.put(key, value);
                }
            }
        }
    }
}
