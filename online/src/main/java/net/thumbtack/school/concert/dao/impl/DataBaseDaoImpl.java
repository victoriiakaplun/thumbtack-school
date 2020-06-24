package net.thumbtack.school.concert.dao.impl;

import net.thumbtack.school.concert.dao.DataBaseDao;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Data;

import static net.thumbtack.school.concert.data.DataBase.getDataBase;

public class DataBaseDaoImpl implements DataBaseDao {

    public DataBaseDaoImpl() {
    }

    @Override
    public void initializeDataBase(Data data) throws BaseServerException {
        getDataBase().initialize(data);
    }

    @Override
    public void start() {
        getDataBase().start();
    }
}
