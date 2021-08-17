package org.simbirsoft.service.impl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.simbirsoft.service.DataParserService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataParserServiceImpl implements DataParserService {

    @Override
    public String getWordCount(String URL) {
        String str = getConnection(URL);

        return Arrays.stream(str.split(" ")).collect(Collectors.toList())
                .stream()
                .map(s -> s.replaceAll("[^a-zA-Zа-яА-Я]", " "))
                .flatMap(s -> Arrays.stream(s.toLowerCase().split("\\s+")))
                .filter(s -> s.length() > 4)
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
                .map(e -> String.format("%s - %d%s%s", e.getKey(), e.getValue(), ",", " "))
                .collect(Collectors.joining());
    }

    public String getConnection(String URL) {
        try {
            Connection.Response response = Jsoup.connect(URL).
                    userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                    .timeout(10000)
                    .execute();

            return response.parse().outerHtml();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
