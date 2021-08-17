package org.simbirsoft.service.impl;

import lombok.Data;
import org.simbirsoft.dao.QueryDao;
import org.simbirsoft.domain.Query;
import org.simbirsoft.service.DataParserService;
import org.simbirsoft.service.QueryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
public class QueryServiceImpl implements QueryService {
    private final QueryDao queryDao;
    private final DataParserService dataParser;

    @Override
    public void insertQuery(String URL) {
        queryDao.save(Query.builder()
                .withLink(URL)
                .withWordAndQuantity(dataParser.getWordCount(URL))
                .build());
    }

    @Override
    public List<String> getQueryByLink(String URL) {
        String str = queryDao.findByLink(URL)
                .isPresent() ? queryDao.findByLink(URL).get().getWordAndQuantity() : "Result not found";

        return Arrays.stream(str.split(",")).collect(Collectors.toList());
    }

    @Override
    public Query getQueryById(Integer id) {
        return queryDao.findById(id)
                .isPresent() ? queryDao.findById(id).get() : Query.builder()
                .withID(null)
                .withLink(null)
                .withWordAndQuantity(null)
                .build();
    }
}
