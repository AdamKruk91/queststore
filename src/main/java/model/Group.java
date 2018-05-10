package model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int ID;
    private String name;

    private ArrayList<Student> studentsList = new ArrayList<Student>();
    private ArrayList<Mentor> mentorsList = new ArrayList<Mentor>();

    public Group(String name){
        this.name = name;
    }

    public Group(int ID, String name, ArrayList<Student> studentsList, ArrayList<Mentor> mentorsList){
        this.ID = ID;
        this.name = name;
        this.studentsList = studentsList;
        this.mentorsList = mentorsList;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents(){
        return studentsList;
    }

    public ArrayList<Mentor> getMentors() {
        return mentorsList;
    }
}