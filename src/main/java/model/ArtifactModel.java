package model;


public class ArtifactModel extends UsableObjectModel {

    private int price;
    private String status;

    public ArtifactModel(String name, String description, int price, String category, String status) {
        super(name, description, category);
        this.price = price;
        this.status = status;
    }

    public ArtifactModel(int id, String name, String description, int price, String category, String status) {
        super(id, name, description, category);
        this.price = price;
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
