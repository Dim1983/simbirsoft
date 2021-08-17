package org.simbirsoft.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simbirsoft.dao.impl.QueryCrudDaoImpl;
import org.simbirsoft.domain.Query;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class QueryCrudDaoImplTest extends AbstractDaoTest {
    private QueryCrudDaoImpl queryCrudDaoTest;

    @BeforeEach
    void init() {
        super.init();
        queryCrudDaoTest = new QueryCrudDaoImpl(namedParameterJdbcTemplate);
    }

    @Test
    void saveShouldBeInsertEntityInToDataBase() {
        Query query = Query.builder()
                .withID(1)
                .withWordAndQuantity("resultlink")
                .withLink("link")
                .build();
        queryCrudDaoTest.save(query);

        assertThat(queryCrudDaoTest.findById(1).get(), is(query));
    }

    @Test
    void findByIdShouldReturnEntityFromDataBase() {
        Query query = Query.builder()
                .withID(1)
                .withWordAndQuantity("resultlink")
                .withLink("link")
                .build();
        queryCrudDaoTest.save(query);

        assertThat(queryCrudDaoTest.findById(1).get(), is(query));
    }

    @Test
    void findAllShouldReturnAllEntityFromDataBase() {
        Query firstQuery = Query.builder()
                .withID(1)
                .withWordAndQuantity("resultlink")
                .withLink("link")
                .build();
        Query secondQuery = Query.builder()
                .withID(2)
                .withWordAndQuantity("resultlink")
                .withLink("link")
                .build();

        queryCrudDaoTest.save(firstQuery);
        queryCrudDaoTest.save(secondQuery);

        assertThat(queryCrudDaoTest.findAll(0), is(Arrays.asList(firstQuery, secondQuery)));
    }

    @Test
    void updateShouldBeUpdateEntityInToDataBase() {
        Query oldQuery = Query.builder()
                .withID(1)
                .withWordAndQuantity("resultlink")
                .withLink("link")
                .build();

        Query newQuery = Query.builder()
                .withID(1)
                .withWordAndQuantity("newresultlink")
                .withLink("newlink")
                .build();

        queryCrudDaoTest.save(oldQuery);
        queryCrudDaoTest.update(newQuery);

        assertThat(queryCrudDaoTest.findById(1).get(), is(newQuery));
    }

    @Test
    void deleteShouldBeDeleteEntityFromDataBase() {
        Query query = Query.builder()
                .withID(1)
                .withWordAndQuantity("newresultlink")
                .withLink("newlink")
                .build();
        queryCrudDaoTest.save(query);

        queryCrudDaoTest.deleteById(1);

        Optional<Query> expected = Optional.empty();

        assertThat(queryCrudDaoTest.findById(1), is(expected));
    }

    @Test
    void findByLinkShouldBeReturnEntityFromDataBase() {
        Query query = Query.builder()
                .withID(1)
                .withWordAndQuantity("newresultlink")
                .withLink("newlink")
                .build();

        queryCrudDaoTest.save(query);

        assertThat(queryCrudDaoTest.findByLink("newlink").get(), is(query));
    }
}
