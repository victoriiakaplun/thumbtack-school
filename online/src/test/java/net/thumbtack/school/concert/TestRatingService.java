package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.dto.request.AddSongDtoRequest;
import net.thumbtack.school.concert.dto.request.CancelRatingDtoRequest;
import net.thumbtack.school.concert.dto.request.RateDtoRequest;
import net.thumbtack.school.concert.dto.request.SongDto;
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

public class TestRatingService {

    private final Server server = new Server();
    private final Gson gson = new Gson();

    @Before
    public void setUp() throws IOException, BaseServerException {
        server.startServer(null);
    }

    @Test
    public void testRateSong() {
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
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString1, songs));
        String responseAddSong = server.addSongs(requestAddSong);

        String requestRateSong = gson.toJson(new RateDtoRequest(
                uuidString, songId1, 4)
        );
        String responseRateSong = server.rateSong(requestRateSong);
        assertEquals("success", gson.fromJson(responseRateSong, SuccessDtoResponse.class).getResponse());

        String requestChangeRating = gson.toJson(new RateDtoRequest(
                uuidString, songId1, 3)
        );
        String responseChangeRating = server.changeSongRating(requestChangeRating);
        assertEquals("success", gson.fromJson(responseChangeRating, SuccessDtoResponse.class).getResponse());

        String requestChangeRating1 = gson.toJson(new RateDtoRequest(
                uuidString1, songId1, 3)
        );
        String responseChangeRating1 = server.changeSongRating(requestChangeRating1);
        assertEquals(ServerErrorCode.RATING_ALREADY_EXISTS.getErrorString(),
                gson.fromJson(responseChangeRating1, ErrorResponseDto.class).getMessage());

        String requestRateSong2 = gson.toJson(new RateDtoRequest(
                uuidString, 3, 3));
        String responseRateSong2 = server.rateSong(requestRateSong2);
        assertEquals(ServerErrorCode.SONG_NOT_FOUND.getErrorString(), gson.fromJson(responseRateSong2, ErrorResponseDto.class).getMessage());

        String requestRateSong3 = gson.toJson(new RateDtoRequest(
                UUID.randomUUID().toString(), songId2, 3));
        String responseRateSong3 = server.rateSong(requestRateSong3);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseRateSong3, ErrorResponseDto.class).getMessage());

        String requestRateSong4 = gson.toJson(new RateDtoRequest(
                uuidString1, songId1, 7)
        );
        String responseRateSong4 = server.rateSong(requestRateSong4);
        assertEquals(ServerErrorCode.INVALID_SONG_RATING.getErrorString(), gson.fromJson(responseRateSong4, ErrorResponseDto.class).getMessage());

        String requestRateSong5 = gson.toJson(new RateDtoRequest(
                "", songId2, 3));
        String responseRateSong5 = server.rateSong(requestRateSong5);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseRateSong5, ErrorResponseDto.class).getMessage());

        String requestRateSong6 = gson.toJson(new RateDtoRequest(uuidString, -1, 5));
        String responseRateSong6 = server.rateSong(requestRateSong6);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(), gson.fromJson(responseRateSong6, ErrorResponseDto.class).getMessage());
    }

    @Test
    public void testCancelRating() {
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
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString1, songs));
        String responseAddSong = server.addSongs(requestAddSong);

        String requestRateSong = gson.toJson(new RateDtoRequest(
                uuidString, songId1, 4)
        );
        String responseRateSong = server.rateSong(requestRateSong);

        String requestCancelRating = gson.toJson(
                new CancelRatingDtoRequest("", songId1));
        String responseCancelRating = server.cancelSongRating(requestCancelRating);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseCancelRating, ErrorResponseDto.class).getMessage());

        String requestCancelRating1 = gson.toJson(new CancelRatingDtoRequest(uuidString, -1));
        String responseCancelRating1 = server.cancelSongRating(requestCancelRating1);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(), gson.fromJson(responseCancelRating1, ErrorResponseDto.class).getMessage());

        String requestCancelRating2 = gson.toJson(
                new CancelRatingDtoRequest(
                        UUID.randomUUID().toString(), songId1));
        String responseCancelRating2 = server.cancelSongRating(requestCancelRating2);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseCancelRating2, ErrorResponseDto.class).getMessage());

        String requestCancelRating3 = gson.toJson(
                new CancelRatingDtoRequest(uuidString, 3));
        String responseCancelRating3 = server.cancelSongRating(requestCancelRating3);
        assertEquals(ServerErrorCode.SONG_NOT_FOUND.getErrorString(), gson.fromJson(responseCancelRating3, ErrorResponseDto.class).getMessage());

        String requestCancelRating4 = gson.toJson(
                new CancelRatingDtoRequest(uuidString, songId2)
        );
        String responseCancelRating4 = server.cancelSongRating(requestCancelRating4);
        assertEquals(ServerErrorCode.RATING_NOT_FOUND.getErrorString(), gson.fromJson(responseCancelRating4, ErrorResponseDto.class).getMessage());

        String requestCancelRating5 = gson.toJson(
                new CancelRatingDtoRequest(uuidString1, songId1)
        );
        String responseCancelRating5 = server.cancelSongRating(requestCancelRating5);
        assertEquals(ServerErrorCode.RATING_ALREADY_EXISTS.getErrorString(), gson.fromJson(responseCancelRating5, ErrorResponseDto.class).getMessage());

        String requestCancelRating6 = gson.toJson(
                new CancelRatingDtoRequest(uuidString1, songId2)
        );
        String responseCancelRating6 = server.cancelSongRating(requestCancelRating6);
        assertEquals("success", gson.fromJson(responseCancelRating6, SuccessDtoResponse.class).getResponse());
    }

    @After
    public void afterTest() throws IOException {
        server.stopServer(null);
    }
}
