package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.ModelMapperException;
import com.epam.jwd_final.web.mapper.ModelMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamModelMapper implements ModelMapper<Team> {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    @Override
    public Team mapToEntity(ResultSet rs) throws ModelMapperException {
        try {
            final int id = rs.getInt(ID_COLUMN);
            final String name = rs.getString(NAME_COLUMN);
            return new Team(id, name);
        } catch (SQLException e) {
            throw new ModelMapperException(e.getMessage(), e.getCause());
        }
    }
}
