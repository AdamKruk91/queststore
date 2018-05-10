package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import exceptions.DataAccessException;
import model.*;
import view.AdminView;

public class AdminController extends AbstractContoller implements HttpHandler {

    private AdminView view = new AdminView();
    private GroupDAO groupDao = new GroupDAOSQL();
    private MentorDAO mentorDao = new MentorDAOSQL();
    private LoginDAO loginDAO = new LoginDAOSQL();
    private LevelDAO levelDAO = new LevelDAOSQL();
    private AdminDAO adminDao = new AdminDAOSQL();

    public void handle(HttpExchange httpExchange) throws IOException {

        try {
            if(isCookieValid(httpExchange)) {

                int loginID = getLoginIdFromCookie(httpExchange);
                String userType = "";
                userType = loginDAO.getUserCategory(loginID);

                if(!userType.equals("Admin")) {
                    redirectTo(httpExchange, "/login");
                } else {
                    handleRendering(httpExchange, loginID);
                }
            } else {
                redirectTo(httpExchange, "/login");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            handleNegativeResponse(httpExchange, "/error");
        }

    }

    private void handleRendering(HttpExchange httpExchange, int loginID) throws IOException {
        String URI = httpExchange.getRequestURI().toString();

        if(URI.contains("/static")) {
            redirectTo(httpExchange, URI.substring(URI.indexOf("/static")));
        } else if(URI.contains("/admin/display-mentors/delete/")){
            handleDeleteMentor(httpExchange);
        } else {
            System.out.println(URI);
            switch (URI) {
                case "/admin/display-mentors":
                    renderMentorsData(httpExchange);
                    break;
                case "/admin/create-mentor":
                    try {
                        handleCreateMentor(httpExchange);
                    } catch (DataAccessException e) {
                      handleNegativeResponse(httpExchange, "/admin/error");
                    }
                    break;
                case "/admin/create-group":
                    handleCreateGroup(httpExchange);
                    break;
                case "/admin/edit-mentor":
                    try {
                        handleEditMentor(httpExchange);
                    } catch (DataAccessException e) {
                        handleNegativeResponse(httpExchange, "/admin/error");
                    }
                    break;
                case "/admin/manage-level":
                    handleManageLevel(httpExchange);
                    break;
                case "/admin":
                    renderProfile(httpExchange, loginID);
                    break;
            }
        }
    }

    private void renderProfile(HttpExchange httpExchange, int loginID) throws IOException {
        try {
            Admin admin = getAdmin(loginID);
            String response = view.getProfileScreen(admin);
            handlePositiveResponse(httpExchange, response);
        } catch (DataAccessException e){
            redirectTo(httpExchange, "/login");
        }
    }

    private void renderMentorsData(HttpExchange httpExchange) throws IOException {
        try {
            List<Mentor> allMentors = mentorDao.getAll();
            String response = view.getMentorsDisplay(allMentors);
            handlePositiveResponse(httpExchange, response);
        } catch (DataAccessException e){
            handleNegativeResponse(httpExchange, "/error");
        }
    }

    private void handleCreateMentor(HttpExchange httpExchange) throws IOException, DataAccessException {
        String method = httpExchange.getRequestMethod();
            if (method.equals("POST")) {
                try {
                    Mentor mentor = createMentorFromISR(httpExchange);
                    mentorDao.add(mentor);
                    String response = view.getCreateMentorMessage(groupDao.getAll(), "Mentor creation was successful!");
                    handlePositiveResponse(httpExchange, response);
                } catch (DataAccessException e) {
                    renderCreateMentorWithMessage(httpExchange, "Mentor creation failed!");
                }
            } else {
                renderCreateMentor(httpExchange);
            }
    }

    private void renderCreateMentor(HttpExchange httpExchange) throws IOException, DataAccessException {
        String response = view.getCreateMentor(groupDao.getAll());
        handlePositiveResponse(httpExchange, response);
    }

    private void renderCreateMentorWithMessage(HttpExchange httpExchange, String message) throws IOException, DataAccessException {
        String response = view.getCreateMentorMessage(groupDao.getAll(), message);
        handlePositiveResponse(httpExchange, response);
    }

    private void handleEditMentor(HttpExchange httpExchange) throws IOException, DataAccessException{
            String method = httpExchange.getRequestMethod();
            if (method.equals("POST")) {
                try {
                    Mentor mentor = createMentorWithIdFromISR(httpExchange);
                    mentorDao.update(mentor);
                    renderEditMentor(httpExchange);
                } catch (DataAccessException e) {
                    handleNegativeResponse(httpExchange, "/error");
                }
            } else {
                renderEditMentor(httpExchange);
            }
    }

    private void renderEditMentor(HttpExchange httpExchange) throws IOException, DataAccessException{
        String response = view.getEditMentor(mentorDao.getAll(), groupDao.getAll());
        handlePositiveResponse(httpExchange, response);
    }

    private void handleCreateGroup(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            try {
                Map<String, String> inputs = getMapFromISR(httpExchange);
                String name = inputs.get("name");
                Group group = new Group(name);
                groupDao.add(group);
                renderCreateGroupWithMessage(httpExchange, "Group creation was successful!");
            } catch (DataAccessException e){
                renderCreateGroupWithMessage(httpExchange, "Group creation was unsuccessful!");
            }
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

    private Mentor createMentorFromISR(HttpExchange httpExchange) throws IOException, DataAccessException {
        Map<String, String> inputs = getMapFromISR(httpExchange);
        String login = inputs.get("login");
        String password = inputs.get("password");
        String firstName = inputs.get("name");
        String lastName = inputs.get("surname");
        String email = inputs.get("email");
        int groupId = Integer.parseInt(inputs.get("dropdown"));
        Group group = groupDao.getByID(groupId);
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(group);
        return new Mentor(login, password, firstName, lastName, email, groups);
    }

    private Mentor createMentorWithIdFromISR(HttpExchange httpExchange) throws IOException, DataAccessException{
        Map<String, String> inputs = getMapFromISR(httpExchange);
        int id = Integer.parseInt(inputs.get("id"));
        String login = inputs.get("login");
        String firstName = inputs.get("name");
        String lastName = inputs.get("surname");
        String email = inputs.get("email");
        String password = inputs.get("password");
        int groupId = Integer.parseInt(inputs.get("dropdown"));
        Group group = groupDao.getByID(groupId);
        ArrayList<Group> groups = new ArrayList<>();
        groups.add(group);
        return new Mentor(id, login, password, firstName, lastName, email, groups);
    }

    private void handleDeleteMentor(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String mentorStrID = URI.replace("/admin/display-mentors/delete/", "");
        int mentorID = Integer.parseInt(mentorStrID);
        try {
            mentorDao.delete(mentorID);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        redirectTo(httpExchange, "/admin/display-mentors");
    }

    private void handleManageLevel(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            try {
                Map<String, String> inputs = getMapFromISR(httpExchange);
                String name = inputs.get("name");
                int experience = Integer.parseInt(inputs.get("experience"));
                levelDAO.add(name, experience);
                renderCreateLevelWithMessage(httpExchange, "Level creation was successful!");
            } catch (DataAccessException e){
                renderCreateLevelWithMessage(httpExchange, "Level creation was unsuccessful!");
            }
        } else{
            renderCreateLevel(httpExchange);
        }
    }

    private void renderCreateLevel(HttpExchange httpExchange) throws IOException {
        String response = view.getCreateLevel();
        handlePositiveResponse(httpExchange, response);
    }

    private void renderCreateLevelWithMessage(HttpExchange httpExchange, String message) throws IOException {
        String response = view.getCreateLevelWithMessage(message);
        handlePositiveResponse(httpExchange, response);
    }

    private Admin getAdmin(int id) throws DataAccessException{
        return adminDao.get(id);
    }
}

