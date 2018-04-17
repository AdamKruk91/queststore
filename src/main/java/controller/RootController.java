package controller;


import com.sun.net.httpserver.HttpServer;
import view.Static;

import java.net.InetSocketAddress;

public class RootController {

    public static void main(String[] args) {

//        new UserController().startApplication();

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/login", new UserController());
            server.createContext("/static", new Static());
            server.setExecutor(null);

            server.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}


