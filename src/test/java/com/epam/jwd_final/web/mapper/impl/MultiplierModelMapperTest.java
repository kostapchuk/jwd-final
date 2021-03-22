package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Multiplier;
import com.epam.jwd_final.web.exception.ModelMapperException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MultiplierModelMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String MATCH_ID_COLUMN = "match_id";
    private static final String RESULT_TYPE_ID_COLUMN = "result_type_id";
    private static final String COEFFICIENT_COLUMN = "coefficient";

    @Mock
    private ResultSet resultSet;

    @Test
    void mapToEntityTest_ShouldPass() throws SQLException, ModelMapperException {
        when(resultSet.getInt(ID_COLUMN)).thenReturn(1);
        when(resultSet.getInt(MATCH_ID_COLUMN)).thenReturn(25);
        when(resultSet.getInt(RESULT_TYPE_ID_COLUMN)).thenReturn(1);
        when(resultSet.getBigDecimal(COEFFICIENT_COLUMN)).thenReturn(new BigDecimal("2.23"));

        Multiplier multiplier = new MultiplierModelMapper().mapToEntity(resultSet);

        assertEquals(1, multiplier.getId());
        assertEquals(25, multiplier.getMatchId());
        assertEquals(1, multiplier.getResult().getId());
        assertEquals(new BigDecimal("2.23"), multiplier.getCoefficient());
    }
}