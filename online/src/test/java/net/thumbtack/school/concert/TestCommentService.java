package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.dto.request.*;
import net.thumbtack.school.concert.dto.response.ErrorResponseDto;
import net.thumbtack.school.concert.dto.response.RegisterUserDtoResponse;
import net.thumbtack.school.concert.dto.response.SuccessDtoResponse;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestCommentService {
    private final Server server = new Server();
    private final Gson gson = new Gson();

    @Before
    public void setUp() throws IOException, BaseServerException {
        server.startServer(null);
    }

    @Test
    public void addCommentTest() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String responseJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(responseJsonString, RegisterUserDtoResponse.class).getUuid();
        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        int songId1 = 0;
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        int songId2 = 1;
        songs.add(songDto1);
        songs.add(songDto2);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        String requestAddComment = gson.toJson(new AddCommentDtoRequest(
                uuidString, songId1, "comment"));
        String responseAddComment = server.addСomment(requestAddComment);
        assertEquals("success", gson.fromJson(responseAddComment, SuccessDtoResponse.class).getResponse());
        String requestAddComment1 = gson.toJson(new AddCommentDtoRequest(
                UUID.randomUUID().toString(), songId1, "comment1"));
        String responseAddComment1 = server.addСomment(requestAddComment1);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseAddComment1, ErrorResponseDto.class).getMessage());

        String requestAddComment2 = gson.toJson(new AddCommentDtoRequest(
                uuidString, 3, "comment2"));
        String responseAddComment2 = server.addСomment(requestAddComment2);
        assertEquals(ServerErrorCode.SONG_NOT_FOUND.getErrorString(), gson.fromJson(responseAddComment2, ErrorResponseDto.class).getMessage());

        String requestAddComment3 = gson.toJson(new AddCommentDtoRequest(
                uuidString, songId1, "comment"));
        String responseAddComment3 = server.addСomment(requestAddComment3);
        assertEquals(ServerErrorCode.COMMENT_ALREADY_EXISTS.getErrorString(), gson.fromJson(responseAddComment3, ErrorResponseDto.class).getMessage());

        String requestAddComment4 = gson.toJson(new AddCommentDtoRequest(
                "", songId1, "comment4"));
        String responseAddComment4 = server.addСomment(requestAddComment4);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseAddComment4, ErrorResponseDto.class).getMessage());

        String requestAddComment5 = gson.toJson(new AddCommentDtoRequest(uuidString, -1, "comment5"));
        String responseAddComment5 = server.addСomment(requestAddComment5);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(), gson.fromJson(responseAddComment5, ErrorResponseDto.class).getMessage());

        String requestAddComment6 = gson.toJson(new AddCommentDtoRequest(
                uuidString, songId1, ""));
        String responseAddComment6 = server.addСomment(requestAddComment6);
        assertEquals(ServerErrorCode.EMPTY_COMMENT_STRING.getErrorString(), gson.fromJson(responseAddComment6, ErrorResponseDto.class).getMessage());
    }

    @Test
    public void testJoinToComment() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String responseJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(responseJsonString, RegisterUserDtoResponse.class).getUuid();
        String requestJsonString1 = gson.toJson(new User("bbbb", "cccc", "loginB", "456d"));
        String responseJsonString1 = server.registerUser(requestJsonString1);
        String uuidString1 = gson.fromJson(responseJsonString1, RegisterUserDtoResponse.class).getUuid();
        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        int songId1 = 0;
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        int songId2 = 1;
        songs.add(songDto1);
        songs.add(songDto2);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        String requestAddComment = gson.toJson(new AddCommentDtoRequest(
                uuidString, songId1, "comment"));
        String responseAddComment = server.addСomment(requestAddComment);
        int commentId = 0;

        String requestJoinToComment = gson.toJson(new JoiningCommentDtoRequest(uuidString1, songId1, commentId));
        String responseJoinToComment = server.joinToComment(requestJoinToComment);
        assertEquals("success", gson.fromJson(requestJoinToComment, SuccessDtoResponse.class).getResponse());

        String requestJoinToComment1 = gson.toJson(new JoiningCommentDtoRequest("", songId1, commentId));
        String responseJoinToComment1 = server.joinToComment(requestJoinToComment1);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseJoinToComment1, ErrorResponseDto.class).getMessage());

        String requestJoinToComment2 = gson.toJson(new JoiningCommentDtoRequest(uuidString1, -1, commentId));
        String responseJoinToComment2 = server.joinToComment(requestJoinToComment2);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(),
                gson.fromJson(responseJoinToComment2, ErrorResponseDto.class).getMessage());

        String requestJoinToComment3 = gson.toJson(new JoiningCommentDtoRequest(uuidString1,
                songId1,
                -1)
        );
        String responseJoinToComment3 = server.joinToComment(requestJoinToComment3);
        assertEquals(ServerErrorCode.WRONG_COMMENT_ID.getErrorString(),
                gson.fromJson(responseJoinToComment3, ErrorResponseDto.class).getMessage());

        String requestJoinToComment5 = gson.toJson(
                new JoiningCommentDtoRequest(uuidString1, 3, commentId));
        String responseJoinToComment5 = server.joinToComment(requestJoinToComment5);
        assertEquals(ServerErrorCode.SONG_NOT_FOUND.getErrorString(),
                gson.fromJson(responseJoinToComment5, ErrorResponseDto.class).getMessage());

        String requestJoinToComment6 = gson.toJson(
                new JoiningCommentDtoRequest(uuidString1, songId1, 3));
        String responseJoinToComment6 = server.joinToComment(requestJoinToComment6);
        assertEquals(ServerErrorCode.COMMENT_NOT_FOUND.getErrorString(),
                gson.fromJson(responseJoinToComment6, ErrorResponseDto.class).getMessage());


    }

    @Test
    public void cancelJoining() throws BaseServerException {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String responseJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(responseJsonString, RegisterUserDtoResponse.class).getUuid();
        String requestJsonString1 = gson.toJson(new User("bbbb", "cccc", "loginB", "456d"));
        String responseJsonString1 = server.registerUser(requestJsonString1);
        String uuidString1 = gson.fromJson(responseJsonString1, RegisterUserDtoResponse.class).getUuid();
        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        int songId1 = 0;
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        int songId2 = 1;
        songs.add(songDto1);
        songs.add(songDto2);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        String requestAddComment = gson.toJson(new AddCommentDtoRequest(
                uuidString, songId1, "comment"));
        String responseAddComment = server.addСomment(requestAddComment);
        int commentId = 0;

        String requestJoinToComment = gson.toJson(new JoiningCommentDtoRequest(uuidString1, songId1, commentId));
        String responseJoinToComment = server.joinToComment(requestJoinToComment);

        String requestCancelJoining1 = gson.toJson(new JoiningCommentDtoRequest("", songId1, commentId));
        String responseCancelJoining1 = server.cancelJoiningToComment(requestCancelJoining1);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseCancelJoining1, ErrorResponseDto.class).getMessage());

        String requestCancelJoining2 = gson.toJson(new JoiningCommentDtoRequest(uuidString, -1, commentId));
        String responseCancelJoining2 = server.cancelJoiningToComment(requestCancelJoining2);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(), gson.fromJson(responseCancelJoining2, ErrorResponseDto.class).getMessage());

        String requestCancelJoining3 = gson.toJson(new JoiningCommentDtoRequest(uuidString, songId1, -1));
        String responseCancelJoining3 = server.cancelJoiningToComment(requestCancelJoining3);
        assertEquals(ServerErrorCode.WRONG_COMMENT_ID.getErrorString(), gson.fromJson(responseCancelJoining3, ErrorResponseDto.class).getMessage());

        String responseCancelJoining = server.cancelJoiningToComment(requestJoinToComment);
        assertEquals("success", gson.fromJson(responseJoinToComment, SuccessDtoResponse.class).getResponse());
    }

    @Test
    public void testChangeComment() throws BaseServerException {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String responseJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(responseJsonString, RegisterUserDtoResponse.class).getUuid();
        String requestJsonString1 = gson.toJson(new User("bbbb", "cccc", "loginB", "456d"));
        String responseJsonString1 = server.registerUser(requestJsonString1);
        String uuidString1 = gson.fromJson(responseJsonString1, RegisterUserDtoResponse.class).getUuid();
        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        int songId1 = 0;
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        int songId2 = 1;
        songs.add(songDto1);
        songs.add(songDto2);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        String requestAddComment = gson.toJson(new AddCommentDtoRequest(
                uuidString, songId1, "comment"));
        String responseAddComment = server.addСomment(requestAddComment);
        int commentId = 0;

        String requestJoinToComment = gson.toJson(new JoiningCommentDtoRequest(uuidString1, songId1, commentId));
        String responseJoinToComment = server.joinToComment(requestJoinToComment);
        assertEquals("success", gson.fromJson(requestJoinToComment, SuccessDtoResponse.class).getResponse());

        String requestChangeComment = gson.toJson(new ChangeCommentDtoRequest("", songId1, commentId, "changed"));
        String responseChangeComment = server.changeComment(requestChangeComment);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseChangeComment, ErrorResponseDto.class).getMessage());

        String requestChangeComment1 = gson.toJson(new ChangeCommentDtoRequest(uuidString1, -1, commentId, "changed"));
        String responseChangeComment1 = server.changeComment(requestChangeComment1);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(), gson.fromJson(responseChangeComment1, ErrorResponseDto.class).getMessage());

        String requestChangeComment2 = gson.toJson(new ChangeCommentDtoRequest(uuidString1, songId1, -1, "changed"));
        String responseChangeComment2 = server.changeComment(requestChangeComment2);
        assertEquals(ServerErrorCode.WRONG_COMMENT_ID.getErrorString(), gson.fromJson(responseChangeComment2, ErrorResponseDto.class).getMessage());

        String requestChangeComment3 = gson.toJson(new ChangeCommentDtoRequest(uuidString1, songId1, commentId, ""));
        String responseChangeComment3 = server.changeComment(requestChangeComment3);
        assertEquals(ServerErrorCode.EMPTY_COMMENT_STRING.getErrorString(), gson.fromJson(responseChangeComment3, ErrorResponseDto.class).getMessage());

        String requestChangeComment4 = gson.toJson(new ChangeCommentDtoRequest(UUID.randomUUID().toString(), songId1, commentId, "changed"));
        String responseChangeComment4 = server.changeComment(requestChangeComment4);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseChangeComment4, ErrorResponseDto.class).getMessage());

        String requestChangeComment5 = gson.toJson(new ChangeCommentDtoRequest(uuidString1, songId1, commentId, "changed"));
        String responseChangeComment5 = server.changeComment(requestChangeComment5);
        assertEquals(ServerErrorCode.IMPOSSIBLE_CHANGE_COMMENT.getErrorString(), gson.fromJson(responseChangeComment5, ErrorResponseDto.class).getMessage());

        String requestChangeComment6 = gson.toJson(new ChangeCommentDtoRequest(uuidString, songId1, commentId, "changed"));
        String responseChangeComment6 = server.changeComment(requestChangeComment6);
        assertEquals("success", gson.fromJson(responseChangeComment6, SuccessDtoResponse.class).getResponse());

        String requestCancelJoining = gson.toJson(new JoiningCommentDtoRequest(uuidString1, songId1, commentId));
        String responseCancelJoining = server.cancelJoiningToComment(requestCancelJoining);

        String requestChangeComment7 = gson.toJson(new ChangeCommentDtoRequest(uuidString, songId1, commentId, "changed"));
        String responseChangeComment7 = server.changeComment(requestChangeComment7);
        assertEquals("success", gson.fromJson(responseChangeComment7, SuccessDtoResponse.class).getResponse());


    }

    @After
    public void afterTest() throws IOException {
        server.stopServer(null);
    }
}
