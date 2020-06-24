package net.thumbtack.school.concert.model;

import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.util.List;
import java.util.Map;

public class Data {
    private DualHashBidiMap<String, User> usersMap;
    private DualHashBidiMap<String, String> sessionsMap;
    private DualHashBidiMap<Integer, Song> songIdMap;
    private DualHashBidiMap<String, List<Song>> songsByUserLogin;
    private DualHashBidiMap<Integer, List<Comment>> commentsMap;
    private DualHashBidiMap<Integer, Comment> commentIdMap;
    private int currentSongId;
    private int currentCommentId;

    public Data() {
    }

    public DualHashBidiMap<String, User> getUsersMap() {
        return usersMap;
    }

    public DualHashBidiMap<String, String> getSessionsMap() {
        return sessionsMap;
    }

    public DualHashBidiMap<Integer, Song> getSongIdMap() {
        return songIdMap;
    }

    public DualHashBidiMap<String, List<Song>> getSongsByUserLogin() {
        return songsByUserLogin;
    }

    public Map<Integer, List<Comment>> getCommentsMap() {
        return commentsMap;
    }

    public DualHashBidiMap<Integer, Comment> getCommentIdMap() {
        return commentIdMap;
    }

    public int getCurrentSongId() {
        return currentSongId;
    }

    public int getCurrentCommentId() {
        return currentCommentId;
    }
}
