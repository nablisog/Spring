package com.retriever.models;

import java.util.List;

public class Buckets {

	private String name;
	private String alternateName;
	private int value;
	private List<String> buckets;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public List<String> getBuckets() {
		return buckets;
	}
	public void setBuckets(List<String> buckets) {
		this.buckets = buckets;
	}
	
	
	
}
