package com.example.careercsv.report.parser;

import com.example.careercsv.report.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.InputStream;
import java.util.List;

@ActiveProfiles(profiles = {"test"})
@ExtendWith({SpringExtension.class})
class FileParserTest {

    @Configuration
    @ComponentScan(basePackages = {"com.example.careercsv.report.parser.impl"})
    static class TestConfig {}

    @Autowired
    private FileParser fileParser;

    @Nested
    @DisplayName("Loaded from files")
    class LoadedFromFileTest {
        @DisplayName("Test reading CSV file with 20 values")
        @Test
        void testParseValidCsvFileWith20Records() {

            int expectedRecordsSize = 20;
            InputStream inputStream = getClass().getResourceAsStream("/csv/valid_all_values_20_records.csv");

            Assertions.assertThat(inputStream).isNotNull();

            Assertions.assertThatCode (() -> {
                List<Transaction> transactionList = fileParser.parse(inputStream);
                Assertions.assertThat(transactionList).isNotNull();
                Assertions.assertThat(transactionList).hasSize(expectedRecordsSize);

                transactionList.stream().forEach(t -> {
                    Assertions.assertThat(t).isNotNull();

                    Assertions.assertThat(t.transactionId()).isNotBlank();
                    Assertions.assertThat(t.customerId()).isNotBlank();
                    Assertions.assertThat(t.itemId()).isNotBlank();
                    Assertions.assertThat(t.transactionDate()).isNotNull();
                    Assertions.assertThat(t.itemPrice()).isNotNull();
                    Assertions.assertThat(t.itemQuantity()).isNotNull();
                });

            }).doesNotThrowAnyException();
        }
    }
}
