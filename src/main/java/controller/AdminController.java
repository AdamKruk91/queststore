package controller;

import java.io.IOException;
import java.sql.SQLException;
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
    private LoginDao loginDao = new LoginDAOSQL();
    private LevelDAO levelDAO = new LevelDAOSQL();
    private AdminDAO adminDao = new AdminDAOSQL();

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
        try {
            Admin admin = getAdmin(loginID);
            String response = view.getProfileScreen(admin);
            handlePositiveResponse(httpExchange, response);
        } catch (DataAccessException e){
            redirectTo(httpExchange, "/login");
        }
    }

    private void renderMentorsData(HttpExchange httpExchange) throws IOException {
        List<Mentor> allMentors = mentorDao.getAll();
        String response = view.getMentorsDisplay(allMentors);
        handlePositiveResponse(httpExchange, response);
    }

    private void handleCreateMentor(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            Mentor mentor = createMentorFromISR(httpExchange);
            try {
                mentorDao.add(mentor);
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
        String response = view.getCreateMentor(groupDao.getAll());
        handlePositiveResponse(httpExchange, response);
    }

    private void renderCreateMentorWithMessage(HttpExchange httpExchange, String message) throws IOException {
        String response = view.getCreateMentorMessage(groupDao.getAll(), message);
        handlePositiveResponse(httpExchange, response);
    }

    private void handleEditMentor(HttpExchange httpExchange) throws IOException{
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")) {
            Mentor mentor = createMentorWithIdFromISR(httpExchange);
            mentorDao.update(mentor);
            renderEditMentor(httpExchange);
        } else {
            renderEditMentor(httpExchange);
        }
    }

    private void renderEditMentor(HttpExchange httpExchange) throws IOException{
        String response = view.getEditMentor(mentorDao.getAll(), groupDao.getAll());
        handlePositiveResponse(httpExchange, response);
    }

    private void handleCreateGroup(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if(method.equals("POST")){
            Map<String, String> inputs = getMapFromISR(httpExchange);
            String name = inputs.get("name");

            groupDao.add(name);
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

    private Mentor createMentorFromISR(HttpExchange httpExchange) throws IOException, DataAccessException {
        Map<String, String> inputs = getMapFromISR(httpExchange);
        String login = inputs.get("login");
        String firstName = inputs.get("name");
        String lastName = inputs.get("surname");
        String email = inputs.get("email");
        String password = inputs.get("password");
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

    private Admin getAdmin(int id) throws DataAccessException{
        return adminDao.get(id);
    }
}

