package controller;


import com.sun.net.httpserver.HttpServer;
import view.Static;

import java.net.InetSocketAddress;

public class RootController {

    public static void main(String[] args) {


        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            server.createContext("/login", new LoginController());
            server.createContext("/static", new Static());
            server.createContext("/student", new StudentController());
            server.createContext("/logout", new LogoutController());
            server.createContext("/admin", new AdminController());
            server.setExecutor(null);

            server.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
   }
}


