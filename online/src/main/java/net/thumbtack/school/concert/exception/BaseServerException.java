package net.thumbtack.school.concert.exception;

import net.thumbtack.school.concert.error.ServerErrorCode;

public class BaseServerException extends Exception {

    private ServerErrorCode errorCode;

    public BaseServerException(ServerErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ServerErrorCode getErrorCode() {
        return errorCode;
    }
}
