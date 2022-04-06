package com.retriever.models;

import java.util.List;

public class JsonBodyObject {
	private int totalHits;
	private int offset;
	private String keyset;
	List<Documents> documents;
	List<Facets> facets;
//	List<Aggregations> aggregations;
	public int getTotalHits() {
		return totalHits;
	}
	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getKeyset() {
		return keyset;
	}
	public void setKeyset(String keyset) {
		this.keyset = keyset;
	}
	public List<Documents> getDocuments() {
		return documents;
	}
	public void setDocuments(List<Documents> documents) {
		this.documents = documents;
	}
	public List<Facets> getFacets() {
		return facets;
	}
	public void setFacets(List<Facets> facets) {
		this.facets = facets;
	}
//	public List<Aggregations> getAggregations() {
//		return aggregations;
//	}
//	public void setAggregations(List<Aggregations> aggregations) {
//		this.aggregations = aggregations;
//	}
	
	
}
