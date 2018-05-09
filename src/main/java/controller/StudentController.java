package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import model.UsableObject;
import model.Level;
import model.Student;
import view.StudentView;

public class StudentController extends AbstractContoller implements HttpHandler {

    private StudentView view;
    private InputController inputController;
    private LoginDAOSQL loginDao = new LoginDAOSQL();
    private LevelDAOSQL levelDao = new LevelDAOSQL();
    private StudentDAOSQL studentDao = new StudentDAOSQL();
    private TransactionDao transactionDao = new TransactionDao();

    public StudentController() {
        view = new StudentView();
        inputController = new InputController();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieValid(httpExchange)) {
                int loginID = getLoginIdFromCookie(httpExchange);
                String userType = loginDao.getUserCategory(loginID);


                if (!userType.equals("Student")) {
                    redirectTo(httpExchange, "/login");
                } else {
                    handleRendering(httpExchange, loginID);
                }
            } else {
                redirectTo(httpExchange, "/login");
            }
            System.out.println("Success i guess");
        } catch (SQLException e) {
            e.printStackTrace();
            // TODO : display error message in browser
        }
    }

    private void handleRendering(HttpExchange httpExchange, int loginID) throws IOException, SQLException {

        final String URI = httpExchange.getRequestURI().toString();

        if(URI.contains("/static")) {
            redirectTo(httpExchange, URI.substring(URI.indexOf("/static")));

        } else if(URI.startsWith("/student/wallet/use/")) {
            useArtifact(httpExchange);
        } else {

            switch (URI) {
                case "/student":
                    renderProfile(httpExchange, loginID);
                    break;
                case "/student/wallet":
                    renderWallet(httpExchange, loginID);
                    break;
                case "/student/wallet/pending":
                    renderWalletPending(httpExchange, loginID);
                    break;
                case "/student/wallet/used":
                    renderWalletUsed(httpExchange, loginID);
                    break;

                default:
                    System.out.println("Wrong address:" + URI);
            }
        }
    }

    private void useArtifact(HttpExchange httpExchange) throws SQLException, IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/student/wallet/use/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        UsableObject artifact = itemDao.getItemByID(artifactID);
        transactionDao.updateStatusOfTransaction(artifact, 2);
        redirectTo(httpExchange,"/student/wallet");
    }

    private void renderProfile(HttpExchange httpExchange, int loginID) throws IOException {
        Student student = getStudent(loginID);
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

    private void renderWallet(HttpExchange httpExchange, int loginID) throws IOException {
        Student student = getStudent(loginID);
        TransactionDao transactionDao = new TransactionDao();
        List<UsableObject> artifacts = transactionDao.getStudentArtifact(student.getID());
        String response = view.getWalletScreen(student, artifacts);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void renderWalletPending(HttpExchange httpExchange, int loginID) throws IOException {
        Student student = getStudent(loginID);
        TransactionDao transactionDao = new TransactionDao();
        List<UsableObject> artifacts = transactionDao.getStudentArtifact(student.getID(), 2);
        String response = view.getWalletPendingScreen(student, artifacts);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void renderWalletUsed(HttpExchange httpExchange, int loginID) throws IOException {
        Student student = getStudent(loginID);
        TransactionDao transactionDao = new TransactionDao();
        List<UsableObject> artifacts = transactionDao.getStudentArtifact(student.getID(), 1);
        String response = view.getWalletUsedScreen(student, artifacts);
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }



    private Student getStudent(int id) {
        return studentDao.getStudentById(id);
    }

    private UsableObject selectArtifact() throws SQLException {
        List<UsableObject> itemCollection  = itemDao.getItemCollectionByType("Artifact");
        view.displayCollectionOfItem(itemCollection);
        int idArtifact = inputController.getIntInput("Enter artifact id to buy: ");
        UsableObject matchedArtifact = null;
        for (UsableObject artifact: itemCollection)
            if(artifact.getID() == idArtifact)
                matchedArtifact = artifact;
        return matchedArtifact;
    }

    private void buyArtifact(Student student) throws SQLException {
        UsableObject artifact = selectArtifact();
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.insertTransaction(student.getID(), artifact.getID());
    }

    private void  displayWallet(Student student) {
        view.displayWallet(student.getWallet());
        TransactionDao transactionDao = new TransactionDao();
        List<UsableObject> artifactsCollection = transactionDao.getStudentArtifact(student.getID());
        view.displayBoughtArtifacts(artifactsCollection);
    }

    public void controlMenuOptions(int loginId) throws SQLException {
        Student student = getStudent(loginId);
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
     private void checkExperience(Student student){
         try {
             int totalExp = student.getMyWallet().getTotalCoolcoins();
             Level level = levelDao.getLevel(totalExp);
             view.displayCurrentExperience(totalExp, level.getName());
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }
}