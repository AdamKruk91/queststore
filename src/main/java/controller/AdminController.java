package controller;

import java.sql.SQLException;
import java.util.List;

import model.Iterator;
import view.AdminView;
import model.MentorModel;
import model.GroupModel;
import dao.MentorDao;
import dao.GroupDao;
import dao.LoginDao;
import dao.LevelDao;


public class AdminController {

    private AdminView view;
    private InputController inputController;
    private MentorDao mentorDao = new MentorDao();
    private LoginDao loginDao = new LoginDao();
    private LevelDao levelDAO = new LevelDao();

    public AdminController() {
        view = new AdminView();
        inputController = new InputController();
    }

    public void controlMenuOptions() {
        boolean whileRunning = true;
        while (whileRunning) {
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
                    deleteMentor();
                    break;
                case 6:
                    try {
                        manageLevels();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
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
        for (MentorModel mentor : allMentors) {
            if (mentor.getID().equals(id))
                matchedMentor = mentor;
        }
        return matchedMentor;
    }

    private void updateMentorData(MentorModel mentor) {
        mentorDao.updateMentorTable(mentor);
    }

    private void updateLoginData(MentorModel mentor, String login, String password) {
        loginDao.updateLoginTable(mentor, login, password);
    }

    private void editMentorDataPanel() {
        MentorModel mentorToEdit = selectMentor();
        String mentorLogin = mentorToEdit.getEmail();
        String mentorPassword = mentorToEdit.getPassword();
        boolean isChoose = true;
        while (isChoose) {
            view.displayEditMentorMenu();
            int userChoice = inputController.getIntInput("Select field number to edit: ");
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
                case 5:
                    GroupModel groupModel = selectGroup();
                    mentorToEdit.setIdGroup(groupModel.getId());
                case 0:
                    isChoose = false;
                    break;
                default:
                    break;
            }
        }
        updateMentorData(mentorToEdit);
        updateLoginData(mentorToEdit, mentorLogin, mentorPassword);
    }

    private void displayMentorData() {
        MentorModel mentor = selectMentor();
        view.displayMentorData(mentor);
    }

    private void deleteMentor(){
        MentorModel mentorModel = selectMentor();
        mentorDao.deleteMentor(mentorModel.getID());
        loginDao.removeLoginByMail(mentorModel.getEmail());
    }

    private void manageLevels() throws SQLException {
        boolean running = true;

        while(running) {
            Iterator levelsIterator = levelDAO.getLevels();
            String userInput = view.displayLevelsMenu(levelsIterator);

            switch (userInput) {
                case "1":
                    createNewLevel();
                    break;
                case "2":
                    removeLevel();
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }

    private void createNewLevel() {
        String name = inputController.getStringInput("Please enter name:");
        int expTier = inputController.getIntInput("Please enter exp amount:");
        try {
            levelDAO.addLevel(name, expTier);
        } catch (SQLException e) {
            System.out.println("ERROR: Level with this name or exp amount already exists!");
        }
    }

    private void removeLevel() throws SQLException {
        // TODO: Screen is cleared, levels not visible
        view.displayLevels(levelDAO.getLevels());
        String userInput = inputController.getStringInput("Choose ID to remove level: ");
        int levelID = Integer.parseInt(userInput);
        // TODO: check if exists first?
        try {
            levelDAO.deleteLevel(levelID);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

}


















































