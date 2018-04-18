package view;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class LoginView {

    public String getLoginScreen () {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }

    public String getWrongLoginScreen () {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/wrong-login.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }
}