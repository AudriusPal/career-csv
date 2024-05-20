package com.example.careercsv.report.parser;

import com.example.careercsv.report.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileParser {

    String HEADER_TRANSACTION_ID = "transaction_id";
    String HEADER_CUSTOMER_ID = "customer_id";
    String HEADER_ITEM_ID = "item_id";
    String HEADER_TRANSACTION_DATE = "transaction_date";
    String HEADER_ITEM_PRICE = "item_price";
    String HEADER_ITEM_QUANTITY = "item_quantity";

    List<Transaction> parse(InputStream inputStream) throws IOException;
}
