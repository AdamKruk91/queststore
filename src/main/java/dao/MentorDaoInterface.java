package dao;

import java.sql.SQLException;
import java.util.List;

import exceptions.DataAccessException;
import model.MentorModel;

public interface MentorDaoInterface {

    List<MentorModel> getAllMentorsCollection();
    void insertNewMentor(MentorModel mentor) throws SQLException;
    void updateMentorTable(MentorModel mentor);
    void deleteMentor(int id) throws DataAccessException;
    MentorModel getMentorById(int id);


}
