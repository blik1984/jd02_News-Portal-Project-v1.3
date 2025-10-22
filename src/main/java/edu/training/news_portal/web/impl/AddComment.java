package edu.training.news_portal.web.impl;

import java.io.IOException;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.bean.CommentBuilder;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.service.CommentService;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddComment implements Command {

	private final CommentService service = ServiceProvider.getInstance().getCommentService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new IllegalStateException("User is not authenticated");
		}

		User user = (User) session.getAttribute("auth");
		if (user == null) {
			throw new IllegalStateException("User is not authenticated");
		}

		Comment comment = CommentBuilder.buildCommentFromRequest(request);
		comment.setUserId(user.getId());
		try {
			service.addComment(comment);
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		response.sendRedirect("Controller?command=page_news&id=" + comment.getNewsId());

	}

}
