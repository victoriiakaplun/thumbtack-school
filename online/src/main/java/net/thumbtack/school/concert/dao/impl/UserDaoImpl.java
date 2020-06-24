package net.thumbtack.school.concert.dao.impl;

import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.User;

import static net.thumbtack.school.concert.data.DataBase.getDataBase;

public class UserDaoImpl implements UserDao {
    public UserDaoImpl() {
    }

    @Override
    public void insert(User user) throws BaseServerException {
        getDataBase().insertUser(user);
    }

    @Override
    public void login(String login, String uuidString) throws BaseServerException {
        getDataBase().insertUuid(login, uuidString);
    }

    @Override
    public User getUserByLogin(String login) {
        return getDataBase().getUserByLogin(login);
    }

    @Override
    public User getUserByUuid(String uuidString) {
        return getDataBase().getUserByUuid(uuidString);
    }

    @Override
    public void deleteUuid(String uuidString) throws BaseServerException {
        getDataBase().deleteUuid(uuidString);
    }

    @Override
    public void leave(String requestJsonString) throws BaseServerException {
        getDataBase().leave(requestJsonString);
    }
}
