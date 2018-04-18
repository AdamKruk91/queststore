package controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.LoginDao;
import view.LoginView;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;
import java.util.UUID;


public class LoginController implements HttpHandler {

    private LoginView view;
    private LoginDao loginDao;
    public static Map<String,Integer> loggedInUsers;

    public LoginController() {
        view = new LoginView();
        loginDao = new LoginDao();
        loggedInUsers = new HashMap<>();

    }

    public void handle(HttpExchange httpExchange) throws IOException {

        if(isCookieValid(httpExchange)) {
           renderWithCookie(httpExchange);
        } else {
            renderWithoutCookie(httpExchange);
        }
    }

    private boolean isCookieValid(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;

        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            if(loggedInUsers.containsKey(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

    private void renderWithCookie(HttpExchange httpExchange) throws IOException {

        int loginID = getLoginIdFromCookie(httpExchange);
        String userType = loginDao.findStatusByLoginId(loginID);

        switch (userType) {
            case "Admin":
                // todo
                break;
            case "Mentor":
                // todo
                break;
            case "Student":
                redirectTo(httpExchange, "/student");
                break;
        }
    }

    private int getLoginIdFromCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        cookie = HttpCookie.parse(cookieStr).get(0);
        return loggedInUsers.get(cookie.getValue());
    }

    private void renderWithoutCookie(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {

            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map<String, String> inputs = parseFormData(formData);
            String login = inputs.get("login");
            String password = inputs.get("password");
            int loginID = loginDao.findLoginId(login, password);
            createCookie(httpExchange, loginID);
            renderWithCookie(httpExchange);
        } else if (method.equals("GET")) {
            response = view.getLoginScreen();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private void createCookie(HttpExchange httpExchange, int loginID) throws IOException {
        final UUID uuid = UUID.randomUUID();
        HttpCookie cookie = new HttpCookie("sessionId", uuid.toString());
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        loggedInUsers.put(uuid.toString(), loginID);
        redirectTo(httpExchange,"/student");

    }

    private void redirectTo(HttpExchange httpExchange, String URI) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", URI);
        httpExchange.sendResponseHeaders(302, -1);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    private String logInUser(String login, String password) {
        LoginDao loginDao = new LoginDao();
        int idStatus = loginDao.findStatusId(login, password);
        if (idStatus == 0) {
            view.displayLoginFailed();
            System.exit(0);
        }
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

