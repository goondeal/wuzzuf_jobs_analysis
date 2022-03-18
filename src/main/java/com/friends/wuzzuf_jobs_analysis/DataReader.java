package com.friends.wuzzuf_jobs_analysis;

import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataReader {
	private SparkSession sparkSession;
	private DataFrameReader dfr;
	
	// Singleton.
	private static DataReader instance;
	
	private DataReader() {
		sparkSession = SparkSession.builder()
        		.appName("Wuzzuf Jobs Analysis")
                .master("local[2]").getOrCreate();
		dfr = sparkSession.read().option("header", true);
	}
	
	public static DataReader getReader() {
		if (instance == null) {
			instance = new DataReader();
		}
		return instance;
	}
	
	public Dataset<Row> readCSV(String path) {
        final Dataset<Row> df = dfr.csv(path);
        
        return df;
	}
	
	public Dataset<Row> readJSON(String path) {
        final Dataset<Row> df = dfr.json(path);
        return df;
	}

}