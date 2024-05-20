package com.example.careercsv.report.parser;

import com.example.careercsv.report.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * Parses the CSV file with headers
 * transaction_id
 * customer_id
 * item_id
 * transaction_date
 * item_price
 * item_quantity
 */
public interface FileParser {

    String HEADER_TRANSACTION_ID = "transaction_id";
    String HEADER_CUSTOMER_ID = "customer_id";
    String HEADER_ITEM_ID = "item_id";
    String HEADER_TRANSACTION_DATE = "transaction_date";
    String HEADER_ITEM_PRICE = "item_price";
    String HEADER_ITEM_QUANTITY = "item_quantity";

    /**
     * Parses a CSV file provided as an input stream.
     *
     * @param inputStream The input stream containing the CSV data.
     * @return A list of transactions parsed from the CSV file.
     * @throws IOException If an I/O error occurs while reading the input stream.
     */
    List<Transaction> parse(InputStream inputStream) throws IOException;
}
