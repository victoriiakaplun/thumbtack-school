package net.thumbtack.school.concert.dto;

import net.thumbtack.school.concert.exception.BaseServerException;

public interface IValidated {

    void validate() throws BaseServerException;
}
