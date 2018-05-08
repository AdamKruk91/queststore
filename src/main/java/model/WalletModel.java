package model;


import java.util.ArrayList;

public class WalletModel {

    private final int ID;
    private int amount;
    private int totalCoinsEarned;
    private ArrayList<ArtifactModel> ownedArtifacts;

    public WalletModel(int ID){
        this.ID = ID;
        this.amount = 0;
        this.ownedArtifacts = new ArrayList<ArtifactModel>();
    }

    public WalletModel(int ID, int amount, int totalCoinsEarned, ArrayList<ArtifactModel> ownedArtifacts){
        this.ID = ID;
        this.amount = amount;
        this.totalCoinsEarned = totalCoinsEarned;
        this.ownedArtifacts = ownedArtifacts;
    }

    public int getID() {
        return ID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalCoinsEarned() {
        return totalCoinsEarned;
    }

    public void setTotalCoinsEarned(int totalCoinsEarned) {
        this.totalCoinsEarned = totalCoinsEarned;
    }

    public void addToArtifact(ArtifactModel artifact){
        ownedArtifacts.add(artifact);
    }

    public void removeFromArtifact(ArtifactModel artifact){
        ownedArtifacts.remove(artifact);
    }

    public Iterator getOwnedArtifactIterator(){
        return new Iterator<ArtifactModel>(ownedArtifacts);
    }
}