package com.epam.jwd_final.web.service;

import com.epam.jwd_final.web.exception.ServiceException;

import java.util.List;

/**
 * TeamService.
 *
 * @author Ostapchuk Kirill
 */
public interface TeamService {

    List<String> findAll() throws ServiceException;

    int findIdByName(String name) throws ServiceException;

    String findNameById(int id) throws ServiceException;
}
