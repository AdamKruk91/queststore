package model;


public abstract class User {

    private int ID;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;

    public User(int ID, String login, String password, String name, String surname, String email) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    public User(String login, String password, String name, String surname, String email) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public abstract String getUserCategory();

    public String getFullName(){
        return name + " " + surname;
    }

    public abstract void setUserCategory(String userCategory);
}

