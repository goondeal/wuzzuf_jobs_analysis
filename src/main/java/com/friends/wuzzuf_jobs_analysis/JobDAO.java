package com.friends.wuzzuf_jobs_analysis;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions.col;

public class JobDAO {
	private Dataset<Row> dataset;
	
	public JobDAO(Dataset<Row> df) {
		this.dataset = df;
	}
	
	public void head() {
		dataset.show(10);
	}
	
	public void summarize(){
        System.out.println("Total Rows count: " + dataset.count());
        
        System.out.println("******************** Description ***********************");
        Dataset<Row> describe = dataset.describe();
        describe.show();
        
        System.out.println("******************** DataSet Schema ***********************");
        // TODO: Replace with dataset.
        describe.printSchema();
    }

	public Dataset<Row> getDataset() {
        return dataset;
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

    public void printMostCompaniesJobs() {
    	Dataset<Row> result = dataset.groupBy("Company").count().sort(col("count").desc());
//    	SparkSession session = DataReader.getReader().getSparkSession();
//        dataset.createOrReplaceTempView("wuzzuf");
//        Dataset<Row> result = session.sql("SELECT Company AS company, COUNT(Company) AS jobs_count" +
//                " FROM wuzzuf GROUP BY Company " +
//                "ORDER BY jobs_count DESC");
    	result.show();
    }
	
	
	
}
