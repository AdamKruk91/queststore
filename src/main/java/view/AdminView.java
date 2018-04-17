package view;


import dao.GroupDao;
import dao.MentorDao;
import model.GroupModel;
import model.MentorModel;

import java.security.acl.Group;
import java.util.List;

public class AdminView {

    public void displayAdminMenu(){
        System.out.println("\nMENU: \n" +
                            "1 - Create mentor\n" +
                            "2 - Create new group\n" + 
                            "3 - Edit mentor\n" +
                            "4 - Display mentor data\n" +
                            "5 - Exit\n");
    }

    public void displayEditMentorMenu() {
        System.out.println("\n1. name\n"
                        + "2. last name\n"
                        + "3. email\n"
                        + "4. password\n"
                        + "5. exit\n");
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
}