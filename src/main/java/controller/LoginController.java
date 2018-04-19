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


public class LoginController extends AbstractContoller implements HttpHandler {

    private LoginView view;
    private LoginDao loginDao;
    public static Map<String,Integer> loggedInUsers;

    public LoginController() {
        view = new LoginView();
        loginDao = new LoginDao();
        loggedInUsers = new HashMap<>();

    }

    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieValid(httpExchange)) {
                renderWithCookie(httpExchange);
            } else {
                renderWithoutCookie(httpExchange);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    private void renderWithCookie(HttpExchange httpExchange) throws IOException, SQLException {

        int loginID = getLoginIdFromCookie(httpExchange);
        String userType = loginDao.findStatusByLoginId(loginID);

        switch (userType) {
            case "Admin":
                redirectTo(httpExchange, "/admin");
                break;
            case "Mentor":
                // todo
                break;
            case "Student":
                redirectTo(httpExchange, "/student");
                break;
        }
    }

    private void renderWithoutCookie(HttpExchange httpExchange) throws IOException, SQLException {
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

    private String logInUser(String login, String password) throws SQLException {
        LoginDao loginDao = new LoginDao();
        int idStatus = loginDao.findStatusId(login, password);
        String userStatus = loginDao.findStatus(idStatus);
        return userStatus;
    }

    private int getLoginId(String login, String password) throws SQLException {
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

    public void startApplication() throws SQLException {
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


    private void displayUserMenu(int loginId, String statusName) throws SQLException {
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

