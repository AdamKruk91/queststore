package model;


public class Student extends User {
    private Group group;
    private Wallet wallet;
    private String userCategory = "Student";

    public Student(String login, String password,
                   String name, String surname, String email, Group group) {
        super(login, password, name, surname, email);
        this.group = group;
    }

    public Student(int ID, String login, String password,
                   String name, String surname, String email, Group group) {
        super(ID, login, password, name, surname, email);
        this.group = group;
        wallet = new Wallet(ID);
    }

    public Student(int ID, String login, String password,
                   String name, String surname, String email, Group group, Wallet wallet) {
        super(ID, login, password, name, surname, email);
        this.group = group;
        this.wallet = wallet;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Wallet getWallet() {
        return wallet;
    }

}
