package com.epam.jwd_final.tiger_bet.service;

import com.epam.jwd_final.tiger_bet.domain.BetDto;

import java.util.List;
import java.util.Optional;

public interface BetService {

    Optional<List<BetDto>> findAllBetsByUserName(String name);
}
