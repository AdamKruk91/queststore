package dao;

import exceptions.DataAccessException;
import model.Quest;

import java.util.List;

public interface QuestDAO {
    void addQuest(Quest newQuest) throws DataAccessException;
    void removeQuest(Quest removeQuest) throws DataAccessException;
    void updateQuest(Quest updateQuest) throws DataAccessException;
    List<String> getCategoriesName() throws DataAccessException;
    Quest getByID(int id) throws DataAccessException;
    void updateUserQuest(int user_id, int quest_id) throws DataAccessException;
}
