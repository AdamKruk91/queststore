package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.LoginDAOSQL;
import exceptions.DataAccessException;
import view.LoginView;

import java.io.*;
import java.util.*;


public class LoginController extends AbstractContoller implements HttpHandler {

    private LoginView view;
    private LoginDAOSQL loginDao;

    public LoginController() {
        view = new LoginView();
        loginDao = new LoginDAOSQL();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieValid(httpExchange)) {
                renderWithCookie(httpExchange);
            } else {
                renderWithoutCookie(httpExchange);
            }
        }catch(DataAccessException e){
            // TODO: display error page/communicate
            e.printStackTrace();
        }
    }


    private void renderWithCookie(HttpExchange httpExchange) throws IOException, DataAccessException {

        int userID = getLoginIdFromCookie(httpExchange);
        String userType = loginDao.getUserCategory(userID);

        switch (userType) {
            case "Admin":
                redirectTo(httpExchange, "/admin");
                break;
            case "Mentor":
                redirectTo(httpExchange, "/mentor/request");
                break;
            case "Student":
                redirectTo(httpExchange, "/student");
                break;
        }
    }

    private void renderWithoutCookie(HttpExchange httpExchange) throws IOException, DataAccessException {
        String response = "";
        String method = httpExchange.getRequestMethod();

        if (method.equals("POST")) {

            Map<String, String> inputs = getMapFromISR(httpExchange);
            String login = inputs.get("login");
            String password = inputs.get("password");
            int userID = loginDao.getUserId(login, password);

            createCookie(httpExchange, userID);
            renderWithCookie(httpExchange);

        } else if (method.equals("GET")) {

            response = view.getLoginScreen();
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}

