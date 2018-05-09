package dao;

import model.Session;

public interface SessionDAO {
    void addSession();
    void deleteSession();
    Session getSessionByUserId (int user_id);
}
