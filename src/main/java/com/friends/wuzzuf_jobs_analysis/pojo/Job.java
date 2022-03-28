package com.friends.wuzzuf_jobs_analysis.pojo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Job {
	
	private String title;
	private Company company;
    private JobType type;
    private String level;
    private int minYearsOfExp;
    private int maxYearsOfExp;
    private String[] skills;
    
	public Job(String title, Company company, JobType type, String level, int minYearsOfExp, int maxYearsOfExp, String[] skills) {	
		this.title = title;
		this.company = company;
		this.type = type;
		this.level = level;
		this.minYearsOfExp = minYearsOfExp;
		this.maxYearsOfExp = maxYearsOfExp;
		this.skills = skills;
	}
	
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public JobType getType() {
		return type;
	}

	public void setType(JobType type) {
		this.type = type;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getMinYearsOfExp() {
		return minYearsOfExp;
	}

	public void setMinYearsOfExp(int minYearsOfExp) {
		this.minYearsOfExp = minYearsOfExp;
	}

	public int getMaxYearsOfExp() {
		return maxYearsOfExp;
	}

	public void setMaxYearsOfExp(int maxYearsOfExp) {
		this.maxYearsOfExp = maxYearsOfExp;
	}

	public String[] getSkills() {
		return skills;
	}

	public void setSkills(String[] skills) {
		this.skills = skills;
	}

	@Override
	public String toString() {
		return "Job [title=" + title + ", company=" + company + ", type=" + type + ", level=" + level
				+ ", minYearsOfExp=" + minYearsOfExp + ", maxYearsOfExp=" + maxYearsOfExp + ", skills="
				+ Arrays.toString(skills) + "]";
	}

	public Map<String, Object> toJSON() {
		Map<String, Object> result = new HashMap<>();
		
		result.put("title", getTitle());
		result.put("company", company.toJSON());
		result.put("type", getType().toString());
		result.put("min_years_of_exp", getMinYearsOfExp());
		Object maxYears = getMaxYearsOfExp() < Integer.MAX_VALUE ? getMaxYearsOfExp() : "no_limit";
		result.put("max_years_of_exp", maxYears);
		result.put("skills", getSkills());
		
		return result;
	}
	
	
	
  
    
}
