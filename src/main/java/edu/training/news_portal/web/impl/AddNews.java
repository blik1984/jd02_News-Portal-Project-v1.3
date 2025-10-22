package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import edu.training.news_portal.bean.News;
import edu.training.news_portal.bean.NewsGroups;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.service.NewsService;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddNews implements Command {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService(); 

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String title = request.getParameter("title");
		String brief = request.getParameter("brief");
		String content = request.getParameter("content");
		String groupParam = request.getParameter("group");
		String publishDateTimeParam = request.getParameter("publishDateTime");

		if (title == null || brief == null || content == null || groupParam == null || publishDateTimeParam == null
				|| title.isBlank() || brief.isBlank() || content.isBlank() || groupParam.isBlank()
				|| publishDateTimeParam.isBlank()) {

			request.setAttribute("error", "Все поля обязательны для заполнения");
			request.getRequestDispatcher("WEB-INF/jsp/create_news.jsp").forward(request, response);
			return;
		}

		NewsGroups group;
		try {
			group = NewsGroups.valueOf(groupParam);
		} catch (IllegalArgumentException e) {
			request.setAttribute("error", "Неверная группа новостей");
			request.getRequestDispatcher("WEB-INF/jsp/create_news.jsp").forward(request, response);
			return;
		}

		LocalDateTime publishDateTime;
		try {
			publishDateTime = LocalDateTime.parse(publishDateTimeParam);
		} catch (DateTimeParseException e) {
			request.setAttribute("error", "Неверный формат даты и времени публикации");
			request.getRequestDispatcher("WEB-INF/jsp/create_news.jsp").forward(request, response);
			return;
		}
		User currentUser = (User) request.getSession().getAttribute("auth");

		News news = new News();
		news.setTitle(title);
		news.setBrief(brief);
		news.setContent(content);
		news.setGroup(group);
		news.setPublishingDateTime(publishDateTime);
		news.setPublisher(currentUser);

		try {
			newsService.addNews(news);
			response.sendRedirect("Controller?command=page_main");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", "Ошибка при добавлении новости");
			request.getRequestDispatcher("WEB-INF/jsp/create_news.jsp").forward(request, response);
		}
	}
}
