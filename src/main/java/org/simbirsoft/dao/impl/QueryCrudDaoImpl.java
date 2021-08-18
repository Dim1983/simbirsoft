package org.simbirsoft.dao.impl;

import org.simbirsoft.dao.QueryDao;
import org.simbirsoft.domain.Query;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class QueryCrudDaoImpl extends AbstractCrudDaoImpl<Query> implements QueryDao {
    private static final String SAVE_QUERY = "INSERT INTO resultdata(link, wordandquantity) values(:link,:wordandquantity)";
    private static final String DELETE_QUERY = "DELETE FROM  resultdata WHERE id = :id";
    private static final String UPDATE_QUERY = "UPDATE resultdata SET (link, wordandquantity) = (:link, :wordandquantity) WHERE id =:id";
    private static final String FIND_ALL_QUERY = "SELECT * FROM resultdata limit :limit offset :offset";
    private static final String FIND_QUERY = "SELECT * FROM resultdata WHERE id = :id";
    private static final String FIND_LINK = "SELECT * FROM resultdata WHERE link = :link limit 1";
    private static final RowMapper<Query> ROW_MAPPER = (resultSet, i) -> Query.builder()
            .withID(resultSet.getInt("id"))
            .withLink(resultSet.getString("link"))
            .withWordAndQuantity(resultSet.getString("wordandquantity"))
            .build();

    public QueryCrudDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SAVE_QUERY, DELETE_QUERY, UPDATE_QUERY, FIND_ALL_QUERY, FIND_QUERY, ROW_MAPPER);
    }

    @Override
    public void insert(MapSqlParameterSource parameters, Query query) {
        System.out.println("Into insert" + query);
        parameters.addValue("id", query.getID());
        parameters.addValue("link", query.getLink());
        parameters.addValue("wordandquantity", query.getWordAndQuantity());
    }

    @Override
    public Optional<Query> findByLink(String link) {
        Map<String, Object> params = new HashMap<>();
        params.put("link", link);

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_LINK, params, ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
