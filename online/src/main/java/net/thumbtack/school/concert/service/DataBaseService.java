package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import net.thumbtack.school.concert.dao.DataBaseDao;
import net.thumbtack.school.concert.dao.impl.DataBaseDaoImpl;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataBaseService {

    private DataBaseDao dataBaseDao;
    private Gson gson;

    public DataBaseService() {
        dataBaseDao = new DataBaseDaoImpl();
        gson = new Gson();
    }

    public void start() {
        dataBaseDao.start();
    }

    public void initializeDataBase(String savedDataFileName) throws IOException, BaseServerException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(savedDataFileName))) {
            dataBaseDao.initializeDataBase(gson.fromJson(bufferedReader, Data.class));
        }
    }
}
