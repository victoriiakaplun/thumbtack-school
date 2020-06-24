package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.User;

public interface UserDao {

    void insert(User user) throws BaseServerException;

    void login(String login, String uuidString) throws BaseServerException;

    User getUserByLogin(String login) throws BaseServerException;

    User getUserByUuid(String uuidString) throws BaseServerException;

    void deleteUuid(String uuidString) throws BaseServerException;

    void leave(String requestJsonString) throws BaseServerException;
}
