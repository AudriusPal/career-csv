package com.example.careercsv.report.service;

import com.example.careercsv.report.ReportDTO;
import com.example.careercsv.report.Transaction;
import com.example.careercsv.report.parser.FileParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ActiveProfiles(profiles = {"test"})
@ExtendWith({SpringExtension.class})
class ReportServiceTest {

    @Configuration
    @ComponentScan(basePackages = {"com.example.careercsv.report.service.impl"})
    static class TestConfig {}

    @MockBean
    FileParser fileParser;

    @Autowired
    private ReportService reportService;

    @DisplayName("Test report from list of transactions")
    @Test
    void testGenerateReportFromListOfTransactions() {

        List<Transaction> transactions = List.of(
          new Transaction("1", "1", "1", LocalDate.of(2020, 10, 5), 1.0, 1),
          new Transaction("2", "1", "1", LocalDate.of(2020, 10, 5), 1.0, 2),
          new Transaction("3", "2", "2", LocalDate.of(2021, 10, 5), 1.0, 3));

        ReportDTO reportDTO = reportService.generateReport(transactions);

        Assertions.assertThat(reportDTO).isNotNull();
        Assertions.assertThat(reportDTO.totalRevenue()).isEqualTo(6.0);
        Assertions.assertThat(reportDTO.uniqueCustomersNumber()).isEqualTo(2);
        Assertions.assertThat(reportDTO.mostPopularItemId()).isEqualTo("1");
        Assertions.assertThat(reportDTO.dateWithHighestRevenue()).isEqualTo(LocalDate.of(2020, 10, 5));
    }
}
