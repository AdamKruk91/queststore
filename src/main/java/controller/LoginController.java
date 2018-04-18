package controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.LoginDao;
import view.LoginView;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;


public class LoginController implements HttpHandler {

    private LoginView view;


    public LoginController() {
        view = new LoginView();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> inputs = parseFormData(formData);
            String login = inputs.get("login");
            String password = inputs.get("password");
            String userStatus = logInUser(login, password);
            switch(userStatus) {
                case "Admin":
                    // todo
                    break;
                case "Mentor":
                    // todo
                    break;
                case "Student":
                    StudentController studentController = new StudentController();
                    Headers responseHeaders = httpExchange.getResponseHeaders();
                    responseHeaders.set("Location", "/student");
                    httpExchange.sendResponseHeaders(302,-1);
                    break;
                case "Default":
                    response = view.getWrongLoginScreen();
                    httpExchange.sendResponseHeaders(200, response.length());
                    break;
            }
        } else if (method.equals("GET")) {
            response = view.getLoginScreen();
            httpExchange.sendResponseHeaders(200, response.length());
        }

            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }


    private String logInUser(String login, String password) {
        LoginDao loginDao = new LoginDao();
        int idStatus = loginDao.findStatusId(login, password);
        String userStatus = loginDao.findStatus(idStatus);
        return userStatus;
    }

    private int getLoginId(String login, String password) {
        LoginDao loginDao = new LoginDao();
        return loginDao.findLoginId(login, password);
    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            new URLDecoder();
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    public void startApplication() {
        InputController inputController = new InputController();
        String login = inputController.getStringInput("Enter login: ");
        String password = inputController.getStringInput("Enter password: ");
        String userStatus = logInUser(login, password);
        if (userStatus.equals(0)) {
            System.out.println("Wrong login or password! ");
            System.exit(0);
        }
        int loginId = getLoginId(login, password);
        displayUserMenu(loginId, userStatus);
    }


    private void displayUserMenu(int loginId, String statusName) {
//        view.displayWelcomeMessage();
        switch (statusName) {
            case "Admin":
                AdminController adminController = new AdminController();
                adminController.controlMenuOptions();
                break;
            case "Mentor":
                MentorController mentorController = new MentorController();
                mentorController.controlMenuOptions();
                break;
            case "Student":
                StudentController studentController = new StudentController();
                studentController.controlMenuOptions(loginId);
                break;
        }
    }
}

