package net.thumbtack.school.concert.dto.response;

import net.thumbtack.school.concert.model.ConcertData;

import java.util.List;

public class ConcertDataListDtoResponse {

    private List<ConcertData> concertData;

    public ConcertDataListDtoResponse(List<ConcertData> concertData) {
        this.concertData = concertData;
    }

    public List<ConcertData> getConcertData() {
        return concertData;
    }
}
