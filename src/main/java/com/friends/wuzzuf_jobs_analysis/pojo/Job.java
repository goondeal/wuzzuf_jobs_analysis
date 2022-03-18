package com.friends.wuzzuf_jobs_analysis.pojo;

import java.util.ArrayList;

public class Job {
	private String title;
    private Company company;
    private JobType type;
    private String level;
    private String yearsOfExp;
    private ArrayList<String> skills;
	public Job(String title, Company company, JobType type, String level, String yearsOfExp, ArrayList<String> skills) {
		super();
		this.title = title;
		this.company = company;
		this.type = type;
		this.level = level;
		this.yearsOfExp = yearsOfExp;
		this.skills = skills;
	}
  
    
}
