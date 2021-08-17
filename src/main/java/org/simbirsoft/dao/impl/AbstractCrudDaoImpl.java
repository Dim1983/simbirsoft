package org.simbirsoft.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simbirsoft.dao.CrudDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Integer> {
    protected NamedParameterJdbcTemplate jdbcTemplate;
    private static final Integer LIMIT_LINE_OF_PAGE = 10;

    private final String saveQuery;
    private final String deleteQuery;
    private final String updateQuery;
    private final String findAllQuery;
    private final String findQuery;
    private final RowMapper<E> rowMapper;

    @Override
    public void save(E entity) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        insert(parameters, entity);

        jdbcTemplate.update(saveQuery, parameters);
    }

    @Override
    public Optional<E> findById(Integer id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return Optional.of(jdbcTemplate.queryForObject(findQuery, params, rowMapper));
        } catch (EmptyResultDataAccessException e) {

            return Optional.empty();
        }
    }

    @Override
    public List<E> findAll(Integer offset) {
        MapSqlParameterSource parameter = new MapSqlParameterSource();
        parameter.addValue("limit", LIMIT_LINE_OF_PAGE);
        parameter.addValue("offset", offset);

        return jdbcTemplate.query(findAllQuery, parameter, rowMapper);
    }

    @Override
    public void update(E entity) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        insert(parameters, entity);

        jdbcTemplate.update(updateQuery, parameters);
    }

    @Override
    public void deleteById(Integer id) {
        Map<String, Integer> parametr = new HashMap<>();
        parametr.put("id", id);

        jdbcTemplate.update(deleteQuery, parametr);
    }

    public abstract void insert(MapSqlParameterSource parameters, E entity);
}
