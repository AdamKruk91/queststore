package controller;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import dao.SessionDAO;
import dao.SessionDAOSQL;
import exceptions.DataAccessException;
import model.Session;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

abstract class AbstractContoller{

    SessionDAO sessionDAO = new SessionDAOSQL();

    void redirectTo(HttpExchange httpExchange, String URI) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", URI);
        httpExchange.sendResponseHeaders(302, -1);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    void createCookie(HttpExchange httpExchange, int loginID) throws IOException, DataAccessException {
        final UUID uuid = UUID.randomUUID();

        HttpCookie cookie = new HttpCookie("sessionId", uuid.toString());
        httpExchange.getResponseHeaders().add("Set-Cookie", cookie.toString());

        Session session = new Session(uuid.toString(), loginID);
        sessionDAO.addSession(session);
        redirectTo(httpExchange,"/login");
    }

    boolean isCookieValid(HttpExchange httpExchange) throws DataAccessException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;

        if (cookieStr != null) {
            cookie = HttpCookie.parse(cookieStr).get(0);
            String sessionID = cookie.getValue();
            return sessionDAO.sessionExists(sessionID);
        }
        return false;
    }

    int getLoginIdFromCookie(HttpExchange httpExchange) throws DataAccessException {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        cookie = HttpCookie.parse(cookieStr).get(0);
        String sessionID = cookie.getValue();
        Session session = sessionDAO.getSession(sessionID);
        return session.getUserID();
    }

    void handlePositiveResponse(HttpExchange httpExchange, String response) throws IOException{
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    void handleNegativeResponse(HttpExchange httpExchange, String URI) throws IOException{
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Location", URI);
        httpExchange.sendResponseHeaders(403, -1);
        OutputStream os = httpExchange.getResponseBody();
        os.write("".getBytes());
        os.close();
    }

    static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
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

    Map<String, String> getMapFromISR(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        return parseFormData(formData);
    }
}
