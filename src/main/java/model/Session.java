package model;

public class Session {

    private String sessionID;
    private int userID;

    public Session(String sessionID, int userID) {
        this.sessionID = sessionID;
        this.userID = userID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public int getUserID() {
        return userID;
    }
}
