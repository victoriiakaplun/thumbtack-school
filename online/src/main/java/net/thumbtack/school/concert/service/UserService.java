package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.dao.impl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.AuthorizeUserDtoRequest;
import net.thumbtack.school.concert.dto.request.LogoutUserDtoRequest;
import net.thumbtack.school.concert.dto.request.RegisterUserDtoRequest;
import net.thumbtack.school.concert.dto.response.AuthorizeUserDtoResponse;
import net.thumbtack.school.concert.dto.response.ErrorResponseDto;
import net.thumbtack.school.concert.dto.response.RegisterUserDtoResponse;
import net.thumbtack.school.concert.dto.response.SuccessDtoResponse;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.User;

import java.util.UUID;


public class UserService {

    private UserDao userDao;
    private Gson gson;

    public UserService() {
        userDao = new UserDaoImpl();
        gson = new Gson();
    }

    public String register(String requestJsonString) {
        try {
            RegisterUserDtoRequest registerUserDtoRequest = gson.fromJson(requestJsonString, RegisterUserDtoRequest.class);
            registerUserDtoRequest.validate();
            User user = new User(
                    registerUserDtoRequest.getFirstName(),
                    registerUserDtoRequest.getLastName(),
                    registerUserDtoRequest.getLogin(),
                    registerUserDtoRequest.getPassword()
            );
            userDao.insert(user);
            String uuid = UUID.randomUUID().toString();
            userDao.login(user.getLogin(), uuid);
            return gson.toJson(new RegisterUserDtoResponse(uuid), RegisterUserDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String authorize(String requestJsonString) {
        try {
            AuthorizeUserDtoRequest authorizeUserDtoRequest = gson.fromJson(requestJsonString, AuthorizeUserDtoRequest.class);
            authorizeUserDtoRequest.validate();
            User user = userDao.getUserByLogin(authorizeUserDtoRequest.getLogin());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            if (!user.getPassword().equals(authorizeUserDtoRequest.getPassword())) {
                return gson.toJson(new ErrorResponseDto(ServerErrorCode.WRONG_PASSWORD), ErrorResponseDto.class);
            }
            String uuid = UUID.randomUUID().toString();
            userDao.login(user.getLogin(), uuid);
            return gson.toJson(new AuthorizeUserDtoResponse(uuid), AuthorizeUserDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String logout(String requestJsonString) {
        try {
            LogoutUserDtoRequest logoutUserDtoRequest = gson.fromJson(requestJsonString, LogoutUserDtoRequest.class);
            logoutUserDtoRequest.validate();
            userDao.deleteUuid(logoutUserDtoRequest.getUuidString());
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);

        }
    }

    public String leave(String requestJsonString) throws BaseServerException {
        try {
            if (requestJsonString.isEmpty()) {
                return gson.toJson(new ErrorResponseDto(ServerErrorCode.EMPTY_TOKEN), ErrorResponseDto.class);
            }
            User user = userDao.getUserByUuid(requestJsonString);
            if (user == null) {
                return gson.toJson(new ErrorResponseDto(ServerErrorCode.USER_NOT_FOUND), ErrorResponseDto.class);
            }
            userDao.leave(requestJsonString);
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        }
    }
}


