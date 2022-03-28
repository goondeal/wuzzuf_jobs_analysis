package com.friends.wuzzuf_jobs_analysis;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WuzzufJobsAnalysisApplication {

	public static void main(String[] args) throws IOException{
		SpringApplication.run(WuzzufJobsAnalysisApplication.class, args);
		System.out.println("Serving...");
		
		
//		DataReader dr = DataReader.getReader();
//		Dataset<Row> result =  dr.readCSV("src/main/resources/Wuzzuf_Jobs.csv");
//		
//		
//		JobDAO jobDAO = new JobDAO(result);
//		Visualizer visualizer = new Visualizer();
		
//		jobDAO.head();
//		System.out.println(jobDAO.getMostWantedSkills());
//		System.out.println(jobDAO.getMostPopularAreas(20));
//		System.out.println(jobDAO.getJobs().stream().limit(5).map(Job::toJSON).collect(Collectors.toList()));
//		System.out.println(jobDAO.getMostCompaniesJobs().size());
//		System.out.println(jobDAO.getMostCompaniesJobs());
		
//		visualizer.displayBarChart(jobDAO.getMostCompaniesJobsMap());
//		System.out.println("Hello");
//		jobDAO.printSkills();
//		jobDAO.getYearsOFExp();
//		jobDAO.showDistinctJobTypes();
//		jobDAO.cleanData();
//		jobDAO.printMostCompaniesJobs();
//		jobDAO.printMostJobTiltes();
	
	}

}
