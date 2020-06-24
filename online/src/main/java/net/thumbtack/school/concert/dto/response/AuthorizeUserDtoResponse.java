package net.thumbtack.school.concert.dto.response;

public class AuthorizeUserDtoResponse {

    private String uuidString;

    public AuthorizeUserDtoResponse(String token) {
        this.uuidString = token;
    }

    public String getUuid() {
        return uuidString;
    }
}
