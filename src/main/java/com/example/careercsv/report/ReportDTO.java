package com.example.careercsv.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.time.LocalDate;

@JsonRootName("Report")
public record ReportDTO(
    @JsonProperty(value = "totalRevenue")
    Double totalRevenue,

    @JsonProperty(value = "uniqueCustomersNumber")
    Integer uniqueCustomersNumber,

    @JsonProperty(value = "mostPopularItemId")
    String mostPopularItemId,

    @JsonProperty(value = "dateWithHighestRevenue")
    LocalDate dateWithHighestRevenue
) {
}
