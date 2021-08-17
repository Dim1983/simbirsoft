package org.simbirsoft.dao;

import org.simbirsoft.domain.Query;

import java.util.Optional;

public interface QueryDao extends CrudDao<Query,Integer>{
    Optional<Query> findByLink(String link);
}
