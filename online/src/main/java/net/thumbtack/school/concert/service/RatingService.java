package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.dao.RatingDao;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.dao.impl.RatingDaoImpl;
import net.thumbtack.school.concert.dao.impl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.CancelRatingDtoRequest;
import net.thumbtack.school.concert.dto.request.RateDtoRequest;
import net.thumbtack.school.concert.dto.response.ErrorResponseDto;
import net.thumbtack.school.concert.dto.response.SuccessDtoResponse;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.User;

public class RatingService {

    private RatingDao ratingDao;
    private UserDao userDao;
    private Gson gson;

    public RatingService() {
        ratingDao = new RatingDaoImpl();
        userDao = new UserDaoImpl();
        gson = new Gson();
    }

    public String rate(String requestJsonString) {
        try {
            RateDtoRequest rateSongDtoRequest = gson.fromJson(requestJsonString, RateDtoRequest.class);
            rateSongDtoRequest.validate();
            User user = userDao.getUserByUuid(rateSongDtoRequest.getUuidString());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            ratingDao.insertRating(rateSongDtoRequest.getUuidString(), rateSongDtoRequest.getRating(), rateSongDtoRequest.getSongId());
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }


    public String cancel(String requestJsonString) {
        try {
            CancelRatingDtoRequest cancelRatingDtoRequest = gson.fromJson(requestJsonString, CancelRatingDtoRequest.class);
            cancelRatingDtoRequest.validate();
            User user = userDao.getUserByUuid(cancelRatingDtoRequest.getUuidString());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            ratingDao.cancelRating(cancelRatingDtoRequest.getUuidString(), cancelRatingDtoRequest.getSongId());
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }
}
