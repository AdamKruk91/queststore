package model;


public class ArtifactModel extends ItemModel { 
    
    private boolean used;

    public ArtifactModel(int id, String type, String name, String description, int value) {
        super(id, type, name, description, value);
        this.used = false;    
    }

    public ArtifactModel(String type, String name, String description, int value) {
        super(type, name, description, value);
    }
    
    public boolean getIsUsed() {
        return this.used;
    }
}
