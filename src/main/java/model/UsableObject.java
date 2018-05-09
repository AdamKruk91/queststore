package model;


public abstract class UsableObject {

    private int id;
    private String name;
    private String description;
    private String category;

    public UsableObject(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    public UsableObject(int id, String name, String description, String category) {
        this(name, description, category);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getID() {return this.id; }
}