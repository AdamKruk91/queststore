package model;


public class StudentModel extends UserModel {

    private String groupName;
    private int myGroupId;
    private WalletModel myWallet;


    public StudentModel(String firstName, String lastName, String email, String password,
                        int myGroupId, WalletModel myWallet) {
        super(firstName, lastName, email, password);
        this.myWallet = myWallet;
        this.myGroupId = myGroupId;
    }

    public StudentModel(int id, String firstName, String lastName, String email, String password, String groupName, WalletModel myWallet) {
        super(id, firstName, lastName, email, password);
        this.myWallet = myWallet;
        this.groupName = groupName;
    }

    public WalletModel getMyWallet() {
        return myWallet;
    }

    public int getGroupId() {
        return this.myGroupId;
    }
}
    //public void setGroup(GroupModel group) { this.myGroup=group; }

