package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.dto.EventDto;
import com.epam.jwd_final.web.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * EventService.
 *
 * @author Ostapchuk Kirill
 */
public interface EventService {

    boolean createEvent(LocalDateTime start, String firstTeam, String secondTeam, EnumMap<Result, BigDecimal> coefficients)
            throws ServiceException;

    List<EventDto> findAllUnfinishedByDateBetween(LocalDateTime from, LocalDateTime to) throws ServiceException;

    void cancel(int id) throws ServiceException;
}
