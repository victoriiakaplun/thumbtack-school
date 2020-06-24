package net.thumbtack.school.concert.dto.response;

public class RegisterUserDtoResponse {

    private String uuidString;

    public RegisterUserDtoResponse(String token) {
        this.uuidString = token;
    }

    public String getUuid() {
        return uuidString;
    }
}
