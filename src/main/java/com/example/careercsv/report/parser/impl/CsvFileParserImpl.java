package com.example.careercsv.report.parser.impl;

import com.example.careercsv.report.Transaction;
import com.example.careercsv.report.parser.FileParser;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
class CsvFileParserImpl implements FileParser {

    private static final Logger log = LoggerFactory.getLogger(CsvFileParserImpl.class);

    private static final String[] HEADERS = { HEADER_TRANSACTION_ID, HEADER_CUSTOMER_ID, HEADER_ITEM_ID,
            HEADER_TRANSACTION_DATE, HEADER_ITEM_PRICE, HEADER_ITEM_QUANTITY};

    @Override
    public List<Transaction> parse(InputStream inputStream) throws IOException {

        log.debug("parsing input stream");
        Assert.notNull(inputStream, "can't parse, stream is null");

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .setSkipHeaderRecord(true)
                .build();

        Reader reader = new InputStreamReader(inputStream);
        Iterable<CSVRecord> csvRecords = csvFormat.parse(reader);

        List<Transaction> transactions = new ArrayList<>();

        for (CSVRecord csvRecord : csvRecords) {
            String transactionId = csvRecord.get(HEADER_TRANSACTION_ID);
            String customerId = csvRecord.get(HEADER_CUSTOMER_ID);
            String itemId = csvRecord.get(HEADER_ITEM_ID);
            LocalDate transactionDate = LocalDate.parse(csvRecord.get(HEADER_TRANSACTION_DATE));
            Double itemPrice = Double.parseDouble(csvRecord.get(HEADER_ITEM_PRICE));
            Integer itemQuantity = Integer.parseInt(csvRecord.get(HEADER_ITEM_QUANTITY));

            Transaction transaction = new Transaction(transactionId, customerId, itemId, transactionDate, itemPrice, itemQuantity);
            log.trace("parsed entry {}", transaction);

            transactions.add(transaction);
        }

        return transactions;
    }
}
