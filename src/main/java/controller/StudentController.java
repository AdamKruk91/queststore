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
import model.Group;
import model.Level;
import model.Student;
import view.StudentView;


public class StudentController extends AbstractContoller implements HttpHandler {

    private StudentView view;
    private LoginDAO loginDao = new LoginDAOSQL();
    private LevelDAO levelDao = new LevelDAOSQL();
    private StudentDAO studentDao = new StudentDAOSQL();
    private ArtifactDAO artifactDao = new ArtifactDAOSQL();
    private WalletDAO walletDao = new WalletDAOSQL();
    private GroupDAO groupDAO = new GroupDAOSQL();

    StudentController() {
        view = new StudentView();
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
        } else if(URI.startsWith("/student/store/buy/")) {
            buyArtifact(httpExchange, userID);
        } else if(URI.startsWith("/student/wallet/cancel/")) {
            cancelUseArtifact(httpExchange);
        } else if(URI.startsWith("/student/profile/")) {
            displayProfile(httpExchange);
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
                case "/student/store":
                    renderStore(httpExchange, userID);
                case "/student/myclass":
                    renderMyClass(httpExchange, userID);
                default:
                    System.out.println("Wrong address:" + URI);
            }
        }
    }

    private void cancelUseArtifact(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/student/wallet/cancel/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        try {
            Artifact artifact = artifactDao.getInstantiatedArtifact(artifactID);
            artifact.setStatus("In wallet");
            artifactDao.updateArtifactStatus(artifact);
        }catch(DataAccessException e){
            e.printStackTrace();
            //TODO: display error
        }
        redirectTo(httpExchange,"/student/wallet");
    }


    private void useArtifact(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/student/wallet/use/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        try {
            Artifact artifact = artifactDao.getInstantiatedArtifact(artifactID);
            artifact.setStatus("Use requested");
            artifactDao.updateArtifactStatus(artifact);
        }catch(DataAccessException e){
            e.printStackTrace();
            //TODO: display error
        }
        redirectTo(httpExchange,"/student/wallet");
    }

    private void buyArtifact(HttpExchange httpExchange, int userID) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/student/store/buy/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        try {
            Artifact artifact = artifactDao.getArtifact(artifactID);
            if(artifact != null && buyArtifactSuccess(artifact, userID)) {
               // TODO: purchase success info display?
            } else {
                // TODO: purchase fail display
            }
        }catch(DataAccessException e){
            e.printStackTrace();
            //TODO: display error
        }
        redirectTo(httpExchange,"/student/store");
    }

    private boolean buyArtifactSuccess(Artifact artifact, int userID) {
     try {
            Student buyer = studentDao.get(userID);
            int accountBalance = buyer.getWallet().getAmount();

            if(accountBalance >= artifact.getPrice()) {

                accountBalance -= artifact.getPrice();
                buyer.getWallet().setAmount(accountBalance);
                walletDao.update(buyer.getWallet());
                artifactDao.addToWallet(artifact, buyer.getID());
                // TODO add artifact to wallet
                return true;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add error page
        }
        return false;
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

    private void displayProfile(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String userIDStr = URI.replace("/student/profile/", "");
        int userID = Integer.parseInt(userIDStr);
            renderProfile(httpExchange, userID);
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

    private void renderStore(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Student student = studentDao.get(userID);
            List<Artifact> artifacts;
            artifacts = artifactDao.getArtifactCollection();
            String response = view.getStoreScreen(student, artifacts);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add erorr page
        }
    }

    private void renderMyClass(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Student student = studentDao.get(userID);
            Group group = groupDAO.getByUser(userID);
            String response = view.getMyClassScreen(student, group);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add erorr page
        }
    }
}