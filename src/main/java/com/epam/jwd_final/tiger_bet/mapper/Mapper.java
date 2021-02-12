package com.epam.jwd_final.tiger_bet.mapper;

import java.sql.SQLException;

public interface Mapper<SOURCE, TARGET> {

    TARGET mapFrom(SOURCE source) throws SQLException;
}
