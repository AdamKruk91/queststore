package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exceptions.DataAccessException;

import java.io.IOException;
import java.net.HttpCookie;

public class LogoutController extends AbstractContoller implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        cookie = HttpCookie.parse(cookieStr).get(0);

        try {
            sessionDAO.deleteSession(cookie.getValue());
        } catch (DataAccessException e) {
            // TODO: display error screen/message
            e.printStackTrace();
        }

        redirectTo(httpExchange, "/login");
    }
}