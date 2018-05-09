package controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import exceptions.DataAccessException;
import model.Artifact;
import model.Level;
import model.Student;
import view.StudentView;


public class StudentController extends AbstractContoller implements HttpHandler {

    private StudentView view;
    private InputController inputController;
    private LoginDAOSQL loginDao = new LoginDAOSQL();
    private LevelDAO levelDao = new LevelDAOSQL();
    private StudentDAO studentDao = new StudentDAOSQL();
    private ArtifactDAO artifactDao = new ArtifactDAOSQL();

    StudentController() {
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
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: display error page
        }
    }

    private void handleRendering(HttpExchange httpExchange, int userID) throws IOException, SQLException {

        final String URI = httpExchange.getRequestURI().toString();

        if(URI.contains("/static")) {
            redirectTo(httpExchange, URI.substring(URI.indexOf("/static")));

        } else if(URI.startsWith("/student/wallet/use/")) {
            useArtifact(httpExchange);
        } else {

            switch (URI) {
                case "/student":
                    renderProfile(httpExchange, userID);
                    break;
                case "/student/wallet":
                    renderWallet(httpExchange, userID);
                    break;
                case "/student/wallet/pending":
                    renderWalletPending(httpExchange, userID);
                    break;
                case "/student/wallet/used":
                    renderWalletUsed(httpExchange, userID);
                    break;

                default:
                    System.out.println("Wrong address:" + URI);
            }
        }
    }

    private void useArtifact(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/student/wallet/use/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        try {
            Artifact artifact = artifactDao.getInstantiatedArtifact(artifactID);
            artifactDao.updateArtifactStatus(artifact);
        }catch(DataAccessException e){
            e.printStackTrace();
            //TODO: display error
        }
        redirectTo(httpExchange,"/student/wallet");
    }

    private void renderProfile(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Student student = studentDao.get(userID);
            int totalExp = student.getWallet().getTotalCoinsEarned();
            Level level;
            level = levelDao.getLevel(totalExp);
            String response = view.getProfileScreen(student, level);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add error page
        }

    }

    private void renderWallet(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Student student = studentDao.get(userID);
            List<Artifact> artifacts = artifactDao.getUserUnusedArtifacts(student.getID());
            String response = view.getWalletScreen(student, artifacts);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(DataAccessException e){
            e.printStackTrace();
            //TODO: add error page
        }

    }

    private void renderWalletPending(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Student student = studentDao.get(userID);
            List<Artifact> artifacts;
            artifacts = artifactDao.getUserRequestedArtifacts(student.getID());
            String response = view.getWalletPendingScreen(student, artifacts);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add erorr page
        }
    }

    private void renderWalletUsed(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Student student = studentDao.get(userID);
            List<Artifact> artifacts;
            artifacts = artifactDao.getUserUsedArtifacts(student.getID());
            String response = view.getWalletUsedScreen(student, artifacts);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add erorr page
        }
    }

    private Artifact selectArtifact() {
        List<Artifact> itemCollection  = null;
        try {
            itemCollection = artifactDao.getArtifactCollection();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add error page
        }
        view.displayCollectionOfItem(itemCollection);
        int idArtifact = inputController.getIntInput("Enter artifact id to buy: ");
        Artifact matchedArtifact = null;
        for (Artifact artifact: itemCollection)
            if(artifact.getID() == idArtifact)
                matchedArtifact = artifact;
        return matchedArtifact;
    }

    private void buyArtifact(Student student) {
        Artifact artifact = selectArtifact();
        try {
            artifactDao.insertTransaction(student.getID(), artifact.getID());
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add error page
        }
    }

    private void  displayWallet(Student student) {
        view.displayWallet(student.getWallet());
        List<Artifact> artifactsCollection = null;
        try {
            artifactsCollection = artifactDao.getArtifacts(student.getID());
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add error page
        }
        view.displayBoughtArtifacts(artifactsCollection);
    }

    public void controlMenuOptions(int userID) throws DataAccessException {
        Student student = studentDao.get(userID);
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
             int totalExp = student.getWallet().getTotalCoinsEarned();
             Level level = levelDao.getLevel(totalExp);
             view.displayCurrentExperience(totalExp, level.getName());
         } catch (DataAccessException e) {
             e.printStackTrace();
             //TODO: add error page
         }
     }
}