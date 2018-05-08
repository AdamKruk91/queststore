package model;

import java.util.ArrayList;

public class GroupModel {

    private int ID;
    private String name;

    private ArrayList<StudentModel> studentsList = new ArrayList<StudentModel>();
    private ArrayList<MentorModel> mentorsList = new ArrayList<MentorModel>();

    public GroupModel(String name){
        this.name = name;
    }

    public GroupModel(int ID, String name, ArrayList<StudentModel> studentsList, ArrayList<MentorModel> mentorsList){
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

    public void addCodecooler(StudentModel student){
        studentsList.add(student);
    }

    public void removeCodecooler(StudentModel student){
        studentsList.remove(student);
    }

    public void addMentor(MentorModel mentor){
        mentorsList.add(mentor);
    }

    public void removeMentor(MentorModel mentor){
        mentorsList.remove(mentor);
    }

    public Iterator getCodecoolersIterator(){
        Iterator iterator = new Iterator<StudentModel>(studentsList);
        return iterator;
    }

    public Iterator getMentorsIterator(){
        Iterator iterator = new Iterator<MentorModel>(mentorsList);
        return iterator;
    }
}