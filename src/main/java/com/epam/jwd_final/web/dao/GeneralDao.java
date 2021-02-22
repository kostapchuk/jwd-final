package com.epam.jwd_final.web.dao;

import com.epam.jwd_final.web.domain.Entity;

import java.util.List;
import java.util.Optional;

public interface GeneralDao<T extends Entity> {

    Optional<T> querySelectOne(String query, List<Object> params);

    Optional<List<T>> querySelectAll(String query, List<Object> params);
}
