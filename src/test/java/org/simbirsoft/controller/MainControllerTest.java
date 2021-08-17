package org.simbirsoft.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.simbirsoft.config.SpringConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
class MainControllerTest {
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
                .setName("testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;")
                .setType(H2)
                .addScript("schema.sql")
                .build();

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(embeddedDatabase);
    }

    @Test
    void givenWacWhenServletContextThenItProvidesMainController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("mainController"));
    }

    @Test
    void givenHomePageURIWhenMockMVCThenReturnsIndexJSPViewName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.TEXT_HTML))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name("index"));
    }

    @Test
    public void givenCreateURIWithQueryParameterWhenMockMVCThenResponseResult() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/create")
                .param("link", "https://coderoad.ru/36615330/Mockito-doAnswer-%D0%BF%D1%80%D0%BE%D1%82%D0%B8%D0%B2-thenReturn"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("lists"))
                .andExpect(view().name("result"));
    }
}
