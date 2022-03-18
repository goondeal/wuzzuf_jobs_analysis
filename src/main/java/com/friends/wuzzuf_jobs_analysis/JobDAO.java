package com.friends.wuzzuf_jobs_analysis;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

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

    public Dataset<Row> getDataset() {
        return dataset;
    }
	
	
	
}
