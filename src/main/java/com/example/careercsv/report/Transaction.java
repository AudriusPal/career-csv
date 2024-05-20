package com.example.careercsv.report;

import java.time.LocalDate;

public record Transaction (
        String transactionId,
        String customerId,
        String itemId,
        LocalDate transactionDate,
        Double itemPrice,
        Integer itemQuantity
) {
}
