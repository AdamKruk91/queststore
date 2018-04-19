package controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.UUID;

abstract class AbstractContoller{

    void redirectTo(HttpExchange httpExchange, String URI) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", URI);
        httpExchange.sendResponseHeaders(302, -1);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    void createCookie(HttpExchange httpExchange, int loginID) throws IOException {
        final UUID uuid = UUID.randomUUID();
        HttpCookie cookie = new HttpCookie("sessionId", uuid.toString());
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        LoginController.loggedInUsers.put(uuid.toString(), loginID);
        redirectTo(httpExchange,"/login");
    }

    boolean isCookieValid(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;

        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            if(LoginController.loggedInUsers.containsKey(cookie.getValue())) {
                return true;
            }
        }
        return false;
    }

    int getLoginIdFromCookie(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        cookie = HttpCookie.parse(cookieStr).get(0);
        return LoginController.loggedInUsers.get(cookie.getValue());
    }

    void handlePositiveResponse(HttpExchange httpExchange, String response) throws IOException{
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


}
