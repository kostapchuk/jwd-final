package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.dto.EventDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface EventService {

    void createEvent(LocalDateTime start, String firstTeam, String secondTeam, Map<Result, BigDecimal> coefficients)
            throws ServiceException;

    List<EventDto> findAllUnfinishedByDateBetween(LocalDate from, LocalDate to) throws ServiceException;

    void cancel(int id) throws ServiceException;
}
