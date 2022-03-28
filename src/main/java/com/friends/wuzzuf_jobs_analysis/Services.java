package com.friends.wuzzuf_jobs_analysis;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Service;


@Service
public class Services {
	private DataReader dr = DataReader.getReader();
	private Dataset<Row> result =  dr.readCSV("src/main/resources/Wuzzuf_Jobs.csv");
	
	private JobDAO jobDao = new JobDAO(result);
	private Visualizer visualizer = new Visualizer();
	
	public Map<String, Object> getSummary(){
        return jobDao.getSummary();
    }
	
	public List<Map> getMostPopularCompanies() {	
    	return jobDao.getMostPopularCompanies();
    }
	
	public List<Map> getMostPopularJobTiltes() {
		return jobDao.getMostPopularJobTiltes();
	}
	
	public List<Map> getMostPopularAreas() {
		return jobDao.getMostPopularAreas();
	}
	
	public List<Map> getMostPopularSkills() {
		return jobDao.getMostWantedSkills();
	}
	
	public String getTop20CompaniesChartPath(){
        try {
        	String path = "target/classes/top_20_companies.jpg";
        	Map<String, Long> res = jobDao.getMostCompaniesJobsMap();
        	visualizer.savePieChart("Top 20 Companies", res, path);
            return "top_20_companies.jpg";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public String getTop20TitlesChartPath(){
        try {
        	String path = "target/classes/top_20_titles.jpg";
        	
        	List<Map> top20Titles = jobDao.getMostPopularJobTiltes();
        	List titles = top20Titles.stream().map(map -> map.get("title")).collect(Collectors.toList());
        	List counts = top20Titles.stream().map(map -> map.get("jobs_count")).collect(Collectors.toList());
        	
        	visualizer.saveBarChart("Top 20 titles", titles, counts, "Tilte", "Count", path);
            return "top_20_titles.jpg";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public String getTop50AreasChartPath(){
		try {
        	String path = "target/classes/top_50_Areas.jpg";
        	
        	List<Map> top50Areas = getMostPopularAreas();
        	List areas = top50Areas.stream().map(map -> map.get("location")).collect(Collectors.toList());
        	List counts = top50Areas.stream().map(map -> map.get("count")).collect(Collectors.toList());
        	
        	visualizer.saveBarChart("Top 50 Areas", areas, counts, "Area", "Count", path);
            return "top_50_Areas.jpg";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
	
}
