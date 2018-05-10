package model;

public class Admin extends User {

    private String userCategory = "Admin";

    public Admin(int ID, String login, String password, String name, String surname, String email){
        super(ID, login, password, name, surname, email);
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
}
