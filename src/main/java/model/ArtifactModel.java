package model;


public class ArtifactModel extends UsableObjectModel {

    private int price;

    public ArtifactModel(int id, String name, String description, int price, String category) {
        super(id, name, description, category);
        this.price = price;
    }

    public ArtifactModel(String name, String description, int price, String category) {
        super(name, description, category);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
