package com.epam.jwd_final.web.mapper;

import com.epam.jwd_final.web.domain.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ModelMapper<E extends Entity> {

    E mapToEntity(ResultSet rs) throws SQLException;
}
