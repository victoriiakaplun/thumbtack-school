package net.thumbtack.school.concert.dao.impl;

import net.thumbtack.school.concert.dao.SongDao;
import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.ConcertData;
import net.thumbtack.school.concert.model.Song;

import java.util.List;

import static net.thumbtack.school.concert.data.DataBase.getDataBase;

public class SongDaoImpl implements SongDao {

    public SongDaoImpl() {
    }

    @Override
    public void insertSong(Song song, String uuidString) throws BaseServerException {
        getDataBase().insertSong(song, uuidString);
    }

    @Override
    public void cancelSong(int songId, String uuidString) throws BaseServerException {
        getDataBase().cancelSong(songId, uuidString);
    }

    @Override
    public List<Song> getAllSongs() {
        return getDataBase().getAllSongs();
    }

    @Override
    public List<Song> getAllSongsByComposers(List<String> filters) {
        return getDataBase().getAllSongsByComposers(filters);
    }

    @Override
    public List<Song> getAllSongsByPoets(List<String> filters) {
        return getDataBase().getAllSongsByPoets(filters);
    }

    @Override
    public List<Song> getAllSongsByArtist(String filter) {
        return getDataBase().getAllSongsByArtist(filter);
    }

    @Override
    public List<ConcertData> getProgram() throws BaseServerException {
        return getDataBase().getConcertData();
    }
}
