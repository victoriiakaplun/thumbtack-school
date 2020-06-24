package net.thumbtack.school.concert.dto.response;

import net.thumbtack.school.concert.error.ServerErrorCode;

public class ErrorResponseDto {

    private String errorCode;
    private String message;

    private ErrorResponseDto(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorResponseDto(ServerErrorCode errorCode) {
        this(errorCode.name(), errorCode.getErrorString());
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
