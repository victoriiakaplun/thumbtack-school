package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class RateDtoRequest implements IValidated {

    private String uuidString;
    private int songId;
    private int rating;

    public RateDtoRequest(String uuidString, int songId, int rating) {
        this.uuidString = uuidString;
        this.songId = songId;
        this.rating = rating;
    }

    public String getUuidString() {
        return uuidString;
    }

    public int getSongId() {
        return songId;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public void validate() throws BaseServerException {
        if (songId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_SONG_ID);
        }
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (rating < 1 || rating > 5) {
            throw new BaseServerException(ServerErrorCode.INVALID_SONG_RATING);
        }
    }
}
