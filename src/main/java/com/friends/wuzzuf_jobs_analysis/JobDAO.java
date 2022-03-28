package com.friends.wuzzuf_jobs_analysis;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.util.Collections.reverseOrder;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.col;

import com.friends.wuzzuf_jobs_analysis.pojo.Company;
import com.friends.wuzzuf_jobs_analysis.pojo.Job;
import com.friends.wuzzuf_jobs_analysis.pojo.JobType;


public class JobDAO {
	private Dataset<Row> dataset;
	private ArrayList<Job> jobs;

	public JobDAO(Dataset<Row> df) {
		this.dataset = df;
		cleanData();
		this.jobs = serializeDatasetToJobs();
	}
	
	public Dataset<Row> getDataset() {
        return dataset;
    }
	
	public ArrayList<Job> getJobs() {
		return jobs;
	}
	
	private ArrayList<Job> serializeDatasetToJobs() {
		ArrayList<Job> jobs = new ArrayList<>();
		dataset.collectAsList().forEach(row -> {
			String title = row.getString(0).strip();
			
			String companyName = row.getString(1).strip();
			String companyLocation = row.getString(2).strip();
			String companyCity = row.getString(6).strip();
			Company company = new Company(companyName, companyCity, companyLocation);
			
			JobType jobType = serializeJobType(row.getString(3).strip());
			String level = row.getString(4).strip();
			int[] yearsOfExp = getYearsExp(row.getString(5).strip());
			String[] skills = getSkills(row.getString(7).strip());
			
			Job job = new Job(title, company, jobType, level, yearsOfExp[0], yearsOfExp[1], skills);
			
			jobs.add(job);
		});
		
		return jobs;
	}
	
	private JobType serializeJobType(String jobTypeStr) {
		switch (jobTypeStr) {
			case "Part Time":
				return JobType.PART_TIME;
			case "Work From Home":
				return JobType.WORK_FROM_HOME;	
			case "Freelance / Project":
				return JobType.FREELANCE_PROJECT;
			case "Full Time":
				return JobType.FULL_TIME;
			case "Shift Based":
				return JobType.SHIFT_BASED;	
			case "Internship":
				return JobType.INTERNSHIP;	
			default:
				return JobType.UNDEFINED;
		}
	}
	
	
	/* Read the dataset, convert it to DataFrame or Spark Dataset, and display some from it. */
	public void head() {
		dataset.show(10);
	}
	
	// Display structure and summary of the data.
	public Map<String, Object> getSummary(){
        Map<String, Object> result = new HashMap<>();
        
        result.put("total_jobs", dataset.count());
        result.put("first_20_jobs", jobs.stream().limit(20).map(Job::toJSON).collect(Collectors.toList()));
        return result;
    }

	
	public void removeNulls() {
	    System.out.println("Total Rows count before removing nan values: " + dataset.count());
	    // Remove dataset NaN values.
	    dataset = dataset.na().drop();
	    System.out.println("Total Rows count After removing nan values: " + dataset.count());
	}
	
	public void removeDuplicates() {
	    System.out.println("Total Rows count before removing duplicates: " + dataset.count());
	    // Remove dataset duplicates.
	    dataset = dataset.dropDuplicates();
	    System.out.println("Total Rows count after removing duplicates: " + dataset.count());
	}
		
	public void cleanData() {
		removeNulls();
		removeDuplicates();
	}

    /*
     * Count the jobs for each company and display that in order.
     * (What are the most demanding companies for jobs ?)
     * */
    public Dataset<Row> getMostCompaniesJobs() {
    	Dataset<Row> result = dataset.groupBy("Company").count().sort(col("count").desc());
    	return result;	
    }
    
    // To be used to draw the BarChart.
    public Map<String, Long> getMostCompaniesJobsMap() {	
    	Map<String, Long> result = new HashMap<String, Long>();
    	getMostCompaniesJobs()
    	.limit(20)
    	.collectAsList()
    	.forEach(row -> result.put(row.getString(0), row.getLong(1)));
    	
    	return result;
    }
    
    public List<Map> getMostPopularCompanies() {
    	List<Map> result = new ArrayList<>();
    	getMostCompaniesJobs()
    	.limit(20)
    	.collectAsList()    	
    	.forEach(row -> {
    		Map<String, Object> map = new HashMap<>();
    		List<Company> tmp = jobs.
    				stream()
    				.map(Job::getCompany)
    				.filter(c -> c.getName().equals(row.getString(0))).collect(Collectors.toList());
    		if (!tmp.isEmpty()) {
    			map.put("company", tmp.get(0).toJSON());
    		}
    		else {
    			
    			map.put("company", row.getString(0));
    		}
    		
    		map.put("jobs_count", row.getLong(1));
    		result.add(map);
    	});
    	
    	return result;
    }
    
    
    // Find out what are the most popular job titles.
    public List<Map> getMostPopularJobTiltes() {
    	Dataset<Row> resultDf = dataset.groupBy("Title").count().sort(col("count").desc());
    	List<Map> result = new ArrayList<>();
    	
    	resultDf
    	.limit(20)
    	.collectAsList()    	
    	.forEach(row -> {
    		Map<String, Object> map = new HashMap<>();
    		
    		map.put("title", row.getString(0));
    		map.put("jobs_count", row.getLong(1));
    		result.add(map);
    	});
    	
    	return result;
    }
    
    
    // Helper method to see if it's feasible to make an Enum for job types or not.
    public void showDistinctJobTypes() {
    	dataset.select("Type").distinct().show();
    }
    
    // Find out the most popular areas (top 50).
    public List<Map> getMostPopularAreas() {
    	Map<String, Long> result = jobs.stream()
    	.map(Job::getCompany)
    	.map(Company::getLocation)
    	.collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    	
    	return result.entrySet()
		  .stream()
		  .sorted(reverseOrder(Map.Entry.comparingByValue()))
		  .limit(50)
		  .map(e -> {
			  Map<String, Object> map = new HashMap<>();
			  map.put("location", e.getKey());
			  map.put("count", e.getValue());
			  return map;
		  })
		  .collect(Collectors.toList());

    }

    
 // Print skills one by one and how many each repeated and order the output
    // to find out the most important skills required.
    public List<Map> getMostWantedSkills() {
    	Map<String, Integer> result = new HashMap<>();
    	
    	jobs.stream().map(Job::getSkills).forEach(skillSet -> {
    		for (String skill : skillSet) {
				if (result.containsKey(skill))
					result.put(skill, result.get(skill)+1);
				else
					result.put(skill, 1);
			}
    	});
    	
    	return result.entrySet()
    			  .stream()
    			  .sorted(reverseOrder(Map.Entry.comparingByValue()))
    			  .limit(50)
    			  .map(e -> {
    				  Map<String, Object> map = new HashMap<>();
    				  map.put("skill", e.getKey());
    				  map.put("count", e.getValue());
    				  return map;
    			  })
    			  .collect(Collectors.toList());
    }
    
    private String[] getSkills(String skillsCSV) {
    	String[] skills = skillsCSV.split(",");
    	
    	List<String> result = new ArrayList<>();
    	for (String skill: skills) {
			String processedSkill = skill.trim().toLowerCase();
			if (!processedSkill.isEmpty())
				result.add(processedSkill);	
		}
    	return result.toArray(new String[0]);
    }
    
    
    // Helper method 
    public void printSkills() {
    	dataset.select("Skills")
    	.collectAsList()
    	.stream()
    	.map(row -> row.toString())
    	.map(this::getSkills)
    	.forEach(System.out::println);
    }
    
    
    // Factorize the YearsExp feature and convert it to numbers in new col. (Bonus )
    private int[] getYearsExp(String yearsStr) {
    	Pattern rangePattern = Pattern.compile("\\d+-\\d+"); // matches: 1-3
    	Matcher matcher = rangePattern.matcher(yearsStr);
    	if (matcher.find()) {
    		String[] minMax = matcher.group().split("-");
    		return new int[] {Integer.valueOf(minMax[0]), Integer.valueOf(minMax[1])};
    	}
    	
    	Pattern minPattern = Pattern.compile("\\d+\\+"); // matches: 5+
    	Matcher matcher2 = minPattern.matcher(yearsStr);
    	if (matcher2.find()) {
    		String[] minMax = matcher2.group().split("\\+");
    		return new int[] {Integer.valueOf(minMax[0]), Integer.MAX_VALUE};
    	}
    	
    	// null scenario.
    	return new int[] {0, Integer.MAX_VALUE};
        
    }
    
    
    // Helper method that displays years_of_exp different patterns. 
    public void getYearsOFExp() {
    	dataset.select("YearsExp")
    	.collectAsList()
    	.stream()
    	.map(row -> row.toString().split(" ")[0]) // from (1-3 years of exp) get (1-3) only. 
    	.map(this::getYearsExp)
    	.forEach(System.out::println);
    }
    
  
}
