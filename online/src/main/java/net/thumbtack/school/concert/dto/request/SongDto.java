package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

import java.util.ArrayList;
import java.util.List;

public class SongDto implements IValidated {

    private String name;
    private List<String> composers;
    private List<String> poets;
    private String artist;
    private int time;

    public SongDto(String name, List<String> composers, List<String> poets, String artist, int time) {
        this.name = name;
        this.composers = new ArrayList<>(composers);
        this.poets = new ArrayList<>(poets);
        this.artist = artist;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public List<String> getComposers() {
        return composers;
    }

    public List<String> getPoets() {
        return poets;
    }

    public String getArtist() {
        return artist;
    }

    public int getTime() {
        return time;
    }

    public void validate() throws BaseServerException {
        if (name.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_SONG_NAME_STRING);
        }
        if (composers.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_SONG_COMPOSERS_LIST);
        }
        for (String composer : composers) {
            if (composer.isEmpty()) {
                throw new BaseServerException(ServerErrorCode.EMPTY_SONG_COMPOSER_STRING);
            }
        }
        if (poets.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_SONG_POETS_LIST);
        }
        for (String poet : poets) {
            if (poet.isEmpty()) {
                throw new BaseServerException(ServerErrorCode.EMPTY_SONG_POET_STRING);
            }
        }
        if (artist.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_SONG_ARTIST_STRING);
        }
        if (time < 60) {
            throw new BaseServerException(ServerErrorCode.INVALID_SONG_TIME);
        }
    }
}
