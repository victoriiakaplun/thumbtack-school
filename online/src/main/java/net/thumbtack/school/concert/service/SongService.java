package net.thumbtack.school.concert.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.dao.UserDao;
import net.thumbtack.school.concert.dao.impl.SongDaoImpl;
import net.thumbtack.school.concert.dao.impl.UserDaoImpl;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.CancelSongDtoRequest;
import net.thumbtack.school.concert.dto.request.FilterSongDtoRequest;
import net.thumbtack.school.concert.dto.request.SongDto;
import net.thumbtack.school.concert.dto.response.ConcertDataListDtoResponse;
import net.thumbtack.school.concert.dto.response.ErrorResponseDto;
import net.thumbtack.school.concert.dto.response.SongListDtoResponse;
import net.thumbtack.school.concert.dto.response.SuccessDtoResponse;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;

public class SongService {
    private SongDao songDao;
    private Gson gson;
    private UserDao userDao;

    public SongService() {
        userDao = new UserDaoImpl();
        songDao = new SongDaoImpl();
        gson = new Gson();
    }

    public String add(String requestJsonString) {
        try {
            AddSongDtoRequest addSongDtoRequest = gson.fromJson(requestJsonString, AddSongDtoRequest.class);
            addSongDtoRequest.validate();
            for (SongDto songDto : addSongDtoRequest.getSongList()) {
                Song song = new Song(userDao.getUserByUuid(addSongDtoRequest.getUuidString()).getLogin(),
                        songDto.getName(),
                        songDto.getComposers(),
                        songDto.getPoets(),
                        songDto.getArtist(),
                        songDto.getTime());
                songDao.insertSong(song, addSongDtoRequest.getUuidString());
            }
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }

    }

    public String cancel(String requestJsonString) {
        try {
            CancelSongDtoRequest cancelSongDtoRequest = gson.fromJson(requestJsonString, CancelSongDtoRequest.class);
            cancelSongDtoRequest.validate();
            songDao.cancelSong(cancelSongDtoRequest.getSongId(), cancelSongDtoRequest.getUuidString());
            return gson.toJson(new SuccessDtoResponse(), SuccessDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String getAllSongs(String requestJsonString) throws BaseServerException {
        if (requestJsonString.isEmpty()) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.EMPTY_TOKEN), ErrorResponseDto.class);
        }
        User user = userDao.getUserByUuid(requestJsonString);
        if (user == null) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.USER_NOT_FOUND), ErrorResponseDto.class);
        }
        return gson.toJson(new SongListDtoResponse(songDao.getAllSongs()), SongListDtoResponse.class);
    }

    public String getAllSongsByComposers(String requestJsonString) {
        try {
            FilterSongDtoRequest filterSongDtoRequest = gson.fromJson(requestJsonString, FilterSongDtoRequest.class);
            filterSongDtoRequest.validate();
            User user = userDao.getUserByUuid(filterSongDtoRequest.getUuidString());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            return gson.toJson(
                    new SongListDtoResponse(
                            songDao.getAllSongsByComposers(filterSongDtoRequest.getFilters())),
                    SongListDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String getAllSongsByPoets(String requestJsonString) {
        try {
            FilterSongDtoRequest filterSongDtoRequest = gson.fromJson(requestJsonString, FilterSongDtoRequest.class);
            filterSongDtoRequest.validate();
            User user = userDao.getUserByUuid(filterSongDtoRequest.getUuidString());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            return gson.toJson(
                    new SongListDtoResponse(
                            songDao.getAllSongsByPoets(filterSongDtoRequest.getFilters())),
                    SongListDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String getAllSongsByArtist(String requestJsonString) {
        try {
            FilterSongDtoRequest filterSongDtoRequest = gson.fromJson(requestJsonString, FilterSongDtoRequest.class);
            if (filterSongDtoRequest.getFilters().size() > 1) {
                throw new BaseServerException(ServerErrorCode.INVALID_SONG_FILTERS);
            }
            filterSongDtoRequest.validate();
            User user = userDao.getUserByUuid(filterSongDtoRequest.getUuidString());
            if (user == null) {
                throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
            }
            return gson.toJson(
                    new SongListDtoResponse(
                            songDao.getAllSongsByArtist(filterSongDtoRequest.getFilters().get(0))),
                    SongListDtoResponse.class);
        } catch (JsonSyntaxException e) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.INVALID_INPUT_DATA_FORMAT), ErrorResponseDto.class);
        } catch (BaseServerException e) {
            return gson.toJson(new ErrorResponseDto(e.getErrorCode()), ErrorResponseDto.class);
        }
    }

    public String getProgram(String requestJsonString) throws BaseServerException {
        if (requestJsonString.isEmpty()) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.EMPTY_TOKEN), ErrorResponseDto.class);
        }
        User user = userDao.getUserByUuid(requestJsonString);
        if (user == null) {
            return gson.toJson(new ErrorResponseDto(ServerErrorCode.USER_NOT_FOUND), ErrorResponseDto.class);
        }
        return gson.toJson(new ConcertDataListDtoResponse(songDao.getProgram()), ConcertDataListDtoResponse.class);
    }
}
