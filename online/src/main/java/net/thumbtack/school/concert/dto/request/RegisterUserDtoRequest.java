package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

public class RegisterUserDtoRequest implements IValidated {

    private String firstName;
    private String lastName;
    private String login;
    private String password;

    public RegisterUserDtoRequest(String firstName, String lastName, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    @Override
    public void validate() throws BaseServerException {
        if (firstName.length() < 4) {
            throw new BaseServerException(ServerErrorCode.TOO_SHORT_FIRST_NAME);
        }
        if (lastName.length() < 4) {
            throw new BaseServerException(ServerErrorCode.TOO_SHORT_LAST_NAME);
        }
        if (login.length() < 4) {
            throw new BaseServerException(ServerErrorCode.TOO_SHORT_LOGIN);
        }
        if (password.length() < 4) {
            throw new BaseServerException(ServerErrorCode.TOO_SHORT_PASSWORD);
        }
        if (password.matches("\\D*")) {
            throw new BaseServerException(ServerErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
