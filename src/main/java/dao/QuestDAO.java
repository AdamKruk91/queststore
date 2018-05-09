package dao;

import exceptions.DataAccessException;
import model.Quest;

import java.util.List;

public interface QuestDAO {
    void add(Quest newQuest) throws DataAccessException;
    void remove(Quest removeQuest) throws DataAccessException;
    void update(Quest updateQuest) throws DataAccessException;
    List<String> getCategoriesName() throws DataAccessException;
    Quest get(int id) throws DataAccessException;
    void updateUserQuest(int user_id, int quest_id) throws DataAccessException;
}
