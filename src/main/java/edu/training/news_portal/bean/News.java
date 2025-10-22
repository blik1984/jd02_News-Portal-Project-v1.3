package edu.training.news_portal.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class News {

	private int id;
	private NewsGroups group;
	private String title;
	private String brief;
	private String content;
	private LocalDateTime publishingDateTime;
	private LocalDateTime createDateTime;
	private LocalDateTime updateDateTime;
	private List<String> authors = new ArrayList<String>();
	private User publisher;
	private String fileContentPath;

	public News() {
	}

	public News(int id, NewsGroups group, String title, String brief, String content, List<String> authors,
			User publisher) {
		this.id = id;
		this.group = group;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.authors = authors;
		this.publisher = publisher;
	}

	public News(int id, NewsGroups group, String title, String brief, String content, User publisher) {
		this.id = id;
		this.group = group;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.publisher = publisher;
	}

	public boolean addAuthor(String user) {
		if (!authors.contains(user)) {
			authors.add(user);
			return true;
		}
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NewsGroups getGroup() {
		return group;
	}

	public void setGroup(NewsGroups group) {
		this.group = group;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getPublishingDateTime() {
		return publishingDateTime;
	}

	public void setPublishingDateTime(LocalDateTime publishingDateTime) {
		this.publishingDateTime = publishingDateTime;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}
	
	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public String getFileContentPath() {
		return fileContentPath;
	}

	public void setFileContentPath(String fileContentPath) {
		this.fileContentPath = fileContentPath;
	}

	@Override
	public int hashCode() {
		return Objects.hash(authors, brief, content, createDateTime, fileContentPath, group, id, publisher,
				publishingDateTime, title, updateDateTime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		News other = (News) obj;
		return Objects.equals(authors, other.authors) && Objects.equals(brief, other.brief)
				&& Objects.equals(content, other.content) && Objects.equals(createDateTime, other.createDateTime)
				&& Objects.equals(fileContentPath, other.fileContentPath) && group == other.group && id == other.id
				&& Objects.equals(publisher, other.publisher)
				&& Objects.equals(publishingDateTime, other.publishingDateTime) && Objects.equals(title, other.title)
				&& Objects.equals(updateDateTime, other.updateDateTime);
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", group=" + group + ", title=" + title + ", brief=" + brief + ", content=" + content
				+ ", publishingDateTime=" + publishingDateTime + ", createDateTime=" + createDateTime
				+ ", updateDateTime=" + updateDateTime + ", authors=" + authors + ", publisher=" + publisher
				+ ", fileContentPath=" + fileContentPath + "]";
	}

}
