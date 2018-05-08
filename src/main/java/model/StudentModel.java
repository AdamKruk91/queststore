package model;


public class StudentModel extends UserModel{
    private GroupModel group;
    private WalletModel wallet;
    private String userCategory = "Codecooler";

    public StudentModel(String login, String password,
                      String name, String surname, String email, GroupModel group) {
        super(login, password, name, surname, email);
        this.group = group;
    }

    public StudentModel(int ID, String login, String password,
                      String name, String surname, String email, GroupModel group) {
        super(ID, login, password, name, surname, email);
        this.group = group;
        wallet = new WalletModel(ID);
    }

    public StudentModel(int ID, String login, String password,
                      String name, String surname, String email, GroupModel group, WalletModel wallet) {
        super(ID, login, password, name, surname, email);
        this.group = group;
        this.wallet = wallet;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public GroupModel getGroup() {
        return group;
    }

    public void setGroup(GroupModel group) {
        this.group = group;
    }

    public WalletModel getWallet() {
        return wallet;
    }

}
