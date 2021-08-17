package org.simbirsoft.service;

import org.simbirsoft.domain.Query;

import java.util.List;

public interface QueryService {
    void insertQuery(String URL);

    List<String> getQueryByLink(String URL);

    Query getQueryById(Integer id);
}
