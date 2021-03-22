package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.ModelMapperException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamModelMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";

    @Mock
    private ResultSet resultSet;

    @Test
    void mapToEntityTest_ShouldPass() throws SQLException, ModelMapperException {
        when(resultSet.getInt(ID_COLUMN)).thenReturn(1);
        when(resultSet.getString(NAME_COLUMN)).thenReturn("Arsenal");

        Team team = new TeamModelMapper().mapToEntity(resultSet);

        assertEquals(1, team.getId());
        assertEquals("Arsenal", team.getName());
    }
}