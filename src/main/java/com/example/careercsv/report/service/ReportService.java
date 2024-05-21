package com.example.careercsv.report.service;

import com.example.careercsv.report.ReportDTO;
import com.example.careercsv.report.Transaction;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service interface for generating reports
 */
public interface ReportService {

    /**
     * Generates report from file
     *
     * @param file The input MultipartFile file.
     * @return A ReportDTO containing the calculated report data.
     */
    ReportDTO generateReport(MultipartFile file);

    /**
     * Generates report list of transactions
     *
     * @param transactions list of transactions
     * @return A ReportDTO containing the calculated report data.
     */
    ReportDTO generateReport(List<Transaction> transactions);

}
