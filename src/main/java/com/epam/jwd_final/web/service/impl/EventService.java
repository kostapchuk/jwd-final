package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.domain.EventDto;
import com.epam.jwd_final.web.domain.MatchDto;
import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public enum EventService {

    INSTANCE;

    private final MatchService matchService;
    private final MultiplierService multiplierService;

    EventService() {
        matchService = MatchServiceImpl.INSTANCE;
        multiplierService = MultiplierServiceImpl.INSTANCE;
    }

    public Optional<List<EventDto>> findAll() throws ServiceException {
        final List<MatchDto> matchDtos = matchService.findAllUnfinishedByDate(LocalDate.now()).orElse(Collections.emptyList());
        List<EventDto> eventDtos = new ArrayList<>();
        for (MatchDto matchDto : matchDtos) {
            final Map<Result, BigDecimal> coefficients = multiplierService.findCoefficientsByMatchId(matchDto.getId());
            eventDtos.add(new EventDto(
                    matchDto, coefficients.get(Result.FIRST_TEAM),
                    coefficients.get(Result.SECOND_TEAM), coefficients.get(Result.DRAW))
            );
        }
        if (eventDtos.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(eventDtos);
    }
}
