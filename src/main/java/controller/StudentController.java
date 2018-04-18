package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.ItemDao;
import dao.LevelDao;
import dao.StudentDao;
import dao.TransactionDao;
import model.ItemModel;
import model.Level;
import view.StudentView;
import model.StudentModel;

public class StudentController implements HttpHandler {

    private StudentView view;
    private InputController inputController;
    private LevelDao levelDao = new LevelDao();
    ItemDao itemDao = new ItemDao();
    StudentDao studentDao = new StudentDao();

    public StudentController() {
        view = new StudentView();
        inputController = new InputController();
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Success i guess");
    }

    private StudentModel getStudent(int idLogin) {
        return studentDao.getStudentByIdLogin(idLogin);
    }

    private ItemModel selectArtifact() {
        List<ItemModel> itemCollection  = itemDao.getItemCollectionByType("Artifact");
        view.displayCollectionOfItem(itemCollection);
        int idArtifact = inputController.getIntInput("Enter artifact id to buy: ");
        ItemModel matchedArtifact = null;
        for (ItemModel artifact: itemCollection)
            if(artifact.getID() == idArtifact)
                matchedArtifact = artifact;
        return matchedArtifact;
    }

    private void buyArtifact(StudentModel student) {
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

    public void controlMenuOptions(int loginId) {
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