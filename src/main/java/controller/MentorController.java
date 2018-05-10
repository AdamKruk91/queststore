package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import dao.*;
import exceptions.DataAccessException;
import model.*;
import view.MentorView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;


public class MentorController extends AbstractContoller implements HttpHandler {

    private LoginDAO loginDao = new LoginDAOSQL();
    private ArtifactDAO artifactDao = new ArtifactDAOSQL();
    private MentorDAO mentorDao = new MentorDAOSQL();
    private MentorView view = new MentorView();

    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (isCookieValid(httpExchange)) {
                int userID = getLoginIdFromCookie(httpExchange);
                String userType = loginDao.getUserCategory(userID);
                if (!userType.equals("Mentor")) {
                    redirectTo(httpExchange, "/login");
                } else {
                    handleRendering(httpExchange, userID);
                }
            } else {
                redirectTo(httpExchange, "/login");
            }
            System.out.println("Success i guess");
        } catch (DataAccessException e) {
            e.printStackTrace();
            // TODO : display error message in browser
        }
    }

    private void handleRendering(HttpExchange httpExchange, int userID) throws IOException {

        final String URI = httpExchange.getRequestURI().toString();

        if (URI.contains("/static")) {
            redirectTo(httpExchange, URI.substring(URI.indexOf("/static")));

        } else if (URI.startsWith("/mentor/request/accept/")) {
            acceptRequest(httpExchange);
        } else if (URI.startsWith("/mentor/request/cancel/")) {
            cancelRequest(httpExchange);
        } else {

            switch (URI) {
//                case "/mentor":
//                    renderProfile(httpExchange, userID);
//                    break;
                case "/mentor/request":
                    renderRequest(httpExchange, userID);
                    break;
                case "/mentor/create-artifact":
                    handleCreateArtifact(httpExchange);
                    break;
//                case "/student/wallet/used":
//                    renderWalletUsed(httpExchange, userID);
//                    break;
//
                default:
                    System.out.println("Wrong address:" + URI);
            }
        }
    }

    private void handleCreateArtifact(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        if (method.equals("POST")) {
            try {
                Artifact artifact = createArtifactFromISR(httpExchange);
                artifactDao.add(artifact);
                createArtifact(httpExchange);
                //TODO: message if positive
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        } else {
            createArtifact(httpExchange);
        }
    }

    private Artifact createArtifactFromISR(HttpExchange httpExchange) throws IOException {
            Map<String, String> inputs = getMapFromISR(httpExchange);
            String name = inputs.get("name");
            String description = inputs.get("description");
            int price = Integer.valueOf(inputs.get("price"));
            return new Artifact(name, description, price, "Normal");
        }


    private void createArtifact(HttpExchange httpExchange) throws IOException {
        String response = view.getCreateArtifactScreen();
        handlePositiveResponse(httpExchange, response);
    }



    private void renderRequest(HttpExchange httpExchange, int userID) throws IOException {
        try {
            Mentor mentor = mentorDao.get(userID);
            String response = view.getRequestScreen(mentor);
            httpExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (DataAccessException e) {
            e.printStackTrace();
            //TODO: add error page
        }
    }

    private void acceptRequest(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/mentor/request/accept/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        useRequest(artifactID, "Used");
        redirectTo(httpExchange,"/mentor/request");
    }

    private void cancelRequest(HttpExchange httpExchange) throws IOException {
        final String URI = httpExchange.getRequestURI().toString();
        String artifactStrID = URI.replace("/mentor/request/cancel/", "");
        int artifactID = Integer.parseInt(artifactStrID);
        useRequest(artifactID, "In wallet");
        redirectTo(httpExchange,"/mentor/request");
    }

    private void useRequest(int artifactID, String status){
        try {
            Artifact artifact = artifactDao.getInstantiatedArtifact(artifactID);
            artifact.setStatus(status);
            artifactDao.updateArtifactStatus(artifact);
        }catch(DataAccessException e){
            e.printStackTrace();
            //TODO: display error
        }

}

//    private MentorView view;
//    private InputController inputController;
//    private StudentDAOSQL studentDao = new StudentDAOSQL();
//    LoginDAOSQL loginDao = new LoginDAOSQL();
//
//    public MentorController() {
//        view = new MentorView();
//        inputController = new InputController();
//    }
//
//    public void controlMenuOptions() throws SQLException {
//        boolean whileRunning = true;
//        while (whileRunning) {
//            view.displayMentorMenu();
//            int userChoice = inputController.getIntInput("SELECT AN OPTION: ");
//            switch (userChoice) {
//                case 1:
//                    createStudent();
//                    break;
//                case 2:
//                    createQuest();
//                    break;
//                case 3:
//                    createArtifact();
//                    break;
//                case 4:
//                    changePriceOfItem("Quest");
//                    break;
//                case 5:
//                    changePriceOfItem("Artifact");
//                    break;
//                case 6:
//                    markStudentQuest("Quest");
//                    break;
//                case 7:
//                    markItem("Artifact");
//                    break;
//                case 8:
//                    displayStudentWallet();
//                    break;
//                case 9:
//                    showAllStudents();
//                    break;
//                case 10:
//                    editStudent();
//                    break;
//                case 11:
//                    deleteStudent();
//                    break;
//                case 0:
//                    whileRunning = false;
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    private Group selectGroup() {
//        GroupDAOSQL groupDao = new GroupDAOSQL();
//        List<Group> allGroups =groupDao.getGroupsCollection();
//        view.displayAllGroups(allGroups);
//        int id = inputController.getIntInput("Enter id of the chosen group: ");
//        Group selectedGroup = null;
//        for (Group group: allGroups)
//            if (group.getId() == id)
//                selectedGroup = group;
//        return selectedGroup;
//    }
//
//    private UsableObject selectItem(String type) throws SQLException {
//        ItemDao itemDao = new ItemDao();
//        List<UsableObject> itemCollection = itemDao.getItemCollectionByType(type);
//        view.displayItemCollection(itemCollection);
//        int id = inputController.getIntInput("Enter id of item: ");
//        UsableObject matchedItem = null;
//        for (UsableObject item: itemCollection)
//            if (item.getID() == id)
//                matchedItem = item;
//        return matchedItem;
//    }
//
//    private void createStudent() throws SQLException {
//        String studentName = inputController.getStringInput("Enter student name: ");
//        String studentLastName = inputController.getStringInput("Enter student last name: ");
//        String studentEmail = inputController.getStringInput("Enter student email: ");
//        String studentPassword = inputController.getStringInput("Enter student password: ");
//        Group selectedGroup = selectGroup();
//        int idGroup = selectedGroup.getID();
//        StudentDAOSQL studentDao = new StudentDAOSQL();
//        Wallet wallet = new Wallet();
//        Student student = new Student(studentName, studentLastName, studentEmail, studentPassword, idGroup, wallet);
//        studentDao.addStudent(student);
//    }
//
//    private void createQuest() throws SQLException {
//        String questName = inputController.getStringInput("Enter quest name: ");
//        String questDescription = inputController.getStringInput("Enter quest description: ");
//        int questValue = inputController.getIntInput("Enter quest value: ");
//        Quest newQuest = new Quest("Quest", questName, questDescription, questValue);
//        ItemDao itemDao = new ItemDao();
//        itemDao.insertNewItem(newQuest);
//    }
//
//    private void createArtifact() throws SQLException {
//        String artifactName = inputController.getStringInput("Enter artifact name: ");
//        String artifactDescription = inputController.getStringInput("Enter artifact description: ");
//        int artifactValue = inputController.getIntInput("Enter artifact value: ");
//        Artifact newArtifact = new Artifact("Artifact", artifactName, artifactDescription, artifactValue);
//        ItemDao itemDao = new ItemDao();
//        itemDao.insertNewItem(newArtifact);
//    }
//
//    private Student selectStudent() {
//        List<Student> allStudents = studentDao.getStudentsCollection();
//        view.displayAllStudents(allStudents);
//        int id = inputController.getIntInput("Enter id of student: ");
//        Student matchedStudent = null;
//        for (Student student: allStudents)
//            if (student.getID().equals(id))
//                matchedStudent = student;
//        return matchedStudent;
//    }
//
//    private void changePriceOfItem(String type) throws SQLException {
//        UsableObject item = selectItem(type);
//        int newPrice = inputController.getIntInput("Enter new price: ");
//        item.setValue(newPrice);
//        ItemDao itemDao = new ItemDao();
//        itemDao.updateValueOfItem(item);
//    }
//
//    private UsableObject chooseItemToMark(String typeName) throws SQLException {
//        Student selectedStudent = selectStudent();
//        int studentId = selectedStudent.getID();
//        ItemDao itemDao = new ItemDao();
//        List<UsableObject> itemCollection = itemDao.selectStudentsItems(studentId, typeName);
//        view.displayItemCollection(itemCollection);
//        int id = inputController.getIntInput("Enter id of item: ");
//        UsableObject matchedItem = null;
//        for (UsableObject item: itemCollection)
//            if (item.getID() == id)
//                matchedItem = item;
//        return matchedItem;
//    }
//
//    private void markItem(String typeName) throws SQLException {
//        UsableObject itemToMark = chooseItemToMark(typeName);
//        TransactionDao transactionDao = new TransactionDao();
//        transactionDao.updateStatusOfTransaction(itemToMark, 1);
//    }
//
//    private List<UsableObject> getStudentArtifacts(int id) {
//        TransactionDao transactionDao = new TransactionDao();
//        return transactionDao.getStudentArtifact(id);
//    }
//
//    private void  displayStudentWallet() {
//        Student student = selectStudent();
//        List<UsableObject> studentArtifacts = getStudentArtifacts(student.getID());
//        view.displayStudentWallet(student.getMyWallet());
//        view.displayStudentArtifacts(studentArtifacts);
//    }
//
//    private void markStudentQuest(String typeName) throws SQLException {
//        StudentDAOSQL studentDao = new StudentDAOSQL();
//        Student selectedStudent = selectStudent();
//        UsableObject item = selectItem(typeName);
//        int itemValue = item.getValue();
//        selectedStudent.updateAccountBalance(itemValue);
//        studentDao.updateWallet(selectedStudent);
//        TransactionDao transactionDao = new TransactionDao();
//        transactionDao.insertTransaction(selectedStudent.getID(), item.getID());
//    }
//
//    private void showAllStudents(){
//        List<Student> allStudents = studentDao.getStudentsCollection();
//        System.out.println(allStudents.size());
//        view.displayAllStudents(allStudents);
//    }
//
//    private void deleteStudent(){
//        Student student = selectStudent();
//        studentDao.deleteStudent(student.getID());
//        studentDao.deleteWallet(student.getID());
//        loginDao.removeLoginByMail(student.getEmail());
//    }
//
//    private void editStudent() throws SQLException {
//        Student studentToEdit = selectStudent();
//        String mentorLogin = studentToEdit.getEmail();
//        String mentorPassword = studentToEdit.getPassword();
//        boolean isChoosed =  true;
//        while (isChoosed) {
//            view.displayEditStudentMenu();
//            int userChoice = inputController.getIntInput("Select field number to edit: ");
//            switch (userChoice) {
//                case 1:
//                    String name = inputController.getStringInput("Enter student name:");
//                    studentToEdit.setName(name);
//                    break;
//                case 2:
//                    String lastName = inputController.getStringInput("Enter student last name");
//                    studentToEdit.setLastName(lastName);
//                    break;
//                case 3:
//                    String email = inputController.getStringInput("Enter student email");
//                    studentToEdit.setEmail(email);
//                    break;
//                case 4:
//                    String password = inputController.getStringInput("Enter student password");
//                    studentToEdit.setPassword(password);
//                    break;
//                case 5:
//                    Group groupModel = selectGroup();
//                    studentToEdit.setGroup(groupModel);
//                case 6:
//                    isChoosed = false;
//                    break;
//                default:
//                    break;
//            }
//        }
//        updateStudentData(studentToEdit);
//        updateLoginData(studentToEdit, mentorLogin, mentorPassword);
//    }
//
//    private void updateStudentData(Student student){
//        studentDao.updateStudent(student);
//    }
//
//    private void updateLoginData(Student student, String login, String password) throws SQLException {
//        loginDao.updateLoginTable(student, login, password);
//    }
}