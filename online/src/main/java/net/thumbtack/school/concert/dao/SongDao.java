package net.thumbtack.school.concert.dao;

import net.thumbtack.school.concert.exception.BaseServerException;
import net.thumbtack.school.concert.model.ConcertData;
import net.thumbtack.school.concert.model.Song;

import java.util.List;

public interface SongDao {

      void insertSong(Song song, String uuidString) throws BaseServerException;

      void cancelSong(int songId, String uuidString) throws BaseServerException;

      List<Song> getAllSongs() throws BaseServerException;

      List<Song> getAllSongsByComposers(List<String> filters) throws BaseServerException;

      List<Song> getAllSongsByPoets(List<String> filters) throws BaseServerException;

      List<Song> getAllSongsByArtist(String filter) throws BaseServerException;

      List<ConcertData> getProgram() throws BaseServerException;
}
