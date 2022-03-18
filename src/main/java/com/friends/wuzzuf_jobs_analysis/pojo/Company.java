package com.friends.wuzzuf_jobs_analysis.pojo;

import java.util.Objects;

public class Company {
	
	private String name;
	private String city;
	private String location;
	
	public Company(String name, String city, String location) {
		this.name = name;
		this.city = city;
		this.location = location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "Company [name=" + name + ", city=" + city + ", location=" + location + "]";
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
	
}
