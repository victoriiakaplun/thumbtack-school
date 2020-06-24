package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class ChangeCommentDtoRequest implements IValidated {

    private String uuidString;
    private int songId;
    private int commentId;
    private String changed;

    public ChangeCommentDtoRequest(String uuidString, int songId, int commentId, String changed) {
        this.uuidString = uuidString;
        this.songId = songId;
        this.commentId = commentId;
        this.changed = changed;
    }

    public String getUuidString() {
        return uuidString;
    }

    public int getSongId() {
        return songId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getChanged() {
        return changed;
    }


    @Override
    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (songId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_SONG_ID);
        }
        if (commentId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_COMMENT_ID);
        }
        if (changed.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_COMMENT_STRING);
        }
    }
}
