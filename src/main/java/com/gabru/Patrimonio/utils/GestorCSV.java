package com.gabru.Patrimonio.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
@Service
public class GestorCSV {
    public List<CSVRecord> getRegistrosCsv (MultipartFile file, Charset charset, CSVFormat format) throws IOException {
        List<CSVRecord> registros = new ArrayList<>();
        try {
            CSVParser parser = CSVParser.parse(file.getInputStream(), charset,format);
            registros = parser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registros;
    }
}
