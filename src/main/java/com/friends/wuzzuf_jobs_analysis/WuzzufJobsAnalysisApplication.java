package com.friends.wuzzuf_jobs_analysis;

import java.io.IOException;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
public class WuzzufJobsAnalysisApplication {

	public static void main(String[] args) throws IOException{
//		SpringApplication.run(WuzzufJobsAnalysisApplication.class, args);
		DataReader dr = DataReader.getReader();
		Dataset<Row> result =  dr.readCSV("src/main/resources/Wuzzuf_Jobs.csv");
		
		
		JobDAO jobDAO = new JobDAO(result);
		
		jobDAO.head();
		jobDAO.removeDuplicates();
		
	}

}
