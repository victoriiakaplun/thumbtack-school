package net.thumbtack.school.concert.dto.request;

import net.thumbtack.school.concert.dto.IValidated;
import net.thumbtack.school.concert.error.ServerErrorCode;
import net.thumbtack.school.concert.exception.BaseServerException;

import java.util.ArrayList;
import java.util.List;

public class AddSongDtoRequest implements IValidated {
    private String uuidString;
    private List<SongDto> songList;

    public AddSongDtoRequest(String uuidString, List<SongDto> songList) {
        this.uuidString = uuidString;
        this.songList = new ArrayList<>(songList);
    }

    @Override
    public void validate() throws BaseServerException {
        if (uuidString.isEmpty()) {
            throw new BaseServerException(ServerErrorCode.EMPTY_TOKEN);
        }
        for (SongDto songDto : songList) {
            songDto.validate();
        }
    }

    public String getUuidString() {
        return uuidString;
    }

    public List<SongDto> getSongList() {
        return songList;
    }
}
