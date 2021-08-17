package org.simbirsoft.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simbirsoft.dao.QueryDao;
import org.simbirsoft.domain.Query;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryServiceImplTest {
    @Mock
    DataParserServiceImpl dataParserService;

    @Mock
    QueryDao queryDao;

    @InjectMocks
    QueryServiceImpl queryService;

    @Test
    void insertQueryShouldBeCallQueryDaoSave() {
        when(dataParserService.getWordCount(anyString())).thenReturn("div-2");
        doNothing().when(queryDao).save(Mockito.anyObject());

        queryService.insertQuery(Mockito.anyString());

        Mockito.verify(queryDao, Mockito.times(1)).save(Mockito.anyObject());
        Mockito.verify(dataParserService, Mockito.times(1)).getWordCount(anyString());
    }

    @Test
    void getQueryByLinkShouldBeCallQueryDaoFindByLink() {
        Query expected = Query.builder()
                .withID(1)
                .withLink("link")
                .withWordAndQuantity("result")
                .build();
        when(queryDao.findByLink(anyString())).thenReturn(Optional.of(expected));

        queryService.getQueryByLink(Mockito.anyString());

        Mockito.verify(queryDao, Mockito.times(2)).findByLink(anyString());
    }

    @Test
    void getQueryByIdShouldBeCallQueryDaoFindById() {
        Query expected = Query.builder()
                .withID(1)
                .withLink("link")
                .withWordAndQuantity("result")
                .build();
        when(queryDao.findById(anyInt())).thenReturn(Optional.of(expected));

        queryService.getQueryById(anyInt());

        Mockito.verify(queryDao, Mockito.times(2)).findById(anyInt());
    }
}