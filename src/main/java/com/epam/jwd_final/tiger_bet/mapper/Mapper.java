package com.epam.jwd_final.tiger_bet.mapper;

import com.epam.jwd_final.tiger_bet.domain.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T extends Entity> {

    T mapFrom(ResultSet rs) throws SQLException;
}
