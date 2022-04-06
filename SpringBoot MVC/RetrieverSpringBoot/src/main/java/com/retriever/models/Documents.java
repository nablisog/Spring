package com.retriever.models;

import java.util.List;

public class Documents {
	private String id;
	private Source source;
	private String socialsource;
	private String story;
	private String status;
	private boolean repost;
	private String docId;
	private String url;
	private int profileId;
	private String profileName;
	private boolean isGroup;
	private long docdate;
	private long updated;
	private List<Attachments> attachments;
	private List<String> tags;
	private Author author;
	private List<Urls> urls;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	public String getSocialsource() {
		return socialsource;
	}
	public void setSocialsource(String socialsource) {
		this.socialsource = socialsource;
	}
	public String getStory() {
		return story;
	}
	public void setStory(String story) {
		this.story = story;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isRepost() {
		return repost;
	}
	public void setRepost(boolean repost) {
		this.repost = repost;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public long getDocdate() {
		return docdate;
	}
	public void setDocdate(long docdate) {
		this.docdate = docdate;
	}
	public long getUpdated() {
		return updated;
	}
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	public List<Attachments> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachments> attachments) {
		this.attachments = attachments;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public List<Urls> getUrls() {
		return urls;
	}
	public void setUrls(List<Urls> urls) {
		this.urls = urls;
	}
}
