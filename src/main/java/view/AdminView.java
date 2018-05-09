package view;

import dao.GroupDAOSQL;
import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;
import java.util.Scanner;

public class AdminView {

    private String getUserInput() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }

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
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/create-group.twig");
        JtwigModel model = JtwigModel.newModel();
        return template.render(model);
    }

    public String getCreateGroupMessage(String message) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/create-group-message.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("message", message);
        return template.render(model);
    }

    public String getEditMentor(List<Mentor> mentors, List<Group> groups) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/edit-mentor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("mentors", mentors);
        model.with("groups", groups);
        return template.render(model);
    }
}