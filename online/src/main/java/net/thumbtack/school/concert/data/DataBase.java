package net.thumbtack.school.concert.data;

import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.*;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataBase {
    private static DataBase dataBase = null;
    private DualHashBidiMap<String, User> usersMap;
    private DualHashBidiMap<String, String> sessionsMap;
    private DualHashBidiMap<Integer, Song> songIdMap;
    private DualHashBidiMap<String, List<Song>> songsByUserLogin;
    private DualHashBidiMap<Integer, List<Comment>> commentsMap;
    private DualHashBidiMap<Integer, Comment> commentIdMap;
    private int currentSongId;
    private int currentCommentId;

    private DataBase() {
    }

    public static DataBase getDataBase() {
        if (dataBase == null) {
            dataBase = new DataBase();
        }
        return dataBase;
    }

    public void start() {
        usersMap = new DualHashBidiMap<>();
        sessionsMap = new DualHashBidiMap<>();
        songsByUserLogin = new DualHashBidiMap<>();
        songIdMap = new DualHashBidiMap<>();
        commentsMap = new DualHashBidiMap<>();
        commentIdMap = new DualHashBidiMap<>();
        currentSongId = 0;
        currentCommentId = 0;
    }

    public void initialize(Data data) throws BaseServerException {
        if (data != null) {
            usersMap = new DualHashBidiMap<>(data.getUsersMap());
            sessionsMap = new DualHashBidiMap<>(data.getSessionsMap());
            songsByUserLogin = new DualHashBidiMap<>(data.getSongsByUserLogin());
            songIdMap = new DualHashBidiMap<>(data.getSongIdMap());
            commentsMap = new DualHashBidiMap<>(data.getCommentsMap());
            commentIdMap = new DualHashBidiMap<>(data.getCommentIdMap());
            currentSongId = data.getCurrentSongId();
            currentCommentId = data.getCurrentCommentId();
        } else {
            throw new BaseServerException(ServerErrorCode.DATA_BASE_ERROR);
        }
    }

    public void insertUser(User user) throws BaseServerException {
        if (usersMap.containsValue(user)) {
            throw new BaseServerException(ServerErrorCode.USER_ALREADY_EXIST);
        }
        usersMap.put(user.getLogin(), user);
    }

    public void insertUuid(String login, String uuidString) throws BaseServerException {
        if (!usersMap.containsKey(login)) {
            throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        sessionsMap.put(uuidString, login);
    }

    public void deleteUuid(String uuidString) throws BaseServerException {
        User user = getUserByUuid(uuidString);
        if (user == null) {
            throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        sessionsMap.remove(uuidString, user.getLogin());
    }

    public User getUserByUuid(String uuidString) {
        return usersMap.get(sessionsMap.get(uuidString));
    }

    private String getUuidByLogin(String login) {
        return sessionsMap.getKey(login);
    }

    public User getUserByLogin(String login) {
        return usersMap.get(login);
    }

    public void insertSong(Song song, String uuidString) throws BaseServerException {
        checkSession(uuidString);
        if (songIdMap.containsValue(song)) {
            throw new BaseServerException(ServerErrorCode.SONG_ALREADY_EXIST);
        }
        String login = getUserByUuid(uuidString).getLogin();
        song.getRatingsMap().put(login, 5);
        songIdMap.put(currentSongId, song);
        songsByUserLogin.compute(login, (author, songs) -> {
            if (songs == null) {
                List<Song> songsList = new ArrayList<>();
                songsList.add(song);
                return songsList;
            } else {
                songs.add(song);
                return songs;
            }
        });
        currentSongId++;
    }

    public void cancelSong(int songId, String uuidString) throws BaseServerException {
        User user = getUserByUuid(uuidString);
        if (user == null) {
            throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
        }
        checkSong(songId);
        String login = user.getLogin();
        Song song = songIdMap.get(songId);
        if (!songsByUserLogin.get(login).contains(song)) {
            throw new BaseServerException(ServerErrorCode.SONG_NOT_FOUND);
        }
        Map<String, Integer> ratings = song.getRatingsMap();
        if (ratings.size() != 1) {
            ratings.remove(login);
            if (!songsByUserLogin.containsKey(null)) {
                songsByUserLogin.put(null, new ArrayList<>());
            }
            songsByUserLogin.get(null).add(song);
        } else {
            commentsMap.remove(songId);
            commentIdMap.remove(songId);
            songIdMap.remove(songId);
        }
        songsByUserLogin.get(login).remove(song);
    }

    public void insertRating(String uuidString, int rating, int songId) throws BaseServerException {
        checkUser(sessionsMap.get(uuidString));
        checkSong(songId);
        String author = getUserByUuid(uuidString).getLogin();
        if (getAuthorBySong(songId).equals(author)) {
            throw new BaseServerException(ServerErrorCode.RATING_ALREADY_EXISTS);
        }
        songIdMap.get(songId).getRatingsMap().put(author, rating);
    }

    public void cancelRating(String uuidString, int songId) throws BaseServerException {
        String login = getUserByUuid(uuidString).getLogin();
        checkUser(login);
        checkSong(songId);
        Song song = songIdMap.get(songId);
        if (!song.getRatingsMap().containsKey(login)) {
            throw new BaseServerException(ServerErrorCode.RATING_NOT_FOUND);
        }
        if (songsByUserLogin.get(login).contains(song)) {
            throw new BaseServerException(ServerErrorCode.RATING_ALREADY_EXISTS);
        }
        song.getRatingsMap().remove(login);
    }

    public void insertComment(int songId, Comment comment) throws BaseServerException {
        checkSession(getUuidByLogin(comment.getAuthor()));
        checkUser(comment.getAuthor());
        checkSong(songId);
        commentsMap.computeIfAbsent(songId, k -> new ArrayList<>());
        if (commentsMap.get(songId).contains(comment)) {
            throw new BaseServerException(ServerErrorCode.COMMENT_ALREADY_EXISTS);
        }
        commentIdMap.put(currentCommentId, comment);
        commentsMap.get(songId).add(comment);
        currentCommentId++;
    }

    public void joinToComment(String uuidString, int songId, int commentId) throws BaseServerException {
        checkSession(uuidString);
        checkSong(songId);
        if (!commentIdMap.containsKey(commentId)) {
            throw new BaseServerException(ServerErrorCode.COMMENT_NOT_FOUND);
        }
        commentIdMap.get(commentId).getJoinedUsers().add(getUserByUuid(uuidString).getLogin());
    }

    private void checkSession(String uuidString) throws BaseServerException {
        if (!sessionsMap.containsKey(uuidString)) {
            throw new BaseServerException(ServerErrorCode.SESSION_NOT_FOUND);
        }
    }

    private void checkUser(String login) throws BaseServerException {
        if (!usersMap.containsKey(login)) {
            throw new BaseServerException(ServerErrorCode.USER_NOT_FOUND);
        }
    }

    private void checkSong(int songId) throws BaseServerException {
        if (!songIdMap.containsKey(songId)) {
            throw new BaseServerException(ServerErrorCode.SONG_NOT_FOUND);
        }
    }

    private String getAuthorBySong(int songId) throws BaseServerException {
        checkSong(songId);
        return songIdMap.get(songId).getAuthor();
    }

    private int getSongIdByCommentId(int commentId) {
        if (commentIdMap.containsKey(commentId)) {
            return commentIdMap.get(commentId).getSongId();
        }
        return -1;
    }

    public void cancelJoining(String uuidString, int songId, int commentId) throws BaseServerException {
        checkSession(uuidString);
        checkUser(getUserByUuid(uuidString).getLogin());
        checkSong(songId);

        if (!commentIdMap.containsKey(commentId)) {
            throw new BaseServerException(ServerErrorCode.COMMENT_NOT_FOUND);
        }
        commentIdMap.get(commentId).getJoinedUsers().remove(getUserByUuid(uuidString).getLogin());
    }

    public void changeComment(String uuidString, int songId, int commentId, String changed) throws BaseServerException {
        String login = getUserByUuid(uuidString).getLogin();
        checkUser(login);
        checkSong(songId);
        if (!commentIdMap.containsKey(commentId)) {
            throw new BaseServerException(ServerErrorCode.COMMENT_NOT_FOUND);
        }
        if (!login.equals(commentIdMap.get(commentId).getAuthor())) {
            throw new BaseServerException(ServerErrorCode.IMPOSSIBLE_CHANGE_COMMENT);
        }
        if (commentIdMap.get(commentId).getJoinedUsers().isEmpty()) {
            commentIdMap.get(commentId).setComment(changed);
        } else {
            commentIdMap.get(commentId).setAuthor(null);
            insertComment(songId, new Comment(songId, login, changed));
            currentCommentId++;
        }
    }

    public List<Song> getAllSongs() {
        if (songIdMap.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(songIdMap.values());
    }

    public List<Song> getAllSongsByComposers(List<String> filters) {
        List<Song> songList = new ArrayList<>();
        if (!songIdMap.isEmpty()) {
            songList = songIdMap
                    .values()
                    .stream()
                    .filter(song -> song.getComposers().stream().anyMatch(filters::contains))
                    .collect(Collectors.toList());
        }
        return songList;
    }

    public List<Song> getAllSongsByPoets(List<String> filters) {
        List<Song> songList = new ArrayList<>();
        if (!songIdMap.isEmpty()) {
            songList = songIdMap
                    .values()
                    .stream()
                    .filter(song -> song.getPoets().stream().anyMatch(filters::contains))
                    .collect(Collectors.toList());
        }
        return songList;
    }

    public List<Song> getAllSongsByArtist(String filter) {
        List<Song> songList = new ArrayList<>();
        if (!songIdMap.isEmpty()) {
            songList = songIdMap
                    .values()
                    .stream()
                    .filter(song -> song.getArtist().equals(filter))
                    .collect(Collectors.toList());
        }
        return songList;
    }

    private List<Comment> getCommentsListBySong(int songId) {

        return commentsMap.get(songId);
    }

    public List<ConcertData> getConcertData() throws BaseServerException {
        int summaryTime = 0;
        List<ConcertData> concertDataList = new ArrayList<>();
        List<Song> songList = new ArrayList<>(songIdMap.values());
        songList.sort(
                (o1, o2) -> o2.getRatingSum() - o1.getRatingSum()
        );
        for (Song song : songList) {
            int tmpSummary = summaryTime + song.getTime() + 10;
            if (tmpSummary <= 3600) {
                summaryTime += song.getTime() + 10;
                concertDataList.add(new ConcertData(song,
                                usersMap.get(getAuthorBySong(songIdMap.getKey(song))),
                                (double) (song.getRatingSum()) / song.getRatingsMap().size(),
                                getCommentsListBySong(songIdMap.getKey(song))
                        )
                );

            }
        }
        return concertDataList;
    }

    public void leave(String requestJsonString) throws BaseServerException {
        String filter = getUserByUuid(requestJsonString).getLogin();
        if (songsByUserLogin.containsKey(filter)) {
            for (Song song : songsByUserLogin.get(filter)) {
                cancelSong(songIdMap.getKey(song), requestJsonString);
            }
        }

        List<Comment> comments;
        if (!commentIdMap.isEmpty()) {
            comments = commentIdMap
                    .values()
                    .stream()
                    .filter(comment -> comment.getAuthor().equals(filter))
                    .collect(Collectors.toList());
            for (Comment comment : comments) {
                if (commentIdMap.containsValue(comment)) {
                    int commentId = commentIdMap.getKey(comment);
                    int songId = getSongIdByCommentId(commentId);
                    if (comment.getJoinedUsers().isEmpty()) {
                        commentIdMap.remove(commentId);
                        commentsMap.get(songId).remove(commentId);
                    } else {
                        commentIdMap.get(commentId).setAuthor(null);
                    }
                }
            }
        }
        List<Song> songs;
        songs = songIdMap
                .values()
                .stream()
                .filter(song -> song.getRatingsMap().keySet().stream()
                        .anyMatch(author -> author.equals(filter)))
                .collect(Collectors.toList());
        for (Song song : songs) {
            while (song.getRatingsMap().containsKey(filter)) {
                song.getRatingsMap().remove(filter);
            }
        }
        sessionsMap.remove(getUserByUuid(requestJsonString).getLogin());
        usersMap.remove(getUserByUuid(requestJsonString).getLogin());
    }
}

