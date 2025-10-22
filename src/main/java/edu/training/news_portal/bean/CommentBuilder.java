package edu.training.news_portal.bean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

public class CommentBuilder {

	public static Comment buildCommentFromRequest(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new IllegalStateException("No active session found.");
		}

		User user = (User) session.getAttribute("auth");
		if (user == null) {
			throw new IllegalStateException("User is not authenticated.");
		}

		String commentText = request.getParameter("commentText");
		String newsIdStr = request.getParameter("newsId");

		if (commentText == null || commentText.trim().isEmpty()) {
			throw new IllegalArgumentException("Comment text is empty.");
		}

		if (newsIdStr == null || newsIdStr.trim().isEmpty()) {
			throw new IllegalArgumentException("News ID is missing.");
		}

		int newsId;
		try {
			newsId = Integer.parseInt(newsIdStr);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid news ID format.");
		}

		Comment comment = new Comment();
		comment.setText(commentText.trim());
		comment.setNewsId(newsId);
		comment.setUserId(user.getId());
		comment.setUserName(user.getName());
		comment.setCreatedAt(LocalDateTime.now());
		comment.setStatusId(1); 

		return comment;
	}
}
