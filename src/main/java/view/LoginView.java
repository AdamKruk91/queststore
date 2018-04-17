package view;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class LoginView {

    public String getLoginScreen () {
        String response = "";
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
        JtwigModel model = JtwigModel.newModel();
        response = template.render(model);
        return response;
    }

    public void displayLoginFailed() {
        System.out.println("Login failed.");
    }
}