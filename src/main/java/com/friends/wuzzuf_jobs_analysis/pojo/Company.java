package com.friends.wuzzuf_jobs_analysis.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Company {
	
	private String name;
	private String country;
	private String location;
	
	public Company(String name, String country, String location) {
		this.name = name;
		this.country = country;
		this.location = location;
	}
	public String getName() {
		return name;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getLocation() {
		return location;
	}
	
	@Override
	public String toString() {
		return "Company [name=" + name + ", country=" + country + ", location=" + location + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(name, other.name);
	}
	
	public Map<String, Object> toJSON() {
		Map<String, Object> result = new HashMap<>();
		result.put("name", getName());
		result.put("country", getCountry());
		result.put("location", getLocation());
		return result;
	}
	
	
	
}
