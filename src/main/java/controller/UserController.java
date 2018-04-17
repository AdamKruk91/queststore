package controller;

import dao.LoginDao;
import view.UserView;


public class UserController {

    private UserView view;

    public UserController() {
        view = new UserView();
    }

    private String logInUser(String login, String password) {
        LoginDao loginDao = new LoginDao();
        int idStatus = loginDao.findStatusId(login, password);
        if (idStatus == 0) {
            view.displayLoginFailed();
            System.exit(0);
        }
        String userStatus = loginDao.findStatus(idStatus);
        return userStatus;
    }

    private int getLoginId(String login, String password) {
        LoginDao loginDao = new LoginDao();
        return loginDao.findLoginId(login, password);
    }

    public void startApplication() {
        InputController inputController = new InputController();
        String login = inputController.getStringInput("Enter login: ");
        String password = inputController.getStringInput("Enter password: ");
        String userStatus = logInUser(login, password);
        if (userStatus.equals(0)) {
            System.out.println("Wrong login or password! ");
            System.exit(0);
        }
        int loginId = getLoginId(login, password);
        displayUserMenu(loginId, userStatus);
    }


    private void displayUserMenu(int loginId, String statusName) {
        view.displayWelcomeMessage();
        switch (statusName) {
            case "Admin":
                AdminController adminController = new AdminController();
                adminController.controlMenuOptions();
                break;
            case "Mentor":
                MentorController mentorController = new MentorController();
                mentorController.controlMenuOptions();
                break;
            case "Student":
                StudentController studentController = new StudentController();
                studentController.controlMenuOptions(loginId);
                break;
        }
    }
}

