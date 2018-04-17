package controller;


public class RootController {

    private UserController userController;

    public RootController() {
        userController = new UserController();
    }

    public static void main(String[] args) {
        RootController controller = new RootController();
        controller.userController.startApplication();
    }
}


