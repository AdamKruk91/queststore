package dao;

import java.sql.SQLException;
import java.util.List;

public interface MentorDaoInterface {

    List<MentorModel> getAllMentorsCollection();
    void insertNewMentor(MentorModel mentor) throws SQLException;
    void updateMentorTable(MentorModel mentor);
    void deleteMentor(int id);
    MentorModel getMentorById(int id);


}
