package view;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public abstract class AbstractView {

    public String createTwigWithoutArgs(String twigPath){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(twigPath);
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }

    public String createTwigWithMessage(String twigPath, String message){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(twigPath);
        JtwigModel model = JtwigModel.newModel();
        model.with("message", message);
        return template.render(model);
    }
}
