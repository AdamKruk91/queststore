package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.*;
import view.AdminView;

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
                userType = loginDao.getUserCategory(loginID);
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

        if(URI.contains("/static")) {
            redirectTo(httpExchange, URI.substring(URI.indexOf("/static")));
        } else {
            System.out.println(URI);
            switch (URI) {
                case "/admin/display-mentors":
                    renderMentorsData(httpExchange);
                    break;
                case "/admin/create-mentor":
                    handleCreateMentor(httpExchange);
                    break;
                case "/admin/create-group":
                    handleCreateGroup(httpExchange);
                    break;
                case "/admin/edit-mentor":
                    handleEditMentor(httpExchange);
                    break;
                case "/admin":
                    renderProfile(httpExchange, loginID);
                    break;
            }
        }
    }

    private void renderProfile(HttpExchange httpExchange, int loginID) throws IOException {
        AdminModel admin = getAdmin(loginID);
        String response = view.getProfileScreen(admin);
        handlePositiveResponse(httpExchange, response);
    }

    private void renderMentorsData(HttpExchange httpExchange) throws IOException {
        List<MentorModel> allMentors = mentorDao.getAllMentorsCollection();
        String response = view.getMentorsDisplay(allMentors);
        handlePositiveResponse(httpExchange, response);
    }

    private void handleCreateMentor(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            MentorModel mentorModel = createMentorFromISR(httpExchange);
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

    private void renderCreateMentorWithMessage(HttpExchange httpExchange, String message) throws IOException {
        String response = view.getCreateMentorMessage(groupDao.getGroupsCollection(), message);
        handlePositiveResponse(httpExchange, response);
    }

    private void handleEditMentor(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")) {
            MentorModel mentorModel = createMentorWithIdFromISR(httpExchange);
            mentorDao.updateMentorTable(mentorModel);
            renderEditMentor(httpExchange);
        } else {
            renderEditMentor(httpExchange);
        }
    }

    private void renderEditMentor(HttpExchange httpExchange) throws IOException{
        String response = view.getEditMentor(mentorDao.getAllMentorsCollection(), groupDao.getGroupsCollection());
        handlePositiveResponse(httpExchange, response);
    }

    private void handleCreateGroup(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            Map<String, String> inputs = getMapFromISR(httpExchange);
            String name = inputs.get("name");

            groupDao.addNewGroup(name);
            renderCreateGroupWithMessage(httpExchange, "Group creation was successful!");
        } else{
            renderCreateGroup(httpExchange);
        }
    }

    private void renderCreateGroup(HttpExchange httpExchange) throws IOException {
        String response = view.getCreateGroup();
        handlePositiveResponse(httpExchange, response);
    }

    private void renderCreateGroupWithMessage(HttpExchange httpExchange, String message) throws IOException{
        String response = view.getCreateGroupMessage(message);
        handlePositiveResponse(httpExchange, response);
    }

    private MentorModel createMentorFromISR(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getMapFromISR(httpExchange);
        String firstName = inputs.get("name");
        String lastName = inputs.get("surname");
        String email = inputs.get("email");
        String password = inputs.get("password");
        int groupId = Integer.parseInt(inputs.get("dropdown"));
        return new MentorModel(firstName, lastName, email, password, groupId);
    }

    private MentorModel createMentorWithIdFromISR(HttpExchange httpExchange) throws IOException {
        Map<String, String> inputs = getMapFromISR(httpExchange);
        int id = Integer.parseInt(inputs.get("id"));
        String firstName = inputs.get("name");
        String lastName = inputs.get("surname");
        String email = inputs.get("email");
        String password = inputs.get("password");
        int groupId = Integer.parseInt(inputs.get("dropdown"));
        return new MentorModel(id, firstName, lastName, email, password, groupId);
    }

    private AdminModel getAdmin(int id) {
        return adminDao.getAdmin(id);
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

