package com.example.careercsv.report;

import java.time.LocalDate;

public record ReportDTO(
        Double totalRevenue,
        Integer uniqueCustomersNumber,
        String mostPopularItemId,
        LocalDate dateWithHighestRevenue
) {
}
