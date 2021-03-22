package com.epam.jwd_final.web.mapper.impl;

import com.epam.jwd_final.web.domain.Bet;
import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.ModelMapperException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BetModelMapperTest {

    private static final String ID_COLUMN = "id";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String MULTIPLIER_ID_COLUMN = "multiplier_id";
    private static final String BET_MONEY_COLUMN = "bet_money";

    @Mock
    private ResultSet resultSet;

    @Test
    void mapToEntityTest_ShouldPass() throws SQLException, ModelMapperException {
        when(resultSet.getInt(ID_COLUMN)).thenReturn(1);
        when(resultSet.getInt(USER_ID_COLUMN)).thenReturn(69);
        when(resultSet.getInt(MULTIPLIER_ID_COLUMN)).thenReturn(37);
        when(resultSet.getBigDecimal(BET_MONEY_COLUMN)).thenReturn(new BigDecimal("2.23"));

        Bet bet = new BetModelMapper().mapToEntity(resultSet);

        assertEquals(1, bet.getId());
        assertEquals(69, bet.getUserId());
        assertEquals(37, bet.getMultiplierId());
        assertEquals(new BigDecimal("2.23"), bet.getBetMoney());
    }
}