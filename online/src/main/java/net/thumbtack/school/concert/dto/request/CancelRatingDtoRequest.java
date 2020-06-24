package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

import java.util.Objects;

public class CancelRatingDtoRequest implements IValidated {

    private String uuidString;
    private int songId;

    public CancelRatingDtoRequest(String uuidString, int songId) {
        this.uuidString = uuidString;
        this.songId = songId;
    }

    public String getUuidString() {
        return uuidString;
    }

    public int getSongId() {
        return songId;
    }

    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (songId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_SONG_ID);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CancelRatingDtoRequest that = (CancelRatingDtoRequest) o;
        return songId == that.songId &&
                Objects.equals(uuidString, that.uuidString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidString, songId);
    }
}
