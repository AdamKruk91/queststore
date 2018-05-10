package model;


public class Quest extends UsableObject {
    private int reward;
    
    public Quest(int id, String name, String description, String category, int reward) {
        super(id, name, description, category);
        this.reward = reward;
    }

    public Quest(String name, String description, String category, int reward) {
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
    

