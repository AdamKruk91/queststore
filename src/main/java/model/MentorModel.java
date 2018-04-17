package model;


public class MentorModel extends UserModel {

    private int idGroup;

    public MentorModel(int id, String firstName, String lastName, String email, String password, int idGroup) {
        super(id, firstName, lastName, email, password);
        this.idGroup = idGroup;
    }

    public MentorModel( String firstName, String lastName, String email, String password, int idGroup) {
        super(firstName, lastName, email, password);
        this.idGroup =idGroup ;
    }

    public int getIdGroup() {
        return idGroup;
    }
}
