package model;

import java.util.ArrayList;

public class Mentor extends User {

    private ArrayList<Group> groupList;
    private String userCategory = "Mentor";

    public Mentor(String login, String password,
                  String name, String surname, String email, ArrayList<Group> groupList){
        super(login, password, name, surname, email);
        this.groupList = groupList;
    }

    public Mentor(int ID, String login, String password,
                  String name, String surname, String email,
                  ArrayList<Group> groupList){
        super(ID, login, password, name, surname, email);
        this.groupList = groupList;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public void addGroup(Group group){
        groupList.add(group);
    }

    public void removeGroup(Group group){
        groupList.remove(group);
    }

    public IteratorImpl getGroupIterator() {
        IteratorImpl<Group> iterator = new IteratorImpl<Group>(groupList);
        return iterator;
    }
}
