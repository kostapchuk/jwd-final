package com.epam.jwd_final.web.service.impl;

import com.epam.jwd_final.web.domain.Result;
import com.epam.jwd_final.web.domain.dto.EventDto;
import com.epam.jwd_final.web.domain.dto.MatchDto;
import com.epam.jwd_final.web.exception.ServiceException;
import com.epam.jwd_final.web.service.BetService;
import com.epam.jwd_final.web.service.EventService;
import com.epam.jwd_final.web.service.MatchService;
import com.epam.jwd_final.web.service.MultiplierService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public enum EventServiceImpl implements EventService {

    INSTANCE;

    private final MatchService matchService;
    private final MultiplierService multiplierService;
    private final BetService betService;

    EventServiceImpl() {
        matchService = MatchServiceImpl.INSTANCE;
        multiplierService = MultiplierServiceImpl.INSTANCE;
        betService = BetServiceImpl.INSTANCE;
    }

    @Override
    public void createEvent(LocalDateTime start, String firstTeam, String secondTeam, Map<Result, BigDecimal> coefficients)
            throws ServiceException {
        if (firstTeam.equals(secondTeam)) {
            throw new ServiceException("First team cannot equal second team");
        }

        // TODO: make transactional
        matchService.save(matchService.createMatch(start, firstTeam, secondTeam));
        createMultipliers(
                coefficients,
                matchService.findIdByStartByFirstTeamBySecondTeam(start, firstTeam, secondTeam)
        );
    }

    void createMultipliers(Map<Result, BigDecimal> coefficients, int matchId) throws ServiceException {
        for (Result result : coefficients.keySet()) {
            multiplierService.saveMultiplier(
                    multiplierService.createMultiplier(matchId, result, coefficients.get(result)));
        }
    }

    @Override
    public List<EventDto> findAllUnfinishedByDateBetween(LocalDateTime from, LocalDateTime to) throws ServiceException {
        final List<MatchDto> matchDtos =
                matchService.findAllUnfinishedByDateBetween(from, to);
        List<EventDto> eventDtos = new ArrayList<>();
        for (MatchDto matchDto : matchDtos) {
            final Map<Result, BigDecimal> coefficients = multiplierService.findCoefficientsByMatchId(matchDto.getId());
            eventDtos.add(new EventDto(matchDto, coefficients));
        }
        if (eventDtos.isEmpty()) {
            return Collections.emptyList();
        }
        return eventDtos;
    }

    @Override
    public void cancel(int id) throws ServiceException {
        for (Result value : Result.values()) {
            final int multiplierId = multiplierService.findIdByMatchIdByResult(id, value);
            betService.deleteAllByMultiplierId(multiplierId);
            multiplierService.deleteById(multiplierId);
        }
        matchService.deleteById(id);
    }
}
