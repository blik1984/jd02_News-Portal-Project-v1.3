package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.util.List;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.bean.News;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.service.CommentService;
import edu.training.news_portal.service.NewsService;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PageNews implements Command {
	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private final CommentService commentService = ServiceProvider.getInstance().getCommentService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idParam = request.getParameter("id");

		if (idParam == null) {
			response.sendRedirect("Controller?command=page_main");
			return;
		}

		try {
			int newsId = Integer.parseInt(idParam);

			News news = newsService.getNews(newsId);

			if (news == null) {
				response.sendRedirect("Controller?command=page_main");
				return;
			}

			User currentUser = (User) request.getSession().getAttribute("auth");

			// вызов нового метода с текущим пользователем
			List<Comment> comments = commentService.getCommentsByNews(newsId, currentUser);
			
			String editingCommentIdStr = request.getParameter("editingCommentId");
			if (editingCommentIdStr != null) {
				try {
					int editingCommentId = Integer.parseInt(editingCommentIdStr);
					request.setAttribute("editingCommentId", editingCommentId);
				} catch (NumberFormatException e) {
					// невалидный ID — игнорируем
				}
			}

			request.setAttribute("news", news);
			request.setAttribute("comments", comments);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/news.jsp");
			dispatcher.forward(request, response);

		} catch (NumberFormatException | ServiceException e) {
			e.printStackTrace();
			response.sendRedirect("Controller?command=page_main");
		}
	}
}