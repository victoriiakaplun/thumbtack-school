package net.thumbtack.school.concert.dao.impl;

import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.exception.BaseServerException;

import static net.thumbtack.school.concert.data.DataBase.getDataBase;

public class RatingDaoImpl implements RatingDao {

    public RatingDaoImpl() {
    }

    @Override
    public void insertRating(String uuidString, int rating, int songId) throws BaseServerException {
        getDataBase().insertRating(uuidString, rating, songId);
    }

    @Override
    public void cancelRating(String uuidString, int songId) throws BaseServerException {
        getDataBase().cancelRating(uuidString, songId);
    }
}
