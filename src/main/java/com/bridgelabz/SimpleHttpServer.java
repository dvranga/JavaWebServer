package com.bridgelabz;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class SimpleHttpServer {
    public static int DEFAULT_PORT=9000;
    public static int port;
    private HttpServer httpServer;

    public static void main(String[] args) {
        SimpleHttpServer httpServer = new SimpleHttpServer();
        httpServer.start(DEFAULT_PORT)
    }

    private void start(int port) {
        this.port=port;
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port),0);
            System.out.println("server started at "+port);
            httpServer.createContext("/",new Handlers.RootHandler());
            httpServer.createContext("/echoHeader", new Handlers.EchoHeaderHandler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
