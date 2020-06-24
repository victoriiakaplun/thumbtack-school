package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Comment;

public interface CommentDao {

    void insertComment(int songId, Comment comment) throws BaseServerException;

    void joinToComment(String uuidString, int songId, int commentId) throws BaseServerException;

    void cancelJoining(String uuidString, int songId, int commentId) throws BaseServerException;

    void changeComment(String uuidString, int songId, int commentId, String changed) throws BaseServerException;
}
