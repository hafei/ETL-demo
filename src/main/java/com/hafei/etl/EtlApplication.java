package com.hafei.etl;

import com.hafei.etl.model.CusipInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class EtlApplication implements CommandLineRunner {
	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public static void main(String[] args) {
		log.info("Starting ETL application");
		SpringApplication.run(EtlApplication.class, args);
		log.info("ETL application completed");
	}

	@Override
	public void run(String... args) throws Exception {
		// Generate fake CUSIP data
		log.info("Generating fake CUSIP data");

		//where is the log file?
		List<CusipInfo> cusipInfoList = generateFakeData(10000);

		log.info("Saving fake CUSIP data to database");
		// Save CUSIP data to database
		saveToDatabase(cusipInfoList);

		// Start background thread to update JDBC configuration every 10 seconds
		// ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		// executorService.scheduleAtFixedRate(new JdbcConfigUpdater(dataSource, jdbcTemplate), 0, 10, TimeUnit.SECONDS);
	}

	private List<CusipInfo> generateFakeData(int numRows) throws IOException {
		List<CusipInfo> cusipInfoList = new ArrayList<>();

		// Load fake data CSV file from resources folder
		Resource resource = new ClassPathResource("fake_data.csv");
		InputStream inputStream = resource.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

		// Parse CSV data
		CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(reader);
		int count = 0;
		for (CSVRecord record : csvParser) {
			if (count >= numRows) {
				break;
			}
			CusipInfo cusipInfo = new CusipInfo(
					record.get("CUSIP"), record.get("Issuer"), record.get("IssueDate"), record.get("MaturityDate"),
					Double.parseDouble(record.get("CouponRate")), Double.parseDouble(record.get("Price")),
					Double.parseDouble(record.get("ParValue")), Double.parseDouble(record.get("MarketValue")),
					record.get("Rating"), record.get("Sector"));
			cusipInfoList.add(cusipInfo);
			count++;
		}

		// Close the CSV parser and input stream
		csvParser.close();
		reader.close();
		inputStream.close();

		return cusipInfoList;
	}

	private void saveToDatabase(List<CusipInfo> cusipInfoList) {
		for (CusipInfo cusipInfo : cusipInfoList) {
			jdbcTemplate.update("INSERT INTO CUSIP_Info (CUSIP, Issuer, IssueDate, MaturityDate, "
							+ "CouponRate, Price, ParValue, MarketValue, Rating, Sector) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					cusipInfo.getCusip(), cusipInfo.getIssuer(), cusipInfo.getIssueDate(), cusipInfo.getMaturityDate(),
					cusipInfo.getCouponRate(), cusipInfo.getPrice(), cusipInfo.getParValue(), cusipInfo.getMarketValue(),
					cusipInfo.getRating(), cusipInfo.getSector());
		}
		System.out.println("Imported " + cusipInfoList.size() + " CUSIPs into database.");
	}

	private static class JdbcConfigUpdater implements Runnable {

		private final DataSource dataSource;
		private final JdbcTemplate jdbcTemplate;

		public JdbcConfigUpdater(DataSource dataSource, JdbcTemplate jdbcTemplate) {
			this.dataSource = dataSource;
			this.jdbcTemplate = jdbcTemplate;
		}

		@Override
		public void run() {
			try {
				// Load JDBC properties from properties file
				Resource resource = new ClassPathResource("application.properties");
				InputStream inputStream = resource.getInputStream();
				Properties properties = new Properties();
				properties.load(inputStream);

				// Update DataSource bean with new properties
				String url = properties.getProperty("jdbc.url");
				String username = properties.getProperty("jdbc.username");
				String password = properties.getProperty("jdbc.password");
				String driverClassName = properties.getProperty("jdbc.driverClassName");

				DataSource newDataSource = DataSourceBuilder.create()
						.url(url)
						.username(username)
						.password(password)
						.driverClassName(driverClassName)
						.build();
				jdbcTemplate.setDataSource(newDataSource);

				// Close the input stream
				inputStream.close();

				System.out.println("Updated JDBC configuration: " + url);
			} catch (IOException e) {
				System.err.println("Error updating JDBC configuration: " + e.getMessage());
			}
		}
	}
}
