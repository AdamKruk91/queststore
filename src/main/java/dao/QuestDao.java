package dao;

import exceptions.DataAccessException;
import model.QuestModel;

import java.sql.SQLException;
import java.util.List;

public class QuestDao implements QuestDaoInterface{
    @Override
    public void addQuest(QuestModel newQuest) throws DataAccessException {
        try{

        } catch (SQLException e){
            throw new DataAccessException("Fail to add Quest");
        }
    }

    @Override
    public void removeQuest(QuestModel removeQuest) throws DataAccessException {
        try{

        } catch (SQLException e){
            throw new DataAccessException("Fail to remove Quest");
        }
    }

    @Override
    public void updateQuest(QuestModel updateQuest) throws DataAccessException {
        try{

        } catch (SQLException e){
            throw new DataAccessException("Fail to update Quest");
        }
    }

    @Override
    public List<String> getCategoriesName() throws DataAccessException {
        try{
            return null;
        } catch (SQLException e){
            throw new DataAccessException("Fail to get categories names");
        }
    }

    @Override
    public QuestModel getByID(int id) throws DataAccessException {
        try{
            return null;
        } catch (SQLException e){
            throw new DataAccessException("Fail to get Quest");
        }
    }

    @Override
    public void updateUserQuest(int user_id, int quest_id) throws DataAccessException {
        try{

        } catch (SQLException e){
            throw new DataAccessException("Fail to update user Quest");
        }
    }
}
