package dao;

import exceptions.DataAccessException;
import model.QuestModel;

import java.util.List;

public interface QuestDaoInterface {
    void addQuest(QuestModel newQuest) throws DataAccessException;
    void removeQuest(QuestModel removeQuest) throws DataAccessException;
    void updateQuest(QuestModel updateQuest) throws DataAccessException;
    List<String> getCategoriesName() throws DataAccessException;
    QuestModel getByID(int id) throws DataAccessException;
    void updateUserQuest(int user_id, int quest_id) throws DataAccessException;
}
