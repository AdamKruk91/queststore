package model;


public abstract class ItemModel {
    
    private String type;
    private String name;
    private String description;
    private int ID;
    private int value;

    public ItemModel(String type, String name, String description, int value) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.value = value;
    }
    
    public ItemModel(int ID, String type, String name, String description, int value) {
        this(type, name, description, value);
        this.ID = ID;
    }

    public String getName() {
        return this.name;
    }
    public String getType() {
        return this.type;
    }
    public String getDescription() {
        return this.description;
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int newValue) {
        this.value = newValue;
    }
    public int getID() {return this.ID; }
}