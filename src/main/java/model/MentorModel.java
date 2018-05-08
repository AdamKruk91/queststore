package model;

import java.util.ArrayList;

public class MentorModel extends UserModel{

    private ArrayList<GroupModel> groupList;
    private String userCategory = "Mentor";

    public MentorModel(String login, String password,
                  String name, String surname, String email){
        super(login, password, name, surname, email);
        this.groupList = new ArrayList<GroupModel>();
    }

    public MentorModel(int ID, String login, String password,
                  String name, String surname, String email,
                  ArrayList<GroupModel> groupList){
        super(ID, login, password, name, surname, email);
        this.groupList = groupList;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }
}
