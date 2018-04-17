package dao;

import model.MentorModel;

import java.util.List;

public interface MentorDaoInterface {

    List<MentorModel> getAllMentorsCollection();
    void insertNewMentor(MentorModel mentor);
    void updateMentorTable(MentorModel mentor);


}
