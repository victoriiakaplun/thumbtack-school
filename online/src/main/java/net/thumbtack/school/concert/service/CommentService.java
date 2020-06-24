package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.dao.CommentDao;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.dao.impl.CommentDaoImpl;
import net.thumbtack.school.concert.dao.impl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.AddCommentDtoRequest;
import net.thumbtack.school.concert.dto.request.ChangeCommentDtoRequest;
import net.thumbtack.school.concert.dto.request.JoiningCommentDtoRequest;
import net.thumbtack.school.concert.dto.response.ErrorResponseDto;
import net.thumbtack.school.concert.dto.response.SuccessDtoResponse;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.User;

public class CommentService {

    private CommentDao commentDao;
    private UserDao userDao;
    private Gson gson;

    public CommentService() {
        commentDao = new CommentDaoImpl();
        userDao = new UserDaoImpl();
        gson = new Gson();
    }


    public String addComment(String requestJsonString) {
        try {
            AddCommentDtoRequest addCommentDtoRequest = gson.fromJson(requestJsonString, AddCommentDtoRequest.class);
            addCommentDtoRequest.validate();
            User user = userDao.getUserByUuid(addCommentDtoRequest.getUuidString());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            int songId = addCommentDtoRequest.getSongId();
            Comment comment = new Comment(songId, user.getLogin(), addCommentDtoRequest.getComment());
            commentDao.insertComment(songId, comment);
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        }
    }

    public String join(String requestJsonString) {
        try {
            JoiningCommentDtoRequest joinToCommentDtoRequest = gson.fromJson(requestJsonString, JoiningCommentDtoRequest.class);
            joinToCommentDtoRequest.validate();
            commentDao.joinToComment(joinToCommentDtoRequest.getUuidString(),
                    joinToCommentDtoRequest.getSongId(),
                    joinToCommentDtoRequest.getCommentId()
            );
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String cancelJoining(String requestJsonString) {
        try {
            JoiningCommentDtoRequest joiningCommentDtoRequest = gson.fromJson(requestJsonString, JoiningCommentDtoRequest.class);
            joiningCommentDtoRequest.validate();
            commentDao.cancelJoining(joiningCommentDtoRequest.getUuidString(),
                    joiningCommentDtoRequest.getSongId(),
                    joiningCommentDtoRequest.getCommentId()
            );
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String changeComment(String requestJsonString) {
        try {
            ChangeCommentDtoRequest changeCommentDtoRequest = gson.fromJson(requestJsonString, ChangeCommentDtoRequest.class);
            changeCommentDtoRequest.validate();
            if (userDao.getUserByUuid(changeCommentDtoRequest.getUuidString()) == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            commentDao.changeComment(changeCommentDtoRequest.getUuidString(),
                    changeCommentDtoRequest.getSongId(),
                    changeCommentDtoRequest.getCommentId(),
                    changeCommentDtoRequest.getChanged());
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }
}
