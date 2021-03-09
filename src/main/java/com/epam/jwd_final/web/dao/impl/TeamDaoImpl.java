package com.epam.jwd_final.web.dao.impl;

import com.epam.jwd_final.web.dao.AbstractDao;
import com.epam.jwd_final.web.dao.TeamDao;
import com.epam.jwd_final.web.domain.Team;
import com.epam.jwd_final.web.exception.DaoException;
import com.epam.jwd_final.web.mapper.ModelMapper;
import com.epam.jwd_final.web.mapper.impl.TeamModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TeamDaoImpl extends AbstractDao<Team> implements TeamDao {

    private static final String FIND_ONE_BY_ID_SQL =
            "select id, name from team where id = ?";

    private static final String FIND_ONE_BY_NAME_SQL =
            "select id, name from team where name = ?";

    private static final String FIND_ALL_SQL =
            "select id, name from team";

    @Override
    public Optional<Team> findOneById(int id) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_ID_SQL,
                Collections.singletonList(id)
        );
    }

    @Override
    public Optional<Team> findOneByName(String name) throws DaoException {
        return querySelectOne(
                FIND_ONE_BY_NAME_SQL,
                Collections.singletonList(name)
        );
    }

    @Override
    public List<Team> findAll() throws DaoException {
        return querySelectAll(
                FIND_ALL_SQL,
                Collections.emptyList()
        );
    }

    @Override
    protected ModelMapper<Team> retrieveModelMapper() {
        return new TeamModelMapper();
    }
}
