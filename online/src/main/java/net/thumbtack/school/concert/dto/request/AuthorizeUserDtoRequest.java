package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class AuthorizeUserDtoRequest implements IValidated {
    private String login;
    private String password;

    public AuthorizeUserDtoRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public void validate() throws BaseServerException {
        if (login.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_LOGIN_STRING);
        }
        if (password.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_PASSWORD_STRING);
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
