package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Data;

public interface DataBaseDao {

    void initializeDataBase(Data data) throws BaseServerException;

    void start();
}
