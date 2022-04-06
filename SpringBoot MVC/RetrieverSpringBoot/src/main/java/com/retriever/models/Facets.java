package com.retriever.models;

import java.util.List;

public class Facets {

	private String name;
	private List<Buckets> buckets;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Buckets> getBuckets() {
		return buckets;
	}
	public void setBuckets(List<Buckets> buckets) {
		this.buckets = buckets;
	}
	
	
	
}
