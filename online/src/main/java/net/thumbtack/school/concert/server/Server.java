package net.thumbtack.school.concert.server;

import com.google.gson.Gson;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.service.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static net.thumbtack.school.concert.data.DataBase.getDataBase;

public class Server {
    private DataBaseService dataBaseService;
    private boolean isStarted;
    private UserService userService;
    private SongService songService;
    private CommentService commentService;
    private RatingService ratingService;


    public Server() {
    }

    /**
     * запуск сервера
     */
    public void startServer(String savedDataFileName) throws IOException, BaseServerException {
        dataBaseService = new DataBaseService();
        userService = new UserService();
        songService = new SongService();
        commentService = new CommentService();
        ratingService = new RatingService();
        dataBaseService.start();
        if (savedDataFileName != null) {
            dataBaseService.initializeDataBase(savedDataFileName);
        }
        isStarted = true;
    }

    /**
     * остановка сервера
     */
    public void stopServer(String savedDataFileName) throws IOException {
        if (isStarted) {
            if (savedDataFileName != null) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(savedDataFileName))) {
                    Gson gson = new Gson();
                    gson.toJson(getDataBase(), bw);
                }
            }
            isStarted = false;
        }
    }

    /**
     * регистрация пользователя
     */
    public String registerUser(String requestJsonString) {
        return userService.register(requestJsonString);
    }

    /**
     * покинуть сервер
     */
    public String leaveUser(String requestJsonString) throws BaseServerException {
        return userService.leave(requestJsonString);
    }

    /**
     * вход
     */
    public String authorizeUser(String requestJsonString) {
        return userService.authorize(requestJsonString);
    }

    /**
     * выход
     */
    public String logoutUser(String requestJsonString) {
        return userService.logout(requestJsonString);
    }

    /**
     * добавить песню
     */
    public String addSongs(String requestJsonString) {
        return songService.add(requestJsonString);
    }

    /**
     * отменить предложение песни
     */
    public String cancelSong(String requestJsonString) {
        return songService.cancel(requestJsonString);
    }

    /**
     * выставить оценку
     */
    public String rateSong(String requestJsonString) {
        return ratingService.rate(requestJsonString);
    }

    /**
     * изменить оценку
     */
    public String changeSongRating(String requestJsonString) {
        return ratingService.rate(requestJsonString);
    }

    /**
     * удалить оценку
     */
    public String cancelSongRating(String requestJsonString) {
        return ratingService.cancel(requestJsonString);
    }

    /**
     * оставить комментарий к предложению
     */
    public String addСomment(String requestJsonString) {
        return commentService.addComment(requestJsonString);
    }

    /**
     * изменить комментарий
     */
    public String changeComment(String requestJsonString) {
        return commentService.changeComment(requestJsonString);
    }

    /**
     * присоединиться к коментарию другого автора
     */
    public String joinToComment(String requestJsonString) {
        return commentService.join(requestJsonString);
    }

    /**
     * отменить присоединение к комментарию
     */
    public String cancelJoiningToComment(String requestJsonString) {
        return commentService.cancelJoining(requestJsonString);
    }

    /**
     * получить все песни
     */
    public String getAllSongs(String requestJsonString) throws BaseServerException {
        return songService.getAllSongs(requestJsonString);
    }

    /**
     * получить все песни указанного композитора(ов)
     */
    public String getAllSongsOfComposers(String requestJsonString) {
        return songService.getAllSongsByComposers(requestJsonString);
    }

    /**
     * получить все песни указанного автора(ов) слов
     */
    public String getAllSongsOfPoets(String requestJsonString) {
        return songService.getAllSongsByPoets(requestJsonString);
    }

    /**
     * получить все песни исполнителя
     */
    public String getAllSongsOfArtist(String requestJsonString) {
        return songService.getAllSongsByArtist(requestJsonString);
    }

    /**
     * получить программу концерта
     */
    public String getConcertProgram(String requestJsonString) throws BaseServerException {
        return songService.getProgram(requestJsonString);
    }
}
