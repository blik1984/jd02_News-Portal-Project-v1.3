package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import edu.training.news_portal.bean.News;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.service.NewsService;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.web.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PageUserHome implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();
	private static final int PAGE_SIZE = 6;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		String errorMessage = "Пожалуйста авторизуйтесь!";
		String encodedMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8.toString());

		if (session == null || session.getAttribute("auth") == null) {
			response.sendRedirect("Controller?command=page_auth&message=" + encodedMessage);
			return;
		}

		User user = (User) session.getAttribute("auth");

		String pageParam = request.getParameter("page");
		int currentPage = 1;
		if (pageParam != null) {
			try {
				currentPage = Integer.parseInt(pageParam);
				if (currentPage < 1) {
					currentPage = 1;
				}
			} catch (NumberFormatException e) {
				currentPage = 1;
			}
		}

		try {
			List<News> pagedNews = newsService.getNewsPage(currentPage, PAGE_SIZE);

			int totalNews = newsService.countAllNews();
			int totalPages = (int) Math.ceil((double) totalNews / PAGE_SIZE);

			request.setAttribute("topNews", pagedNews);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("totalPages", totalPages);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userHome.jsp");
			dispatcher.forward(request, response);

		} catch (ServiceException e) {
			request.setAttribute("errorMessage", "Ошибка при загрузке новостей");
			response.sendRedirect("Controller?command=page_error");
		}
	}
}
