package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class LogoutUserDtoRequest implements IValidated {

    private String uuidString;

    public LogoutUserDtoRequest(String tokenString) {
        this.uuidString = tokenString;
    }

    @Override
    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
    }

    public String getUuidString() {
        return uuidString;
    }
}
