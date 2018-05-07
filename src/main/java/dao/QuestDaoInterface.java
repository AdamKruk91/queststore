package dao;

import model.QuestModel;

import java.util.List;

public interface QuestDaoInterface {
    void addQuest(QuestModel newQuest);
    void removeQuest(QuestModel removeQuest);
    void updateQuest(QuestModel updateQuest);
    List<String> getCategoriesName();
    QuestModel getByID(int id);
    void updateUserQuest(int user_id, int quest_id);
}
