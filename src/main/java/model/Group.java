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

    public void addCodecooler(Student student){
        studentsList.add(student);
    }

    public void removeCodecooler(Student student){
        studentsList.remove(student);
    }

    public void addMentor(Mentor mentor){
        mentorsList.add(mentor);
    }

    public void removeMentor(Mentor mentor){
        mentorsList.remove(mentor);
    }

    public IteratorImpl getCodecoolersIterator(){
        IteratorImpl iterator = new IteratorImpl<Student>(studentsList);
        return iterator;
    }

    public IteratorImpl getMentorsIterator(){
        IteratorImpl iterator = new IteratorImpl<Mentor>(mentorsList);
        return iterator;
    }

    public List<Student> getStudents(){
        return studentsList;
    }
}