package com.epam.jwd_final.tiger_bet.service;

import java.util.List;
import java.util.Optional;

public interface GeneralService<T> {

    Optional<List<T>> findAll();
}
