package model;


public class QuestModel extends ItemModel { 
    
    public QuestModel(int id, String type, String name, String description, int value) {
        super(id, type, name, description, value);
    }

    public QuestModel(String type, String name, String description, int value) {
        super(type, name, description, value);
    }
}
    

