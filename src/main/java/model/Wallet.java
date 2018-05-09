package model;


import java.util.ArrayList;

public class Wallet {

    private final int ID;
    private int amount;
    private int totalCoinsEarned;
    private ArrayList<Artifact> ownedArtifacts;

    public Wallet(int ID){
        this.ID = ID;
        this.amount = 0;
        this.ownedArtifacts = new ArrayList<Artifact>();
    }

    public Wallet(int ID, int amount, int totalCoinsEarned, ArrayList<Artifact> ownedArtifacts){
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

    public void addToArtifact(Artifact artifact){
        ownedArtifacts.add(artifact);
    }

    public void removeFromArtifact(Artifact artifact){
        ownedArtifacts.remove(artifact);
    }

    public IteratorImpl getOwnedArtifactIterator(){
        return new IteratorImpl<Artifact>(ownedArtifacts);
    }
}