package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.security.acl.Group;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.*;
import org.apache.commons.lang3.ArrayUtils;
import view.AdminView;
import view.Static;


public class AdminController extends AbstractContoller implements HttpHandler {

    private AdminView view;
    private InputController inputController;
    private GroupDao groupDao = new GroupDao();
    private MentorDao mentorDao = new MentorDao();
    private LoginDao loginDao = new LoginDao();
    private LevelDao levelDAO = new LevelDao();
    private AdminDao adminDao = new AdminDao();


    public AdminController() {
        view = new AdminView();
        inputController = new InputController();
    }

    public void handle(HttpExchange httpExchange) throws IOException {

        if(isCookieValid(httpExchange)) {
            int loginID = getLoginIdFromCookie(httpExchange);
            String userType = "";

            try {
                userType = loginDao.findStatusByLoginId(loginID);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(!userType.equals("Admin")) {
                redirectTo(httpExchange, "/login");
            } else {
                handleRendering(httpExchange, loginID);
            }
        } else {
            redirectTo(httpExchange, "/login");
        }
    }

    private void handleRendering(HttpExchange httpExchange, int loginID) throws IOException {
        //TODO get URI and do switch on it
        String URI = httpExchange.getRequestURI().toString();

        if(URI.startsWith("/admin/static")){
            redirectTo(httpExchange, URI.replace("/admin", ""));
        } else {
            System.out.println(URI);
            switch (URI) {
                case "/admin/display-mentors":
                    renderMentorsData(httpExchange);
                    break;
                case "/admin/create-mentor":
                    handleCreateMentor(httpExchange);
                    break;
                case "/admin":
                    renderProfile(httpExchange, loginID);
                    System.out.println("admin");
                    break;
            }
        }
    }

    private void renderMentorsData(HttpExchange httpExchange) throws IOException {
        List<MentorModel> allMentors = mentorDao.getAllMentorsCollection();
        String response = view.getMentorsDisplay(allMentors);
        handlePositiveResponse(httpExchange, response);
    }

    private void handleCreateMentor(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            Map<String, String> inputs = getMapFromISR(httpExchange);
            String firstName = inputs.get("name");
            String lastName = inputs.get("surname");
            String email = inputs.get("email");
            String password = inputs.get("password");
            int groupId = Integer.parseInt(inputs.get("dropdown"));
            MentorModel mentorModel = new MentorModel(firstName, lastName, email, password, groupId);
            try {
                mentorDao.insertNewMentor(mentorModel);
                renderCreateMentorWithMessage(httpExchange, "Mentor creation was successful!");
            } catch (SQLException e){
                e.printStackTrace();
                renderCreateMentorWithMessage(httpExchange, "Mentor creation failed!");
            }
        } else{
            renderCreateMentor(httpExchange);
        }
    }

    private void renderCreateMentor(HttpExchange httpExchange) throws IOException {
        String response = view.getCreateMentor(groupDao.getGroupsCollection());
        handlePositiveResponse(httpExchange, response);
    }

    private void renderProfile(HttpExchange httpExchange, int loginID) throws IOException {
        AdminModel admin = getAdmin(loginID);
        String response = view.getProfileScreen(admin);
        handlePositiveResponse(httpExchange, response);
    }

    private AdminModel getAdmin(int idLogin) {
        return adminDao.getAdminByIdLogin(idLogin);
    }

    public void controlMenuOptions() throws SQLException {
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
                case 0:
                    whileRunning = false;
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

    private void createMentor() throws SQLException {
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

    private void updateLoginData(MentorModel mentor, String login, String password) throws SQLException {
        loginDao.updateLoginTable(mentor, login, password);
    }

    private void editMentorDataPanel() throws SQLException {
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


















































