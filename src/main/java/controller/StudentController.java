package controller;

import java.util.List;

import dao.ItemDao;
import dao.StudentDao;
import dao.TransactionDao;
import model.ItemModel;
import view.StudentView;
import model.StudentModel;


public class StudentController {

    private StudentView view;
    private InputController inputController;

    public StudentController() {
        view = new StudentView();
        inputController = new InputController();
    }

    private StudentModel getStudent(int idLogin) {
        StudentDao studentDao = new StudentDao();
        return studentDao.getStudentByIdLogin(idLogin);
    }

    private ItemModel selectArtifact() {
        ItemDao itemDao = new ItemDao();
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
        int userChoice = 0;
        while (userChoice != 5) {
            view.displayStudentMenu();
            userChoice = inputController.getIntInput("SELECT AN OPTION: ");
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
                    //4 - See your level of experience\n"; TEŻ MIELIŚMY CIĄĆ
                    break;
                default:
                    break;
            }
        }
    }
}