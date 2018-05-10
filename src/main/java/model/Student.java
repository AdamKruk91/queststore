package model;


public class Student extends User {
    private Wallet wallet;
    private String userCategory = "Student";

    public Student(String login, String password,
                   String name, String surname, String email) {
        super(login, password, name, surname, email);
    }

    public Student(int ID, String login, String password,
                   String name, String surname, String email) {
        super(ID, login, password, name, surname, email);
        wallet = new Wallet(ID);
    }

    public Student(int ID, String login, String password,
                   String name, String surname, String email, Wallet wallet) {
        super(ID, login, password, name, surname, email);
        this.wallet = wallet;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public Wallet getWallet() {
        return wallet;
    }

}
