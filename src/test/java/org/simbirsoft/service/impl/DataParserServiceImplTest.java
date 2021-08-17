package org.simbirsoft.service.impl;

import org.junit.jupiter.api.Test;
import org.simbirsoft.service.DataParserService;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class DataParserServiceImplTest {
    private final String URL_EXAMPLE = "https://ru.stackoverflow.com/questions/490427/java-lang-nullpointerexception-attempt-to-write-to-field-int-android-support-v?noredirect=1&lq=1";

    DataParserService dataParserService = new DataParserServiceImpl();

    @Test
    void getWordCountShouldReturnQuantityWordOnPage() {
        StringBuilder result = new StringBuilder();
        try {
            File file = new File("src/test/resources/example.txt");
            if(file.exists()) {
                file.delete();
            }

            file.createNewFile();

            FileWriter resultData = new FileWriter(file);

            resultData.write(dataParserService.getWordCount(URL_EXAMPLE));
            resultData.flush();
            resultData.close();

            FileReader fileReader = new FileReader("src/test/resources/example.txt");
            Scanner sc = new Scanner(fileReader);
            while (sc.hasNext()) {
                result.append(sc.next()).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        assertThat(dataParserService.getWordCount(URL_EXAMPLE), is(result.toString()));
    }

}