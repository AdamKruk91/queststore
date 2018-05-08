package model;


public class QuestModel extends UsableObjectModel {
    private int reward;
    
    public QuestModel(int id, String name, String description, String category, int reward) {
        super(id, name, description, category);
        this.reward = reward;
    }

    public QuestModel(String name, String description,String category, int reward) {
        super(name, description, category);
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}
    

