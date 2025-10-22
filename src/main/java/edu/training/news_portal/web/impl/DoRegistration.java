package edu.training.news_portal.web.impl;

import java.io.IOException;

import edu.training.news_portal.bean.RegistrationInfo;
import edu.training.news_portal.service.ServiceException;
import edu.training.news_portal.service.ServiceProvider;
import edu.training.news_portal.service.UserSecurity;
import edu.training.news_portal.web.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DoRegistration implements Command {

	private final UserSecurity userSecurity = ServiceProvider.getInstance().getUserSecurity();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RegistrationInfo.RegBuilder builder = new RegistrationInfo.RegBuilder();
		builder.email(request.getParameter("email")).password(request.getParameter("password"))
				.name(request.getParameter("name"));

		RegistrationInfo regInfo = builder.build();

		try {
			if (userSecurity.emailExists(regInfo.getEmail())) {
				request.setAttribute("errorMessage", "Пользователь с таким email уже существует");
				request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
				return;
			}

			userSecurity.registration(regInfo);
			response.sendRedirect("Controller?command=page_auth");

		} catch (ServiceException e) {
			// Логировать, возможно отправить на страницу ошибки
			request.setAttribute("errorMessage", "Ошибка регистрации. Попробуйте позже.");
			request.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(request, response);
		}
	}
}