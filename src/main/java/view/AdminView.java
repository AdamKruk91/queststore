package view;

import dao.GroupDao;
import model.*;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.util.List;
import java.util.Scanner;

public class AdminView {

    public void displayAdminMenu(){
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

    public String displayLevelsMenu(Iterator iterator) {
        displayLevels(iterator);
        System.out.println("\n--------\n" +
                "1. Add Level\n" +
                "2. Delete Level\n" +
                "0. ABORT\n\n");
        System.out.print("Choose action: ");
        return getUserInput().trim();
}

    public void displayLevels(Iterator iterator) {

        while(iterator.hasNext()) {
            Level level = (Level) iterator.next();
            System.out.println(String.format("ID: %d, Name: %s, Exp: %s", level.getID(), level.getName(), level.getExperienceAmount()));
        }
    }
    public void displayAllMentors(List<MentorModel> mentorsCollection) {
        for (MentorModel mentor: mentorsCollection) {
            System.out.println(mentor.getID() + ". "  +mentor.getFullName());
        }
    }
    public void displayMentorData(MentorModel mentor) {
        int groupId = mentor.getIdGroup();
        GroupDao dao = new GroupDao();
        String groupName = dao.getGroupNameById(groupId);
        System.out.println("\n===MENTOR DATA==\n"
                         + mentor.getFullName() 
                         + "\nemail: " + mentor.getEmail() 
                         + "\npassword: " + mentor.getPassword()
                         + "\ngroup name: " + groupName + '\n');
    }

    public void displayAllGroups(List<GroupModel> groupsCollection) {
        for (GroupModel group: groupsCollection) {
            System.out.println(group.getId() + ". "  +group.getGroupName());
        }
    }

    private String getUserInput(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }

    public String getProfileScreen (AdminModel adminModel) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/admin-profile.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("name", adminModel.getFullName());
        model.with("email", adminModel.getEmail());
        return template.render(model);
    }
}