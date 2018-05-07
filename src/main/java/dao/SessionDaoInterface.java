package dao;

public interface SessionDaoInterface {
    void addSession();
    void deleteSession();
    SessionModel getSessionByUserId (int user_id);
}
