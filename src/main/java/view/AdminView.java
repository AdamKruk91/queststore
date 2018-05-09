package view;

import dao.GroupDAOSQL;
import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;
import java.util.Scanner;

public class AdminView {

    public void displayAdminMenu() {
        System.out.println("\nMENU: \n" +
                "1 - Create mentor\n" +
                "2 - Create new group\n" +
                "3 - Edit mentor\n" +
                "4 - Display mentor data\n" +
                "5 - Delete mentor\n" +
                "6 - Manage levels\n" +
                "0 - Exit\n");
    }

    public void displayEditMentorMenu() {
        System.out.println("\n1. name\n"
                + "2. last name\n"
                + "3. email\n"
                + "4. password\n"
                + "5. edit Group\n"
                + "0. exit\n");
    }

    public String displayLevelsMenu(IteratorImpl iterator) {
        displayLevels(iterator);
        System.out.println("\n--------\n" +
                "1. Add Level\n" +
                "2. Delete Level\n" +
                "0. ABORT\n\n");
        System.out.print("Choose action: ");
        return getUserInput().trim();
    }

    public void displayLevels(IteratorImpl iterator) {

        while (iterator.hasNext()) {
            Level level = (Level) iterator.next();
            System.out.println(String.format("ID: %d, Name: %s, Exp: %s", level.getID(), level.getName(), level.getExperienceAmount()));
        }
    }

    public void displayAllMentors(List<Mentor> mentorsCollection) {
        for (Mentor mentor : mentorsCollection) {
            System.out.println(mentor.getID() + ". " + mentor.getFullName());
        }
    }

    public void displayMentorData(Mentor mentor) {
        int groupId = mentor.getIdGroup();
        GroupDAOSQL dao = new GroupDAOSQL();
        String groupName = dao.getGroupNameById(groupId);
        System.out.println("\n===MENTOR DATA==\n"
                + mentor.getFullName()
                + "\nemail: " + mentor.getEmail()
                + "\npassword: " + mentor.getPassword()
                + "\ngroup name: " + groupName + '\n');
    }

    public void displayAllGroups(List<Group> groupsCollection) {
        for (Group group : groupsCollection) {
            System.out.println(group.getID() + ". " + group.getGroupName());
        }
    }

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