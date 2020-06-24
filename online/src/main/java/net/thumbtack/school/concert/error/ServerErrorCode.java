package net.thumbtack.school.concert.error;


public enum ServerErrorCode {

    INVALID_INPUT_DATA_FORMAT("Invalid input data format!"),
    EMPTY_TOKEN("Empty token!"),
    DATA_BASE_ERROR("Error with data base initialization!"),

    SESSION_NOT_FOUND("Session is not found!"),
    TOO_SHORT_FIRST_NAME("First name is too short!"),
    TOO_SHORT_LAST_NAME("Last name is too short!"),
    TOO_SHORT_LOGIN("Login is too short!"),
    USER_ALREADY_EXIST("User already exists!"),
    TOO_SHORT_PASSWORD("Password is too short!"),
    INVALID_PASSWORD_FORMAT("Password does not contain numbers!"),
    USER_NOT_FOUND("User is not found!"),
    EMPTY_LOGIN_STRING("Login is empty!"),
    EMPTY_PASSWORD_STRING("Password is empty!"),
    WRONG_PASSWORD("Incorrect password!"),

    EMPTY_SONG_NAME_STRING("Song name is empty!"),
    EMPTY_SONG_COMPOSER_STRING("Song composer string is empty!"),
    EMPTY_SONG_COMPOSERS_LIST("Song composers list is empty!"),
    EMPTY_SONG_POETS_LIST("Song poets list is empty!"),
    EMPTY_SONG_POET_STRING("Song poet string is empty!"),
    EMPTY_SONG_ARTIST_STRING("Song artist is empty"),
    INVALID_SONG_TIME("Inсorrect song time!"),
    SONG_ALREADY_EXIST("Song already exists!"),
    SONG_NOT_FOUND("Song is not found!"),
    WRONG_SONG_ID("Song id is wrong!"),
    INVALID_SONG_FILTERS("Invalid filters"),
    EMPTY_FILTER("Empty filter!"),

    INVALID_SONG_RATING("Incorrect song rating!"),
    RATING_ALREADY_EXISTS("This user already rates this song!"),
    RATING_NOT_FOUND("Rating is not found!"),

    EMPTY_COMMENT_STRING("Comment is empty!"),
    COMMENT_ALREADY_EXISTS("This user has already added comment to this song!"),
    COMMENT_NOT_FOUND("Comment is not found!"),
    WRONG_COMMENT_ID("Comment id is wrong!"),
    IMPOSSIBLE_CHANGE_COMMENT("Impossible to change comment!");

    private String errorString;

    ServerErrorCode(String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

    public static ServerErrorCode fromString(String stateString) {
        return ServerErrorCode.valueOf(stateString);
    }


}
