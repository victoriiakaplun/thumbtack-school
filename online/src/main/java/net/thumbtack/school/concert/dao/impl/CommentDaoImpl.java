package net.thumbtack.school.concert.dao.impl;

import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Comment;

import static net.thumbtack.school.concert.data.DataBase.getDataBase;

public class CommentDaoImpl implements CommentDao {

    public CommentDaoImpl() {
    }

    @Override
    public void insertComment(int songId, Comment comment) throws BaseServerException {
        getDataBase().insertComment(songId, comment);
    }

    @Override
    public void joinToComment(String uuidString, int songId, int commentId) throws BaseServerException {
        getDataBase().joinToComment(uuidString, songId, commentId);
    }

    @Override
    public void cancelJoining(String uuidString, int songId, int commentId) throws BaseServerException {
        getDataBase().cancelJoining(uuidString, songId, commentId);
    }

    @Override
    public void changeComment(String uuidString, int songId, int commentId, String changed) throws BaseServerException {
        getDataBase().changeComment(uuidString, songId, commentId, changed);
    }

}
