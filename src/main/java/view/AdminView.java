package view;

import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;

public class AdminView extends AbstractView{

    public String getProfileScreen(Admin adminModel) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin-profile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("name", adminModel.getFullName());
        model.with("email", adminModel.getEmail());
        return template.render(model);
    }

    public String getMentorsDisplay(List<Mentor> mentors) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/display-mentors.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentors);
        return template.render(model);
    }

    public String getCreateMentor(List<Group> groups) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/create-mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        for(Group group: groups){
            System.out.println(group.getName());
        }
        model.with("groups", groups);
        return template.render(model);
    }

    public String getCreateMentorMessage(List<Group> groups, String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/create-mentor-message.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("groups", groups);
        model.with("message", message);
        return template.render(model);
    }

    public String getCreateGroup() {
        return createTwigWithoutArgs("templates/create-group.twig");
    }

    public String getCreateGroupMessage(String message) {
        return createTwigWithMessage("templates/create-group-message.twig", message);
    }

    public String getEditMentor(List<Mentor> mentors, List<Group> groups) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/edit-mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentors);
        model.with("groups", groups);
        return template.render(model);
    }

    public String getCreateLevel() {
        return createTwigWithoutArgs("templates/create-level.twig");

    }

    public String getCreateLevelWithMessage(String message) {
        return createTwigWithMessage("templates/create-level-message.twig", message);
    }

    public String getSomethingWentWrongPage(){
        return createTwigWithoutArgs("templates/error-page-admin.twig");
    }
}