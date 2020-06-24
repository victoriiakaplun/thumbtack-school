package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class AddCommentDtoRequest implements IValidated {

    private String uuidString;
    private int songId;
    private String comment;

    public AddCommentDtoRequest(String uuidString, int songId, String comment) {
        this.uuidString = uuidString;
        this.songId = songId;
        this.comment = comment;
    }

    public String getUuidString() {
        return uuidString;
    }

    public int getSongId() {
        return songId;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (songId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_SONG_ID);
        }
        if (comment.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_COMMENT_STRING);
        }
    }
}
