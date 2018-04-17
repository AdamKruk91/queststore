package model;


public class StudentModel extends UserModel {

    private GroupModel group;
    private int myGroupId;
    private WalletModel myWallet;


    public StudentModel(String firstName, String lastName, String email, String password,
                        int myGroupId, WalletModel myWallet) {
        super(firstName, lastName, email, password);
        this.myWallet = myWallet;
        this.myGroupId = myGroupId;
    }

    public StudentModel(int id, String firstName, String lastName, String email, String password, GroupModel group, WalletModel myWallet) {
        super(id, firstName, lastName, email, password);
        this.myWallet = myWallet;
        this.group = group;
    }

    public WalletModel getMyWallet() {
        return myWallet;
    }

    public GroupModel getGroup() {
        return group;
    }

    public int getGroupId() {
        return this.myGroupId;
    }

    public void updateAccountBalance(int value){
        this.myWallet.setBalance(value);
        this.myWallet.setTotalCoolcoins(value);
    }
}


