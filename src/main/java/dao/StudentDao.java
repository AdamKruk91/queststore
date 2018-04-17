package dao;

import model.GroupModel;
import model.StudentModel;
import model.WalletModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends ManipulationDao implements StudentDaoInterface {


    private int getIdStatus() {
        ResultSet result = selectDataFromTable("Status", "id_status", "name='Mentor'");
        return getIntFromResult(result, "id_status");
    }

    private String prepareGetAllStudentsSql() {
        String columns = "Login.email, Login.password, Student.id_student, first_name, last_name, Groups.id_group, id_wallet, total_coolcoins, balance, Groups.name AS group_name";
        String joinStmt1 = "Login.id_login=Student.id_login";
        String joinStmt2 = "Wallet.id_student=Student.id_student";
        String joinStmt3 = "Student.id_group = Groups.id_group";

        String sql = "SELECT " + columns + " FROM Student " +
                " JOIN Login  ON " + joinStmt1 +
                " JOIN Wallet  ON " + joinStmt2 +
                " JOIN Groups ON " + joinStmt3;
        return sql;
    }

    public StudentModel  getStudentByIdLogin(int idLogin) { // todo change method name, it's confusing becouse we get only id
        String columns = "Login.email, Login.password, Student.id_student, first_name, last_name, id_wallet, total_coolcoins, balance, Student.id_group, Groups.name AS group_name";
        String joinStmt1 = "Login.id_login=Student.id_login";
        String joinStmt2 = "Wallet.id_student=Student.id_student";
        String joinStmt3 = "Groups.id_group = Student.id_group";
        String condition = "Student.id_login=" +idLogin;

        String sql = "SELECT " + columns + " FROM Student JOIN Login ON " +  joinStmt1 +
                    " JOIN Wallet ON " + joinStmt2 +
                    " Join Groups ON " + joinStmt3 +
                    " WHERE " +  condition;
        ResultSet result = executeSelect(sql);
        return createStudentObject(result);
    }

    private StudentModel createStudentObject(ResultSet result) {
        StudentModel student = null;
        try {
            String email = result.getString("email");
            String password = result.getString("password");
            int id = result.getInt("id_student");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            int idWallet = result.getInt("id_wallet");
            int totalCoolcoins = result.getInt("total_coolcoins");
            int groupId = result.getInt("id_group");
            String groupName = result.getString("group_name");
            int balance = result.getInt("balance");
            WalletModel wallet = new WalletModel(idWallet, totalCoolcoins, balance);
            GroupModel group = new GroupModel(groupId, groupName);
            student = new StudentModel(id, firstName, lastName, email, password, group, wallet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public List<StudentModel> getStudentsCollection() {
        String sql = prepareGetAllStudentsSql();
        ResultSet result = executeSelect(sql);
        List<StudentModel> studentCollection = new ArrayList<>();
        try {
            while (result.next()) {
                StudentModel student = createStudentObject(result);
                studentCollection.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentCollection;
    }

    private void insertNewWallet(int idStudent) {
        String tableName = "Wallet";
        String columns = "(total_coolcoins, balance, id_student)";
        String values = "("+ 0 +", " + 0 +", " + idStudent + ")";
        insertDataIntoTable(tableName, columns, values);
    }

    private int insertNewLogin(String email, String password) {
        LoginDao loginDao = new LoginDao();
        int idStatus = loginDao.findStatusIdByName("Student");
        loginDao.insertNewLogin(email, password, idStatus);
        return loginDao.findLoginId(email, password);
    }

    public void insertNewStudent(StudentModel student) {
        int idLogin = insertNewLogin(student.getEmail(), student.getPassword());
        int id_group = student.getGroupId();
        String table = "Student";
        String columns = "(first_name, last_name, id_login, id_status, id_group)";
        int idStatus = getIdStatus();
        String values = "('" + student.getFirstName() + "', '" + student.getLastName() + "', " + idLogin +", "+ idStatus + ", " + id_group + ");";
        insertDataIntoTable(table, columns, values);
        insertNewWallet(student.getID());
    }

    public void updateWallet(StudentModel student){
        int balance = student.getMyWallet().getBalance();
        int totalCoolcoins = student.getMyWallet().getTotalCoolcoins();
        int idStudent = student.getID();
        updateDataInTable("Wallet", "balance="+ balance +", total_coolcoins=" + totalCoolcoins, "id_student=" + idStudent);
    }
}
