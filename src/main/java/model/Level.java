package model;

public class Level {
    private int id;
    private String name;
    private int experienceAmount;

    public Level(int id, String name, int experienceAmount) {
        this.id = id;
        this.name = name;
        this.experienceAmount = experienceAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public int getExperienceAmount() {
        return experienceAmount;
    }

    public void setExperienceAmount(int experienceAmount) {
        this.experienceAmount = experienceAmount;
    }
}
