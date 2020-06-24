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
import static org.junit.Assert.assertNotNull;

public class TestUserService {
    private final Server server = new Server();
    private final Gson gson = new Gson();

    @Before
    public void setUp() throws IOException, BaseServerException {
        server.startServer(null);
    }


    @Test
    public void testRegisterUser() throws IOException {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "cccc", "123d"));
        String responseJsonString = server.registerUser(requestJsonString);
        String requestJsonString1 = gson.toJson(new User("aaa", "bbbb", "bbbb", "1234d"));
        String responseJsonString1 = server.registerUser(requestJsonString1);
        String requestJsonString2 = gson.toJson(new User("aaaa", "bbb", "cccc", "1234d"));
        String responseJsonString2 = server.registerUser(requestJsonString2);
        String requestJsonString3 = gson.toJson(new User("aaaa", "bbbb", "ccc", "1234d"));
        String responseJsonString3 = server.registerUser(requestJsonString3);
        String requestJsonString4 = gson.toJson(new User("aaaa", "bbbb", "dddd", "123"));
        String responseJsonString4 = server.registerUser(requestJsonString4);
        String requestJsonString5 = gson.toJson(new User("aaaa", "bbbb", "eeee", "ffff"));
        String responseJsonString5 = server.registerUser(requestJsonString5);
        String requestJsonString6 = gson.toJson(new User("aaaa", "bbbb", "cccc", "123d"));
        String responseJsonString6 = server.registerUser(requestJsonString6);
        assertNotNull(responseJsonString);
        assertEquals(ServerErrorCode.TOO_SHORT_FIRST_NAME.toString(), gson.fromJson(responseJsonString1, ErrorResponseDto.class).getErrorCode());
        assertEquals(ServerErrorCode.TOO_SHORT_LAST_NAME.toString(), gson.fromJson(responseJsonString2, ErrorResponseDto.class).getErrorCode());
        assertEquals(ServerErrorCode.TOO_SHORT_LOGIN.toString(), gson.fromJson(responseJsonString3, ErrorResponseDto.class).getErrorCode());
        assertEquals(ServerErrorCode.TOO_SHORT_PASSWORD.toString(), gson.fromJson(responseJsonString4, ErrorResponseDto.class).getErrorCode());
        assertEquals(ServerErrorCode.INVALID_PASSWORD_FORMAT.toString(), gson.fromJson(responseJsonString5, ErrorResponseDto.class).getErrorCode());
        assertEquals(ServerErrorCode.USER_ALREADY_EXIST.toString(), gson.fromJson(responseJsonString6, ErrorResponseDto.class).getErrorCode());
        server.stopServer(null);
    }


    @Test
    public void testAuthorizeUser() throws IOException, BaseServerException {
        Server server = new Server();
        server.startServer(null);
        Gson gson = new Gson();
        String requestRegisterJsonString = gson.toJson(new User("aaaa", "bbbb", "cccc", "123d"));
        String responseRegisterJsonString = server.registerUser(requestRegisterJsonString);
        String requestAuthorizeJsonString1 = gson.toJson(new AuthorizeUserDtoRequest("cccc", "123d"));
        String responseAuthorizeJsonString1 = server.authorizeUser(requestAuthorizeJsonString1);
        String requestAuthorizeJsonString2 = gson.toJson(new AuthorizeUserDtoRequest("aaaa", "123d"));
        String responseAuthorizeJsonString2 = server.authorizeUser(requestAuthorizeJsonString2);
        String requestAuthorizeJsonString3 = gson.toJson(new AuthorizeUserDtoRequest("cccc", "1234d"));
        String responseAuthorizeJsonString3 = server.authorizeUser(requestAuthorizeJsonString3);
        String requestAuthorizeJsonString4 = gson.toJson(new AuthorizeUserDtoRequest("", "1234d"));
        String responseAuthorizeJsonString4 = server.authorizeUser(requestAuthorizeJsonString4);
        String requestAuthorizeJsonString5 = gson.toJson(new AuthorizeUserDtoRequest("cccc", ""));
        String responseAuthorizeJsonString5 = server.authorizeUser(requestAuthorizeJsonString5);
        assertNotNull(responseAuthorizeJsonString1);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(),
                gson.fromJson(responseAuthorizeJsonString2, ErrorResponseDto.class).getMessage());
        assertEquals(ServerErrorCode.WRONG_PASSWORD.getErrorString(),
                gson.fromJson(responseAuthorizeJsonString3, ErrorResponseDto.class).getMessage());
        assertEquals(ServerErrorCode.EMPTY_LOGIN_STRING.getErrorString(),
                gson.fromJson(responseAuthorizeJsonString4, ErrorResponseDto.class).getMessage());
        assertEquals(ServerErrorCode.EMPTY_PASSWORD_STRING.getErrorString(),
                gson.fromJson(responseAuthorizeJsonString5, ErrorResponseDto.class).getMessage());
        server.stopServer(null);
    }

    @Test
    public void testDeleteUuid() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "cccc", "123d"));
        String responseJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(responseJsonString, RegisterUserDtoResponse.class).getUuid();

        String requestLogout1 = gson.toJson(new LogoutUserDtoRequest(UUID.randomUUID().toString()));
        String responseLogout1 = server.logoutUser(requestLogout1);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(),
                gson.fromJson(responseLogout1, ErrorResponseDto.class).getMessage());

        String requestLogout = gson.toJson(new LogoutUserDtoRequest(uuidString));
        String responseLogout = server.logoutUser(requestLogout);
        assertEquals("success", gson.fromJson(responseLogout, SuccessDtoResponse.class).getResponse());

        String requestLogout2 = gson.toJson(new LogoutUserDtoRequest(""));
        String responseLogout2 = server.logoutUser(requestLogout2);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(),
                gson.fromJson(responseLogout2, ErrorResponseDto.class).getMessage());
    }

    @Test
    public void testLeaveServer() throws BaseServerException {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();

        String requestJsonString1 = gson.toJson(new User("cccc", "dddd", "loginB", "123e"));
        String uuidJsonString1 = server.registerUser(requestJsonString1);
        String uuidString1 = gson.fromJson(uuidJsonString1, RegisterUserDtoResponse.class).getUuid();

        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 1000);
        int songId1 = 0;
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 2000);
        songs.add(songDto1);
        songs.add(songDto2);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);

        String requestRateSong1 = gson.toJson(new RateDtoRequest(uuidString1, songId1, 5));
        String responseRateSong1 = server.rateSong(requestRateSong1);

        String requestAddComment = gson.toJson(new AddCommentDtoRequest(uuidString1, songId1, "comment1"));
        String responseAddComment = server.addСomment(requestAddComment);

        String requestJoin = gson.toJson(new JoiningCommentDtoRequest(uuidString, songId1, 0));
        String responseJoin = server.joinToComment(requestJoin);

        String responseLeaveServer = server.leaveUser(uuidString1);
        assertEquals("success", gson.fromJson(responseLeaveServer, SuccessDtoResponse.class).getResponse());

        String responseLeaveServer1 = server.leaveUser("");
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseLeaveServer1, ErrorResponseDto.class).getMessage());

        String responseLeaveServer2 = server.leaveUser(UUID.randomUUID().toString());
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseLeaveServer2, ErrorResponseDto.class).getMessage());
    }

    @After
    public void afterTest() throws IOException {
        server.stopServer(null);
    }
}


