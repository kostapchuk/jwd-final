package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.exception.ServiceException;

import java.util.List;

public interface TeamService {

    List<String> findAll() throws ServiceException;
}