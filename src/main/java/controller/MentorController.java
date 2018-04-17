package controller;

import dao.ItemDao;
import dao.StudentDao;
import dao.TransactionDao;
import dao.GroupDao;
import view.MentorView;
import model.StudentModel;
import model.WalletModel;
import model.GroupModel;
import model.ItemModel;
import model.QuestModel;
import model.ArtifactModel;

import java.util.List;


public class MentorController {

    private MentorView view;
    private InputController inputController;

    public MentorController() {
        view = new MentorView();
        inputController = new InputController();
    }

    public void controlMenuOptions() {
        int userChoice = 0;
        while (userChoice != 9) {
            view.displayMentorMenu();
            userChoice = inputController.getIntInput("SELECT AN OPTION: ");
            switch (userChoice) {
                case 1:
                    createStudent();
                    break;
                case 2:
                    createQuest();
                    break;
                case 3:
                    createArtifact();
                    break;
                case 4:
                    changePriceOfItem("Quest");
                    break;
                case 5:
                    changePriceOfItem("Artifact");
                    break; 
                case 6:
                    addValueForQuest("Quest");
                    break;
                case 7:
                    markItem("Artifact");
                    break;
                case 8:
                    displayStudentWallet();
                    break;
                default:
                    break;
            }
        }
    }

    private GroupModel selectGroup() {
        GroupDao groupDao = new GroupDao();
        List<GroupModel> allGroups =groupDao.getGroupsCollection();
        view.displayAllGroups(allGroups);
        int id = inputController.getIntInput("Enter id of the chosen group: ");
        GroupModel selectedGroup = null;
        for (GroupModel group: allGroups)
            if (group.getId() == id)
                selectedGroup = group;
        return selectedGroup;
    }

    private ItemModel selectItem(String type) {
        ItemDao itemDao = new ItemDao();
        List<ItemModel> itemCollection = itemDao.getItemCollectionByType(type);
        view.displayItemCollection(itemCollection);
        int id = inputController.getIntInput("Enter id of item: ");
        ItemModel matchedItem = null;
        for (ItemModel item: itemCollection)
            if (item.getID() == id)
                matchedItem = item;
        return matchedItem;
    }

    private void createStudent() {
        String studentName = inputController.getStringInput("Enter student name: ");
        String studentLastName = inputController.getStringInput("Enter student last name: ");
        String studentEmail = inputController.getStringInput("Enter student email: ");
        String studentPassword = inputController.getStringInput("Enter student password: ");
        GroupModel selectedGroup = selectGroup();
        int idGroup = selectedGroup.getId();
        StudentDao studentDao = new StudentDao();
        WalletModel wallet = new WalletModel();
        StudentModel student = new StudentModel(studentName, studentLastName, studentEmail, studentPassword, idGroup, wallet);
        studentDao.insertNewStudent(student);
    }

    private void createQuest() {
        String questName = inputController.getStringInput("Enter quest name: ");
        String questDescription = inputController.getStringInput("Enter quest description: ");
        int questValue = inputController.getIntInput("Enter quest value: ");
        QuestModel newQuest = new QuestModel("Quest", questName, questDescription, questValue);
        ItemDao itemDao = new ItemDao();
        itemDao.insertNewItem(newQuest);
    }

    private void createArtifact() {
        String artifactName = inputController.getStringInput("Enter artifact name: ");
        String artifactDescription = inputController.getStringInput("Enter artifact description: ");
        int artifactValue = inputController.getIntInput("Enter artifact value: ");
        ArtifactModel newArtifact = new ArtifactModel("Artifact", artifactName, artifactDescription, artifactValue);
        ItemDao itemDao = new ItemDao();
        itemDao.insertNewItem(newArtifact);
    }

    private StudentModel selectStudent() {
        StudentDao studentDao = new StudentDao();
        List<StudentModel> allStudents = studentDao.getStudentsCollection();
        view.displayAllStudents(allStudents);
        int id = inputController.getIntInput("Enter id of student: ");
        StudentModel matchedStudent = null;
        for (StudentModel student: allStudents)
            if (student.getID().equals(id))
                matchedStudent = student;
        return matchedStudent;
    }

    private void changePriceOfItem(String type) {
        ItemModel item = selectItem(type);
        int newPrice = inputController.getIntInput("Enter new price: ");
        item.setValue(newPrice);
        ItemDao itemDao = new ItemDao();
        itemDao.updateValueOfItem(item);
    }

    private ItemModel chooseItemToMark(String typeName) {
        StudentModel selectedStudent = selectStudent();
        int studentId = selectedStudent.getID();
        ItemDao itemDao = new ItemDao();
        List<ItemModel> itemCollection = itemDao.selectStudentsItems(studentId, typeName);
        view.displayItemCollection(itemCollection);
        int id = inputController.getIntInput("Enter id of item: ");
        ItemModel matchedItem = null;
        for (ItemModel item: itemCollection)
            if (item.getID() == id)
                matchedItem = item;
        return matchedItem;
    }

    private void markItem(String typeName) {
        ItemModel itemToMark = chooseItemToMark(typeName);
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.updateStatusOfTransaction(itemToMark);
    }

    private List<ItemModel> getStudentArtifacts(int id) {
        TransactionDao transactionDao = new TransactionDao();
        return transactionDao.getStudentArtifact(id);
    }

    private void  displayStudentWallet() {
        StudentModel student = selectStudent();
        List<ItemModel> studentArtifacts = getStudentArtifacts(student.getID());
        view.displayStudentWallet(student.getMyWallet());
        view.displayStudentArtifacts(studentArtifacts);
    }

    private void addValueForQuest(String typeName) {
        StudentDao studentDao = new StudentDao();
        StudentModel selectedStudent = selectStudent();
        ItemModel item = selectItem(typeName);
        int itemValue = item.getValue();
        selectedStudent.updateAccountBalance(itemValue);
        studentDao.updateWallet(selectedStudent);
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.insertTransaction(selectedStudent.getID(), item.getID());
    }
}