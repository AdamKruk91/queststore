package model;


public class WalletModel {

    private int totalCoolcoins;
    private int balance;
    private int id;

    public WalletModel() {
        this.totalCoolcoins = 0;
        this.balance = 0;
    }

    public WalletModel(int id, int totalCoolcoins, int balance) {
        this.id = id;
        this.totalCoolcoins = totalCoolcoins;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
    public int getId() {
        return id;
    }
    public int getTotalCoolcoins() {
        return totalCoolcoins;
    }
    public void setTotalCoolcoins(int value) {
        this.totalCoolcoins += value;
    }
    public void setBalance(int value) {
        this.balance += value;
    }
}