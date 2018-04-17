package model;

public class GroupModel {

    private String groupName;
    private int id;

    public GroupModel(String name) {
        this.groupName = name;
    }
    public GroupModel(int id, String name){
        this.groupName = name;
        this.id = id;
    }
    public String getGroupName() {
        return this.groupName;
    }

    public int getId () {return this.id; }
}