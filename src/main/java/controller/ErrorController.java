package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import view.ErrorControllerView;

import java.io.IOException;

public class ErrorController extends AbstractContoller implements HttpHandler {

    private ErrorControllerView view = new ErrorControllerView();

    public void handle(HttpExchange httpExchange) throws IOException {
        handleErrorPage(httpExchange);
    }

    private void handleErrorPage(HttpExchange httpExchange) throws IOException{
        String response = view.getErrorPage();
        handlePositiveResponse(httpExchange, response);
    }
}
