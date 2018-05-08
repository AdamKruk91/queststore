package model;

public class AdminModel extends UserModel {

    private String userCategory = "Creepy Guy";

    public AdminModel(int ID, String login, String password, String name, String surname, String email){
        super(ID, login, password, name, surname, email);
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
}
