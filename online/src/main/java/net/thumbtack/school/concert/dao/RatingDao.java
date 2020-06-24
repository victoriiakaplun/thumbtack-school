package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.BaseServerException;

public interface RatingDao {

    void insertRating(String uuidString, int rating, int songId) throws BaseServerException;

    void cancelRating(String uuidString, int songId) throws BaseServerException;
}
