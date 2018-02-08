package controller;

import java.util.List;

import model.ItemModel;
import view.AdminView;
import model.MentorModel;
import model.GroupModel;
import dao.*;


public class AdminController {

    private AdminView view;
    private InputController inputController;

    public AdminController() {
        view = new AdminView();
        inputController = new InputController();
    }

    public void controlMenuOptions() {
        boolean exit = false;
        while (!exit) {
            view.displayAdminMenu();
            int userChoice = inputController.getIntInput("SELECT AN OPTION: ");
            switch (userChoice) {
                case 1:
                    createMentor();
                    break;
                case 2:
                    createGroup();
                    break;
                case 3:
                    editMentorDataPanel();
                    break;
                case 4:
                    displayMentorData();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong number!");
            }
        }
    }

    private GroupModel selectGroup() {
        GroupDao groupDao = new GroupDao();
        List<GroupModel> allGroups =groupDao.getGroupsCollection();
        view.displayAllGroups(allGroups);
        int id = inputController.getIntInput("Enter id of the chosen group: ");
        GroupModel selectedGroup = null;
        for (GroupModel group: allGroups)
            if (group.getId() == id)
                selectedGroup = group;
        return selectedGroup;
    }

    private void createMentor() {
        String mentorName = inputController.getStringInput("Enter mentor name: ");
        String mentorLastName = inputController.getStringInput("Enter mentor last name: ");
        String mentorEmail = inputController.getStringInput("Enter mentor email: ");
        String mentorPassword = inputController.getStringInput("Enter mentor password: ");
        GroupModel selectedGroup = selectGroup();
        int idGroup = selectedGroup.getId();
        MentorDao mentorDao = new MentorDao();
        MentorModel mentor = new MentorModel(mentorName, mentorLastName, mentorEmail, mentorPassword, idGroup);
        mentorDao.insertNewMentor(mentor);
    }

    private void createGroup() {
        String groupName = inputController.getStringInput("Enter group name: ");
        GroupDao groupDao = new GroupDao();
        groupDao.addNewGroup(groupName);
    }

    private MentorModel selectMentor() {
        MentorDao mentorDao = new MentorDao();
        List<MentorModel> allMentors = mentorDao.getAllMentorsCollection();
        view.displayAllMentors(allMentors);
        int id = inputController.getIntInput("Enter mentor id to edit: ");
        MentorModel matchedMentor = null;
        for (MentorModel mentor: allMentors)
            if (mentor.getID().equals(id))
                matchedMentor = mentor;
        return matchedMentor;
    }

    private void editMentorDataPanel() {
        MentorModel mentorToEdit = selectMentor();
        int userChoice = 0;
        while (userChoice != 5) {
            view.displayEditMentorMenu();
            userChoice = inputController.getIntInput("Select field number to edit: ");
            switch (userChoice) {
                case 1:
                    String name = inputController.getStringInput("Enter mentor name:");
                    mentorToEdit.setName(name);
                    break;
                case 2:
                    String lastName = inputController.getStringInput("Enter mentor last name");
                    mentorToEdit.setLastName(lastName);
                    break;
                case 3:
                    String email = inputController.getStringInput("Enter mentor email");
                    mentorToEdit.setEmail(email);
                    break;
                case 4:
                    String password = inputController.getStringInput("Enter mentor password");
                    mentorToEdit.setPassword(password);
                    break;
                default:
                    break;
            }
        }
        MentorDao mentorDao = new MentorDao();
        mentorDao.updateMentorData(mentorToEdit);
    }

    private void displayMentorData() {
        MentorModel mentor = selectMentor();
        view.displayMentorData(mentor);
    }

}


















































