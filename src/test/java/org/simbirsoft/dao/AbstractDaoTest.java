package org.simbirsoft.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

public class AbstractDaoTest {
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    void init() {
        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setName("testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;")
                .setType(H2)
                .addScript("schema.sql")
                .build();

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(embeddedDatabase);
    }
}
