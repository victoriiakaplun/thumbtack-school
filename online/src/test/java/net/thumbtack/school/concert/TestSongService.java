package net.thumbtack.school.concert;

import com.google.gson.Gson;
import net.thumbtack.school.concert.dto.request.*;
import net.thumbtack.school.concert.dto.response.*;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.Comment;
import net.thumbtack.school.concert.model.ConcertData;
import net.thumbtack.school.concert.model.Song;
import net.thumbtack.school.concert.model.User;
import net.thumbtack.school.concert.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSongService {
    private final Server server = new Server();
    private final Gson gson = new Gson();

    @Before
    public void setUp() throws IOException, BaseServerException {
        server.startServer(null);
    }

    @Test
    public void testAddSong() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();
        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        songs.add(songDto1);
        songs.add(songDto2);

        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        assertEquals("success", gson.fromJson(responseAddSong, SuccessDtoResponse.class).getResponse());

        String requestAddSong1 = gson.toJson(new AddSongDtoRequest("", songs));
        String responseAddSong1 = server.addSongs(requestAddSong1);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseAddSong1, ErrorResponseDto.class).getMessage());

        String requestJsonString2 = gson.toJson(new User("bbbb", "cccc", "loginB", "456d"));
        String uuidString1 = server.registerUser(requestJsonString2);
        songs.clear();
        songs.add(new SongDto("name3", listOfComposers, listOfPoets, "artist3", 10));
        String requestAddSong2 = gson.toJson(new AddSongDtoRequest(uuidString1, songs));
        String responseAddSong2 = server.addSongs(requestAddSong2);
        assertEquals(ServerErrorCode.INVALID_SONG_TIME.getErrorString(), gson.fromJson(responseAddSong2, ErrorResponseDto.class).getMessage());

        songs.clear();
        songs.add(new SongDto("", listOfComposers, listOfPoets, "artist3", 100));
        String requestAddSong3 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong3 = server.addSongs(requestAddSong3);
        assertEquals(ServerErrorCode.EMPTY_SONG_NAME_STRING.getErrorString(), gson.fromJson(responseAddSong3, ErrorResponseDto.class).getMessage());

        songs.clear();
        songs.add(new SongDto("name4", new ArrayList<>(), listOfPoets, "artist4", 89));
        String requestAddSong4 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong4 = server.addSongs(requestAddSong4);
        assertEquals(ServerErrorCode.EMPTY_SONG_COMPOSERS_LIST.getErrorString(), gson.fromJson(responseAddSong4, ErrorResponseDto.class).getMessage());

        songs.clear();
        songs.add(new SongDto("name5", listOfComposers, new ArrayList<>(), "artist5", 90));
        String requestAddSong5 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong5 = server.addSongs(requestAddSong5);
        assertEquals(ServerErrorCode.EMPTY_SONG_POETS_LIST.getErrorString(), gson.fromJson(responseAddSong5, ErrorResponseDto.class).getMessage());

        songs.clear();
        songs.add(new SongDto("name6", listOfComposers, listOfPoets, "", 100));
        String requestAddSong6 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong6 = server.addSongs(requestAddSong6);
        assertEquals(ServerErrorCode.EMPTY_SONG_ARTIST_STRING.getErrorString(), gson.fromJson(responseAddSong6, ErrorResponseDto.class).getMessage());

        songs.clear();
        List<String> emptyComposerList = new ArrayList<>();
        emptyComposerList.add("");
        songs.add(new SongDto("name7", emptyComposerList, listOfPoets, "artist7", 60));
        String requestAddSong7 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong7 = server.addSongs(requestAddSong7);
        assertEquals(ServerErrorCode.EMPTY_SONG_COMPOSER_STRING.getErrorString(), gson.fromJson(responseAddSong7, ErrorResponseDto.class).getMessage());

        songs.clear();
        List<String> emptyPoetList = new ArrayList<>();
        emptyPoetList.add("");
        songs.add(new SongDto("name8", listOfComposers, emptyPoetList, "artist8", 60));
        String requestAddSong8 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong8 = server.addSongs(requestAddSong8);
        assertEquals(ServerErrorCode.EMPTY_SONG_POET_STRING.getErrorString(), gson.fromJson(responseAddSong8, ErrorResponseDto.class).getMessage());

        songs.clear();
        songs.add(new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60));
        String requestAddSong9 = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong9 = server.addSongs(requestAddSong9);
        assertEquals(ServerErrorCode.SONG_ALREADY_EXIST.getErrorString(), gson.fromJson(responseAddSong9, ErrorResponseDto.class).getMessage());
    }

    @Test
    public void testCancelSong() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();
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

        String requestRateSong = gson.toJson(new RateDtoRequest(
                uuidString1, songId1, 4)
        );
        String responseRateSong = server.rateSong(requestRateSong);

        String requestCancelSong = gson.toJson(new CancelSongDtoRequest(uuidString, songId1));
        String responseCancelSong = server.cancelSong(requestCancelSong);
        assertEquals("success", gson.fromJson(responseCancelSong, SuccessDtoResponse.class).getResponse());

        String requestCancelSong1 = gson.toJson(new CancelSongDtoRequest(uuidString, songId2));
        String responseCancelSong1 = server.cancelSong(requestCancelSong1);
        assertEquals("success", gson.fromJson(responseCancelSong1, SuccessDtoResponse.class).getResponse());

        String requestCancelSong2 = gson.toJson(new CancelSongDtoRequest("", songId1));
        String responseCancelSong2 = server.cancelSong(requestCancelSong2);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseCancelSong2, ErrorResponseDto.class).getMessage());

        String requestCancelSong3 = gson.toJson(new CancelSongDtoRequest(uuidString, -1));
        String responseCancelSong3 = server.cancelSong(requestCancelSong3);
        assertEquals(ServerErrorCode.WRONG_SONG_ID.getErrorString(), gson.fromJson(responseCancelSong3, ErrorResponseDto.class).getMessage());

        String requestCancelSong4 = gson.toJson(new CancelSongDtoRequest(UUID.randomUUID().toString(), songId1));
        String responseCancelSong4 = server.cancelSong(requestCancelSong4);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseCancelSong4, ErrorResponseDto.class).getMessage());

        String requestCancelSong5 = gson.toJson(new CancelSongDtoRequest(
                uuidString, 3));
        String responseCancelSong5 = server.cancelSong(requestCancelSong5);
        assertEquals(ServerErrorCode.SONG_NOT_FOUND.getErrorString(), gson.fromJson(responseCancelSong5, ErrorResponseDto.class).getMessage());
    }

    @Test
    public void testGetAllSongs() throws BaseServerException {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();
        String responseGetAllSongs = server.getAllSongs("");
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseGetAllSongs, ErrorResponseDto.class).getMessage());
        String responseGetAllSongs1 = server.getAllSongs(uuidString);
        assertTrue(gson.fromJson(responseGetAllSongs1, SongListDtoResponse.class).getSongList().isEmpty());
        String responseGetAllSongs2 = server.getAllSongs(UUID.randomUUID().toString());
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseGetAllSongs2, ErrorResponseDto.class).getMessage());

        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        songs.add(songDto1);
        songs.add(songDto2);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        assertEquals(2, gson.fromJson(server.getAllSongs(uuidString), SongListDtoResponse.class).getSongList().size());
    }

    @Test
    public void testGetSongsByComposers() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();

        String requestGetSongs = gson.toJson(new FilterSongDtoRequest("", new ArrayList<>()));
        String responseGetSongs = server.getAllSongsOfComposers(requestGetSongs);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseGetSongs, ErrorResponseDto.class).getMessage());

        List<String> filter = new ArrayList<>();
        String requestGetSongs1 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs1 = server.getAllSongsOfComposers(requestGetSongs1);
        assertEquals(ServerErrorCode.INVALID_SONG_FILTERS.getErrorString(), gson.fromJson(responseGetSongs1, ErrorResponseDto.class).getMessage());
        filter.add("");
        String requestGetSongs2 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs2 = server.getAllSongsOfComposers(requestGetSongs2);
        assertEquals(ServerErrorCode.EMPTY_FILTER.getErrorString(), gson.fromJson(responseGetSongs2, ErrorResponseDto.class).getMessage());

        filter.clear();
        filter.add("composer1");
        filter.add("composer2");

        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        listOfComposers.add("composer2");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        List<String> listOfComposers1 = new ArrayList<>();
        listOfComposers1.add("composer1");
        SongDto songDto3 = new SongDto("name3", listOfComposers1, listOfPoets, "artist3", 123);
        songs.add(songDto1);
        songs.add(songDto2);
        songs.add(songDto3);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        List<Song> exp = new ArrayList<>();
        exp.add(new Song("loginA", "name1", listOfComposers, listOfPoets, "artist1", 60));
        exp.add(new Song("loginA", "name2", listOfComposers, listOfPoets, "artist2", 230));
        exp.add(new Song("loginA", "name3", listOfComposers1, listOfPoets, "artist3", 123));

        String requestGetSongs3 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs3 = server.getAllSongsOfComposers(requestGetSongs3);
        assertEquals(exp, gson.fromJson(responseGetSongs3, SongListDtoResponse.class).getSongList());

        exp.remove(new Song("loginA", "name3", listOfComposers1, listOfPoets, "artist3", 123));
        List<String> filter1 = new ArrayList<>();
        filter1.add("composer2");
        String requestGetSongs4 = gson.toJson(new FilterSongDtoRequest(uuidString, filter1));
        String responseGetSongs4 = server.getAllSongsOfComposers(requestGetSongs4);
        assertEquals(exp, gson.fromJson(responseGetSongs4, SongListDtoResponse.class).getSongList());

        String requestGetSongs5 = gson.toJson(new FilterSongDtoRequest(UUID.randomUUID().toString(), filter));
        String responseGetSongs5 = server.getAllSongsOfComposers(requestGetSongs5);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseGetSongs5, ErrorResponseDto.class).getMessage());

        filter.clear();
        filter.add("other composer");
        exp.clear();
        String requestGetSongs6 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs6 = server.getAllSongsOfPoets(requestGetSongs6);
        assertTrue(gson.fromJson(responseGetSongs6, SongListDtoResponse.class).getSongList().isEmpty());
    }

    @Test
    public void testGetAllSongsByPoets() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();

        String requestGetSongs = gson.toJson(new FilterSongDtoRequest("", new ArrayList<>()));
        String responseGetSongs = server.getAllSongsOfComposers(requestGetSongs);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseGetSongs, ErrorResponseDto.class).getMessage());

        List<String> filter = new ArrayList<>();
        String requestGetSongs1 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs1 = server.getAllSongsOfComposers(requestGetSongs1);
        assertEquals(ServerErrorCode.INVALID_SONG_FILTERS.getErrorString(), gson.fromJson(responseGetSongs1, ErrorResponseDto.class).getMessage());
        filter.add("");
        String requestGetSongs2 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs2 = server.getAllSongsOfComposers(requestGetSongs2);
        assertEquals(ServerErrorCode.EMPTY_FILTER.getErrorString(), gson.fromJson(responseGetSongs2, ErrorResponseDto.class).getMessage());

        filter.clear();
        filter.add("poet1");
        filter.add("poet2");
        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        List<String> listOfPoets1 = new ArrayList<>();
        listOfPoets1.add("poet2");
        SongDto songDto3 = new SongDto("name3", listOfComposers, listOfPoets1, "artist3", 123);
        songs.add(songDto1);
        songs.add(songDto2);
        songs.add(songDto3);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        List<Song> exp = new ArrayList<>();
        exp.add(new Song("loginA", "name1", listOfComposers, listOfPoets, "artist1", 60));
        exp.add(new Song("loginA", "name2", listOfComposers, listOfPoets, "artist2", 230));
        exp.add(new Song("loginA", "name3", listOfComposers, listOfPoets1, "artist3", 123));
        String requestGetSongs3 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs3 = server.getAllSongsOfPoets(requestGetSongs3);
        assertEquals(exp, gson.fromJson(responseGetSongs3, SongListDtoResponse.class).getSongList());
        filter.remove("poet2");
        exp.remove(new Song("loginA", "name3", listOfComposers, listOfPoets1, "artist3", 123));
        String requestGetSongs4 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs4 = server.getAllSongsOfPoets(requestGetSongs4);
        assertEquals(exp, gson.fromJson(responseGetSongs4, SongListDtoResponse.class).getSongList());

        filter.clear();
        filter.add("other poet");
        exp.clear();
        String requestGetSongs5 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs5 = server.getAllSongsOfPoets(requestGetSongs5);
        assertTrue(gson.fromJson(responseGetSongs5, SongListDtoResponse.class).getSongList().isEmpty());
    }

    @Test
    public void testGetSongsByArtist() {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();

        List<String> filter = new ArrayList<>();
        filter.add("artist1");
        filter.add("artist2");
        String requestGetSongs = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs = server.getAllSongsOfArtist(requestGetSongs);
        assertEquals(ServerErrorCode.INVALID_SONG_FILTERS.getErrorString(), gson.fromJson(responseGetSongs, ErrorResponseDto.class).getMessage());

        filter.clear();
        filter.add("");
        String requestGetSongs1 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs1 = server.getAllSongsOfArtist(requestGetSongs1);
        assertEquals(ServerErrorCode.EMPTY_FILTER.getErrorString(), gson.fromJson(responseGetSongs1, ErrorResponseDto.class).getMessage());

        filter.clear();
        filter.add("artist1");
        String requestGetSongs2 = gson.toJson(new FilterSongDtoRequest("", filter));
        String responseGetSongs2 = server.getAllSongsOfArtist(requestGetSongs2);
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseGetSongs2, ErrorResponseDto.class).getMessage());

        String requestGetSongs3 = gson.toJson(new FilterSongDtoRequest(UUID.randomUUID().toString(), filter));
        String responseGetSongs3 = server.getAllSongsOfArtist(requestGetSongs3);
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseGetSongs3, ErrorResponseDto.class).getMessage());

        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 60);
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 230);
        SongDto songDto3 = new SongDto("name3", listOfComposers, listOfPoets, "artist1", 123);
        songs.add(songDto1);
        songs.add(songDto2);
        songs.add(songDto3);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);
        List<Song> exp = new ArrayList<>();
        exp.add(new Song("loginA", "name1", listOfComposers, listOfPoets, "artist1", 60));
        exp.add(new Song("loginA", "name3", listOfComposers, listOfPoets, "artist1", 123));
        String requestGetSongs4 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs4 = server.getAllSongsOfArtist(requestGetSongs4);
        assertEquals(exp, gson.fromJson(responseGetSongs4, SongListDtoResponse.class).getSongList());

        filter.clear();
        filter.add("other artist");
        String requestGetSongs5 = gson.toJson(new FilterSongDtoRequest(uuidString, filter));
        String responseGetSongs5 = server.getAllSongsOfArtist(requestGetSongs5);
        assertTrue(gson.fromJson(responseGetSongs5, SongListDtoResponse.class).getSongList().isEmpty());
    }

    @Test
    public void testGetConcertProgram() throws BaseServerException {
        String requestJsonString = gson.toJson(new User("aaaa", "bbbb", "loginA", "123d"));
        String uuidJsonString = server.registerUser(requestJsonString);
        String uuidString = gson.fromJson(uuidJsonString, RegisterUserDtoResponse.class).getUuid();

        String requestJsonString1 = gson.toJson(new User("cccc", "dddd", "loginB", "123e"));
        String uuidJsonString1 = server.registerUser(requestJsonString1);
        String uuidString1 = gson.fromJson(uuidJsonString1, RegisterUserDtoResponse.class).getUuid();

        String requestJsonString2 = gson.toJson(new User("ffff", "gggg", "loginC", "123h"));
        String uuidJsonString2 = server.registerUser(requestJsonString2);
        String uuidString2 = gson.fromJson(uuidJsonString2, RegisterUserDtoResponse.class).getUuid();

        String requestJsonString3 = gson.toJson(new User("iiii", "jjjj", "loginD", "123k"));
        String uuidJsonString3 = server.registerUser(requestJsonString3);
        String uuidString3 = gson.fromJson(uuidJsonString3, RegisterUserDtoResponse.class).getUuid();

        List<String> listOfComposers = new ArrayList<>();
        listOfComposers.add("composer1");
        List<String> listOfPoets = new ArrayList<>();
        listOfPoets.add("poet1");
        listOfPoets.add("poet2");
        List<SongDto> songs = new ArrayList<>();
        SongDto songDto1 = new SongDto("name1", listOfComposers, listOfPoets, "artist1", 1000);
        int songId1 = 0;
        SongDto songDto2 = new SongDto("name2", listOfComposers, listOfPoets, "artist2", 2000);
        int songId2 = 1;
        SongDto songDto3 = new SongDto("name3", listOfComposers, listOfPoets, "artist3", 200);
        int songId3 = 2;
        SongDto songDto4 = new SongDto("name4", listOfComposers, listOfPoets, "artist4", 400);
        int songId4 = 3;
        SongDto songDto5 = new SongDto("name5", listOfComposers, listOfPoets, "artist5", 290);
        int songId5 = 4;
        SongDto songDto6 = new SongDto("name6", listOfComposers, listOfPoets, "artist6", 61);
        int songId6 = 5;
        SongDto songDto7 = new SongDto("name7", listOfComposers, listOfPoets, "artist7", 60);
        songs.add(songDto1);
        songs.add(songDto2);
        songs.add(songDto3);
        songs.add(songDto4);
        songs.add(songDto5);
        songs.add(songDto6);
        songs.add(songDto7);
        String requestAddSong = gson.toJson(new AddSongDtoRequest(uuidString, songs));
        String responseAddSong = server.addSongs(requestAddSong);

        String requestRateSong1 = gson.toJson(new RateDtoRequest(uuidString1, songId1, 5));
        String responseRateSong1 = server.rateSong(requestRateSong1);
        String requestRateSong2 = gson.toJson(new RateDtoRequest(uuidString2, songId1, 5));
        String responseRateSong2 = server.rateSong(requestRateSong2);
        String requestRateSong3 = gson.toJson(new RateDtoRequest(uuidString3, songId1, 5));
        String responseRateSong3 = server.rateSong(requestRateSong3);

        String requestRateSong4 = gson.toJson(new RateDtoRequest(uuidString1, songId2, 5));
        String responseRateSong4 = server.rateSong(requestRateSong4);
        String requestRateSong5 = gson.toJson(new RateDtoRequest(uuidString2, songId2, 4));
        String responseRateSong5 = server.rateSong(requestRateSong5);
        String requestRateSong6 = gson.toJson(new RateDtoRequest(uuidString3, songId2, 4));
        String responseRateSong6 = server.rateSong(requestRateSong6);

        String requestRateSong7 = gson.toJson(new RateDtoRequest(uuidString1, songId3, 4));
        String responseRateSong7 = server.rateSong(requestRateSong7);
        String requestRateSong8 = gson.toJson(new RateDtoRequest(uuidString2, songId3, 4));
        String responseRateSong8 = server.rateSong(requestRateSong8);
        String requestRateSong9 = gson.toJson(new RateDtoRequest(uuidString3, songId3, 4));
        String responseRateSong9 = server.rateSong(requestRateSong9);

        String requestRateSong10 = gson.toJson(new RateDtoRequest(uuidString1, songId4, 4));
        String responseRateSong10 = server.rateSong(requestRateSong10);
        String requestRateSong11 = gson.toJson(new RateDtoRequest(uuidString2, songId4, 3));
        String responseRateSong11 = server.rateSong(requestRateSong11);
        String requestRateSong12 = gson.toJson(new RateDtoRequest(uuidString3, songId4, 3));
        String responseRateSong12 = server.rateSong(requestRateSong12);

        String requestRateSong13 = gson.toJson(new RateDtoRequest(uuidString1, songId5, 3));
        String responseRateSong13 = server.rateSong(requestRateSong13);
        String requestRateSong14 = gson.toJson(new RateDtoRequest(uuidString2, songId5, 3));
        String responseRateSong14 = server.rateSong(requestRateSong14);
        String requestRateSong15 = gson.toJson(new RateDtoRequest(uuidString3, songId5, 3));
        String responseRateSong15 = server.rateSong(requestRateSong15);

        String requestRateSong16 = gson.toJson(new RateDtoRequest(uuidString1, songId6, 3));
        String responseRateSong16 = server.rateSong(requestRateSong16);
        String requestRateSong17 = gson.toJson(new RateDtoRequest(uuidString2, songId6, 2));
        String responseRateSong17 = server.rateSong(requestRateSong17);
        String requestRateSong18 = gson.toJson(new RateDtoRequest(uuidString3, songId6, 2));
        String responseRateSong18 = server.rateSong(requestRateSong18);

        String requestAddComment = gson.toJson(new AddCommentDtoRequest(uuidString1, songId1, "comment1"));
        String responseAddComment = server.addСomment(requestAddComment);
        int commentId = 0;
        List<Comment> comments = new ArrayList<>();
        Set<String> joined = new HashSet<>();
        joined.add("loginC");
        comments.add(new Comment(songId1, "loginB", "comment1", joined));

        String requestJoin = gson.toJson(new JoiningCommentDtoRequest(uuidString2, songId1, commentId));
        String responseJoin = server.joinToComment(requestJoin);
        String requestAddComment1 = gson.toJson(new AddCommentDtoRequest(uuidString2, songId2, "comment2"));
        String responseAddComment1 = server.addСomment(requestAddComment1);
        List<Comment> comments1 = new ArrayList<>();
        comments1.add(new Comment(songId2, "loginC", "comment2"));

        String responseGetProgram = server.getConcertProgram(uuidString3);

        List<ConcertData> exp = new ArrayList<>();
        User author = new User("aaaa", "bbbb", "loginA", "123d");
        exp.add(new ConcertData(new Song("loginA", "name1", listOfComposers, listOfPoets, "artist1", 1000),
                        author,
                        (double) 20 / 4,
                comments
                )
        );

        exp.add(new ConcertData(new Song("loginA", "name2", listOfComposers, listOfPoets, "artist2", 2000),
                        author,
                        (double) 18 / 4,
                comments1
                )
        );
        exp.add(new ConcertData(new Song("loginA", "name3", listOfComposers, listOfPoets, "artist3", 200),
                author,
                (double) 17 / 4,
                null)
        );
        exp.add(new ConcertData(new Song("loginA", "name5", listOfComposers, listOfPoets, "artist5", 290),
                author,
                (double) 14 / 4,
                null)
        );
        exp.add(new ConcertData(new Song("loginA", "name7", listOfComposers, listOfPoets, "artist7", 60),
                author,
                (double) 5,
                null)
        );
        assertEquals(exp, gson.fromJson(responseGetProgram, ConcertDataListDtoResponse.class).getConcertData());

        String responseGetProgram1 = server.getConcertProgram("");
        assertEquals(ServerErrorCode.EMPTY_TOKEN.getErrorString(), gson.fromJson(responseGetProgram1, ErrorResponseDto.class).getMessage());

        String responseGetProgram2 = server.getConcertProgram(UUID.randomUUID().toString());
        assertEquals(ServerErrorCode.USER_NOT_FOUND.getErrorString(), gson.fromJson(responseGetProgram2, ErrorResponseDto.class).getMessage());

    }

    @After
    public void afterTest() throws IOException {
        server.stopServer(null);
    }
}
