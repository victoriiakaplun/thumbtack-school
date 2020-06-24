package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class JoiningCommentDtoRequest implements IValidated {

    private String uuidString;
    private int songId;
    private int commentId;

    public JoiningCommentDtoRequest(String uuidString, int songId, int commentId) {
        this.uuidString = uuidString;
        this.songId = songId;
        this.commentId = commentId;
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

    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (commentId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_COMMENT_ID);
        }
        if (songId < 0) {
            throw new BaseServerException(ServerErrorCode.WRONG_SONG_ID);
        }
    }
}
