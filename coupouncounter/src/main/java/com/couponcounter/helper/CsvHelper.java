package com.couponcounter.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.couponcounter.entity.Product;
import com.couponcounter.exceptions.ExcelFileException;

@Component
public class CsvHelper {
	public static String fileType = "text/csv";
	static String[] csvHeaders = { "product_name", "brand", "mrp" };

	public static List<Product> csvToTutorials(BufferedReader fileReader) throws ExcelFileException {
		try {
			CSVParser csvParser = new CSVParser(fileReader,
					CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			List<Product> products = new ArrayList<Product>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				String brand = csvRecord.get("brand").toLowerCase();
				Product product = new Product(0, csvRecord.get("product_name"), brand,
						Integer.parseInt(csvRecord.get("mrp")));
				products.add(product);
			}
			csvParser.close();
			return products;
		} catch (IOException e) {
			throw new ExcelFileException("fail to parse CSV file: " + e.getMessage());
		}
	}

	public static List<Product> readCsvFileName(String fileName) throws IOException, ExcelFileException {
		FileInputStream excelFile = new FileInputStream(new File(fileName));
		BufferedReader fileReader = new BufferedReader(new InputStreamReader(excelFile, "UTF-8"));
		return csvToTutorials(fileReader);
	}

	public static List<Product> readCsvMultipartFile(MultipartFile file) throws IOException, ExcelFileException {
		InputStream inputStream = file.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		return csvToTutorials(reader);
	}
}