package com.example.careercsv.report.service.impl;

import com.example.careercsv.report.ReportDTO;
import com.example.careercsv.report.Transaction;
import com.example.careercsv.report.parser.FileParser;
import com.example.careercsv.report.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
class CsvReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(CsvReportServiceImpl.class);

    private final FileParser fileParser;

    public CsvReportServiceImpl(FileParser fileParser) {
        this.fileParser = fileParser;
    }

    @Override
    public ReportDTO generateReport(MultipartFile file) {
        log.debug("start generating report from file");
        Assert.notNull(file, "file is null!");
        log.trace("report generating from {} with size {}", file.getOriginalFilename(), file.getSize());

        try {
            List<Transaction> transactions = fileParser.parse(file.getInputStream());
            return generateReport(transactions);
        } catch (Exception e) {
            throw new RuntimeException("Error while generating report!");
        }
    }

    @Override
    public ReportDTO generateReport(List<Transaction> transactions) {
        log.debug("start generating report from list of transaction");
        Assert.notNull(transactions, "transactions are null!");

        ReportDTO reportDTO = new ReportDTO(
                calculateTotalRevenue(transactions),
                calculateUniqueCustomersNumber(transactions),
                findMostPopularItemId(transactions),
                findDateWithHighestRevenue(transactions)
        );

        log.trace("generated report {}", reportDTO);
        return reportDTO;
    }


    private Double calculateTotalRevenue(List<Transaction> transactions) {
        Assert.notNull(transactions, "transactions are null!");

        Double totalRevenue = 0.0;

        for (Transaction transaction : transactions) {
            totalRevenue += transaction.itemPrice() * transaction.itemQuantity();
        }
        log.trace("calculated total revenue {}", totalRevenue);
        return totalRevenue;
    }

    private Integer calculateUniqueCustomersNumber(List<Transaction> transactions) {
        Assert.notNull(transactions, "transactions are null!");
        Long uniqueCustomers = transactions.stream().map(t -> t.customerId()).distinct().count();
        log.trace("calculated unique customer {}", uniqueCustomers);
        return uniqueCustomers.intValue();
    }

    private String findMostPopularItemId(List<Transaction> transactions) {
        Assert.notNull(transactions, "transactions are null!");

        Map<String, Integer> countMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            countMap.put(transaction.itemId(), countMap.getOrDefault(transaction.itemId(), 0) + transaction.itemQuantity());
        }
        String itemId = countMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("no value found!"));

        log.trace("found most popular item with id {}", itemId);
        return itemId;
    }

    private LocalDate findDateWithHighestRevenue(List<Transaction> transactions) {
        Assert.notNull(transactions, "transactions are null!");

        Map<LocalDate, Double> countMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            countMap.put(transaction.transactionDate(), countMap.getOrDefault(transaction.transactionDate(), 0.0) +
                    transaction.itemQuantity() * transaction.itemPrice());
        }

        LocalDate highestRevenueDate = countMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new RuntimeException("no value found!"));

        log.trace("found highest revenue date {}", highestRevenueDate);
        return highestRevenueDate;
    }
}
