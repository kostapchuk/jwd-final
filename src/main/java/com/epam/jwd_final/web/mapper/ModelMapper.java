package com.epam.jwd_final.web.mapper;

import com.epam.jwd_final.web.domain.Entity;
import com.epam.jwd_final.web.exception.ModelMapperException;

import java.sql.ResultSet;

public interface ModelMapper<E extends Entity> {

    E mapToEntity(ResultSet rs) throws ModelMapperException;
}
