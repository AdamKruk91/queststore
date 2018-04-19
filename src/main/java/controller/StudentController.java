package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.ItemModel;
import model.Level;
import view.StudentView;
import model.StudentModel;

public class StudentController extends AbstractContoller implements HttpHandler {

    private StudentView view;
    private InputController inputController;
    private LoginDao loginDao = new LoginDao();
    private LevelDao levelDao = new LevelDao();
    private ItemDao itemDao = new ItemDao();
    private StudentDao studentDao = new StudentDao();

    public StudentController() {
        view = new StudentView();
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

            if(!userType.equals("Student")) {
                redirectTo(httpExchange, "/login");
            } else {
                handleRendering(httpExchange, loginID);
            }
        } else {
            redirectTo(httpExchange, "/login");
        }
        System.out.println("Success i guess");
    }

    private void handleRendering(HttpExchange httpExchange, int loginID) throws IOException {
        //TODO get URI and do switch on it
        renderProfile(httpExchange, loginID);
    }

    private void renderProfile(HttpExchange httpExchange, int loginID) throws IOException {
        StudentModel student = getStudent(loginID);
        int totalExp = student.getMyWallet().getTotalCoolcoins();
        Level level = null;
        // TODO: display error message in browser!
        try {
            level = levelDao.getLevel(totalExp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String response = view.getProfileScreen(student, level);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private StudentModel getStudent(int idLogin) {
        return studentDao.getStudentByIdLogin(idLogin);
    }

    private ItemModel selectArtifact() throws SQLException {
        List<ItemModel> itemCollection  = itemDao.getItemCollectionByType("Artifact");
        view.displayCollectionOfItem(itemCollection);
        int idArtifact = inputController.getIntInput("Enter artifact id to buy: ");
        ItemModel matchedArtifact = null;
        for (ItemModel artifact: itemCollection)
            if(artifact.getID() == idArtifact)
                matchedArtifact = artifact;
        return matchedArtifact;
    }

    private void buyArtifact(StudentModel student) throws SQLException {
        ItemModel artifact = selectArtifact();
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.insertTransaction(student.getID(), artifact.getID());
    }

    private void  displayWallet(StudentModel student) {
        view.displayWallet(student.getMyWallet());
        TransactionDao transactionDao = new TransactionDao();
        List<ItemModel> artifactsCollection = transactionDao.getStudentArtifact(student.getID());
        view.displayBoughtArtifacts(artifactsCollection);
    }

    public void controlMenuOptions(int loginId) throws SQLException {
        StudentModel student = getStudent(loginId);
        boolean whileRunning = true;
        while (whileRunning) {
            view.displayStudentMenu();
            int userChoice = inputController.getIntInput("SELECT AN OPTION: ");
            switch (userChoice) {
                case 1:
                    displayWallet(student);
                    break;
                case 2:
                    buyArtifact(student);
                    break;
                case 3:
                    //Buy artifact together with teammates; CHYBA TNIEMY
                    break;
                case 4:
                    checkExperience(student);
                    break;
                default:
                    break;
            }
        }
    }
     private void checkExperience(StudentModel student){
         try {
             int totalExp = student.getMyWallet().getTotalCoolcoins();
             Level level = levelDao.getLevel(totalExp);
             view.displayCurrentExperience(totalExp, level.getName());
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
}