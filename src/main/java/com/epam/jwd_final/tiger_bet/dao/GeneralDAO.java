package com.epam.jwd_final.tiger_bet.dao;

import com.epam.jwd_final.tiger_bet.domain.Entity;

import java.util.List;
import java.util.Optional;

public interface GeneralDAO<T extends Entity> {

    Optional<T> queryForSingleResult(String query, List<Object> params);

    List<T> query(String query, List<Object> params);

}
