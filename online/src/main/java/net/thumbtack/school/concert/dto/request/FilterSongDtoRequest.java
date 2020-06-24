package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

import java.util.List;

public class FilterSongDtoRequest implements IValidated {

    private String uuidString;
    private List<String> filters;

    public FilterSongDtoRequest(String uuidString, List<String> filters) {
        this.uuidString = uuidString;
        this.filters = filters;
    }

    public String getUuidString() {
        return uuidString;
    }

    public List<String> getFilters() {
        return filters;
    }

    @Override
    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        if (filters.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.INVALID_SONG_FILTERS);
        }
        for (String filter : filters) {
            if (filter.isEmpty()) {
                throw new BaseServerException(ServerErrorCode.EMPTY_FILTER);
            }
        }
    }
}
