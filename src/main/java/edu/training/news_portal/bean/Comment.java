package edu.training.news_portal.bean;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {

	private int id;
	private int newsId;
	private int userId;
	private String userName;
	private String text;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int statusId;
	private String status;
	private boolean editable;

	public Comment() {
	}

	public Comment(int id, int newsId, int userId, String userName, String text, LocalDateTime createdAt, int statusId,
			String status) {
		this.id = id;
		this.newsId = newsId;
		this.userId = userId;
		this.userName = userName;
		this.createdAt = LocalDateTime.now();
		this.statusId = statusId;
		this.status = status;
		
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getId() {
		return id;
	}

	public int getNewsId() {
		return newsId;
	}

	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusId() {
		return statusId;
	}

	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, editable, id, newsId, status, statusId, text, updatedAt, userId, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		return Objects.equals(createdAt, other.createdAt) && editable == other.editable && id == other.id
				&& newsId == other.newsId && Objects.equals(status, other.status) && statusId == other.statusId
				&& Objects.equals(text, other.text) && Objects.equals(updatedAt, other.updatedAt)
				&& userId == other.userId && Objects.equals(userName, other.userName);
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", newsId=" + newsId + ", userId=" + userId + ", userName=" + userName + ", text="
				+ text + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", statusId=" + statusId
				+ ", status=" + status + ", editable=" + editable + "]";
	}

}
