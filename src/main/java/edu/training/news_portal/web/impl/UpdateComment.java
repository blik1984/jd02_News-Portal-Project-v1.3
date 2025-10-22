package edu.training.news_portal.web.impl;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.training.news_portal.bean.Comment;
import edu.training.news_portal.bean.Role;
import edu.training.news_portal.bean.User;
import edu.training.news_portal.service.CommentService;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateComment implements Command {
	private final CommentService commentService = ServiceProvider.getInstance().getCommentService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User currentUser = (User) request.getSession().getAttribute("auth");
		if (currentUser == null) {
			// Пользователь неавторизован — редирект на страницу входа
			response.sendRedirect("Controller?command=page_auth");
			return;
		}

		try {
			// Получаем параметры из формы
			String commentIdParam = request.getParameter("commentId");
			String commentText = request.getParameter("commentText");

			if (commentIdParam == null || commentText == null || commentText.trim().isEmpty()) {
				// Ошибка, невалидные данные — можно редиректить обратно с ошибкой или просто
				// назад
				response.sendRedirect("Controller?command=page_main");
				return;
			}

			int commentId = Integer.parseInt(commentIdParam);

			// Получаем текущий комментарий из БД (чтобы проверить владельца и новость)
			Comment existingComment = commentService.getCommentById(commentId);

			if (existingComment == null) {
				// Комментарий не найден — редирект назад
				response.sendRedirect("Controller?command=page_main");
				return;
			}

			// Проверяем, что текущий пользователь — владелец комментария или админ
			boolean isOwner = existingComment.getUserId() == currentUser.getId();
			boolean isAdmin = Role.ADMINISTRATOR  == currentUser.getRole();
			boolean within30Minutes = existingComment.getCreatedAt().plusMinutes(30).isAfter(LocalDateTime.now());


			if (!isAdmin && (!isOwner || !within30Minutes)) {
				// Нет прав на редактирование
				response.sendRedirect("Controller?command=page_news&id=" + existingComment.getNewsId());
				return;
			}

			// Обновляем комментарий
			existingComment.setText(commentText.trim());
			existingComment.setUpdatedAt(LocalDateTime.now());

			boolean updated = commentService.updateComment(existingComment, currentUser);

			// После обновления — редирект обратно на страницу новости
			if (updated) {
				response.sendRedirect("Controller?command=page_news&id=" + existingComment.getNewsId());
			} else {
				// Если обновить не удалось — можно тоже редиректить с ошибкой или назад
				response.sendRedirect("Controller?command=page_news&id=" + existingComment.getNewsId());
			}

		} catch (NumberFormatException | ServiceException e) {
			response.sendRedirect("Controller?command=page_main");
		}
	}
}
