package dao;

import java.util.List;

import exceptions.DataAccessException;
import model.MentorModel;

public interface MentorDaoInterface {

    void add(MentorModel mentor) throws DataAccessException;
    void update(MentorModel mentor) throws DataAccessException;
    List<MentorModel> getAll() throws DataAccessException;
    void delete(int id) throws DataAccessException;
    MentorModel get(int id) throws DataAccessException;
}
