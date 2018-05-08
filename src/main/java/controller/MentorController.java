package controller;

import dao.*;
import view.MentorView;
import model.WalletModel;
import model.GroupModel;
import model.UsableObjectModel;
import model.QuestModel;
import model.ArtifactModel;

import java.sql.SQLException;
import java.util.List;


public class MentorController {

    private MentorView view;
    private InputController inputController;
    private StudentDao studentDao = new StudentDao();
    LoginDao loginDao = new LoginDao();

    public MentorController() {
        view = new MentorView();
        inputController = new InputController();
    }

    public void controlMenuOptions() throws SQLException {
        boolean whileRunning = true;
        while (whileRunning) {
            view.displayMentorMenu();
            int userChoice = inputController.getIntInput("SELECT AN OPTION: ");
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
                    markStudentQuest("Quest");
                    break;
                case 7:
                    markItem("Artifact");
                    break;
                case 8:
                    displayStudentWallet();
                    break;
                case 9:
                    showAllStudents();
                    break;
                case 10:
                    editStudent();
                    break;
                case 11:
                    deleteStudent();
                    break;
                case 0:
                    whileRunning = false;
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

    private UsableObjectModel selectItem(String type) throws SQLException {
        ItemDao itemDao = new ItemDao();
        List<UsableObjectModel> itemCollection = itemDao.getItemCollectionByType(type);
        view.displayItemCollection(itemCollection);
        int id = inputController.getIntInput("Enter id of item: ");
        UsableObjectModel matchedItem = null;
        for (UsableObjectModel item: itemCollection)
            if (item.getID() == id)
                matchedItem = item;
        return matchedItem;
    }

    private void createStudent() throws SQLException {
        String studentName = inputController.getStringInput("Enter student name: ");
        String studentLastName = inputController.getStringInput("Enter student last name: ");
        String studentEmail = inputController.getStringInput("Enter student email: ");
        String studentPassword = inputController.getStringInput("Enter student password: ");
        GroupModel selectedGroup = selectGroup();
        int idGroup = selectedGroup.getId();
        StudentDao studentDao = new StudentDao();
        WalletModel wallet = new WalletModel();
        StudentModel student = new StudentModel(studentName, studentLastName, studentEmail, studentPassword, idGroup, wallet);
        studentDao.addStudent(student);
    }

    private void createQuest() throws SQLException {
        String questName = inputController.getStringInput("Enter quest name: ");
        String questDescription = inputController.getStringInput("Enter quest description: ");
        int questValue = inputController.getIntInput("Enter quest value: ");
        QuestModel newQuest = new QuestModel("Quest", questName, questDescription, questValue);
        ItemDao itemDao = new ItemDao();
        itemDao.insertNewItem(newQuest);
    }

    private void createArtifact() throws SQLException {
        String artifactName = inputController.getStringInput("Enter artifact name: ");
        String artifactDescription = inputController.getStringInput("Enter artifact description: ");
        int artifactValue = inputController.getIntInput("Enter artifact value: ");
        ArtifactModel newArtifact = new ArtifactModel("Artifact", artifactName, artifactDescription, artifactValue);
        ItemDao itemDao = new ItemDao();
        itemDao.insertNewItem(newArtifact);
    }

    private StudentModel selectStudent() {
        List<StudentModel> allStudents = studentDao.getStudentsCollection();
        view.displayAllStudents(allStudents);
        int id = inputController.getIntInput("Enter id of student: ");
        StudentModel matchedStudent = null;
        for (StudentModel student: allStudents)
            if (student.getID().equals(id))
                matchedStudent = student;
        return matchedStudent;
    }

    private void changePriceOfItem(String type) throws SQLException {
        UsableObjectModel item = selectItem(type);
        int newPrice = inputController.getIntInput("Enter new price: ");
        item.setValue(newPrice);
        ItemDao itemDao = new ItemDao();
        itemDao.updateValueOfItem(item);
    }

    private UsableObjectModel chooseItemToMark(String typeName) throws SQLException {
        StudentModel selectedStudent = selectStudent();
        int studentId = selectedStudent.getID();
        ItemDao itemDao = new ItemDao();
        List<UsableObjectModel> itemCollection = itemDao.selectStudentsItems(studentId, typeName);
        view.displayItemCollection(itemCollection);
        int id = inputController.getIntInput("Enter id of item: ");
        UsableObjectModel matchedItem = null;
        for (UsableObjectModel item: itemCollection)
            if (item.getID() == id)
                matchedItem = item;
        return matchedItem;
    }

    private void markItem(String typeName) throws SQLException {
        UsableObjectModel itemToMark = chooseItemToMark(typeName);
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.updateStatusOfTransaction(itemToMark, 1);
    }

    private List<UsableObjectModel> getStudentArtifacts(int id) {
        TransactionDao transactionDao = new TransactionDao();
        return transactionDao.getStudentArtifact(id);
    }

    private void  displayStudentWallet() {
        StudentModel student = selectStudent();
        List<UsableObjectModel> studentArtifacts = getStudentArtifacts(student.getID());
        view.displayStudentWallet(student.getMyWallet());
        view.displayStudentArtifacts(studentArtifacts);
    }

    private void markStudentQuest(String typeName) throws SQLException {
        StudentDao studentDao = new StudentDao();
        StudentModel selectedStudent = selectStudent();
        UsableObjectModel item = selectItem(typeName);
        int itemValue = item.getValue();
        selectedStudent.updateAccountBalance(itemValue);
        studentDao.updateWallet(selectedStudent);
        TransactionDao transactionDao = new TransactionDao();
        transactionDao.insertTransaction(selectedStudent.getID(), item.getID());
    }

    private void showAllStudents(){
        List<StudentModel> allStudents = studentDao.getStudentsCollection();
        System.out.println(allStudents.size());
        view.displayAllStudents(allStudents);
    }

    private void deleteStudent(){
        StudentModel studentModel = selectStudent();
        studentDao.deleteStudent(studentModel.getID());
        studentDao.deleteWallet(studentModel.getID());
        loginDao.removeLoginByMail(studentModel.getEmail());
    }

    private void editStudent() throws SQLException {
        StudentModel studentToEdit = selectStudent();
        String mentorLogin = studentToEdit.getEmail();
        String mentorPassword = studentToEdit.getPassword();
        boolean isChoosed =  true;
        while (isChoosed) {
            view.displayEditStudentMenu();
            int userChoice = inputController.getIntInput("Select field number to edit: ");
            switch (userChoice) {
                case 1:
                    String name = inputController.getStringInput("Enter student name:");
                    studentToEdit.setName(name);
                    break;
                case 2:
                    String lastName = inputController.getStringInput("Enter student last name");
                    studentToEdit.setLastName(lastName);
                    break;
                case 3:
                    String email = inputController.getStringInput("Enter student email");
                    studentToEdit.setEmail(email);
                    break;
                case 4:
                    String password = inputController.getStringInput("Enter student password");
                    studentToEdit.setPassword(password);
                    break;
                case 5:
                    GroupModel groupModel = selectGroup();
                    studentToEdit.setGroup(groupModel);
                case 6:
                    isChoosed = false;
                    break;
                default:
                    break;
            }
        }
        updateStudentData(studentToEdit);
        updateLoginData(studentToEdit, mentorLogin, mentorPassword);
    }

    private void updateStudentData(StudentModel studentModel){
        studentDao.updateStudent(studentModel);
    }

    private void updateLoginData(StudentModel studentModel, String login, String password) throws SQLException {
        loginDao.updateLoginTable(studentModel, login, password);
    }
}