package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class CancelSongDtoRequest implements IValidated {

    private String uuidString;
    private int songId;

    public CancelSongDtoRequest(String uuidString, int songId) {
        this.uuidString = uuidString;
        this.songId = songId;
    }

    public String getUuidString() {
        return uuidString;
    }

    public int getSongId() {
        return songId;
    }

    @Override
    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (songId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_SONG_ID);
        }
    }
}
